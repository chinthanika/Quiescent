package com.example.quiescentv2;

import android.content.Context;

import androidx.annotation.NonNull;


public class SettingsUtils {

    public static int getBackgroundByPresetPosition(int position) {
        Preset preset = Preset.values()[position];
        return preset.getResId();
    }

    public static void saveSelectedPreset(int presetIndex) {
        BreathePreferences.getInstance().putInt(BreathePreferences.SELECTED_PRESET_KEY, presetIndex);
    }

    public static int getSelectedPreset() {
        int preset = BreathePreferences.getInstance().getInt(BreathePreferences.SELECTED_PRESET_KEY);
        return preset != -1 ? preset : MainViewModel.DEFAULT_PRESET_INDEX;
    }

    public static void saveSelectedInhaleDuration(int duration) {
        BreathePreferences.getInstance().putInt(BreathePreferences.SELECTED_INHALE_DURATION_KEY, duration);
    }

    public static int getSelectedInhaleDuration() {
        int duration = BreathePreferences.getInstance().getInt(BreathePreferences.SELECTED_INHALE_DURATION_KEY);
        return duration != -1 ? duration : MainViewModel.DEFAULT_DURATION;
    }

    public static void saveSelectedExhaleDuration(int duration) {
        BreathePreferences.getInstance().putInt(BreathePreferences.SELECTED_EXHALE_DURATION_KEY, duration);
    }

    public static int getSelectedExhaleDuration() {
        int duration = BreathePreferences.getInstance().getInt(BreathePreferences.SELECTED_EXHALE_DURATION_KEY);
        return duration != -1 ? duration : MainViewModel.DEFAULT_DURATION;
    }

    public static void saveSelectedHoldDuration(int duration) {
        BreathePreferences.getInstance().putInt(BreathePreferences.SELECTED_HOLD_DURATION_KEY, duration);
    }

    public static int getSelectedHoldDuration() {
        int duration = BreathePreferences.getInstance().getInt(BreathePreferences.SELECTED_HOLD_DURATION_KEY);
        return duration != -1 ? duration : MainViewModel.DEFAULT_DURATION;
    }
}
