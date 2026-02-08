
package View;
import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.FlatLightLaf;
import java.util.logging.Level;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import main.MenuUtama;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;


public class laporanPeminjaman extends javax.swing.JPanel {
Connection conn;
PreparedStatement ps;
ResultSet rs;

int currentPage = 1;
int rowsPerPage = 8;
int totalData = 0;
int totalPage = 0;

private final int noColumnIndex = 0;
private final int nocolumnWidth = 100;
DefaultTableModel model;
  
private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
private Date tanggalMulai;
private Date tanggalAkhir;
private String statusPeminjaman = "Sudah dikembalikan";

    public laporanPeminjaman() {
        initComponents();
        conn = koneksi.koneksi.KoneksiDB();
       
          model = new DefaultTableModel(
        new Object[]{"ID","ID Anggota","Nama Anggota","Tgl Pinjam","Tgl Kembali","Tgl Dikembalikan", "Status", "Denda"}, 0
    );
    jTable1.setModel(model);
 setColumnWitdh();
    cbPage.removeAllItems();
    hitungTotalData();
    loadComboPage();
    loadTable();
    setLayout();
    txtcari.setPlaceholder("Serch");
    }

    public void setLayout(){
         txt_tglMulai.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Tanggal");
         txt_tglAkhir.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Tanggal");
      }
    
