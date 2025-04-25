package ait.socket.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class SocketServerAppl {
    public static void main(String[] args) {
        int port = 1337;
        try (ServerSocket serverSocket = new ServerSocket(port)){
                System.out.println("Server waiting...");
                Socket socket = serverSocket.accept();
                System.out.println("Connection established");
                System.out.println("Client host: " + socket.getInetAddress() + " : " + socket.getPort());
                BufferedReader socketReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter socketWriter = new PrintWriter(socket.getOutputStream(), true);
                while (true){
                    String message = socketReader.readLine();
                    if (message == null){
                        System.out.println("Connection: " + socket.getInetAddress() + " : " + socket.getPort() + " closed");
                        break;
                    }
                    System.out.println("Server received: " + message);
                    socketWriter.println(message + " " + LocalTime.now().format(DateTimeFormatter.ofPattern("hh.mm.ss")));
                }
        } catch (IOException e) {
            System.out.println("Connection closed");
//            e.printStackTrace();
        }
    }
}
