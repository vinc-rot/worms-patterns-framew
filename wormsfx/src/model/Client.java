package model;

import controller.NetworkObserver;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.LinkedList;

public class Client implements Runnable{

    public static Client instance;
    private int ServerPort;
    private String ServerIP;
    private String userName;
    private LinkedList<String> messagesToSend;
    private volatile boolean hasMessages = false;

    public Client(int ServerPort, String ServerIP, String userName){
        this.userName = userName;
        messagesToSend = new LinkedList<String>();
        this.ServerPort = ServerPort;
        this.ServerIP = ServerIP;
        instance = this;
    }

    public static Client getInstance() {
        return instance;
    }

    public void addNextMessage(String message){
        synchronized (messagesToSend){
            hasMessages = true;
            messagesToSend.push(message);
        }
    }

    @Override
    public void run()
    {
            try{
                // establish the connection
                Socket s = new Socket(ServerIP, ServerPort);

                // obtaining input and out streams
                DataInputStream dis = new DataInputStream(s.getInputStream());
                DataOutputStream dos = new DataOutputStream(s.getOutputStream());

                // start des Network Observers
                NetworkObserver no = new NetworkObserver();
                no.addObserver(Game.getInGameControllerInstance());

                while(true){
                    if(dis.available() > 0){
                        String msg = dis.readUTF();
                        no.setMessage(msg);
                    }
                    if(hasMessages) {
                        String nextSend = "";
                        synchronized (messagesToSend) {
                            nextSend = messagesToSend.pop();
                            hasMessages = !messagesToSend.isEmpty();
                        }
                        dos.writeUTF(nextSend);
                    }
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
                System.out.println("Failure on Connect");
            }

    }

}