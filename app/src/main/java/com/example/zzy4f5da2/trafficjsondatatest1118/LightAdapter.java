package com.example.zzy4f5da2.trafficjsondatatest1118;

import android.app.Activity;
import android.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import java.util.HashMap;
import java.util.List;

public class LightAdapter extends BaseAdapter {

    Activity activity;
    List<LightBean>mlist;
    HashMap<Integer,Boolean>checkMap = new HashMap<>();

    public LightAdapter(Activity activity, List<LightBean> list) {
        this.activity = activity;
        this.mlist = list;
        for(int i = 0 ; i <  list.size() ; i++){
            checkMap.put(i,false);
        }
    }

    @Override
    public int getCount() {
        return mlist.size();
    }

    @Override
    public Object getItem(int position) {
        return mlist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        convertView = View.inflate(activity,R.layout.item,null);
        TextView red = convertView.findViewById(R.id.red);
        TextView green = convertView.findViewById(R.id.green);
        TextView yellow = convertView.findViewById(R.id.yellow);
        TextView road = convertView.findViewById(R.id.road);
        final CheckBox check = convertView.findViewById(R.id.check);
        Button set = convertView.findViewById(R.id.set);

        final LightBean item = mlist.get(position);
        red.setText(item.getRed()+"");
        green.setText(item.getGreen()+"");
        yellow.setText(item.getYellow()+"");
        road.setText(item.getRoad()+"");

        check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                checkMap.put(position,isChecked);
            }
        });

        check.setChecked(checkMap.get(position));

        set.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                View dialogView = View.inflate(activity,R.layout.dialog,null);
                final EditText Dred = dialogView.findViewById(R.id.dia_red);
                final EditText Dyellow = dialogView.findViewById(R.id.dia_yellow);
                final EditText Dgreen = dialogView.findViewById(R.id.dia_green);
                TextView Dok = dialogView.findViewById(R.id.dia_ok);
                TextView Dcancel = dialogView.findViewById(R.id.dia_cancel);

                Dred.setText(item.getRed()+"");
                Dyellow.setText(item.getYellow()+"");
                Dgreen.setText(item.getGreen()+"");


                final AlertDialog dialog = new AlertDialog.Builder(activity).create();
                dialog.show();
                dialog.setContentView(dialogView);

                Dok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        item.setRed(Integer.parseInt(Dred.getText().toString()));
                        item.setYellow(Integer.parseInt(Dyellow.getText().toString()));
                        item.setGreen(Integer.parseInt(Dgreen.getText().toString()));
                        notifyDataSetChanged();
                        Toast.makeText(activity, "修改成功", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });

                Dcancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

            }
        });


        return convertView;
    }
}
