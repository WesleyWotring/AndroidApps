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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

/**
 * HW05
 * Group F22
 * Wesley Wotring and Kyle Prindle
 */
public class RegisterFragment extends Fragment {
    View view;
    EditText name, email, pass;
    String nameString, emailString, passString;
    Button cancel, submit;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;



    public RegisterFragment() {
        // Required empty public constructor
    }


    public static RegisterFragment newInstance() {
        RegisterFragment fragment = new RegisterFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle("Register");
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_register, container, false);
        submit = view.findViewById(R.id.buttonSubmit);
        cancel = view.findViewById(R.id.buttonCancelRegister);
        email = view.findViewById(R.id.editTextEmail);
        pass = view.findViewById(R.id.editTextPassword);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                passString = pass.getText().toString();
                emailString = email.getText().toString();

                if(passString.isEmpty() || emailString.isEmpty()){
                    Toast.makeText(getActivity(), R.string.fields, Toast.LENGTH_SHORT).show();
                }else{
                    mAuth = FirebaseAuth.getInstance();
                    mAuth.createUserWithEmailAndPassword(emailString,passString)
                    .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                           if (task.isSuccessful()){
                           FirebaseUser user = mAuth.getCurrentUser();

                           //setting user to user class object
                           User userAdd = new User();
                           userAdd.setEmail(emailString);
                           userAdd.setPassword(passString);
                           userAdd.setU_id(mAuth.getCurrentUser().getUid());

                           //push user to the collection
                           db = FirebaseFirestore.getInstance();
                           db.collection("users")
                                .add(userAdd)
                                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                     @Override
                                     public void onSuccess(DocumentReference documentReference) {
                                         //go to forums
                                        mListener.createSuccessfulGotoForums();
                                     }
                                });
                           }else{
                                Toast.makeText(getActivity(), R.string.error + " " + task.getException(), Toast.LENGTH_SHORT).show();
                           }
                    }
                    });
                }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.cancelRegister();
            }
        });

        return view;
    }



    RegisterFragmentListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (RegisterFragmentListener) context;
    }

    interface RegisterFragmentListener {
        void cancelRegister();
        void createSuccessfulGotoForums();
    }

}