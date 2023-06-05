package com.example.ContactsApp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.util.ArrayList;
/**
 * InClass07
 * Group_F22_Inclass07
 * Wesley Wotring and Kyle Prindle
 */

public class MainActivity extends AppCompatActivity implements ContactListFragment.ContactFragmentListener, AddContactFragment.AddContactFragmentListener, ContactDetailsFragment.ContactDetailsFragmentListener, EditContactFragment.EditContactFragmentListener  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerView, ContactListFragment.newInstance(), "start")
                .commit();


    }


    @Override
    public void sendContact(Contact selectedContact) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerView, ContactDetailsFragment.newInstance(selectedContact), "contactSend")
                .addToBackStack(null)
                .commit();

    }

    @Override
    public void gotoAddContact(ArrayList<Contact> contactList) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerView, AddContactFragment.newInstance(contactList), "contactSend")
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void addContact(ArrayList<Contact> contactList) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerView, ContactListFragment.newInstance(), "contactSend")
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void cancel() {
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void deleteContact() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerView, ContactListFragment.newInstance(), "contactDelete")
                .commit();
    }

    @Override
    public void updateContact(Contact selectedContact) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerView, EditContactFragment.newInstance(selectedContact), "contactUpdate")
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void editContact() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerView, ContactListFragment.newInstance(), "editContact")
                .commit();
    }

    @Override
    public void cancelUpdate() {
        getSupportFragmentManager().popBackStack();
    }
}
