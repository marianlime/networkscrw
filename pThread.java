package com.company;
import java.net.*;
import java.io.*;

public class parkingThread extends Thread {

    private Socket actionSocket = null;
    private parkingState mySharedActionStateObject;
    private String myActionServerThreadName;
    private double mySharedVariable;


    public parkingThread(Socket actionSocket, String ActionServerThreadName, parkingState SharedObject){
        this.actionSocket = actionSocket;
        mySharedActionStateObject = SharedObject;
        myActionServerThreadName = ActionServerThreadName;
    }

    public void run(){
        try{
            System.out.println(myActionServerThreadName + "initialising");
            PrintWriter out = new PrintWriter(actionSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(actionSocket.getInputStream()));
            String inputLine, outputLine;

            while((inputLine = in.readLine()) != null){
                try{
                    mySharedActionStateObject.acquireLock();
                    outputLine = mySharedActionStateObject.processInput(myActionServerThreadName, inputLine);
                    out.print(outputLine);
                    mySharedActionStateObject.releaseLock();
                }
                catch(InterruptedException e){
                    System.err.println("Failed to get lock when reading" + e);
                }
            }

            out.close();
            in.close();
            actionSocket.close();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

}
