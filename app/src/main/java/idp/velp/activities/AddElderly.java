package idp.velp.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import com.tokenautocomplete.TokenCompleteTextView;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import idp.velp.R;
import idp.velp.util.TokenCompletionView;

public class AddElderly extends AppCompatActivity {
    private SharedPreferences sharedPreferences;
    private EditText elderlyDOB;
    private EditText elderlySpecialNeeds;
    private TokenCompletionView elderlyLanguages;
    private TokenCompletionView elderlyDialects;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_elderly);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_layout);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Add Elderly");

        //retrieve shared preferences
        sharedPreferences = getSharedPreferences("prefs", Context.MODE_PRIVATE);

        elderlyDOB = (EditText) findViewById(R.id.elderly_dob);
        elderlySpecialNeeds = (EditText) findViewById(R.id.elderly_special_needs);
        elderlyLanguages = (TokenCompletionView) findViewById(R.id.elderly_languages);
        elderlyDialects = (TokenCompletionView) findViewById(R.id.elderly_dialects);

        elderlyDOB.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    showDatePicker();
                }
            }
        });

        elderlyDOB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker();
            }
        });

        elderlySpecialNeeds.setMaxLines(20);
        elderlySpecialNeeds.setHorizontallyScrolling(false);
        elderlySpecialNeeds.setImeOptions(EditorInfo.IME_ACTION_DONE);

        String[] languages = {"English", "Malay", "Mandarin", "Tamil"};

        elderlyLanguages.setOptions(languages);

        //create adapter
        ArrayAdapter<String> languagesAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, languages);
        //disable duplicates
        elderlyLanguages.allowDuplicates(false);
        //set threshold to 0 so option is displayed on view
        elderlyLanguages.setThreshold(0);
        //set delete on token click
        elderlyLanguages.setTokenClickStyle(TokenCompleteTextView.TokenClickStyle.Delete);
        //set adapter
        elderlyLanguages.setAdapter(languagesAdapter);
        //set on click listener
        elderlyLanguages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //show all options
                elderlyLanguages.showDropDown();
            }
        });
        elderlyLanguages.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    //show all options
                    elderlyLanguages.showDropDown();
                }
            }
        });

        String[] dialects = {"Cantonese", "Hakka", "Hokkien", "Teochew"};

        elderlyDialects.setOptions(dialects);

        //create adapter
        ArrayAdapter<String> dialectsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, dialects);
        //disable duplicates
        elderlyDialects.allowDuplicates(false);
        //set threshold to 0 so option is displayed on view
        elderlyDialects.setThreshold(0);
        //set delete on token click
        elderlyDialects.setTokenClickStyle(TokenCompleteTextView.TokenClickStyle.Delete);
        //set adapter
        elderlyDialects.setAdapter(dialectsAdapter);
        //set on click listener
        elderlyDialects.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //show all options
                elderlyDialects.showDropDown();
            }
        });
        elderlyDialects.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    //show all options
                    elderlyDialects.showDropDown();
                }
            }
        });
    }

    public void showDatePicker() {
        Calendar cal = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog();
        datePickerDialog.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
                //date = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;

                //set date
                elderlyDOB.setText(dayOfMonth + " " + new DateFormatSymbols().getShortMonths()[monthOfYear] + " " + year);

            }
        });
        datePickerDialog.show(getFragmentManager(), "");
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
                editor.putBoolean("task1", true);
                editor.commit();
                Intent intent2 = new Intent(this, CaregiverProfile.class);
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
