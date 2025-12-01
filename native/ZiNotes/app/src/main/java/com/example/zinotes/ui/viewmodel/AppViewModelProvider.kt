package com.example.zinotes.ui.viewmodel

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.zinotes.ZiNotesApplication

object AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            ListViewModel(ziNotesApplication().repository)
        }
        initializer {
            DetailViewModel(ziNotesApplication().repository)
        }
        initializer {
            AddViewModel(ziNotesApplication().repository)
        }
        initializer {
            EditViewModel(ziNotesApplication().repository)
        }
    }
}

fun CreationExtras.ziNotesApplication(): ZiNotesApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as ZiNotesApplication)