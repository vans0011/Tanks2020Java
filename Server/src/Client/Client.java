package Client;

import Game.Game;
import Game.Player;
import IO.Input;
import graphics.TextureAtlas;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private static final String SERVER_HOST ="localhost";
    private static final int SERVER_PORT = 8080;
    private Socket clientSocket;
    private Scanner inMessage;
    private PrintWriter outMessage;
    public Player newPlayer;
    public String update = null;
    public int x,y;


    private String clientName = null;

    public String getClientName(){
        return this.clientName;
    }

    public Client(TextureAtlas atlas, Graphics2D graphics){
        try {
            clientSocket = new Socket(SERVER_HOST,SERVER_PORT);
            inMessage = new Scanner(clientSocket.getInputStream());
            outMessage = new PrintWriter(clientSocket.getOutputStream());
            newPlayer = null;

        }catch (IOException e){
            e.printStackTrace();
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (true) {
                        if (inMessage.hasNext()) {
                            String inMes = inMessage.nextLine();
                            //System.out.println(inMes);
                            String[] masMes = inMes.split(" ");
                            switch (masMes[0]){
                                case "0": clientName = masMes[1];
                                    x = Integer.parseInt(masMes[2]);
                                    y = Integer.parseInt(masMes[3]);
                                    break;
                                case "1":
                                    newPlayer = new Player(Integer.parseInt(masMes[2]),Integer.parseInt(masMes[3]), 2, 3,atlas);
                                    System.out.println("newPlayer is create");
                                    break;
                                case "2": update = masMes[2];
                                break;
                                    //System.out.println(masMes[2]);
                            }

                        }
                    }
                } catch (Exception e) {
                }
            }
        }).start();

    }

    public int getX(){
        return this.x;
    }

    public int getY(){
        return this.y;
    }

   public void update(){
        if(update!=null){
         newPlayer.clientUpdate(update);
        }
        update = null;
    }
    public void outMsg(Input input){

        if(input.getKey(KeyEvent.VK_UP)){
            sendMsg("2 " + getClientName() + " UP");
        }else if(input.getKey(KeyEvent.VK_DOWN)){
            sendMsg("2 " + getClientName() + " DOWN");
        }else if(input.getKey(KeyEvent.VK_RIGHT)){
            sendMsg("2 " + getClientName() + " RIGHT");
        }else if (input.getKey(KeyEvent.VK_LEFT)){
            sendMsg("2 " + getClientName() + " LEFT");
        }
    }

    public void render(Graphics2D g){
        if (newPlayer != null){
            newPlayer.render(g);
        }

    };

    public void sendMsg(String msg) {
        outMessage.println(msg);
        outMessage.flush();
    }

    }






