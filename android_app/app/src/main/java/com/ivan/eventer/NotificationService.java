package com.ivan.eventer;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by Dreadnought on 12.05.2018.
 */

public class NotificationService extends Service {

    public NotificationService(){
        super();
    }
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
