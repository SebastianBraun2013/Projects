package javaserverdungeonsandseminars;

import java.net.*;
import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author nicholas.bohm
 */
public class Server {

    private ServerSocket serverSocket;
    private Socket client;
    private PrintWriter out;
    private BufferedReader in;

    public Server(int port) throws IOException {
        this.serverSocket = new ServerSocket(port);
    }

    public boolean inputReady() {
        try {
            return in.ready();
        } catch (Exception e) {
            System.out.println(e);
        }
        return false;
    }

    public String readLine() {
        try {
            return in.readLine();
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;

    }

    public void sendLine(String msg) {
        this.out.println(msg);
    }

    public void stop() throws IOException {
        in.close();
        out.close();
        client.close();
        this.serverSocket.close();
    }

    void waitForConnection() {
        try {
            this.client = serverSocket.accept();

            this.out = new PrintWriter(this.client.getOutputStream(), true);
            this.in = new BufferedReader(new InputStreamReader(client.getInputStream()));
        } catch (Exception theseHands) {
            theseHands.printStackTrace();
        }

    }

    public void close() throws IOException {
        
        this.in.close();
        this.out.close();
        this.client.close();
    }
}