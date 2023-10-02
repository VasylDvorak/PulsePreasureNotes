package com.pulsepreassurenotes.delegates

import android.content.ContentValues
import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.GenericTypeIndicator
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.pulsepreassurenotes.model.Record
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

const val REFERENCE = "recoder"

class FireBaseDatabaseDelegate(private var loadDataFirebaseCallback: (MutableList<Record>) -> Unit) :
    ReadWriteProperty<Any?, MutableList<Record>?> {

    private val database = Firebase.database
    private val myRef = database.getReference(REFERENCE)

    init {
        myRef.keepSynced(true)
    }

    override fun getValue(thisRef: Any?, property: KProperty<*>): MutableList<Record> {
        val output = mutableListOf<Record>()
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val listDataType = object : GenericTypeIndicator<List<Record>>() {}
                val listData = snapshot.getValue(listDataType) ?: mutableListOf()
                loadDataFirebaseCallback(listData.toMutableList())
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w(
                    ContentValues.TAG, "ErrorFireBase",
                    error.toException()
                )
            }

        })
        return output
    }

    override fun setValue(
        thisRef: Any?,
        property: KProperty<*>,
        value: MutableList<Record>?
    ) {
        if (!value.isNullOrEmpty()) {
            myRef.setValue(value.toList())
        }
    }
}
