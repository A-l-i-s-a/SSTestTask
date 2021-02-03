package com.ivlieva.sstesttask.util

import android.net.Uri
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

lateinit var AUTH: FirebaseAuth
lateinit var REF_DATABASE_ROOT: DatabaseReference
lateinit var REF_STORAGE_ROOT: StorageReference
lateinit var CURRENT_UID: String

const val FOLDER_FILES = "task_files"
const val NODE_TASKS = "task"
const val CHILD_DATE_FINISH = "date_finish"
const val CHILD_DATE_START = "date_start"
const val CHILD_DESCRIPTION = "description"
const val CHILD_NAME = "name"
const val CHILD_FILE_URL = "file_url"

fun initFirebase() {
    AUTH = FirebaseAuth.getInstance()
    CURRENT_UID = AUTH.currentUser?.uid.toString()
    REF_DATABASE_ROOT = FirebaseDatabase.getInstance().reference
    REF_STORAGE_ROOT = FirebaseStorage.getInstance().reference
}

fun saveFile(uri: Uri): String {
    val path = REF_STORAGE_ROOT.child(FOLDER_FILES).child(uri.lastPathSegment.toString())
    path.putFile(uri)
        .addOnFailureListener { exception ->  showToast(exception.message.toString()) }
    return path.path
}