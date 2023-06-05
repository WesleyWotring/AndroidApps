package com.example.PostsApp;


/**
 * HW05
 * Group F22
 * Wesley Wotring and Kyle Prindle
 */
public class User {
    String u_id, name, email, password;

    public User(String u_id, String name, String email, String password) {
        this.u_id = u_id;
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public User() {
    }

    public String getU_id() {
        return u_id;
    }

    public void setU_id(String u_id) {
        this.u_id = u_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
