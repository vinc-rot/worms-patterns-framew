package model;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.LinkedList;
import java.util.Scanner;

public class PeterClientOld {

    public static PeterClientOld instance;
    private int ServerPort;
    private String userName;
    private final LinkedList<String> messagesToSend;
    private boolean hasMessages = false;

    public PeterClientOld(int ServerPort, String userName){
        this.userName = userName;
        messagesToSend = new LinkedList<String>();
        this.ServerPort = ServerPort;
        instance = this;
    }

    public static PeterClientOld getInstance() {
        return instance;
    }

    public void addNextMessage(String message){
        synchronized (messagesToSend){
            hasMessages = true;
            messagesToSend.push(message);
        }
        System.out.println("test123");
    }

    public void clientStart() throws UnknownHostException, IOException
    {
        Scanner scn = new Scanner(System.in);

        // getting localhost ip
        InetAddress ip = InetAddress.getByName("localhost");

        // establish the connection
        Socket s = new Socket(ip, ServerPort);

        // obtaining input and out streams
        DataInputStream dis = new DataInputStream(s.getInputStream());
        DataOutputStream dos = new DataOutputStream(s.getOutputStream());

        // sendMessage thread
        Thread sendMessage = new Thread(new Runnable()
        {
            @Override
            public void run() {
                while (true) {

                    // read the message to deliver.
                    if(hasMessages) {
                        String nextSend = "";
                        synchronized (messagesToSend) {
                            nextSend = messagesToSend.pop();
                            hasMessages = !messagesToSend.isEmpty();
                        }
                        try {
                            // write on the output stream
                            System.out.println("test123");
                            dos.writeUTF(nextSend);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }


                    }
                }
            }
        });

        // readMessage thread
        Thread readMessage = new Thread(new Runnable()
        {
            @Override
            public void run() {
                while (true) {
                    try {
                        // read the message sent to this client
                        String msg = dis.readUTF();
                        System.out.println(msg);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        sendMessage.start();
        readMessage.start();

    }

}