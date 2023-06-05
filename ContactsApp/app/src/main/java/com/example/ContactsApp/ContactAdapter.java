package com.example.ContactsApp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

/**
 * InClass07
 * Group_F22_Inclass07
 * Wesley Wotring and Kyle Prindle
 */

public class ContactAdapter extends ArrayAdapter<Contact> {

    public ContactAdapter(@NonNull Context context, int resource, @NonNull List<Contact> objects) {
        super(context, resource, objects);
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.contact_layout, parent, false);
        }
        Contact contact = getItem(position);

        TextView name = convertView.findViewById(R.id.textViewName);
        TextView email = convertView.findViewById(R.id.textViewEmail);
        TextView phone = convertView.findViewById(R.id.textViewPhone);
        TextView ptype = convertView.findViewById(R.id.textViewType);
        TextView id = convertView.findViewById(R.id.textViewID);




        name.setText(contact.getName());
        email.setText(contact.getEmail());
        phone.setText(contact.getPhone());
        ptype.setText(contact.getPhoneType());
        id.setText(String.valueOf(contact.getCid()));




        return convertView;
    }
}
