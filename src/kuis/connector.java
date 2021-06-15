package kuis;

import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class connector {
    String DBurl = "jdbc:mysql://localhost/prakpbo_kuis";
    String DBusername = "root";
    String DBpassword = "";
    Connection koneksi;
    Statement statement;

    public connector(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            koneksi = DriverManager.getConnection(DBurl,DBusername,DBpassword);
        }catch (Exception ex){
            JOptionPane.showMessageDialog(null, "Koneksi Gagal!", "Peringatan", JOptionPane.ERROR_MESSAGE);
        }
    }
}
