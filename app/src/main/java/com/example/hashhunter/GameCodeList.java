package com.example.hashhunter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

/**
 * a class for displaying gamecodes in a listview
 */
public class GameCodeList extends ArrayAdapter<GameCode> {
    private ArrayList<GameCode> gameCodes;
    private Context context;

    /**
     * constructor for the class
     * @param context the context (passed by an activity)
     * @param gameCodes the list of gamecodes
     */
    public GameCodeList(Context context, ArrayList<GameCode> gameCodes) {
        super(context, 0, gameCodes);

        this.gameCodes = gameCodes;
        this.context = context;
    }

    /**
     * returns the view for a single gamecode
     * @param position position of the view
     * @param convertView a View, not used in the method
     * @param parent a ViewGroup, not used in the method
     * @return
     */
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;

        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.gamecode_list_content, parent, false);
        }

        GameCode code = gameCodes.get(position);

        TextView title = view.findViewById(R.id.title_text);
        TextView lat = view.findViewById(R.id.lat_text);
        TextView lon = view.findViewById(R.id.lon_text);

        title.setText(code.getCode());

        try {
            lat.setText(String.format("Latitude: %s", code.getLatitude().toString()));
        } catch (NullPointerException e) {
            lat.setText("null");
        }
        try {
            lon.setText(String.format("Longitude: %s", code.getLongitude().toString()));
        } catch (NullPointerException e) {
            lon.setText("null");
        }

        return view;
    }

}