    void hitungTotalData() {
    try {
        ps = conn.prepareStatement("SELECT COUNT(*) FROM pengembalian");
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
    String tglMulai = txt_tglMulai.getText();
    String tglAkhir = txt_tglAkhir.getText();
    
    try {
    this.tanggalMulai = dateFormat.parse(tglMulai);
    this.tanggalAkhir = dateFormat.parse(tglAkhir);

    int offset = (currentPage - 1) * rowsPerPage;

    String sql =
        "SELECT " +
        " pmj.ID_Peminjaman, " +
        " agt.ID_Anggota, " +
        " agt.Nama_Anggota, " +
        " bk.ID_Buku, " +
        " pmj.Tanggal_Peminjaman, " +
        " pmj.Tanggal_Pengembalian, " +
        " png.Tanggal_Dikembalikan, " +
        " dpm.Status_Peminjaman, " +
        " SUM(dpn.Jumlah_Denda) AS Total_Denda " +
        "FROM pengembalian png " +
        "INNER JOIN detail_pengembalian dpn ON dpn.ID_Pengembalian = png.ID_Pengembalian " +
        "INNER JOIN peminjaman pmj ON pmj.ID_Peminjaman = png.ID_Peminjaman " +
        "INNER JOIN detail_peminjaman dpm ON dpm.ID_Peminjaman = pmj.ID_Peminjaman " +
        "INNER JOIN buku bk ON bk.ID_Buku = dpn.ID_Buku " +
        "INNER JOIN anggota agt ON agt.ID_Anggota = pmj.ID_Anggota " +
        "WHERE pmj.Tanggal_Peminjaman BETWEEN ? AND ? " +
        "AND dpm.Status_Peminjaman = ? " +
        "GROUP BY " +
        " png.ID_Pengembalian, pmj.ID_Peminjaman, agt.ID_Anggota, agt.Nama_Anggota, " +
        " bk.ID_Buku, pmj.Tanggal_Peminjaman, pmj.Tanggal_Pengembalian, " +
        " png.Tanggal_Dikembalikan, dpm.Status_Peminjaman " +
        "ORDER BY png.ID_Pengembalian ASC " +
        "LIMIT ? OFFSET ?";

    ps = conn.prepareStatement(sql);

    ps.setDate(1, new java.sql.Date(this.tanggalMulai.getTime()));
    ps.setDate(2, new java.sql.Date(this.tanggalAkhir.getTime()));
    ps.setString(3, statusPeminjaman);
    ps.setInt(4, rowsPerPage);
    ps.setInt(5, offset);

    rs = ps.executeQuery();

    while (rs.next()) {
        Object[] rowData = {
            rs.getString("ID_Peminjaman"),
            rs.getString("ID_Anggota"),
            rs.getString("Nama_Anggota"),
            rs.getString("Tanggal_Peminjaman"),
            rs.getString("Tanggal_Pengembalian"),
            rs.getString("Tanggal_Dikembalikan"),
            rs.getString("Status_Peminjaman"),
            rs.getString("Total_Denda")
        };
        model.addRow(rowData);
    }

} catch (Exception e) {
  JOptionPane.showMessageDialog(this, e.getMessage());
} 

}
    
    private void setColumnWitdh() {
        TableColumnModel columnModel = jTable1.getColumnModel();
        columnModel.getColumn(noColumnIndex).setPreferredWidth(nocolumnWidth);
        columnModel.getColumn(noColumnIndex).setMaxWidth(nocolumnWidth);
        columnModel.getColumn(noColumnIndex).setMinWidth(nocolumnWidth);
    }

    private void resetForm() {
       
    }
    
    
    void updatePage() {
    loadTable();
    cbPage.setSelectedItem(currentPage);
}
  
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        dateChooser1 = new com.raven.datechooser.DateChooser();
        dateChooser2 = new com.raven.datechooser.DateChooser();
        panelMain = new javax.swing.JPanel();
        panelView = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        btnTampilkan = new templet.IconButtonCustom();
        btnCancel = new templet.IconButtonCustom();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new templet.TableCustom();
        btnFirst = new javax.swing.JButton();
        btnPrev = new javax.swing.JButton();
        btnLast = new javax.swing.JButton();
        btnNext = new javax.swing.JButton();
        cbPage = new javax.swing.JComboBox<>();
        txtcari = new templet.TextfieldCustom1();
        jLabel9 = new javax.swing.JLabel();
        txt_tglMulai = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        txt_tglAkhir = new javax.swing.JTextField();
        btnPrint = new templet.IconButtonCustom();

        dateChooser1.setTextRefernce(txt_tglMulai);

        dateChooser2.setTextRefernce(txt_tglAkhir);

        setLayout(new java.awt.CardLayout());

        panelMain.setBackground(new java.awt.Color(255, 255, 255));
        panelMain.setLayout(new java.awt.CardLayout());

        panelView.setBackground(new java.awt.Color(255, 255, 255));

        jLabel13.setBackground(new java.awt.Color(102, 102, 102));
        jLabel13.setFont(new java.awt.Font("Bahnschrift", 1, 18)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(51, 51, 51));
        jLabel13.setText("Laporan Peminjaman Perpustakkan");

        jLabel14.setFont(new java.awt.Font("Bahnschrift", 1, 12)); // NOI18N
        jLabel14.setText("Laporan > Peminjaman");

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/mingcute_paper-line.png"))); // NOI18N

        btnTampilkan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/add.png"))); // NOI18N
        btnTampilkan.setText("TAMPILKAN");
        btnTampilkan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTampilkanActionPerformed(evt);
            }
        });

        btnCancel.setBackground(new java.awt.Color(255, 102, 0));
        btnCancel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/back.png"))); // NOI18N
        btnCancel.setText("BATAL");
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4", "Title 5", "Title 6", "Title 7", "Title 8", "Title 9"
            }
        ));
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
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

        txtcari.setSelectedTextColor(new java.awt.Color(51, 51, 51));
        txtcari.setSelectionColor(new java.awt.Color(153, 153, 153));
        txtcari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtcariActionPerformed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Bahnschrift", 0, 14)); // NOI18N
        jLabel9.setText("Tanggal Mulai");

        txt_tglMulai.setFont(new java.awt.Font("Bahnschrift", 0, 12)); // NOI18N
        txt_tglMulai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_tglMulaiActionPerformed(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Bahnschrift", 0, 14)); // NOI18N
        jLabel10.setText("Tanggal Akhir");

        txt_tglAkhir.setFont(new java.awt.Font("Bahnschrift", 0, 12)); // NOI18N
        txt_tglAkhir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_tglAkhirActionPerformed(evt);
            }
        });

        btnPrint.setBackground(new java.awt.Color(0, 204, 204));
        btnPrint.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Print.png"))); // NOI18N
        btnPrint.setText("PRINT");
        btnPrint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrintActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelViewLayout = new javax.swing.GroupLayout(panelView);
        panelView.setLayout(panelViewLayout);
        panelViewLayout.setHorizontalGroup(
            panelViewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelViewLayout.createSequentialGroup()
                .addGroup(panelViewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(panelViewLayout.createSequentialGroup()
                        .addContainerGap(21, Short.MAX_VALUE)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 792, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panelViewLayout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addGroup(panelViewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelViewLayout.createSequentialGroup()
                                .addGroup(panelViewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel9)
                                    .addComponent(txt_tglMulai, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(panelViewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel10)
                                    .addGroup(panelViewLayout.createSequentialGroup()
                                        .addComponent(txt_tglAkhir, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(btnTampilkan, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnPrint, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtcari, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(panelViewLayout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel13)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel14)))))
                .addGap(20, 20, 20))
            .addGroup(panelViewLayout.createSequentialGroup()
                .addGap(202, 202, 202)
                .addComponent(btnFirst)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnPrev, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(cbPage, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnNext, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnLast)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelViewLayout.setVerticalGroup(
            panelViewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelViewLayout.createSequentialGroup()
                .addGroup(panelViewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelViewLayout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelViewLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(panelViewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel13, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(18, 18, 18)
                .addGroup(panelViewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(panelViewLayout.createSequentialGroup()
                        .addGroup(panelViewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9)
                            .addComponent(jLabel10))
                        .addGap(5, 5, 5)
                        .addComponent(txt_tglMulai, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelViewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(panelViewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txt_tglAkhir, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnTampilkan, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnPrint, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(txtcari, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 386, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelViewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnFirst)
                    .addComponent(btnPrev)
                    .addComponent(btnLast)
                    .addComponent(btnNext)
                    .addComponent(cbPage, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18))
        );

        panelMain.add(panelView, "card2");

        add(panelMain, "card2");
    }// </editor-fold>//GEN-END:initComponents

    private void btnPrevActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrevActionPerformed
if (currentPage > 1) {
        currentPage--;
        updatePage();
    }
    }//GEN-LAST:event_btnPrevActionPerformed

    private void btnFirstActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFirstActionPerformed
        currentPage = 1;
    updatePage();
    }//GEN-LAST:event_btnFirstActionPerformed

    private void btnNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNextActionPerformed
         if (currentPage < totalPage) {
        currentPage++;
        updatePage();
    } 

    }//GEN-LAST:event_btnNextActionPerformed

    private void btnLastActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLastActionPerformed
      currentPage = totalPage;
    updatePage();
    }//GEN-LAST:event_btnLastActionPerformed

    private void btnTampilkanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTampilkanActionPerformed
        currentPage = 1;
    hitungTotalData();
    loadComboPage();
    loadTable();
    }//GEN-LAST:event_btnTampilkanActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        
    }//GEN-LAST:event_jTable1MouseClicked

    private void txtcariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtcariActionPerformed
        serchData();
    }//GEN-LAST:event_txtcariActionPerformed

    private void cbPageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbPageActionPerformed
        if (cbPage.getSelectedItem() != null) {
        int selectedPage = (Integer) cbPage.getSelectedItem();

        if (selectedPage != currentPage) {
            currentPage = selectedPage;
            loadTable();
        }
    }
    }//GEN-LAST:event_cbPageActionPerformed

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        txtcari.setText("");
        loadTable();
    }//GEN-LAST:event_btnCancelActionPerformed

    private void txt_tglMulaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_tglMulaiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_tglMulaiActionPerformed

    private void txt_tglAkhirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_tglAkhirActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_tglAkhirActionPerformed

    private void btnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrintActionPerformed
      cetakLaporan();
    }//GEN-LAST:event_btnPrintActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private templet.IconButtonCustom btnCancel;
    private javax.swing.JButton btnFirst;
    private javax.swing.JButton btnLast;
    private javax.swing.JButton btnNext;
    private javax.swing.JButton btnPrev;
    private templet.IconButtonCustom btnPrint;
    private templet.IconButtonCustom btnTampilkan;
    private javax.swing.JComboBox<Integer> cbPage;
    private com.raven.datechooser.DateChooser dateChooser1;
    private com.raven.datechooser.DateChooser dateChooser2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane2;
    private templet.TableCustom jTable1;
    private javax.swing.JPanel panelMain;
    private javax.swing.JPanel panelView;
    private javax.swing.JTextField txt_tglAkhir;
    private javax.swing.JTextField txt_tglMulai;
    private templet.TextfieldCustom1 txtcari;
    // End of variables declaration//GEN-END:variables

   private void serchData() {
    String kataKunci = txtcari.getText();

    DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
    model.setRowCount(0);

    try {
        String sql =
            "SELECT pmj.ID_Peminjaman, " +
            "agt.ID_Anggota, agt.Nama_Anggota, " +
            "bk.Judul_Buku, " +
            "pmj.Tanggal_Peminjaman, pmj.Tanggal_Pengembalian, " +
            "png.Tanggal_Dikembalikan, " +
            "dpm.Status_Peminjaman, " +
            "SUM(dpn.Jumlah_Denda) AS Total_Denda " +
            "FROM pengembalian png " +
            "INNER JOIN detail_pengembalian dpn ON dpn.ID_Pengembalian = png.ID_Pengembalian " +
            "INNER JOIN peminjaman pmj ON pmj.ID_Peminjaman = png.ID_Peminjaman " +
            "INNER JOIN detail_peminjaman dpm ON dpm.ID_Peminjaman = pmj.ID_Peminjaman " +
            "INNER JOIN buku bk ON bk.ID_Buku = dpn.ID_Buku " +
            "INNER JOIN anggota agt ON agt.ID_Anggota = pmj.ID_Anggota " +
            "WHERE dpm.Status_Peminjaman = 'Sudah dikembalikan' " +
            "AND (agt.Nama_Anggota LIKE ? OR bk.Judul_Buku LIKE ?) " +
            "GROUP BY png.ID_Pengembalian, pmj.ID_Peminjaman, agt.ID_Anggota, " +
            "agt.Nama_Anggota, bk.Judul_Buku, pmj.Tanggal_Peminjaman, " +
            "pmj.Tanggal_Pengembalian, png.Tanggal_Dikembalikan, dpm.Status_Peminjaman " +
            "ORDER BY png.ID_Pengembalian";

        PreparedStatement st = conn.prepareStatement(sql);
        st.setString(1, "%" + kataKunci + "%");
        st.setString(2, "%" + kataKunci + "%");

        ResultSet rs = st.executeQuery();

        while (rs.next()) {
            Object[] rowData = {
                rs.getString("ID_Peminjaman"),
                rs.getString("Nama_Anggota"),
                rs.getString("Judul_Buku"),
                rs.getString("Tanggal_Peminjaman"),
                rs.getString("Tanggal_Pengembalian"),
                rs.getString("Tanggal_Dikembalikan"),
                rs.getString("Status_Peminjaman"),
                rs.getInt("Total_Denda")
            };
            model.addRow(rowData);
        }

    } catch (SQLException e) {
        java.util.logging.Logger.getLogger(laporanPeminjaman.class.getName())
                .log(Level.SEVERE, null, e);
    }
}

   private void cetakLaporan() {
    try {
        if (jTable1.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this,
                "Tidak ada data untuk dicetak",
                "Informasi",
                JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        String tglMulaiText = txt_tglMulai.getText().trim();
        String tglAkhirText = txt_tglAkhir.getText().trim();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Date tglMulai = sdf.parse(tglMulaiText);
        Date tglAkhir = sdf.parse(tglAkhirText);

        HashMap<String, Object> parameters = new HashMap<>();
        parameters.put("tgl_Mulai", new java.sql.Date(tglMulai.getTime()));
        parameters.put("tgl_Akhir", new java.sql.Date(tglAkhir.getTime()));

        String reportPath = "D:/NetBeansProjects/ApkPerpus/src/Repost/LaporanPiminjaman.jasper";

        JasperPrint print =
            JasperFillManager.fillReport(reportPath, parameters, conn);

        if (print.getPages().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Tidak ada data untuk periode tersebut",
                "Informasi",
                JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        JasperViewer viewer = new JasperViewer(print, false);
        viewer.setExtendedState(JasperViewer.MAXIMIZED_BOTH);
        viewer.setVisible(true);

    } catch (Exception e) {
        JOptionPane.showMessageDialog(this,
            "Gagal mencetak laporan:\n" + e.getMessage(),
            "Error",
            JOptionPane.ERROR_MESSAGE);
    }
}

    
}
    
