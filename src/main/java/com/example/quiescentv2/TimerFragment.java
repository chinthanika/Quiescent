package com.example.quiescentv2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TimerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TimerFragment extends Fragment {

    private long newTime;

    private EditText hourInput;
    private EditText minuteInput;


    public TimerFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static TimerFragment newInstance() { return new TimerFragment(); }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new ViewModelProvider(this).get(MainViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_timer, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        hourInput = view.findViewById(R.id.hours_input);
        minuteInput = view.findViewById(R.id.minutes_input);
        Button setButton = view.findViewById(R.id.set_button);

        setButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 String hourString = hourInput.getText().toString();
                if (hourString.length() == 0) {
                    Toast.makeText(requireActivity(), "Hour field cannot be empty.", Toast.LENGTH_SHORT).show();
                    return;
                }

                String minuteString = minuteInput.getText().toString();
                if (minuteString.length() == 0) {
                    Toast.makeText(requireActivity(), "Minute field cannot be empty.", Toast.LENGTH_SHORT).show();
                    return;
                }

                Long hours = Long.parseLong(hourString) * 360000;
                Long minutes  = Long.parseLong(minuteString) * 60000;

                newTime = hours + minutes;
                if (newTime == 0) {
                    Toast.makeText(requireActivity(), "Please enter positive values", Toast.LENGTH_SHORT).show();
                    return;
                }

                MainViewModel.setStartTime(newTime);
                hourInput.setText("");
                minuteInput.setText("");
                Toast.makeText(requireActivity(), "Timer set.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}