package com.xavirigau.smartdogbed

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity() {

    private lateinit var presenter: MainPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val displayer = MainDisplayer(findViewById(android.R.id.content))
        val resultReadingService = FirebaseResultReadingService(FirebaseDatabase.getInstance())
        presenter = MainPresenter(displayer, resultReadingService)
    }

    override fun onResume() {
        super.onResume()
        presenter.startPresenting()
    }

    override fun onPause() {
        presenter.stopPresenting()
        super.onPause()
    }

}
