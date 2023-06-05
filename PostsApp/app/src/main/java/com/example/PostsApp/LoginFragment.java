package com.example.PostsApp;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * HW05
 * Group F22
 * Wesley Wotring and Kyle Prindle
 */
public class LoginFragment extends Fragment {
    Button login, createAccount;
    EditText email, password;
    String emailString, passString;
    View view;
    private FirebaseAuth mAuth;

    public LoginFragment() {
        // Required empty public constructor
    }


    public static LoginFragment newInstance() {
        LoginFragment fragment = new LoginFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle("Login");

//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_login, container, false);
        createAccount = view.findViewById(R.id.buttonCreateNewAccount);
        login = view.findViewById(R.id.buttonSubmit);

        email = view.findViewById(R.id.editTextEmail);
        password = view.findViewById(R.id.editTextPassword);

        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.createAccount();
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                emailString = email.getText().toString();
                passString = password.getText().toString();

                if(emailString.isEmpty() || passString.isEmpty()){
                    Toast.makeText(getActivity(), R.string.fields, Toast.LENGTH_SHORT).show();
                }
                else{
                    mAuth = FirebaseAuth.getInstance();
                    mAuth.signInWithEmailAndPassword(emailString, passString)
                            .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()){
                                        FirebaseUser user = mAuth.getCurrentUser();
                                        Log.d("app", "Logged in: " + mAuth.getUid());
                                        mListener.loginSuccessfulGotoForums();

                                    }else{
                                        Toast.makeText(getActivity(), R.string.error + " " + task.getException(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });


        return view;
    }

    LoginFragmentListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (LoginFragmentListener) context;
    }


    interface LoginFragmentListener {
        void createAccount();
        void loginSuccessfulGotoForums();
    }

}