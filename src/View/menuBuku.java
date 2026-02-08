
package View;
import java.awt.Component;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.FlatLightLaf;
import java.awt.Color;
import java.awt.Image;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.util.logging.Level;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import main.MenuUtama;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;


public class menuBuku extends javax.swing.JPanel {
Connection conn;
PreparedStatement ps;
ResultSet rs;
private String idKategori;
private String idPenerbit;

int currentPage = 1;
int rowsPerPage = 3;
int totalData = 0;
int totalPage = 0;

private final int noColumnIndex = 0;
private final int nocolumnWidth = 80;
DefaultTableModel model;
  
    public menuBuku() {
        initComponents();
        conn = koneksi.koneksi.KoneksiDB();
       
          model = new DefaultTableModel(
        new Object[]{"ID","Judul Buku","Pengarang","Tahun Terbit","ID Kategori","Kategori","ID Penerbit","Penerbit","Stok","Gambar"}, 0
    );
    jTable1.setModel(model);
    
    jTable1.getColumnModel()
       .getColumn(9)
       .setCellRenderer(new ImageRender());
    
 setColumnWitdh();
    cbPage.removeAllItems();
    hitungTotalData();
    loadComboPage();
    loadTable();
    setLayout();
    visbleBtn();
    loadData();
    txtcari.setPlaceholder("Serch");
    txtID.setEditable(false);
    }

