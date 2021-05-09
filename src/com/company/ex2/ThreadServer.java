package com.company.ex2;

import java.io.*;
import java.net.Socket;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ThreadServer {

    Socket clientSocket = null;
    ObjectOutputStream objectOutputStream = null;
    ObjectInputStream objectInputStream = null;
    boolean acabat;
    Llista llista;

    public ThreadServer(Socket clientSocket) throws IOException, ClassNotFoundException {
        this.clientSocket = clientSocket;
        acabat = false;
        objectOutputStream = new ObjectOutputStream(clientSocket.getOutputStream());
        objectInputStream = new ObjectInputStream(clientSocket.getInputStream());
    }

    public void run() {
        while(!acabat) {
            try {
                llista = (Llista) objectInputStream.readObject();
                objectOutputStream.writeObject(ordenarLlista(llista));
                objectOutputStream.flush();
                acabat = true;
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    private Llista ordenarLlista(Llista llista) {
        List<Integer> nombres = llista.getNumberList();
        Collections.sort(nombres);
        List<Integer> nombreSenseDuplicar = nombres.stream().distinct().collect(Collectors.toList());
        llista.setNumberList(nombreSenseDuplicar);
        return llista;
    }
}
