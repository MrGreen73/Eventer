package back;

import javax.print.attribute.standard.Severity;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.*;

/**
 * Created by user on 13.04.2018.
 */
public class ClientHandler {
    PrintWriter out = null;

    public ClientHandler(Socket fromclient) {
        Thread thread = new Thread(() -> {
            System.out.println("Thread Running");

            Server.listeners.add(this);

            BufferedReader in = null;

            try {
                in = new BufferedReader(new
                        InputStreamReader(fromclient.getInputStream()));
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                out = new PrintWriter(fromclient.getOutputStream(), true);
            } catch (IOException e) {
                e.printStackTrace();
            }
            String input = null, output;

            System.out.println("Wait for messages");
            Connection connection = null;
            Statement statement = null;
            try {
                connection = DriverManager.getConnection(Server.url, Server.username, Server.password);
                statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT * FROM sys.employee");
                if (resultSet.last())
                    Server.ID = resultSet.getInt("ID") + 1;
                else Server.ID = 0;
            } catch (SQLException e) {
                e.printStackTrace();
            }

            while (true) {
                try {
                    input = in.readLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (input.equalsIgnoreCase("exit")) break;
                //out.println("S ::: " + input);
                String s = "";
                try {

                    if (input.split(" ")[0].equals("create")) {

                        for (int i = 0; i < Server.listeners.size(); i++) {

                            if (!Server.listeners.get(i).equals(this)) {
                                Server.listeners.get(i).out.println("Левый чувак обновил базу");
                            } else {
                                Server.listeners.get(i).out.println("Ты обновил базу");
                            }
                        }
                    }

                } catch (Exception ex) {
                    System.out.println(s);
                    System.out.println(s.length());
                    System.out.println(Server.ID);
                    ex.printStackTrace();
                    System.out.println("E2xception creation connection");

                    return;
                }
                System.out.println(input);
            }
            try {
                out.close();
                in.close();
                fromclient.close();
                try {
                    connection.close();
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        thread.start();
    }
}