    public void visbleBtn(){
        btnDelete.setVisible(false);
        btnCancel.setVisible(false);
        btnPrint.setVisible(false);
    }
    public void setLayout(){
        txtID.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "ID Buku");
        txtNama.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Judul Buku");
        txtPengarang.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Pengarang Buku");
        txt_Tanggal.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Tahun Terbit");
        txtImagePath.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Image path");  
        txtStok.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Stok");  
      }
    
    void hitungTotalData() {
    try {
        ps = conn.prepareStatement("SELECT COUNT(*) FROM buku");
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
   getKategori();
   getPenerbit();
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
    "SELECT bk.ID_Buku, bk.Judul_Buku, bk.Pengarang_Buku, bk.Tahun_Terbit, " +
    "ktg.ID_Kategori, ktg.Nama_Kategori, " +
    "pnb.ID_Penerbit, pnb.Nama_Penerbit, bk.Stok ,bk.Gambar " +
    "FROM buku bk " +
    "INNER JOIN kategori ktg ON ktg.ID_Kategori = bk.ID_Kategori " +
    "INNER JOIN penerbit pnb ON pnb.ID_Penerbit = bk.ID_Penerbit " +
    "LIMIT ? OFFSET ?"
);

ps.setInt(1, rowsPerPage);
ps.setInt(2, offset);

        rs = ps.executeQuery();

        while (rs.next()) {
            String idBuku = rs.getString("ID_Buku");
            String judulBuku = rs.getString("Judul_Buku");
            String pengarangBuku = rs.getString("Pengarang_Buku");
            String tahunTerbit = rs.getString("Tahun_Terbit");
            String idKategori = rs.getString("ID_Kategori");
            String namaKategori = rs.getString("Nama_Kategori");
            String idPenerbit = rs.getString("ID_Penerbit");
            String namaPenerbit = rs.getString("Nama_Penerbit");
            String stok = rs.getString("Stok");
            
           byte[] imageData = rs.getBytes("Gambar");
               ImageIcon imageIcon = null;

        if (imageData != null) {
        ImageIcon icon = new ImageIcon(imageData);
        Image img = icon.getImage().getScaledInstance(72, 106, Image.SCALE_SMOOTH);
        imageIcon = new ImageIcon(img);
}

            
            Object[] rowData = {idBuku, judulBuku, pengarangBuku, tahunTerbit, idKategori, namaKategori, idPenerbit, namaPenerbit, stok ,imageIcon};
            model.addRow(rowData);
        }

    } catch (SQLException e) {
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
       txtID.setText("");
       txtNama.setText("");
       txtPengarang.setText("");
       txt_Tanggal.setText("");
    }
    
    private void showPanel(){
        panelMain.removeAll();
        panelMain.add(new menuBuku());
        panelMain.repaint();
        panelMain.revalidate();
    }
    
    void updatePage() {
    loadTable();
    cbPage.setSelectedItem(currentPage);
}
  
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelMain = new javax.swing.JPanel();
        panelView = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        btnAdd = new templet.IconButtonCustom();
        btnDelete = new templet.IconButtonCustom();
        btnCancel = new templet.IconButtonCustom();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new templet.TableCustom();
        btnFirst = new javax.swing.JButton();
        btnPrev = new javax.swing.JButton();
        btnLast = new javax.swing.JButton();
        btnNext = new javax.swing.JButton();
        cbPage = new javax.swing.JComboBox<>();
        txtcari = new templet.TextfieldCustom1();
        btnPrint = new templet.IconButtonCustom();
        panelAdd = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        btnSave = new templet.IconButtonCustom();
        btnBatal = new templet.IconButtonCustom();
        jLabel3 = new javax.swing.JLabel();
        txtID = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtNama = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtPengarang = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txt_Tanggal = new javax.swing.JTextField();
        jLabel90 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        cmbPenerbit = new javax.swing.JComboBox<>();
        lbGambar = new javax.swing.JLabel();
        txtImagePath = new javax.swing.JTextField();
        cmbKategori = new javax.swing.JComboBox<>();
        btnBrowse = new templet.IconButtonCustom();
        jLabel6 = new javax.swing.JLabel();
        txtStok = new javax.swing.JTextField();

        setLayout(new java.awt.CardLayout());

        panelMain.setBackground(new java.awt.Color(255, 255, 255));
        panelMain.setLayout(new java.awt.CardLayout());

        panelView.setBackground(new java.awt.Color(255, 255, 255));

        jLabel13.setBackground(new java.awt.Color(102, 102, 102));
        jLabel13.setFont(new java.awt.Font("Bahnschrift", 1, 18)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(51, 51, 51));
        jLabel13.setText("Data Buku Perpustakaan");

        jLabel14.setFont(new java.awt.Font("Bahnschrift", 1, 12)); // NOI18N
        jLabel14.setText("Master Data > Buku");

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/book_icon.png"))); // NOI18N

        btnAdd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/add.png"))); // NOI18N
        btnAdd.setText("TAMBAH");
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
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
                {null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4", "Title 5", "Title 6", "Title 7", "Title 8", "Title 9"
            }
        ));
        jTable1.setRowHeight(120);
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
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnPrint, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txtcari, javax.swing.GroupLayout.PREFERRED_SIZE, 313, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(24, 24, 24)))))
                .addContainerGap(20, Short.MAX_VALUE))
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
                            .addComponent(jLabel13, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(15, 15, 15)
                        .addGroup(panelViewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtcari, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnPrint, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 33, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
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

        panelAdd.setBackground(new java.awt.Color(255, 255, 255));

        jLabel15.setBackground(new java.awt.Color(102, 102, 102));
        jLabel15.setFont(new java.awt.Font("Bahnschrift", 1, 18)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(51, 51, 51));
        jLabel15.setText("Tambah Data Buku Perpustakaan");

        jLabel16.setFont(new java.awt.Font("Bahnschrift", 1, 12)); // NOI18N
        jLabel16.setText("Master Data > Buku");

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/book_icon.png"))); // NOI18N

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

        jLabel3.setFont(new java.awt.Font("Bahnschrift", 0, 14)); // NOI18N
        jLabel3.setText("ID");

        txtID.setFont(new java.awt.Font("Bahnschrift", 0, 12)); // NOI18N

        jLabel4.setFont(new java.awt.Font("Bahnschrift", 0, 14)); // NOI18N
        jLabel4.setText("Judul");

        txtNama.setFont(new java.awt.Font("Bahnschrift", 0, 12)); // NOI18N

        jLabel5.setFont(new java.awt.Font("Bahnschrift", 0, 14)); // NOI18N
        jLabel5.setText("Pengarang");

        txtPengarang.setFont(new java.awt.Font("Bahnschrift", 0, 12)); // NOI18N
        txtPengarang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPengarangActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Bahnschrift", 0, 14)); // NOI18N
        jLabel7.setText("Tahun Terbit");

        txt_Tanggal.setFont(new java.awt.Font("Bahnschrift", 0, 12)); // NOI18N
        txt_Tanggal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_TanggalActionPerformed(evt);
            }
        });

        jLabel90.setFont(new java.awt.Font("Bahnschrift", 0, 14)); // NOI18N
        jLabel90.setText("Kategori");

        jLabel8.setFont(new java.awt.Font("Bahnschrift", 0, 14)); // NOI18N
        jLabel8.setText("Penerbit");

        cmbPenerbit.setForeground(new java.awt.Color(153, 153, 153));
        cmbPenerbit.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { " " }));

        lbGambar.setFont(new java.awt.Font("Bahnschrift", 0, 14)); // NOI18N
        lbGambar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/ImgBook.png"))); // NOI18N
        lbGambar.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        lbGambar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));

        txtImagePath.setFont(new java.awt.Font("Bahnschrift", 0, 12)); // NOI18N

        cmbKategori.setForeground(new java.awt.Color(153, 153, 153));

        btnBrowse.setBackground(new java.awt.Color(255, 255, 255));
        btnBrowse.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        btnBrowse.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/upload.png"))); // NOI18N
        btnBrowse.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBrowseActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Bahnschrift", 0, 14)); // NOI18N
        jLabel6.setText("Stok");

        txtStok.setFont(new java.awt.Font("Bahnschrift", 0, 12)); // NOI18N
        txtStok.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtStokActionPerformed(evt);
            }
        });

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
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 375, Short.MAX_VALUE)
                        .addComponent(jLabel16)
                        .addGap(14, 14, 14))
                    .addGroup(panelAddLayout.createSequentialGroup()
                        .addGroup(panelAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(txtStok, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(cmbKategori, javax.swing.GroupLayout.Alignment.LEADING, 0, 621, Short.MAX_VALUE)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panelAddLayout.createSequentialGroup()
                                    .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(btnBatal, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panelAddLayout.createSequentialGroup()
                                    .addGap(6, 6, 6)
                                    .addGroup(panelAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING)))
                                .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(txtNama, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(txtPengarang, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(txt_Tanggal, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(cmbPenerbit, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txtID, javax.swing.GroupLayout.Alignment.LEADING))
                            .addComponent(jLabel6)
                            .addComponent(jLabel90)
                            .addComponent(jLabel8))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(panelAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbGambar)
                            .addGroup(panelAddLayout.createSequentialGroup()
                                .addComponent(txtImagePath, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnBrowse, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(26, 26, 26))))
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
                .addGap(15, 15, 15)
                .addComponent(jLabel3)
                .addGap(5, 5, 5)
                .addGroup(panelAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelAddLayout.createSequentialGroup()
                        .addComponent(txtID, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(jLabel4)
                        .addGap(5, 5, 5)
                        .addComponent(txtNama, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(9, 9, 9)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtPengarang, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(11, 11, 11)
                        .addComponent(jLabel7)
                        .addGap(9, 9, 9)
                        .addComponent(txt_Tanggal, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(lbGambar))
                .addGap(10, 10, 10)
                .addGroup(panelAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelAddLayout.createSequentialGroup()
                        .addGroup(panelAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel90)
                            .addComponent(txtImagePath, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cmbKategori, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cmbPenerbit, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtStok, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnBrowse, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(21, Short.MAX_VALUE))
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
     
     txtID.setText(setIDBuku());
       if (btnAdd.getText().equals("UBAH")) {
    dataTabel();
    btnSave.setText("PERBARUI");
}

    }//GEN-LAST:event_btnAddActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        if(btnAdd.getText().equals("TAMBAH")){
            btnAdd.setText("UBAH");
        }
        btnPrint.setVisible(true);
        btnDelete.setVisible(true);
        btnCancel.setVisible(true);
    }//GEN-LAST:event_jTable1MouseClicked

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
     deleteData();
    }//GEN-LAST:event_btnDeleteActionPerformed

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
        visbleBtn();
        loadTable();
        btnAdd.setText("TAMBAH");
    }//GEN-LAST:event_btnCancelActionPerformed

    private void btnBrowseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBrowseActionPerformed
        getImage();
    }//GEN-LAST:event_btnBrowseActionPerformed

    private void txt_TanggalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_TanggalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_TanggalActionPerformed

    private void txtPengarangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPengarangActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPengarangActionPerformed

    private void btnBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBatalActionPerformed
        showPanel();
        loadTable();
    }//GEN-LAST:event_btnBatalActionPerformed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        if(btnSave.getText().equals("TAMBAH")){
            btnSave.setText("SIMPAN");
        }else if(btnSave.getText().equals("SIMPAN")){
            insertData();
        }else if(btnSave.getText().equals("PERBARUI")){
            updateData();
        }

    }//GEN-LAST:event_btnSaveActionPerformed

    private void txtStokActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtStokActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtStokActionPerformed

    private void btnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrintActionPerformed
        cetakLaporan();
    }//GEN-LAST:event_btnPrintActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private templet.IconButtonCustom btnAdd;
    private templet.IconButtonCustom btnBatal;
    private templet.IconButtonCustom btnBrowse;
    private templet.IconButtonCustom btnCancel;
    private templet.IconButtonCustom btnDelete;
    private javax.swing.JButton btnFirst;
    private javax.swing.JButton btnLast;
    private javax.swing.JButton btnNext;
    private javax.swing.JButton btnPrev;
    private templet.IconButtonCustom btnPrint;
    private templet.IconButtonCustom btnSave;
    private javax.swing.JComboBox<Integer> cbPage;
    private javax.swing.JComboBox<String> cmbKategori;
    private javax.swing.JComboBox<String> cmbPenerbit;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel90;
    private javax.swing.JScrollPane jScrollPane2;
    private templet.TableCustom jTable1;
    private javax.swing.JLabel lbGambar;
    private javax.swing.JPanel panelAdd;
    private javax.swing.JPanel panelMain;
    private javax.swing.JPanel panelView;
    private javax.swing.JTextField txtID;
    private javax.swing.JTextField txtImagePath;
    private javax.swing.JTextField txtNama;
    private javax.swing.JTextField txtPengarang;
    private javax.swing.JTextField txtStok;
    private javax.swing.JTextField txt_Tanggal;
    private templet.TextfieldCustom1 txtcari;
    // End of variables declaration//GEN-END:variables

   
