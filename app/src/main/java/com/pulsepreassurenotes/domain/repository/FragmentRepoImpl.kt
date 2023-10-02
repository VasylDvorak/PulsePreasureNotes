package com.pulsepreassurenotes.domain.repository

import com.pulsepreassurenotes.delegates.FireBaseDatabaseDelegate
import com.pulsepreassurenotes.model.Record
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

    private fun getMonthName(number: Int) = listOf(
        "январь", "февраль", "март", "апрель", "май", "июнь",
        "июль", "август", "сентябрь", "октябрь", "ноябрь", "декабрь"
    )[number]
}
