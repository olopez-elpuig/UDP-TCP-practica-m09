package com.company.ex2;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    int port;

        public Server(int port) {
        this.port = port;
    }

    public void listen() {
        ServerSocket serverSocket = null;
        Socket clientSocket = null;

        try {
            serverSocket = new ServerSocket(port);
            while(true) {
                clientSocket = serverSocket.accept();
                ThreadServer threadServer = new ThreadServer(clientSocket);
                Thread client = new Thread((Runnable) threadServer);
                client.start();
            }
        } catch (IOException ex) {

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Server server = new Server(5558);
        server.listen();
    }
}

