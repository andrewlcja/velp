package idp.velp.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import idp.velp.R;

public class EditRequest extends AppCompatActivity {
    private SharedPreferences sharedPreferences;
    private EditText repeatText;
    private EditText requestElderly;
    private EditText requestType;
    private EditText requestDate;
    private EditText requestStartTime;
    private EditText requestEndTime;
    private EditText requestUntil;
    private EditText requestAdditionalInfo;
    private TextView requestNumber;
    private TextView requestNumberLabel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_request);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_layout);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Edit Request");
        //retrieve shared preferences
        sharedPreferences = getSharedPreferences("prefs", Context.MODE_PRIVATE);

        repeatText = (EditText) findViewById(R.id.request_repeat);
        requestElderly = (EditText) findViewById(R.id.request_elderly);
        requestType = (EditText) findViewById(R.id.request_type);
        requestDate = (EditText) findViewById(R.id.request_date);
        requestStartTime = (EditText) findViewById(R.id.request_start_time);
        requestEndTime = (EditText) findViewById(R.id.request_end_time);
        requestUntil = (EditText) findViewById(R.id.request_until);
        requestAdditionalInfo = (EditText) findViewById(R.id.request_addtional_information);
        requestNumber = (TextView) findViewById(R.id.request_number);
        requestNumberLabel = (TextView) findViewById(R.id.request_number_label);

        repeatText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    showRepeatOptions(v);
                }
            }
        });

        requestElderly.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    showElderlyOptions();
                }
            }
        });

        requestElderly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showElderlyOptions();
            }
        });

        requestType.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    showTypeOptions();
                }
            }
        });

        requestType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTypeOptions();
            }
        });

        requestStartTime.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    showTimePicker(v);
                }
            }
        });

        requestEndTime.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    showTimePicker(v);
                }
            }
        });

        requestUntil.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    showDatePicker(v);
                }
            }
        });

        requestDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    showDatePicker(v);
                }
            }
        });

        requestAdditionalInfo.setMaxLines(20);
        requestAdditionalInfo.setHorizontallyScrolling(false);
        requestAdditionalInfo.setImeOptions(EditorInfo.IME_ACTION_DONE);


        //remove focus
        RelativeLayout editRequestLayout = (RelativeLayout) findViewById(R.id.edit_request_layout);
        if (editRequestLayout.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        }
    }

    public void showDatePicker(View view) {
        final View customView = view;
        Calendar cal = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog();
        datePickerDialog.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
                //date = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;

                //set date
                switch(customView.getId()) {
                    case R.id.request_date:
                        requestDate.setText(dayOfMonth + " " + new DateFormatSymbols().getShortMonths()[monthOfYear] + " " + year);
                        break;
                    case R.id.request_until:
                        requestUntil.setText(dayOfMonth + " " + new DateFormatSymbols().getShortMonths()[monthOfYear] + " " + year);
                        break;
                }

            }
        });
        datePickerDialog.show(getFragmentManager(), "");
    }

    public void increase(View view) {
        int num = Integer.parseInt(requestNumber.getText().toString());
        requestNumber.setText(++num + "");

        if (num > 1) {
            requestNumberLabel.setText(" volunteers");
        }
    }

    public void decrease(View view) {
        int num = Integer.parseInt(requestNumber.getText().toString());

        if (num > 1) {
            requestNumber.setText(--num + "");
        }

        if (num == 1) {
            requestNumberLabel.setText(" volunteer");
        }
    }

    public void showTimePicker(View view) {
        final View customView = view;
        TimePickerDialog startTimePickerDialog = TimePickerDialog.newInstance(new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute, int second) {
                try {
                    //startTime = hourOfDay + ":" + minute;

                    //date formats
                    SimpleDateFormat sdf = new SimpleDateFormat("H:mm");
                    SimpleDateFormat dateFormat = new SimpleDateFormat("h:mma");
                    Date time = sdf.parse(hourOfDay + ":" + minute);

                    switch (customView.getId()) {
                        case R.id.request_start_time:
                            requestStartTime.setText(dateFormat.format(time).replace(".", ""));
                            break;
                        case R.id.request_end_time:
                            requestEndTime.setText(dateFormat.format(time).replace(".", ""));
                            break;
                    }

                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }, 7, 0, false);

        //min start time is 7am, max start time is 11pm
        startTimePickerDialog.setMinTime(7, 0, 0);
        startTimePickerDialog.setMaxTime(23, 0, 0);

        //only allow interval of 30 mins
        startTimePickerDialog.setTimeInterval(1, 30);
        startTimePickerDialog.show(getFragmentManager(), "");
    }


    public void showRepeatOptions(View view) {
        String[] options = {"None", "Every Week", "Every Month"};

        new MaterialDialog.Builder(this)
                .items(options)
                .widgetColor(ContextCompat.getColor(this, R.color.colorPrimary))
                .itemsCallback(new MaterialDialog.ListCallback() {
                    @Override
                    public void onSelection(MaterialDialog dialog, View itemView, int which, CharSequence text) {
                        repeatText.setText(text.toString());
                    }
                })
                .show();
    }

    public void showElderlyOptions() {
        String[] options = {"Tan Siew Mei"};

        new MaterialDialog.Builder(this)
                .items(options)
                .widgetColor(ContextCompat.getColor(this, R.color.colorPrimary))
                .itemsCallback(new MaterialDialog.ListCallback() {
                    @Override
                    public void onSelection(MaterialDialog dialog, View itemView, int which, CharSequence text) {
                        requestElderly.setText(text.toString());
                    }
                })
                .show();
    }

    public void showTypeOptions() {
        String[] options = {"Grocery Shopping", "Medical Appointment", "Miscellaneous"};

        new MaterialDialog.Builder(this)
                .items(options)
                .widgetColor(ContextCompat.getColor(this, R.color.colorPrimary))
                .itemsCallback(new MaterialDialog.ListCallback() {
                    @Override
                    public void onSelection(MaterialDialog dialog, View itemView, int which, CharSequence text) {
                        requestType.setText(text.toString());
                    }
                })
                .show();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_add, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.action_settings:
                return true;

            case R.id.action_submit:
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("task11", true);
                editor.commit();
                Intent intent2 = new Intent(this, ViewRecurringRequest.class);
                intent2.putExtra("fromEdit", true);
                intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent2);
                finish();
                return true;
            case R.id.action_logout:
                Intent intent3 = new Intent(this, Login.class);
                intent3.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent3);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
