package back;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.io.*;
import java.net.*;

/**
 * Created by user on 13.04.2018.
 */
public class TestS {
    PrintWriter out = null;

    public TestS(Socket fromserver) {
        Thread thread = new Thread(() -> {


            try {

                BufferedReader in = new
                        BufferedReader(new
                        InputStreamReader(fromserver.getInputStream()));

                String fserver;

                while (true) {
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    fserver = in.readLine();
                    System.out.println(fserver);
                    if (fserver.equals("k"))
                        break;
                }

                out.close();
                in.close();
                fromserver.close();
            } catch (IOException e) {
                e.printStackTrace();
            }


        });
        thread.start();
    }
}


