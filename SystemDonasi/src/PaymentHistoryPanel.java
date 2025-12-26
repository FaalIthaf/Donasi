import java.awt.*;
import java.time.LocalDate;
import java.util.*;
import java.util.List;
import javax.swing.*;
import javax.swing.table.*;

public class PaymentHistoryPanel extends JPanel {
    private DashBoard app;
    private JTable paymentTable;
    private DefaultTableModel tableModel;
    private List<Pembayaran> pembayaranList;
    
    public PaymentHistoryPanel(DashBoard app) {
        this.app = app;
        this.pembayaranList = new ArrayList<>();
        
        setLayout(new BorderLayout());
        setBackground(UIConstants.PANEL_BG);
        
        add(createHeader(), BorderLayout.NORTH);
        add(createContent(), BorderLayout.CENTER);
    }
    
    private JPanel createHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(UIConstants.SUCCESS);
        header.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));
        
        JLabel title = new JLabel("Riwayat Pembayaran");
        title.setFont(UIConstants.TITLE_FONT);
        title.setForeground(Color.WHITE);
        
        JLabel subtitle = new JLabel("Kelola dan pantau semua pembayaran Anda");
        subtitle.setFont(UIConstants.SUBTITLE_FONT);
        subtitle.setForeground(new Color(189, 195, 199));
        
        JPanel titlePanel = new JPanel(new GridLayout(2, 1, 0, 5));
        titlePanel.setOpaque(false);
        titlePanel.add(title);
        titlePanel.add(subtitle);
        
        RoundedButton kembaliBtn = new RoundedButton("Kembali");
        kembaliBtn.setFont(new Font("Times New Roman", Font.PLAIN, 12));
        kembaliBtn.setBackground(new Color(39, 174, 96));
        kembaliBtn.setForeground(Color.WHITE);
        kembaliBtn.setPreferredSize(new Dimension(100, 40));
        kembaliBtn.addActionListener(e -> app.showPanel("Donatur"));
        
        header.add(titlePanel, BorderLayout.WEST);
        header.add(kembaliBtn, BorderLayout.EAST);
        
        return header;
    }
    
    private JPanel createContent() {
        JPanel content = new JPanel(new BorderLayout());
        content.setBackground(UIConstants.PANEL_BG);
        content.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Button Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        buttonPanel.setOpaque(false);
        
        RoundedButton tambahBtn = new RoundedButton("+ Tambah Pembayaran");
        tambahBtn.setFont(new Font("Times New Roman", Font.BOLD, 12));
        tambahBtn.setBackground(new Color(46, 204, 113));
        tambahBtn.setForeground(Color.WHITE);
        tambahBtn.setPreferredSize(new Dimension(150, 40));
        tambahBtn.addActionListener(e -> showAddPaymentDialog());
        
        buttonPanel.add(tambahBtn);
        content.add(buttonPanel, BorderLayout.NORTH);
        
        // Table
        String[] columnNames = {"ID", "Nominal", "Metode", "Tanggal", "Status", "Catatan"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        paymentTable = new JTable(tableModel);
        paymentTable.setFont(new Font("Times New Roman", Font.PLAIN, 12));
        paymentTable.getTableHeader().setFont(new Font("Times New Roman", Font.BOLD, 13));
        paymentTable.setRowHeight(25);
        paymentTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        // Set column widths
        TableColumnModel columnModel = paymentTable.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(80);  // ID
        columnModel.getColumn(1).setPreferredWidth(120); // Nominal
        columnModel.getColumn(2).setPreferredWidth(110); // Metode
        columnModel.getColumn(3).setPreferredWidth(100); // Tanggal
        columnModel.getColumn(4).setPreferredWidth(100); // Status
        columnModel.getColumn(5).setPreferredWidth(200); // Catatan
        
        // Apply renderer
        for (int i = 0; i < paymentTable.getColumnCount(); i++) {
            paymentTable.getColumnModel().getColumn(i).setCellRenderer(new ZebraTableCellRenderer());
        }
        
        JScrollPane scrollPane = new JScrollPane(paymentTable);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(189, 195, 199)));
        content.add(scrollPane, BorderLayout.CENTER);
        
        // Action Panel
        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        actionPanel.setOpaque(false);
        
        RoundedButton hapusBtn = new RoundedButton("Hapus");
        hapusBtn.setFont(new Font("Times New Roman", Font.BOLD, 12));
        hapusBtn.setBackground(new Color(231, 76, 60));
        hapusBtn.setForeground(Color.WHITE);
        hapusBtn.setPreferredSize(new Dimension(100, 40));
        hapusBtn.addActionListener(e -> deleteSelectedPayment());
        
        RoundedButton detailBtn = new RoundedButton("Detail");
        detailBtn.setFont(new Font("Times New Roman", Font.BOLD, 12));
        detailBtn.setBackground(new Color(52, 152, 219));
        detailBtn.setForeground(Color.WHITE);
        detailBtn.setPreferredSize(new Dimension(100, 40));
        detailBtn.addActionListener(e -> showPaymentDetail());
        
        actionPanel.add(detailBtn);
        actionPanel.add(hapusBtn);
        content.add(actionPanel, BorderLayout.SOUTH);
        
        return content;
    }
    
    private void showAddPaymentDialog() {
        JDialog dialog = new JDialog((JFrame) SwingUtilities.getWindowAncestor(this), 
            "Tambah Pembayaran", true);
        dialog.setSize(500, 400);
        dialog.setLocationRelativeTo(null);
        dialog.setResizable(false);
        
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(UIConstants.PANEL_BG);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JLabel titleLabel = new JLabel("Form Pembayaran Baru");
        titleLabel.setFont(new Font("Times New Roman", Font.BOLD, 18));
        titleLabel.setForeground(new Color(44, 62, 80));
        mainPanel.add(titleLabel, BorderLayout.NORTH);
        
        JPanel contentPanel = new JPanel(new GridBagLayout());
        contentPanel.setBackground(UIConstants.PANEL_BG);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Nominal
        gbc.gridx = 0; gbc.gridy = 0;
        contentPanel.add(new JLabel("Nominal Pembayaran:"), gbc);
        gbc.gridx = 1;
        JTextField nominalField = new JTextField(20);
        contentPanel.add(nominalField, gbc);
        
        // Metode Pembayaran
        gbc.gridx = 0; gbc.gridy = 1;
        contentPanel.add(new JLabel("Metode Pembayaran:"), gbc);
        gbc.gridx = 1;
        String[] metodes = {"Transfer Bank", "E-Wallet", "Kartu Kredit", "Cash"};
        JComboBox<String> metodeBox = new JComboBox<>(metodes);
        contentPanel.add(metodeBox, gbc);
        
        // Tanggal
        gbc.gridx = 0; gbc.gridy = 2;
        contentPanel.add(new JLabel("Tanggal Pembayaran:"), gbc);
        gbc.gridx = 1;
        JTextField dateField = new JTextField(20);
        dateField.setText(LocalDate.now().toString());
        dateField.setEditable(false);
        contentPanel.add(dateField, gbc);
        
        // Status
        gbc.gridx = 0; gbc.gridy = 3;
        contentPanel.add(new JLabel("Status:"), gbc);
        gbc.gridx = 1;
        String[] statuses = {"Menunggu Verifikasi", "Terverifikasi", "Bermasalah"};
        JComboBox<String> statusBox = new JComboBox<>(statuses);
        contentPanel.add(statusBox, gbc);
        
        // Catatan
        gbc.gridx = 0; gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.NORTH;
        contentPanel.add(new JLabel("Catatan:"), gbc);
        gbc.gridx = 1;
        JTextArea notesArea = new JTextArea(3, 20);
        notesArea.setLineWrap(true);
        notesArea.setWrapStyleWord(true);
        JScrollPane notesScroll = new JScrollPane(notesArea);
        contentPanel.add(notesScroll, gbc);
        
        mainPanel.add(contentPanel, BorderLayout.CENTER);
        
        // Button Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        buttonPanel.setOpaque(false);
        
        RoundedButton saveBtn = new RoundedButton("Simpan");
        saveBtn.setBackground(new Color(46, 204, 113));
        saveBtn.setForeground(Color.WHITE);
        saveBtn.setFont(new Font("Times New Roman", Font.BOLD, 12));
        saveBtn.addActionListener(e -> {
            String nominal = nominalField.getText().trim();
            String metode = (String) metodeBox.getSelectedItem();
            String tanggal = dateField.getText();
            String status = (String) statusBox.getSelectedItem();
            String catatan = notesArea.getText().trim();
            
            if (nominal.isEmpty()) {
                JOptionPane.showMessageDialog(dialog, 
                    "Mohon masukkan nominal pembayaran!", 
                    "Peringatan", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            try {
                double amount = Double.parseDouble(nominal);
                if (amount <= 0) {
                    throw new NumberFormatException();
                }
                
                Pembayaran pembayaran = new Pembayaran(
                    generatePaymentId(),
                    amount,
                    metode,
                    LocalDate.parse(tanggal),
                    status,
                    catatan.isEmpty() ? "-" : catatan
                );
                
                pembayaranList.add(pembayaran);
                refreshTable();
                
                JOptionPane.showMessageDialog(dialog,
                    "Pembayaran berhasil ditambahkan!",
                    "Sukses", JOptionPane.INFORMATION_MESSAGE);
                dialog.dispose();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(dialog,
                    "Nominal harus berupa angka yang valid dan lebih dari 0!",
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        RoundedButton cancelBtn = new RoundedButton("Batalkan");
        cancelBtn.setBackground(new Color(231, 76, 60));
        cancelBtn.setForeground(Color.WHITE);
        cancelBtn.setFont(new Font("Times New Roman", Font.BOLD, 12));
        cancelBtn.addActionListener(e -> dialog.dispose());
        
        buttonPanel.add(saveBtn);
        buttonPanel.add(cancelBtn);
        
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        dialog.add(mainPanel);
        dialog.setVisible(true);
    }
    
    private void deleteSelectedPayment() {
        int selectedRow = paymentTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this,
                "Silakan pilih pembayaran yang akan dihapus!",
                "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int option = JOptionPane.showConfirmDialog(this,
            "Apakah Anda yakin ingin menghapus pembayaran ini?",
            "Konfirmasi Hapus", JOptionPane.YES_NO_OPTION);
        
        if (option == JOptionPane.YES_OPTION) {
            pembayaranList.remove(selectedRow);
            refreshTable();
            JOptionPane.showMessageDialog(this,
                "Pembayaran berhasil dihapus!",
                "Sukses", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    private void showPaymentDetail() {
        int selectedRow = paymentTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this,
                "Silakan pilih pembayaran untuk melihat detail!",
                "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        Pembayaran pembayaran = pembayaranList.get(selectedRow);
        
        JDialog detailDialog = new JDialog((JFrame) SwingUtilities.getWindowAncestor(this),
            "Detail Pembayaran", true);
        detailDialog.setSize(400, 300);
        detailDialog.setLocationRelativeTo(null);
        detailDialog.setResizable(false);
        
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(UIConstants.PANEL_BG);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JLabel titleLabel = new JLabel("Detail Pembayaran");
        titleLabel.setFont(new Font("Times New Roman", Font.BOLD, 16));
        mainPanel.add(titleLabel, BorderLayout.NORTH);
        
        JPanel detailPanel = new JPanel(new GridLayout(6, 2, 10, 10));
        detailPanel.setBackground(UIConstants.PANEL_BG);
        
        detailPanel.add(new JLabel("ID Pembayaran:"));
        detailPanel.add(new JLabel(pembayaran.getId()));
        
        detailPanel.add(new JLabel("Nominal:"));
        detailPanel.add(new JLabel("Rp " + String.format("%.0f", pembayaran.getNominal())));
        
        detailPanel.add(new JLabel("Metode:"));
        detailPanel.add(new JLabel(pembayaran.getMetode()));
        
        detailPanel.add(new JLabel("Tanggal:"));
        detailPanel.add(new JLabel(pembayaran.getTanggal().toString()));
        
        detailPanel.add(new JLabel("Status:"));
        JLabel statusLabel = new JLabel(pembayaran.getStatus());
        if (pembayaran.getStatus().equals("Terverifikasi")) {
            statusLabel.setForeground(new Color(46, 204, 113));
            statusLabel.setFont(new Font("Times New Roman", Font.BOLD, 12));
        } else if (pembayaran.getStatus().equals("Bermasalah")) {
            statusLabel.setForeground(new Color(231, 76, 60));
            statusLabel.setFont(new Font("Times New Roman", Font.BOLD, 12));
        }
        detailPanel.add(statusLabel);
        
        detailPanel.add(new JLabel("Catatan:"));
        detailPanel.add(new JLabel(pembayaran.getCatatan()));
        
        mainPanel.add(detailPanel, BorderLayout.CENTER);
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setOpaque(false);
        
        RoundedButton closeBtn = new RoundedButton("Tutup");
        closeBtn.setBackground(new Color(52, 152, 219));
        closeBtn.setForeground(Color.WHITE);
        closeBtn.addActionListener(e -> detailDialog.dispose());
        buttonPanel.add(closeBtn);
        
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        detailDialog.add(mainPanel);
        detailDialog.setVisible(true);
    }
    
    private void refreshTable() {
        tableModel.setRowCount(0);
        for (Pembayaran pembayaran : pembayaranList) {
            Object[] row = {
                pembayaran.getId(),
                "Rp " + String.format("%.0f", pembayaran.getNominal()),
                pembayaran.getMetode(),
                pembayaran.getTanggal().toString(),
                pembayaran.getStatus(),
                pembayaran.getCatatan()
            };
            tableModel.addRow(row);
        }
    }
    
    private String generatePaymentId() {
        return "PAY" + System.currentTimeMillis();
    }
    
    public void refreshPaymentList() {
        refreshTable();
    }
}

// ========== PAYMENT MODEL CLASS ==========
class Pembayaran {
    private String id;
    private double nominal;
    private String metode;
    private LocalDate tanggal;
    private String status;
    private String catatan;
    
    public Pembayaran(String id, double nominal, String metode, LocalDate tanggal, 
                     String status, String catatan) {
        this.id = id;
        this.nominal = nominal;
        this.metode = metode;
        this.tanggal = tanggal;
        this.status = status;
        this.catatan = catatan;
    }
    
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    
    public double getNominal() { return nominal; }
    public void setNominal(double nominal) { this.nominal = nominal; }
    
    public String getMetode() { return metode; }
    public void setMetode(String metode) { this.metode = metode; }
    
    public LocalDate getTanggal() { return tanggal; }
    public void setTanggal(LocalDate tanggal) { this.tanggal = tanggal; }
    
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    
    public String getCatatan() { return catatan; }
    public void setCatatan(String catatan) { this.catatan = catatan; }
}