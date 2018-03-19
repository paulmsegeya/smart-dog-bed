package com.xavirigau.smartdogbed

import com.google.firebase.database.FirebaseDatabase
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Locale

class FirebaseResultStoringService(
        private val database: FirebaseDatabase,
        private val clock: Clock = SystemClock(),
        private val dateFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)
) : ResultStoringService {

    override fun store(result: Result) {
        val date = dateFormat.format(clock.currentTimeInMillis().toDate())
        val timestamp = clock.currentTimeInMillis().toString()
        database.getReference("state").child(date).child(timestamp).setValue(result.name())
    }

}
