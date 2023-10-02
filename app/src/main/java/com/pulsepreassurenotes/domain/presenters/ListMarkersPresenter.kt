package com.pulsepreassurenotes.domain.presenters


import com.google.android.gms.maps.model.MarkerOptions
import com.pulsepreassurenotes.domain.repository.IMapFragmentRepo
import com.pulsepreassurenotes.domain.repository.MapFragmentRepo
import com.pulsepreassurenotes.domain.view_MVP.MapFragmentView
import com.pulsepreassurenotes.model.Record
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.core.Single
import moxy.MvpPresenter

class ListMarkersPresenter : MvpPresenter<MapFragmentView>() {


    private val mainThreadScheduler: Scheduler = AndroidSchedulers.mainThread()

    private var listMarkersRepo: IMapFragmentRepo = MapFragmentRepo()


    public override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.init()

    }

    lateinit var callMenuRepo: Single<MutableList<Record>>
    fun loadMarkers() {
        callMenuRepo = listMarkersRepo.getMarkers() ?: Single.just(mutableListOf())
        loadMarkersJavaRx()
    }


    fun loadMarkersJavaRx() {
        callMenuRepo
            .observeOn(mainThreadScheduler)
            .subscribe({ markers ->
                if (!markers.isNullOrEmpty()) {
                    viewState.loadMarkers(markers)
                } else {
                    viewState.loadMarkers(mutableListOf())
                }
            }, {
                viewState.loadMarkers(mutableListOf())
            })
    }

    fun onCorrectionClick(i: Int, marker: Record) {
        listMarkersRepo.onCorrectionClick(i, marker)
    }

    fun onRemove(i: Int): MutableList<Record> = listMarkersRepo.onRemove(i)

    fun saveListMarkers() {
        callMenuRepo
            .observeOn(mainThreadScheduler)
            .subscribe({ markers ->
                listMarkersRepo.saveListMarkers()
            }, {

            })

      //  listMarkersRepo.saveListMarkers()
    }

    fun addNote(): MutableList<Record> = listMarkersRepo.addMarkerOnMap()

}
