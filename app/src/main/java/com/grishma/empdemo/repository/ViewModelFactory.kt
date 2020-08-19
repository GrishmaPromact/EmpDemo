package com.grishma.empdemo.repository

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.grishma.empdemo.viewmodel.MainViewModel

/**
 * View model factory
 */
class ViewModelFactory constructor(private val repository: MainRepository): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(MainViewModel::class.java!!)) {
            MainViewModel(this.repository) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}