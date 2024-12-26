package com.yohana.echolearn

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import kotlin.random.Random

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = "user_data"
)

class EchoLearnApplication: Application(){
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer(dataStore)
    }
}