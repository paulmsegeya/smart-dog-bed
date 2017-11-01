package com.xavirigau.smartdogbed

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import io.reactivex.Observable
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*


class FirebaseResultReadingService(
        private val database: FirebaseDatabase,
        private val clock: Clock = SystemClock(),
        private val dateFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)
) : ResultReadingService {

    override fun read(): Observable<Sleeping> {
        return Observable.create { emitter ->
            val date = dateFormat.format(clock.currentTimeInMillis().toDate())
            val dateData = database.getReference("state").child(date)
            dateData.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onCancelled(databaseError: DatabaseError) {
                    emitter.onError(RuntimeException("Error reading from Firebase", databaseError.toException()))
                }

                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    dateData.removeEventListener(this)
                    val results = dataSnapshot.children.map { it.key to it.value }
                    val totalSleptInMillis = results.foldIndexed(0L, { index, acc, pair ->
                        if (index < results.size - 1 && pair.second == Busy.name()) {
                            val currentTimestamp = pair.first.toLong()
                            val nextTimestamp = results[index + 1].first.toLong()
                            return@foldIndexed (nextTimestamp - currentTimestamp) + acc
                        }
                        return@foldIndexed acc
                    })
                    emitter.onNext(Sleeping(totalSleptInMillis))
                    emitter.onComplete()
                }
            })
        }
    }
}

