package com.pulsepreassurenotes.delegates

import android.content.Context
import android.preference.PreferenceManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.pulsepreassurenotes.model.Record
import org.koin.mp.KoinPlatform
import java.lang.reflect.Type
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class SharedPreferencesDelegate(private val name: String) :
    ReadWriteProperty<Any?, MutableList<Record>?> {
    private val appSharedPrefs =
        PreferenceManager.getDefaultSharedPreferences(
            KoinPlatform.getKoin().get<Context>().applicationContext
        )
    private val gson by lazy { Gson() }

    override fun getValue(thisRef: Any?, property: KProperty<*>): MutableList<Record> {
        val type: Type = object : TypeToken<MutableList<Record?>?>() {}.type
        val gsonString = appSharedPrefs.getString(name, "[]")
        return gson.fromJson(gsonString, type)
    }

    override fun setValue(
        thisRef: Any?,
        property: KProperty<*>,
        value: MutableList<Record>?
    ) {
        val prefsEditor = appSharedPrefs.edit()
        val jsonStr = gson.toJson(value)
        prefsEditor.putString(name, jsonStr)
        prefsEditor.apply()
    }
}