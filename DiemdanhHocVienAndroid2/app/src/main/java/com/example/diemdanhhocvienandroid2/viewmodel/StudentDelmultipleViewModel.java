package com.example.diemdanhhocvienandroid2.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AttendanceStudentViewModel extends ViewModel {
    MutableLiveData<String> mutableLiveData = new MutableLiveData<>();

    public void setText(String s){
        this.mutableLiveData.setValue(s);
    }
    public MutableLiveData<String> getText(){
        return this.mutableLiveData;
    }
}
