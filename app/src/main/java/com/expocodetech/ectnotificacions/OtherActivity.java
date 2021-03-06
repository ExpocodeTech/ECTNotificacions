package com.expocodetech.ectnotificacions;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class OtherActivity extends AppCompatActivity {

    TextView mTvMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other);

        mTvMessage = (TextView) findViewById(R.id.tvMessage);

        Intent intent = getIntent();
        if (intent != null) {
            //Obtenemos el texto del mensaje que viene como Extra en Intent enviado por la Notificacion
            String message = intent.getStringExtra(MainActivity.NOTIF_MESSAGE);

            mTvMessage.setText(message);
            //Instanciamos el NotificationManager para cancelar la Notificacion
            NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            mNotificationManager.cancel(MainActivity.NOTIF_ID);
        }
    }
}
