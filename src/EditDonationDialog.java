import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;

public class EditDonationDialog extends JDialog {
    private JTextField namaField, emailField, jumlahField;
    private JComboBox<String> kategoriCombo, bankCombo, statusCombo;
    private JTextArea pesanArea;
    private JButton saveBtn, cancelBtn, markPaidBtn;
    private boolean confirmed = false;
    private Donasi donasi;
    private DashBoard app;
    
    public EditDonationDialog(JFrame parent, Donasi donasi, DashBoard app) {
        super(parent, "Edit Donasi", true);
        this.donasi = donasi;
        this.app = app;
        
        setSize(500, 550);
        setLocationRelativeTo(parent);
        setResizable(false);
        
        initComponents();
        loadData();
    }
    
    private void initComponents() {
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(UIConstants.PANEL_BG);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);
        
        // Nama
        gbc.gridx = 0; gbc.gridy = 0;
        mainPanel.add(createLabel("Nama Lengkap:"), gbc);
        gbc.gridx = 1; gbc.weightx = 1.0;
        namaField = createTextField();
        mainPanel.add(namaField, gbc);
        
        // Email
        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0;
        mainPanel.add(createLabel("Email:"), gbc);
        gbc.gridx = 1; gbc.weightx = 1.0;
        emailField = createTextField();
        mainPanel.add(emailField, gbc);
        
        // Kategori
        gbc.gridx = 0; gbc.gridy = 2; gbc.weightx = 0;
        mainPanel.add(createLabel("Kategori:"), gbc);
        gbc.gridx = 1; gbc.weightx = 1.0;
        String[] categories = {"Pendidikan", "Kesehatan", "Bencana Alam", 
                              "Kemanusiaan", "Lingkungan", "Lainnya"};
        kategoriCombo = new JComboBox<>(categories);
        kategoriCombo.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        kategoriCombo.setBackground(Color.WHITE);
        mainPanel.add(kategoriCombo, gbc);
        
        // Jumlah
        gbc.gridx = 0; gbc.gridy = 3; gbc.weightx = 0;
        mainPanel.add(createLabel("Jumlah (Rp):"), gbc);
        gbc.gridx = 1; gbc.weightx = 1.0;
        jumlahField = createTextField();
        mainPanel.add(jumlahField, gbc);
        
        // Bank
        gbc.gridx = 0; gbc.gridy = 4; gbc.weightx = 0;
        mainPanel.add(createLabel("Bank/Metode:"), gbc);
        gbc.gridx = 1; gbc.weightx = 1.0;
        String[] banks = {"Tunai", "BCA", "Mandiri", "BRI", "BTN", "CIMB Niaga", "Danamon", "OVO", "GoPay", "Dana"};
        bankCombo = new JComboBox<>(banks);
        bankCombo.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        bankCombo.setBackground(Color.WHITE);
        mainPanel.add(bankCombo, gbc);
        
        // Status
        gbc.gridx = 0; gbc.gridy = 5; gbc.weightx = 0;
        mainPanel.add(createLabel("Status Pembayaran:"), gbc);
        gbc.gridx = 1; gbc.weightx = 1.0;
        String[] statuses = {"Belum Dibayar", "Sudah Dibayar"};
        statusCombo = new JComboBox<>(statuses);
        statusCombo.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        statusCombo.setBackground(Color.WHITE);
        statusCombo.addActionListener(e -> updateFieldStates());
        mainPanel.add(statusCombo, gbc);
        
