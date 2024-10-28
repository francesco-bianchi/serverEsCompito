package com.example;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(3000);
        Username user = new Username();
        System.out.println("Server partito");
        while(true){
            Socket socket = serverSocket.accept();
            GestoreServer gs = new GestoreServer(socket , user);
            gs.start();
        }
    }
}