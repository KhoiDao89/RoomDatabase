package vn.khoidao.roomdemo.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import vn.khoidao.roomdemo.dao.IWordDao
import vn.khoidao.roomdemo.model.Word

@Database(entities = arrayOf(Word::class), version = 1, exportSchema = false)
public abstract class WordRoomDatabase: RoomDatabase() {

    abstract fun wordDao(): IWordDao

    private class WordDatabaseCallback(private val scope: CoroutineScope)
        : RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database -> scope.launch { populateDatabase(database.wordDao()) }}
        }

        suspend fun populateDatabase(wordDao: IWordDao){
            wordDao.deleteAll()
            var word = Word("Khoi")
            wordDao.insertWord(word)
            word = Word("Dao")
            wordDao.insertWord(word)
        }
    }

    companion object{
        @Volatile
        private var INSTANCE: WordRoomDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): WordRoomDatabase{
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(context.applicationContext,
                                                    WordRoomDatabase::class.java,
                                            "word_database")
                                                    .addCallback(WordDatabaseCallback(scope))
                                                     .build()
                INSTANCE = instance
                instance
            }
        }
    }



}