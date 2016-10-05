package idp.velp.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import idp.velp.R;

public class Login extends AppCompatActivity {

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(ContextCompat.getColor(Login.this, R.color.colorPrimary));
        }

        //retrieve shared preferences
        sharedPreferences = getSharedPreferences("prefs", Context.MODE_PRIVATE);
    }

    public void login(View view) {
        EditText emailEditText = (EditText) findViewById(R.id.username);
        String email = emailEditText.getText().toString().trim();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if (email.equals("jerry@gmail.com")) {
            editor.putBoolean("volunteer", true);
        } else {
            editor.putBoolean("volunteer", false);
        }
        editor.commit();
        Intent intent = new Intent(this, Home.class);
        startActivity(intent);
        finish();
    }
}
