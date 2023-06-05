package com.example.ContactsApp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * InClass07
 * Group_F22_Inclass07
 * Wesley Wotring and Kyle Prindle
 */

public class EditContactFragment extends Fragment {
    View view;
    Contact selectedContact;
    EditText nameEdit, emailEdit, phoneEdit, typeEdit, idEdit;
    TextView nameText, emailText, phoneText, typeText, idText;
    Button update, cancel2;
    String nameEditString, emailEditString, idEditString, phoneEditString, typeEditString;



    public EditContactFragment() {
        // Required empty public constructor
    }



    public static EditContactFragment newInstance(Contact selectedContact) {
        EditContactFragment fragment = new EditContactFragment();
       fragment.selectedContact = selectedContact;

        return fragment;
    }

    public EditContactFragment.EditContactFragmentListener editListener;
    public void onAttach(@NonNull Context context){
        super.onAttach(context);
        if(context instanceof EditContactFragment.EditContactFragmentListener){
            editListener = (EditContactFragment.EditContactFragmentListener) context;

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
        view = inflater.inflate(R.layout.fragment_edit_contact, container, false);

        nameEdit = view.findViewById(R.id.editTextEditName);
        emailEdit= view.findViewById(R.id.editTextEditEmail);
        phoneEdit= view.findViewById(R.id.editTextEditPhone);
        typeEdit= view.findViewById(R.id.EditTextEditType);
        idEdit = view.findViewById(R.id.editTextEditId);

        nameText = view.findViewById(R.id.textViewNameEdit);
        emailText= view.findViewById(R.id.textViewEmailEdit);
        phoneText= view.findViewById(R.id.textViewNumberEdit);
        typeText= view.findViewById(R.id.textViewTypeEdit);
        idText = view.findViewById(R.id.textViewIDEdit);

        nameText.setText(selectedContact.getName());
        emailText.setText(selectedContact.getEmail());
        phoneText.setText(selectedContact.getPhone());
        typeText.setText(selectedContact.getPhoneType());
        idText.setText(String.valueOf(selectedContact.getCid()));

        cancel2 = view.findViewById(R.id.buttonCancelEdit);
        update = view.findViewById(R.id.buttonEditUpdate);

        cancel2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editListener.cancelUpdate();
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                nameEditString = nameText.getText().toString();
                emailEditString = emailText.getText().toString();
                phoneEditString = phoneText.getText().toString();
                typeEditString = typeText.getText().toString();
                idEditString = idText.getText().toString();

                if(nameEditString.isEmpty() || emailEditString.isEmpty() || phoneEditString.isEmpty() || typeEditString.isEmpty()  || idEditString.isEmpty() ){
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            AlertDialog.Builder alertBuilder = new AlertDialog.Builder(getContext());
                            alertBuilder.setTitle("Error")
                                    .setMessage("Fill all feilds")
                                    .setPositiveButton("OKAY", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                        }
                                    });
                            alertBuilder.create().show();
                        }
                    });

                }else{
                    /**
                    ID = Integer.parseInt(idString);
                    Contact newContact = new Contact(ID,nameString, emailString, phoneString, typeString);
                    contactList.add(newContact);
                    createContact(ID,nameString, emailString, phoneString, typeString);

                     **/
                    editListener.editContact();

                }
            }
        });






        return view;
    }

    public interface EditContactFragmentListener{
        void editContact();
        void cancelUpdate();

    }
}