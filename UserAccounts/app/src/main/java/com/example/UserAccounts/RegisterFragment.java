package com.example.UserAccounts;

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
/**
 * In Class 04
 * Group_B1_InClass04
 * Wesley Wotring and Zachary Hall
 */
public class RegisterFragment extends Fragment {
    String nameString;
    String emailString;
    String passwordString;
    EditText nameRegister;
    EditText emailRegister;
    EditText passwordRegister;
    Button subButton;
    View view;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";

    // TODO: Rename and change types of parameters
    public DataServices.Account account;

    public RegisterFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static RegisterFragment newInstance() {
        RegisterFragment fragment = new RegisterFragment();

       // Bundle args = new Bundle();
        //args.putString(ARG_PARAM1, param1);
        //args.putString(ARG_PARAM2, param2);
        //fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
           // mParam1 = getArguments().getString(ARG_PARAM1);
        }
    }
    public IRegisterFragmentListener listenerRegister;
    public void onAttach(@NonNull Context context){
        super.onAttach(context);
        if(context instanceof IRegisterFragmentListener){
            listenerRegister = (IRegisterFragmentListener) context;

        } else {
            throw new RuntimeException("Error");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_register, container, false);

        subButton = view.findViewById(R.id.buttonSubmit);
        nameRegister = view.findViewById(R.id.editTextName);
        emailRegister = view.findViewById(R.id.editTextRegisterEmail);
        passwordRegister = view.findViewById(R.id.editTextRegisterPassword);




        subButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nameString = nameRegister.getText().toString();
                emailString = emailRegister.getText().toString();
                passwordString = passwordRegister.getText().toString();

                if(nameString =="") {
                    Toast.makeText(v.getContext(), R.string.errorEmpty, Toast.LENGTH_SHORT).show();

                }else if(emailString =="") {
                    Toast.makeText(v.getContext(), R.string.errorEmpty, Toast.LENGTH_SHORT).show();

                }else if (passwordString == "") {
                    Toast.makeText(v.getContext(), R.string.errorEmpty, Toast.LENGTH_SHORT).show();

                }else {
                    DataServices.AccountRequestTask task = DataServices.register(nameString, emailString, passwordString);
                    if(task.isSuccessful()){
                        DataServices.Account account = task.getAccount();
                        listenerRegister.registerAccount(account);
                    }else{
                        String error = task.getErrorMessage();
                        Toast.makeText(v.getContext(), error, Toast.LENGTH_SHORT).show();
                    }
                }
            }

        });


        Button cancelButton = view.findViewById(R.id.buttonCancel);



        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listenerRegister.cancelRegister();
            }

        });




        return view;
    }
    public interface IRegisterFragmentListener{
        void cancelRegister();
        void registerAccount(DataServices.Account account);

    }

}

