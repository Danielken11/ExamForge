package com.example.examforge;

import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Server {

    public Socket socket;
    public ObjectOutputStream out;
    public ObjectInputStream input;
    public Boolean isConnected = false;

public Server(){
    connect();
}

    public void connect() {
        try {
            this.socket = new Socket("192.168.100.237", 19997);
            this.out = new ObjectOutputStream(socket.getOutputStream());
            this.input = new ObjectInputStream(socket.getInputStream());
            isConnected = true;
        } catch (IOException ex) {
            ex.printStackTrace();
            System.out.println("Connection Error");
        }

    }
}
