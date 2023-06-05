package com.example.GradeCalculatorDatabase;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GradesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GradesFragment extends Fragment {
    View view;
    TextView gpa, totalHours;
    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    GradesAdapter adapter;
    double gpaFinal = 0.0, gpaTemp,gpaValue, hoursTemp = 0.0;
    ArrayList<Course> courseArrayList = new ArrayList<>();

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";
//
//    // TODO: Rename and change types of parameters
//    private String mParam1;
//    private String mParam2;

    public GradesFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static GradesFragment newInstance( /**String param1, String param2 **/) {
        GradesFragment fragment = new GradesFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu, menu);
    }


    public boolean onOptionsItemSelected(MenuItem item) {
        mListener.goToAddCourse();
        return false;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_grades, container, false);

        totalHours = view.findViewById(R.id.tvHours);
        gpa = view.findViewById(R.id.tvGPA);

        recyclerView = view.findViewById(R.id.courseRV);

        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        //JUST FOR YOU CARLOS
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                layoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);


        adapter = new GradesAdapter(courseArrayList);
        recyclerView.setAdapter(adapter);

        data();





        return view;
    }

    private void data(){
        AppDatabase db = Room.databaseBuilder(getContext(), AppDatabase.class, "course.db")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();

        Log.d("demo", "Database list " + db.courseDAO().getAll());

        courseArrayList.clear();
        hoursTemp = 0;
        gpaFinal = 0;
        gpaTemp = 0;
        gpaValue = 0;

        for (int i =0; i < db.courseDAO().getAll().size(); i++){
            //getting the course data
            Course newCourse = db.courseDAO().getAll().get(i);
            courseArrayList.add(newCourse);

            //manipulate the GPA and total hours
            hoursTemp += newCourse.getCourseHours();

            //get the letter and set the gpaValue
            if(newCourse.getCourseGrade() == 'A'){
                gpaValue = newCourse.getCourseHours() * 4.0;
            }else if(newCourse.getCourseGrade() == 'B'){
                gpaValue = newCourse.getCourseHours() * 3.0;
            }else if(newCourse.getCourseGrade() == 'C'){
                gpaValue = newCourse.getCourseHours() * 2.0;
            }else if(newCourse.getCourseGrade() == 'D'){
                gpaValue =  newCourse.getCourseHours() * 1.0;
            }else if(newCourse.getCourseGrade() == 'F'){
                gpaValue = newCourse.getCourseHours() * 0.0;
            }

            gpaTemp += gpaValue;

        }

        if(hoursTemp == 0.0){
            gpaFinal = 4.0;
        }

        gpaFinal = gpaTemp / hoursTemp;
        gpa.setText(String.valueOf(gpaFinal));
        totalHours.setText(String.valueOf(hoursTemp));

    }

    private void delete(Course course, AppDatabase db){
        db.courseDAO().delete(course);
        adapter.notifyDataSetChanged();
        //db.notifyAll();
    }


    GradesFragmentListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof GradesFragmentListener) {
            mListener = (GradesFragmentListener) context;
        } else {
            //"Must implement CitiesList Interface"
            throw new RuntimeException("Not Working ");
        }

    }


    interface GradesFragmentListener {
        void goToAddCourse();
    }




    public class GradesAdapter extends RecyclerView.Adapter<GradesFragment.GradesAdapter.GradesViewHolder> {
        ArrayList<Course> courseList;



        public GradesAdapter(ArrayList<Course> courseList) {
            this.courseList = courseList;
        }

        @NonNull
        @Override
        public GradesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.course_layout, parent, false);
            GradesViewHolder gradesViewHolder = new GradesViewHolder(view);

            return gradesViewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull GradesViewHolder holder, int position) {

            Course course = courseList.get(position);

            holder.name.setText(course.getCourseName());
            holder.number.setText(course.getCourseNum());
            holder.hours.setText(String.valueOf(course.getCourseHours()));
            holder.grade.setText(String.valueOf(course.getCourseGrade()));
            holder.delete.setVisibility(View.VISIBLE);


            holder.delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    AppDatabase db = Room.databaseBuilder(getContext(), AppDatabase.class, "course.db")
                            .allowMainThreadQueries()
                            .fallbackToDestructiveMigration()
                            .build();


                    delete(course,db);
                }
            });


        }

        @Override
        public int getItemCount() {
            return this.courseList.size();
        }

        public class GradesViewHolder extends RecyclerView.ViewHolder {
            ImageView delete;
            TextView name, hours, grade, number;


            public GradesViewHolder(@NonNull View itemView) {
                super(itemView);
                name = itemView.findViewById(R.id.textViewCourseName);
                grade = itemView.findViewById(R.id.textViewLetterGrade);
                hours = itemView.findViewById(R.id.textViewCourseHours);
                number = itemView.findViewById(R.id.textViewCourseNum);


                delete = itemView.findViewById(R.id.imageViewDelete);


            }
        }

    }
}