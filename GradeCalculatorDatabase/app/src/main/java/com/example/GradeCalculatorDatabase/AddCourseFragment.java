package com.example.GradeCalculatorDatabase;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.room.Room;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddCourseFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddCourseFragment extends Fragment {
    View view;
    EditText name, number, hours;
    int hoursNum;
    String nameText, numberText, hoursText;
    RadioGroup radioGroup;
    RadioButton A,B,C,D,F;
    char grade = 'A';
    Button submit, cancel;

//    // TODO: Rename parameter arguments, choose names that match
//    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";
//
//    // TODO: Rename and change types of parameters
//    private String mParam1;
//    private String mParam2;

    public AddCourseFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddCourseFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddCourseFragment newInstance(String param1, String param2) {
        AddCourseFragment fragment = new AddCourseFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
        return fragment;
    }
//     MENU SHITTTTTT
//    public void onCreateOptionsMenu (Menu menu, MenuInflater inflater){
//        setHasOptionsMenu(true);
//
//    }
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.game_menu, menu);
//        return true;
//    }
//
//    private MenuInflater getMenuInflater() {
//
//    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
        getActivity().setTitle("Add Course");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_add_course, container, false);
        number = view.findViewById(R.id.editTextNumber);
        name = view.findViewById(R.id.editTextCourseName);
        hours = view.findViewById(R.id.editTextHours);

        submit = view.findViewById(R.id.buttonSubmit);
        cancel = view.findViewById(R.id.buttonCancel);

        radioGroup = view.findViewById(R.id.radioGroup);
        A = view.findViewById(R.id.radioButtonA);
        B = view.findViewById(R.id.radioButtonB);
        C = view.findViewById(R.id.radioButtonC);
        D = view.findViewById(R.id.radioButtonD);
        F = view.findViewById(R.id.radioButtonF);


        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.cancel();
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(radioGroup.getCheckedRadioButtonId() == A.getId()){
                    grade = 'A';

                }else if(radioGroup.getCheckedRadioButtonId() == B.getId()){
                    grade = 'B';

                } else if(radioGroup.getCheckedRadioButtonId() == C.getId()){
                    grade = 'C';

                } else if(radioGroup.getCheckedRadioButtonId() == D.getId()){
                    grade = 'D';

                }else {
                    grade = 'F';
                }

                hoursText = hours.getText().toString();
                nameText = name.getText().toString();
                numberText = number.getText().toString();

                try{
                    hoursNum = Integer.parseInt(hoursText);
                }catch(Exception parse){
                    parse.printStackTrace();
                }

                if(hoursText.isEmpty() || nameText.isEmpty() || numberText.isEmpty() || hoursNum < 0){
                    Toast.makeText(getContext(), "Fill all fields", Toast.LENGTH_SHORT);
                }else{
                    // add the course to the database here

                    Course course = new Course(numberText, nameText, hoursNum, grade);

                    AppDatabase db = Room.databaseBuilder(getActivity(), AppDatabase.class, "course.db")
                            .allowMainThreadQueries()
                            .allowMainThreadQueries()
                            .build();

                    db.courseDAO().insertAll(course);

                    mListener.submit();
                }
            }
        });








        return view;
    }


    AddCourseFragmentListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof AddCourseFragmentListener) {
            mListener = (AddCourseFragmentListener) context;
        } else {
            //"Must implement CitiesList Interface"
            throw new RuntimeException("Not Working ");
        }

    }


    interface AddCourseFragmentListener {
        void cancel();
        void submit();
    }
}