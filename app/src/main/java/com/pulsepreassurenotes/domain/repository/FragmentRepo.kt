package com.pulsepreassurenotes.domain.repository


import com.pulsepreassurenotes.model.Record

interface FragmentRepo {
    fun addRecord(): MutableList<Record>
    fun saveListRecords()
    fun onCorrectionClick(i: Int, record: Record)
    fun onRemove(i: Int): MutableList<Record>
    fun setListRecords(newListRecords: MutableList<Record>)
    fun loadListRecordsFromfireBaseDatabase()
}
