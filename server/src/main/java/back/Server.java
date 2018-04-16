package back;

import com.mysql.fabric.jdbc.FabricMySQLDriver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.*;
import java.util.ArrayList;
import java.util.Vector;

public class Server {
    public static final String username = "root";
    public static final String password = "vadim123";
    public static final String url = "jdbc:mysql://localhost:3306/sys";
    public static volatile int ID;
    public static Vector<ClientHandler> listeners = new Vector<>();

    public static void main(String[] args) throws IOException {
        System.out.println("Welcome to Server side");
        ServerSocket servers = null;

        Driver driver;
        try {
            driver = new FabricMySQLDriver();
        } catch (SQLException ex) {
            System.out.println("Exeption creation driver");
            return;
        }
        try {
            DriverManager.registerDriver(driver);
        } catch (SQLException ex) {
            System.out.println("Exception register driver");
            return;
        }

        try {
            servers = new ServerSocket(4444);
        } catch (IOException e) {
            System.out.println("Couldn't listen to port 4444");
            System.exit(-1);
        }
        while (true) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                System.out.print("Waiting for a client...");
                Socket fromclient = servers.accept();

                System.out.println(listeners.size());
                System.out.println("Client connected");
                //new thread
                ClientHandler client = new ClientHandler(fromclient);

            } catch (IOException e) {
                System.out.println("Can't accept");
                servers.close();
                System.exit(-1);
            }


        }

    }
}