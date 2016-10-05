package idp.velp.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import idp.velp.R;

public class Home extends AppCompatActivity {
    private SharedPreferences sharedPreferences;
    private boolean task1;
    private boolean task2;
    private boolean task4;
    private boolean task5;
    private boolean volunteer;

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
        task4 = sharedPreferences.getBoolean("task4", false);
        task5 = sharedPreferences.getBoolean("task5", false);
        volunteer = sharedPreferences.getBoolean("volunteer", false);

        FloatingActionButton fabAdd = (FloatingActionButton) findViewById(R.id.fab_add);
        FloatingActionButton fabSearch = (FloatingActionButton) findViewById(R.id.fab_search);

        CardView task2SuccessState = (CardView) findViewById(R.id.task2_success_state);
        LinearLayout task2EmptyState = (LinearLayout) findViewById(R.id.task2_empty_state);
        TextView task2EmptyStateName = (TextView) findViewById(R.id.task2_empty_state_name);
        TextView requestCapacity = (TextView) findViewById(R.id.request_capacity);
        TextView elderlyName = (TextView) findViewById(R.id.elderly_name);

        if (volunteer) {
            fabAdd.setVisibility(View.GONE);
            fabSearch.setVisibility(View.VISIBLE);

            if (task4) {
                task2EmptyState.setVisibility(View.GONE);
                task2SuccessState.setVisibility(View.VISIBLE);
                elderlyName.setText("Pending approval");

                task2SuccessState.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Home.this, ViewSearchRequest.class);
                        startActivity(intent);
                    }
                });
            } else {
                task2EmptyState.setVisibility(View.VISIBLE);
                task2EmptyStateName.setText("Hi Jerry!");
            }

            if (task5) {
                requestCapacity.setText("1/1");
                elderlyName.setText("Tan Siew Mei");

                task2SuccessState.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Home.this, ViewVolunteeredRequest.class);
                        startActivity(intent);
                    }
                });
            }
        } else {
            //caregiver
            fabAdd.setVisibility(View.VISIBLE);
            fabSearch.setVisibility(View.GONE);

            if (task2) {
                task2EmptyState.setVisibility(View.GONE);
                task2SuccessState.setVisibility(View.VISIBLE);
            }

            if (task4) {
                task2SuccessState.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Home.this, ViewRequest.class);
                        startActivity(intent);
                    }
                });
            }

            if (task5) {
                requestCapacity.setText("1/1");
            }
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
                Intent intent = new Intent(this, CaregiverProfile.class);

                if (volunteer) {
                    intent = new Intent(this, VolunteerProfile.class);
                }

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


    public void goToViewRequest(View view) {
        Intent intent = new Intent(this, ViewRequest.class);
        startActivity(intent);
    }


    public void goToSearch(View view) {
        Intent intent = new Intent(this, Search.class);
        startActivity(intent);
    }

}
