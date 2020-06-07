package com.pratheek.thoughts

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ThoughtViewModel(application: Application) : AndroidViewModel(application) {

    private var repository: ThoughtRepository

    var thoughtList: LiveData<List<ThoughtEntity>>

    init {
        val thoughtDAO = ThoughtDataBase.getDatabase(application, viewModelScope).thoughtDao()
        repository = ThoughtRepository(thoughtDAO)
        thoughtList = repository.allThought
    }

    fun insert(thoughtEntity: ThoughtEntity) = viewModelScope.launch(Dispatchers.IO) {
        repository.insertThought(thoughtEntity)
    }

}
