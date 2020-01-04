package com.example.android.roomwordssample;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class WordRepository {
    private WordDao mWordDao;
    private LiveData<List<Word>> mAllWords;

    WordRepository(Application application) {
        WordRoomDatabase db = WordRoomDatabase.getDatabase(application);
        mWordDao = db.wordDao();
        mAllWords = mWordDao.getAllWords();
    }

    LiveData<List<Word>> getAllWords() {
        return mAllWords;
    }

    public void insert(Word word) {
        new insertAsyncTask(mWordDao).execute(word);
    }
    public void deleteAll(){new deleteAllWordsAsyncTask(mWordDao).execute();}
    public void deleteWord(Word word){new deleteWordAsyncTask(mWordDao).execute(word);}
    private static class insertAsyncTask extends AsyncTask<Word, Void, Void> {
        private WordDao mAsyncTaskDao;

        insertAsyncTask(WordDao Dao) {
            mAsyncTaskDao = Dao;
        }


        @Override
        protected Void doInBackground(final Word... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }
    private static class deleteAllWordsAsyncTask extends AsyncTask<Void, Void, Void>{
        private WordDao asyncTaskDao;
        deleteAllWordsAsyncTask(WordDao dao){
            asyncTaskDao = dao;
        }
        @Override
        protected Void doInBackground(Void... voids) {
            asyncTaskDao.deleteAll();
            return null;
        }
    }
    private static class deleteWordAsyncTask extends AsyncTask<Word, Void, Void>{
        private WordDao asyncTaskDao;
        deleteWordAsyncTask(WordDao dao){
            asyncTaskDao = dao;
        }


        @Override
        protected Void doInBackground(final Word... params) {
            asyncTaskDao.deletWord(params[0]);
            return null;
        }
    }
}
