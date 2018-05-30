package com.yr.bustop;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by YR on 2018/5/24.
 */

public class RealTimeBusPage extends LinearLayout{
    Context context;
    ListView listView;
    ArrayList<Stop> data;
    String[] menu=new String[]{"加入常用站牌","到站前提醒"};

//    public RealTimeBusPage() {
//        super();
//    }


    public RealTimeBusPage(Context context){
        super(context);

    }

    public RealTimeBusPage(final Context context, final ArrayList<Stop> data) {
        super(context);
        this.context = context;
        this.data = data;

        View view = LayoutInflater.from(context).inflate(R.layout.real_time_fragment, null);
        LinearLayout.LayoutParams params =
                new LinearLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.MATCH_PARENT);
        view.setLayoutParams(params);

        listView = view.findViewById(R.id.Stoplistview);

        StopAdapter adapter = new StopAdapter(context,data);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d("onItemClick", i+"");
                AlertDialog dialog = new AlertDialog.Builder(context)
                        .setTitle(data.get(i).getName())
                        .setItems(menu, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(context, menu[which], Toast.LENGTH_SHORT).show();
                            }
                        })
                        .show();
            }
        });

        addView(view);

    }





}
