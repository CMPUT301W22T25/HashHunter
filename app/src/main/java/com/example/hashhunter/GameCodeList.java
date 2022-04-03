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

public class GameCodeList extends ArrayAdapter<GameCode> {
    private ArrayList<GameCode> gameCodes;
    private Context context;

    public GameCodeList(Context context, ArrayList<GameCode> gameCodes) {
        super(context, 0, gameCodes);

        this.gameCodes = gameCodes;
        this.context = context;
    }

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
