package com.ivan.eventer.view.Fon;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.ivan.eventer.R;
import com.ivan.eventer.controller.StartActivity;

public class FonActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fon);

        new Handler().postDelayed(() -> {

            Intent startApp = new Intent(FonActivity.this, StartActivity.class);
            startActivity(startApp);
            finish();

        }, 2 * 550);

    }

}
