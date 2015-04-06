package com.desitum.shveetlife.network;

/**
 * Created by Zmyth97 on 4/5/2015.
 */
public class ProcessData {

    public ProcessData(){

    }

    public void process(int commandId, int objectID){
      String command = "";
      String commands[] = {"ADD", "REMOVE", "PLACE", "EDIT"};
      command = commands[commandId];
      handleObjects(command, objectID);
    }

    private void handleObjects(String command, int objectID){
        //TODO For kody! :D
        //You have the command (above method) and this just needs to handle the objects based on ID,
        //Not quite sure what you wanted to do with this or how you wanted to do it......
    }
}
