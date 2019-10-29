package com.example.todolist.extension

import com.google.android.gms.tasks.Task
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import java.util.concurrent.CancellationException
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

suspend fun <T> Task<T>.await(): T = suspendCoroutine { continuation ->
    addOnCanceledListener { continuation.resumeWithException(CancellationException()) }
    addOnSuccessListener { continuation.resume(it) }
}


suspend fun DatabaseReference.await(): DataSnapshot =

    suspendCoroutine { continuation ->
        addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                continuation.resumeWithException(CancellationException())
            }

            override fun onDataChange(p0: DataSnapshot) {
                continuation.resume(p0)
            }
        })
    }