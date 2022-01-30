package com.example.mysql;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private Button search;
    private Button delete;
    private Button insert;
    private Button update;
    private Button cretable;
    private Button deltable;
    private Button join;
    private TextView textView;
    private EditText sqltext;
    String sql;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg){
            if(msg.what==0){
                String text = (String) msg.obj;
                textView.setText(text);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    private void initView() {
        search = findViewById(R.id.search);
        delete = findViewById(R.id.delete);
        insert = findViewById(R.id.insert);
        update = findViewById(R.id.update);
        cretable = findViewById(R.id.cretable);
        deltable = findViewById(R.id.deltable);
        join = findViewById(R.id.join);
        textView = findViewById(R.id.result);
        sqltext = findViewById(R.id.sqltext);
        search.setOnClickListener(this);
        delete.setOnClickListener(this);
        insert.setOnClickListener(this);
        update.setOnClickListener(this);
        cretable.setOnClickListener(this);
        deltable.setOnClickListener(this);
        join.setOnClickListener(this);
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
            case R.id.cretable:
                docretable();
                break;
            case R.id.deltable:
                dodeltable();
                break;
            case R.id.join:
                dojoin();
                break;
        }
    }

    private void dosearch() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    Connection conn = DbOpenHelper.getConnection();
                    Statement stmt = conn.createStatement();
                    //sql = "select * from student where studentID = 1010000";
                    sql = sqltext.getText().toString();
                    Log.d("tag",sql);
                    ResultSet rs = stmt.executeQuery(sql);
                    int studentID;
                    String studentname;
                    String schoolID;
                    String scorelevel;
                    String subjectID;
                    String text="";
                    if(rs==null){
                        text = "查询结果为空！";
                    }
                    else{
                        while(rs.next()){
                            studentID = rs.getInt("studentID");
                            studentname = rs.getString("studentname");
                            schoolID = rs.getString("schoolID");
                            scorelevel = rs.getString("scorelevel");
                            subjectID = rs.getString("subjectID");
                            text = "    ID            name      schID   scolevel   subID"+"\n";
                            text = text + studentID + "      " + studentname + "      " + schoolID + "        " + scorelevel + "        " + subjectID + "\n";
                        }
                    }
                    conn.close();
                    Message msg = Message.obtain();
                    msg.what=0;
                    msg.obj = text;
                    handler.sendMessage(msg);
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void dodelete(){
        new Thread(new Runnable() {
            String text = "";
            @Override
            public void run() {
                try{
                    Connection conn = DbOpenHelper.getConnection();
                    Statement stmt = conn.createStatement();
                    sql = sqltext.getText().toString();
                    //sql = "delete from student where studentID = 100";
                    stmt.execute(sql);
                    conn.close();
                    text = "删除成功";
                }catch (SQLException e) {
                    e.printStackTrace();
                    text = "删除失败";
                }
                Message msg = Message.obtain();
                msg.what=0;
                msg.obj = text;
                handler.sendMessage(msg);
            }
        }).start();
    }

    private void doinsert(){
        new Thread(new Runnable() {
            String text = "";
            @Override
            public void run() {
                try{
                    Connection conn = DbOpenHelper.getConnection();
                    Statement stmt = conn.createStatement();
                    sql = sqltext.getText().toString();
                    //sql = "insert into student values(100,'pkj','10003','B','10005')";
                    stmt.execute(sql);
                    conn.close();
                    text = "插入成功";
                }catch (SQLException e){
                    e.printStackTrace();
                    text = "插入失败";
                }
                Message msg = Message.obtain();
                msg.what=0;
                msg.obj = text;
                handler.sendMessage(msg);
            }
        }).start();
    }

    private void doupdate(){
        new Thread(new Runnable() {
            String text = "";
            @Override
            public void run() {
                try{
                    Connection conn = DbOpenHelper.getConnection();
                    Statement stmt = conn.createStatement();
                    //sql = "update student set scorelevel = 'A' where studentID = 100";
                    sql = sqltext.getText().toString();
                    stmt.executeUpdate(sql);
                    conn.close();
                    text = "修改成功";
                }catch (SQLException e){
                    e.printStackTrace();
                    text = "修改失败";
                }
                Message msg = Message.obtain();
                msg.what=0;
                msg.obj = text;
                handler.sendMessage(msg);
            }
        }).start();
    }

    private void docretable(){
        new Thread(new Runnable() {
            String text = "";
            @Override
            public void run() {
                try{
                    Connection conn = DbOpenHelper.getConnection();
                    Statement stmt = conn.createStatement();
                    //sql = "create table t_user (username varchar(50) not null primary key,password varchar(20) not null ) ";
                    sql = sqltext.getText().toString();
                    stmt.executeUpdate(sql);
                    conn.close();
                    text = "添加表成功";
                }catch (SQLException e){
                    e.printStackTrace();
                    text = "添加表失败";
                }
                Message msg = Message.obtain();
                msg.what=0;
                msg.obj = text;
                handler.sendMessage(msg);
            }
        }).start();
    }

    private void dodeltable(){
        new Thread(new Runnable() {
            String text = "";
            @Override
            public void run() {
                try{
                    Connection conn = DbOpenHelper.getConnection();
                    Statement stmt = conn.createStatement();
                    //sql = "drop table t_user";
                    sql = sqltext.getText().toString();
                    stmt.execute(sql);
                    conn.close();
                    text = "删除表成功";
                }catch (SQLException e){
                    e.printStackTrace();
                    text = "删除表失败";
                }
                Message msg = Message.obtain();
                msg.what=0;
                msg.obj = text;
                handler.sendMessage(msg);
            }
        }).start();
    }

    private void dojoin() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    Connection conn = DbOpenHelper.getConnection();
                    Statement stmt = conn.createStatement();
                    //sql = "select teachername,schoolname from teacher inner join school on teacher.schoolID = school.schoolID where teacherID = 10100";
                    sql = sqltext.getText().toString();
                    Log.d("tag",sql);
                    ResultSet rs = stmt.executeQuery(sql);
                    String teachername;
                    String schoolname;
                    String text="";
                    if(rs==null){
                        text = "查询结果为空！";
                    }
                    else{
                        while(rs.next()){
                            teachername = rs.getString("teachername");
                            schoolname = rs.getString("schoolname");
                            text = text + teachername + "      " + schoolname + "\n";
                        }
                    }
                    conn.close();
                    Message msg = Message.obtain();
                    msg.what=0;
                    msg.obj = text;
                    handler.sendMessage(msg);
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
        }).start();
    }
