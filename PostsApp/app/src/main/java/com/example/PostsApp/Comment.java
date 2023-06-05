package com.example.PostsApp;

import com.google.firebase.firestore.DocumentId;

import java.util.Date;
/**
 * HW05
 * Group F22
 * Wesley Wotring and Kyle Prindle
 */
public class Comment {
    String name, comment, user_id;

    @DocumentId
    String doc_id;
    Date date;

    public Comment(String name, String comment, String user_id, String doc_id, Date date) {
        this.name = name;
        this.comment = comment;
        this.user_id = user_id;
        this.date = date;
        this.doc_id = doc_id;
    }

    public Comment() {
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
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
}
