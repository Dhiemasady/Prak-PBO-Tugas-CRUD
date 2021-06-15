package kuis;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class cekData {
    private JTextField tfUsernameLog;
    private JButton btnCari;
    private JButton btnTambah;
    private JPasswordField tfPasswordLog;
    private JPanel homePanel;

    connector connector = new connector();

    public cekData() {

        JFrame frame = new JFrame("Tugas KUIS");
        frame.setContentPane(homePanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);


        btnCari.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (tfUsernameLog.getText().equals("")||tfPasswordLog.getText().equals("")){
                        JOptionPane.showMessageDialog(null,"Data Tidak Boleh Kosong", "Pesan", JOptionPane.ERROR_MESSAGE);
                        tfUsernameLog.requestFocus();
                        bersihkanData();
                    }else {
                        connector.statement = connector.koneksi.createStatement();

                        String query = ("SELECT * FROM data WHERE username = '"+getUsername()+"' AND password = '"+getPassword()+"'");
                        ResultSet rs = connector.statement.executeQuery(query);
                        if(rs.next()){
                            frame.setVisible(false);
                            new formOutput(getUsername(),getPassword());
                        }else{
                            JOptionPane.showMessageDialog(null, "Username atau Password Salah", "Peringatan", JOptionPane.ERROR_MESSAGE);
                            bersihkanData();
                        }
                    }
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                } catch (SQLException ev){
                    JOptionPane.showMessageDialog(null,"gagal database");
                }
            }
        });


        btnTambah.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
                formInput formInput = new formInput();
            }
        });
    }

    private void bersihkanData() {
        tfUsernameLog.setText(null);
        tfPasswordLog.setText(null);
    }

    public String getUsername(){
        return tfUsernameLog.getText();
    }
    public String getPassword(){
        return String.valueOf(tfPasswordLog.getPassword());
    }
}
