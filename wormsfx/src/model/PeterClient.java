package model;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Scanner;

public class PeterClient implements Runnable{

    public static PeterClient instance;
    private int ServerPort;
    private String userName;
    private LinkedList<String> messagesToSend;
    private boolean hasMessages = false;

    public PeterClient(int ServerPort, String userName){
        this.userName = userName;
        messagesToSend = new LinkedList<String>();
        this.ServerPort = ServerPort;
        instance = this;
    }

    public static PeterClient getInstance() {
        return instance;
    }

    public void addNextMessage(String message){
        System.out.println("OUT: ADD NEXT MESSAGE");
        synchronized (messagesToSend){
            hasMessages = true;
            messagesToSend.push(message);
        }
    }

    @Override
    public void run()
    {
            try{
                Scanner scn = new Scanner(System.in);

                System.out.println("OUT: IN RUN");
                // getting localhost ip
                InetAddress ip = InetAddress.getByName("localhost");

                // establish the connection
                Socket s = new Socket(ip, ServerPort);

                // obtaining input and out streams
                DataInputStream dis = new DataInputStream(s.getInputStream());
                DataOutputStream dos = new DataOutputStream(s.getOutputStream());

                System.out.println("OUT: VOR WHILE");

                while(true){
                    if(hasMessages) {
                        System.out.println(hasMessages);
                        String nextSend = "";
                        synchronized (messagesToSend) {
                            nextSend = messagesToSend.pop();
                            hasMessages = !messagesToSend.isEmpty();
                        }
                        System.out.println(hasMessages+"OUT: IN WHILE SCHLEIFE");
                        dos.writeUTF(nextSend);
                    }
                    //String msg = dis.readUTF();
                    //System.out.println(msg);

                }
            }
            catch(IOException ex){
                ex.printStackTrace();
                System.out.println("Failure on Connect");
            }

    }

}