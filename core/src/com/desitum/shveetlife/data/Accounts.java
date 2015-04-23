package com.desitum.shveetlife.data;

import com.desitum.shveetlife.network.Client;
import com.desitum.shveetlife.network.Server;

import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * Created by Zmyth97 on 4/7/2015.
 */
public class Accounts {

    public String wantedUsername;
    public String wantedPassword;
    public String wantedIpAddress;

    public boolean isValid;
    public boolean tryAgain;

    private ArrayList<String> accounts;
    private ArrayList<String> passwords;

    public Accounts() {
        isValid = false;
        tryAgain = false;

        accounts = new ArrayList<String>();
        passwords = new ArrayList<String>();
        accounts.add("Zmyth97");
        passwords.add("Pass");
        accounts.add("Kody");
        passwords.add("Pass");
    }

    public void checkExisting(String username, String password, String ipAddress){

        if(accounts.contains(username)){
            verifyPassword(password, username, ipAddress);
        } else {
            makeAccountCheck(username, ipAddress);
            if(!tryAgain) {
                promptNewAccount(username, ipAddress);
            }
        }
    }

    private void verifyPassword(String password, String username, String ipAddress){
        int userID = accounts.indexOf(username);
        if(passwords.get(userID).equals(password)){
                wantedIpAddress = ipAddress;
                wantedPassword = password;
                wantedUsername = username;
                isValid = true;
        } else {
            JOptionPane.showMessageDialog(null, "Wrong password!");
        }
    }

    private void makeAccountCheck(String username, String ipAddress){
        int result = JOptionPane.showConfirmDialog(null, "That Username doesn't exist, would you like to make a new account?", "Make New Account", JOptionPane.YES_NO_OPTION);
        if(result == JOptionPane.YES_OPTION){
            promptNewAccount(username, ipAddress);
        } else {
            tryAgain = true;
        }
    }
    private void promptNewAccount(String username, String ipAddress){
        JTextField userField = new JTextField(username);
        JTextField passField = new JTextField(20);

        JPanel myPanel = new JPanel(new GridLayout(10, 10));
        myPanel.add(new JLabel("New Username:"));
        myPanel.add(userField);
        myPanel.add(new JLabel("New Password:"));
        myPanel.add(passField);

        int result = JOptionPane.showConfirmDialog(null, myPanel, "Make an Account", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            if(!accounts.contains(userField.getText())) {
                if (userField.getText().length() > 0 && passField.getText().length() > 0) {
                    accounts.add(userField.getText());
                    passwords.add(passField.getText());
                    wantedPassword = passField.getText();
                    wantedUsername = userField.getText();
                    wantedIpAddress = ipAddress;
                    isValid = true;
                } else {
                    JOptionPane.showMessageDialog(null, "You must fill out both fields!");
                    promptNewAccount(username, ipAddress);
                }
            } else {
                JOptionPane.showMessageDialog(null, "That username is taken");
                promptNewAccount(username, ipAddress);
            }
        }

    }
}
