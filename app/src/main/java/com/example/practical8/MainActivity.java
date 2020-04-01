package com.example.practical8;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity
{
    ListView list;
    ArrayList<HashMap<String, String>> formList;

    public String loadJSONFromAsset()
    {
        String json = null;
        try {
            InputStream is = getApplicationContext().getAssets().open("new.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        list=findViewById(R.id.list);

        try {
            JSONObject obj = new JSONObject(loadJSONFromAsset());
            JSONArray m_jArry = obj.getJSONArray("india");
            formList= new ArrayList<HashMap<String, String>>();
            HashMap<String, String>m_li;

            for (int i = 0; i<m_jArry.length(); i++) {
                JSONObject jo_inside = m_jArry.getJSONObject(i);
                Log.d("Details-->", jo_inside.getString("team"));
                String formula_value = jo_inside.getString("team");
                String url_value = jo_inside.getString("player");

//Add your values in your `ArrayList` as below:
                m_li = new HashMap<String, String>();
                m_li.put("team", formula_value);
                m_li.put("player", url_value);

                formList.add(m_li);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ArrayAdapter arrayAdapter=new ArrayAdapter(getApplicationContext(),android.R.layout.simple_list_item_1,formList);
        list.setAdapter(arrayAdapter);
    }
}
