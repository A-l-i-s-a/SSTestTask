package com.ivlieva.sstesttask

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ivlieva.sstesttask.util.*
import dagger.hilt.android.AndroidEntryPoint
import io.realm.Realm
import io.realm.RealmConfiguration

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initFirebase()

        Realm.init(this)
        val configuration = RealmConfiguration.Builder()
            .name("test_task.db")
            .deleteRealmIfMigrationNeeded()
            .schemaVersion(0)
            .build()
        Realm.setDefaultConfiguration(configuration)
    }

    override fun onStart() {
        super.onStart()
        APP_ACTIVITY = this
    }
}