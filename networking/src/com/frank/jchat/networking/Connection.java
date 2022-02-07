package com.frank.jchat.networking;

import com.frank.jchat.base.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

@SuppressWarnings("unused")
public class Connection {
    private Socket socket;
    private final ObjectOutputStream outputStream;
    private final ObjectInputStream inputStream;
    private final Runnable inputRunnable;

    private List<Message> messageList;

    public Connection(Socket socket) throws IOException {
        this.socket = socket;
        this.outputStream = new ObjectOutputStream(socket.getOutputStream());
        this.inputStream = new ObjectInputStream(socket.getInputStream());

        inputRunnable = () -> {
            while (!socket.isClosed()) {
                try {
                    Object object = inputStream.readObject();

                    if (object instanceof Message message) {
                        messageList.add(message);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                    try {
                        socket.close();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        };
    }

    private void startConnection() {
        new Thread(inputRunnable).start();
    }

    private void closeConnection() {
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void reconnect() {
        String address = socket.getInetAddress().getHostAddress();
        int port = socket.getPort();

        try {
            socket = new Socket(address, port);
            startConnection();
            System.out.println("Reconnection was successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void send(Message message) {
        try {
            outputStream.writeObject(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Message> getMessageList() {
        return messageList;
    }
}
