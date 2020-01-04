package com.example.android.roomwordssample;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Word.class}, version = 1, exportSchema = false)
public abstract class WordRoomDatabase extends RoomDatabase {
    public abstract WordDao wordDao();
    private static WordRoomDatabase INSTANCE;
    public static WordRoomDatabase getDatabase(final Context context){
        if(INSTANCE == null){
            synchronized (WordRoomDatabase.class) {
                if(INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                             WordRoomDatabase.class, "word_database")
                            .fallbackToDestructiveMigration()
                            .addCallback(RoomDataBaseCallBack)
                            .build();
                }
            }
        }
        return INSTANCE;
    }
    private static RoomDatabase.Callback RoomDataBaseCallBack = new RoomDatabase.Callback(){
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
            new PopulateDbAsync(INSTANCE).execute();
        }
    };
    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void>{
        private final WordDao dao;
        String[] words = {"dolphin", "Whale", "Crocodile"};
        private PopulateDbAsync(WordRoomDatabase db){
            dao = db.wordDao();
        }
        @Override
        protected Void doInBackground(Void... voids) {
            if(dao.getAnyWord().length<1){
            for(int i = 0 ; i <= words.length - 1; i++ ){
                Word word = new Word(words[i]);
                dao.insert(word);
            }
            }
            return null;
        }
    }
}
