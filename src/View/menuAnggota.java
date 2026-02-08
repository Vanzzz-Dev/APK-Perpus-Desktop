
package View;
import com.formdev.flatlaf.FlatClientProperties;
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
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;


public class menuAnggota extends javax.swing.JPanel {
Connection conn;
PreparedStatement ps;
ResultSet rs;

int currentPage = 1;
int rowsPerPage = 9;
int totalData = 0;
int totalPage = 0;

private final int noColumnIndex = 0;
private final int nocolumnWidth = 100;
DefaultTableModel model;
  
    public menuAnggota() {
        initComponents();
        conn = koneksi.koneksi.KoneksiDB();
       
          model = new DefaultTableModel(
        new Object[]{"ID","Nama","Email","Telepon","JenisKelamin","Tanggal Bergabung"}, 0
    );
    jTable1.setModel(model);
 setColumnWitdh();
    cbPage.removeAllItems();
    hitungTotalData();
    loadComboPage();
    loadTable();
    setLayout();
    visbleBtn();
    txtcari.setPlaceholder("Serch");
    txtID.setEditable(false);
    }

    public void visbleBtn(){
        btnDelete.setVisible(false);
        btnCancel.setVisible(false);
        btnPrint.setVisible(false);
    }
    public void setLayout(){
        txtNama.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Nama Anggota");
        txtEmail.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Email");
        txtTelepon.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Telepon");
        txt_Tanggal.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Tanggal Bergabung");
      }
    
