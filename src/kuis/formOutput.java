package kuis;

import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.regex.Pattern;


public class formOutput {
    private JButton btnKeluar;
    private JTextField tfOutEmail;
    private JTextField tfOutUsername;
    private JTextField tfOutPassword;
    private JTextField tfOutNama;
    private JTextField tfOutTempatLahir;
    private JTextField tfOutDomisili;
    private JTextArea taOutDeskDiri;
    private JPanel formOutPanel;
    private JButton btnHapus;
    private JButton btnUbah;
    private JPanel jpCalender;

    connector connector = new connector();

    JDateChooser datePicker = new JDateChooser();
    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

    public formOutput(String username, String password) throws IOException {

        JFrame frame = new JFrame("Form Pendaftaran");
        frame.setContentPane(formOutPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

        datePicker.setDateFormatString("yyyy-MM-dd");
        jpCalender.add(datePicker);

        try {
            connector.statement = connector.koneksi.createStatement();

            String query = ("SELECT * FROM data WHERE username ='"+username+"' AND password ='"+password+"'");
            ResultSet resultSet = connector.statement.executeQuery(query);
            if(resultSet.next()){
                tfOutEmail.setText(resultSet.getString("email"));
                tfOutUsername.setText(resultSet.getString("username"));
                tfOutPassword.setText(resultSet.getString("password"));
                tfOutNama.setText(resultSet.getString("nama"));
                tfOutTempatLahir.setText(resultSet.getString("tempatLahir"));
                datePicker.setDate(resultSet.getDate("tglLahir"));
                tfOutDomisili.setText(resultSet.getString("domisili"));
                taOutDeskDiri.setText(resultSet.getString("deskdiri"));
            }else{
                JOptionPane.showMessageDialog(null, "TIDAK DITEMUKAN", "Peringatan", JOptionPane.ERROR_MESSAGE);

            }

        }catch (SQLException e){
            JOptionPane.showMessageDialog(null, "Terjadi Error!", "Status", JOptionPane.ERROR_MESSAGE);
        }

        btnUbah.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {
                    if (tfOutEmail.getText().equals("")||tfOutUsername.getText().equals("")||tfOutPassword.getText().equals("")||tfOutNama.getText().equals("")||
                            tfOutTempatLahir.getText().equals("")||datePicker.getDate().equals("")||tfOutDomisili.getText().equals("")||taOutDeskDiri.getText().equals("")) {
                        JOptionPane.showMessageDialog(null, "Data Tidak Boleh Kosong", "Pesan", JOptionPane.ERROR_MESSAGE);
                    }if(!Pattern.matches("^[a-zA-Z0-9]+[@]{1}+[a-zA-Z0-9]+[.]{1}+[a-zA-Z0-9]+$",tfOutEmail.getText())){
                        JOptionPane.showMessageDialog(null, "Please enter a valid email","Error",JOptionPane.ERROR_MESSAGE);
                    }else {
                        connector.statement = connector.koneksi.createStatement();
                        String query = ("UPDATE data SET email = '"+getEmail()+"', username = '"+getUsername()+"', password = '"+getPassword()+"', nama = '"+getNama()+"', tempatLahir = '"+getTempatLahir()+"', tglLahir = '"+getTglLahir()+"', domisili = '"+getDomisili()+"', deskDiri ='"+getDeskDiri()+"'  WHERE data.username = '"+username+"' AND data.password = '"+password+"';");
                        connector.statement.executeUpdate(query);

                        JOptionPane.showMessageDialog(null, "Data Berhasil Di Ubah!", "Peringatan!",JOptionPane.INFORMATION_MESSAGE);

                        frame.setVisible(false);
                        new cekData();
                    }

                }catch (SQLException evt){
                    JOptionPane.showMessageDialog(null, "Data Gagal Di Ubah", "Peringatan!", JOptionPane.ERROR_MESSAGE);
                }

            }
        });

        btnHapus.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    connector.statement = connector.koneksi.createStatement();
                    String query = ("DELETE FROM data WHERE data.username = '"+username+"' AND data.password = '"+password+"'");
                    connector.statement.executeUpdate(query);

                    JOptionPane.showMessageDialog(null, "Data Berhasil Di Hapus!", "Peringatan!",JOptionPane.INFORMATION_MESSAGE);

                    frame.setVisible(false);
                    new cekData();

                }catch (SQLException evt){
                    JOptionPane.showMessageDialog(null, "Data Gagal Di Hapus", "Peringatan!", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        btnKeluar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
                cekData cekData = new cekData();
            }
        });
    }

    public String getEmail(){
        return tfOutEmail.getText();
    }
    public String getUsername(){
        return tfOutUsername.getText();
    }
    public String getPassword(){
        return String.valueOf(tfOutPassword.getText());
    }
    public String getNama(){
        return tfOutNama.getText();
    }
    public String getTempatLahir(){
        return tfOutTempatLahir.getText();
    }
    public String getTglLahir(){
        return df.format(datePicker.getDate());
    }
    public String getDomisili(){
        return tfOutDomisili.getText();
    }
    public String getDeskDiri(){
        return taOutDeskDiri.getText();
    }

}
