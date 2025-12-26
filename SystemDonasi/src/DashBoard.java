
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;
import javax.swing.*;
import javax.swing.table.*;


class RoundedButton extends JButton {
    private int radius = 20;
    
    public RoundedButton(String text) {
        super(text);
        setOpaque(false);
        setFocusPainted(false);
        setBorderPainted(false);
        setContentAreaFilled(false);
        setCursor(new Cursor(Cursor.HAND_CURSOR));
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, radius, radius);
        
        super.paintComponent(g);
        g2.dispose();
    }
    
    @Override
    protected void paintBorder(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(getForeground());
        g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, radius, radius);
        g2.dispose();
    }
}

class RoundedPanel extends JPanel {
    private int radius = 15;
    private Color shadowColor = new Color(0, 0, 0, 30);
    
    public RoundedPanel(int radius) {
        this.radius = radius;
        setOpaque(false);
    }
    
    public RoundedPanel() {
        setOpaque(false);
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        g2.setColor(shadowColor);
        g2.fillRoundRect(2, 2, getWidth() - 4, getHeight() - 4, radius, radius);
        
        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, radius, radius);
        
        g2.dispose();
        super.paintComponent(g);
    }
}

class UIConstants {
    public static final Color PRIMARY = new Color(52, 73, 94);
    public static final Color SUCCESS = new Color(31, 58, 95);
    public static final Color ACCENT = new Color(52, 152, 219);
    public static final Color DANGER = new Color(231, 76, 60);
    public static final Color PANEL_BG = new Color(255,250,250);

    public static final Font TITLE_FONT = new Font("Times New Roman", Font.BOLD, 32);
    public static final Font SUBTITLE_FONT = new Font("Times New Roman", Font.PLAIN, 14);
    public static final Font HEADER_FONT = new Font("Times New Roman", Font.BOLD, 18);

    public static void applyGlobal() {
        UIManager.put("Label.font", new Font("Times New Roman", Font.PLAIN, 14));
        UIManager.put("Button.font", new Font("Times New Roman", Font.PLAIN, 14));
        UIManager.put("Table.font", new Font("Times New Roman", Font.PLAIN, 12));
        UIManager.put("TableHeader.font", new Font("Times New Roman", Font.BOLD, 13));
        UIManager.put("TabbedPane.font", new Font("Times New Roman", Font.PLAIN, 13));
    }
}

class ZebraTableCellRenderer extends DefaultTableCellRenderer {
    private Color even = Color.WHITE;
    private Color odd = new Color(250, 250, 250);

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {
        Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        if (isSelected) {
            c.setBackground(table.getSelectionBackground());
            c.setForeground(table.getSelectionForeground());
        } else {
            c.setBackground((row % 2 == 0) ? even : odd);
            c.setForeground(Color.DARK_GRAY);
        }
        return c;
    }
}

class Donasi {
    private String id;
    private String namaDonatur;
    private String email;
    private String kategori;
    private double jumlah;
    private LocalDate tanggal;
    private String pesan;
    private String bank;
    private String status; 
    
    public Donasi(String id, String namaDonatur, String email, String kategori, 
                  double jumlah, LocalDate tanggal, String pesan, String bank) {
        this.id = id;
        this.namaDonatur = namaDonatur;
        this.email = email;
        this.kategori = kategori;
        this.jumlah = jumlah;
        this.tanggal = tanggal;
        this.pesan = pesan;
        this.bank = bank;
        this.status = "Belum Dibayar"; 
    }
    
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getNamaDonatur() { return namaDonatur; }
    public void setNamaDonatur(String namaDonatur) { this.namaDonatur = namaDonatur; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getKategori() { return kategori; }
    public void setKategori(String kategori) { this.kategori = kategori; }
    public double getJumlah() { return jumlah; }
    public void setJumlah(double jumlah) { this.jumlah = jumlah; }
    public LocalDate getTanggal() { return tanggal; }
    public void setTanggal(LocalDate tanggal) { this.tanggal = tanggal; }
    public String getPesan() { return pesan; }
    public void setPesan(String pesan) { this.pesan = pesan; }
    public String getBank() { return bank; }
    public void setBank(String bank) { this.bank = bank; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    
    public String toCSV() {
        return id + "," + namaDonatur + "," + email + "," + kategori + "," + 
               jumlah + "," + tanggal.toString() + "," + pesan.replace(",", ";") + "," + (bank != null ? bank : "Tunai") + "," + (status != null ? status : "Belum Dibayar");
    }
    
    public static Donasi fromCSV(String csv) {
        String[] parts = csv.split(",", 9);
        String bank = (parts.length > 7) ? parts[7] : "Tunai";
        Donasi donasi = new Donasi(parts[0], parts[1], parts[2], parts[3],
                         Double.parseDouble(parts[4]), 
                         LocalDate.parse(parts[5]),
                         parts.length > 6 ? parts[6].replace(";", ",") : "",
                         bank);
        if (parts.length > 8) {
            donasi.setStatus(parts[8]);
        }
        return donasi;
    }
}

class FileHandler {
    private static final String FILE_NAME = "donasi_data.csv";
    
    public static void saveData(List<Donasi> donasiList) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_NAME))) {
            for (Donasi d : donasiList) {
                writer.println(d.toCSV());
            }
        }
    }
    
    public static List<Donasi> loadData() {
        List<Donasi> list = new ArrayList<>();
        File file = new File(FILE_NAME);
        
        if (!file.exists()) {
            return list;
        }
        
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    list.add(Donasi.fromCSV(line));
                }
            }
        } catch (IOException | ArrayIndexOutOfBoundsException e) {
            JOptionPane.showMessageDialog(null, 
                "Error membaca file: " + e.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
        
        return list;
    }
}

