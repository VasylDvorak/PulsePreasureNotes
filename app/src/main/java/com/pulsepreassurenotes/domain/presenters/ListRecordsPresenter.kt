package com.pulsepreassurenotes.domain.presenters


import com.pulsepreassurenotes.domain.repository.FragmentRepo
import com.pulsepreassurenotes.domain.repository.FragmentRepoImpl
import com.pulsepreassurenotes.domain.view_MVP.IFragmentView
import com.pulsepreassurenotes.model.Record
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.schedulers.Schedulers
import moxy.MvpPresenter


class ListRecordsPresenter : MvpPresenter<IFragmentView>() {


    private val mainThreadScheduler: Scheduler = AndroidSchedulers.mainThread()

    private var listRecordsRepo: FragmentRepo = FragmentRepoImpl(::loadDataFireBase)

    private fun loadDataFireBase(records: MutableList<Record>) {
        viewState.loadRecords(records)
    }

    public override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.init()
    }

    fun loadMarkers() {
        Observable.just(listRecordsRepo.loadListRecordsFromfireBaseDatabase())
            .subscribeOn(Schedulers.io())
            .observeOn(mainThreadScheduler)
    }

    fun onCorrectionClick(i: Int, marker: Record) {
        Observable.just(listRecordsRepo.onCorrectionClick(i, marker))
            .subscribeOn(Schedulers.io())
            .observeOn(mainThreadScheduler)
    }

    fun onRemove(i: Int): MutableList<Record> = listRecordsRepo.onRemove(i)

    fun saveListRecords() {
        Observable.just(listRecordsRepo.saveListRecords())
            .subscribeOn(Schedulers.io())
            .observeOn(mainThreadScheduler)
    }

    fun addRecord(): MutableList<Record> = listRecordsRepo.addRecord()

}