public class ImageRender extends DefaultTableCellRenderer {

    @Override
    public Component getTableCellRendererComponent(
            JTable table,
            Object value,
            boolean isSelected,
            boolean hasFocus,
            int row,
            int column) {

        if (value instanceof ImageIcon) {
            ImageIcon icon = (ImageIcon) value;
            JLabel label = new JLabel(icon);
            label.setHorizontalAlignment(JLabel.CENTER);
            return label;
        }

        return super.getTableCellRendererComponent(
                table, value, isSelected, hasFocus, row, column);
    }
}
    
 private String setIDBuku() {
    String urutan;
    Date now = new Date();
    SimpleDateFormat noFormat = new SimpleDateFormat("yyMM");
    String no = noFormat.format(now);

    String sql = "SELECT RIGHT(ID_Buku, 4) AS Nomor " +
                 "FROM buku " +
                 "WHERE ID_Buku LIKE ? " +
                 "ORDER BY ID_Buku DESC " +
                 "LIMIT 1";

    try (PreparedStatement st = conn.prepareStatement(sql)) {
        st.setString(1, "BK" + no + "%");
        ResultSet rs = st.executeQuery();

        if (rs.next()) {
            int nomor = Integer.parseInt(rs.getString("Nomor")) + 1;
            urutan = "BK" + no + String.format("%03d", nomor);
        } else {
            urutan = "BK" + no + "0001";
        }

    } catch (SQLException e) {
        java.util.logging.Logger
            .getLogger(menuBuku.class.getName())
            .log(Level.SEVERE, null, e);
        urutan = "BK" + no + "001";
    }

    return urutan;
}

    private void insertData() {
        String idBuku = txtID.getText();
        String JudulBuku = txtNama.getText();
        String Pengarang_Buku = txtPengarang.getText();       
        String tahunTerbit = txt_Tanggal.getText();
        String stok = txtStok.getText();
        String imagePath = txtImagePath.getText();
        
        if(idBuku.isEmpty() || JudulBuku.isEmpty() || Pengarang_Buku.isEmpty() || tahunTerbit.isEmpty() || stok.isEmpty()  ||
                cmbKategori.getSelectedItem().toString().equals("Pilih Kategori") || 
                cmbPenerbit.getSelectedItem().toString().equals("Pilih Penerbit") || 
                txtImagePath.equals("Image Path")){
            JOptionPane.showMessageDialog(this,"Semua kolom harus diisi !","Validasi",JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        try{
         String sql = "INSERT INTO buku (ID_Buku, Judul_Buku, Pengarang_Buku, Tahun_Terbit, ID_Kategori, ID_Penerbit, Stok ,Gambar) VALUES (?,?,?,?,?,?,?,?)";
         try(PreparedStatement st = conn.prepareStatement(sql)){
          st.setString(1, idBuku);
          st.setString(2, JudulBuku);
          st.setString(3, Pengarang_Buku);
          st.setString(4, tahunTerbit);
          st.setString(5, idKategori);
          st.setString(6, idPenerbit);
          st.setString(7, stok);
          
          
          File imageFile = new File(imagePath);
          FileInputStream fis = new FileInputStream(imageFile);
          st.setBinaryStream(8, fis, (int) imageFile.length());
          
          int rowInserted = st.executeUpdate();
          if(rowInserted > 0){
              JOptionPane.showMessageDialog(this, "Data Berhasil Di Tambahkan");
              resetForm();
              loadTable();
              showPanel();
          }
         } catch (FileNotFoundException ex){
          java.util.logging.Logger.getLogger(menuBuku.class.getName()).log(Level.SEVERE, null, ex);
         }
        }catch (Exception e){
        java.util.logging.Logger.getLogger(menuBuku.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    
    private void dataTabel() {
    panelView.setVisible(false);
    panelAdd.setVisible(true);

    int row = jTable1.getSelectedRow();
    jLabel15.setText("Perbarui Data Buku Perpustakaan");

    txtID.setEnabled(false);
    String id = jTable1.getModel().getValueAt(row, 0).toString();

    txtID.setText(jTable1.getValueAt(row, 0).toString());
    txtNama.setText(jTable1.getValueAt(row, 1).toString());
    txtPengarang.setText(jTable1.getValueAt(row, 2).toString());
    txt_Tanggal.setText(jTable1.getValueAt(row, 3).toString());
    idKategori = jTable1.getModel().getValueAt(row, 4).toString();
    idPenerbit = jTable1.getModel().getValueAt(row, 6).toString();
    txtStok.setText(jTable1.getValueAt(row, 8).toString());
    
    setImage(lbGambar, id);
    getIDKategori(idKategori);
    getIDPenerbit(idPenerbit);
}

    
  private void updateData() {
    String idBuku = txtID.getText();
    String JudulBuku = txtNama.getText();
    String Pengarang = txtPengarang.getText();
    String tahunTerbit = txt_Tanggal.getText();
    String stok = txtStok.getText();
    String imagePath = txtImagePath.getText();

    if (idBuku.isEmpty() || JudulBuku.isEmpty() || Pengarang.isEmpty() || tahunTerbit.isEmpty()
            || cmbKategori.getSelectedIndex() == 0
            || cmbPenerbit.getSelectedIndex() == 0) {
        JOptionPane.showMessageDialog(this, "Semua kolom wajib diisi!", "Validasi", JOptionPane.ERROR_MESSAGE);
        return;
    }

    try {
        PreparedStatement st;

        if (!imagePath.isEmpty()) {
            String sql = "UPDATE buku SET Judul_Buku=?, Pengarang_Buku=?, Tahun_Terbit=?, ID_Kategori=?, ID_Penerbit=?, Stok=?, Gambar=? WHERE ID_Buku=?";
            st = conn.prepareStatement(sql);

            File imageFile = new File(imagePath);
            FileInputStream fis = new FileInputStream(imageFile);

            st.setString(1, JudulBuku);
            st.setString(2, Pengarang);
            st.setString(3, tahunTerbit);
            st.setString(4, idKategori);
            st.setString(5, idPenerbit);
            st.setString(6, stok);
            st.setBinaryStream(7, fis, (int) imageFile.length());
            st.setString(8, idBuku);

        } else {
            String sql = "UPDATE buku SET Judul_Buku=?, Pengarang_Buku=?, Tahun_Terbit=?, ID_Kategori=?, ID_Penerbit=?, Stok=? WHERE ID_Buku=?";
            st = conn.prepareStatement(sql);

            st.setString(1, JudulBuku);
            st.setString(2, Pengarang);
            st.setString(3, tahunTerbit);
            st.setString(4, idKategori);
            st.setString(5, idPenerbit);
            st.setString(6, stok);
            st.setString(7, idBuku);
        }

        if (st.executeUpdate() > 0) {
            JOptionPane.showMessageDialog(this, "Data berhasil diperbarui");
            resetForm();
            loadTable();
            showPanel();
        }

    } catch (Exception e) {
        e.printStackTrace();
    }
}


    private void deleteData() {
        int selectedRow = jTable1.getSelectedRow();
        int confirm = JOptionPane.showConfirmDialog(this, "Apakah yakin ingin menghapus data ini ?",
                "Konfirmasi Hapus Data", JOptionPane.YES_NO_OPTION);
        
        if(confirm == JOptionPane.YES_OPTION){
            String id = jTable1.getValueAt(selectedRow, 0).toString();
            
            try{
            String sql = "DELETE FROM buku WHERE ID_Buku=?";
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
        loadTable();
        showPanel();
    }

    private void getKategori(){
        try{
        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
        model.addElement("Pilih Kategori");
        
        String sql = "SELECT ID_Kategori, Nama_Kategori FROM kategori";
        PreparedStatement st = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        ResultSet rs = st.executeQuery();
        
        while(rs.next()){
         String namaKategori = rs.getString("Nama_Kategori");
         model.addElement(namaKategori);
        }
        
        cmbKategori.setModel(model);
        cmbKategori.addActionListener(e -> {
         int selectIndex = cmbKategori.getSelectedIndex();
         
         if(selectIndex > 0){
          try{
              rs.absolute(selectIndex);
              idKategori = rs.getString("ID_Kategori");
          }catch(SQLException ex){
           ex.printStackTrace();
          }
         }
        });
        }catch(Exception e){
            e.printStackTrace();
        }
        
    }
    
    private void getPenerbit(){
        try{
        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
        model.addElement("Pilih Penerbit");
        String sql = "SELECT ID_Penerbit, Nama_Penerbit FROM penerbit";
           PreparedStatement st = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        ResultSet rs = st.executeQuery();
        
        while(rs.next()){
         String namaKategori = rs.getString("Nama_Penerbit");
         model.addElement(namaKategori);
        }
        
        cmbPenerbit.setModel(model);
        cmbPenerbit.addActionListener(e -> {
         int selectIndex = cmbPenerbit.getSelectedIndex();
         
         if(selectIndex > 0){
          try{
              rs.absolute(selectIndex);
              idPenerbit = rs.getString("ID_Penerbit");
          }catch(SQLException ex){
           ex.printStackTrace();
          }
         }
        });
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    private void serchData() {
        String kataKunci = txtcari.getText();
        
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        model.setRowCount(0);
        
        try{
        String sql = "SELECT bk.ID_Buku, bk.Judul_Buku, bk.Pengarang_Buku,bk.Tahun_Terbit, ktg.ID_Kategori, ktg.Nama_Kategori,pnb.ID_Penerbit, pnb.Nama_Penerbit, bk.Stok, bk.Gambar \n" +
                     "FROM buku bk \n" +
                     "INNER JOIN kategori ktg ON ktg.ID_Kategori = bk.ID_Kategori \n" +
                     "INNER JOIN penerbit pnb ON pnb.ID_Penerbit = bk.ID_Penerbit \n" +
                     "WHERE bk.Judul_Buku LIKE ? OR bk.Pengarang_Buku Like ?";
        try(PreparedStatement st = conn.prepareStatement(sql)){
         st.setString(1, "%" + kataKunci + "%");
         st.setString(2, "%" + kataKunci + "%");
         ResultSet rs = st.executeQuery();
         
         while (rs.next()) {
            String idBuku = rs.getString("ID_Buku");
            String judulBuku = rs.getString("Judul_Buku");
            String pengarangBuku = rs.getString("Pengarang_Buku");
            String tahunTerbit = rs.getString("Tahun_Terbit");
            String idKategori = rs.getString("ID_Kategori");
            String namaKategori = rs.getString("Nama_Kategori");
            String idPenerbit = rs.getString("ID_Penerbit");
            String namaPenerbit = rs.getString("Nama_Penerbit");
            String stok = rs.getString("Stok");
            
           byte[] imageData = rs.getBytes("Gambar");
               ImageIcon imageIcon = null;

        if (imageData != null) {
        ImageIcon icon = new ImageIcon(imageData);
        Image img = icon.getImage().getScaledInstance(72, 106, Image.SCALE_SMOOTH);
        imageIcon = new ImageIcon(img);
}

            
            Object[] rowData = {idBuku, judulBuku, pengarangBuku, tahunTerbit, idKategori, namaKategori, idPenerbit, namaPenerbit, stok ,imageIcon};
            model.addRow(rowData);
        }
            }
        } catch(SQLException e){
                     java.util.logging.Logger.getLogger(menuBuku.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    
    private void getImage(){
     JFileChooser fileChooser = new JFileChooser();
     fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
     fileChooser.setAcceptAllFileFilterUsed(false);
     fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Gambar","jpg","png","jpeg"));
     
     int result = fileChooser.showOpenDialog(this);
     if(result == JFileChooser.APPROVE_OPTION){
      File selectedFile = fileChooser.getSelectedFile();
      String path = selectedFile.getAbsolutePath();
      txtImagePath.setText(path);
      
      try{
       byte[] img = Files.readAllBytes(selectedFile.toPath());
       ImageIcon imageIcon = new ImageIcon(img);
       
       int labelWidth = lbGambar.getWidth();
       int labelHeight = lbGambar.getHeight();
       
       int imageWidth = imageIcon.getIconWidth();
       int imageHeight = imageIcon.getIconHeight();
       
       double scaleX = (double) labelWidth / (double) imageWidth;
       double scaleY = (double) labelHeight / (double) imageHeight;
       double scale = Math.min(scaleX, scaleY);
       
       Image scaleImage = imageIcon.getImage().getScaledInstance(
            (int) (scale * imageWidth),
            (int) (scale * imageHeight),
            Image.SCALE_SMOOTH);
       
       lbGambar.setIcon(new ImageIcon(scaleImage));
      }catch(Exception e){
       e.printStackTrace();
      }
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
    
    void getIDKategori(String id){
        try{
            String sql = "SELECT ID_Kategori, Nama_Kategori FROM kategori";
            PreparedStatement st = conn.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            
            while(rs.next()){
             String idKat = rs.getString("ID_Kategori");
             String namaKat = rs.getString("Nama_Kategori");
             
             if(id.equals(idKat)){
                 cmbKategori.setSelectedItem(namaKat);
             }
            }
            
            cmbKategori.addItemListener(new ItemListener(){
                @Override
                public void itemStateChanged(ItemEvent e) {
                    if(e.getStateChange() == ItemEvent.SELECTED){
                        String selectedKat = cmbKategori.getSelectedItem().toString();
                        updateIDKategori(selectedKat);
                    }
                }
                
            });
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    void updateIDKategori(String selectedKat){
        try {
            String sql = "SELECT ID_Kategori, Nama_Kategori FROM kategori WHERE Nama_Kategori = ?";
            PreparedStatement st = conn.prepareStatement(sql);
            st.setString(1, selectedKat);
            ResultSet rs = st.executeQuery();
            
            if(rs.next()){
             String idKat = rs.getString("ID_Kategori");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    } 
    
     void getIDPenerbit(String id){
        try{
            String sql = "SELECT ID_Penerbit, Nama_Penerbit FROM penerbit";
            PreparedStatement st = conn.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            
            while(rs.next()){
             String idPer = rs.getString("ID_Penerbit");
             String namaPer = rs.getString("Nama_Penerbit");
             
             if(id.equals(idPer)){
                 cmbPenerbit.setSelectedItem(namaPer);
             }
            }
            
            cmbPenerbit.addItemListener(new ItemListener(){
                @Override
                public void itemStateChanged(ItemEvent e) {
                    if(e.getStateChange() == ItemEvent.SELECTED){
                        String selectedPenerbit = cmbPenerbit.getSelectedItem().toString();
                        updateIDPenerbit(selectedPenerbit);
                    }
                }
                
            });
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    void updateIDPenerbit(String selectedPenerbit){
    try {
            String sql = "SELECT ID_Penerbit, Nama_Penerbit FROM penerbit WHERE Nama_Penerbit = ?";
            PreparedStatement st = conn.prepareStatement(sql);
            st.setString(1, selectedPenerbit);
            ResultSet rs = st.executeQuery();
            
            if(rs.next()){
             String idPer = rs.getString("ID_Penerbit");
            }
        } catch (Exception e) {
            e.printStackTrace();
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
        int row = jTable1.getSelectedRow();
        String id = jTable1.getValueAt(row, 0).toString();
        
       

        HashMap<String, Object> parameters = new HashMap<>();
        parameters.put("idBuku", id);

        String reportPath = "D:/NetBeansProjects/ApkPerpus/src/Repost/LaporanDataBuku.jasper";

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
    
