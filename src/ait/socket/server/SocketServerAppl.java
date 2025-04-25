package ait.socket.server;

import ait.socket.server.task.ClientHandel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class SocketServerAppl {
    public static void main(String[] args) throws InterruptedException {
        int port = 1337;
        ExecutorService executorService = Executors.newFixedThreadPool(2);

        try (ServerSocket serverSocket = new ServerSocket(port)){
            while (true) {
                System.out.println("Server waiting...");
                Socket socket = serverSocket.accept();
                System.out.println("Connection established");
                System.out.println("Client host: " + socket.getInetAddress() + " : " + socket.getPort());
                Runnable task = new ClientHandel(socket);
                executorService.execute(task);
            }
        } catch (IOException e) {
            System.out.println("Connection closed");
//            e.printStackTrace();
        } finally {
            executorService.shutdown();
            executorService.awaitTermination(30, TimeUnit.SECONDS);
            System.out.println("Server finished");
        }
    }
}
