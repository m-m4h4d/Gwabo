package com.example.gwabo.ui.groceries;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class GroceriesViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public GroceriesViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("");
    }

    public LiveData<String> getText() {
        return mText;
    }
}