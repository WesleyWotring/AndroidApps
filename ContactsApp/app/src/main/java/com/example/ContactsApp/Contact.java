package com.example.ContactsApp;

/**
 * InClass07
 * Group_F22_Inclass07
 * Wesley Wotring and Kyle Prindle
 */

public class Contact {
    String Name, Email, Phone, PhoneType;
    int Cid;

    public Contact(int Cid, String name, String email, String phone, String type) {
        this.Cid = Cid;
        this.Name = name;
        this.Email = email;
        this.Phone = phone;
        this.PhoneType = type;
    }

    public Contact() {
    }

    public String toString(){
        return "name:" + Name + "email" + Email + "phone" + Phone + "phone type" + PhoneType + "ID" + Cid;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        this.Name = name;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        this.Email = email;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        this.Phone = phone;
    }

    public String getPhoneType() {
        return PhoneType;
    }

    public void setPhoneType(String phoneType) {
        this.PhoneType = phoneType;
    }

    public int getCid() {
        return Cid;
    }

    public void setCid(int cid) {
        Cid = cid;
    }
}
