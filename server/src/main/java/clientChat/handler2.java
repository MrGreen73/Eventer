package clientChat;


import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;

import java.lang.reflect.Type;
import java.util.ArrayList;

@Slf4j
public class handler2 extends StompSessionHandlerAdapter {
    private Long testId;
    private StompSession session;
    private ArrayList<SessionListener> listeners;

    public handler2(long testId) {
        listeners = new ArrayList<>();
        this.testId = testId;
    }

    public void addListener(SessionListener listener) {
        listeners.add(listener);
    }

    @Override
    public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
        this.session = session;

//заглушка на в
        System.out.println("Подписался на "+ testId);
        session.subscribe("/topic/greeting" + testId, this);
        session.subscribe("/topic/greeting/eventUpdate", new StompSessionHandlerAdapter() {
            @Override
            public void handleException(StompSession session, StompCommand command, StompHeaders headers, byte[] payload, Throwable exception) {
                exception.printStackTrace();
            }

            @Override
            public Type getPayloadType(StompHeaders headers) {
                return Message.class;
            }

            @Override
            public void handleFrame(StompHeaders headers, Object payload) {
                Commands.update();
            }
        });
        // session.subscribe("/topic/greeting" + 48, this);
        System.out.println("New session: " + session.getSessionId());

        for (SessionListener listener : listeners) {
            listener.wasConnected(this);
        }

    }

    @Override
    public void handleException(StompSession session, StompCommand command, StompHeaders headers, byte[] payload, Throwable exception) {
        exception.printStackTrace();
    }

    @Override
    public Type getPayloadType(StompHeaders headers) {
        return Message.class;
    }

    @Override
    public void handleFrame(StompHeaders headers, Object payload) {
        for (SessionListener listener : listeners) {
            listener.gotMessage((Message) payload);
        }
    }

    public boolean isConnected() {
        return session.isConnected();
    }

    public void send(Message message) {
        session.send("/app/hello", message);
    }

    public void disconnect() {
        session.disconnect();
        for (SessionListener listener : listeners) {
            listener.wasDisconnected();
        }
    }

    interface SessionListener {
        void wasConnected(handler2 handler);

        void gotMessage(Message message);

        void wasDisconnected();
    }
}
