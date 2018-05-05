package clientChat;


import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import java.net.*;
import java.io.*;
import java.util.Scanner;

public class Proxy {
    static Long IDtest;

    public static void main(String[] ar) throws InterruptedException, IOException {
        int port = 6667;
        ServerSocket ss = new ServerSocket(port);
        while (true) {
            Thread.sleep(10);

            Socket socket = ss.accept();

            Thread thread = new Thread() {
                @Override
                public void run() {
                    WebSocketClient webSocketClient = new StandardWebSocketClient();
                    WebSocketStompClient stompClient = new WebSocketStompClient(webSocketClient);
                    stompClient.setMessageConverter(new MappingJackson2MessageConverter());
                    stompClient.setTaskScheduler(new ConcurrentTaskScheduler());
                    Long id = 45L;
                    String urlChat = "ws://192.168.180.58:8080/hello";
                    InputStream sin = null;
                    try {
                        sin = socket.getInputStream();
                    } catch (IOException e) {

                    }


                    // Конвертируем потоки в другой тип, чтоб легче обрабатывать текстовые сообщения.
                    DataInputStream in = new DataInputStream(sin);

                    handler2 sessionHandler = null;
                    try {
                        long l = Long.valueOf(in.readUTF());
                        System.out.println(l);
                        sessionHandler = new handler2(l);
                        sessionHandler.addListener(new Reciever(socket));
                        sessionHandler.addListener(new Sender(socket, l));
                        stompClient.connect(urlChat, sessionHandler);
                    } catch (IOException e) {

                    }


                }
            };
            thread.start();

        }

    }
}