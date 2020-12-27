package Serv;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import ClientHandler.ClientHandler;


public class Server {
    static final int PORT = 8080;
    Socket clientSocket = null;
    ServerSocket server = null;
    private ArrayList<ClientHandler> clientHandlers = new ArrayList<ClientHandler>();

    public Server() {

        try  {
            server = new ServerSocket(PORT);
            System.out.println("Start server");

            while (true) {
                clientSocket = server.accept();
                ClientHandler clientHandler = new ClientHandler(clientSocket, this);
                clientHandlers.add(clientHandler);
                System.out.println("new client");
                new Thread(clientHandler).start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try{
                clientSocket.close();
                server.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }

    }

    public void sendMesAll(String msg){
        String mes[] = msg.split(" ");
       //System.out.println(msg);
        //if (mes[0].equals("2")) {

        //}
        for (ClientHandler o : clientHandlers){
            if(mes[1].equals(o.getPlayersName())){ continue;}
            o.sendMsg(msg);
        }

    }
    public void getAllMap(String name){

        for(ClientHandler o : clientHandlers){
            if(name.equals(o.getPlayersName())){
                for(ClientHandler i : clientHandlers){
                    if (name.equals(i.getPlayersName())){continue;}
                    o.sendMsg(i.getLocation());
                }
                break;
            }
        }

    }

    public void removeClient(String player){

        clientHandlers.remove(player);
    }

}
