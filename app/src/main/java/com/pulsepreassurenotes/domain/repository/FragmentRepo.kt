package com.pulsepreassurenotes.domain.repository


import com.pulsepreassurenotes.model.Record
import io.reactivex.rxjava3.core.Single

interface FragmentRepo {
    fun getRecords(): Single<MutableList<Record>>
    fun addRecord(): MutableList<Record>
    fun saveListRecords()
    fun onCorrectionClick(i: Int, record: Record)
    fun onRemove(i: Int): MutableList<Record>
}
