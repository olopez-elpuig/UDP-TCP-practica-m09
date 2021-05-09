package com.company.ex1;

import java.io.IOException;
import java.net.*;
import java.nio.ByteBuffer;

public class ClientVelocimetre {

    private MulticastSocket multicastSocket;
    private InetAddress ipMulticast;
    boolean continueRunning = true;
    private int portDesti;
    private String serverIp;
    private InetAddress ipDesti;

    int total = 0;
    int counter = 0;
    int resultat;

    InetSocketAddress groupMulticast;
    NetworkInterface networkInterface;

    public ClientVelocimetre(String serverIp, int portDesti) {
        this.serverIp = serverIp;
        this.portDesti = portDesti;

        try {
            multicastSocket = new MulticastSocket(5557);
            ipMulticast = InetAddress.getByName("224.0.2.1");
            groupMulticast = new InetSocketAddress(ipMulticast, 5557);
            networkInterface = NetworkInterface.getByName("wlan1");
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            ipDesti = InetAddress.getByName(serverIp);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    public void runClient() throws IOException {
        byte[] receivedData = new byte[4];

        DatagramPacket packet;
        DatagramSocket socket = new DatagramSocket();

        multicastSocket.joinGroup(groupMulticast, networkInterface);

        while (true) {
            packet = new DatagramPacket(receivedData, 4);
            multicastSocket.receive(packet);
            resultat = getDataToRequest(packet.getData(), packet.getLength());
        }
    }

    public int getDataToRequest(byte[] data, int length) {
        int n = ByteBuffer.wrap(data).getInt();
        int resultat = 0;
        total = total + n;
        counter++;

        if (counter % 5 == 0) {
            resultat = total / 5;
            System.out.println(resultat);
            total = 0;
            counter = 0;
        }
        return resultat;
    }

    public static void main(String[] args) {
        ClientVelocimetre clientVelocimetre = new ClientVelocimetre("224.0.2.1", 5557);
        try {
            clientVelocimetre.runClient();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
