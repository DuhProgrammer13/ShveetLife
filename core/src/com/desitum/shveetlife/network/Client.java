package com.desitum.shveetlife.network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

import javax.swing.JOptionPane;

/**
 * Created by Zmyth97 on 4/3/2015.
 */
public class Client {
    private static Socket socket;
    private static DataInputStream in;
    private static DataOutputStream out;


    public Client () {
    }

    public String startClient(String wantedIP) {
        try {
            socket = new Socket(wantedIP, 9001);
            in = new DataInputStream(socket.getInputStream());
            String data = in.readUTF();
            return data;
        } catch (Exception exception) {
            JOptionPane.showMessageDialog(null, "Problems with the Client");
        }
        return "";
    }

    public String readData(){
        String data = "";
        try {
            if(socket != null && in.available() > 0) {
                in = new DataInputStream(socket.getInputStream());
                data = in.readUTF();
            } else {

            }
        } catch(Exception exception){
        }
        return data;
    }

    public void sendData(String command) {
        try {
            if(socket != null) {
                out = new DataOutputStream(socket.getOutputStream());
                out.writeUTF(command);
                out.flush();
            } else {
                //If the server is null you have bigger problems than an empty else section... :P
            }
        } catch (Exception exception) {

        }
    }

    public void disconnect(){
        try {
            socket.shutdownInput();
            socket.shutdownOutput();
            socket.close();
            socket = null;
            in = null;
            out = null;
        } catch(Exception exception){
            JOptionPane.showMessageDialog(null, "It had troubles closing the client socket");
        }
    }
}