    void hitungTotalData() {
    try {
        ps = conn.prepareStatement("SELECT COUNT(*) FROM anggota");
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
            "SELECT * FROM anggota LIMIT ? OFFSET ?"
        );
        ps.setInt(1, rowsPerPage);
        ps.setInt(2, offset);
        rs = ps.executeQuery();

        while (rs.next()) {
            model.addRow(new Object[]{
              rs.getString("ID_Anggota"),
                rs.getString("Nama_Anggota"),
                rs.getString("Email"),
                rs.getString("Telepon"),
                rs.getString("Jenis_Kelamin"),
                rs.getDate("Tanggal_Bergabung")
            });
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
       txtEmail.setText("");
       txtTelepon.setText("");
       rbJenisKelamin.clearSelection();
       txt_Tanggal.setText("");
    }
    
    private void showPanel(){
        panelMain.removeAll();
        panelMain.add(new menuAnggota());
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

        rbJenisKelamin = new javax.swing.ButtonGroup();
        dateChooser1 = new com.raven.datechooser.DateChooser();
        panelMain = new javax.swing.JPanel();
        panelView = new javax.swing.JPanel();
        btnPrint = new templet.IconButtonCustom();
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
        jLabel9 = new javax.swing.JLabel();
        txtpass = new javax.swing.JPasswordField();
        jLabel5 = new javax.swing.JLabel();
        txtEmail = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtTelepon = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txt_Tanggal = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        rbLaki = new javax.swing.JRadioButton();
        rbPerempuan = new javax.swing.JRadioButton();

        dateChooser1.setTextRefernce(txt_Tanggal);

        setLayout(new java.awt.CardLayout());

        panelMain.setBackground(new java.awt.Color(255, 255, 255));
        panelMain.setLayout(new java.awt.CardLayout());

        panelView.setBackground(new java.awt.Color(255, 255, 255));

        btnPrint.setBackground(new java.awt.Color(0, 204, 204));
        btnPrint.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Print.png"))); // NOI18N
        btnPrint.setText("PRINT");
        btnPrint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrintActionPerformed(evt);
            }
        });

        jLabel13.setBackground(new java.awt.Color(102, 102, 102));
        jLabel13.setFont(new java.awt.Font("Bahnschrift", 1, 18)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(51, 51, 51));
        jLabel13.setText("Data Anggota Perpustakkan");

        jLabel14.setFont(new java.awt.Font("Bahnschrift", 1, 12)); // NOI18N
        jLabel14.setText("Master Data > Anggota");

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/users.png"))); // NOI18N

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
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
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
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 406, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
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
        jLabel15.setText("Tambah Data Anggota Perpustakkan");

        jLabel16.setFont(new java.awt.Font("Bahnschrift", 1, 12)); // NOI18N
        jLabel16.setText("Master Data > Anggota");

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/users.png"))); // NOI18N

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
        jLabel4.setText("Nama");

        txtNama.setFont(new java.awt.Font("Bahnschrift", 0, 12)); // NOI18N

        jLabel9.setFont(new java.awt.Font("Bahnschrift", 0, 14)); // NOI18N
        jLabel9.setText("Password");

        jLabel5.setFont(new java.awt.Font("Bahnschrift", 0, 14)); // NOI18N
        jLabel5.setText("Email");

        txtEmail.setFont(new java.awt.Font("Bahnschrift", 0, 12)); // NOI18N
        txtEmail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtEmailActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Bahnschrift", 0, 14)); // NOI18N
        jLabel6.setText("Telepon");

        txtTelepon.setFont(new java.awt.Font("Bahnschrift", 0, 12)); // NOI18N
        txtTelepon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTeleponActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Bahnschrift", 0, 14)); // NOI18N
        jLabel7.setText("Tanggal Bergabung");

        txt_Tanggal.setFont(new java.awt.Font("Bahnschrift", 0, 12)); // NOI18N
        txt_Tanggal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_TanggalActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Bahnschrift", 0, 14)); // NOI18N
        jLabel8.setText("Jenis Kelamin");

        rbJenisKelamin.add(rbLaki);
        rbLaki.setText("Laki-laki");

        rbJenisKelamin.add(rbPerempuan);
        rbPerempuan.setText("Perempuan");

        javax.swing.GroupLayout panelAddLayout = new javax.swing.GroupLayout(panelAdd);
        panelAdd.setLayout(panelAddLayout);
        panelAddLayout.setHorizontalGroup(
            panelAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelAddLayout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(panelAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtEmail)
                    .addComponent(txtTelepon)
                    .addComponent(txt_Tanggal)
                    .addGroup(panelAddLayout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel15)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel16)
                        .addGap(14, 14, 14))
                    .addGroup(panelAddLayout.createSequentialGroup()
                        .addGroup(panelAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtNama)
                            .addComponent(txtID)
                            .addGroup(panelAddLayout.createSequentialGroup()
                                .addGroup(panelAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(panelAddLayout.createSequentialGroup()
                                        .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(btnBatal, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(panelAddLayout.createSequentialGroup()
                                        .addGap(6, 6, 6)
                                        .addGroup(panelAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel4)
                                            .addComponent(jLabel3))))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addGap(24, 24, 24))
                    .addGroup(panelAddLayout.createSequentialGroup()
                        .addComponent(txtpass)
                        .addGap(24, 24, 24))
                    .addGroup(panelAddLayout.createSequentialGroup()
                        .addGroup(panelAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7)
                            .addComponent(jLabel8)
                            .addGroup(panelAddLayout.createSequentialGroup()
                                .addComponent(rbLaki)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(rbPerempuan))
                            .addGroup(panelAddLayout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addGroup(panelAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel6)
                                    .addComponent(jLabel5)))
                            .addComponent(jLabel9))
                        .addGap(651, 651, 651))))
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
                .addComponent(txtID, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(jLabel4)
                .addGap(5, 5, 5)
                .addComponent(txtNama, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(jLabel9)
                .addGap(5, 5, 5)
                .addComponent(txtpass, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(jLabel5)
                .addGap(5, 5, 5)
                .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(jLabel6)
                .addGap(5, 5, 5)
                .addComponent(txtTelepon, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(jLabel8)
                .addGap(5, 5, 5)
                .addGroup(panelAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rbLaki)
                    .addComponent(rbPerempuan))
                .addGap(10, 10, 10)
                .addComponent(jLabel7)
                .addGap(5, 5, 5)
                .addComponent(txt_Tanggal, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(42, Short.MAX_VALUE))
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
     
     txtID.setText(setIDAnggota());
       if (btnAdd.getText().equals("UBAH")) {
    dataTabel();
    btnSave.setText("PERBARUI");
}

    }//GEN-LAST:event_btnAddActionPerformed

    private void txt_TanggalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_TanggalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_TanggalActionPerformed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
     if(btnSave.getText().equals("TAMBAH")){
         btnSave.setText("SIMPAN");
     }else if(btnSave.getText().equals("SIMPAN")){
         insertData();
     }else if(btnSave.getText().equals("PERBARUI")){
         updateData();
     }
     
    }//GEN-LAST:event_btnSaveActionPerformed

    private void btnBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBatalActionPerformed
       showPanel();
       loadTable();
    }//GEN-LAST:event_btnBatalActionPerformed

    private void txtEmailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtEmailActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtEmailActionPerformed

    private void txtTeleponActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTeleponActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTeleponActionPerformed

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
    }//GEN-LAST:event_btnCancelActionPerformed

    private void btnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrintActionPerformed
        cetakLaporan();
    }//GEN-LAST:event_btnPrintActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private templet.IconButtonCustom btnAdd;
    private templet.IconButtonCustom btnBatal;
    private templet.IconButtonCustom btnCancel;
    private templet.IconButtonCustom btnDelete;
    private javax.swing.JButton btnFirst;
    private javax.swing.JButton btnLast;
    private javax.swing.JButton btnNext;
    private javax.swing.JButton btnPrev;
    private templet.IconButtonCustom btnPrint;
    private templet.IconButtonCustom btnSave;
    private javax.swing.JComboBox<Integer> cbPage;
    private com.raven.datechooser.DateChooser dateChooser1;
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
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane2;
    private templet.TableCustom jTable1;
    private javax.swing.JPanel panelAdd;
    private javax.swing.JPanel panelMain;
    private javax.swing.JPanel panelView;
    private javax.swing.ButtonGroup rbJenisKelamin;
    private javax.swing.JRadioButton rbLaki;
    private javax.swing.JRadioButton rbPerempuan;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtID;
    private javax.swing.JTextField txtNama;
    private javax.swing.JTextField txtTelepon;
    private javax.swing.JTextField txt_Tanggal;
    private templet.TextfieldCustom1 txtcari;
    private javax.swing.JPasswordField txtpass;
    // End of variables declaration//GEN-END:variables

 private String setIDAnggota() {
    String urutan;
    Date now = new Date();
    SimpleDateFormat noFormat = new SimpleDateFormat("yyMM");
    String no = noFormat.format(now);

    String sql = "SELECT RIGHT(ID_Anggota, 3) AS Nomor " +
                 "FROM anggota " +
                 "WHERE ID_Anggota LIKE ? " +
                 "ORDER BY ID_Anggota DESC " +
                 "LIMIT 1";

    try (PreparedStatement st = conn.prepareStatement(sql)) {
        st.setString(1, "AGT" + no + "%");
        ResultSet rs = st.executeQuery();

        if (rs.next()) {
            int nomor = Integer.parseInt(rs.getString("Nomor")) + 1;
            urutan = "AGT" + no + String.format("%03d", nomor);
        } else {
            urutan = "AGT" + no + "001";
        }

    } catch (SQLException e) {
        java.util.logging.Logger
            .getLogger(menuAnggota.class.getName())
            .log(Level.SEVERE, null, e);
        urutan = "AGT" + no + "001";
    }

    return urutan;
}

    private void insertData() {
        String idAnggota = txtID.getText();
        String namaAnggota = txtNama.getText();
        String password = txtpass.getText();
        String emailAnggota = txtEmail.getText();
        String teleponAnggota = txtTelepon.getText();
        String jenisKelamin;
        if(rbLaki.isSelected()){
            jenisKelamin = rbLaki.getText();
        }else if(rbPerempuan.isSelected()){
            jenisKelamin = rbPerempuan.getText();
        }else {
            jenisKelamin = "";
        }
        String level = "Anggota";
        
        String tanggalBergabung = txt_Tanggal.getText();
        
        if(idAnggota.isEmpty() || namaAnggota.isEmpty() || password.isEmpty() || emailAnggota.isEmpty()|| teleponAnggota.isEmpty() || jenisKelamin.isEmpty() || tanggalBergabung.isEmpty()){
            JOptionPane.showMessageDialog(this,"Semua kolom harus diisi !","Validasi",JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        try{
         String sql = "INSERT INTO anggota (ID_Anggota, Nama_Anggota,Password, Email, Telepon, Jenis_Kelamin, Tanggal_Bergabung,LevelAnggota) VALUES (?,?,?,?,?,?,?,?)";
         try(PreparedStatement st = conn.prepareStatement(sql)){
          st.setString(1, idAnggota);
          st.setString(2, namaAnggota);
          st.setString(3, password);
          st.setString(4, emailAnggota);
          st.setString(5, teleponAnggota);
          st.setString(6, jenisKelamin);
          st.setString(7, tanggalBergabung);
          st.setString(8, level);
          
          int rowInserted = st.executeUpdate();
          if(rowInserted > 0){
              JOptionPane.showMessageDialog(this, "Data Berhasil Di Tambahkan");
              resetForm();
              loadTable();
              showPanel();
          }
         }
        }catch (Exception e){
        java.util.logging.Logger.getLogger(menuAnggota.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    
    private void dataTabel() {
    int row = jTable1.getSelectedRow();

    if (row == -1) {
        JOptionPane.showMessageDialog(this,
            "Silakan pilih data pada tabel terlebih dahulu!",
            "Peringatan",
            JOptionPane.WARNING_MESSAGE
        );
        return;
    }

    panelView.setVisible(false);
    panelAdd.setVisible(true);

    jLabel15.setText("Perbarui Data Anggota Perpustakaan");

    txtID.setEnabled(false);
    txt_Tanggal.setEnabled(false);

    txtID.setText(jTable1.getValueAt(row, 0).toString());
    txtNama.setText(jTable1.getValueAt(row, 1).toString());
    txtEmail.setText(jTable1.getValueAt(row, 2).toString());
    txtTelepon.setText(jTable1.getValueAt(row, 3).toString());

    String jenisKelamin = jTable1.getValueAt(row, 4).toString();
    if (jenisKelamin.equals("Laki-laki")) {
        rbLaki.setSelected(true);
    } else if (jenisKelamin.equals("Perempuan")) {
        rbPerempuan.setSelected(true);
    }

    txt_Tanggal.setText(jTable1.getValueAt(row, 5).toString());
}

    
    private void updateData() {
        String idAnggota = txtID.getText();
        String namaAnggota = txtNama.getText();
        String emailAnggota = txtEmail.getText();
        String teleponAnggota = txtTelepon.getText();
        String jenisKelamin;
        if(rbLaki.isSelected()){
            jenisKelamin = rbLaki.getText();
        }else if(rbPerempuan.isSelected()){
            jenisKelamin = rbPerempuan.getText();
        }else {
            jenisKelamin = "";
        }
        
        String tanggalBergabung = txt_Tanggal.getText();
        
        if(idAnggota.isEmpty() || namaAnggota.isEmpty() || emailAnggota.isEmpty()|| teleponAnggota.isEmpty() || jenisKelamin.isEmpty() || tanggalBergabung.isEmpty()){
            JOptionPane.showMessageDialog(this,"Semua kolom harus diisi !","Validasi",JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        try{
            String sql = "UPDATE anggota SET Nama_Anggota=?, Email=?, Telepon=?, Jenis_Kelamin=?, Tanggal_Bergabung=? WHERE ID_Anggota=?";
         try(PreparedStatement st = conn.prepareStatement(sql)){
          st.setString(1, namaAnggota);
          st.setString(2, emailAnggota);
          st.setString(3, teleponAnggota);
          st.setString(4, jenisKelamin);
          st.setString(5, tanggalBergabung);
          st.setString(6, idAnggota);
          
          int rowUpdated = st.executeUpdate();
          if(rowUpdated > 0){
              JOptionPane.showMessageDialog(this, "Data Berhasil Di Perbarui");
              resetForm();
              loadTable();
              showPanel();
          }
         }
        }catch (Exception e){
        java.util.logging.Logger.getLogger(menuAnggota.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    private void deleteData() {
        int selectedRow = jTable1.getSelectedRow();
        int confirm = JOptionPane.showConfirmDialog(this, "Apakah yakin ingin menghapus data ini ?",
                "Konfirmasi Hapus Data", JOptionPane.YES_NO_OPTION);
        
        if(confirm == JOptionPane.YES_OPTION){
            String id = jTable1.getValueAt(selectedRow, 0).toString();
            
            try{
            String sql = "DELETE FROM anggota WHERE ID_Anggota=?";
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
             java.util.logging.Logger.getLogger(menuAnggota.class.getName()).log(Level.SEVERE, null, e);
           }
            
        }
        resetForm();
        loadTable();
        showPanel();
    }

    private void serchData() {
        String kataKunci = txtcari.getText();
        
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        model.setRowCount(0);
        
        try{
        String sql = "SELECT * FROM anggota WHERE Nama_Anggota LIKE ? OR Email Like ? ";
        try(PreparedStatement st = conn.prepareStatement(sql)){
         st.setString(1, "%" + kataKunci + "%");
         st.setString(2, "%" + kataKunci + "%");
         ResultSet rs = st.executeQuery();
         
         while(rs.next()){
             String idAnggota = rs.getString("ID_Anggota");
             String namaAnggota = rs.getString("Nama_Anggota");
             String emailAnggota = rs.getString("Email");
             String teleponAnggota = rs.getString("Telepon");
             String jenisKelamin = rs.getString("Jenis_Kelamin");
             String tanggalBergabung = rs.getString("Tanggal_Bergabung");
             
             Object[] rowData = {idAnggota,namaAnggota,emailAnggota,teleponAnggota,jenisKelamin,tanggalBergabung};
             model.addRow(rowData);
                }
            }
        } catch(SQLException e){
                     java.util.logging.Logger.getLogger(menuAnggota.class.getName()).log(Level.SEVERE, null, e);
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
        parameters.put("idAnggota", id);

        String reportPath = "D:/NetBeansProjects/ApkPerpus/src/Repost/kartuAnggota.jasper";

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
    
