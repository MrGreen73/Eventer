package com.ivan.eventer.backend;

import com.ivan.eventer.model.Event;
import com.ivan.eventer.model.ListEvents;
import com.ivan.eventer.model.User;

import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Created by user on 30.04.2018.
 */
public class Commands {
    // Create a new RestTemplate instance
    public static String IP = "192.168.1.61:8008";
    static int serverPort = 6667; // здесь обязательно нужно указать порт к которому привязывается сервер.
    static String address = "192.168.180.58"; // это IP-адрес компьютера, где исполняется наша серверная программа.


    public static User loginUser(String email, String password) {

        String url = "http://" + IP + "/loginUser?email=" + email +
                "&password=" + password;
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        User user = restTemplate.getForObject(url, User.class);
        return user;
    }


    public static void updatePerson(String email, String name, String age, String city) {

        Thread thread = new Thread() {
            @Override
            public void run() {
                String url = "http://" + IP + "/updatePerson?name=" + name +
                        "&age=" + age + "&city=" + city + "&email=" + email;
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                restTemplate.getForObject(url, Void.class);
            }
        };
        thread.start();
    }


    public static ArrayList<Event> allEventsOfUser(String email) {
        final ListEvents[] s = {null};
        Thread thread = new Thread() {
            @Override
            public void run() {
                String url = "http://" + IP + "/AllEventsOfUser?email=" + email;

                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                s[0] = restTemplate.getForObject(url, ListEvents.class);

            }
        };
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (s[0] == null)
            return null;
        return s[0].getListEvent();
    }


    public static void addEventToUser(String email, String id) {
        ListEvents s = null;
        Thread thread = new Thread() {
            @Override
            public void run() {
                String url = "http://" + IP + "/addEventToUser?email=" + email + "&id=" + id;

                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                restTemplate.getForObject(url, Void.class);

            }
        };
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }


    public static void addUserToEvent(String email, String id) {
        ListEvents s = null;
        Thread thread = new Thread() {
            @Override
            public void run() {
                String url = "http://" + IP + "/addUserToEvent?email=" + email + "&id=" + id;

                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                restTemplate.getForObject(url, Void.class);

            }
        };
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }


    public static Event findEventById(String id) {
        final Event[] s = {null};
        Thread thread = new Thread() {
            @Override
            public void run() {
                String url = "http://" + IP + "/findEventById?id=" + id;

                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                s[0] = restTemplate.getForObject(url, Event.class);
            }
        };
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return s[0];
    }


    public static String createEvent(String email, String name, String description, byte[] array, String kind, String time, String date) {
        String url = "http://" + IP + "/post";

        Event event = new Event(email, name, description, email);
        event.setImage(array);
        //   event.setPlace(place);
        event.setTime(time);
        event.setKind(kind);
        event.setDate(date);
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        ResponseEntity<String> postResponse = restTemplate.postForEntity(url, event, String.class);
        System.out.println("Response for Post Request: " + postResponse.getBody());
        return postResponse.getBody();
    }

    public static String createUser(String name, String email, String age, String city, String password, byte[] arr) {
        final String[] s = new String[1];
        Thread t = new Thread() {
            @Override
            public void run() {
                String url = "http://" + IP + "/postUser";

                User user = new User(name, email, age, city, password, arr);
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                ResponseEntity<String> postResponse = restTemplate.postForEntity(url, user, String.class);
                System.out.println("Response for Post Request: " + postResponse.getBody());
                s[0] = postResponse.getBody();
            }
        };
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return s[0];
    }


    public static void deleteEvent(String id) {
        String url = "http://" + IP + "/deleteById?id=" + id;
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        restTemplate.getForObject(url, Void.class);
    }

    public static ArrayList<Event> getEvents() {

        String url = "http://" + IP + "/AllEvents";
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        ListEvents s = restTemplate.getForObject(url, ListEvents.class);
        System.err.println("1111111111111111111111 " + s.getListEvent().size() + " 11111111111111111111111111111111111111111111111111111");
// return s;
// System.err.println(s.mListEvent.size());

        return s.getListEvent();

    }


    /* public static void makeConnection() {
         WebSocketClient webSocketClient = new StandardWebSocketClient();
         WebSocketStompClient stompClient = new WebSocketStompClient(webSocketClient);
         stompClient.setMessageConverter(new MappingJackson2MessageConverter());
         stompClient.setTaskScheduler(new ConcurrentTaskScheduler());
         String urlChat = "ws://192.168.180.58:8080/hello";
         handler2 sessionHandler = new handler2();
         sessionHandler.addListener(new Reciever());
         sessionHandler.addListener(new Sender());
         stompClient.connect(urlChat, sessionHandler);

     }*/

    public static void makeConnection() {
        try {
            InetAddress ipAddress = InetAddress.getByName(address); // создаем объект который отображает вышеописанный IP-адрес.
            System.out.println("Any of you heard of a socket with IP address " + address + " and port " + serverPort + "?");
            Socket socket = new Socket(ipAddress, serverPort); // создаем сокет используя IP-адрес и порт сервера.
            System.out.println("Yes! I just got hold of the program.");

            // Берем входной и выходной потоки сокета, теперь можем получать и отсылать данные клиентом.
            InputStream sin = socket.getInputStream();
            OutputStream sout = socket.getOutputStream();

            // Конвертируем потоки в другой тип, чтоб легче обрабатывать текстовые сообщения.
            DataInputStream in = new DataInputStream(sin);
            DataOutputStream out = new DataOutputStream(sout);

            // Создаем поток для чтения с клавиатуры.
            BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));
            String line = null;
            System.out.println("Type in something and press enter. Will send it to the server and tell ya what it thinks.");
            System.out.println();
            new Thread(() -> {
                while (true) {
                    String line2 = null; // ждем пока сервер отошлет строку текста.
                    try {
                        line2 = in.readUTF();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    System.out.println("The server was very polite. It sent me this : " + line2);

                }
            }).start();
            while (true) {
                line = keyboard.readLine(); // ждем пока пользователь введет что-то и нажмет кнопку Enter.
                System.out.println("Sending this line to the server...");
                out.writeUTF(line); // отсылаем введенную строку текста серверу.
                //   out.wr
                out.flush(); // заставляем поток закончить передачу данных.

            }
        } catch (Exception x) {
            x.printStackTrace();
        }
    }

    public static void update() {
        System.out.println("пацаны обновились");
    }

}
