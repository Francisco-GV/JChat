package com.frank.jchat.networking.service;

import com.frank.jchat.networking.Connection;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

@SuppressWarnings("unused")
public class ServerService {
    private ServerSocket serverSocket;
    private int port;
    private final Runnable serverRunnable;
    private IOService ioService;

    {
        serverRunnable = () -> {
            while (serverSocket != null && !serverSocket.isClosed()) {
                try {
                    Socket socket = serverSocket.accept();



                    ioService.getConnections().add(new Connection(socket));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
    }

    public ServerService(int port, IOService ioService) {
        this.port = port;
        this.ioService = ioService;
    }

    public ServerService(IOService ioService) {
        this.ioService = ioService;
    }

    public boolean start() {
        if (serverSocket == null) {
            try {
                serverSocket = new ServerSocket(port);
                new Thread(serverRunnable).start();
                return true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.err.println("Server Socket already started. Please restart instead.");
        }

        return false;
    }

    public boolean restart() {
        try {
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        serverSocket = null;

        return start();
    }


    /**
     * Changes the next port that will use the server.
     * DOESN'T change the current server port, must restart.
     * @param port the port that will use the server.
     */
    public void setPort(int port) {
        this.port = port;
    }
}
