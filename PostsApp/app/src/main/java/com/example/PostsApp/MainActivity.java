package com.example.PostsApp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;

/**
 * HW05
 * Group F22
 * Wesley Wotring and Kyle Prindle
 */
public class MainActivity extends AppCompatActivity implements LoginFragment.LoginFragmentListener, RegisterFragment.RegisterFragmentListener, ForumsFragment.ForumsFragmentListener, CreateForumFragment.CreateForumFragmentListener, CommentForumFragment.CommentForumFragmentListener {
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        if(mAuth.getCurrentUser() == null){
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.rootView, new LoginFragment())
                    .commit();

        }else{
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.rootView, new ForumsFragment())
                    .commit();

        }



    }


    @Override
    public void createAccount() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView, new RegisterFragment())
                .commit();

    }

    @Override
    public void logout() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView, new LoginFragment())
                .commit();
    }

    @Override
    public void goToCreateForum() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView, new CreateForumFragment())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void goToForum(String docID) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView, CommentForumFragment.newInstance(docID))
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void loginSuccessfulGotoForums() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView, new ForumsFragment())
                .commit();
    }

    @Override
    public void cancelRegister() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView, new LoginFragment())
                .commit();
        ;
    }

    @Override
    public void createSuccessfulGotoForums() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView, new ForumsFragment())
                .commit();

    }

    @Override
    public void cancelForum() {
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void createForum() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView, new ForumsFragment())
                .commit();

    }

    @Override
    public void createComment() {
        //
    }
}