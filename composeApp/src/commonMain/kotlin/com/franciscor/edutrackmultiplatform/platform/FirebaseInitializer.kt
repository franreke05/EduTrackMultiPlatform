package com.franciscor.edutrackmultiplatform.platform

private var firebaseInitialized = false

fun ensureFirebaseInitialized() {
    if (firebaseInitialized) return
    platformInitializeFirebase()
    firebaseInitialized = true
}

expect fun platformInitializeFirebase()
