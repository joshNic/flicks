package com.example.flicks.movieDetails

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.flicks.models.Result

class MovieDetailViewModel(resultProperty: Result, app: Application) : AndroidViewModel(app){
    private val _selectedProperty = MutableLiveData<Result>()
    val selectedProperty: LiveData<Result>
    get() = _selectedProperty

    init {
        _selectedProperty.value = resultProperty
    }

}