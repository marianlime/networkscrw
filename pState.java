package com.company;

import java.io.*;
import java.net.*;

public class parkingState {

    private parkingState mySharedObj;
    private String myThreadName;
    private String parkingServer;
    private double parkingSpaces;
    private double queue;
    private boolean accessing=false;
    private int threadsWaiting = 0;


    parkingState(double SharedVariable){
        parkingSpaces = SharedVariable;
        queue = SharedVariable;
    }


    public synchronized void acquireLock() throws InterruptedException{
        Thread me = Thread.currentThread();
        System.out.println(me.getName()+" is attempting to acquire a lock");
        ++threadsWaiting;
        while(accessing){
            System.out.println(me.getName()+" waiting to get a lock as some else is accessing...");
            wait();
        }
        --threadsWaiting;
        accessing = true;
        System.out.println(me.getName()+" got a lock");
    }


    public synchronized void releaseLock(){
        accessing  = false;
        notifyAll();
        Thread me = Thread.currentThread();
        System.out.println(me.getName()+" released a lock");
    }



    public synchronized String processInput(String myThreadName, String theInput){
        System.out.println(myThreadName + " received "+ theInput);
        String theOutPut = null;
        if(theInput.equalsIgnoreCase("Enter")) {
            if (myThreadName.equals("EntryA")) {
                parkingSpaces = parkingSpaces - 1;
                if (parkingSpaces == 0) {
                    queue = queue + 1;
                }
                System.out.println(myThreadName + " sent a car to  " + parkingServer);
                theOutPut = "Car entered Car Park. There are only " + parkingSpaces + " available.";
            } else if (myThreadName.equals("EntryB")) {
                parkingSpaces = parkingSpaces - 1;
                if (parkingSpaces == 0) {
                    queue = queue + 1;
                }
                System.out.println(myThreadName + " sent a car to " + parkingServer);
                theOutPut = "Car entered Car Park. There are only " + parkingSpaces + " available.";
            }
            else {
                theOutPut = myThreadName + "received incorrect request - only understand \"Enter\"";
            }
        }
            if (theInput.equalsIgnoreCase("Exit")) {
                if (myThreadName.equals("ExitA")) {
                    parkingSpaces = parkingSpaces + 1;


                } else if (myThreadName.equals("ExitB")) {
                    parkingSpaces = parkingSpaces + 1;

                } else {
                    System.out.println("Error - Thread call not recognised");
                }
            }
        else{
            theOutPut = myThreadName + " received incorrect request  - only understand \"Exit\"";

        }
        System.out.println(theOutPut);
        return theOutPut;
    }

}
