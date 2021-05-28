package com.example.potrafisz.ui.langlearn;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class LanglearnViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public LanglearnViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is slideshow fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}