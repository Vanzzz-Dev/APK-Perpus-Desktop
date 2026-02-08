/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package View;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class menuDasboard extends javax.swing.JPanel {
Connection conn;
PreparedStatement ps;
ResultSet rs;


int currentPage = 1;
int rowsPerPage = 7;
int totalData = 0;
int totalPage = 0;

private final int noColumnIndex = 0;
private final int nocolumnWidth = 80;
DefaultTableModel model;

    public menuDasboard() {
        initComponents();
        conn = koneksi.koneksi.KoneksiDB();
        model = new DefaultTableModel(
        new Object[]{"ID","Tanggal Pinjam","Tanggal Kembali","Nama Anggota","Judul","Jumlah Pinjam","Status Pinjam"}, 0
    );
         jTable1.setModel(model);
        loadData();
        hitungTotalData();
    loadComboPage();
    loadTable();
    loadData();
    }
    
     void hitungTotalData() {
    try {
        ps = conn.prepareStatement("SELECT COUNT(*) FROM detail_peminjaman");
        rs = ps.executeQuery();
        if (rs.next()) {
            totalData = rs.getInt(1);
            totalPage = (int) Math.ceil((double) totalData / rowsPerPage);
        }
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this, e.getMessage());
    }
}
     void loadComboPage() {
    cbPage.removeAllItems();
    for (int i = 1; i <= totalPage; i++) {
        cbPage.addItem(i);
    }
    cbPage.setSelectedItem(currentPage);
}
     
      void loadTable() {
    model.setRowCount(0);
    int offset = (currentPage - 1) * rowsPerPage;

    try {
      ps = conn.prepareStatement(
    
    "SELECT pmd.ID_Peminjaman,pmj.Tanggal_Peminjaman,pmj.Tanggal_Pengembalian, agt.Nama_Anggota, bk.Judul_Buku, pmd.Jumlah_Pinjam, pmd.Status_Peminjaman\n" +
"FROM detail_Peminjaman pmd\n" +
"INNER JOIN peminjaman pmj ON pmj.ID_Peminjaman = pmd.ID_Peminjaman\n" +
"INNER JOIN buku bk ON bk.ID_Buku = pmd.ID_Buku\n" +
"INNER JOIN anggota agt ON agt.ID_Anggota = pmj.ID_Anggota LIMIT ? OFFSET ?"
);

ps.setInt(1, rowsPerPage);
ps.setInt(2, offset);

        rs = ps.executeQuery();

        while (rs.next()) {
            String idPeminjaman = rs.getString("ID_Peminjaman");
            String tanggalPeminjaman = rs.getString("Tanggal_Peminjaman");
            String tanggalPengembalian = rs.getString("Tanggal_Pengembalian");
            String namaAnggota = rs.getString("Nama_Anggota");
            String judulBuku = rs.getString("Judul_Buku");
            String jumlahPinjam = rs.getString("Jumlah_Pinjam");
            String statusPinjam = rs.getString("Status_Peminjaman");

            
            Object[] rowData = {idPeminjaman, tanggalPeminjaman, tanggalPengembalian, namaAnggota, judulBuku,jumlahPinjam,statusPinjam};
            model.addRow(rowData);
        }

    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this, e.getMessage());
    }
}
       void updatePage() {
    loadTable();
    cbPage.setSelectedItem(currentPage);
}

  
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        panelCustom1 = new templet.PanelCustom();
        jLabel1 = new javax.swing.JLabel();
        jumlah_Anggota = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        panelCustom2 = new templet.PanelCustom();
        jLabel4 = new javax.swing.JLabel();
        jumlah_Buku = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        panelCustom3 = new templet.PanelCustom();
        jLabel7 = new javax.swing.JLabel();
        jumlah_Pengembalian = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        panelCustom4 = new templet.PanelCustom();
        jLabel10 = new javax.swing.JLabel();
        jumlah_Peminjaman = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new templet.TableCustom();
        btnFirst = new javax.swing.JButton();
        btnPrev = new javax.swing.JButton();
        btnLast = new javax.swing.JButton();
        btnNext = new javax.swing.JButton();
        cbPage = new javax.swing.JComboBox<>();

        setLayout(new java.awt.CardLayout());

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        panelCustom1.setBackground(new java.awt.Color(100, 196, 237));
        panelCustom1.setRoundBottomLeft(15);
        panelCustom1.setRoundBottomRight(15);
        panelCustom1.setRoundTopLeft(15);
        panelCustom1.setRoundTopRight(15);

        jLabel1.setFont(new java.awt.Font("Bahnschrift", 1, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(102, 102, 102));
        jLabel1.setText("ANGGOTA");

        jumlah_Anggota.setFont(new java.awt.Font("Bahnschrift", 1, 36)); // NOI18N
        jumlah_Anggota.setText("999");

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/grup.png"))); // NOI18N

        javax.swing.GroupLayout panelCustom1Layout = new javax.swing.GroupLayout(panelCustom1);
        panelCustom1.setLayout(panelCustom1Layout);
        panelCustom1Layout.setHorizontalGroup(
            panelCustom1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelCustom1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(panelCustom1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jumlah_Anggota))
                .addGap(18, 18, 18)
                .addComponent(jLabel3)
                .addContainerGap(22, Short.MAX_VALUE))
        );
        panelCustom1Layout.setVerticalGroup(
            panelCustom1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelCustom1Layout.createSequentialGroup()
                .addGroup(panelCustom1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelCustom1Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(jLabel1)
                        .addGap(12, 12, 12)
                        .addComponent(jumlah_Anggota))
                    .addGroup(panelCustom1Layout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addComponent(jLabel3)))
                .addContainerGap(15, Short.MAX_VALUE))
        );

        panelCustom2.setBackground(new java.awt.Color(100, 196, 237));
        panelCustom2.setRoundBottomLeft(15);
        panelCustom2.setRoundBottomRight(15);
        panelCustom2.setRoundTopLeft(15);
        panelCustom2.setRoundTopRight(15);

        jLabel4.setFont(new java.awt.Font("Bahnschrift", 1, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(102, 102, 102));
        jLabel4.setText("BUKU");

        jumlah_Buku.setFont(new java.awt.Font("Bahnschrift", 1, 36)); // NOI18N
        jumlah_Buku.setText("999");

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Book.png"))); // NOI18N

        javax.swing.GroupLayout panelCustom2Layout = new javax.swing.GroupLayout(panelCustom2);
        panelCustom2.setLayout(panelCustom2Layout);
        panelCustom2Layout.setHorizontalGroup(
            panelCustom2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelCustom2Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(panelCustom2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(jumlah_Buku))
                .addGap(18, 18, 18)
                .addComponent(jLabel6)
                .addContainerGap(18, Short.MAX_VALUE))
        );
        panelCustom2Layout.setVerticalGroup(
            panelCustom2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelCustom2Layout.createSequentialGroup()
                .addGroup(panelCustom2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelCustom2Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(jLabel4)
                        .addGap(12, 12, 12)
                        .addComponent(jumlah_Buku))
                    .addGroup(panelCustom2Layout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addComponent(jLabel6)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panelCustom3.setBackground(new java.awt.Color(100, 196, 237));
        panelCustom3.setRoundBottomLeft(15);
        panelCustom3.setRoundBottomRight(15);
        panelCustom3.setRoundTopLeft(15);
        panelCustom3.setRoundTopRight(15);

        jLabel7.setFont(new java.awt.Font("Bahnschrift", 1, 12)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(102, 102, 102));
        jLabel7.setText("PEGIMBALIAN");

        jumlah_Pengembalian.setFont(new java.awt.Font("Bahnschrift", 1, 36)); // NOI18N
        jumlah_Pengembalian.setText("999");

        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Return Book.png"))); // NOI18N

        javax.swing.GroupLayout panelCustom3Layout = new javax.swing.GroupLayout(panelCustom3);
        panelCustom3.setLayout(panelCustom3Layout);
        panelCustom3Layout.setHorizontalGroup(
            panelCustom3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelCustom3Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(panelCustom3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7)
                    .addComponent(jumlah_Pengembalian))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel9)
                .addContainerGap(18, Short.MAX_VALUE))
        );
        panelCustom3Layout.setVerticalGroup(
            panelCustom3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelCustom3Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel7)
                .addGap(12, 12, 12)
                .addComponent(jumlah_Pengembalian)
                .addContainerGap(15, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelCustom3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel9)
                .addGap(27, 27, 27))
        );

        panelCustom4.setBackground(new java.awt.Color(100, 196, 237));
        panelCustom4.setRoundBottomLeft(15);
        panelCustom4.setRoundBottomRight(15);
        panelCustom4.setRoundTopLeft(15);
        panelCustom4.setRoundTopRight(15);

        jLabel10.setFont(new java.awt.Font("Bahnschrift", 1, 12)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(102, 102, 102));
        jLabel10.setText("PEMINJAMAN");

        jumlah_Peminjaman.setFont(new java.awt.Font("Bahnschrift", 1, 36)); // NOI18N
        jumlah_Peminjaman.setText("999");

        jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Borrow Book.png"))); // NOI18N

        javax.swing.GroupLayout panelCustom4Layout = new javax.swing.GroupLayout(panelCustom4);
        panelCustom4.setLayout(panelCustom4Layout);
        panelCustom4Layout.setHorizontalGroup(
            panelCustom4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelCustom4Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(panelCustom4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel10)
                    .addComponent(jumlah_Peminjaman))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel12)
                .addContainerGap(18, Short.MAX_VALUE))
        );
        panelCustom4Layout.setVerticalGroup(
            panelCustom4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelCustom4Layout.createSequentialGroup()
                .addGroup(panelCustom4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelCustom4Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(jLabel10)
                        .addGap(12, 12, 12)
                        .addComponent(jumlah_Peminjaman))
                    .addGroup(panelCustom4Layout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addComponent(jLabel12)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel13.setFont(new java.awt.Font("Bahnschrift", 1, 18)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(153, 153, 153));
        jLabel13.setText("Riwayat Peminjaman Buku");

        jLabel14.setFont(new java.awt.Font("Bahnschrift", 1, 12)); // NOI18N
        jLabel14.setText("Master Data > Dasboard");

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4", "Title 5", "Title 6", "Title 7", "Title 8"
            }
        ));
        jScrollPane2.setViewportView(jTable1);

        btnFirst.setText("First Page");
        btnFirst.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFirstActionPerformed(evt);
            }
        });

        btnPrev.setText("<");
        btnPrev.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrevActionPerformed(evt);
            }
        });

        btnLast.setText("Last Page");
        btnLast.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLastActionPerformed(evt);
            }
        });

        btnNext.setText(">");
        btnNext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNextActionPerformed(evt);
            }
        });

        cbPage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbPageActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(24, 24, 24)
                                .addComponent(panelCustom1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(panelCustom2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(panelCustom4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(panelCustom3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(20, 20, 20)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel14)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel13)
                                        .addGap(565, 565, 565))))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(220, 220, 220)
                                .addComponent(btnFirst)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnPrev, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(cbPage, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnNext, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnLast)))
                        .addGap(0, 13, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane2)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(panelCustom1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(panelCustom2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(panelCustom4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(panelCustom3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jLabel13)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 321, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnFirst)
                    .addComponent(btnPrev)
                    .addComponent(btnLast)
                    .addComponent(btnNext)
                    .addComponent(cbPage, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(13, 13, 13))
        );

        add(jPanel1, "card2");
    }// </editor-fold>//GEN-END:initComponents

    private void btnFirstActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFirstActionPerformed
        currentPage = 1;
        updatePage();
    }//GEN-LAST:event_btnFirstActionPerformed

    private void btnPrevActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrevActionPerformed
        if (currentPage > 1) {
            currentPage--;
            updatePage();
        }
    }//GEN-LAST:event_btnPrevActionPerformed

    private void btnLastActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLastActionPerformed
        currentPage = totalPage;
        updatePage();
    }//GEN-LAST:event_btnLastActionPerformed

    private void btnNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNextActionPerformed
        if (currentPage < totalPage) {
            currentPage++;
            updatePage();
        }
    }//GEN-LAST:event_btnNextActionPerformed

    private void cbPageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbPageActionPerformed
        if (cbPage.getSelectedItem() != null) {
            int selectedPage = (Integer) cbPage.getSelectedItem();

            if (selectedPage != currentPage) {
                currentPage = selectedPage;
                loadTable();
            }
        }
    }//GEN-LAST:event_cbPageActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnFirst;
    private javax.swing.JButton btnLast;
    private javax.swing.JButton btnNext;
    private javax.swing.JButton btnPrev;
    private javax.swing.JComboBox<Integer> cbPage;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane2;
    private templet.TableCustom jTable1;
    private javax.swing.JLabel jumlah_Anggota;
    private javax.swing.JLabel jumlah_Buku;
    private javax.swing.JLabel jumlah_Peminjaman;
    private javax.swing.JLabel jumlah_Pengembalian;
    private templet.PanelCustom panelCustom1;
    private templet.PanelCustom panelCustom2;
    private templet.PanelCustom panelCustom3;
    private templet.PanelCustom panelCustom4;
    // End of variables declaration//GEN-END:variables

    private int jumlahAnggota(){
        int totalAnggota = 0;
        
        try {
            String sql = ("SELECT COUNT(*) AS total FROM anggota");
            PreparedStatement st = conn.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            
            if(rs.next()){
             totalAnggota = rs.getInt("total");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return totalAnggota;
    }
    private int jumlahBuku(){
        int totalAnggota = 0;
        
        try {
            String sql = ("SELECT COUNT(*) AS total FROM buku");
            PreparedStatement st = conn.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            
            if(rs.next()){
             totalAnggota = rs.getInt("total");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return totalAnggota;
    }
    private int jumlahPeminjaman(){
        int totalAnggota = 0;
        
        try {
            String sql = ("SELECT COUNT(*) AS total FROM peminjaman");
            PreparedStatement st = conn.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            
            if(rs.next()){
             totalAnggota = rs.getInt("total");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return totalAnggota;
    }
    private int jumlahPengembalian(){
        int totalAnggota = 0;
        
        try {
            String sql = ("SELECT COUNT(*) AS total FROM pengembalian");
            PreparedStatement st = conn.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            
            if(rs.next()){
             totalAnggota = rs.getInt("total");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return totalAnggota;
    }
    
    private void loadData() {
        jumlah_Anggota.setText(String.valueOf(jumlahAnggota()));
        jumlah_Buku.setText(String.valueOf(jumlahBuku()));
        jumlah_Peminjaman.setText(String.valueOf(jumlahPeminjaman()));
        jumlah_Pengembalian.setText(String.valueOf(jumlahPengembalian()));
    }
}
