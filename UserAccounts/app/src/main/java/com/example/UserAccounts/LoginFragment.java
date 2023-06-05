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
public class LoginFragment extends Fragment {
    EditText loginEmail;
    EditText loginPassword;
    View view;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    Button loginButton;
    Button newAccountButton;



    public LoginFragment() {
        // Required empty public constructor
    }


    public static LoginFragment newInstance() {
        LoginFragment fragment = new LoginFragment();
        //Bundle args = new Bundle();
        //args.putString(ARG_PARAM1, param1);
        //args.putString(ARG_PARAM2, param2);
        //fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
/*
            if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);

            mParam2 = getArguments().getString(ARG_PARAM2);
            */

        //}
    }

    public void onAttach(@NonNull Context context){
        super.onAttach(context);
        if(context instanceof ILoginFragmentListener){
            listener = (ILoginFragmentListener) context;

        } else {
            throw new RuntimeException("Error");
        }
    }

    public ILoginFragmentListener listener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_login, container, false);

        Button loginButton = view.findViewById(R.id.buttonLogin);



        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                loginEmail = view.findViewById(R.id.editTextEmailLogin);
                loginPassword = view.findViewById(R.id.editTextPasswordLogin);

                String emailLogin = loginEmail.getText().toString();
                String passwordLogin = loginPassword.getText().toString();

                if(emailLogin.equals("")){
                    Toast.makeText(v.getContext(), R.string.errorEmpty, Toast.LENGTH_SHORT).show();

                }else if (passwordLogin == "") {
                    Toast.makeText(v.getContext(), R.string.errorEmpty, Toast.LENGTH_SHORT).show();
                }
                /* else if(!emailLogin.matches("[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,4}\n" +
                        "\n")){
                    Toast.makeText(v.getContext(), R.string.errorEmpty, Toast.LENGTH_SHORT).show();

                } */ else{

                    DataServices.AccountRequestTask task = DataServices.login(emailLogin, passwordLogin);
                    if(task.isSuccessful()){//successful
                        DataServices.Account account = task.getAccount();
                        listener.loginAccount(account);

                    } else { //not successful
                         String error = task.getErrorMessage();
                         Toast.makeText(v.getContext(), error, Toast.LENGTH_SHORT).show();
                    }

                    }

                }

        });

        Button createAccount = view.findViewById(R.id.buttonCreateAccount);

        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.goToRegister();
            }
        });



        return view;
    }
    public interface ILoginFragmentListener{
        void goToRegister();
        void loginAccount(DataServices.Account account);
    }
}