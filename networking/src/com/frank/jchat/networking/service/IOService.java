package com.frank.jchat.networking.service;

import com.frank.jchat.networking.Connection;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@SuppressWarnings("unused")
public class IOService {
    private final ServerService serverService;
    private final ClientService clientService;

    private final List<Connection> connections;

    public IOService() {
        connections = Collections.synchronizedList(new ArrayList<>());
        clientService = new ClientService();
        serverService = new ServerService(this);
    }

    public boolean startServer(int port) {
        return serverService.start();
    }

    public void tryConnection(String ip, int port) {
        Connection connection = clientService.tryConnection(ip, port);

        System.out.printf("Connection with %s:%d was successfully%n", ip, port);

        connections.add(connection);
    }

    public List<Connection> getConnections() {
        return connections;
    }
}
