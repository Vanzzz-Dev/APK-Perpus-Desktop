
package View;
import javax.swing.JLabel;
import com.formdev.flatlaf.FlatClientProperties;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;



public class menuPeminjaman extends javax.swing.JPanel {
Connection conn;
PreparedStatement ps;
ResultSet rs;

int currentPage = 1;
int rowsPerPage = 6;
int totalData = 0;
int totalPage = 0;

private final int noColumnIndex = 0;
private final int nocolumnWidth = 80;
private String userID;
DefaultTableModel model;
  
    public menuPeminjaman(String userID) {
        initComponents();
        conn = koneksi.koneksi.KoneksiDB();
       
          model = new DefaultTableModel(
        new Object[]{"ID","Tanggal Pinjam","Tanggal Kembali","Total Pinjam","Anggota","Petugas"}, 0
    );
    jTable1.setModel(model);
        this.userID = userID;
    setColumnWitdh();
    cbPage.removeAllItems();
    hitungTotalData();
    loadComboPage();
    loadTable();
    setLayout();
    visbleBtn();
    loadData();
    setTableSementara();
    loadDataSementara();
    setModelTableDetail();
    txtcari.setPlaceholder("Serch");
    
    txtJumlah.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               if(btnUbah.getText().equals("TAMBAH")){
                   insertDataSementara();
               }else if(btnUbah.getText().equals("UBAH")){
                   updateData();
               }
            }
     
    });
    
    btnUbah.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(btnUbah.getText().equals("TAMBAH")){
                 insertDataSementara();
                }else if(btnUbah.getText().equals("UBAH")){
                 updateData();
                }
            }        
    });
    }
    
    public void setModelTableDetail(){
        DefaultTableModel model = (DefaultTableModel) TableDetail.getModel();
        model.addColumn("ID");
        model.addColumn("ID Buku");
        model.addColumn("Judul");
        model.addColumn("Jumlah");
        model.addColumn("Status Peminjaman");
    }

    public void visbleBtn(){
        btnDelete.setVisible(false);
        btnBatal2.setVisible(false);
        btnUbah.setVisible(false);
    }
    public void setLayout(){
        txtID.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "ID Peminjaman");
        txtPinjam.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Tanggal Peminjaman");
        txtKembali.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Tanggal Pengebalian");
        txtidAnggota.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "ID Anggota");
        txtNama.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Nama Anggota");
        txtEmail.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Email");
        txtTelpon.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Telpon");
        txtidBuku.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "ID Buku");
        txtJudul.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Judul Buku");
        txtPengarang.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Pengarang");
        txtPenerbit.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Penerbit");
        txtJumlah.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Jumlah");
        
      }
    
    void hitungTotalData() {
    try {
        ps = conn.prepareStatement("SELECT COUNT(*) FROM peminjaman");
        rs = ps.executeQuery();
        if (rs.next()) {
            totalData = rs.getInt(1);
            totalPage = (int) Math.ceil((double) totalData / rowsPerPage);
        }
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this, e.getMessage());
    }
}
    
   void loadData(){
       
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
    
    " SELECT pm.ID_Peminjaman, pm.Tanggal_Peminjaman, pm.Tanggal_Pengembalian, pm.Total_Pinjam, agt.Nama_Anggota, usr.Nama_User\n" +
"FROM peminjaman pm\n" +
"INNER JOIN anggota agt ON agt.ID_Anggota = pm.ID_Anggota\n" +
"INNER JOIN USER usr ON usr.ID_User = pm.ID_User LIMIT ? OFFSET ?"
);

