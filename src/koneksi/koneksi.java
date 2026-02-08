package koneksi;

import java.sql.*;   // memanggil library untuk SQL (JDBC)
import javax.swing.*;// memanggil library Swing (untuk GUI, seperti JOptionPane)

public class koneksi {
    public static Connection KoneksiDB(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/perpus","root","");
                  return conn;
        }catch (ClassNotFoundException | SQLException e){
            JOptionPane.showMessageDialog(null, "Server Belum Nyala \n Ulangi Apk","Pesan",JOptionPane.WARNING_MESSAGE);
        } 
        return null;
    }
    
//    public static void main(String[] args) {
//        Connection test = KoneksiDB();
//        if (test != null) {
//            JOptionPane.showMessageDialog(null, "Koneksi berhasil!");
//        } else {
//            JOptionPane.showMessageDialog(null, "Koneksi gagal!");
//        }
//    }
}



