package com.example.myredis;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import redis.clients.jedis.Jedis;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private static final String HOST = "192.168.48.130";
    private static final int PORT = 6379;
    private static final String PASSWORD = "123456";

    private Button search;
    private Button delete;
    private Button insert;
    private Button update;
    private Button special;
    private EditText input;
    private EditText output;
    String keys;
    String values;

    private static Jedis jedis;

    public static Jedis getJedis(){//连接redis服务器
        try{
            Jedis resourse = new Jedis(HOST,PORT);
            resourse.auth(PASSWORD);
            return resourse;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg){
            if(msg.what==0){
                String text = (String) msg.obj;
                output.setText(text);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView(){
        search = findViewById(R.id.search);
        delete = findViewById(R.id.delete);
        insert = findViewById(R.id.insert);
        update = findViewById(R.id.update);
        special = findViewById(R.id.special);
        input = findViewById(R.id.input);
        output = findViewById(R.id.output);
        search.setOnClickListener(this);
        delete.setOnClickListener(this);
        insert.setOnClickListener(this);
        update.setOnClickListener(this);
        special.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.search:
                dosearch();
                break;
            case R.id.delete:
                dodelete();
                break;
            case R.id.insert:
                doinsert();
                break;
            case R.id.update:
                doupdate();
                break;
            case R.id.special:
                testMap();
                break;
        }
    }

    private void dosearch(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    jedis = getJedis();
                    keys = input.getText().toString();
                    String value = "查询结果为：";
                    String value1 = jedis.get(keys);
                    value += value1;
                    if(value1==null) value = "查询结果为空";
                    jedis.close();
                    Message msg = Message.obtain();
                    msg.what=0;
                    msg.obj = value;
                    handler.sendMessage(msg);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void dodelete(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    jedis = getJedis();
                    keys = input.getText().toString();
                    jedis.del(keys);
                    String value = "删除成功";
                    jedis.close();
                    Message msg = Message.obtain();
                    msg.what=0;
                    msg.obj = value;
                    handler.sendMessage(msg);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void doupdate(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    jedis = getJedis();
                    keys = input.getText().toString();
                    values = output.getText().toString();
                    jedis.set(keys,values);
                    String value = "更新成功";
                    jedis.close();
                    Message msg = Message.obtain();
                    msg.what=0;
                    msg.obj = value;
                    handler.sendMessage(msg);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void doinsert(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    jedis = getJedis();
                    keys = input.getText().toString();
                    values = output.getText().toString();
                    jedis.set(keys,values);
                    String value = "添加成功";
                    jedis.close();
                    Message msg = Message.obtain();
                    msg.what=0;
                    msg.obj = value;
                    handler.sendMessage(msg);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void testMap() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //添加数据
                    Map<String, String> map = new HashMap<String, String>();
                    map.put("name", "yc");
                    map.put("age", "22");
                    map.put("qq", "1933108196");
                    jedis = getJedis();
                    jedis.hmset("user", map);
                    //取出users中的Name,执行结果:[minxr]-->注意结果是一个泛型的List
                    //第一个参数是存入redis中map对象的key,后面跟的是放入map中对象的key,后面的key可以是多个，是可变的
                    List<String> rsmap = jedis.hmget("user", "name", "age", "qq");
                    System.out.println(rsmap);
                    String value = "特色操作成功";
                    jedis.close();
                    Message msg = Message.obtain();
                    msg.what=0;
                    msg.obj = value;
                    handler.sendMessage(msg);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }
}