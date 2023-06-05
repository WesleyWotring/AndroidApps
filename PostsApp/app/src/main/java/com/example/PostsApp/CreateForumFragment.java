package com.example.PostsApp;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;

/**
 * HW05
 * Group F22
 * Wesley Wotring and Kyle Prindle
 */
public class CreateForumFragment extends Fragment {
    private static final String ARG_USER_TOKEN = "ARG_USER_TOKEN";
    private FirebaseAuth mAuth;
    FirebaseUser user;
    User currentUser = new User();

    View view;
    EditText title, description;
    String titleString, descriptionString;
    Button cancel, submit;
    String user_idCheck;



    public CreateForumFragment() {
        // Required empty public constructor
    }


    public CreateForumFragment newInstance() {
        CreateForumFragment fragment = new CreateForumFragment();
//        Bundle args = new Bundle();
//        args.putSerializable(ARG_USER_TOKEN, (Serializable) user);
//        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            user = (FirebaseUser) getArguments().getSerializable(ARG_USER_TOKEN);
//        }
        getActivity().setTitle("Create Forum");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_create_forum, container, false);

        title = view.findViewById(R.id.editTextTitle);
        description = view.findViewById(R.id.editTextDescription);
        cancel = view.findViewById(R.id.buttonCancelForum);
        submit = view.findViewById(R.id.buttonSubmitForum);
        cancel.setText("Cancel");





        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                titleString = title.getText().toString();
                descriptionString = description.getText().toString();
                if(titleString.isEmpty() || descriptionString.isEmpty()){
                    Toast.makeText(getActivity(), R.string.fields, Toast.LENGTH_SHORT).show();
                }else{
                    //post the forum
                    //ArrayList<User> userList = new ArrayList<>();
//                    FirebaseFirestore db = FirebaseFirestore.getInstance();

                    mAuth = FirebaseAuth.getInstance();
                    user = mAuth.getCurrentUser();
//                    user_idCheck = mAuth.getCurrentUser().getUid();
//
//                    for(int i = 0; i <= userList.size(); i++){
//                        if (user_idCheck.equals(userList.get(i).getU_id())){
//                            currentUser = userList.get(i);
//                        }
//                    }
//                    String userName = currentUser.getName();

                    FirebaseFirestore db2 = FirebaseFirestore.getInstance();

                    Forum forum = new Forum();
                    //i think we have to get name from the users collection tried above
                    forum.setName(user.getDisplayName());
                    forum.setTitle(titleString);
                    forum.setDescription(descriptionString);
                    forum.setUser_id(mAuth.getCurrentUser().getUid());
                    forum.setDate(new Date());
                   // forum.setLikeMap(new HashMap());


                    db2.collection("forums")
                            .add(forum)
                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    mListener.createForum();
                                }
                            });

                }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.cancelForum();
            }
        });

        return view;
    }


    CreateForumFragment.CreateForumFragmentListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (CreateForumFragment.CreateForumFragmentListener) context;
    }

    interface CreateForumFragmentListener {
        void cancelForum();
        void createForum();
    }


}