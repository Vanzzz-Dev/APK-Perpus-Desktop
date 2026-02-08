package main;

import View.menuBukuSiswa;
import View.menuDasboard;
import View.menuProfil;
import com.formdev.flatlaf.FlatLightLaf;
import java.awt.Point;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import javax.swing.JPanel;
import javax.swing.Timer;

public class MenuUtama extends javax.swing.JFrame {
    String hari;
    String tanggal;
    String waktu;
    private String userID;
    private String levelUser;
    

    private MenuUtama() {
        
    }

    public String getUserID() {
        return userID;
    }

    public String getLevelUser() {
        return levelUser;
    }
    public MenuUtama(String userID, String namaUser, String levelUser) {
        initComponents();
        this.userID = userID;
        this.levelUser = levelUser;
        txtUser.setText(namaUser);
        jalankanWaktu();
        addMenu();
    }
    
    
    public JPanel getPanelUtama(){
     return pn_utama;
    }

    private void addMenu(){
    if(levelUser.equals("Admin")){
    sideMenu.add(new menuMaster(this));
    sideMenu.add(new menuTransaksi(this));
    sideMenu.add(new menuLaporan(this));
    }else if(levelUser.equals("Petugas")){
    sideMenu.add(new menuTransaksi(this));
    sideMenu.add(new menuLaporan(this));
    }else{
    sideMenu.add(new menuSiswa(this));
    }
   }
    
   private void jalankanWaktu() {
    Timer timer = new Timer(1000, e -> {
        Date now = new Date();

        Locale localeID = new Locale("id", "ID");

        SimpleDateFormat fHari = new SimpleDateFormat("EEEE", localeID);
        SimpleDateFormat fTanggal = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat fWaktu = new SimpleDateFormat("HH:mm:ss");

        hari = fHari.format(now);
        tanggal = fTanggal.format(now);
        waktu = fWaktu.format(now);

        Hari.setText(hari);     
        Tanggal.setText(tanggal);   
        Waktu.setText(waktu);     
    });
    timer.start();
}
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pn_utama = new javax.swing.JPanel();
        pn_kiri = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        sideMenu = new javax.swing.JPanel();
        jPanelGradient1 = new templet.JPanelGradient();
        Waktu = new javax.swing.JLabel();
        Tanggal = new javax.swing.JLabel();
        Hari = new javax.swing.JLabel();
        txtUser = new javax.swing.JLabel();
        Profil = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        pn_utama.setBackground(new java.awt.Color(255, 255, 255));
        pn_utama.setLayout(new java.awt.BorderLayout());

        pn_kiri.setBackground(new java.awt.Color(255, 255, 255));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/logo.png"))); // NOI18N

