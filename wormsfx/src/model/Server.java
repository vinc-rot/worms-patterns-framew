package model;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;


// Java implementation of Server side
// It contains two classes : Server and ClientHandler
// Save file as Server.java


// Server class
public class Server implements Runnable
{

    private int port;
    private ServerSocket ss;
    private Socket s;

    // Vector to store active clients
    static Vector<ServerThreadHandler> ar = new Vector<>();

    // counter for clients
    static int i = 1;

    public Server(int port) {
        this.port = port;
    }

    public boolean connect () {
        // server is listening on port 1234
        try {
            ss = new ServerSocket(port);
            return true;
        }
        catch (IOException ex)
        {
            return false;
        }

    }


    @Override
    public void run()
    {
        try {
           /* // server is listening on port 1234
            ServerSocket ss = new ServerSocket(port);

            Socket s;

  */          // running infinite loop for getting
            // client request
            while (true) {
                // Accept the incoming request
                s = ss.accept();

                System.out.println("New client request received : " + s);

                // obtain input and output streams
                DataInputStream dis = new DataInputStream(s.getInputStream());
                DataOutputStream dos = new DataOutputStream(s.getOutputStream());

                System.out.println("Creating a new handler for this client...");

                // Create a new handler object for handling this request.
                ServerThreadHandler mtch = new ServerThreadHandler(s, "player" + i, dis, dos);

                // Create a new Thread with this object.
                Thread t = new Thread(mtch);

                System.out.println("Adding this client to active client list");

                // add this client to active clients list
                ar.add(mtch);

                // start the thread.
                t.start();

                // increment i for new client.
                // i is used for naming only, and can be replaced
                // by any naming scheme
                i++;

            }
        }
        catch(BindException bex){

        }
        catch(IOException ex){
            ex.printStackTrace();
            System.out.println("Failure on Etablishing Server");
        }


    }
}


