package com.pratheek.thoughts

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData

class ThoughtRepository(private var thoughtDAO: ThoughtDAO) {

    val allThought: LiveData<List<ThoughtEntity>> = thoughtDAO.getListOfThought()

    @WorkerThread
    suspend fun insertThought(thoughtEntity: ThoughtEntity) {
       thoughtDAO.insertThought(thoughtEntity)
    }

}