package com.example;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(3000);

        while(true){
            Socket socket = serverSocket.accept();
            GestoreServer gs = new GestoreServer();
            gs.start();
        }
    }
}