        // Pesan
        gbc.gridx = 0; gbc.gridy = 6; gbc.weightx = 0;
        mainPanel.add(createLabel("Pesan:"), gbc);
        gbc.gridx = 1; gbc.weightx = 1.0; gbc.weighty = 1.0;
        pesanArea = new JTextArea(3, 25);
        pesanArea.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        pesanArea.setLineWrap(true);
        pesanArea.setWrapStyleWord(true);
        pesanArea.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(189, 195, 199)),
            BorderFactory.createEmptyBorder(8, 8, 8, 8)
        ));
        JScrollPane scrollPane = new JScrollPane(pesanArea);
        mainPanel.add(scrollPane, gbc);
        
        // Buttons
        gbc.gridx = 0; gbc.gridy = 7; gbc.gridwidth = 2; gbc.weighty = 0;
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        buttonPanel.setOpaque(false);
        
        saveBtn = new RoundedButton(" Simpan");
        saveBtn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        saveBtn.setBackground(new Color(46, 204, 113));
        saveBtn.setForeground(Color.WHITE);
        saveBtn.setPreferredSize(new Dimension(100, 35));
        saveBtn.addActionListener(e -> saveChanges());
        
        markPaidBtn = new RoundedButton(" Tandai Dibayar");
        markPaidBtn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        markPaidBtn.setBackground(new Color(52, 152, 219));
        markPaidBtn.setForeground(Color.WHITE);
        markPaidBtn.setPreferredSize(new Dimension(140, 35));
        markPaidBtn.addActionListener(e -> markAsPaid());
        
        cancelBtn = new RoundedButton(" Batal");
        cancelBtn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        cancelBtn.setBackground(new Color(231, 76, 60));
        cancelBtn.setForeground(Color.WHITE);
        cancelBtn.setPreferredSize(new Dimension(100, 35));
        cancelBtn.addActionListener(e -> dispose());
        
        buttonPanel.add(saveBtn);
        buttonPanel.add(markPaidBtn);
        buttonPanel.add(cancelBtn);
        
        mainPanel.add(buttonPanel, gbc);
        
        add(mainPanel);
    }
    
    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.BOLD, 13));
        label.setForeground(new Color(44, 62, 80));
        return label;
    }
    
    private JTextField createTextField() {
        JTextField field = new JTextField();
        field.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(189, 195, 199)),
            BorderFactory.createEmptyBorder(6, 8, 6, 8)
        ));
        field.setBackground(Color.WHITE);
        return field;
    }
    
    private void loadData() {
        namaField.setText(donasi.getNamaDonatur());
        emailField.setText(donasi.getEmail());
        kategoriCombo.setSelectedItem(donasi.getKategori());
        jumlahField.setText(String.format("%.0f", donasi.getJumlah()));
        bankCombo.setSelectedItem(donasi.getBank());
        statusCombo.setSelectedItem(donasi.getStatus());
        pesanArea.setText(donasi.getPesan());
        
        
        updateFieldStates();
    }
    
    private void updateFieldStates() {
        boolean isUnpaid = "Belum Dibayar".equals(statusCombo.getSelectedItem());
        // Enable jumlah editing only jika status "Belum Dibayar"
        jumlahField.setEditable(isUnpaid);
        jumlahField.setBackground(isUnpaid ? Color.WHITE : new Color(240, 240, 240));
    }
    
    private void markAsPaid() {
        statusCombo.setSelectedItem("Sudah Dibayar");
        JOptionPane.showMessageDialog(this, "Status donasi telah diubah menjadi 'Sudah Dibayar'", "Informasi", JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void saveChanges() {
        try {
            String nama = namaField.getText().trim();
            String email = emailField.getText().trim();
            String kategori = (String) kategoriCombo.getSelectedItem();
            String jumlahStr = jumlahField.getText().trim();
            String bank = (String) bankCombo.getSelectedItem();
            String pesan = pesanArea.getText().trim();
            
            if (nama.isEmpty() || email.isEmpty() || jumlahStr.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Nama, Email, dan Jumlah harus diisi!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
                JOptionPane.showMessageDialog(this, "Format email tidak valid!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            double jumlah;
            try {
                jumlah = Double.parseDouble(jumlahStr);
                if (jumlah <= 0) throw new NumberFormatException();
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Jumlah harus angka positif!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Update data
            donasi.setNamaDonatur(nama);
            donasi.setEmail(email);
            donasi.setKategori(kategori);
            donasi.setJumlah(jumlah);
            donasi.setBank(bank);
            donasi.setPesan(pesan);
            donasi.setStatus((String) statusCombo.getSelectedItem());
            
            // Find and update in list
            for (int i = 0; i < app.getDonasiList().size(); i++) {
                if (app.getDonasiList().get(i).getId().equals(donasi.getId())) {
                    app.updateDonasi(i, donasi);
                    break;
                }
            }
            
            confirmed = true;
            dispose();
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public boolean isConfirmed() {
        return confirmed;
    }
}