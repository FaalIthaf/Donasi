import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class DonorDashboardPanel extends JPanel {
    private DashBoard app;
    private JLabel myDonationLabel, myAmountLabel, lastDonationLabel, categoryLabel;
    
    public DonorDashboardPanel(DashBoard app) {
        this.app = app;
        setLayout(new BorderLayout());
        setBackground(UIConstants.PANEL_BG);
        
        add(createHeader(), BorderLayout.NORTH);
        add(createContent(), BorderLayout.CENTER);
    }
    
    private JPanel createHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(UIConstants.SUCCESS);
        header.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));
        
        JLabel title = new JLabel("Dashboard Donatur");
        title.setFont(UIConstants.TITLE_FONT);
        title.setForeground(Color.WHITE);
        
        JLabel subtitle = new JLabel("Kelola donasi Anda dan pantau kontribusi");
        subtitle.setFont(UIConstants.SUBTITLE_FONT);
        subtitle.setForeground(new Color(189, 195, 199));
        
        JPanel titlePanel = new JPanel(new GridLayout(2, 1, 0, 5));
        titlePanel.setOpaque(false);
        titlePanel.add(title);
        titlePanel.add(subtitle);
        
        RoundedButton logoutBtn = new RoundedButton("Logout");
        logoutBtn.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        logoutBtn.setBackground(new Color(39, 174, 96));
        logoutBtn.setForeground(Color.WHITE);
        logoutBtn.setPreferredSize(new Dimension(100, 40));
        logoutBtn.addActionListener(e -> app.logout());
        
        header.add(titlePanel, BorderLayout.WEST);
        header.add(logoutBtn, BorderLayout.EAST);
        
        return header;
    }
    
    private JPanel createContent() {
        JPanel content = new JPanel(new GridBagLayout());
        content.setBackground(UIConstants.PANEL_BG);
        content.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.weightx = 1.0;
        
        // Top stats: 3 cards in one row
        gbc.gridx = 0; gbc.gridy = 0;
        content.add(createStatCard("Donasi Saya", "0", new Color(52, 152, 219)), gbc);
        
        gbc.gridx = 1;
        content.add(createStatCard("Total Nominal", "Rp 0", new Color(46, 204, 113)), gbc);
        
        gbc.gridx = 2;
        content.add(createStatCard("Kategori Favorit", "—", new Color(155, 89, 182)), gbc);
        
        // Menu buttons
        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 3;
        content.add(createMenuButtons(), gbc);
        
        return content;
    }
    
    private JPanel createStatCard(String title, String value, Color color) {
        RoundedPanel card = new RoundedPanel(15);
        card.setLayout(new BorderLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));
        card.setPreferredSize(new Dimension(280, 150));
        
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        titleLabel.setForeground(new Color(127, 140, 141));
        
        JLabel valueLabel;
        if (title.equals("Donasi Saya")) {
            myDonationLabel = new JLabel(value);
            valueLabel = myDonationLabel;
        } else if (title.equals("Total Nominal")) {
            myAmountLabel = new JLabel(value);
            valueLabel = myAmountLabel;
        } else {
            categoryLabel = new JLabel(value);
            valueLabel = categoryLabel;
        }
        
        valueLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        valueLabel.setForeground(color);
        
        card.add(titleLabel, BorderLayout.NORTH);
        card.add(valueLabel, BorderLayout.CENTER);
        
        return card;
    }
    
    private JPanel createMenuButtons() {
        JPanel menuPanel = new JPanel(new GridLayout(2, 2, 20, 20));
        menuPanel.setOpaque(false);
        menuPanel.setBorder(BorderFactory.createEmptyBorder(25, 0, 0, 0));
        
        RoundedButton donateBtn = new RoundedButton("Donasi Sekarang");
        donateBtn.setFont(new Font("Segoe UI", Font.BOLD, 16));
        donateBtn.setBackground(new Color(46, 204, 113));
        donateBtn.setForeground(Color.WHITE);
        donateBtn.setPreferredSize(new Dimension(240, 85));
        donateBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                donateBtn.setBackground(new Color(39, 174, 96));
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                donateBtn.setBackground(new Color(46, 204, 113));
            }
        });
        donateBtn.addActionListener(e -> app.showPanel("Input"));
        
        RoundedButton historyBtn = new RoundedButton("Riwayat Donasi");
        historyBtn.setFont(new Font("Segoe UI", Font.BOLD, 16));
        historyBtn.setBackground(new Color(52, 152, 219));
        historyBtn.setForeground(Color.WHITE);
        historyBtn.setPreferredSize(new Dimension(240, 85));
        historyBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                historyBtn.setBackground(new Color(41, 128, 185));
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                historyBtn.setBackground(new Color(52, 152, 219));
            }
        });
        historyBtn.addActionListener(e -> app.showPanel("ListData"));
        
        // Donor tidak menampilkan laporan global; jika ingin melihat ringkasan, pakai Riwayat/Profil
        
        RoundedButton logoutBtn = new RoundedButton("Keluar");
        logoutBtn.setFont(new Font("Segoe UI", Font.BOLD, 16));
        logoutBtn.setBackground(new Color(231, 76, 60));
        logoutBtn.setForeground(Color.WHITE);
        logoutBtn.setPreferredSize(new Dimension(240, 85));
        logoutBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                logoutBtn.setBackground(new Color(192, 57, 43));
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                logoutBtn.setBackground(new Color(231, 76, 60));
            }
        });
        logoutBtn.addActionListener(e -> app.logout());
        
        menuPanel.add(donateBtn);
        menuPanel.add(historyBtn);
        // placeholder panel to keep layout consistent
        menuPanel.add(new JPanel());
        menuPanel.add(logoutBtn);
        
        return menuPanel;
    }
    
    public void refreshStats() {
        int myDonationCount = 0;
        double myTotal = 0;
        String favoriteCategory = "—";
        Map<String, Integer> categoryCount = new HashMap<>();

        for (Donasi d : app.getDonasiList()) {
            if (app.currentUserId != null && !app.currentUserId.isEmpty()) {
                if (!d.getNamaDonatur().equals(app.currentUserId)) continue;
            }
            myDonationCount++;
            myTotal += d.getJumlah();
            categoryCount.put(d.getKategori(), categoryCount.getOrDefault(d.getKategori(), 0) + 1);
        }
        
        // Find most frequent category
        if (!categoryCount.isEmpty()) {
            favoriteCategory = categoryCount.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("—");
        }
        
        myDonationLabel.setText(String.valueOf(myDonationCount));
        myAmountLabel.setText(String.format("Rp %.0f", myTotal));
        categoryLabel.setText(favoriteCategory);
    }
}