package com.example.madassignment.delivery

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel

class DeliveryViewModel(application: Application) : AndroidViewModel(application){

    init {
        Log.i("DeliveryViewModel", "DeliveryViewModel created!")
    }

    override fun onCleared() {
        super.onCleared()
        Log.i("DeliveryViewModel", "DeliveryViewModel destroyed!")
    }
}