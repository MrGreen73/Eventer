package clientChat;

import clientChat.EventApp;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by user on 22.04.2018.
 */
public interface EventsRepository extends CrudRepository<EventApp, Long> {
}
