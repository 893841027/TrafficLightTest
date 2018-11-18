package com.example.zzy4f5da2.trafficjsondatatest1118;

import android.app.AlertDialog;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.*;

public class MainActivity extends AppCompatActivity {

    private JSONObject jso;
    List<LightBean>list = new ArrayList<>();
    private ListView listView;
    private Button mulRecharge;
    private Button query;
    private LightAdapter adapter;
    private Spinner spinner;
    private List<String> spinnerList;
    int pos = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViewID();
        getData();
        initSpinnerList();
        addEventListener();

    }

    void initSpinnerList(){
        spinnerList = new ArrayList<>();
            spinnerList.add("路口升序");
            spinnerList.add("路口降序");
            spinnerList.add("红灯升序");
            spinnerList.add("红灯降序");
            spinnerList.add("黄灯升序");
            spinnerList.add("黄灯降序");
            spinnerList.add("绿灯升序");
            spinnerList.add("绿灯降序");
            ArrayAdapter spinnerAdapter =
                    new ArrayAdapter(MainActivity.this, android.R.layout.simple_spinner_dropdown_item, spinnerList);
            spinner.setAdapter(spinnerAdapter);
    }

    void initViewID(){
        listView = findViewById(R.id.listview);
        spinner = findViewById(R.id.spinner);
        mulRecharge = findViewById(R.id.muilt);
        query = findViewById(R.id.query);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    void jsonSwitchToBean() throws JSONException {
        JSONArray jsa_item = new JSONArray(jso.optString("trafficLight"));
        Log.e("jsonSwitchToBean: ", jsa_item.toString());
        for(int i = 0 ; i <  jsa_item.length() ; i++){
            JSONObject jso_item = jsa_item.optJSONObject(i);
            LightBean bean = new LightBean();
            bean.setRoad(jso_item.optInt("road"));
            bean.setRed(jso_item.optInt("red"));
            bean.setGreen(jso_item.optInt("green"));
            bean.setYellow(jso_item.optInt("yellow"));
            list.add(bean);
        }
        initAdapter();
    }

    void initAdapter(){
        adapter = new LightAdapter(MainActivity.this,list);
        listView.setAdapter(adapter);
    }

    void queryList(){
        if(pos==0){
            RoadAsc();
        }else if(pos==1){
            RoadDesc();
        }else if(pos==2){
            RedAsc();
        }else if(pos==3){
            RedDesc();
        }else if(pos==4){
            YellowAsc();
        }else if(pos==5){
            YellowDesc();
        }else if(pos==6){
            GreenAsc();
        }else if(pos==7){
            GreenDesc();
        }
        adapter.notifyDataSetChanged();
    }

    void addEventListener(){

        query.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                queryList();
            }
        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                pos = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mulRecharge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                View dialogView = View.inflate(MainActivity.this,R.layout.dialog,null);
                final EditText Dred = dialogView.findViewById(R.id.dia_red);
                final EditText Dyellow = dialogView.findViewById(R.id.dia_yellow);
                final EditText Dgreen = dialogView.findViewById(R.id.dia_green);
                TextView Dok = dialogView.findViewById(R.id.dia_ok);
                TextView Dcancel = dialogView.findViewById(R.id.dia_cancel);

                Dred.setText("10");
                Dyellow.setText("10");
                Dgreen.setText("10");

                final AlertDialog dialog = new AlertDialog.Builder(MainActivity.this).create();
                dialog.show();
                dialog.setContentView(dialogView);

                Dok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                         HashMap<Integer,Boolean>map = adapter.checkMap;
                            if(map.size() > 0){
                                Set<Integer> key = map.keySet();
                                for( Integer k : key){
                                    if(map.get(k)){
                                        LightBean item = adapter.mlist.get(k);
                                        item.setRed(Integer.parseInt(Dred.getText().toString()));
                                        item.setYellow(Integer.parseInt(Dyellow.getText().toString()));
                                        item.setGreen(Integer.parseInt(Dgreen.getText().toString()));
                                    }
                                }
                                adapter.notifyDataSetChanged();
                            }
                        Toast.makeText(MainActivity.this, "批量充值成功", Toast.LENGTH_SHORT).show();
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
    }


    void getData(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    jso = new DataManager().getData("http://192.168.79.86:8080/res/trafficLight.json");
                    runOnUiThread(new Runnable() {
                        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                        @Override
                        public void run() {
                            try {
                                Toast.makeText(getApplicationContext(), "success", Toast.LENGTH_SHORT).show();
                                jsonSwitchToBean();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();

    }

    void RedAsc(){
        Collections.sort(adapter.mlist, new Comparator<LightBean>() {
            @Override
            public int compare(LightBean o1, LightBean o2) {
                return o1.getRed() - o2.getRed();
            }
        });
    }

    void RedDesc(){
        Collections.sort(adapter.mlist, new Comparator<LightBean>() {
            @Override
            public int compare(LightBean o1, LightBean o2) {
                return - o1.getRed() + o2.getRed();
            }
        });
    }

    void GreenAsc(){
        Collections.sort(adapter.mlist, new Comparator<LightBean>() {
            @Override
            public int compare(LightBean o1, LightBean o2) {
                return o1.getGreen() - o2.getGreen();
            }
        });
    }

    void GreenDesc(){
        Collections.sort(adapter.mlist, new Comparator<LightBean>() {
            @Override
            public int compare(LightBean o1, LightBean o2) {
                return - o1.getGreen() + o2.getGreen();
            }
        });
    }

    void YellowAsc(){
        Collections.sort(adapter.mlist, new Comparator<LightBean>() {
            @Override
            public int compare(LightBean o1, LightBean o2) {
                return o1.getYellow() - o2.getYellow();
            }
        });
    }

    void YellowDesc(){
        Collections.sort(adapter.mlist, new Comparator<LightBean>() {
            @Override
            public int compare(LightBean o1, LightBean o2) {
                return - o1.getYellow() + o2.getYellow();
            }
        });
    }

    void RoadAsc(){
        Collections.sort(adapter.mlist, new Comparator<LightBean>() {
            @Override
            public int compare(LightBean o1, LightBean o2) {
                return o1.getRoad() - o2.getRoad();
            }
        });
    }

    void RoadDesc(){
        Collections.sort(adapter.mlist, new Comparator<LightBean>() {
            @Override
            public int compare(LightBean o1, LightBean o2) {
                return - o1.getRoad() + o2.getRoad();
            }
        });
    }

}
