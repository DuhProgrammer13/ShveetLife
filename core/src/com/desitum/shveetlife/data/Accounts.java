package com.desitum.shveetlife.data;

import java.util.ArrayList;

/**
 * Created by Zmyth97 on 4/7/2015.
 */
public class Accounts {

    private String username;
    private String password;

    private ArrayList<Accounts> accounts;

    public Accounts(String username, String password){
        this.username = username;
        this.password = password;

        accounts = new ArrayList<Accounts>();

         checkExisting(username, password);
    }

    private void checkExisting(String username, String password){
        if(accounts.contains(username)){
            verifyPassword(password);
        } else {
            promptNewAccount(username);
        }
    }

    private void verifyPassword(String password){

    }

    private void promptNewAccount(String username){
        //TODO Something user interface with prompting to make a new account password using the username given?
    }
}
