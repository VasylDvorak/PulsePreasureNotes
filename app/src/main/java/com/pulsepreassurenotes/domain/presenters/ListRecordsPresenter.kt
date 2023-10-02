package com.pulsepreassurenotes.domain.presenters


import com.pulsepreassurenotes.domain.repository.FragmentRepo
import com.pulsepreassurenotes.domain.repository.FragmentRepoImpl
import com.pulsepreassurenotes.domain.view_MVP.IFragmentView
import com.pulsepreassurenotes.model.Record
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.core.Single
import moxy.MvpPresenter

const val REFERENCE = "recoder"

class ListRecordsPresenter : MvpPresenter<IFragmentView>() {


    private val mainThreadScheduler: Scheduler = AndroidSchedulers.mainThread()

    private var listRecordsRepo: FragmentRepo = FragmentRepoImpl(::loadDataFireBase)

    private fun loadDataFireBase(records: MutableList<Record>) {
        viewState.loadMarkers(records)
    }


    public override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.init()

    }

    lateinit var callRecordsRepo: Single<MutableList<Record>>
    fun loadMarkers() {
        callRecordsRepo = Single.just(mutableListOf())
        loadRecordsJavaRx()
    }


    fun loadRecordsJavaRx() {


        callRecordsRepo
            .observeOn(mainThreadScheduler)
            .subscribe({ markers ->
                listRecordsRepo.loadListRecordsFromfireBaseDatabase()
            }, {
                viewState.loadMarkers(mutableListOf())
            })
    }

    fun onCorrectionClick(i: Int, marker: Record) {
        listRecordsRepo.onCorrectionClick(i, marker)
    }

    fun onRemove(i: Int): MutableList<Record> = listRecordsRepo.onRemove(i)

    fun saveListRecords() {
        callRecordsRepo
            .observeOn(mainThreadScheduler)
            .subscribe({ markers ->
                listRecordsRepo.saveListRecords()
            }, {

            })
    }

    fun addRecord(): MutableList<Record> = listRecordsRepo.addRecord()

}
