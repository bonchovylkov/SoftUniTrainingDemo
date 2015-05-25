package com.homeassignment.softunitrainingdemo;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class MainActivity extends ActionBarActivity implements View.OnClickListener{

    String LOG_DEBUG="LOG_DEBUG";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button  btnImplicitIntent = (Button)findViewById(R.id.btnImplicitIntent);
        btnImplicitIntent.setOnClickListener(this);
        Button  btnExplicitIntent = (Button)findViewById(R.id.btnExplicitIntent);
        btnExplicitIntent.setOnClickListener(this);
        Button  btnStartSimpleListView = (Button)findViewById(R.id.btnStartSimpleListView);
        btnStartSimpleListView.setOnClickListener(this);

        Button  btnStartCustomListView = (Button)findViewById(R.id.btnStartCustomListView);
        btnStartCustomListView.setOnClickListener(this);

        Button  btnStartNotification = (Button)findViewById(R.id.btnStartNotification);
        btnStartNotification.setOnClickListener(this);

        Button  btnStartGame = (Button)findViewById(R.id.btnStartGame);
        btnStartGame.setOnClickListener(this);



    }




    @Override
    protected void onStart() {
        super.onStart();
        Log.d("LOG_DEBUG", "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("LOG_DEBUG", "onResume");
    }

    @Override
    protected void onPause() {
        Log.d("LOG_DEBUG", "onPause");
        super.onPause();
    }

    @Override
    protected void onStop() {
        Log.d("LOG_DEBUG", "onStop");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.d("LOG_DEBUG", "onDestroy");
        super.onDestroy();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

//    public void btnImplicitIntent_OnClick(View view) {
//        Intent intent = new Intent (Intent.ACTION_VIEW, Uri.parse("http://www.softuni.bg "));
//        startActivity(intent);
//
//    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnImplicitIntent:
                Intent intent = new Intent (Intent.ACTION_VIEW, Uri.parse("http://www.softuni.bg "));
                startActivity(intent);
                break;
            case R.id.btnExplicitIntent:
                Intent exlplicitIntent = new Intent (this,SecondActivity.class);
                exlplicitIntent.putExtra("data","i have been passed");
                startActivity(exlplicitIntent);
                break;
            case R.id.btnStartSimpleListView:
                Intent startSimpleActivity = new Intent (this,SimpleListAdapter.class);

                startActivity(startSimpleActivity);
                break;

            case R.id.btnStartCustomListView:
                Intent startCustmActivity = new Intent (this,CustomListAdapter.class);

                startActivity(startCustmActivity);
                break;

            case R.id.btnStartGame:
                Intent startGame = new Intent (this,TickTackToe.class);

                startActivity(startGame);
                break;



            case R.id.btnStartNotification:

                Intent openGoogle = new Intent(Intent.ACTION_VIEW,Uri.parse("http://www.softuni.bg "));
                PendingIntent pendingIntent = PendingIntent.getActivity(this,0,openGoogle,0);

          Bitmap largeIcon =    ((BitmapDrawable)getResources().getDrawable(R.drawable.softuni)).getBitmap();
                NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.softuni)
                        .setLargeIcon(largeIcon)
                        .setContentTitle("My notification")
                        .setContentText("Open soft uni!");

                mBuilder.setContentIntent(pendingIntent);
                NotificationManager mNotificationManager =
                        (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

                mNotificationManager.notify(1, mBuilder.build());

                break;


        }
    }
}
