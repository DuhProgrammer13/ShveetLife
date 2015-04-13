package com.desitum.shveetlife.data;

import com.desitum.shveetlife.network.Client;
import com.desitum.shveetlife.network.Server;
import com.desitum.shveetlife.screens.MenuScreen;

import java.util.ArrayList;

import javax.swing.JOptionPane;

/**
 * Created by Zmyth97 on 4/7/2015.
 */
public class Accounts {

    private String username;
    private String password;
    private String ipAddress;

    public boolean isValid;
    private Server myServer;

    private ArrayList<String> accounts;
    private ArrayList<String> passwords;

    public Accounts(String username, String password, String ipAddress) {
        this.username = username;
        this.password = password;
        this.ipAddress = ipAddress;

        isValid = false;

        myServer = new Server();

        accounts = new ArrayList<String>();
        passwords = new ArrayList<String>();

        checkExisting(username, password, ipAddress);
    }

    private void checkExisting(String username, String password, String ipAddress){
        accounts.add("Zmyth97");
        passwords.add("Pass");
        accounts.add("Kody");
        passwords.add("Pass");

        if(accounts.contains(username)){
            verifyPassword(password, username, ipAddress);
        } else {
            promptNewAccount(username);
        }
    }

    public void runServer(){
        myServer.RunServer("localhost");
    }
    private void verifyPassword(String password, String username, String ipAddress){
        int userID = accounts.indexOf(username);
        if(passwords.indexOf(password) == userID){
            myServer.RunServer(ipAddress);
            isValid = true;
        } else {
            JOptionPane.showMessageDialog(null, "Wrong password!");
            //TODO Tell the user their password/username is incorrect
        }
    }

    private void promptNewAccount(String username){
        JOptionPane.showMessageDialog(null, "Username doesn't exist!");
        //TODO Something user interface with prompting to make a new account password using the username given?
        //Make sure to add the new password and username to the ArrayList of Passwords so they match up with new usernames.
        //If you can think of a better way than this go for it, but this is the first to pop into my head!
    }
}
