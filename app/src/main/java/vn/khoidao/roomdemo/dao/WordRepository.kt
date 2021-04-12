package vn.khoidao.roomdemo.dao

import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow
import vn.khoidao.roomdemo.model.Word

class WordRepository(private val wordDao: IWordDao) {

    val allWords: Flow<List<Word>> = wordDao.getAlphabetizedWords()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(word: Word){
        wordDao.insertWord(word)
    }
}