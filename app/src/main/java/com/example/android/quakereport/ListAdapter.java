package com.example.android.quakereport;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {

    // List of all earthquakes to display
    private final ArrayList<Earthquake> earthquakes;
    private Context context;

    // Provides a reference to the views for each data item
    static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView mag;
        private final TextView locationOffset;
        private final TextView primaryLocation;
        private final TextView date;
        private final TextView time;

        ViewHolder(View itemView) {
            super(itemView);
            mag = itemView.findViewById(R.id.mag);
            locationOffset = itemView.findViewById(R.id.location_offset);
            primaryLocation = itemView.findViewById(R.id.primary_location);
            date = itemView.findViewById(R.id.date);
            time = itemView.findViewById(R.id.time);
        }
    }

    // Constructor for adapter
    ListAdapter(ArrayList<Earthquake> earthquakes) {
        this.earthquakes = earthquakes;
    }

    // Create new views (invoked by the layout manager)
    @NonNull
    @Override
    public ListAdapter.ViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);
        context = parent.getContext();
        return new ViewHolder(v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {

        final Earthquake currentEarthquake = earthquakes.get(position);

        double magnitudeDecimal = currentEarthquake.getMag();
        DecimalFormat formatter = new DecimalFormat("0.0");
        String magnitudeFormatted = formatter.format(magnitudeDecimal);
        viewHolder.mag.setText(magnitudeFormatted);

        GradientDrawable magnitudeCircle = (GradientDrawable) viewHolder.mag.getBackground();
        int magnitudeColor = getMagnitudeColor(magnitudeDecimal);
        magnitudeCircle.setColor(magnitudeColor);

        final String location = currentEarthquake.getPlace();
        if (location.contains(" of")) {
            viewHolder.locationOffset.setText(location.split("(?<= of)")[0]);
            viewHolder.primaryLocation.setText(location.split(" of")[1]);
        } else {
            viewHolder.locationOffset.setText(R.string.location_offset);
            viewHolder.primaryLocation.setText(location);
        }

        Date date = new Date(currentEarthquake.getDateUnix());

        String formattedDate = formatDate(date);
        viewHolder.date.setText(formattedDate);

        String formattedTime = formatTime(date);
        viewHolder.time.setText(formattedTime);

        final String url = currentEarthquake.getUrl();

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                context.startActivity(i);
            }
        });
    }

    // Return the size of list (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return earthquakes.size();
    }

    /**
     * Return the formatted date string (i.e. "Mar 3, 1984") from a Date object.
     */
    private String formatDate(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("LLL dd, yyyy");
        return dateFormat.format(date);
    }

    /**
     * Return the formatted date string (i.e. "4:30 PM") from a Date object.
     */
    private String formatTime(Date date) {
        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a");
        return timeFormat.format(date);
    }

    // Return the right background color depending on the magnitude of earthquake
    private int getMagnitudeColor(double mag) {
        int magnitudeColorResourceId;
        int magnitudeFloor = (int) Math.floor(mag);
        switch (magnitudeFloor) {
            case 0:
            case 1:
                magnitudeColorResourceId = R.color.magnitude1;
                break;
            case 2:
                magnitudeColorResourceId = R.color.magnitude2;
                break;
            case 3:
                magnitudeColorResourceId = R.color.magnitude3;
                break;
            case 4:
                magnitudeColorResourceId = R.color.magnitude4;
                break;
            case 5:
                magnitudeColorResourceId = R.color.magnitude5;
                break;
            case 6:
                magnitudeColorResourceId = R.color.magnitude6;
                break;
            case 7:
                magnitudeColorResourceId = R.color.magnitude7;
                break;
            case 8:
                magnitudeColorResourceId = R.color.magnitude8;
                break;
            case 9:
                magnitudeColorResourceId = R.color.magnitude9;
                break;
            default:
                magnitudeColorResourceId = R.color.magnitude10plus;
                break;
        }
        return ContextCompat.getColor(context, magnitudeColorResourceId);
    }

    public void setEarthquakes(List<Earthquake> new_data) {
        earthquakes.clear();
        earthquakes.addAll(new_data);
        notifyDataSetChanged();
    }
}
