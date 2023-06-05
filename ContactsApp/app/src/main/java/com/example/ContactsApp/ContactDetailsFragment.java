package com.example.ContactsApp;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * InClass07
 * Group_F22_Inclass07
 * Wesley Wotring and Kyle Prindle
 */

public class ContactDetailsFragment extends Fragment {
    View view;
    TextView name,email,phone,type,id;
    Contact selectedContact;
    private final OkHttpClient client = new OkHttpClient();


    // TODO: Rename and change types of parameters

    public ContactDetailsFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static ContactDetailsFragment newInstance(Contact selectedContact) {
        ContactDetailsFragment fragment = new ContactDetailsFragment();
        fragment.selectedContact = selectedContact;

        return fragment;
    }

    public ContactDetailsFragment.ContactDetailsFragmentListener detailListener;
    public void onAttach(@NonNull Context context){
        super.onAttach(context);
        if(context instanceof ContactDetailsFragment.ContactDetailsFragmentListener){
            detailListener = (ContactDetailsFragment.ContactDetailsFragmentListener) context;

        } else {
            throw new RuntimeException("Error");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment



        view = inflater.inflate(R.layout.fragment_contact_details, container, false);

        name = view.findViewById(R.id.textViewName);
        email = view.findViewById(R.id.textViewEmail);
        phone = view.findViewById(R.id.textViewPhone);
        type = view.findViewById(R.id.textViewPhoneType);
        id = view.findViewById(R.id.textViewID);

        name.setText(selectedContact.getName());
        email.setText(selectedContact.getEmail());
        phone.setText(selectedContact.getPhone());
        type.setText(selectedContact.getPhoneType());
        id.setText(String.valueOf(selectedContact.getCid()));

        Button delete = view.findViewById(R.id.buttonDelete);
        Button update = view.findViewById(R.id.buttonUpdateDetails);

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Request req = new Request.Builder()
                        .url("https://www.theappsdr.com/contact/json/delete")
                        .build();

                detailListener.deleteContact();
            }
        });

    update.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            detailListener.updateContact(selectedContact);
        }
    });

        return view;
    }
    public interface ContactDetailsFragmentListener{
        void deleteContact();
        void updateContact(Contact contact);

    }


}