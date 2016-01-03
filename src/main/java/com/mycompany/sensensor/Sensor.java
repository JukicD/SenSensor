/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.sensensor;

import com.forall.modell.DataProxy;
import java.io.IOException;
import java.net.URI;
import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.websocket.ClientEndpoint;
import javax.websocket.ContainerProvider;
import javax.websocket.DeploymentException;
import javax.websocket.OnClose;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;

/**
 *
 * @author jd
 */
@ClientEndpoint(encoders = DataProxyEncoder.class)
public class Sensor {

    private double[] buffer;

    private Session session;
    ExecutorService service = Executors.newFixedThreadPool(10);

    public void Sensor() {
        buffer = new double[50];
    }

    public void measure() {
        System.out.println("Start measuring!");
        DataProxy proxy = new DataProxy();
        int i = 0;

        long start = System.currentTimeMillis();

        while(true){
            proxy.setData(sin(i));
            proxy.setTimeStamp(System.currentTimeMillis() - start);

            session.getAsyncRemote().sendObject(proxy);
            i++;
            try {
                Thread.sleep(50);
            } catch (InterruptedException ex) {
                Logger.getLogger(Sensor.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
    }

    @OnClose
    public void onClose(Session session) {
        this.session = null;
    }

    public void connectToServer() {

        System.out.println("Connecting ...");
        WebSocketContainer container = ContainerProvider.getWebSocketContainer();

        try {
            URI uri = URI.create("ws://192.168.0.8:8080/SenServer/temperature");
            container.connectToServer(this, uri);
            System.out.println("Connected !");
        } catch (IOException ex) {
            System.out.println("Connection failed! " + ex);
        } catch (DeploymentException ex) {
            Logger.getLogger(Sensor.class.getName()).log(Level.SEVERE, null, ex);
            System.exit(-1);
        }
    }

    private void sendData(double[] data) {

        ByteBuffer byteBuffer = ByteBuffer.allocate(data.length * 8);
        DoubleBuffer doubleBuffer = byteBuffer.asDoubleBuffer();
        doubleBuffer.put(data);
        session.getAsyncRemote().sendBinary(byteBuffer);
    }

    public static void main(String[] args) {

        System.out.println("Loading ...");
        Sensor sensor = new Sensor();
        sensor.connectToServer();
        sensor.measure();
    }

    private double sin(int i) {
        return Math.sin((double) i * 0.01) * 50 + 50;
    }
}