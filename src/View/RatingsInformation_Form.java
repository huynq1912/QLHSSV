/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import DAO.RatingsDAO;
import DB.Connect;
import Model.Ratings;
import java.awt.Color;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author admin
 */
public class RatingsInformation_Form extends javax.swing.JPanel {

    /**
     * Creates new form RatingsInformation_Form
     */
    public RatingsInformation_Form() {
        initComponents();
        Ma_KH();
        Ma_SV();
        inBang();
        dataBang();
        setOpaque(false);
    }
    
    
      private DefaultTableModel tblModel;
    private void Ma_SV()
    {
        try 
        {
            Connection con = Connect.openConnection();
            Statement st = con.createStatement();
            String sql = "select * from tblSinhVien";
            ResultSet rs = st.executeQuery(sql);
            while(rs.next())
            {
                this.cbMaSV.addItem(rs.getString("Ma_SV"));
            }
            con.close();
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        }
    }
    
    private void Ma_KH()
    {
        try 
        {
            Connection con = Connect.openConnection();
            Statement st = con.createStatement();
            String sql = "select * from tblKhoaHoc";
            ResultSet rs = st.executeQuery(sql);
            while(rs.next())
            {
                this.cbMaKH.addItem(rs.getString("Ma_KH"));
            }
            con.close();
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        }
    }
    
    private void inBang()
    {
        tblModel = new DefaultTableModel();
        tblModel.setColumnIdentifiers(new String[] {"Mã SV", "Họ Tên", "Mã KH", "Năm Học", "Học Kỳ", "Xếp Loại"});
        tblXepHang.setModel(tblModel);
    }
    
