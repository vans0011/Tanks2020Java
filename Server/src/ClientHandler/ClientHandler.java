package ClientHandler;

import Game.Player;
import Serv.Server;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class ClientHandler implements Runnable{
    private Server server;
    private PrintWriter outMessage;

    private Scanner inMessage;
    private static final String HOST = "localhost";
    private static final int PORT = 8080;

    private Socket clientSocket = null;
    public static int clients_count = 0;
    public int x;
    public int y;
    private String playersName;


    public ClientHandler(Socket socket, Server server) {
        try {
            clients_count++;
            this.server = server;
            this.clientSocket = socket;
            this.outMessage = new PrintWriter(socket.getOutputStream());
            this.inMessage = new Scanner(socket.getInputStream());
            playersName = "Player"+ clients_count;

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
      @Override
              public void run() {
              try {

                  while (true){
                      getMap();
                      sendMsg("0 " + getPlayersName() + " " + this.x + " " + this.y);
                      if (clients_count > 1) {
                          server.sendMesAll("1 " + getPlayersName() + " " + this.x + " " + this.y);
                      }
                      server.getAllMap(getPlayersName());
                      break;
                  }

                  while (true){
                      if (inMessage.hasNext()){
                          String client_msg = inMessage.nextLine();
                          String m[] = client_msg.split(" ");
                         // System.out.println(client_msg);
                          if (client_msg.equalsIgnoreCase("##session##end##")){
                              break;
                          }

                          switch (m[0]){
                              case "2":
                                  if(m[2].equals("UP")){
                                  y -=3;
                              }else if(m[2].equals("DOWN")){
                                  y +=3;
                              }else if(m[2].equals("RIGHT")){
                                  x +=3;
                              }else if (m[2].equals("LEFT")){
                                  x -=3;
                              }

                          }
                          server.sendMesAll(client_msg);

                      }
                      Thread.sleep(10);
                  }
              }catch (InterruptedException e){
                  e.printStackTrace();
              }
              finally {
                  this.close();
              }
        }

    public void sendMsg(String msg){
        try{
            outMessage.println(msg);
            outMessage.flush();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void getMap(){
        switch (this.clients_count){
            case 1:
                this.x = 600;
                this.y = 210;
                break;
            case 2:
                this.x = 100;
                this.y = 210;
        }

    }

    public String getLocation(){
        return "1 " + getPlayersName() + " " +this.x + " " +this.y ;
    }

    public String getPlayersName() {
        return playersName;
    }

    public void close(){
        server.removeClient(playersName);
        clients_count--;
        server.sendMesAll("Clients in the chat ");
    }





}
