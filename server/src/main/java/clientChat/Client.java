package clientChat;


import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import java.io.IOException;
import java.util.Scanner;

public class Client {
    static Long id;

    public static void main(String[] args) throws IOException {
   /*     WebSocketClient webSocketClient = new StandardWebSocketClient();
        WebSocketStompClient stompClient = new WebSocketStompClient(webSocketClient);
        stompClient.setMessageConverter(new MappingJackson2MessageConverter());
        stompClient.setTaskScheduler(new ConcurrentTaskScheduler());
        id = new Scanner(System.in).nextLong();
        String urlChat = "ws://192.168.180.58:8080/hello";
        handler2 sessionHandler = new handler2();
        sessionHandler.addListener(new Reciever());
        sessionHandler.addListener(new Sender());
        stompClient.connect(urlChat, sessionHandler);
*/
Commands.createUser("тест","тест@mail.ru","123");
   Commands.createEvent(12,"тест","тест@mail.ru","123");
//        new Scanner(System.in).nextLine(); //Don't close immediately.
    }
}
