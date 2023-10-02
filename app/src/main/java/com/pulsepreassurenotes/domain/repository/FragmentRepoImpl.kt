package com.pulsepreassurenotes.domain.repository

import android.content.Context
import com.pulsepreassurenotes.delegates.FireBaseDatabaseDelegate
import com.pulsepreassurenotes.model.Record
import com.pulsepreasurenotes.R
import org.koin.java.KoinJavaComponent.getKoin
import java.util.Calendar

class FragmentRepoImpl(
    private var loadDataFirebaseCallback: (MutableList<Record>) -> Unit
) : FragmentRepo {
    var listFromFireBase: MutableList<Record> by FireBaseDatabaseDelegate(::loadDataFireBase)
    var listRecordsTemp: MutableList<Record> = mutableListOf()
    private fun loadDataFireBase(records: MutableList<Record>) {
        listRecordsTemp = records
        loadDataFirebaseCallback(listRecordsTemp)
    }

    override fun setListRecords(newListRecords: MutableList<Record>) {
        listRecordsTemp = newListRecords
    }

    override fun loadListRecordsFromfireBaseDatabase() = listFromFireBase

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
        listFromFireBase = listRecordsTemp
    }

    override fun onCorrectionClick(i: Int, record: Record) {
        listRecordsTemp[i] = record
    }

    override fun onRemove(i: Int): MutableList<Record> {
        listRecordsTemp.removeAt(i)
        return listRecordsTemp
    }

    private fun getMonthName(number: Int): String {
        val context = getKoin().get<Context>()
        return listOf(
            context.getString(R.string.january),
            context.getString(R.string.february),
            context.getString(R.string.march),
            context.getString(R.string.april),
            context.getString(R.string.may),
            context.getString(R.string.june),
            context.getString(R.string.july),
            context.getString(R.string.august),
            context.getString(R.string.september),
            context.getString(R.string.october),
            context.getString(R.string.november),
            context.getString(R.string.december))[number]
    }
}