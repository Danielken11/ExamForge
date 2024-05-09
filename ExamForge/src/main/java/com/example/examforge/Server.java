package com.example.examforge;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Server {

    public Socket socket;
    public ObjectOutputStream out;
    public ObjectInputStream in;
    public Boolean connected = false;
    public String result;

public Server(){
    connect();
}

public void sendQuery(String query) throws IOException {
    out.writeObject(query);
}
public boolean getconnectionStatus(){
    return connected;
}
public void receiveQuery() throws IOException, ClassNotFoundException {
    result = in.readObject().toString();
}
    public void connect() {
        try {
            this.socket = new Socket("localhost", 8080);
            this.out = new ObjectOutputStream(socket.getOutputStream());
            this.in = new ObjectInputStream(socket.getInputStream());
            connected = true;
        } catch (IOException ex) {
            ex.printStackTrace();
            System.out.println("Connection Error");
            connected = false;
        }
    }
}
