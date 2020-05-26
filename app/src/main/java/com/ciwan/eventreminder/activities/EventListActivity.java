package com.ciwan.eventreminder.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.ciwan.events.adapters.EventAdapter;
import com.ciwan.events.databases.EventDatabase;
import com.ciwan.events.models.Event;
import com.ciwan.events.utils.CurrentEventUtil;
import com.getbase.floatingactionbutton.FloatingActionButton;

import app.ciwan.events.R;


public class EventListActivity extends AppCompatActivity {

    private Spinner sp_date;
    RecyclerView rv_event_list;
    FloatingActionButton fla_addEvent;

    private EventDatabase eventDatabase;

    public static EventAdapter eventAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_list);

        sp_date = findViewById(R.id.sp_date);
        rv_event_list = (RecyclerView) findViewById(R.id.rv_event_list);
        fla_addEvent = findViewById(R.id.fla_addEvent);


        final ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.dates, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_date.setAdapter(adapter);



        eventDatabase = new EventDatabase(this);
        eventAdapter = new EventAdapter(this, eventDatabase.getAllEvents());
        rv_event_list.setAdapter(eventAdapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rv_event_list.setLayoutManager(linearLayoutManager);


        fla_addEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CurrentEventUtil.event = null;
                Intent intent = new Intent(v.getContext(), EventDetailActivity.class);
                startActivity(intent);
            }
        });

        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT | ItemTouchHelper.DOWN | ItemTouchHelper.UP) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                Toast.makeText(EventListActivity.this, "on Move", Toast.LENGTH_SHORT).show();
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                Toast.makeText(EventListActivity.this, "Deleted!", Toast.LENGTH_SHORT).show();
                //Remove swiped item from list and notify the RecyclerView
                int position = viewHolder.getAdapterPosition();
                eventDatabase.deleteEvent(eventAdapter.getEventInPosition(position));
                eventAdapter.deleteItem(position);
            }
        };


        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(rv_event_list);


    }


    public static void addEvent(Event event){
        eventAdapter.addItem(event);
    }


}
