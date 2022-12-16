package com.kejotech.batterysaverapp.ui.usageStats;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class GalleryViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public GalleryViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is App Usage info");
    }

    public LiveData<String> getText() {
        return mText;
    }
}