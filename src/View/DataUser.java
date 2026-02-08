
package View;
import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.FlatLightLaf;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

public class DataUser extends javax.swing.JDialog {
    
  Connection conn;
PreparedStatement ps;
ResultSet rs;

int currentPage = 1;
int rowsPerPage = 7;
int totalData = 0;
int totalPage = 0;

private final int noColumnIndex = 0;
private final int nocolumnWidth = 100;
DefaultTableModel model;

    public String getIDUser() {
        return iduser;
    }

    public String getNamaUser() {
        return namaUser;
    }

    
private String iduser;
private String namaUser;

  
    public DataUser(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        conn = koneksi.koneksi.KoneksiDB();
       
          model = new DefaultTableModel(
        new Object[]{"ID","Nama"}, 0
    );
    jTable1.setModel(model);
    setColumnWitdh();
    hitungTotalData();
    loadComboPage();
    loadTable();
    accitonButton();
    }
    
      void hitungTotalData() {
    try {
        ps = conn.prepareStatement("SELECT COUNT(*) FROM User");
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
            "SELECT * FROM User LIMIT ? OFFSET ?"
        );
        ps.setInt(1, rowsPerPage);
        ps.setInt(2, offset);
        rs = ps.executeQuery();

        while (rs.next()) {
            model.addRow(new Object[]{
              rs.getString("ID_User"),
                rs.getString("Nama_User"),
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
    
     void updatePage() {
    loadTable();
    cbPage.setSelectedItem(currentPage);
}

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelView = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new templet.TableCustom();
        btnFirst = new javax.swing.JButton();
        btnPrev = new javax.swing.JButton();
        btnLast = new javax.swing.JButton();
        btnNext = new javax.swing.JButton();
        cbPage = new javax.swing.JComboBox<>();
        txtcari = new templet.TextfieldCustom1();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        panelView.setBackground(new java.awt.Color(255, 255, 255));

        jLabel13.setBackground(new java.awt.Color(102, 102, 102));
        jLabel13.setFont(new java.awt.Font("Bahnschrift", 1, 18)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(51, 51, 51));
        jLabel13.setText("Data Anggota Perpustakkan");

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/users.png"))); // NOI18N

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
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
                .addGroup(panelViewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelViewLayout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addGroup(panelViewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 420, Short.MAX_VALUE)
                            .addGroup(panelViewLayout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel13))
                            .addComponent(txtcari, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(panelViewLayout.createSequentialGroup()
                        .addGap(47, 47, 47)
                        .addComponent(btnFirst)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnPrev, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cbPage, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnNext, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnLast)))
                .addContainerGap(21, Short.MAX_VALUE))
        );
        panelViewLayout.setVerticalGroup(
            panelViewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelViewLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(panelViewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel13, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtcari, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 327, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(panelViewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnFirst)
                    .addComponent(btnPrev)
                    .addComponent(btnLast)
                    .addComponent(btnNext)
                    .addComponent(cbPage, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelView, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelView, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
     
    }//GEN-LAST:event_jTable1MouseClicked

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

    private void txtcariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtcariActionPerformed
        serchData();
    }//GEN-LAST:event_txtcariActionPerformed


    public static void main(String args[]) {
   FlatLightLaf.setup();
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                DataUser dialog = new DataUser(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnFirst;
    private javax.swing.JButton btnLast;
    private javax.swing.JButton btnNext;
    private javax.swing.JButton btnPrev;
    private javax.swing.JComboBox<Integer> cbPage;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JScrollPane jScrollPane2;
    private templet.TableCustom jTable1;
    private javax.swing.JPanel panelView;
    private templet.TextfieldCustom1 txtcari;
    // End of variables declaration//GEN-END:variables

private void serchData() {
        String kataKunci = txtcari.getText();
        
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        model.setRowCount(0);
        
        try{
        String sql = "SELECT * FROM User WHERE Nama_User LIKE ? OR ID_User Like ? ";
        try(PreparedStatement st = conn.prepareStatement(sql)){
         st.setString(1, "%" + kataKunci + "%");
         st.setString(2, "%" + kataKunci + "%");
         ResultSet rs = st.executeQuery();
         
         while(rs.next()){
             String iduser = rs.getString("ID_user");
             String namauser = rs.getString("Nama_User");
             
             Object[] rowData = {iduser,namauser};
             model.addRow(rowData);
                }
            }
        } catch(SQLException e){
                     java.util.logging.Logger.getLogger(menuAnggota.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    private void accitonButton() {
      txtcari.addKeyListener(new KeyAdapter(){
          @Override
          public void keyReleased(KeyEvent e) {
              serchData();
          }
       
      });
      
      jTable1.addMouseListener(new MouseAdapter() {
          @Override
          public void mouseClicked(MouseEvent e) {
              pilihData();
          }
 
      });
    }
    
     private void pilihData() {
         int row = jTable1.getSelectedRow();
         
          iduser = jTable1.getValueAt(row, 0).toString();
          namaUser = jTable1.getValueAt(row, 1).toString();
          
          dispose();
      }
}
