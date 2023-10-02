package com.pulsepreassurenotes.domain.repository

import android.content.ContentValues
import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.GenericTypeIndicator
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.pulsepreassurenotes.model.Record
import java.util.Calendar

const val REFERENCE = "recoder"

class FragmentRepoImpl(
    private var loadDataFirebaseCallback: (MutableList<Record>) -> Unit
) : FragmentRepo {

    var listRecordsTemp: MutableList<Record> = mutableListOf()

    override fun setListRecords(newListRecords: MutableList<Record>) {
        listRecordsTemp = newListRecords
    }

    override fun loadListRecordsFromfireBaseDatabase() {
        val database = Firebase.database
        var myRef = database.getReference(com.pulsepreassurenotes.domain.presenters.REFERENCE)
        myRef.keepSynced(true)
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val listDataType = object : GenericTypeIndicator<List<Record>>() {}
                val listData = snapshot.getValue(listDataType) ?: mutableListOf()
                listRecordsTemp = listData.toMutableList()
                changeRecordItem()
            }

            override fun onCancelled(error: DatabaseError) {
                ///  viewState.loadMarkers(mutableListOf())
                Log.w(
                    ContentValues.TAG, "ErrorFireBase",
                    error.toException()
                )
            }

        })

    }

    override fun addRecord(): MutableList<Record> {
        val calendar = Calendar.getInstance()
        listRecordsTemp.add(
            Record(
                year = calendar.get(Calendar.YEAR),
                month = getMonthName(calendar.get(Calendar.MONTH)),
                date = calendar.get(Calendar.DAY_OF_MONTH),
                hours = calendar.get(Calendar.HOUR_OF_DAY),
                minutes = calendar.get(Calendar.MINUTE)
            )
        )
        return listRecordsTemp
    }

    override fun saveListRecords() {
        val database = Firebase.database
        var myRef = database.getReference(REFERENCE)
        myRef.keepSynced(true)
        if (!listRecordsTemp.isNullOrEmpty()) {
            myRef.setValue(listRecordsTemp.toList())
        }
    }

    override fun onCorrectionClick(i: Int, record: Record) {
        listRecordsTemp[i] = record
    }

    override fun onRemove(i: Int): MutableList<Record> {
        listRecordsTemp.removeAt(i)
        return listRecordsTemp
    }

    private fun getMonthName(number: Int) = listOf(
        "январь", "февраль", "март", "апрель", "май", "июнь",
        "июль", "август", "сентябрь", "октябрь", "ноябрь", "декабрь"
    )[number]

    private fun changeRecordItem() {
        loadDataFirebaseCallback(listRecordsTemp)
    }
}
