package model;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;

class ServerThreadHandler implements Runnable
{
    private String name;
    final DataInputStream dis;
    final DataOutputStream dos;
    Socket s;
    boolean isloggedin;

    // constructor
    public ServerThreadHandler(Socket s, String name, DataInputStream dis, DataOutputStream dos) {
        this.dis = dis;
        this.dos = dos;
        this.name = name;
        this.s = s;
        this.isloggedin=true;
    }

    @Override
    public void run() {

        String received;
        while (true)
        {
            try {
                // receive the string
                received = dis.readUTF();

                System.out.println("SERVER: " + received);

                if (received.equals("logout")) {
                    this.isloggedin = false;
                    this.s.close();
                    break;
                } else if (received.equals("gethighscore")){
                    HighScoreList highScoreList = new HighScoreList();
                    highScoreList.deserialize();
                    for(HighScore hs : highScoreList.getHighScores()) {
                        // System.out.println("Name:" + hs.getName() + ", Points: " + hs.getScore() );
                        dos.writeUTF("highscore#" + hs.getName() + "#" + hs.getScore());
                    }
                }
                else if (received.contains("addhighscore")){
                    HighScoreList highScoreList = new HighScoreList();
                    highScoreList.deserialize();

                    StringTokenizer st = new StringTokenizer(received, "#");
                    String addhighscore = st.nextToken();
                    if (addhighscore.equals("addhighscore")) {
                        String name = st.nextToken();
                        String score = st.nextToken();
                        highScoreList.getHighScores().add(new HighScore(Integer.parseInt(score), 1, name));
                    }

                    highScoreList.serialize();
                }
                else{
                    // break the string into message and recipient part
                    StringTokenizer st = new StringTokenizer(received, "@");
                    String MsgToSend = st.nextToken();
                    String recipient = st.nextToken();
                    // search for the recipient in the connected devices list.
                    // ar is the vector storing client of active users
                    for (ServerThreadHandler mc : Server.ar) {
                        // if the recipient is found, write on its
                        // output stream
                        if (mc.name.equals(recipient) && mc.isloggedin == true) {
                            mc.dos.writeUTF(MsgToSend);
                            break;
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        try
        {
            // closing resources
            this.dis.close();
            this.dos.close();

        }catch(IOException e){
            e.printStackTrace();
        }
    }
}