package com.iiitd.prince.graphgenerator;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Set;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
    }

    public void setThePref(View view){
        SharedPreferences sharedPreferences = getSharedPreferences("Prefrences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        EditText newnameText = (EditText) findViewById(R.id.PrefEditText);
        String newname = String.valueOf(newnameText.getText());
        editor.putString("username", newname);
        editor.commit();

        Toast.makeText(this, R.string.setted_username, Toast.LENGTH_SHORT).show();
        newnameText.setText("");

    }
}
