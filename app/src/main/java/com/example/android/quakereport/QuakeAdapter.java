package com.example.android.quakereport;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class QuakeAdapter extends ArrayAdapter<Quake> {
    private static final String LOCATION_SEPARATOR = " of ";

    public QuakeAdapter(@NonNull Context context, ArrayList<Quake> QuakeList) {
        super(context, 0, QuakeList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_layout, parent, false);
        }
        Quake currentItemPosition = getItem(position);


//        LinearLayout wrapper = listItemView.findViewById(R.id.wrapper);
        //format mag
        TextView tvMagnitude = listItemView.findViewById(R.id.tvMagnitude);
        tvMagnitude.setText(formatMagnitude(currentItemPosition.getMagnitude()));

        //circular background
        GradientDrawable magnitudeCircle = (GradientDrawable) tvMagnitude.getBackground();
        int magColor = getMagnitudeColor(currentItemPosition.getMagnitude());
        magnitudeCircle.setColor(magColor);

        //split location
        String location = currentItemPosition.getLocation();
        String[] locationArray = splitLocation(location);

        TextView tvPrimaryLocation = listItemView.findViewById(R.id.tvprimaryLocation);
        String primaryLocation = locationArray[1];
        tvPrimaryLocation.setText(primaryLocation);

        TextView tvOffsetLocation = listItemView.findViewById(R.id.tvoffsetLocation);
        String offsetLocation = locationArray[0];
        tvOffsetLocation.setText(offsetLocation);

        //split data
        Date dateObject = new Date(currentItemPosition.getTimeInMilliseconds());

        TextView tvDate = listItemView.findViewById(R.id.tvDate);
        tvDate.setText(formatDate(dateObject));

        TextView tvTime = listItemView.findViewById(R.id.tvTime);
        tvTime.setText(formatTime(dateObject));

        return listItemView;
    }

    private int getMagnitudeColor(Double magnitude) {
        int colorId;
        int magnitudeFloor = (int) Math.floor(magnitude);
        switch (magnitudeFloor) {
            case 0:
            case 1:
                colorId = R.color.magnitude1;
                break;
            case 2:
                colorId = R.color.magnitude2;
                break;
            case 3:
                colorId = R.color.magnitude3;
                break;
            case 4:
                colorId = R.color.magnitude4;
                break;
            case 5:
                colorId = R.color.magnitude5;
                break;
            case 6:
                colorId = R.color.magnitude6;
                break;
            case 7:
                colorId = R.color.magnitude7;
                break;
            case 8:
                colorId = R.color.magnitude8;
                break;
            case 9:
                colorId = R.color.magnitude9;
                break;
            default:
                colorId = R.color.magnitude10plus;
                break;
        }
        return ContextCompat.getColor(getContext(), colorId);
    }

    private String formatDate(Date dateObject) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("LLL dd ,yyyy");
        return dateFormat.format(dateObject);
    }

    /**
     * Return the formatted date string (i.e. "4:30 PM") from a Date object.
     */
    private String formatTime(Date dateObject) {
        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a");
        return timeFormat.format(dateObject);
    }

    private String[] splitLocation(String location) {
        String[] locationArray = new String[2];

        if (location.contains(LOCATION_SEPARATOR)) {
            locationArray = location.split(LOCATION_SEPARATOR);
            locationArray[0] = locationArray[0] + LOCATION_SEPARATOR;
        } else {
            locationArray[0] = "Near of ";
            locationArray[1] = location;
        }
        return locationArray;
    }

    private String formatMagnitude(double magnitude) {
        DecimalFormat decimalFormat = new DecimalFormat("0.0");
        return decimalFormat.format(magnitude);
    }

}
