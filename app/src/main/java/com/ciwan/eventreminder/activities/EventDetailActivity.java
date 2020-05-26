package com.ciwan.eventreminder.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ciwan.events.databases.EventDatabase;
import com.ciwan.events.models.Event;
import com.ciwan.events.utils.CurrentEventUtil;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.util.Calendar;

import app.ciwan.events.R;


public class EventDetailActivity extends AppCompatActivity  implements TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener {

    private Button btn_save;
    private Button btn_reminders;
    private Button btn_start_date;
    private Button btn_start_date_time;
    private Button btn_end_date;
    private Button btn_end_date_time;

    private EventDatabase eventDatabase;

    private EditText tv_name;
    private EditText tv_type;
    private EditText tv_detail;
    private EditText et_start_date;
    private EditText et_start_date_time;
    private EditText et_end_date;
    private EditText et_end_date_time;
    private EditText et_rf;
    private EditText et_address;

    private int mYear, mMonth, mHour, mMinute, mDay;
    private String mTime;
    private String mDate;

    private Context context;

    private int eventId = -21;

    int selectedDate = 0;
    int selectedTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detail);
        context = this;
        eventDatabase = new EventDatabase(this);

        btn_save = findViewById(R.id.btn_save);
        btn_reminders = findViewById(R.id.btn_reminders);
        btn_start_date = findViewById(R.id.btn_start_date);
        btn_start_date_time = findViewById(R.id.btn_start_date_time);
        btn_end_date = findViewById(R.id.btn_end_date);
        btn_end_date_time = findViewById(R.id.btn_end_date_time);

        tv_name = findViewById(R.id.tv_name);
        tv_type = findViewById(R.id.tv_type);
        tv_detail = findViewById(R.id.tv_detail);
        et_start_date = findViewById(R.id.et_start_date);
        et_start_date_time = findViewById(R.id.et_start_date_time);
        et_end_date = findViewById(R.id.et_end_date);
        et_end_date_time = findViewById(R.id.et_end_date_time);
        et_rf = findViewById(R.id.et_rf);
        et_address = findViewById(R.id.et_address);

        Event temp = CurrentEventUtil.event;

        if (CurrentEventUtil.event != null){
            tv_name.setText(temp.getName());
            tv_type.setText(temp.getEventType());
            tv_detail.setText(temp.getDetail());
            String[] arrOfStart = temp.getStartDate().split("&&", 2);
            String[] arrOfEnd = temp.getEndDate().split("&&", 2);

            et_start_date.setText(arrOfStart[0]);
            et_start_date_time.setText(arrOfStart[1]);

            et_end_date.setText(arrOfEnd[0]);
            et_end_date_time.setText(arrOfEnd[1]);

            et_rf.setText(temp.getRepeatFreq());
            et_address.setText(temp.getAddress());

            eventId = temp.getId();

        }


        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Event event = new Event();
                event.setName(tv_name.getText().toString());
                event.setEventType(tv_type.getText().toString());
                event.setDetail(tv_detail.getText().toString());
                event.setStartDate(et_start_date.getText().toString()+"&&"+et_start_date_time.getText().toString());
                event.setEndDate(et_end_date.getText().toString()+"&&"+et_end_date_time.getText().toString());
                event.setAddress(et_address.getText().toString());
                event.setRepeatFreq(et_rf.getText().toString());

                if (CurrentEventUtil.event != null){
                    event.setId(CurrentEventUtil.event.getId());
                    eventDatabase.updateEvent(event);
                }
                else {
                    eventId = eventDatabase.addEvent(event);
                    EventListActivity.addEvent(event);
                }

                Toast.makeText(context, "Saved!", Toast.LENGTH_SHORT).show();

            }
        });


        btn_reminders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (eventId != -21){
                    Intent intent=new Intent(context, ReminderListActivity.class);
                    intent.putExtra("EVENT_ID", eventId);
                    startActivity(intent);
                }

                else {
                    Toast.makeText(context, "Please first save this event", Toast.LENGTH_SHORT).show();

                }
            }
        });

        btn_start_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedDate = 0;
                setDate();
            }
        });

        btn_start_date_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedTime = 0;
                setTime();
            }
        });


        btn_end_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedDate = 1;
                setDate();
            }
        });

        btn_end_date_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedTime = 1;
                setTime();
            }
        });



    }


    // On clicking Time picker
    public void setTime() {
        Calendar now = Calendar.getInstance();
        TimePickerDialog tpd = TimePickerDialog.newInstance(this, now.get(Calendar.HOUR_OF_DAY), now.get(Calendar.MINUTE), false);
        tpd.setThemeDark(false);
        tpd.show(getFragmentManager(), "Timepickerdialog");
    }

    // On clicking Date picker
    public void setDate() {
        Calendar now = Calendar.getInstance();
        DatePickerDialog dpd = DatePickerDialog.newInstance(this, now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH));
        dpd.show(getFragmentManager(), "Datepickerdialog");
    }

    // Obtain time from time picker
    @Override
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute) {
        mHour = hourOfDay;
        mMinute = minute;
        if (minute < 10) {
            mTime = hourOfDay + ":" + "0" + minute;
        } else {
            mTime = hourOfDay + ":" + minute;
        }

        if (selectedTime == 0)
            et_start_date_time.setText(mTime);
        else
            et_end_date_time.setText(mTime);
    }

    // Obtain date from date picker
    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        monthOfYear++;
        mDay = dayOfMonth;
        mMonth = monthOfYear;
        mYear = year;
        mDate = dayOfMonth + "/" + monthOfYear + "/" + year;

        if (selectedDate == 0)
            et_start_date.setText(mDate);
        else
            et_end_date.setText(mDate);

    }
}
