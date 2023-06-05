package com.example.ContactsApp;

import java.util.ArrayList;
/**
 * InClass07
 * Group_F22_Inclass07
 * Wesley Wotring and Kyle Prindle
 */

public class ContactResponse {
    String status;
    ArrayList<Contact> contacts;

    @Override
    public String toString() {
        return "ContactResponse{" +
                "status='" + status + '\'' +
                '}';
    }
}
