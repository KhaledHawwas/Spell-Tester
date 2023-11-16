package com.example.spelltester

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import com.example.spelltester.data.db.AppDatabase
import com.example.spelltester.data.db.word.Word
import com.example.spelltester.data.repositories.AppRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    val TAG = "MAINACT"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.d(TAG, "onCreate: before build")
        AppDatabase(this)
        AppRepository(AppDatabase.getInstance())
        Log.d(TAG, "onCreate: after build")




    }
}