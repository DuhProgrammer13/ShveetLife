package com.desitum.shveetlife.network;

import java.net.ServerSocket;
import java.net.Socket;
import java.io.DataOutputStream;
/**
 * Created by Zmyth97 on 4/5/2015.
 */
public class Server
{
    private static ServerSocket serverSocket;
    private static Socket clientSocket;
    private static DataOutputStream out;

    public static void main(String [] args) throws Exception
    {
        System.out.println("Starting Server....");
        serverSocket = new ServerSocket(7777);
        System.out.println("Server Started.");
        clientSocket = serverSocket.accept();
        System.out.println("Connection From: " + clientSocket.getInetAddress());
        out = new DataOutputStream(clientSocket.getOutputStream());
        out.writeUTF(modifyInfo("This is my server message!"));
        System.out.println("Data has been sent");
    }

    private static String modifyInfo(String Info){
        String myInfo = "";
        myInfo = "Beginning Fluff" + Info;
        return myInfo;

    }
}
