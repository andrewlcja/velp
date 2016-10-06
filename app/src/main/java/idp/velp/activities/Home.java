package idp.velp.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.squareup.picasso.Picasso;

import idp.velp.R;
import idp.velp.util.CircleTransform;

public class Home extends AppCompatActivity {
    private SharedPreferences sharedPreferences;
    private boolean task1;
    private boolean task2;
    private boolean task4;
    private boolean task5;
    private boolean task6a;
    private boolean task6b;
    private boolean task7;
    private boolean task8;
    private boolean task9;
    private boolean task10;
    private boolean task11;
    private boolean task11b;
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
        task6a = sharedPreferences.getBoolean("task6a", false);
        task6b = sharedPreferences.getBoolean("task6b", false);
        task7 = sharedPreferences.getBoolean("task7", false);
        task8 = sharedPreferences.getBoolean("task8", false);
        task9 = sharedPreferences.getBoolean("task9", false);
        task10 = sharedPreferences.getBoolean("task10", false);
        task11 = sharedPreferences.getBoolean("task11", false);
        task11b = sharedPreferences.getBoolean("task11b", false);
        volunteer = sharedPreferences.getBoolean("volunteer", false);

        FloatingActionButton fabAdd = (FloatingActionButton) findViewById(R.id.fab_add);
        FloatingActionButton fabSearch = (FloatingActionButton) findViewById(R.id.fab_search);

        final CardView task2SuccessState = (CardView) findViewById(R.id.task2_success_state);
        final CardView task8SuccessState = (CardView) findViewById(R.id.task8_success_state);
        final LinearLayout task2EmptyState = (LinearLayout) findViewById(R.id.task2_empty_state);
        final TextView task2EmptyStateName = (TextView) findViewById(R.id.task2_empty_state_name);
        ImageView task4SuccessStateIcon = (ImageView) findViewById(R.id.task4_success_state_icon);
        ImageView task9SuccessStateIcon = (ImageView) findViewById(R.id.task9_success_state_icon);
        TextView requestCapacity = (TextView) findViewById(R.id.request_capacity);
        TextView elderlyName = (TextView) findViewById(R.id.elderly_name);
        TextView task9ElderlyName = (TextView) findViewById(R.id.task9_elderly_name);


        TextView recurringRequestCapacity = (TextView) findViewById(R.id.recurring_request_capacity);

        ImageView requestStatusIcon = (ImageView) findViewById(R.id.request_status_icon);