public class DashBoard extends JFrame {
    public CardLayout cardLayout;
    public JPanel mainPanel;
    private List<Donasi> donasiList;
    public String currentUserId = "donor";
    public String currentUserRole = "donor";
    
    public AdminDashboardPanel adminDashboardPanel;
    public DonorDashboardPanel donorDashboardPanel;
    private ListDataPanel listDataPanel;
    private InputPanel inputPanel;
    private LaporanPanel laporanPanel;
    private PaymentHistoryPanel paymentHistoryPanel;
    
    public DashBoard() {
        donasiList = FileHandler.loadData();


        if (donasiList.size() < 20) {
            String[] sampleData = {
                "DN1766705000001,Farrel, Farrel@gmail.com,Pendidikan,500000.0,2024-10-15,Bantuan pendidikan anak yatim,Transfer Bank,Sudah Dibayar",
                "DN1766705100002,Dikha, Dikha@gmail.com,Bencana Alam,1000000.0,2024-09-20,Bantuan korban banjir,OVO,Sudah Dibayar",
                "DN1766705200003,Faal, Faal@yahoo.com,Kemanusiaan,750000.0,2024-08-10,Dukungan panti asuhan,GoPay,Sudah Dibayar"
            };
           
            try {
                FileHandler.saveData(donasiList);
            } catch (IOException e) {
        
            }
        }

        setTitle("Aplikasi Donasi Digital");
        setSize(1200, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        
        adminDashboardPanel = new AdminDashboardPanel(this);
        donorDashboardPanel = new DonorDashboardPanel(this);
        listDataPanel = new ListDataPanel(this);
        inputPanel = new InputPanel(this);
        laporanPanel = new LaporanPanel(this);
        paymentHistoryPanel = new PaymentHistoryPanel(this);


        mainPanel.add(adminDashboardPanel, "AdminDashboard");
        mainPanel.add(donorDashboardPanel, "DonorDashboard");
        mainPanel.add(listDataPanel, "ListData");
        mainPanel.add(inputPanel, "Input");
        mainPanel.add(laporanPanel, "Laporan");
        mainPanel.add(paymentHistoryPanel, "PaymentHistory");

        add(mainPanel);
        cardLayout.show(mainPanel, "DonorDashboard");
        
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                saveDataToFile();
            }
        });
    }
    
    public void login(String userId, String role) {
        this.currentUserId = userId;
        this.currentUserRole = role;
        if ("admin".equals(role)) {
            adminDashboardPanel.refreshStats();
            cardLayout.show(mainPanel, "AdminDashboard");
        } else {
            donorDashboardPanel.refreshStats();
            cardLayout.show(mainPanel, "DonorDashboard");
        }
    }
    
    public void logout() {
        this.currentUserId = "";
        this.currentUserRole = "";
        new LoginPage().setVisible(true);
        dispose();
    }
    
    public void showPanel(String panelName) {
        cardLayout.show(mainPanel, panelName);
        
        if ("ListData".equals(panelName)) {
            listDataPanel.refreshTable();
        } else if ("Laporan".equals(panelName)) {
            laporanPanel.refreshData();
        } else if ("PaymentHistory".equals(panelName)) {
            paymentHistoryPanel.refreshPaymentList();
        }
    }
    
    public List<Donasi> getDonasiList() {
        return donasiList;
    }

    public void refreshListData() {
        if (listDataPanel != null) listDataPanel.refreshTable();
    }

    public void refreshLaporan() {
        if (laporanPanel != null) laporanPanel.refreshData();
    }
    
    public void addDonasi(Donasi donasi) {
        donasiList.add(donasi);
        saveDataToFile();
    }
    
    public void updateDonasi(int index, Donasi donasi) {
        if (index >= 0 && index < donasiList.size()) {
            donasiList.set(index, donasi);
            saveDataToFile();
        }
    }
    
    public void deleteDonasi(int index) {
        if (index >= 0 && index < donasiList.size()) {
            donasiList.remove(index);
            saveDataToFile();
        }
    }
    
    private void saveDataToFile() {
        try {
            FileHandler.saveData(donasiList);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, 
                "Error menyimpan data: " + e.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public String generateId() {
        return "DN" + System.currentTimeMillis();
    }
    
}
class ListDataPanel extends JPanel {
    private DashBoard app;
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField searchField;
    
    public ListDataPanel(DashBoard app) {
        this.app = app;
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        
        add(createHeader(), BorderLayout.NORTH);
        add(createContent(), BorderLayout.CENTER);
    }
    
    private JPanel createHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(new Color(52, 152, 219));
        header.setBorder(BorderFactory.createEmptyBorder(25, 40, 25, 40));
    
        String titleText = "donor".equals(app.currentUserRole) ? "Riwayat Donasi Saya" : "Data Donasi";
        JLabel title = new JLabel(titleText);
        title.setFont(UIConstants.TITLE_FONT);
        title.setForeground(Color.WHITE);
        
        RoundedButton backBtn = new RoundedButton("← Kembali");
        backBtn.setFont(new Font("Times New Roman", Font.PLAIN, 13));
        backBtn.setBackground(new Color(32, 145, 79));
        backBtn.setForeground(Color.WHITE);
        backBtn.setPreferredSize(new Dimension(120, 40));
        backBtn.addActionListener(e -> {
            if ("admin".equals(app.currentUserRole)) {
                app.adminDashboardPanel.refreshStats();
                app.cardLayout.show(app.mainPanel, "AdminDashboard");
            } else {
                app.donorDashboardPanel.refreshStats();
                app.cardLayout.show(app.mainPanel, "DonorDashboard");
            }
        });
        
        header.add(title, BorderLayout.WEST);
        header.add(backBtn, BorderLayout.EAST);
        
        return header;
    }
    
    private JPanel createContent() {
        JPanel content = new JPanel(new BorderLayout(0, 10));
        content.setBackground(Color.WHITE);
        content.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JPanel topPanel = new JPanel(new BorderLayout(10, 0));
        topPanel.setOpaque(false);
        
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchPanel.setOpaque(false);
        
        JLabel searchLabel = new JLabel("🔍 Cari:");
        searchLabel.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        
        searchField = new JTextField(20);
        searchField.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        searchField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                filterTable();
            }
        });
        
        searchPanel.add(searchLabel);
        searchPanel.add(searchField);
        
        topPanel.add(searchPanel, BorderLayout.WEST);
        
        String[] columns = {"ID", "Nama Donatur", "Email", "Kategori", "Jumlah", "Bank", "Status", "Tanggal", "Pesan"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        table = new JTable(tableModel);

        table.setDefaultRenderer(Object.class, new ZebraTableCellRenderer());
        table.getTableHeader().setReorderingAllowed(false);
        table.setFont(new Font("Times New Roman", Font.PLAIN, 12));
        table.setRowHeight(30);
        table.getTableHeader().setFont(new Font("Times New Roman", Font.BOLD, 12));
        table.getTableHeader().setBackground(new Color(52, 73, 94));
        table.getTableHeader().setForeground(Color.WHITE);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(189, 195, 199)));
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setOpaque(false);
        
        RoundedButton editBtn = new RoundedButton("Edit");
        editBtn.setFont(new Font("Times New Roman", Font.PLAIN, 12));
        editBtn.setBackground(new Color(52, 152, 219));
        editBtn.setForeground(Color.WHITE);
        editBtn.setPreferredSize(new Dimension(100, 35));
        editBtn.addActionListener(e -> editSelectedDonasi());
        
        RoundedButton paymentBtn = new RoundedButton("Bayar");
        paymentBtn.setFont(new Font("Times New Roman", Font.PLAIN, 12));
        paymentBtn.setBackground(new Color(241, 196, 15));
        paymentBtn.setForeground(new Color(44, 62, 80));
        paymentBtn.setPreferredSize(new Dimension(100, 35));
        paymentBtn.addActionListener(e -> processPayment());
        
        RoundedButton deleteBtn = new RoundedButton("Hapus");
        deleteBtn.setFont(new Font("Times New Roman", Font.PLAIN, 12));
        deleteBtn.setBackground(new Color(231, 76, 60));
        deleteBtn.setForeground(Color.WHITE);
        deleteBtn.setPreferredSize(new Dimension(100, 35));
        deleteBtn.addActionListener(e -> deleteSelectedDonasi());
        
        buttonPanel.add(editBtn);
        buttonPanel.add(paymentBtn);
        buttonPanel.add(deleteBtn);
        
        content.add(topPanel, BorderLayout.NORTH);
        content.add(scrollPane, BorderLayout.CENTER);
        content.add(buttonPanel, BorderLayout.SOUTH);
        
        return content;
    }
    
    public void refreshTable() {
        tableModel.setRowCount(0);
        List<Donasi> list = app.getDonasiList();
        
        for (Donasi d : list) {
            if ("donor".equals(app.currentUserRole)) {
                if (!d.getNamaDonatur().equals(app.currentUserId)) {
                    continue;
                }
            }
            
            tableModel.addRow(new Object[]{
                d.getId(),
                d.getNamaDonatur(),
                d.getEmail(),
                d.getKategori(),
                String.format("Rp %.0f", d.getJumlah()),
                d.getBank(),
                d.getStatus(),
                d.getTanggal().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                d.getPesan()
            });
        }
    }
    
    private void filterTable() {
        String keyword = searchField.getText().toLowerCase();
        tableModel.setRowCount(0);
        
        for (Donasi d : app.getDonasiList()) {

            if ("donor".equals(app.currentUserRole)) {
                if (!d.getNamaDonatur().equals(app.currentUserId)) {
                    continue;
                }
            }
            if (d.getNamaDonatur().toLowerCase().contains(keyword) ||
                d.getEmail().toLowerCase().contains(keyword) ||
                d.getKategori().toLowerCase().contains(keyword) ||
                d.getBank().toLowerCase().contains(keyword) ||
                d.getStatus().toLowerCase().contains(keyword)) {
                
                tableModel.addRow(new Object[]{
                    d.getId(),
                    d.getNamaDonatur(),
                    d.getEmail(),
                    d.getKategori(),
                    String.format("Rp %.0f", d.getJumlah()),
                    d.getBank(),
                    d.getStatus(),
                    d.getTanggal().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                    d.getPesan()
                });
            }
        }
    }
    
    private void editSelectedDonasi() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Pilih data donasi yang ingin diedit!", "Info", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        String id = (String) tableModel.getValueAt(selectedRow, 0);
        Donasi donasi = null;
        for (Donasi d : app.getDonasiList()) {
            if (d.getId().equals(id)) {
                donasi = d;
                break;
            }
        }
        
        if (donasi == null) return;
        
        if ("donor".equals(app.currentUserRole) && !donasi.getNamaDonatur().equals(app.currentUserId)) {
            JOptionPane.showMessageDialog(this, "Anda hanya dapat mengedit donasi Anda sendiri!", "Akses Ditolak", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        EditDonationDialog dialog = new EditDonationDialog((JFrame) SwingUtilities.getWindowAncestor(this), donasi, app);
        dialog.setVisible(true);
        
        if (dialog.isConfirmed()) {
            refreshTable();
            JOptionPane.showMessageDialog(this, "Data donasi berhasil diperbarui!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    private void deleteSelectedDonasi() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Pilih data donasi yang ingin dihapus!", "Info", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        String id = (String) tableModel.getValueAt(selectedRow, 0);
        
        Donasi selectedDonasi = null;
        for (Donasi d : app.getDonasiList()) {
            if (d.getId().equals(id)) {
                selectedDonasi = d;
                break;
            }
        }
        
        if (selectedDonasi != null && "donor".equals(app.currentUserRole) && !selectedDonasi.getNamaDonatur().equals(app.currentUserId)) {
            JOptionPane.showMessageDialog(this, "Anda hanya dapat menghapus donasi Anda sendiri!", "Akses Ditolak", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(this, "Apakah Anda yakin ingin menghapus donasi ini?", "Konfirmasi Hapus", JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            for (int i = 0; i < app.getDonasiList().size(); i++) {
                if (app.getDonasiList().get(i).getId().equals(id)) {
                    app.deleteDonasi(i);
                    refreshTable();
                    JOptionPane.showMessageDialog(this, "Data donasi berhasil dihapus!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
                    break;
                }
            }
        }
    }
    
    private void processPayment() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Pilih donasi yang ingin dibayar!", "Info", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        String id = (String) tableModel.getValueAt(selectedRow, 0);
        String status = (String) tableModel.getValueAt(selectedRow, 6);

        if ("Sudah Dibayar".equals(status)) {
            JOptionPane.showMessageDialog(this, "Donasi ini sudah dibayar!", "Info", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        Donasi donasi = null;
        int donasiIndex = -1;
        for (int i = 0; i < app.getDonasiList().size(); i++) {
            if (app.getDonasiList().get(i).getId().equals(id)) {
                donasi = app.getDonasiList().get(i);
                donasiIndex = i;
                break;
            }
        }
        
        if (donasi == null) return;
        
        final Donasi finalDonasi = donasi;
        final int finalDonasiIndex = donasiIndex;

        JDialog paymentDialog = new JDialog((JFrame) SwingUtilities.getWindowAncestor(this), 
            "Proses Pembayaran", true);
        paymentDialog.setSize(500, 400);
        paymentDialog.setLocationRelativeTo(null);
        paymentDialog.setResizable(false);
        
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(UIConstants.PANEL_BG);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JLabel titleLabel = new JLabel("Proses Pembayaran Donasi");
        titleLabel.setFont(new Font("Times New Roman", Font.BOLD, 18));
        titleLabel.setForeground(new Color(44, 62, 80));
        mainPanel.add(titleLabel, BorderLayout.NORTH);
        
        JPanel contentPanel = new JPanel(new GridBagLayout());
        contentPanel.setBackground(UIConstants.PANEL_BG);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        JLabel infoLabel = new JLabel("Informasi Donasi");
        infoLabel.setFont(new Font("Times New Roman", Font.BOLD, 14));
        contentPanel.add(infoLabel, gbc);
        
        gbc.gridwidth = 1;
        gbc.gridy = 1; gbc.gridx = 0;
        contentPanel.add(new JLabel("Nama Donatur:"), gbc);
        gbc.gridx = 1;
        contentPanel.add(new JLabel(finalDonasi.getNamaDonatur()), gbc);
        
        gbc.gridy = 2; gbc.gridx = 0;
        contentPanel.add(new JLabel("Jumlah:"), gbc);
        gbc.gridx = 1;
        contentPanel.add(new JLabel("Rp " + String.format("%.0f", finalDonasi.getJumlah())), gbc);
        
        gbc.gridy = 3; gbc.gridx = 0;
        contentPanel.add(new JLabel("Bank/Metode:"), gbc);
        gbc.gridx = 1;
        JComboBox<String> metodeBox = new JComboBox<>(new String[]{"Transfer Bank", "E-Wallet", "Kartu Kredit", "Cash"});
        metodeBox.setSelectedItem(finalDonasi.getBank());
        contentPanel.add(metodeBox, gbc);
        
        gbc.gridy = 4; gbc.gridx = 0; gbc.gridwidth = 2;
        JLabel notaLabel = new JLabel("Catatan Pembayaran");
        notaLabel.setFont(new Font("Times New Roman", Font.BOLD, 14));
        contentPanel.add(notaLabel, gbc);
        
        gbc.gridy = 5; gbc.gridx = 0; gbc.gridwidth = 2;
        JTextArea notaArea = new JTextArea(3, 30);
        notaArea.setLineWrap(true);
        notaArea.setWrapStyleWord(true);
        JScrollPane notaScroll = new JScrollPane(notaArea);
        contentPanel.add(notaScroll, gbc);
        
        mainPanel.add(contentPanel, BorderLayout.CENTER);
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        buttonPanel.setOpaque(false);
        
        RoundedButton confirmBtn = new RoundedButton("Konfirmasi Pembayaran");
        confirmBtn.setBackground(new Color(46, 204, 113));
        confirmBtn.setForeground(Color.WHITE);
        confirmBtn.setFont(new Font("Times New Roman", Font.BOLD, 12));
        confirmBtn.addActionListener(e -> {
            finalDonasi.setStatus("Sudah Dibayar");
            finalDonasi.setBank((String) metodeBox.getSelectedItem());
            app.updateDonasi(finalDonasiIndex, finalDonasi);
            refreshTable();
            
            JOptionPane.showMessageDialog(paymentDialog,
                "Pembayaran berhasil dikonfirmasi!\n" +
                "Nominal: Rp " + String.format("%.0f", finalDonasi.getJumlah()) + "\n" +
                "Metode: " + metodeBox.getSelectedItem() + "\n" +
                "Status: Sudah Dibayar",
                "Sukses", JOptionPane.INFORMATION_MESSAGE);
            paymentDialog.dispose();
        });
        
        RoundedButton cancelBtn = new RoundedButton("Batalkan");
        cancelBtn.setBackground(new Color(231, 76, 60));
        cancelBtn.setForeground(Color.WHITE);
        cancelBtn.setFont(new Font("Times New Roman", Font.BOLD, 12));
        cancelBtn.addActionListener(e -> paymentDialog.dispose());
        
        buttonPanel.add(confirmBtn);
        buttonPanel.add(cancelBtn);
        
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        paymentDialog.add(mainPanel);
        paymentDialog.setVisible(true);
    }
}

class InputPanel extends JPanel {
    private DashBoard app;
    private JTextField namaField, emailField, jumlahField;
    private JComboBox<String> kategoriCombo, bankCombo;
    private JTextArea pesanArea;
    private JButton submitBtn, resetBtn;
    
    public InputPanel(DashBoard app) {
        this.app = app;
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        
        add(createHeader(), BorderLayout.NORTH);
        add(createForm(), BorderLayout.CENTER);
    }
    
    private JPanel createHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(new Color(52, 152, 219));
        header.setBorder(BorderFactory.createEmptyBorder(25, 40, 25, 40));
        
        JLabel title = new JLabel("Form Donasi");
        title.setFont(UIConstants.TITLE_FONT);
        title.setForeground(Color.WHITE);
        
        RoundedButton backBtn = new RoundedButton("Kembali");
        backBtn.setFont(new Font("Times New Roman", Font.PLAIN, 13));
        backBtn.setBackground(new Color(41, 128, 185));
        backBtn.setForeground(Color.WHITE);
        backBtn.setPreferredSize(new Dimension(120, 40));
        backBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                backBtn.setBackground(new Color(31, 97, 141));
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                backBtn.setBackground(new Color(41, 128, 185));
            }
        });
        backBtn.addActionListener(e -> {
            clearForm();
            if ("admin".equals(app.currentUserRole)) {
                app.adminDashboardPanel.refreshStats();
                app.cardLayout.show(app.mainPanel, "AdminDashboard");
            } else {
                app.donorDashboardPanel.refreshStats();
                app.cardLayout.show(app.mainPanel, "DonorDashboard");
            }
        });
        
        header.add(title, BorderLayout.WEST);
        header.add(backBtn, BorderLayout.EAST);
        
        return header;
    }
    
    private JPanel createForm() {
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(UIConstants.PANEL_BG);
        formPanel.setBorder(BorderFactory.createEmptyBorder(40, 100, 40, 100));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(15, 10, 15, 10);
        
        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(createLabel("Nama Lengkap:"), gbc);
        
        gbc.gridx = 1; gbc.weightx = 1.0;
        namaField = createTextField();
        formPanel.add(namaField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0;
        formPanel.add(createLabel("Email:"), gbc);
        
        gbc.gridx = 1; gbc.weightx = 1.0;
        emailField = createTextField();
        formPanel.add(emailField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 2; gbc.weightx = 0;
        formPanel.add(createLabel("Kategori:"), gbc);
        
        gbc.gridx = 1; gbc.weightx = 1.0;
        String[] categories = {"Pendidikan", "Kesehatan", "Bencana Alam", 
                              "Kemanusiaan", "Lingkungan", "Lainnya"};
        kategoriCombo = new JComboBox<>(categories);
        kategoriCombo.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        kategoriCombo.setBackground(Color.WHITE);
        formPanel.add(kategoriCombo, gbc);
        
        gbc.gridx = 0; gbc.gridy = 3; gbc.weightx = 0;
        formPanel.add(createLabel("Jumlah Donasi (Rp):"), gbc);
        
        gbc.gridx = 1; gbc.weightx = 1.0;
        jumlahField = createTextField();
        formPanel.add(jumlahField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 4; gbc.weightx = 0;
        formPanel.add(createLabel("Bank/Metode Pembayaran:"), gbc);
        
        gbc.gridx = 1; gbc.weightx = 1.0;
        String[] banks = {"Tunai", "BCA", "Mandiri", "BRI", "BTN", "CIMB Niaga", "Danamon", "OVO", "GoPay", "Dana"};
        bankCombo = new JComboBox<>(banks);
        bankCombo.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        bankCombo.setBackground(Color.WHITE);
        formPanel.add(bankCombo, gbc);
        
        gbc.gridx = 0; gbc.gridy = 5; gbc.gridwidth = 2;
        pesanArea = new JTextArea(4, 20);
        pesanArea.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        pesanArea.setLineWrap(true);
        pesanArea.setWrapStyleWord(true);
        pesanArea.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(189, 195, 199)),
            BorderFactory.createEmptyBorder(8, 8, 8, 8)
        ));
        JScrollPane scrollPane = new JScrollPane(pesanArea);
        formPanel.add(scrollPane, gbc);
        
        gbc.gridx = 0; gbc.gridy = 6; gbc.gridwidth = 2; gbc.insets = new Insets(25, 10, 10, 10);
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        buttonPanel.setOpaque(false);
        
        submitBtn = new RoundedButton("Kirim Donasi");
        submitBtn.setFont(new Font("Times New Roman", Font.BOLD, 15));
        submitBtn.setBackground(new Color(46, 204, 113));
        submitBtn.setForeground(Color.WHITE);
        submitBtn.setPreferredSize(new Dimension(200, 50));
        submitBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                submitBtn.setBackground(new Color(39, 174, 96));
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                submitBtn.setBackground(new Color(46, 204, 113));
            }
        });
        submitBtn.addActionListener(e -> submitForm());
        
        resetBtn = new RoundedButton("Reset Form");
        resetBtn.setFont(new Font("Times New Roman", Font.BOLD, 15));
        resetBtn.setBackground(new Color(149, 165, 166));
        resetBtn.setForeground(Color.WHITE);
        resetBtn.setPreferredSize(new Dimension(200, 50));
        resetBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                resetBtn.setBackground(new Color(127, 140, 141));
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                resetBtn.setBackground(new Color(149, 165, 166));
            }
        });
        resetBtn.addActionListener(e -> clearForm());
        
        buttonPanel.add(submitBtn);
        buttonPanel.add(resetBtn);
        
        formPanel.add(buttonPanel, gbc);
        
        return formPanel;
    }
    
    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Times New Roman", Font.BOLD, 15));
        label.setForeground(new Color(44, 62, 80));
        return label;
    }
    
    private JTextField createTextField() {
        JTextField field = new JTextField();
        field.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(189, 195, 199), 1),
            BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));
        field.setBackground(Color.WHITE);
        field.setPreferredSize(new Dimension(200, 40));
        return field;
    }
    
    private void submitForm() {
        try {
            String nama = namaField.getText().trim();
            String email = emailField.getText().trim();
            String kategori = (String) kategoriCombo.getSelectedItem();
            String jumlahStr = jumlahField.getText().trim();
            String pesan = pesanArea.getText().trim();
            String bank = (String) bankCombo.getSelectedItem();
            
            if (nama.isEmpty() || email.isEmpty() || jumlahStr.isEmpty()) {
                JOptionPane.showMessageDialog(this, 
                    "Nama, Email, dan Jumlah harus diisi!", 
                    "Validasi Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
                JOptionPane.showMessageDialog(this, 
                    "Format email tidak valid!", 
                    "Validasi Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            double jumlah;
            try {
                jumlah = Double.parseDouble(jumlahStr);
                if (jumlah <= 0) {
                    throw new NumberFormatException();
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, 
                    "Jumlah donasi harus berupa angka positif!", 
                    "Validasi Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            String preview = "VERIFIKASI DATA DONASI (LANGKAH 2)\n\n" +
                "Nama: " + nama + "\n" +
                "Email: " + email + "\n" +
                "Kategori: " + kategori + "\n" +
                "Jumlah: Rp " + String.format("%.0f", jumlah) + "\n" +
                "Bank/Metode: " + bank + "\n" +
                "Pesan: " + (pesan.isEmpty() ? "(tidak ada)" : pesan) + "\n\n" +
                "Apakah data sudah benar? Klik OK untuk mengirim.";
            
            int confirm = JOptionPane.showConfirmDialog(this, preview, 
                "Konfirmasi Donasi (Langkah 2)", JOptionPane.OK_CANCEL_OPTION);
            
            if (confirm != JOptionPane.OK_OPTION) {
                return; 
            }
            
            String id = app.generateId();
            Donasi donasi = new Donasi(id, nama, email, kategori, 
                                      jumlah, LocalDate.now(), pesan, bank);
            app.addDonasi(donasi);

            if (!"admin".equals(app.currentUserRole)) {
                app.currentUserId = nama;
                app.currentUserRole = "donor";
            }
            app.refreshListData();
            app.refreshLaporan();
            
            JOptionPane.showMessageDialog(this, 
                "Donasi berhasil diproses!\n\nID Donasi: " + id + "\nTerima kasih atas kontribusi Anda!", 
                "Sukses", JOptionPane.INFORMATION_MESSAGE);
            
            clearForm();
            if ("admin".equals(app.currentUserRole)) {
                app.adminDashboardPanel.refreshStats();
                app.cardLayout.show(app.mainPanel, "AdminDashboard");
            } else {
                app.donorDashboardPanel.refreshStats();
                app.cardLayout.show(app.mainPanel, "DonorDashboard");
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Terjadi kesalahan: " + e.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void clearForm() {
        namaField.setText("");
        emailField.setText("");
        kategoriCombo.setSelectedIndex(0);
        jumlahField.setText("");
        bankCombo.setSelectedIndex(0);
        pesanArea.setText("");
    }
}

class LaporanPanel extends JPanel {
    private DashBoard app;
    private JLabel totalDonasiLabel, totalDonaturLabel, totalNominalLabel;
    private JTextArea reportArea;
    
    public LaporanPanel(DashBoard app) {
        this.app = app;
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        
        add(createHeader(), BorderLayout.NORTH);
        add(createContent(), BorderLayout.CENTER);
    }
    
    private JPanel createHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(UIConstants.ACCENT);
        header.setBorder(BorderFactory.createEmptyBorder(25, 40, 25, 40));
        
        JLabel title = new JLabel("Laporan & Statistik");
        title.setFont(UIConstants.TITLE_FONT);
        title.setForeground(Color.WHITE);
        
        RoundedButton backBtn = new RoundedButton("Kembali");
        backBtn.setFont(new Font("Times New Roman", Font.PLAIN, 13));
        backBtn.setBackground(new Color(52, 152, 219));
        backBtn.setForeground(Color.WHITE);
        backBtn.setPreferredSize(new Dimension(120, 40));
        backBtn.addActionListener(e -> {
            app.adminDashboardPanel.refreshStats();
            app.cardLayout.show(app.mainPanel, "AdminDashboard");
        });
        
        header.add(title, BorderLayout.WEST);
        header.add(backBtn, BorderLayout.EAST);
        
        return header;
    }
    
    private JPanel createContent() {
        JPanel content = new JPanel(new BorderLayout(0, 20));
        content.setBackground(Color.WHITE);
        content.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JPanel statsPanel = new JPanel(new GridLayout(1, 3, 15, 0));
        statsPanel.setOpaque(false);

        statsPanel.add(createStatCard("Total Donasi", "0", new Color(52, 152, 219)));
        statsPanel.add(createStatCard("Total Donatur", "0", new Color(46, 204, 113)));
        statsPanel.add(createStatCard("Total Nominal", "Rp 0", new Color(231, 76, 60)));

        JPanel topNorth = new JPanel(new BorderLayout());
        topNorth.setOpaque(false);
        topNorth.add(statsPanel, BorderLayout.CENTER);

        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        rightPanel.setOpaque(false);
        RoundedButton exportBtn = new RoundedButton("Export .txt");
        exportBtn.setFont(new Font("Times New Roman", Font.PLAIN, 13));
        exportBtn.setBackground(new Color(52, 152, 219));
        exportBtn.setForeground(Color.WHITE);
        exportBtn.setPreferredSize(new Dimension(140, 36));
        exportBtn.addActionListener(e -> {
            try {
                String fileName = "laporan_" + LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")) + ".txt";
                try (PrintWriter pw = new PrintWriter(new FileWriter(fileName))) {
                    pw.print(reportArea.getText());
                }
                JOptionPane.showMessageDialog(this, "Laporan berhasil diekspor: " + fileName, "Sukses", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Gagal mengekspor laporan: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        rightPanel.add(exportBtn);
        topNorth.add(rightPanel, BorderLayout.EAST);
        
        reportArea = new JTextArea();
        reportArea.setFont(new Font("Courier New", Font.PLAIN, 12));
        reportArea.setEditable(false);
        reportArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JScrollPane scrollPane = new JScrollPane(reportArea);
        scrollPane.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(189, 195, 199)),
            "Rincian Laporan",
            0, 0, new Font("Times New Roman", Font.BOLD, 14)
        ));
        
        content.add(statsPanel, BorderLayout.NORTH);
        content.add(scrollPane, BorderLayout.CENTER);
        
        return content;
    }
    
    private JPanel createStatCard(String title, String value, Color color) {
        RoundedPanel card = new RoundedPanel(15);
        card.setLayout(new BorderLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        titleLabel.setForeground(new Color(127, 140, 141));

        JLabel valueLabel;
        if (title.equals("Total Donasi")) {
            totalDonasiLabel = new JLabel(value);
            valueLabel = totalDonasiLabel;
        } else if (title.equals("Total Donatur")) {
            totalDonaturLabel = new JLabel(value);
            valueLabel = totalDonaturLabel;
        } else {
            totalNominalLabel = new JLabel(value);
            valueLabel = totalNominalLabel;
        }

        valueLabel.setFont(new Font("Times New Roman", Font.BOLD, 26));
        valueLabel.setForeground(color);

        card.add(titleLabel, BorderLayout.NORTH);
        card.add(valueLabel, BorderLayout.CENTER);

        return card;
    }
    
    public void refreshData() {
        List<Donasi> list = app.getDonasiList();

        if ("donor".equals(app.currentUserRole) && app.currentUserId != null && !app.currentUserId.isEmpty()) {
            List<Donasi> filtered = new ArrayList<>();
            for (Donasi d : list) {
                if (d.getNamaDonatur().equals(app.currentUserId)) filtered.add(d);
            }
            list = filtered;
        }

        totalDonasiLabel.setText(String.valueOf(list.size()));

        Set<String> uniqueDonatur = new HashSet<>();
        double totalNominal = 0;
        Map<String, Double> kategoriMap = new HashMap<>();
        Map<String, Integer> kategoriCount = new HashMap<>();
        Map<String, Double> bankMap = new HashMap<>();
        Map<String, Integer> bankCount = new HashMap<>();

        for (Donasi d : list) {
            uniqueDonatur.add(d.getEmail());
            totalNominal += d.getJumlah();

            kategoriMap.put(d.getKategori(), 
                kategoriMap.getOrDefault(d.getKategori(), 0.0) + d.getJumlah());
            kategoriCount.put(d.getKategori(), 
                kategoriCount.getOrDefault(d.getKategori(), 0) + 1);

            String bank = d.getBank() != null ? d.getBank() : "Tunai";
            bankMap.put(bank, bankMap.getOrDefault(bank, 0.0) + d.getJumlah());
            bankCount.put(bank, bankCount.getOrDefault(bank, 0) + 1);
        }
        
        totalDonaturLabel.setText(String.valueOf(uniqueDonatur.size()));
        totalNominalLabel.setText(String.format("Rp %.0f", totalNominal));
        
        StringBuilder report = new StringBuilder();
        report.append("===========================================================\n");
        report.append("           LAPORAN DONASI - ");
        if ("donor".equals(app.currentUserRole) && app.currentUserId != null && !app.currentUserId.isEmpty()) {
            report.append(app.currentUserId).append(" - ");
        }
        report.append(LocalDate.now().format(
            DateTimeFormatter.ofPattern("dd MMMM yyyy", new Locale("id", "ID")))).append("\n");
        report.append("===========================================================\n\n");
        
        report.append("RINGKASAN:\n");
        report.append("--------------------------------------------------------------\n");
        report.append(String.format("Total Transaksi Donasi  : %d transaksi\n", list.size()));
        report.append(String.format("Total Donatur Unik      : %d orang\n", uniqueDonatur.size()));
        report.append(String.format("Total Dana Terkumpul    : Rp %.2f\n", totalNominal));
        report.append(String.format("Rata-rata per Donasi    : Rp %.2f\n\n", 
            list.isEmpty() ? 0 : totalNominal / list.size()));
        
        report.append("STATISTIK PER KATEGORI:\n");
        report.append("--------------------------------------------------------------\n");
        
        for (Map.Entry<String, Double> entry : kategoriMap.entrySet()) {
            String kategori = entry.getKey();
            double jumlah = entry.getValue();
            int count = kategoriCount.get(kategori);
            double persentase = (jumlah / totalNominal) * 100;
            
            report.append(String.format("%-20s : Rp %12.0f (%d donasi) [%.1f%%]\n", 
                kategori, jumlah, count, persentase));
        }
        
        report.append("\nSTATISTIK PER METODE PEMBAYARAN:\n");
        report.append("--------------------------------------------------------------\n");
        
        for (Map.Entry<String, Double> entry : bankMap.entrySet()) {
            String bank = entry.getKey();
            double jumlah = entry.getValue();
            int count = bankCount.get(bank);
            double persentase = (jumlah / totalNominal) * 100;
            
            report.append(String.format("%-20s : Rp %12.0f (%d donasi) [%.1f%%]\n", 
                bank, jumlah, count, persentase));
        }
        
        report.append("\n");
        report.append("DAFTAR DONATUR TERBESAR:\n");
        report.append("--------------------------------------------------------------\n");
        
        List<Donasi> sortedList = new ArrayList<>(list);
        sortedList.sort(Comparator.comparingDouble(Donasi::getJumlah).reversed());
        
        int topCount = Math.min(10, sortedList.size());
        for (int i = 0; i < topCount; i++) {
            Donasi d = sortedList.get(i);
            report.append(String.format("%2d. %-25s Rp %12.0f [%s]\n", 
                (i + 1), d.getNamaDonatur(), d.getJumlah(), d.getBank()));
        }
        
        report.append("\n===========================================================\n");
        report.append("              Terima kasih atas kepercayaan Anda!\n");
        report.append("===============================================================\n");
        
        reportArea.setText(report.toString());
        reportArea.setCaretPosition(0);
    }
}