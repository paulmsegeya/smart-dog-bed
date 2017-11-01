package com.xavirigau.smartdogbed

import android.content.res.Resources
import android.view.View
import android.widget.TextView
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class MainDisplayer(
        rootView: View,
        private val dateFormat: DateFormat = SimpleDateFormat("H 'hours and' m 'minutes'", Locale.US)
) {

    private val text : TextView = rootView.findViewById(R.id.sleeping_text)
    private val resources : Resources = rootView.resources

    fun display(result: Sleeping) {
        text.text = resources.getString(R.string.your_dog_has_slept, dateFormat.format(result.totalSleptInMillis.toDate()))
    }

}
