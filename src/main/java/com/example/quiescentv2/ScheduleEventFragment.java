package com.example.quiescentv2;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.EditText;
import android.widget.TimePicker;


public class ScheduleEventFragment extends Fragment {

    EditText name;
    EditText pass;
    EditText updateOld;
    EditText updateNew;
    EditText delete;
//    DbAdapter helper;

    public ScheduleEventFragment() {
        // Required empty public constructor
    }

    public static ScheduleEventFragment newInstance() { return new ScheduleEventFragment(); }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        name = requireActivity().findViewById(R.id.editName);
        pass = requireActivity().findViewById(R.id.editPass);
        updateOld = requireActivity().findViewById(R.id.editText3);
        updateNew = requireActivity().findViewById(R.id.editText5);
        delete = requireActivity().findViewById(R.id.editText6);



//        timePicker = (TimePicker) requireActivity().findViewById(R.id.time_picker);
//
//        hours = timePicker.getCurrentHour();
//        minutes = timePicker.getCurrentMinute();
//
//        MainViewModel.setStartTime(hours, minutes);

//        helper = new DbAdapter(requireContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_schedule_event, container, false);
    }

//    public void addUser(View view)
//    {
//        String nameString = name.getText().toString();
//        String passString = pass.getText().toString();
//        if(nameString.isEmpty() || passString.isEmpty())
//        {
//            Message.message(requireContext(),"Enter Both Name and Password");
//        }
//        else
//        {
//            long id = helper.insertData(nameString,passString);
//            if(id<=0)
//            {
//                Message.message(requireContext(),"Insertion Unsuccessful");
//                name.setText("");
//                pass.setText("");
//            } else
//            {
//                Message.message(requireContext(),"Insertion Successful");
//                name.setText("");
//                pass.setText("");
//            }
//        }
//    }
}