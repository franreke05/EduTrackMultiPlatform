package com.franciscor.edutrackmultiplatform.platform

import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.initialize

actual fun platformInitializeFirebase() {
    Firebase.initialize()
}
