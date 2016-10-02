package idp.velp.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import idp.velp.R;
import idp.velp.util.CircleTransform;

public class Profile extends AppCompatActivity {
    private SharedPreferences sharedPreferences;
    private boolean task1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_layout);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Janice Tan");

        //retrieve shared preferences
        sharedPreferences = getSharedPreferences("prefs", Context.MODE_PRIVATE);
        task1 = sharedPreferences.getBoolean("task1", false);

        TextView task1EmptyState = (TextView) findViewById(R.id.task1_empty_state);
        LinearLayout task1SuccessState = (LinearLayout) findViewById(R.id.task1_success_state);

        if (task1) {
            task1EmptyState.setVisibility(View.GONE);
            task1SuccessState.setVisibility(View.VISIBLE);
            ImageView elderlyPic = (ImageView) findViewById(R.id.elderly_pic);
            Picasso.with(this).load(R.drawable.elderly).transform(new CircleTransform()).into(elderlyPic);
        }

    }

    @Override
    public void onBackPressed() {
        if (task1) {
            Intent intent2 = new Intent(this, Home.class);
            intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent2);
            finish();
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_home, menu);
        MenuItem menuItem = menu.findItem(R.id.action_profile);
        menuItem.setVisible(false);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.action_settings:
                return true;
            case R.id.action_logout:
                Intent intent2 = new Intent(this, Login.class);
                intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent2);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void goToElderlyProfile(View view) {
        Intent intent = new Intent(this, ElderlyProfile.class);
        startActivity(intent);
    }


    public void goToAddElderlyProfile(View view) {
        Intent intent = new Intent(this, AddElderly.class);
        startActivity(intent);
    }
}
