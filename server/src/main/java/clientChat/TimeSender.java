package clientChat;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;

@Component
@Slf4j
public class TimeSender {
    private static final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("HH:mm:ss");
    @Autowired
    private SimpMessagingTemplate broker;
   // private Logger log;

    @Autowired
    public TimeSender(final SimpMessagingTemplate broker) {
        this.broker = broker;
    }

}