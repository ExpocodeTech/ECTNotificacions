package com.expocodetech.ectnotificacions;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getName();

    public static final int NOTIF_ID = 1001;
    public static final String NOTIF_MESSAGE = "NOTIF_MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Instanciamos el Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Instanciamos el FloatingActionButton
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflamos el menu
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        } else if (id == R.id.action_sendnotif) {
            sendNotification();
        }

        return super.onOptionsItemSelected(item);
    }

    private void sendNotification() {
        //Preparamos la Notificación com un icono, titulo, texto, prioridad, color led, sonido y persistencia
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.alert_circle)
                        .setContentTitle(getString(R.string.notif_title))
                        .setContentText(getString(R.string.notif_body))
                        .setPriority(NotificationCompat.PRIORITY_MAX)
                        .setLights(Color.CYAN, 1, 0)
                        .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                        .setOngoing(true);

        // Creamos un Intent explicito el cual disparará el OtherActivity de nuestra app
        Intent resultIntent = new Intent(this, OtherActivity.class);
        //Agreamos un Extra con un mensaje
        resultIntent.putExtra(NOTIF_MESSAGE, getString(R.string.notif_body_intent));

        // Creamos un Stack ficticio para la Actividad que iniciaremos, de manear que cuando el
        // usuario haga click sobre la notificación, con esto nos aseguramos que una vez que el
        // usuario navegue a la actividad desde la Notificacion y presione el boton back, la app
        // navegue a la pantalla de la app, en vez de salirse de la misma.
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);

        // Agregamos el  back stack para el Intent, pero no el Intent como tal
        stackBuilder.addParentStack(OtherActivity.class);

        // Agregamos el  Intent que inicia el Activity al inicio del stack
        stackBuilder.addNextIntent(resultIntent);

        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT );
        mBuilder.setContentIntent(resultPendingIntent);

        //Obtenemos una instancia del NotificationManager
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        //Usando el método notify del NotificationManager, enviamos la notificacion asociandola
        // a un ID con elCual podamos actualizarla en caso de que sea necesario
        mNotificationManager.notify(NOTIF_ID, mBuilder.build());
    }
}
