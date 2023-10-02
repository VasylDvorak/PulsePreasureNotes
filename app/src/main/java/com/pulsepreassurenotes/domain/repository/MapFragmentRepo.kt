package com.pulsepreassurenotes.domain.repository

import com.pulsepreassurenotes.delegates.FireBaseDatabaseDelegate
import com.pulsepreassurenotes.model.Record
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.Calendar

class MapFragmentRepo : IMapFragmentRepo {
    var listFireBaseDatabase: MutableList<Record> by FireBaseDatabaseDelegate()
    private var listRecords: MutableList<Record> = mutableListOf()

    override fun getMarkers(): Single<MutableList<Record>> {
        if (!listFireBaseDatabase.isNullOrEmpty()) {
            listRecords = listFireBaseDatabase
        } else {
            listRecords = mutableListOf()
        }
        val output = listRecords
        return Single.fromCallable { output }.subscribeOn(Schedulers.io())
    }

    override fun addMarkerOnMap(): MutableList<Record> {
        val calendar = Calendar.getInstance()
        listRecords.add(
            Record(
                id = listRecords.size,
                year = calendar.get(Calendar.YEAR),
                month = getMonthName(calendar.get(Calendar.MONTH)),
                date = calendar.get(Calendar.DAY_OF_MONTH),
                hours = calendar.get(Calendar.HOUR_OF_DAY),
                minutes = calendar.get(Calendar.MINUTE)
            )
        )
        return listRecords
    }

    override fun saveListMarkers() {
        listFireBaseDatabase = listRecords
    }

    override fun onCorrectionClick(i: Int, marker: Record) {
        listRecords[i] = marker
    }

    override fun onRemove(i: Int): MutableList<Record> {
        listRecords.removeAt(i)
        return listRecords
    }

    private fun getMonthName(number: Int) = listOf(
            "январь", "февраль", "март", "апрель", "май", "июнь",
            "июль", "август", "сентябрь", "октябрь", "ноябрь", "декабрь")[number]
}