/*
    private void doQueryCount() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    Connection conn = DbOpenHelper.getConnection();
                    Statement stmt = conn.createStatement();
                    //String sql = "select * from teacher";
                    sql = sqltext.getText().toString();
                    Log.d("tag",sql);
                    ResultSet rs = stmt.executeQuery(sql);
                    int teacherID;
                    String teachername;
                    String schoolID;
                    String subjectID;
                    String text="";
                    while(rs.next()){
                        teacherID = rs.getInt("teacherID");
                        teachername = rs.getString("teachername");
                        schoolID = rs.getString("schoolID");
                        subjectID = rs.getString("subjectID");
                        text = text + teacherID + " " + teachername + " " + schoolID + " " + subjectID + "\n";
                    }
                    conn.close();
                    Message msg = Message.obtain();
                    msg.what=0;
                    msg.obj = text;
                    handler.sendMessage(msg);
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
        }).start();
    }*/
/*
    private void doQueryCount() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    Connection conn = DbOpenHelper.getConnection();
                    Statement stmt = conn.createStatement();
                    String sql = "select * from school";
                    ResultSet rs = stmt.executeQuery(sql);
                    String schoolID;
                    String schoolname;
                    String schoolloc;
                    String text="";
                    while(rs.next()){
                        schoolID = rs.getString("schoolID");
                        schoolname = rs.getString("schoolname");
                        schoolloc = rs.getString("schoolloc");
                        text = text + schoolID + " " + schoolname + " " + schoolloc + "\n";
                    }
                    conn.close();
                    Message msg = Message.obtain();
                    msg.what=0;
                    msg.obj = text;
                    handler.sendMessage(msg);
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
        }).start();
    }*/
}