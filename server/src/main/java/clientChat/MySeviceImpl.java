package clientChat;

import org.springframework.stereotype.Service;

/**
 * Created by user on 21.04.2018.
 */
@Service
public class MySeviceImpl implements MySevice {
    public String getString(String var) {
        return var.toUpperCase();
    }
}
