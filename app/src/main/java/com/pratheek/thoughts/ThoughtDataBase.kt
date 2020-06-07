package com.pratheek.thoughts

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [ThoughtEntity::class], version = 1)
abstract class ThoughtDataBase : RoomDatabase() {

    abstract fun thoughtDao(): ThoughtDAO

    companion object {
        @Volatile
        private var INSTANCE: ThoughtDataBase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): ThoughtDataBase {
            return INSTANCE ?: synchronized(this) {
                val instance =
                    Room.databaseBuilder(context, ThoughtDataBase::class.java, "article_database")
                        .addCallback(ArticleDataBaseCallback(scope))
                        .build()
                INSTANCE = instance
                instance
            }
        }

        private class ArticleDataBaseCallback(private val scope: CoroutineScope) :
            RoomDatabase.Callback() {
            override fun onOpen(db: SupportSQLiteDatabase) {
                super.onOpen(db)
                INSTANCE.let { database ->
                    scope.launch(Dispatchers.IO) {
                        populateDatabase(database!!.thoughtDao())
                    }
                }
            }
        }

        private fun populateDatabase(thoughtDAO: ThoughtDAO) {

            thoughtDAO.insertThought(ThoughtEntity("Creativity is Intelligence having Fun\n Great Things are done by a series  of small things brought together",R.font.aldrich, R.color.colorOrange, "#FFFFFF"))
        }
    }
}