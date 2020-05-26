package com.ciwan.eventreminder.adapters;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ciwan.events.activities.EventDetailActivity;
import com.ciwan.events.models.Event;
import com.ciwan.events.utils.CurrentEventUtil;

import java.util.ArrayList;

import app.ciwan.events.R;


public class EventAdapter extends RecyclerView.Adapter<EventAdapter.MyViewHolder> {

    ArrayList<Event> events;
    LayoutInflater inflater;
    private Context context;

    public EventAdapter(Context context, ArrayList<Event> events) {
        inflater = LayoutInflater.from(context);
        this.events = events;
        this.context = context;
    }

    public void addItem(Event event){
        events.add(event);
        notifyDataSetChanged();
    }

    public void deleteItem(int position){
        events.remove(position);
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.event_item, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Event selectedProduct = events.get(position);
        holder.setData(selectedProduct, position);

    }

    @Override
    public int getItemCount() {
        return events.size();
    }


    public Event getEventInPosition(int position){
        return events.get(position);
    }


    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView eventName, eventType;


        int position;


        public MyViewHolder(View itemView) {
            super(itemView);
            eventName = (TextView) itemView.findViewById(R.id.tv_event_name);
            eventType = (TextView) itemView.findViewById(R.id.tv_event_type);

            itemView.setOnClickListener(this);

        }

        public void setData(Event event, int position) {

            this.eventName.setText(event.getName());
            this.eventType.setText(event.getEventType());

//            this.password.setText(event.getPassword());
            this.position=position;


        }

        @Override
        public void onClick(View v) {
            int position = getLayoutPosition(); // gets item position
            CurrentEventUtil.event = events.get(position);
            Intent intent = new Intent(context, EventDetailActivity.class);
            context.startActivity(intent);
        }


    }

}
