
package View;
import com.formdev.flatlaf.FlatLightLaf;
import java.awt.Color;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import main.MenuUtama;

public class FromLogin extends javax.swing.JFrame {
Connection conn;
PreparedStatement ps;
ResultSet rs;
int xx, xy;
    
    public FromLogin() {
        initComponents();
        conn = koneksi.koneksi.KoneksiDB();
        off.setVisible(false);
        setBackground(new Color(0,0,0,0));
    }

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        on = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtUser = new templet.TextfieldCustom1();
        txtPass = new templet.PasswordFieldCustom();
        off = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                formMouseDragged(evt);
            }
        });
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                formMousePressed(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/close.png"))); // NOI18N
        jLabel5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel5MouseClicked(evt);
            }
        });
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(830, 0, -1, 20));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Formlogin.png"))); // NOI18N
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 0, -1, 480));

        jButton1.setBackground(new java.awt.Color(0, 255, 255));
        jButton1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Login");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 370, 240, 40));

        on.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/eyes.png"))); // NOI18N
        on.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                onMouseClicked(evt);
            }
        });
        jPanel1.add(on, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 290, -1, 40));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(102, 102, 102));
        jLabel2.setText("Welcoome");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 70, -1, -1));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(102, 102, 102));
        jLabel3.setText("Password");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 250, -1, -1));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(102, 102, 102));
        jLabel4.setText("Username");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 150, -1, -1));

        txtUser.setSelectionColor(new java.awt.Color(153, 153, 153));
        jPanel1.add(txtUser, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 190, 240, 40));

        txtPass.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtPassMouseClicked(evt);
            }
        });
        txtPass.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPassActionPerformed(evt);
            }
        });
        jPanel1.add(txtPass, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 290, 240, 40));

        off.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/eyes_slash.png"))); // NOI18N
        jPanel1.add(off, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 290, -1, 40));

        jLabel6.setFont(new java.awt.Font("Bahnschrift", 0, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(204, 204, 204));
        jLabel6.setText("Register");
        jLabel6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel6MouseClicked(evt);
            }
        });
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 420, -1, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 479, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void formMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMousePressed
       xx = evt.getX();
      xy = evt.getY();
    }//GEN-LAST:event_formMousePressed

    private void formMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseDragged
      int x = evt.getXOnScreen();
      int y = evt.getYOnScreen();
      this.setLocation(x - xx, y - xy);
    }//GEN-LAST:event_formMouseDragged

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
    prosesLogin();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void txtPassActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPassActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPassActionPerformed

    private void onMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_onMouseClicked
        on.setVisible(false);
      off.setVisible(true);
      
      txtPass.setEchoChar((char)0);
    }//GEN-LAST:event_onMouseClicked

    private void txtPassMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtPassMouseClicked
        on.setVisible(true);
      off.setVisible(false);
      
      txtPass.setEchoChar('.');
    }//GEN-LAST:event_txtPassMouseClicked

    private void jLabel5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel5MouseClicked
         System.exit(0);
    }//GEN-LAST:event_jLabel5MouseClicked

    private void jLabel6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel6MouseClicked
    FormRegister mn = new FormRegister();
            mn.setVisible(true);
            mn.revalidate();   
            this.dispose();
    }//GEN-LAST:event_jLabel6MouseClicked

    
    public static void main(String args[]) {
       FlatLightLaf.setup();
        java.awt.EventQueue.invokeLater(() -> new FromLogin().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel off;
    private javax.swing.JLabel on;
    private templet.PasswordFieldCustom txtPass;
    private templet.TextfieldCustom1 txtUser;
    // End of variables declaration//GEN-END:variables
    
    private boolean validasiInput(){
     boolean valid = false;
     
     if(txtUser.getText().trim().isEmpty()){
      JOptionPane.showMessageDialog(this, "Username tidak boleh kosong");
     } else if(txtPass.getText().trim().isEmpty()){
      JOptionPane.showMessageDialog(this, "Password tidak boleh kosong");
     }else{
      valid = true;
     }
     return valid;
    }
    
private Map<String, String> checkLogin(String username, String password) {
    Map<String, String> result = new HashMap<>();

    if (conn == null) return null;

    try {
        String sqlUser = "SELECT * FROM user WHERE Username=? AND Password=?";
        PreparedStatement psUser = conn.prepareStatement(sqlUser);
        psUser.setString(1, username);
        psUser.setString(2, password);

        ResultSet rsUser = psUser.executeQuery();
        if (rsUser.next()) {
            result.put("ID", rsUser.getString("ID_User"));
            result.put("Nama", rsUser.getString("Nama_User"));
            result.put("Level", rsUser.getString("Level"));
            result.put("Tipe", "USER");
            return result;
        }

         String sqlAnggota = "SELECT * FROM anggota WHERE Nama_Anggota=? AND Password=?";
        PreparedStatement psAnggota = conn.prepareStatement(sqlAnggota);
        psAnggota.setString(1, username); 
        psAnggota.setString(2, password);

        ResultSet rsAnggota = psAnggota.executeQuery();
        if (rsAnggota.next()) {
            result.put("ID", rsAnggota.getString("ID_Anggota"));
            result.put("Nama", rsAnggota.getString("Nama_Anggota"));
            result.put("Level", rsAnggota.getString("LevelAnggota"));
            result.put("Tipe", "ANGGOTA");
            return result;
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }

    return null;
}


private void prosesLogin() {
    if (validasiInput()) {
        String username = txtUser.getText();
        String password = new String(txtPass.getPassword());

        Map<String, String> loginResult = checkLogin(username, password);

        if (loginResult != null) {
            String userID   = loginResult.get("ID");
            String namaUser = loginResult.get("Nama");
            String levelUser = loginResult.get("Level");
            String tipeUser  = loginResult.get("Tipe");

            MenuUtama mn = new MenuUtama(userID, namaUser, levelUser);
            mn.setVisible(true);
            mn.revalidate();

            dispose();
        } else {
            JOptionPane.showMessageDialog(
                this,
                "Username dan Password Salah",
                "Pesan",
                JOptionPane.INFORMATION_MESSAGE
            );
        }
    }
}


    
}