ps.setInt(1, rowsPerPage);
ps.setInt(2, offset);

        rs = ps.executeQuery();

        while (rs.next()) {
            String idBuku = rs.getString("ID_Peminjaman");
            String tanggalPeminjaman = rs.getString("Tanggal_Peminjaman");
            String tanggalPengembalian = rs.getString("Tanggal_Pengembalian");
            String totalPinjam = rs.getString("Total_Pinjam");
            String namaAnggota = rs.getString("Nama_Anggota");
            String namaUser = rs.getString("Nama_User");

            
            Object[] rowData = {idBuku, tanggalPeminjaman, tanggalPengembalian, totalPinjam,  namaAnggota, namaUser};
            model.addRow(rowData);
        }

    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this, e.getMessage());
    }
}
    
  void loadTableDetail(DefaultTableModel model, String id) {
    model.setRowCount(0);

    try {
        ps = conn.prepareStatement(
            "SELECT pmd.ID_Peminjaman, bk.ID_Buku, bk.Judul_Buku, " +
            "pmd.Jumlah_Pinjam, pmd.Status_Peminjaman " +
            "FROM detail_peminjaman pmd " +
            "INNER JOIN buku bk ON bk.ID_Buku = pmd.ID_Buku " +
            "WHERE pmd.ID_Peminjaman = ? "
        );

        ps.setString(1, id);

        rs = ps.executeQuery();

        while (rs.next()) {
            Object[] rowData = {
                rs.getString("ID_Peminjaman"),
                rs.getString("ID_Buku"),
                rs.getString("Judul_Buku"),
                rs.getString("Jumlah_Pinjam"),
                rs.getString("Status_Peminjaman")
            };
            model.addRow(rowData);
        }

    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this, e.getMessage());
    }
}

    
    private void loadDataSementara(){
     getDataSementara((DefaultTableModel) TableSementara.getModel());
    }
    
    private void setColumnWitdh() {
        TableColumnModel columnModel = jTable1.getColumnModel();
        columnModel.getColumn(noColumnIndex).setPreferredWidth(nocolumnWidth);
        columnModel.getColumn(noColumnIndex).setMaxWidth(nocolumnWidth);
        columnModel.getColumn(noColumnIndex).setMinWidth(nocolumnWidth);
    }

    private void resetForm() {
       txtidAnggota.setText("");
       txtNama.setText("");
       txtEmail.setText("");
       txtTelpon.setText("");
       
    }
    
    private void showPanel(){
        panelMain.removeAll();
        panelMain.add(new menuPeminjaman(userID));
        panelMain.repaint();
        panelMain.revalidate();
    }
    
    void updatePage() {
    loadTable();
    cbPage.setSelectedItem(currentPage);
}
    
    private void setTableSementara(){
     DefaultTableModel model = (DefaultTableModel) TableSementara.getModel();
     model.addColumn("ID");
     model.addColumn("Judul");
     model.addColumn("Pengarang");
     model.addColumn("Penerbit");
     model.addColumn("Jumlah Pinjam");
    }
    
      void getDataSementara(DefaultTableModel model) {
    model.setRowCount(0);
    int offset = (currentPage - 1) * rowsPerPage;

    try {
        ps = conn.prepareStatement("SELECT * FROM sementara");

        rs = ps.executeQuery();

        while (rs.next()) {
            String idBuku   = rs.getString("ID_Buku");
            String judulBuku = rs.getString("Judul_Buku");
            String pengarangBuku = rs.getString("Pengarang_Buku");
            String penerbitBuku = rs.getString("Penerbit_Buku");
            String jumlahPinjam = rs.getString("Jumlah_Pinjam");
            
            Object[] rowData = {idBuku, judulBuku, pengarangBuku, penerbitBuku, jumlahPinjam};
            model.addRow(rowData);
        }

    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this, e.getMessage());
    }
   
}
      
      private void resetFormBuku(){
      txtidBuku.setText("");
      txtJudul.setText("");
      txtPengarang.setText("");
      txtPenerbit.setText("");
      txtJumlah.setText("");
      lbTotal.setText("");
      lbGambar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/book2.png")));
      }
  
      
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        dateChooser1 = new com.raven.datechooser.DateChooser();
        dateChooser2 = new com.raven.datechooser.DateChooser();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        panelMain = new javax.swing.JPanel();
        panelView = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        btnAdd = new templet.IconButtonCustom();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new templet.TableCustom();
        btnFirst = new javax.swing.JButton();
        btnPrev = new javax.swing.JButton();
        btnLast = new javax.swing.JButton();
        btnNext = new javax.swing.JButton();
        cbPage = new javax.swing.JComboBox<>();
        txtcari = new templet.TextfieldCustom1();
        jScrollPane3 = new javax.swing.JScrollPane();
        TableDetail = new templet.TableCustom();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        panelAdd = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        btnSave = new templet.IconButtonCustom();
        btnBatal = new templet.IconButtonCustom();
        lbGambar = new javax.swing.JLabel();
        panelCustom1 = new templet.PanelCustom();
        jLabel3 = new javax.swing.JLabel();
        txtID = new javax.swing.JTextField();
        txtPinjam = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtKembali = new javax.swing.JTextField();
        txtidAnggota = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtNama = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txtEmail = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txtTelpon = new javax.swing.JTextField();
        btnsetAnggota = new javax.swing.JButton();
        txtidBuku = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        txtJudul = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        txtPengarang = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        txtPenerbit = new javax.swing.JTextField();
        btnsetBuku = new javax.swing.JButton();
        jLabel18 = new javax.swing.JLabel();
        txtJumlah = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        lbTotal = new javax.swing.JLabel();
        btnUbah = new templet.IconButtonCustom();
        btnDelete = new templet.IconButtonCustom();
        btnBatal2 = new templet.IconButtonCustom();
        jScrollPane4 = new javax.swing.JScrollPane();
        TableSementara = new javax.swing.JTable();

        dateChooser1.setTextRefernce(txtPinjam);
        dateChooser1.setVerifyInputWhenFocusTarget(false);

        dateChooser2.setTextRefernce(txtKembali);

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jTable2);

        setLayout(new java.awt.CardLayout());

        panelMain.setBackground(new java.awt.Color(255, 255, 255));
        panelMain.setLayout(new java.awt.CardLayout());

        panelView.setBackground(new java.awt.Color(255, 255, 255));

        jLabel13.setBackground(new java.awt.Color(102, 102, 102));
        jLabel13.setFont(new java.awt.Font("Bahnschrift", 1, 18)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(51, 51, 51));
        jLabel13.setText("Peminjaman Buku Perpustakaan");

        jLabel14.setFont(new java.awt.Font("Bahnschrift", 1, 12)); // NOI18N
        jLabel14.setText("Transaksi > Peminjaman");

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/lucide_book-down.png"))); // NOI18N

        btnAdd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/add.png"))); // NOI18N
        btnAdd.setText("TAMBAH");
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4", "Title 5", "Title 6"
            }
        ));
        jTable1.setRowHeight(39);
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

        TableDetail.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {}
            },
            new String [] {

            }
        ));
        TableDetail.setRowHeight(56);
        TableDetail.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TableDetailMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(TableDetail);

        jLabel20.setBackground(new java.awt.Color(102, 102, 102));
        jLabel20.setFont(new java.awt.Font("Bahnschrift", 1, 18)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(51, 51, 51));
        jLabel20.setText("Detail Peminjaman Buku Perpustakaan");

        jLabel21.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/book_icon.png"))); // NOI18N

        javax.swing.GroupLayout panelViewLayout = new javax.swing.GroupLayout(panelView);
        panelView.setLayout(panelViewLayout);
        panelViewLayout.setHorizontalGroup(
            panelViewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelViewLayout.createSequentialGroup()
                .addGroup(panelViewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(panelViewLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(panelViewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 792, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panelViewLayout.createSequentialGroup()
                                .addComponent(jLabel21)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 348, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(panelViewLayout.createSequentialGroup()
                        .addContainerGap(27, Short.MAX_VALUE)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 792, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelViewLayout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addGroup(panelViewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelViewLayout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel13)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel14))
                            .addGroup(panelViewLayout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txtcari, javax.swing.GroupLayout.PREFERRED_SIZE, 313, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(24, 24, 24)))))
                .addContainerGap(27, Short.MAX_VALUE))
            .addGroup(panelViewLayout.createSequentialGroup()
                .addGap(217, 217, 217)
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
                            .addComponent(jLabel13, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(15, 15, 15)
                        .addGroup(panelViewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtcari, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 14, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 276, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelViewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnFirst)
                    .addComponent(btnPrev)
                    .addComponent(btnLast)
                    .addComponent(btnNext)
                    .addComponent(cbPage, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelViewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel21))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31))
        );

        panelMain.add(panelView, "card2");

        panelAdd.setBackground(new java.awt.Color(255, 255, 255));

        jLabel15.setBackground(new java.awt.Color(102, 102, 102));
        jLabel15.setFont(new java.awt.Font("Bahnschrift", 1, 18)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(51, 51, 51));
        jLabel15.setText("Tambah Data Peminjaman Buku Perpustakaan");

        jLabel16.setFont(new java.awt.Font("Bahnschrift", 1, 12)); // NOI18N
        jLabel16.setText("Transaksi > Buku");

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/lucide_book-down.png"))); // NOI18N

        btnSave.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/add.png"))); // NOI18N
        btnSave.setText("SIMPAN");
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        btnBatal.setBackground(new java.awt.Color(255, 102, 0));
        btnBatal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/back.png"))); // NOI18N
        btnBatal.setText("BATAL");
        btnBatal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBatalActionPerformed(evt);
            }
        });

        lbGambar.setFont(new java.awt.Font("Bahnschrift", 0, 14)); // NOI18N
        lbGambar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/book2.png"))); // NOI18N
        lbGambar.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        lbGambar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));

        panelCustom1.setBackground(new java.awt.Color(248, 247, 247));
        panelCustom1.setRoundBottomLeft(15);
        panelCustom1.setRoundBottomRight(15);
        panelCustom1.setRoundTopLeft(15);
        panelCustom1.setRoundTopRight(15);

        jLabel3.setFont(new java.awt.Font("Bahnschrift", 0, 14)); // NOI18N
        jLabel3.setText("ID");

        jLabel5.setFont(new java.awt.Font("Bahnschrift", 0, 14)); // NOI18N
        jLabel5.setText("Tanggal Kembali");

        jLabel4.setFont(new java.awt.Font("Bahnschrift", 0, 14)); // NOI18N
        jLabel4.setText("Tanggal Pinjam");

        javax.swing.GroupLayout panelCustom1Layout = new javax.swing.GroupLayout(panelCustom1);
        panelCustom1.setLayout(panelCustom1Layout);
        panelCustom1Layout.setHorizontalGroup(
            panelCustom1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelCustom1Layout.createSequentialGroup()
                .addGap(66, 66, 66)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtID, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtPinjam, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(61, 61, 61)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtKembali, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(17, 17, 17))
        );
        panelCustom1Layout.setVerticalGroup(
            panelCustom1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelCustom1Layout.createSequentialGroup()
                .addContainerGap(23, Short.MAX_VALUE)
                .addGroup(panelCustom1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelCustom1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel3))
                    .addGroup(panelCustom1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtPinjam, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel4))
                    .addGroup(panelCustom1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtKembali, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel5)))
                .addGap(20, 20, 20))
        );

        jLabel6.setFont(new java.awt.Font("Bahnschrift", 0, 14)); // NOI18N
        jLabel6.setText("Anggota");

        jLabel7.setFont(new java.awt.Font("Bahnschrift", 0, 14)); // NOI18N
        jLabel7.setText("Nama");

        jLabel8.setFont(new java.awt.Font("Bahnschrift", 0, 14)); // NOI18N
        jLabel8.setText("Email");

        jLabel9.setFont(new java.awt.Font("Bahnschrift", 0, 14)); // NOI18N
        jLabel9.setText("Telepon");

        btnsetAnggota.setText("...");
        btnsetAnggota.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnsetAnggotaActionPerformed(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Bahnschrift", 0, 14)); // NOI18N
        jLabel10.setText("Buku");

        jLabel11.setFont(new java.awt.Font("Bahnschrift", 0, 14)); // NOI18N
        jLabel11.setText("Judul");

        jLabel12.setFont(new java.awt.Font("Bahnschrift", 0, 14)); // NOI18N
        jLabel12.setText("Pengarang");

        jLabel17.setFont(new java.awt.Font("Bahnschrift", 0, 14)); // NOI18N
        jLabel17.setText("Penerbit");

        btnsetBuku.setText("...");
        btnsetBuku.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnsetBukuActionPerformed(evt);
            }
        });

        jLabel18.setFont(new java.awt.Font("Bahnschrift", 0, 14)); // NOI18N
        jLabel18.setText("Jumlah");

        txtJumlah.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtJumlahMouseClicked(evt);
            }
        });
        txtJumlah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtJumlahActionPerformed(evt);
            }
        });

        jLabel19.setFont(new java.awt.Font("Bahnschrift", 0, 14)); // NOI18N
        jLabel19.setText("Total Pinjam");

        lbTotal.setFont(new java.awt.Font("Bahnschrift", 0, 24)); // NOI18N
        lbTotal.setText("Total");

        btnUbah.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/add.png"))); // NOI18N
        btnUbah.setText("UBAH");
        btnUbah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUbahActionPerformed(evt);
            }
        });

        btnDelete.setBackground(new java.awt.Color(255, 0, 51));
        btnDelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/cancel2.png"))); // NOI18N
        btnDelete.setText("HAPUS");
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        btnBatal2.setBackground(new java.awt.Color(255, 102, 0));
        btnBatal2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/back.png"))); // NOI18N
        btnBatal2.setText("BATAL");
        btnBatal2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBatal2ActionPerformed(evt);
            }
        });

        TableSementara.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        TableSementara.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TableSementaraMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(TableSementara);

        javax.swing.GroupLayout panelAddLayout = new javax.swing.GroupLayout(panelAdd);
        panelAdd.setLayout(panelAddLayout);
        panelAddLayout.setHorizontalGroup(
            panelAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelAddLayout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(panelAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelAddLayout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel15)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel16)
                        .addGap(14, 14, 14))
                    .addGroup(panelAddLayout.createSequentialGroup()
                        .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnBatal, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(622, 622, Short.MAX_VALUE))))
            .addGroup(panelAddLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelCustom1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelAddLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(panelAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(panelAddLayout.createSequentialGroup()
                        .addGroup(panelAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelAddLayout.createSequentialGroup()
                                .addGap(3, 3, 3)
                                .addGroup(panelAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelAddLayout.createSequentialGroup()
                                        .addComponent(jLabel6)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(txtidAnggota, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnsetAnggota, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(panelAddLayout.createSequentialGroup()
                                        .addComponent(jLabel7)
                                        .addGap(26, 26, 26)
                                        .addComponent(txtNama, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(panelAddLayout.createSequentialGroup()
                                        .addComponent(jLabel8)
                                        .addGap(28, 28, 28)
                                        .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelAddLayout.createSequentialGroup()
                                .addComponent(jLabel9)
                                .addGap(18, 18, 18)
                                .addComponent(txtTelpon, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(66, 66, 66)
                        .addGroup(panelAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(panelAddLayout.createSequentialGroup()
                                .addComponent(jLabel12)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtPengarang, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(panelAddLayout.createSequentialGroup()
                                .addComponent(jLabel17)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txtPenerbit, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(panelAddLayout.createSequentialGroup()
                                .addGroup(panelAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel11)
                                    .addComponent(jLabel10))
                                .addGroup(panelAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(panelAddLayout.createSequentialGroup()
                                        .addGap(47, 47, 47)
                                        .addComponent(txtidBuku, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnsetBuku, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelAddLayout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtJudul, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panelAddLayout.createSequentialGroup()
                        .addGap(70, 70, 70)
                        .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnBatal2, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(48, 48, 48)
                .addComponent(lbGambar)
                .addGap(18, 18, 18)
                .addGroup(panelAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel18)
                    .addComponent(txtJumlah, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel19)
                    .addComponent(lbTotal))
                .addGap(49, 49, 49))
            .addGroup(panelAddLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(btnUbah, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(731, Short.MAX_VALUE))
            .addGroup(panelAddLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4)
                .addContainerGap())
        );
        panelAddLayout.setVerticalGroup(
            panelAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelAddLayout.createSequentialGroup()
                .addGroup(panelAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelAddLayout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(21, 21, 21))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelAddLayout.createSequentialGroup()
                        .addGroup(panelAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(6, 6, 6)))
                .addGroup(panelAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBatal, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelCustom1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addGroup(panelAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelAddLayout.createSequentialGroup()
                        .addComponent(jLabel18)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtJumlah, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel19)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbTotal))
                    .addGroup(panelAddLayout.createSequentialGroup()
                        .addGroup(panelAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(txtidAnggota, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnsetAnggota))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(txtNama, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9)
                            .addComponent(txtTelpon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(lbGambar)
                    .addGroup(panelAddLayout.createSequentialGroup()
                        .addGroup(panelAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel10)
                            .addComponent(txtidBuku, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnsetBuku))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel11)
                            .addComponent(txtJudul, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel12)
                            .addComponent(txtPengarang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel17)
                            .addComponent(txtPenerbit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(35, 35, 35)
                .addGroup(panelAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnUbah, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBatal2, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panelMain.add(panelAdd, "card2");

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

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
     panelMain.removeAll();
     panelMain.add(panelAdd);
     panelMain.repaint();
     panelMain.revalidate();
     
     txtID.setText(setIdPeminjaman());
     btnUbah.setText("TAMBAH");
    }//GEN-LAST:event_btnAddActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        int row = jTable1.getSelectedRow();
        
        if(row < 0){
            return;
        }
        
        String id = jTable1.getValueAt(row, 0).toString();
        loadTableDetail((DefaultTableModel) TableDetail.getModel(), id);
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

    private void btnBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBatalActionPerformed
        showPanel();
        loadTable();
    }//GEN-LAST:event_btnBatalActionPerformed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        
        if(btnSave.getText().equals("TAMBAH")){
            btnSave.setText("SIMPAN");
        }else if(btnSave.getText().equals("SIMPAN")){
        Validasiform();       
        }else if(btnSave.getText().equals("PERBARUI")){
            updateData();
            resetForm();
            showPanel();
        }
    }//GEN-LAST:event_btnSaveActionPerformed

    private void btnUbahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUbahActionPerformed
        updateData();
    }//GEN-LAST:event_btnUbahActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
       deleteData();
       btnDelete.setVisible(true);
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnsetAnggotaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnsetAnggotaActionPerformed
      setAnggota();
    }//GEN-LAST:event_btnsetAnggotaActionPerformed

    private void btnsetBukuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnsetBukuActionPerformed
      setBuku();
    }//GEN-LAST:event_btnsetBukuActionPerformed

    private void txtJumlahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtJumlahActionPerformed
    
    }//GEN-LAST:event_txtJumlahActionPerformed

    private void txtJumlahMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtJumlahMouseClicked
        
    }//GEN-LAST:event_txtJumlahMouseClicked

    private void btnBatal2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBatal2ActionPerformed
       btnUbah.setText("TAMBAH");
       loadDataSementara();
       resetFormBuku();
         btnDelete.setVisible(false);
       btnBatal2.setVisible(false);
       btnUbah.setVisible(false);
    }//GEN-LAST:event_btnBatal2ActionPerformed

    private void TableSementaraMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TableSementaraMouseClicked
        btnDelete.setVisible(true);
       btnBatal2.setVisible(true);
       dataTabelSementara();
       btnUbah.setVisible(true);
    }//GEN-LAST:event_TableSementaraMouseClicked

    private void TableDetailMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TableDetailMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_TableDetailMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private templet.TableCustom TableDetail;
    private javax.swing.JTable TableSementara;
    private templet.IconButtonCustom btnAdd;
    private templet.IconButtonCustom btnBatal;
    private templet.IconButtonCustom btnBatal2;
    private templet.IconButtonCustom btnDelete;
    private javax.swing.JButton btnFirst;
    private javax.swing.JButton btnLast;
    private javax.swing.JButton btnNext;
    private javax.swing.JButton btnPrev;
    private templet.IconButtonCustom btnSave;
    private templet.IconButtonCustom btnUbah;
    private javax.swing.JButton btnsetAnggota;
    private javax.swing.JButton btnsetBuku;
    private javax.swing.JComboBox<Integer> cbPage;
    private com.raven.datechooser.DateChooser dateChooser1;
    private com.raven.datechooser.DateChooser dateChooser2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private templet.TableCustom jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JLabel lbGambar;
    private javax.swing.JLabel lbTotal;
    private javax.swing.JPanel panelAdd;
    private templet.PanelCustom panelCustom1;
    private javax.swing.JPanel panelMain;
    private javax.swing.JPanel panelView;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtID;
    private javax.swing.JTextField txtJudul;
    private javax.swing.JTextField txtJumlah;
    private javax.swing.JTextField txtKembali;
    private javax.swing.JTextField txtNama;
    private javax.swing.JTextField txtPenerbit;
    private javax.swing.JTextField txtPengarang;
    private javax.swing.JTextField txtPinjam;
    private javax.swing.JTextField txtTelpon;
    private templet.TextfieldCustom1 txtcari;
    private javax.swing.JTextField txtidAnggota;
    private javax.swing.JTextField txtidBuku;
    // End of variables declaration//GEN-END:variables

    
 private String setIdPeminjaman() {
    String urutan;
    Date now = new Date();
    SimpleDateFormat noFormat = new SimpleDateFormat("yyMM");
    String no = noFormat.format(now);

    String sql = "SELECT RIGHT(ID_Peminjaman, 4) AS Nomor " +
                 "FROM peminjaman " +
                 "WHERE ID_Peminjaman LIKE ? " +
                 "ORDER BY ID_Peminjaman DESC " +
                 "LIMIT 1";

    try (PreparedStatement st = conn.prepareStatement(sql)) {
        st.setString(1, "PM" + no + "%");
        ResultSet rs = st.executeQuery();

        if (rs.next()) {
            int nomor = Integer.parseInt(rs.getString("Nomor")) + 1;
            urutan = "PM" + no + String.format("%03d", nomor);
        } else {
            urutan = "PM" + no + "0001";
        }

    } catch (SQLException e) {
        java.util.logging.Logger
            .getLogger(menuBuku.class.getName())
            .log(Level.SEVERE, null, e);
        urutan = "PM" + no + "001";
    }

    return urutan;
}
 
 private int getTotalPinjam() {
    int total = 0;
    try {
        String sql = "SELECT SUM(Jumlah_Pinjam) FROM sementara";
        PreparedStatement ps = conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            total = rs.getInt(1);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return total;
}


    private void insertData() {
        String idPinjam = txtID.getText();
        String tglPeminjaman = txtPinjam.getText();
        String tglKembali = txtKembali.getText();       
        int totalPinjam = getTotalPinjam();       
        String idAnggota = txtidAnggota.getText();       
        
        
        if(idPinjam.isEmpty() || tglPeminjaman.isEmpty() || tglKembali.isEmpty()
                || idAnggota.isEmpty()){
            JOptionPane.showMessageDialog(this,"Semua kolom harus diisi !","Validasi",JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        try{
         String sql = "INSERT INTO peminjaman (ID_Peminjaman, Tanggal_Peminjaman, Tanggal_Pengembalian, Total_Pinjam, ID_Anggota, ID_User) VALUES (?,?,?,?,?,?)";
         try(PreparedStatement st = conn.prepareStatement(sql)){
          st.setString(1, idPinjam);
          st.setString(2, tglPeminjaman);
          st.setString(3, tglKembali);
          st.setInt(4, totalPinjam);
          st.setString(5, idAnggota);
          st.setString(6, userID);
         
          
          int rowInserted = st.executeUpdate();
          if(rowInserted > 0){
              JOptionPane.showMessageDialog(this, "Data Berhasil Di Tambahkan");
              resetForm();
              loadTable();
              showPanel();
          }
         } 
        }catch (Exception e){
        java.util.logging.Logger.getLogger(menuBuku.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    
    private void insertDataDetail() {
        String idPinjam =txtID.getText();  
        try{
            String sql = "INSERT INTO detail_peminjaman "
           + "(ID_Peminjaman, ID_Buku, Jumlah_Pinjam, Status_Peminjaman) "
           + "SELECT ?, ID_Buku, Jumlah_Pinjam, Status_Pinjam FROM sementara";
        try(PreparedStatement ps = conn.prepareStatement(sql)){
        ps.setString(1, idPinjam);
        ps.executeUpdate();
        }
 
        }catch (Exception e){
        java.util.logging.Logger.getLogger(menuBuku.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    
    private void dataTabelSementara() {

    int row = TableSementara.getSelectedRow();

    if(row!=1){
     btnsetBuku.setEnabled(false);
    txtidBuku.setEnabled(false);
    txtJudul.setEnabled(false);
    txtPengarang.setEnabled(false);
    txtPenerbit.setEnabled(false);
    
    String id = TableSementara.getValueAt(row, 0).toString();
    txtidBuku.setText(TableSementara.getValueAt(row, 0).toString());
    txtJudul.setText(TableSementara.getValueAt(row, 1).toString());
    txtPengarang.setText(TableSementara.getValueAt(row, 2).toString());
    txtPenerbit.setText(TableSementara.getValueAt(row, 3).toString());
    txtJumlah.setText(TableSementara.getValueAt(row, 4).toString());
    
    setImage(lbGambar,id);
    txtJumlah.requestFocus();
    }
}

    
  private void updateData() {
    String idBuku = txtidBuku.getText();
    String jumlahPinjam = txtJumlah.getText();
   

    if (idBuku.isEmpty() || jumlahPinjam.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Semua kolom wajib diisi!", "Validasi", JOptionPane.ERROR_MESSAGE);
        return;
    }

    try {

        String sql = "UPDATE sementara SET Jumlah_Pinjam=? WHERE ID_Buku=?";
        PreparedStatement st = conn.prepareStatement(sql);
        st.setString(1, jumlahPinjam);
        st.setString(2, idBuku);
        
        if (st.executeUpdate() > 0) {
            JOptionPane.showMessageDialog(this, "Data berhasil diperbarui");
            resetFormBuku();
            loadDataSementara();
        }

    } catch (Exception e) {
        e.printStackTrace();
    }
}


    private void deleteData() {
        int selectedRow = TableSementara.getSelectedRow();
        int confirm = JOptionPane.showConfirmDialog(this, "Apakah yakin ingin menghapus data ini ?",
                "Konfirmasi Hapus Data", JOptionPane.YES_NO_OPTION);
        
        if(confirm == JOptionPane.YES_OPTION){
            String id = TableSementara.getValueAt(selectedRow, 0).toString();
            
            try{
            String sql = "DELETE FROM sementara WHERE ID_Buku=?";
            try(PreparedStatement st = conn.prepareStatement(sql)){
             st.setString(1, id);
             
             int rowDeleted = st.executeUpdate();
             if(rowDeleted > 0){
                 JOptionPane.showMessageDialog(this, "Data Berhasil Dihapus");
             }else{
                JOptionPane.showMessageDialog(this, "Data Gagal Dihapus");
             }
            }
           } catch(SQLException e){
             java.util.logging.Logger.getLogger(menuBuku.class.getName()).log(Level.SEVERE, null, e);
           }
            
        }
        resetForm();
        loadDataSementara();
    }
    
    private void deleteDataSementara(){
        try {
             String sql = "DELETE FROM sementara";
            try(PreparedStatement st = conn.prepareStatement(sql)){
             st.executeUpdate();
            }
        } catch (Exception e) {
            
        }
    }
  
    
    private void serchData() {
        String kataKunci = txtcari.getText();
        
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        model.setRowCount(0);
        
        try{
        String sql = "SELECT PM.ID_Peminjaman, PM.Tanggal_Peminjaman, PM.Tanggal_Pengembalian,PM.Total_Pinjam, agt.Nama_Anggota, usr.Nama_User\n" +
                     "FROM peminjaman PM\n" +
                     "INNER JOIN anggota agt ON agt.ID_Anggota = PM.ID_Anggota\n" +
                     "INNER JOIN USER usr ON usr.ID_User = PM.ID_User \n" +
                     "WHERE PM.ID_Peminjaman LIKE ? OR agt.Nama_Anggota LIKE ?";
        try(PreparedStatement st = conn.prepareStatement(sql)){
         st.setString(1, "%" + kataKunci + "%");
         st.setString(2, "%" + kataKunci + "%");
         ResultSet rs = st.executeQuery();
         
         while (rs.next()) {
            String idBuku = rs.getString("ID_Peminjaman");
            String tanggalPeminjaman = rs.getString("Tanggal_Peminjaman");
            String tanggalPengembalian = rs.getString("Tanggal_Pengembalian");
            String totalPinjam = rs.getString("Total_Pinjam");
            String namaAnggota = rs.getString("Nama_Anggota");
            String namaUser = rs.getString("Nama_User");
            
            Object[] rowData = {idBuku, tanggalPeminjaman, tanggalPengembalian, totalPinjam, namaAnggota, namaUser};
            model.addRow(rowData);
        }
            }
        } catch(SQLException e){
                     java.util.logging.Logger.getLogger(menuBuku.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    
    
    void setImage(JLabel lb_gambar, String id){
      try{
      String sql = "SELECT * FROM buku WHERE ID_Buku = '" + id + "'";
      PreparedStatement st = conn.prepareStatement(sql);
      ResultSet rs = st.executeQuery();
      
      if(rs.next()){
         byte[] img = rs.getBytes("Gambar");
         if(img != null){
          ImageIcon imageIcon = new ImageIcon(new ImageIcon(img).getImage()
                  .getScaledInstance(lb_gambar.getWidth(), lb_gambar.getHeight(), Image.SCALE_SMOOTH));
          lb_gambar.setIcon(imageIcon);              
         }else{
          ImageIcon defaultIcon = new ImageIcon(getClass().getResource("D:/NetBeansProjects/ApkPerpus/src/img/book3.png"));
         }
      }
      }catch(SQLException e){
       e.printStackTrace();
      }
    } 
    
  public void setAnggota(){
      boolean closable = true;
      DataAnggota anggota = new DataAnggota(null, closable);
      anggota.setVisible(true);
      
      txtidAnggota.setText(anggota.getIdAnggota());
      txtNama.setText(anggota.getNamaAnggota());
      txtEmail.setText(anggota.getEmailAnggota());
      txtTelpon.setText(anggota.getTeleponAnggota());
      
       txtidAnggota.setEnabled(false);
       txtNama.setEnabled(false);
       txtEmail.setEnabled(false);
       txtTelpon.setEnabled(false);
  }
  
  public void setBuku(){
      boolean closable = true;
      DataBuku buku = new DataBuku(null, closable);
      buku.setVisible(true);
      
      String id = buku.getIdBuku();
      txtidBuku.setText(buku.getIdBuku());
      txtJudul.setText(buku.getJudulBuku());
      txtPengarang.setText(buku.getPengarangBuku());
      txtPenerbit.setText(buku.getPenerbitBuku());
      setImage(lbGambar, id);
      
       txtidBuku.setEnabled(false);
       txtJudul.setEnabled(false);
       txtPengarang.setEnabled(false);
       txtPenerbit.setEnabled(false);
  }
  
  private boolean cekStok(String idBuku, int jumlahPinjam) {
    try {
        String sql = "SELECT Stok FROM buku WHERE ID_Buku = ?";
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setString(1, idBuku);

            ResultSet rs = st.executeQuery();

            if (rs.next()) {
                int stokTersedia = rs.getInt("Stok");
                return jumlahPinjam <= stokTersedia;
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return false;
}

  
  public void insertDataSementara(){
        String idBuku = txtidBuku.getText();
        String judulBuku = txtJudul.getText();
        String Pengarang_Buku = txtPengarang.getText();       
        String PenerbitBuku = txtPenerbit.getText();       
        int jumlahPinjam = Integer.parseInt(txtJumlah.getText());   
        String statusPinjam = "Sedang Di Pinjam";       
        
        
        if(idBuku.isEmpty() || judulBuku.isEmpty() || Pengarang_Buku.isEmpty()
            || PenerbitBuku.isEmpty() || statusPinjam.isEmpty()){
            JOptionPane.showMessageDialog(this,"Semua kolom harus diisi !","Validasi",JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if(!cekStok(idBuku, jumlahPinjam)){
         JOptionPane.showMessageDialog(this, "Stok Buku Tidak Tersedia !","Peringatan", JOptionPane.ERROR_MESSAGE);
         return;
        }
        
        try{
         String sql = "INSERT INTO sementara (ID_Buku, Judul_Buku, Pengarang_Buku, Penerbit_Buku, Jumlah_Pinjam, Status_Pinjam) VALUES (?,?,?,?,?,?)";
         try(PreparedStatement st = conn.prepareStatement(sql)){
          st.setString(1, idBuku);
          st.setString(2, judulBuku);
          st.setString(3, Pengarang_Buku);
          st.setString(4, PenerbitBuku);
          st.setInt(5, jumlahPinjam);
          st.setString(6, statusPinjam);
         
          st.executeUpdate();
          int totalPinjam = getTotalPinjam();
          lbTotal.setText(String.valueOf(totalPinjam));
          loadDataSementara();
          
          if(JOptionPane.showConfirmDialog(this,"Mau Tambah Barang?",
        "Konfirmasi",JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION){
          resetFormBuku();
          btnsetBuku.requestFocus();
          }else{
          resetFormBuku();
          btnSave.requestFocus();
          }
         } 
        }catch (Exception e){
        java.util.logging.Logger.getLogger(menuBuku.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    private void Validasiform() {
        String pinjam = txtPinjam.getText();
        String kembali = txtKembali.getText();
        
        if (!pinjam.equals(kembali)) {
            insertData();
            insertDataDetail();
            deleteDataSementara();
            resetForm();
            showPanel();
            loadTable();
        }else{
         JOptionPane.showMessageDialog(null, "Tanggal Kembali sama dengan Tanggal Pinjam", "Pesan", JOptionPane.ERROR_MESSAGE);
        }
    }
}
    
