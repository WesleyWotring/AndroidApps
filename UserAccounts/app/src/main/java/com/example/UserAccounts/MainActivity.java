package com.example.UserAccounts;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

/**
 * In Class 04
 * Group_B1_InClass04
 * Wesley Wotring and Zachary Hall
 */

public class MainActivity extends AppCompatActivity implements LoginFragment.ILoginFragmentListener, AccountFragment.IAccountFragmentListener, RegisterFragment.IRegisterFragmentListener, UpdateAccountFragment.IUpdateFragmentListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        getSupportFragmentManager().beginTransaction()
                .add(R.id.containerView, new LoginFragment(), "login")
                .commit();

        Fragment fragment = getSupportFragmentManager().findFragmentByTag("login");


    }

    public void registerAccount(DataServices.Account account){
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerView, AccountFragment.newInstance(account), "account")
                .commit();

    }
    public void cancelRegister(){
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerView, new LoginFragment(), "login")
                .commit();

    }

    @Override
    public void goToRegister() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerView, RegisterFragment.newInstance(), "register")
                .commit();
    }

    public void loginAccount(DataServices.Account account){
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerView, AccountFragment.newInstance(account), "account")
                .commit();
    }
    public void logout(){
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerView, new LoginFragment(), "login")
                .commit();

    }
    public void editProfile(DataServices.Account account){
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerView, UpdateAccountFragment.newInstance(account), "update")
                .addToBackStack(null)
                .commit();

    }

    @Override
    public void submitEdit(DataServices.Account account) {

        getSupportFragmentManager().popBackStack();

        //Popping from backstack


        //Updating the existing account fragment with the newly created account
        AccountFragment instance = (AccountFragment)getSupportFragmentManager().findFragmentByTag("account");
        instance.updateTheAccount(account);
    }

    public void cancelUpdate(){
        getSupportFragmentManager().popBackStack();
    }
}