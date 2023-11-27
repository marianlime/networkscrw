package com.company;
import java.net.*;
import java.io.*;

public class parkingServer {
    public static void main(String[] args) throws IOException{

        ServerSocket ActionServerSocket = null;
        boolean listening = true;
        String ActionServerName = "ParkingServer";
        int ActionServerPort = 5958;

        int parkingSpaces = 5;

        parkingState ourSharedActionStateObject = new parkingState(parkingSpaces);


        try{
            ActionServerSocket = new ServerSocket(ActionServerPort);
        } catch(IOException e){
            System.err.println("Could not start " + ActionServerName + " specified port");
            System.exit(-1);
        }
        System.out.println(ActionServerName + " started");

        while(listening){
            new parkingThread(ActionServerSocket.accept(), "EntryA", ourSharedActionStateObject).start();
            new parkingThread(ActionServerSocket.accept(), "EntryB", ourSharedActionStateObject).start();
            new parkingThread(ActionServerSocket.accept(), "ExitA", ourSharedActionStateObject).start();
            new parkingThread(ActionServerSocket.accept(), "ExitB", ourSharedActionStateObject).start();
            System.out.println("New " + ActionServerName + " thread started");
        }
        ActionServerSocket.close();
    }
}
