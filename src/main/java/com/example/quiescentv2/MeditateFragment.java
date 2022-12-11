package com.example.quiescentv2;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MeditateFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MeditateFragment extends Fragment implements SettingsDialog.SettingsChangeListener {

    public static final long ONE_SECOND = 1000;
    private static final long DEFAULT_START_TIME = 900000;
    public static final long DONE = 0;
    boolean isPaused;

    private CountDownTimer timer;

    private Button pauseResumeButton;
    private Button resetButton;

    public static MutableLiveData<Boolean> exerciseOver = new MutableLiveData<>();

    public static MutableLiveData<Long> currentTime = new MutableLiveData<>();

    private TextView statusText;
    private View outerCircleView, innerCircleView;

    private Animation animationInhaleText, animationExhaleText, animationInhaleInnerCircle, animationExhaleInnerCircle;
    private Animation mFadeOutAnim, mFadeInAnim;

    private AnimatedVectorDrawableCompat mPlayToPauseAnim, mPauseToPlay;

    private int holdDuration = 0;

    // Texts to show inside the breathing circle
    public static final String INHALE = "INHALE";
    public static final String EXHALE = "EXHALE";
    public static final String HOLD = "HOLD";

    private final Handler handler = new Handler();

    public static MeditateFragment newInstance() {
        return new MeditateFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new ViewModelProvider(this).get(MainViewModel.class);

        isPaused = true;
        if (MainViewModel.getCountDownTime() == 0) {
            MainViewModel.setStartTime(DEFAULT_START_TIME);
            Toast.makeText(requireActivity(), "Timer set to default.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_meditate, container, false);

        requireActivity().findViewById(R.id.lt_content);

        statusText = view.findViewById(R.id.txt_status);
        statusText.setText(INHALE);

        outerCircleView = view.findViewById(R.id.view_circle_outer);
        innerCircleView = view.findViewById(R.id.view_circle_inner);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

            currentTime.observe(getViewLifecycleOwner(), currentTime -> {
                TextView timerText =  view.findViewById(R.id.timer_text);
                timerText.setText(DateUtils.formatElapsedTime(currentTime));

                if (currentTime == DONE) {
                    timer.onFinish();
                }
            });

        exerciseOver.observe(getViewLifecycleOwner(), exerciseOver -> {
            if (exerciseOver) {
                TextView timerText =  view.findViewById(R.id.timer_text);

                timerText.setText("DONE!");
            }

        });

        MainViewModel.getStartTime().observe(getViewLifecycleOwner(), startTime -> {
            if (timer !=null) {
                timer.cancel();
                resetSession();
            }
        });

        pauseResumeButton = requireActivity().findViewById(R.id.pause_resume_button);

        pauseResumeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isPaused) {
                    startSession();
                }
                else {
                    pauseSession();
                }
            }
        });

        resetButton = requireActivity().findViewById(R.id.reset_button);

        resetButton.setOnClickListener(view1 -> resetSession());

    }

    private void startSession () {
        timer = new CountDownTimer(MainViewModel.getCountDownTime(), ONE_SECOND) {
            @Override
            public void onTick(long timeLeft) {
                MainViewModel.setCountDownTime(timeLeft);
                updateTimer();
            }

            @Override
            public void onFinish() {
                exerciseOver.setValue(true);
                isPaused = true;

                pauseResumeButton.setText(R.string.start_btn);
                pauseResumeButton.setVisibility(View.VISIBLE);
                resetButton.setVisibility(View.VISIBLE);

                currentTime.setValue(DONE);
                timer.cancel();
            }
        }.start();

        isPaused = false;
        pauseResumeButton.setText(R.string.pause_btn);
        resetButton.setVisibility(View.INVISIBLE);

        //Start the animations
        statusText.setText(INHALE);

        prepareAnimations();
        statusText.startAnimation(animationInhaleText);
        innerCircleView.startAnimation(animationInhaleInnerCircle);
    }

    private void pauseSession () {
        timer.cancel();
        isPaused = true;

        pauseResumeButton.setText(R.string.start_btn);
        resetButton.setVisibility(View.VISIBLE);


    }

    private void resetSession ()  {
        MainViewModel.resetCountDownTime();
        updateTimer();
        Log.i("CountDownTime", String.valueOf(MainViewModel.getCountDownTime()));

        resetButton.setVisibility(View.INVISIBLE);
        pauseResumeButton.setVisibility(View.VISIBLE);

        //Restart the animations
        statusText.setText(INHALE);

        prepareAnimations();
        statusText.startAnimation(animationInhaleText);
        innerCircleView.startAnimation(animationInhaleInnerCircle);
    }

    private void updateTimer (){
        currentTime.setValue(MainViewModel.getCountDownTime() / ONE_SECOND);
    }

    private void setupBackgroundColor() {
        int backgroundResId = SettingsUtils.getBackgroundByPresetPosition(SettingsUtils.getSelectedPreset());
        setOuterCircleBackground(R.color.myrtle_green);
    }

    private void setOuterCircleBackground(int backgroundResId) {
        outerCircleView.setBackgroundResource(backgroundResId);
    }

    private void setInhaleDuration(int duration) {
        animationInhaleText.setDuration(duration);
        animationInhaleInnerCircle.setDuration(duration);
    }

    private void setExhaleDuration(int duration) {
        animationExhaleText.setDuration(duration);
        animationExhaleInnerCircle.setDuration(duration);
    }

    private void prepareAnimations() {
        int inhaleDuration = SettingsUtils.getSelectedInhaleDuration();
        int exhaleDuration = SettingsUtils.getSelectedExhaleDuration();
        holdDuration = SettingsUtils.getSelectedHoldDuration();

        // Inhale - make large
        animationInhaleText = AnimationUtils.loadAnimation(this.getActivity(), R.anim.anim_text_inhale);
        animationInhaleText.setFillAfter(true);
        animationInhaleText.setAnimationListener(inhaleAnimationListener);

        animationInhaleInnerCircle = AnimationUtils.loadAnimation(this.getActivity(), R.anim.anim_inner_circle_inhale);
        animationInhaleInnerCircle.setFillAfter(true);
        animationInhaleInnerCircle.setAnimationListener(inhaleAnimationListener);

        setInhaleDuration(inhaleDuration);

        // Exhale - make small
        animationExhaleText = AnimationUtils.loadAnimation(this.getActivity(), R.anim.anim_text_exhale);
        animationExhaleText.setFillAfter(true);
        animationExhaleText.setAnimationListener(exhaleAnimationListener);

        animationExhaleInnerCircle = AnimationUtils.loadAnimation(this.getActivity(), R.anim.anim_inner_circle_exhale);
        animationExhaleInnerCircle.setFillAfter(true);
        animationExhaleInnerCircle.setAnimationListener(exhaleAnimationListener);

        setExhaleDuration(exhaleDuration);

    }

    private final Animation.AnimationListener inhaleAnimationListener = new Animation.AnimationListener() {
        @Override
        public void onAnimationStart(Animation animation) {

            MainViewModel.exerciseOver.
                    postValue(Boolean.FALSE);
        }

        @Override
        public void onAnimationEnd(Animation animation) {
            //Log.d(TAG, "inhale animation end");
            statusText.setText(HOLD);
            handler.postDelayed(() -> {
                statusText.setText(EXHALE);
                statusText.startAnimation(animationExhaleText);
                innerCircleView.startAnimation(animationExhaleInnerCircle);
            }, holdDuration);
        }

        @Override
        public void onAnimationRepeat(Animation animation) {
        }
    };

    private final Animation.AnimationListener exhaleAnimationListener = new Animation.AnimationListener() {
        @Override
        public void onAnimationStart(Animation animation) {
        }

        @Override
        public void onAnimationEnd(Animation animation) {
            //Log.d(TAG, "exhale animation end");
            statusText.setText(HOLD);
            handler.postDelayed(() -> {
                statusText.setText(INHALE);
                statusText.startAnimation(animationInhaleText);
                innerCircleView.startAnimation(animationInhaleInnerCircle);
            }, holdDuration);
        }

        @Override
        public void onAnimationRepeat(Animation animation) {
        }
    };

    @Override
    public void onPresetChanged(int backgroundResId) {
        setOuterCircleBackground(backgroundResId);
    }

    @Override
    public void onInhaleValueChanged(int duration) {
        setInhaleDuration(duration);
    }

    @Override
    public void onExhaleValueChanged(int duration) {
        setExhaleDuration(duration);
    }

    @Override
    public void onHoldValueChanged(int duration) {
        holdDuration = duration;
    }

}