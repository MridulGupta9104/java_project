package com.example.javaproject2024.customer_enrollment;

import java.sql.Connection;
import java.sql.DriverManager;

public class MySQLConnection {
    public static Connection doconnect()
    {
        Connection con=null;
        try
        {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con= DriverManager.getConnection("jdbc:mysql://localhost/javaproject2024","root","Mr@091004");
        }
        catch(Exception exp)
        {
            exp.printStackTrace();
        }
        return con;
    }
}
