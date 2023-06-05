package com.example.UserAccounts;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * In Class 04
 * Group_B1_InClass04
 * Wesley Wotring and Zachary Hall
 */
public class UpdateAccountFragment extends Fragment {


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";

    // TODO: Rename and change types of parameters
    public DataServices.Account account;
    TextView emailView;


    public UpdateAccountFragment() {
        // Required empty public constructor
    }


    public static UpdateAccountFragment newInstance(DataServices.Account account) {
        UpdateAccountFragment fragment = new UpdateAccountFragment();
        fragment.account = account;
        //Bundle args = new Bundle();
        //args.putString(ARG_PARAM1, param1);
        //fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            //mParam1 = getArguments().getString(ARG_PARAM1);
        }
    }
    public UpdateAccountFragment.IUpdateFragmentListener listenerUpdateAcc;
    public void onAttach(@NonNull Context context){
        super.onAttach(context);
        if(context instanceof AccountFragment.IAccountFragmentListener){
            listenerUpdateAcc = (UpdateAccountFragment.IUpdateFragmentListener) context;

        } else {
            throw new RuntimeException("Error");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_update_account, container, false);
        emailView = view.findViewById(R.id.textViewUserName);
        emailView.setText(account.getEmail());

        view.findViewById(R.id.buttonUpdateSubmit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText nameText = view.findViewById(R.id.editTextUserName);
                EditText passwordText = view.findViewById(R.id.editTextPasswordUpdate);
//this throws an error
                String pass = passwordText.getText().toString();
                String nameName = nameText.getText().toString();



                if (pass == "" || nameName == "") {
                    Toast.makeText(v.getContext(), R.string.errorEmpty, Toast.LENGTH_SHORT).show();

                } else {

                    DataServices.AccountRequestTask task = DataServices.update(account,nameName, pass);
                    if (task.isSuccessful()) {//successful
                        DataServices.Account account = task.getAccount();
                        listenerUpdateAcc.submitEdit(account);

                    } else { //not successful
                        String error = task.getErrorMessage();
                        Toast.makeText(v.getContext(), error, Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        view.findViewById(R.id.buttonCancelUpdate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listenerUpdateAcc.cancelUpdate();
            }
        });

        return view;
    }
    public interface IUpdateFragmentListener{
        void submitEdit(DataServices.Account account);
        void cancelUpdate();

    }
}