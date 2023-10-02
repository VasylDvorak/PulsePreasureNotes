package com.pulsepreassurenotes.domain.repository


import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.pulsepreassurenotes.model.Record
import io.reactivex.rxjava3.core.Single

interface IMapFragmentRepo {
    fun getMarkers(): Single<MutableList<Record>>
    fun addMarkerOnMap(): MutableList<Record>
    fun saveListMarkers()
    fun onCorrectionClick(i: Int, marker: Record)
    fun onRemove(i: Int): MutableList<Record>
}
