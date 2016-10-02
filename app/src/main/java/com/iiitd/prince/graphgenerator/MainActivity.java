package com.iiitd.prince.graphgenerator;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Button examplebutton = null;
    private Button sqlbutton = null;
    private static final int REQUEST_EXTERNAL_STORAGE = 1;


    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    //persmission method.
    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have read or write permission
        int writePermission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int readPermission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE);

        if (writePermission != PackageManager.PERMISSION_GRANTED || readPermission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        verifyStoragePermissions(this);
        SharedPreferences sharedPreferences = getSharedPreferences("Prefrences", Context.MODE_PRIVATE);
        boolean usernameexists = sharedPreferences.contains("username");

        TextView textView = (TextView) findViewById(R.id.PrefName);

        if(usernameexists == false){
            textView.setText("Hello ! Dummy User");
        }
        else{
            String username = sharedPreferences.getString("username","");
            textView.setText("Hello ! " + username);
        }

        examplebutton = (Button) findViewById(R.id.exampleButton);
        sqlbutton = (Button) findViewById(R.id.sqlButton);

        examplebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ExampleActivity.class);
                startActivity(intent);
            }
        });

        sqlbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SQLViewActivity.class);
                startActivity(intent);
            }
        });





    }

    @Override
    protected void onRestart() {
        super.onRestart();

        SharedPreferences sharedPreferences = getSharedPreferences("Prefrences", Context.MODE_PRIVATE);
        boolean usernameexists = sharedPreferences.contains("username");

        TextView textView = (TextView) findViewById(R.id.PrefName);

        if(usernameexists == false){
            textView.setText("Hello ! Dummy User");
        }
        else{
            String username = sharedPreferences.getString("username","");
            textView.setText("Hello ! "+username);
        }

    }
    @Override
    protected void onResume(){
        super.onResume();
        SharedPreferences sharedPreferences = getSharedPreferences("Prefrences", Context.MODE_PRIVATE);
        boolean usernameexists = sharedPreferences.contains("username");

        TextView textView = (TextView) findViewById(R.id.PrefName);

        if(usernameexists == false){
            textView.setText("Hello ! Dummy User");
        }
        else{
            String username = sharedPreferences.getString("username","");
            textView.setText("Hello ! "+username);
        }



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
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
}
