package idp.velp.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import idp.velp.R;
import idp.velp.util.CircleTransform;

public class Home extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    private boolean task2;
    private boolean task1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_layout);
        setSupportActionBar(toolbar);


        //retrieve shared preferences
        sharedPreferences = getSharedPreferences("prefs", Context.MODE_PRIVATE);
        task1 = sharedPreferences.getBoolean("task1", false);
        task2 = sharedPreferences.getBoolean("task2", false);

        CardView task2SuccessState = (CardView) findViewById(R.id.task2_success_state);
        LinearLayout task2EmptyState = (LinearLayout) findViewById(R.id.task2_empty_state);

        if (task2) {
            task2EmptyState.setVisibility(View.GONE);
            task2SuccessState.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_home, menu);

        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_profile:
                Intent intent = new Intent(this, Profile.class);
                startActivity(intent);
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

    public void goToCreateRequest(View view) {
        if (task1) {
            Intent intent = new Intent(this, CreateRequest.class);
            startActivity(intent);
        } else {
            Toast.makeText(this, "You need to have at least one elderly to create a request!", Toast.LENGTH_SHORT).show();
        }
    }

}
