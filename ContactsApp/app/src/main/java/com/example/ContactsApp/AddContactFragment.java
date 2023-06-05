package com.example.ContactsApp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * InClass07
 * Group_F22_Inclass07
 * Wesley Wotring and Kyle Prindle
 */

public class AddContactFragment extends Fragment {
    View view;
    ArrayList<Contact> contactList;
    EditText nameText,emailText,phoneText,typeText,idText;
    Button cancel, submit;
    String nameString, emailString, phoneString, typeString;
    int ID;
    String idString;
    private final OkHttpClient client = new OkHttpClient();



    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER


    // TODO: Rename and change types of parameters


    public AddContactFragment() {
        // Required empty public constructor
    }


    public static AddContactFragment newInstance(ArrayList<Contact> contactList) {
        AddContactFragment fragment = new AddContactFragment();
        fragment.contactList = contactList;

        return fragment;
    }

    public AddContactFragment.AddContactFragmentListener addListener;
    public void onAttach(@NonNull Context context){
        super.onAttach(context);
        if(context instanceof ContactListFragment.ContactFragmentListener){
            addListener = (AddContactFragment.AddContactFragmentListener) context;

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


        view = inflater.inflate(R.layout.fragment_add_contact, container, false);
        nameText = view.findViewById(R.id.editTextName);
        emailText = view.findViewById(R.id.editTextEmail);
        phoneText = view.findViewById(R.id.editTextPhone);
        typeText = view.findViewById(R.id.editTextType);
        idText = view.findViewById(R.id.editTextID);
        submit = view.findViewById(R.id.buttonSubmit);
        cancel = view.findViewById(R.id.buttonCancel);



        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                nameString = nameText.getText().toString();
                emailString = emailText.getText().toString();
                phoneString = phoneText.getText().toString();
                typeString = typeText.getText().toString();
                idString = idText.getText().toString();

                if(nameString.isEmpty() || emailString.isEmpty() || phoneString.isEmpty() || typeString.isEmpty()  || idString.isEmpty() ){
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

                    ID = Integer.parseInt(idString);
                    Contact newContact = new Contact(ID,nameString, emailString, phoneString, typeString);
                    contactList.add(newContact);
                    createContact(ID,nameString, emailString, phoneString, typeString);

                    addListener.addContact(contactList);
                }

            }
        });




        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addListener.cancel();
            }
        });






        return view;
    }

    public interface AddContactFragmentListener{
        void addContact(ArrayList<Contact> contactList);
        void cancel();

    }
    void createContact(int ID, String name, String email,String phone,String type){

        FormBody formBody = new FormBody.Builder()
                .add("Cid", String.valueOf(ID))
                .add("Name", name)
                .add("Email", email)
                .add("Phone", phone)
                .add("PhoneType", type)
                .build();
        Request request = new Request.Builder()
                .url("https://www.theappsdr.com/contact/json/create")
                .post(formBody)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                ResponseBody responseBody = response.body();
                String body = responseBody.string();
                Log.d("DEMO", "onResponse" + body);

            }
        });


    }

}
