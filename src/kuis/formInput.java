package kuis;

import com.sun.tools.javac.Main;
import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.sql.*;
import java.util.regex.Pattern;


public class formInput {
    private JLabel judul;
    private JTextField tfUsername;
    private JTextField tfNama;
    private JTextField tfDomisili;
    private JTextArea taDeskDiri;
    private JTextField tfEmail;
    private JTextField tfTempatLahir;
    private JButton btnBatal;
    private JButton btnSimpan;
    private JPasswordField tfPassword;
    private JPanel formInputPanel;
    private JPanel jpCalender;
    private JButton btnClear;

    connector connector = new connector();

    JDateChooser datePicker = new JDateChooser();
    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");


    public formInput() {
        JFrame frame = new JFrame("Form Pendaftaran");
        frame.setContentPane(formInputPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

        //Kalender
        datePicker.setDateFormatString("yyyy-MM-dd");
        jpCalender.add(datePicker);


        btnSimpan.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {
                    if (tfEmail.getText().equals("")||tfUsername.getText().equals("")||tfPassword.getText().equals("")||tfNama.getText().equals("")||
                            tfTempatLahir.getText().equals("")||datePicker.getDate().equals("")||tfDomisili.getText().equals("")||taDeskDiri.getText().equals("")){
                        JOptionPane.showMessageDialog(null,"Data Tidak Boleh Kosong", "Pesan", JOptionPane.ERROR_MESSAGE);
                        bersihkanData();
                    }if(!Pattern.matches("^[a-zA-Z0-9]+[@]{1}+[a-zA-Z0-9]+[.]{1}+[a-zA-Z0-9]+$",tfEmail.getText())){
                        JOptionPane.showMessageDialog(null, "Please enter a valid email","Error",JOptionPane.ERROR_MESSAGE);
                    }else{
                        connector.statement = connector.koneksi.createStatement();
                        String query = ("INSERT INTO data VALUES ('"+getEmail()+"','"+getUsername()+"','"+getPassword()+"','"+getNama()+"','"+getTempatLahir()+"','"+getTglLahir()+"','"+getDomisili()+"','"+getDeskDiri()+"')");
                        connector.statement.executeUpdate(query);

                        JOptionPane.showMessageDialog(null, "Data Berhasil Disimpan!", "Hasil",JOptionPane.INFORMATION_MESSAGE);

                        frame.setVisible(false);
                        new cekData();
                    }

                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Data Gagal Disimpan!" + ex, "Hasil", JOptionPane.ERROR_MESSAGE );
                }

            }
        });


        btnClear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                bersihkanData();
            }
        });

        btnBatal.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
                cekData cekData = new cekData();
            }
        });

        tfUsername.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char getUsername = e.getKeyChar();
                if (!(Character.isAlphabetic(getUsername))&&!(Character.isDigit(getUsername))){
                    e.consume();
                    JOptionPane.showMessageDialog(null,"Hanya bisa memasukkan Huruf dan Angka");
                }
            }
        });


        taDeskDiri.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                int max = 199;
                if(taDeskDiri.getText().length() > max+1) {
                    e.consume();
                    String shortened = taDeskDiri.getText().substring(0, max);
                    taDeskDiri.setText(shortened);
                    JOptionPane.showMessageDialog(null,"Melebihi 200 Karakter");
                }else if(taDeskDiri.getText().length() > max) {
                    e.consume();
                    JOptionPane.showMessageDialog(null,"Melebihi 200 Karakter");
                }
            }
        });
    }

    private void bersihkanData() {
        tfEmail.setText(null);
        tfUsername.setText(null);
        tfPassword.setText(null);
        tfNama.setText(null);
        tfTempatLahir.setText(null);
        datePicker.setCalendar(null);
        tfDomisili.setText(null);
        taDeskDiri.setText(null);
    }

    public String getEmail(){
        return tfEmail.getText();
    }
    public String getUsername(){
        return tfUsername.getText();
    }
    public String getPassword(){
        return String.valueOf(tfPassword.getPassword());
    }
    public String getNama(){
        return tfNama.getText();
    }
    public String getTempatLahir(){
        return tfTempatLahir.getText();
    }
    public String getTglLahir(){
        return df.format(datePicker.getDate());
    }
    public String getDomisili(){
        return tfDomisili.getText();
    }
    public String getDeskDiri(){
        return taDeskDiri.getText();
    }


}
