package com.company.ex1;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.nio.ByteBuffer;

public class SrvVelocitats {

    MulticastSocket multicastSocket;
    InetAddress ipMulticast;
    int port;
    boolean continueRunning = true;
    Velocitat simulator;

    public SrvVelocitats(int portValue, String strIp) throws IOException {

        multicastSocket = new MulticastSocket(portValue);
        ipMulticast = InetAddress.getByName(strIp);
        port = portValue;
        simulator = new Velocitat(100);
    }

    public void runServer() throws IOException{

        DatagramPacket packet;
        byte [] sendingData;

        while(continueRunning){
            sendingData = ByteBuffer.allocate(4).putInt(simulator.agafaVelocitat()).array();
            packet = new DatagramPacket(sendingData, sendingData.length,ipMulticast, port);
            multicastSocket.send(packet);
            System.out.println(sendingData);

            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                ex.getMessage();
            }


        }
        multicastSocket.close();
    }

    public static void main(String[] args) throws IOException {

        SrvVelocitats srvVel = new SrvVelocitats(5557, "224.0.0.1");
        srvVel.runServer();
        System.out.println("Parat!");

    }

}