        if (volunteer) {
            task2EmptyStateName.setText("Hi Jerry!");
            task4SuccessStateIcon.setVisibility(View.GONE);
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

            if (task6b) {
                task2SuccessState.setVisibility(View.GONE);
                task2EmptyState.setVisibility(View.VISIBLE);
            }

            if (task9) {
                task2EmptyState.setVisibility(View.GONE);
                task8SuccessState.setVisibility(View.VISIBLE);
                task9ElderlyName.setText("Pending approval");

                task8SuccessState.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Home.this, ViewSearchRecurringRequest.class);
                        startActivity(intent);
                    }
                });
            }

            if (task10){
                recurringRequestCapacity.setText("2/2");
                task9ElderlyName.setText("Tan Siew Mei");

                task8SuccessState.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Home.this, ViewVolunteeredRecurringRequest.class);
                        startActivity(intent);
                    }
                });
            }

            if (task11 && !task11b) {
                final View customView = getLayoutInflater().inflate(R.layout.dialog_edit, null);
                ImageView elderlyPic = (ImageView) customView.findViewById(R.id.elderly_pic);

                Picasso.with(this).load(R.drawable.elderly).transform(new CircleTransform()).into(elderlyPic);
                Thread thread = new Thread() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                        }

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                MaterialDialog dialog = new MaterialDialog.Builder(Home.this)
                                        .customView(customView, true)
                                        .title("Request Updated")
                                        .positiveText("Yes")
                                        .negativeText("No")
                                        .cancelable(false)
                                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                                            @Override
                                            public void onClick(MaterialDialog dialog, DialogAction which) {
                                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                                editor.putBoolean("task11b", true);
                                                editor.commit();
                                                Toast.makeText(Home.this, "Response submitted", Toast.LENGTH_SHORT).show();
                                            }
                                        })
                                        .show();
                            }
                        });
                    }
                };
                thread.start();

            }

        } else {
            //caregiver
            fabAdd.setVisibility(View.VISIBLE);
            fabSearch.setVisibility(View.GONE);

            if (!task7) {
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
                    task4SuccessStateIcon.setVisibility(View.VISIBLE);
                }

                if (task5) {
                    requestCapacity.setText("1/1");
                    task4SuccessStateIcon.setVisibility(View.GONE);
                }

                if (task6b) {
                    final View customView = getLayoutInflater().inflate(R.layout.dialog_feedback, null);
                    ImageView elderlyPic = (ImageView) customView.findViewById(R.id.elderly_pic);

                    LinearLayout task6bEmptyState = (LinearLayout) customView.findViewById(R.id.task6b_empty_state);
                    TextInputLayout task6bSuccessState = (TextInputLayout) customView.findViewById(R.id.task6b_success_state);
                    TextView dialogName = (TextView) customView.findViewById(R.id.dialog_name);
                    TextView dialogGender = (TextView) customView.findViewById(R.id.dialog_gender);
                    TextView dialogAge = (TextView) customView.findViewById(R.id.dialog_age);
                    EditText dialogComments = (EditText) customView.findViewById(R.id.dialog_comments);

                    task6bEmptyState.setVisibility(View.GONE);
                    task6bSuccessState.setVisibility(View.VISIBLE);

                    dialogName.setText("Jerry Yan");
                    dialogGender.setText("Male");
                    dialogAge.setText("22 years old");

                    dialogComments.setMaxLines(20);
                    dialogComments.setHorizontallyScrolling(false);
                    dialogComments.setImeOptions(EditorInfo.IME_ACTION_DONE);


                    Picasso.with(this).load(R.drawable.volunteer).transform(new CircleTransform()).into(elderlyPic);
                    Thread thread = new Thread() {
                        @Override
                        public void run() {
                            try {
                                Thread.sleep(500);
                            } catch (InterruptedException e) {
                            }

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    MaterialDialog dialog = new MaterialDialog.Builder(Home.this)
                                            .customView(customView, true)
                                            .title("Feedback")
                                            .positiveText("Submit")
                                            .cancelable(false)
                                            .onPositive(new MaterialDialog.SingleButtonCallback() {
                                                @Override
                                                public void onClick(MaterialDialog dialog, DialogAction which) {
                                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                                    editor.putBoolean("task7", true);
                                                    editor.commit();
                                                    Toast.makeText(Home.this, "Request ended", Toast.LENGTH_SHORT).show();
                                                    task2EmptyStateName.setText("Hi Janice!");
                                                    task2EmptyState.setVisibility(View.VISIBLE);
                                                    task2SuccessState.setVisibility(View.GONE);
                                                }
                                            })
                                            .show();
                                }
                            });
                        }
                    };
                    thread.start();

                }
            }

            if (task8) {
                task2EmptyState.setVisibility(View.GONE);
                task8SuccessState.setVisibility(View.VISIBLE);
            }

            if (task9) {
                task9SuccessStateIcon.setVisibility(View.VISIBLE);
            }

            if (task10){
                recurringRequestCapacity.setText("2/2");
                task9SuccessStateIcon.setVisibility(View.GONE);
            }
        }

        if (task6a) {
            requestStatusIcon.setVisibility(View.VISIBLE);
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

    public void goToViewRecurringRequest(View view) {
        Intent intent = new Intent(this, ViewRecurringRequest.class);
        startActivity(intent);
    }


    public void goToSearch(View view) {
        Intent intent = new Intent(this, Search.class);
        startActivity(intent);
    }

}
