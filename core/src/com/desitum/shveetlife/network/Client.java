package com.desitum.shveetlife.network;

import java.io.DataInputStream;
import java.net.Socket;
/**
 * Created by Zmyth97 on 4/3/2015.
 */
public class Client
{
    private static Socket socket;
    private static DataInputStream in;

    public static void main(String[] args) throws Exception
    {
        System.out.println("Connecting...");
        socket = new Socket("localhost", 7777);
        System.out.println("Connection Successful");
        in = new DataInputStream(socket.getInputStream());
        System.out.println("Receiving Information...");
        String test = in.readUTF();
        System.out.println("Message From Server: " + handleInfo(test));
    }

    public static String handleInfo(String info){
        String myInfo = "";
        myInfo = info + "added stuff";
        return myInfo;
    }
}
