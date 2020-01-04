package com.example.android.roomwordssample;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class WordViewModel extends AndroidViewModel {
    private WordRepository repository;
    private LiveData<List<Word>> allWords;

    public WordViewModel(Application application) {
        super(application);
        repository = new WordRepository(application);
        allWords = repository.getAllWords();
    }
    LiveData<List<Word>> getAllWords(){return allWords;}
    public void insert (Word word){ repository.insert(word);}
    public void deleteAll(){repository.deleteAll();}
    public void deleteWord(Word word){repository.deleteWord(word);}
}
