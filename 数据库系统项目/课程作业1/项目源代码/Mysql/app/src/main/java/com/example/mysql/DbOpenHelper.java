package com.example.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class DbOpenHelper {
    private static final String CLS = "com.mysql.jdbc.Driver";
    private static final String URL = "jdbc:mysql://192.168.48.130:3306/test";
    private static final String USER = "root";
    private static final String PWD = "123456";

    public static Connection conn; //连接对象
    public static Statement stmt; //命令集
    public static PreparedStatement pStmt; //预编译命令集
    public static ResultSet rs; //结果集

    //取得连接的方法
    public static Connection getConnection(){
        try{
            Class.forName(CLS);
            conn = DriverManager.getConnection(URL,USER,PWD);
        }catch (Exception e){
            e.printStackTrace();
        }
        return conn;
    }

    //关闭数据库操作对象
    public static void closeAll(){
        try{
            if(rs!=null){
                rs.close();
                rs=null;
            }
            if(stmt!=null){
                stmt.close();
                stmt=null;
            }
            if(pStmt!=null){
                pStmt.close();
                pStmt=null;
            }
            if(conn!=null){
                conn.close();
                conn=null;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
