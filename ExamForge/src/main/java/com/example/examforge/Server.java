package com.example.examforge;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Server {

    public Socket socket;
    public ObjectOutputStream out;
    public ObjectInputStream input;

    public void connect() throws IOException {

        this.socket = new Socket("192.168.100.237",19997);
        this.out = new ObjectOutputStream(socket.getOutputStream());
        this.input = new ObjectInputStream(socket.getInputStream());

    }


}
