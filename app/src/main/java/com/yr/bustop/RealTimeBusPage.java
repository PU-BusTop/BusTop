package com.yr.bustop;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TableLayout;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by YR on 2018/5/24.
 */

public class RealTimeBusPage extends LinearLayout {
    ListView listView;
    public RealTimeBusPage(Context context) {
        super(context);
    }

    public RealTimeBusPage(Context context, ArrayList<Map<String,String>> data) {
        super(context);

        View view = LayoutInflater.from(context).inflate(R.layout.real_time_fragment, null);
        LinearLayout.LayoutParams params =
                new LinearLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.MATCH_PARENT);
        view.setLayoutParams(params);

        listView = view.findViewById(R.id.Stoplistview);

        StopAdapter adapter = new StopAdapter(getContext(),data);
        listView.setAdapter(adapter);

        addView(view);

    }
}