        jLabel1.setFont(new java.awt.Font("Bahnschrift", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(51, 51, 51));
        jLabel1.setText("   Wasita");

        jScrollPane1.setBorder(null);

        sideMenu.setBackground(new java.awt.Color(255, 255, 255));
        sideMenu.setLayout(new javax.swing.BoxLayout(sideMenu, javax.swing.BoxLayout.Y_AXIS));
        jScrollPane1.setViewportView(sideMenu);

        javax.swing.GroupLayout pn_kiriLayout = new javax.swing.GroupLayout(pn_kiri);
        pn_kiri.setLayout(pn_kiriLayout);
        pn_kiriLayout.setHorizontalGroup(
            pn_kiriLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn_kiriLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE)
                .addContainerGap())
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        pn_kiriLayout.setVerticalGroup(
            pn_kiriLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn_kiriLayout.createSequentialGroup()
                .addGroup(pn_kiriLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pn_kiriLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel2))
                    .addGroup(pn_kiriLayout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 558, Short.MAX_VALUE))
        );

        jPanelGradient1.setColorEnd(new java.awt.Color(0, 153, 204));
        jPanelGradient1.setColorStart(new java.awt.Color(0, 255, 255));

        Waktu.setFont(new java.awt.Font("Bahnschrift", 0, 10)); // NOI18N
        Waktu.setForeground(new java.awt.Color(255, 255, 255));
        Waktu.setText("00:00:00");

        Tanggal.setFont(new java.awt.Font("Bahnschrift", 0, 10)); // NOI18N
        Tanggal.setForeground(new java.awt.Color(255, 255, 255));
        Tanggal.setText("01-01-2026");

        Hari.setFont(new java.awt.Font("Bahnschrift", 0, 10)); // NOI18N
        Hari.setForeground(new java.awt.Color(255, 255, 255));
        Hari.setText("Selasa");

        txtUser.setFont(new java.awt.Font("Bahnschrift", 0, 18)); // NOI18N
        txtUser.setText("Pengguna");
        txtUser.addInputMethodListener(new java.awt.event.InputMethodListener() {
            public void caretPositionChanged(java.awt.event.InputMethodEvent evt) {
            }
            public void inputMethodTextChanged(java.awt.event.InputMethodEvent evt) {
                txtUserInputMethodTextChanged(evt);
            }
        });

        Profil.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/User2.png"))); // NOI18N
        Profil.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ProfilMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanelGradient1Layout = new javax.swing.GroupLayout(jPanelGradient1);
        jPanelGradient1.setLayout(jPanelGradient1Layout);
        jPanelGradient1Layout.setHorizontalGroup(
            jPanelGradient1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelGradient1Layout.createSequentialGroup()
                .addContainerGap(676, Short.MAX_VALUE)
                .addGroup(jPanelGradient1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanelGradient1Layout.createSequentialGroup()
                        .addComponent(Hari)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Tanggal)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Waktu))
                    .addComponent(txtUser, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(13, 13, 13)
                .addComponent(Profil)
                .addContainerGap())
        );
        jPanelGradient1Layout.setVerticalGroup(
            jPanelGradient1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelGradient1Layout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addGroup(jPanelGradient1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Profil, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanelGradient1Layout.createSequentialGroup()
                        .addComponent(txtUser)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanelGradient1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(Waktu)
                            .addComponent(Tanggal)
                            .addComponent(Hari))
                        .addGap(0, 3, Short.MAX_VALUE)))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(pn_kiri, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanelGradient1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pn_utama, javax.swing.GroupLayout.DEFAULT_SIZE, 859, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanelGradient1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(8, 8, 8)
                .addComponent(pn_utama, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(pn_kiri, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void txtUserInputMethodTextChanged(java.awt.event.InputMethodEvent evt) {//GEN-FIRST:event_txtUserInputMethodTextChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_txtUserInputMethodTextChanged

    private void ProfilMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ProfilMouseClicked
      menuProfil menu = new menuProfil(this, true, this);
        Point p = Profil.getLocationOnScreen();
        int x = p.x + Profil.getWidth() - menu.getWidth();
        int y = p.y + Profil.getHeight();
        menu.setLocation(x,y);
        menu.setVisible(true);
    }//GEN-LAST:event_ProfilMouseClicked

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
       if(levelUser.equals("Admin") || levelUser.equals("Petugas")){
        pn_utama.removeAll();
        pn_utama.add(new menuDasboard());
        pn_utama.repaint();
        pn_utama.revalidate();
       }else{
        pn_utama.removeAll();
        pn_utama.add(new menuBukuSiswa());
        pn_utama.repaint();
        pn_utama.revalidate();
       }
   
    }//GEN-LAST:event_formWindowOpened

    
    public static void main(String args[]) {
    FlatLightLaf.setup();
    java.awt.EventQueue.invokeLater(() -> new MenuUtama().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Hari;
    private javax.swing.JLabel Profil;
    private javax.swing.JLabel Tanggal;
    private javax.swing.JLabel Waktu;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private templet.JPanelGradient jPanelGradient1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel pn_kiri;
    private javax.swing.JPanel pn_utama;
    private javax.swing.JPanel sideMenu;
    private javax.swing.JLabel txtUser;
    // End of variables declaration//GEN-END:variables
}
