package com.company.ex2;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Client {
    String hostname;
    int port;
    boolean continueConnected;
    Llista ret = null;
    String nom;
    List<Integer> numberList;
    int nombre = 0;
    Scanner sc = new Scanner(System.in);
    Socket socket;
    InputStream in;
    ObjectInputStream oiStream;

    OutputStream out;
    ObjectOutputStream ooStream;

    public Client(String hostname, int port) {
        this.hostname = hostname;
        this.port = port;
        continueConnected = true;
    }

    public void run() {
        numberList = new ArrayList<>();
        System.out.println("introdueix el teu nom:");
        nom = sc.nextLine();
        System.out.print("Hola " + nom + ". ");

        do {
            System.out.println("Introdueix un nombre (0 per finalitzar):");
            nombre = sc.nextInt();
            if (nombre > 0) numberList.add(nombre);
        } while (nombre > 0);
        ret = new Llista(nom, numberList);

        try {
            socket = new Socket(InetAddress.getByName(hostname), port);
            out = socket.getOutputStream();
            ooStream = new ObjectOutputStream(out);
            ooStream.writeObject(ret);

            in = socket.getInputStream();
            oiStream = new ObjectInputStream(in);
            ret = (Llista) oiStream.readObject();
            getRequest(ret);

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }


    }

    private void getRequest(Llista serverData) {

        System.out.print(serverData.getNom() + ", aquesta es la llista amb els nombres ordenats que has transmes: ");
        serverData.getNumberList().forEach(integer -> System.out.print(integer + ", "));
        System.out.println();
        System.out.println("Adeu!");

    }


    public static void main(String[] args) {
        Client client = new Client("localhost", 5557);
        client.run();
    }
}

