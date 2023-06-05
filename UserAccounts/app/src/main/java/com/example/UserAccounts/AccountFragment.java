package com.example.UserAccounts;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * In Class 04
 * Group_B1_InClass04
 * Wesley Wotring and Zachary Hall
 */
public class AccountFragment extends Fragment {
    TextView nameView;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";

    // TODO: Rename and change types of parameters
    public DataServices.Account account;

    public AccountFragment() {
        // Required empty public constructor
    }


    public static AccountFragment newInstance(DataServices.Account account) {
        AccountFragment fragment = new AccountFragment();
        fragment.account = account;
       // Bundle args = new Bundle();
      //  args.putSerializable(ARG_PARAM1, account);
        //fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
           // account = getArguments().getSerializable(ARG_PARAM1);
        }
    }

    public IAccountFragmentListener listenerAccount;
    public void onAttach(@NonNull Context context){
        super.onAttach(context);
        if(context instanceof IAccountFragmentListener){
            listenerAccount = (IAccountFragmentListener) context;

        } else {
            throw new RuntimeException("Error");
        }
    }

    public void updateTheAccount(DataServices.Account account) {
        this.account = account;
        nameView.setText(this.account.getName());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_account, container, false);

        Button editProfile = view.findViewById(R.id.buttonEditProfile);
        nameView = view.findViewById(R.id.textViewAccountName);

        String name = account.getName();
        nameView.setText(name);



        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                listenerAccount.editProfile(account);
                }

        });

        Button logOut = view.findViewById(R.id.buttonLogOut);
        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            listenerAccount.logout();
            }

        });




        return view;
    }
    public interface IAccountFragmentListener{
        void editProfile(DataServices.Account account);
        void logout();

    }

}