    private void dataBang()
    {
        try 
        {
            RatingsDAO CSDL = new RatingsDAO();
            List<Ratings> list = CSDL.findAll();
            tblModel.setRowCount(0);
            for(Ratings it:list)
            {
                tblModel.addRow(new Object[]{
                    it.getMaSV(), it.getHoTen(), it.getMaKH(), it.getNamHoc(), it.getHocKy(), it.getXepLoai()
                });
            }
            tblModel.fireTableDataChanged();
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
//            txtThongBao.setText("Lỗi");
        }
    }
    private void dataBang1()
    {
        try 
        {
            RatingsDAO CSDL = new RatingsDAO();
            Ratings it = CSDL.findByMa(cbMaSV.getSelectedItem().toString(), txtNamHoc.getText(), cbHocKy.getSelectedItem().toString());
            tblModel.setRowCount(0);
            
                tblModel.addRow(new Object[]{
                   it.getMaSV(), it.getHoTen(), it.getMaKH(), it.getNamHoc(), it.getHocKy(), it.getXepLoai()
                });
            
            tblModel.fireTableDataChanged();
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
//            txtThongBao.setText("Lỗi");
        }
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelBorder1 = new CustomSwing.PanelBorder();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        cbMaSV = new javax.swing.JComboBox<>();
        cbMaKH = new javax.swing.JComboBox<>();
        cbHocKy = new javax.swing.JComboBox<>();
        cbXepLoai = new javax.swing.JComboBox<>();
        txtHoTen = new javax.swing.JTextField();
        txtNamHoc = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblXepHang = new javax.swing.JTable();
        btntaomoi = new javax.swing.JButton();
        btnthem = new javax.swing.JButton();
        btncapnhat = new javax.swing.JButton();
        btnxoa = new javax.swing.JButton();
        btntimkiem = new javax.swing.JButton();
        txtThongBao = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        btnreset = new javax.swing.JButton();

        setFocusCycleRoot(true);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jLabel1.setText("Mã SV: ");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jLabel2.setText("Họ tên: ");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jLabel3.setText("Mã KH: ");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jLabel4.setText("Năm học: ");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jLabel5.setText("Học Kỳ: ");

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jLabel6.setText("Xếp Loại: ");

        cbMaKH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbMaKHActionPerformed(evt);
            }
        });

        cbHocKy.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "Cả Năm" }));

        cbXepLoai.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Xuất Sắc", "Giỏi", "Khá", "Trung Bình", "Yếu" }));

        tblXepHang.setModel(new javax.swing.table.DefaultTableModel(
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
        tblXepHang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblXepHangMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblXepHang);

        btntaomoi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/icons8_new_20px.png"))); // NOI18N
        btntaomoi.setText("Tạo mới");
        btntaomoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btntaomoiActionPerformed(evt);
            }
        });

        btnthem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/icons8_add_20px_1.png"))); // NOI18N
        btnthem.setText("Thêm");
        btnthem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnthemActionPerformed(evt);
            }
        });

        btncapnhat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/icons8_rotate_right_20px.png"))); // NOI18N
        btncapnhat.setText("Cập nhật");
        btncapnhat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btncapnhatActionPerformed(evt);
            }
        });

        btnxoa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/icons8_delete_20px.png"))); // NOI18N
        btnxoa.setText("Xóa");
        btnxoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnxoaActionPerformed(evt);
            }
        });

        btntimkiem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/icons8_search_20px_1.png"))); // NOI18N
        btntimkiem.setText("Tìm kiếm");
        btntimkiem.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btntimkiemMouseClicked(evt);
            }
        });
        btntimkiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btntimkiemActionPerformed(evt);
            }
        });

        txtThongBao.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtThongBao.setForeground(new java.awt.Color(255, 0, 51));
        txtThongBao.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jButton1.setForeground(new java.awt.Color(0, 102, 255));
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/icons8_notification_20px.png"))); // NOI18N
        jButton1.setText("Thông báo");

        btnreset.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/icons8_update_20px.png"))); // NOI18N
        btnreset.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnresetMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout panelBorder1Layout = new javax.swing.GroupLayout(panelBorder1);
        panelBorder1.setLayout(panelBorder1Layout);
        panelBorder1Layout.setHorizontalGroup(
            panelBorder1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBorder1Layout.createSequentialGroup()
                .addGroup(panelBorder1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelBorder1Layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addGroup(panelBorder1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelBorder1Layout.createSequentialGroup()
                                .addGap(1, 1, 1)
                                .addGroup(panelBorder1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel1)
                                    .addGroup(panelBorder1Layout.createSequentialGroup()
                                        .addGroup(panelBorder1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel4)
                                            .addComponent(jLabel3))
                                        .addGap(0, 0, 0)
                                        .addGroup(panelBorder1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(cbHocKy, 0, 200, Short.MAX_VALUE)
                                            .addComponent(txtNamHoc)))
                                    .addComponent(jLabel5)
                                    .addGroup(panelBorder1Layout.createSequentialGroup()
                                        .addComponent(jLabel6)
                                        .addGap(0, 0, 0)
                                        .addComponent(cbXepLoai, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(panelBorder1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(cbMaKH, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(cbMaSV, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(panelBorder1Layout.createSequentialGroup()
                                    .addComponent(jLabel2)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(txtHoTen, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 35, Short.MAX_VALUE)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 596, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelBorder1Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btntaomoi, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(43, 43, 43)
                        .addComponent(btnthem, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(42, 42, 42)
                        .addComponent(btncapnhat, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(42, 42, 42)
                        .addComponent(btnxoa, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(39, 39, 39)
                        .addComponent(btntimkiem, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(33, 33, 33)
                        .addComponent(btnreset)))
                .addContainerGap(18, Short.MAX_VALUE))
            .addGroup(panelBorder1Layout.createSequentialGroup()
                .addGap(161, 161, 161)
                .addComponent(jButton1)
                .addGap(36, 36, 36)
                .addComponent(txtThongBao, javax.swing.GroupLayout.PREFERRED_SIZE, 472, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelBorder1Layout.setVerticalGroup(
            panelBorder1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBorder1Layout.createSequentialGroup()
                .addGroup(panelBorder1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelBorder1Layout.createSequentialGroup()
                        .addGap(46, 46, 46)
                        .addGroup(panelBorder1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbMaSV, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(43, 43, 43)
                        .addGroup(panelBorder1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtHoTen, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(40, 40, 40)
                        .addGroup(panelBorder1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbMaKH, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(43, 43, 43)
                        .addGroup(panelBorder1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtNamHoc))
                        .addGap(42, 42, 42)
                        .addGroup(panelBorder1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbHocKy, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(39, 39, 39)
                        .addGroup(panelBorder1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbXepLoai, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(78, 78, 78)
                        .addGroup(panelBorder1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btntaomoi, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnthem, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btncapnhat, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnxoa, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btntimkiem, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnreset, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(29, 29, 29))
                    .addGroup(panelBorder1Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 380, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(143, 143, 143)))
                .addGroup(panelBorder1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtThongBao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1))
                .addGap(41, 41, 41))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelBorder1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelBorder1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btntimkiemMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btntimkiemMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_btntimkiemMouseClicked

    private void btntaomoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btntaomoiActionPerformed
        txtHoTen.setText("");
        txtNamHoc.setText("");
        cbHocKy.setSelectedIndex(0);
        cbXepLoai.setSelectedIndex(0);
        cbMaSV.setSelectedIndex(0);
        cbMaKH.setSelectedIndex(0);
        
        txtHoTen.setBackground(Color.white);
        txtNamHoc.setBackground(Color.white);
        txtThongBao.setText("");
        dataBang();
    }//GEN-LAST:event_btntaomoiActionPerformed

    private void btnthemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnthemActionPerformed
         if(txtHoTen.getText().equals(""))
        {
            txtThongBao.setText("Chưa nhập tên");
            txtHoTen.setBackground(Color.red);
            txtHoTen.requestFocus();
        }
        else if(txtNamHoc.getText().equals(""))
        {
            txtThongBao.setText("Chưa nhập năm học");
            txtNamHoc.setBackground(Color.red);
            txtNamHoc.requestFocus();
        }
        else
        {
            try 
            {
                Ratings ratings = new Ratings();
                ratings.setMaSV(cbMaSV.getSelectedItem().toString());
                ratings.setHoTen(txtHoTen.getText());
                ratings.setMaKH(cbMaKH.getSelectedItem().toString());
                ratings.setNamHoc(txtNamHoc.getText());
                ratings.setHocKy(cbHocKy.getSelectedItem().toString());
                ratings.setXepLoai(cbXepLoai.getSelectedItem().toString());
                
                RatingsDAO CSDL = new RatingsDAO();
                if(CSDL.insert(ratings))
                {
                    txtThongBao.setText("Thêm thành công");
                    dataBang();
                }
                else
                {
                    txtThongBao.setText("Thêm không thành công");
                }
            } catch (Exception e) 
            {
                e.printStackTrace();
                txtThongBao.setText("Lỗi");
            }
        }
    }//GEN-LAST:event_btnthemActionPerformed

    private void btncapnhatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btncapnhatActionPerformed
          if(txtHoTen.getText().equals(""))
        {
            txtThongBao.setText("Chưa nhập tên");
            txtHoTen.setBackground(Color.red);
            txtHoTen.requestFocus();
        }
        else if(txtNamHoc.getText().equals(""))
        {
            txtThongBao.setText("Chưa nhập năm học");
            txtNamHoc.setBackground(Color.red);
            txtNamHoc.requestFocus();
        }
        else
        {
            try 
            {
                Ratings ratings = new Ratings();
                ratings.setMaSV(cbMaSV.getSelectedItem().toString());
                ratings.setHoTen(txtHoTen.getText());
                ratings.setMaKH(cbMaKH.getSelectedItem().toString());
                ratings.setNamHoc(txtNamHoc.getText());
                ratings.setHocKy(cbHocKy.getSelectedItem().toString());
                ratings.setXepLoai(cbXepLoai.getSelectedItem().toString());
                
                RatingsDAO CSDL = new RatingsDAO();
                if(CSDL.update(ratings))
                {
                    txtThongBao.setText("Cập Nhật thành công");
                    dataBang();
                }
                else
                {
                    txtThongBao.setText("Cập Nhật không thành công");
                }
            } catch (Exception e) 
            {
                e.printStackTrace();
                txtThongBao.setText("Lỗi");
            }
        }
    }//GEN-LAST:event_btncapnhatActionPerformed

    private void btnxoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnxoaActionPerformed
         int hoi = JOptionPane.showConfirmDialog( null, "Bạn thật sự muốn xóa", "Thông báo", JOptionPane.YES_NO_OPTION);
        if(hoi == JOptionPane.YES_OPTION)
        {
            try {
                RatingsDAO CSDL = new RatingsDAO();
                if(CSDL.delete(cbMaSV.getSelectedItem().toString(), cbHocKy.getSelectedItem().toString()))
                {
                    txtThongBao.setText("Xóa thành công");
                    dataBang();
                }
                else
                {
                    txtThongBao.setText("Xóa không thành công");
                }
            } catch (Exception e) {
                e.printStackTrace();
                txtThongBao.setText("Lỗi");
            }
        }
    }//GEN-LAST:event_btnxoaActionPerformed

    private void btntimkiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btntimkiemActionPerformed
         try 
            {
                RatingsDAO CSDL = new RatingsDAO();
                Ratings it = CSDL.findByMa(cbMaSV.getSelectedItem().toString(), txtNamHoc.getText(), cbHocKy.getSelectedItem().toString());
                if(it != null)
                {
                    txtThongBao.setText("Đã tìm thấy");
                    dataBang1();
                }
                else
                {
                    txtThongBao.setText("Không tìm thấy");
                    inBang();
                }
            } catch (Exception e) 
            {
                e.printStackTrace();
                txtThongBao.setText("Lỗi");
            }
    }//GEN-LAST:event_btntimkiemActionPerformed

    private void tblXepHangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblXepHangMouseClicked
          try 
        {
            int row = tblXepHang.getSelectedRow();
            if(row>=0)
            {
                String id = (String)tblXepHang.getValueAt(row, 0);
                RatingsDAO CSDL = new RatingsDAO();
                Ratings ratings = CSDL.findByMa_SV(id);
                
                if(ratings != null)
                {
                    cbMaSV.setSelectedItem(ratings.getMaSV());
                    txtHoTen.setText(ratings.getHoTen());
                    cbMaKH.setSelectedItem(ratings.getMaKH());
                    txtNamHoc.setText(ratings.getNamHoc());
                    cbHocKy.setSelectedItem(ratings.getHocKy());
                    cbXepLoai.setSelectedItem(ratings.getXepLoai());
                }
            }
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
            txtThongBao.setText("Lỗi");
        }
    }//GEN-LAST:event_tblXepHangMouseClicked

    private void cbMaKHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbMaKHActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbMaKHActionPerformed

    private void btnresetMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnresetMouseClicked
       dataBang();
       txtThongBao.setText("Làm mới thành công");
    }//GEN-LAST:event_btnresetMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btncapnhat;
    private javax.swing.JButton btnreset;
    private javax.swing.JButton btntaomoi;
    private javax.swing.JButton btnthem;
    private javax.swing.JButton btntimkiem;
    private javax.swing.JButton btnxoa;
    private javax.swing.JComboBox<String> cbHocKy;
    private javax.swing.JComboBox<String> cbMaKH;
    private javax.swing.JComboBox<String> cbMaSV;
    private javax.swing.JComboBox<String> cbXepLoai;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane1;
    private CustomSwing.PanelBorder panelBorder1;
    private javax.swing.JTable tblXepHang;
    private javax.swing.JTextField txtHoTen;
    private javax.swing.JTextField txtNamHoc;
    private javax.swing.JTextField txtThongBao;
    // End of variables declaration//GEN-END:variables
}
