package vn.khoidao.roomdemo

import android.app.Application
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import vn.khoidao.roomdemo.dao.WordRepository
import vn.khoidao.roomdemo.database.WordRoomDatabase

class WordApplication : Application() {

    val applicationScope = CoroutineScope(SupervisorJob())
    val database by lazy { WordRoomDatabase.getDatabase(this, applicationScope) }
    val repository by lazy { WordRepository(database.wordDao()) }
}