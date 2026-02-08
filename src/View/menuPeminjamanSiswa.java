
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


public class menuPeminjamanSiswa extends javax.swing.JPanel {
Connection conn;
PreparedStatement ps;
ResultSet rs;

int currentPage = 1;
int rowsPerPage = 6;

private String idUser;
private String userID;
DefaultTableModel model;
  
    public menuPeminjamanSiswa (String userID) {
        initComponents();
        conn = koneksi.koneksi.KoneksiDB();
        this.userID = userID;
    setLayout();
    visbleBtn();
    setTableSementara();
    loadDataSementara();
     txtID.setText(setIdPeminjaman());
     txtID.setEditable(false);
    
    txtJumlah.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                   insertDataSementara();
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
        txtUser.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Penjaga");       
      }
    
    private void loadDataSementara(){
     getDataSementara((DefaultTableModel) TableSementara.getModel());
    }
 
    private void resetForm() {
       txtID.setText(setIdPeminjaman());
       txtidAnggota.setText("");
       txtNama.setText("");
       txtEmail.setText("");
       txtTelpon.setText("");
       txtUser.setText("");
       clearTable();
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
  
      private void clearTable() {
    DefaultTableModel model = (DefaultTableModel) TableSementara.getModel();
    model.setRowCount(0);
}
      
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        dateChooser1 = new com.raven.datechooser.DateChooser();
        dateChooser2 = new com.raven.datechooser.DateChooser();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        panelMain = new javax.swing.JPanel();
        panelAdd = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        btnSave = new templet.IconButtonCustom();
        lbGambar = new javax.swing.JLabel();
        panelCustom1 = new templet.PanelCustom();
        jLabel3 = new javax.swing.JLabel();
        txtID = new javax.swing.JTextField();
        txtPinjam = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtKembali = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        txtUser = new javax.swing.JTextField();
        btnsetBuku1 = new javax.swing.JButton();
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

        panelAdd.setBackground(new java.awt.Color(255, 255, 255));

        jLabel15.setBackground(new java.awt.Color(102, 102, 102));
        jLabel15.setFont(new java.awt.Font("Bahnschrift", 1, 18)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(51, 51, 51));
        jLabel15.setText("Peminjaman Buku Perpustakaan");

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

        jLabel13.setFont(new java.awt.Font("Bahnschrift", 0, 14)); // NOI18N
        jLabel13.setText("Penjaga");

        btnsetBuku1.setText("...");
        btnsetBuku1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnsetBuku1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelCustom1Layout = new javax.swing.GroupLayout(panelCustom1);
        panelCustom1.setLayout(panelCustom1Layout);
        panelCustom1Layout.setHorizontalGroup(
            panelCustom1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelCustom1Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtID, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtPinjam, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtKembali, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel13)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtUser, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnsetBuku1, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(32, Short.MAX_VALUE))
        );
        panelCustom1Layout.setVerticalGroup(
            panelCustom1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelCustom1Layout.createSequentialGroup()
                .addContainerGap(23, Short.MAX_VALUE)
                .addGroup(panelCustom1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4)
                    .addComponent(txtPinjam, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(txtKembali, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13)
                    .addComponent(txtUser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnsetBuku1))
                .addGap(19, 19, 19))
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
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
            .addGroup(panelAddLayout.createSequentialGroup()
                .addGroup(panelAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelAddLayout.createSequentialGroup()
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
                                .addComponent(btnBatal2, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(panelAddLayout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(btnUbah, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)))
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
                .addContainerGap()
                .addGroup(panelAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelCustom1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane4))
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
                .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                .addContainerGap(8, Short.MAX_VALUE))
        );

        panelMain.add(panelAdd, "card2");

        add(panelMain, "card2");
    }// </editor-fold>//GEN-END:initComponents

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        
        if(btnSave.getText().equals("TAMBAH")){
            btnSave.setText("SIMPAN");
        }else if(btnSave.getText().equals("SIMPAN")){
        Validasiform();       
        }else if(btnSave.getText().equals("PERBARUI")){
            updateData();
            resetForm();
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

    private void btnsetBuku1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnsetBuku1ActionPerformed
        setPenjaga();
    }//GEN-LAST:event_btnsetBuku1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable TableSementara;
    private templet.IconButtonCustom btnBatal2;
    private templet.IconButtonCustom btnDelete;
    private templet.IconButtonCustom btnSave;
    private templet.IconButtonCustom btnUbah;
    private javax.swing.JButton btnsetAnggota;
    private javax.swing.JButton btnsetBuku;
    private javax.swing.JButton btnsetBuku1;
    private com.raven.datechooser.DateChooser dateChooser1;
    private com.raven.datechooser.DateChooser dateChooser2;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTable jTable2;
    private javax.swing.JLabel lbGambar;
    private javax.swing.JLabel lbTotal;
    private javax.swing.JPanel panelAdd;
    private templet.PanelCustom panelCustom1;
    private javax.swing.JPanel panelMain;
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
    private javax.swing.JTextField txtUser;
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

private boolean insertData() {
    String idPinjam = txtID.getText();
    String tglPeminjaman = txtPinjam.getText();
    String tglKembali = txtKembali.getText();       
    int totalPinjam = getTotalPinjam();       
    String idAnggota = txtidAnggota.getText();    
    String petugas = idUser;

    if (idPinjam.isEmpty() || tglPeminjaman.isEmpty() || tglKembali.isEmpty()
        || idAnggota.isEmpty()
        || txtUser.getText() == null
        || txtUser.getText().trim().isEmpty()
        || petugas == null) {

        JOptionPane.showMessageDialog(
            this,
            "Semua kolom harus diisi!",
            "Validasi",
            JOptionPane.ERROR_MESSAGE
        );
        return false;
    }

    try {
        String sql = "INSERT INTO peminjaman VALUES (?,?,?,?,?,?)";
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setString(1, idPinjam);
            st.setString(2, tglPeminjaman);
            st.setString(3, tglKembali);
            st.setInt(4, totalPinjam);
            st.setString(5, idAnggota);
            st.setString(6, petugas);

            st.executeUpdate();
            return true;
            
        }
    } catch (Exception e) {
         JOptionPane.showMessageDialog(
        this,
        "Gagal menyimpan data peminjaman!",
        "Error",
        JOptionPane.ERROR_MESSAGE
    );
        e.printStackTrace();
    }
    return false;
}

    
    private void insertDataDetail() {
        String idPinjam = txtID.getText();  
        
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
  public void setPenjaga(){
      boolean closable = true;
      DataUser user = new DataUser(null, closable);
      user.setVisible(true);
      
      txtUser.setText(user.getNamaUser());
      idUser = user.getIDUser();
       txtUser.setEnabled(false);
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

    if (pinjam.equals(kembali)) {
        JOptionPane.showMessageDialog(
            this,
            "Tanggal Kembali sama dengan Tanggal Pinjam",
            "Pesan",
            JOptionPane.ERROR_MESSAGE
        );
        return;
    }
    
    if (!insertData()) {
        return;
    }

    insertDataDetail();
    deleteDataSementara();
    resetForm();
     JOptionPane.showMessageDialog(
        this,
        "Data berhasil ditambahkan",
        "Sukses",
        JOptionPane.INFORMATION_MESSAGE
    );
}

}
    
