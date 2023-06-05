package com.example.GradeCalculatorDatabase;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.inclass09.R;

public class MainActivity extends AppCompatActivity implements GradesFragment.GradesFragmentListener, AddCourseFragment.AddCourseFragmentListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.rootView, new GradesFragment())
                .commit();

    }


    @Override
    public void cancel() {
        getSupportFragmentManager().popBackStack();

    }

    @Override
    public void submit() {
        getSupportFragmentManager().popBackStack();

    }

    @Override
    public void goToAddCourse() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView, new AddCourseFragment())
                .addToBackStack(null)
                .commit();

    }
}