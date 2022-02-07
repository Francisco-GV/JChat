package com.frank.jchat.networking.service;

import com.frank.jchat.networking.Connection;

import java.io.IOException;
import java.net.Socket;

public class ClientService {

    public Connection tryConnection(String ip, int port) {
        try {
            Socket socket = new Socket(ip, port);

            return new Connection(socket);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

}
