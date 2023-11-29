package com.company;

import java.io.*;
import java.net.*;

public class parkingState {

    private parkingState mySharedObj;
    private String myThreadName;
    private String parkingServer;
    private int parkingSpaces;
    private int queueA = 0;
    private int queueB = 0;
    private boolean accessing=false;
    private int threadsWaiting = 0;


    parkingState(int SharedVariable){
        parkingSpaces = SharedVariable;
        queueA = SharedVariable;
        queueB = SharedVariable;
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
                if(parkingSpaces < 5) {
                    parkingSpaces--;
                    System.out.println(myThreadName + " sent a car to WLFB car park");
                    theOutPut = "Car entered Car Park. There are only " + parkingSpaces + " available.";
                }
                if (parkingSpaces == 0) {
                    queueA++;
                }
            } else if (myThreadName.equals("EntryB")) {
                if(parkingSpaces <5) {
                    parkingSpaces--;
                    System.out.println(myThreadName + " sent a car to WLFB car park");
                    theOutPut = "Car entered WLFB Car Park. There are " + parkingSpaces + " available.";
                }
                else {
                    queueB++;
                    theOutPut = "There are no parking spaces available. You have been places in a queue. Your position in queue: " + queueB;
                }
            }
            else {
                theOutPut = myThreadName + "received incorrect request - only understand \"Enter\"";
            }
        }
           else if (theInput.equalsIgnoreCase("Exit")) {
            if (myThreadName.equals("ExitA")) {
                if (parkingSpaces <= 5) {
                    parkingSpaces++;
                    theOutPut = "Car left WLFB Car Park. There are " + parkingSpaces + " available.";
                    if (queueA > 0) {
                        queueA--;
                        parkingSpaces--;
                        theOutPut = "Car left WLFB Car Park. There are " + parkingSpaces + " available.";
                    }
                }

            } else if (myThreadName.equals("ExitB")) {
                if (parkingSpaces <= 5) {
                    parkingSpaces++;
                    theOutPut = "Car left WLFB Car Park. There are " + parkingSpaces + " available.";
                    if (queueA > 0) {
                        queueA--;
                        parkingSpaces--;
                        theOutPut = "Car left WLFB Car Park. There are " + parkingSpaces + " available.";
                    }
                } else {
                    System.out.println("Error - Thread call not recognised");
                }
            }
        }
        else{
            theOutPut = myThreadName + " received incorrect request  - only understand \"Exit\"";

        }
        System.out.println(theOutPut);
        return theOutPut;
    }

}
