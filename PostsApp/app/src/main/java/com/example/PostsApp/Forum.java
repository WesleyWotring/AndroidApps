package com.example.PostsApp;

import com.google.firebase.firestore.DocumentId;

import java.util.Date;
import java.util.HashMap;

/**
 * HW05
 * Group F22
 * Wesley Wotring and Kyle Prindle
 */
public class Forum {
    String name, title, description, user_id;
    HashMap<String, Boolean> likes = new HashMap<String, Boolean>();

    @DocumentId
    String doc_id;
    Date date;



    public Forum(String name, String title, String description, String user_id,String doc_id, Date date, HashMap likeMap) {
        this.name = name;
        this.title = title;
        this.description = description;
        this.date = date;
        this.user_id = user_id;
        this.doc_id = doc_id;
        this.likes = likeMap;

    }

    public Forum() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getDoc_id() {
        return doc_id;
    }

    public void setDoc_id(String doc_id) {
        this.doc_id = doc_id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public HashMap<String, Boolean> getLikes() {
        return likes;
    }

    public void setLikes(HashMap<String, Boolean> likes) {
        this.likes = likes;
    }
}
