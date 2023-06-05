package com.example.ContactsApp;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * InClass07
 * Group_F22_Inclass07
 * Wesley Wotring and Kyle Prindle
 */

public class ContactListFragment extends Fragment {
    final static String TAG = "DEMO";
    ArrayList<Contact> contactList = new ArrayList<>();
    View view;
    ListView listView;
    ArrayAdapter<Contact> contactAdapter;
    private final OkHttpClient client = new OkHttpClient();


    public ContactListFragment() {
        // Required empty public constructor
    }


    public static ContactListFragment newInstance() {
        ContactListFragment fragment = new ContactListFragment();


        return fragment;
    }

    public ContactListFragment.ContactFragmentListener contactListener;
    public void onAttach(@NonNull Context context){
        super.onAttach(context);
        if(context instanceof ContactListFragment.ContactFragmentListener){
            contactListener = (ContactListFragment.ContactFragmentListener) context;

        } else {
            throw new RuntimeException("Error");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
           // getContacts();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_contact_list, container, false);
        Button add = view.findViewById(R.id.buttonAdd);
        listView = view.findViewById(R.id.userList);
        contactAdapter = new ContactAdapter(getContext(),R.layout.contact_layout, contactList);
        listView.setAdapter(contactAdapter);
        contactAdapter.notifyDataSetChanged();



        Request request = new Request.Builder()
                .url("https://www.theappsdr.com/contacts/json")
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
            if(response.isSuccessful()){
                Gson gson = new Gson();
                final ContactResponse contactResponse = gson.fromJson(response.body().charStream(), ContactResponse.class);
                contactList.addAll(contactResponse.contacts);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        contactAdapter.notifyDataSetChanged();
                    }
                });
            }
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Contact selectedContact = new Contact();
                selectedContact = contactList.get(position);

                contactListener.sendContact(selectedContact);

            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Contact selectedContactDelete = new Contact();
                selectedContactDelete = contactList.get(position);
                contactList.remove(selectedContactDelete);

                Request request2 = new Request.Builder()
                        .url("https://www.theappsdr.com/contact/json/delete")
                        .build();

            contactAdapter.notifyDataSetChanged();
                return true;
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contactListener.gotoAddContact(contactList);

            }
        });


        return view;
    }
/**
    void getContacts(){
        Request request = new Request.Builder()
                .url("https://www.theappsdr.com/contacts/json")
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if(response.isSuccessful()) {
                    Gson gson = new Gson();
                    ContactResponse contactResponse = gson.fromJson(response.body().charStream(), ContactResponse.class);

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                        }
                    });

                    ResponseBody responseBody = response.body();
                    String body = responseBody.string();
                    //String[] items = body.split("\n");
                    Log.d("DEMO", "onResponse: " + body);


                    try{

                        JSONObject jsonObject = new JSONObject(response.body().toString());
                        JSONArray jsonArray = new JSONArray("Contacts");

                        for(int i = 0; i < jsonArray.length(); i++){
                            JSONObject contactJSONOBJECT = jsonArray.getJSONObject(i);

                            Contact contact = new Contact();
                            contact.setCid(jsonObject.getInt("Cid"));
                            contact.setName(jsonObject.getString("Name"));
                            contact.setEmail(jsonObject.getString("Email"));
                            contact.setPhone(jsonObject.getString("Phone"));
                            contact.setType(jsonObject.getString("PhoneType"));

                            contactList.add(contact);
                            Log.d(TAG, "List: " + contactList);

                        }

                    }catch(Exception whatever){
                        whatever.printStackTrace();
                    }

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                        }
                    });

                } else {
                    ResponseBody responseBody = response.body();
                    String body = responseBody.string();
                    Log.d(TAG, "onResponse: " + body);
                }
            }
        });
    }
**/
    public interface ContactFragmentListener{
        void sendContact(Contact selectedContact);
        void gotoAddContact(ArrayList<Contact> contactList);

    }
}