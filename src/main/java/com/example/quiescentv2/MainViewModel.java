package com.example.quiescentv2;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MainViewModel extends ViewModel {
    private static long countDownTime;


// the timer will start automatically if you pass true as the third argument.

    public static MutableLiveData<Boolean> exerciseOver = new MutableLiveData<>();

    public static MutableLiveData<Long> currentTime = new MutableLiveData<>();

    public static MutableLiveData<Long> startTime = new MutableLiveData<>();

    // Defaults for @{@link SettingsUtils}
    public static final int DEFAULT_PRESET_INDEX = 0;
    public static final int DEFAULT_DURATION = 4000;

    // Value used to convert between animation
    // duration and seekbar unity
    public static final int MILLISECOND = 2000;

    public static void init () {
        exerciseOver.setValue(false);
    }

    public static void setStartTime(long millisUntilFinished) {
        startTime.setValue(millisUntilFinished);
        resetCountDownTime();
    }

    public static MutableLiveData<Long> getStartTime() {
        return startTime;
    }

    public static void setCountDownTime(long millisUntilFinished) {
        countDownTime = millisUntilFinished;
    }

    public static long getCountDownTime() {
        return countDownTime;
    }

    public static void resetCountDownTime () {
        if (startTime.getValue() != null) {
            countDownTime = startTime.getValue();
        }
    }

    public static MutableLiveData<Boolean> getExerciseOver() {
        return exerciseOver;
    }

    public static MutableLiveData<Long> getCurrentTime() {
        return currentTime;
    }
}