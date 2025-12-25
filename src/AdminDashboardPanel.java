import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class AdminDashboardPanel extends JPanel {
    private DashBoard app;
    private JLabel totalDonasiLabel, totalDonaturLabel, totalNominalLabel;
    
    public AdminDashboardPanel(DashBoard app) {
        this.app = app;
        setLayout(new BorderLayout());
        setBackground(new Color(240, 248, 255));
        
        add(createHeader(), BorderLayout.NORTH);
        add(createContent(), BorderLayout.CENTER);
    }
    
    private JPanel createHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(UIConstants.PRIMARY);
        header.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));
        
        JLabel title = new JLabel("Admin Dashboard");
        title.setFont(UIConstants.TITLE_FONT);
        title.setForeground(Color.WHITE);
        
        JLabel subtitle = new JLabel("Kelola semua data donasi dengan lengkap");
        subtitle.setFont(UIConstants.SUBTITLE_FONT);
        subtitle.setForeground(new Color(189, 195, 199));
        
        JPanel titlePanel = new JPanel(new GridLayout(2, 1, 0, 5));
        titlePanel.setOpaque(false);
        titlePanel.add(title);
        titlePanel.add(subtitle);
        
        RoundedButton logoutBtn = new RoundedButton("Logout");
        logoutBtn.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        logoutBtn.setBackground(new Color(231, 76, 60));
        logoutBtn.setForeground(Color.WHITE);
        logoutBtn.setPreferredSize(new Dimension(100, 40));
        logoutBtn.addActionListener(e -> app.logout());
        
        header.add(titlePanel, BorderLayout.WEST);
        header.add(logoutBtn, BorderLayout.EAST);
        
        return header;
    }
    
    private JPanel createContent() {
        JPanel content = new JPanel(new GridBagLayout());
        content.setOpaque(false);
        content.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.weightx = 1.0;
        
        gbc.gridx = 0; gbc.gridy = 0;
        content.add(createStatCard("Total Donasi", "0", new Color(52, 152, 219)), gbc);
        
        gbc.gridx = 1;
        content.add(createStatCard("Total Donatur", "0", new Color(46, 204, 113)), gbc);
        
        gbc.gridx = 2;
        content.add(createStatCard("Total Nominal", "Rp 0", new Color(155, 89, 182)), gbc);
        
        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 3;
        content.add(createMenuButtons(), gbc);
        
        return content;
    }
    
    private JPanel createStatCard(String title, String value, Color color) {
        RoundedPanel card = new RoundedPanel(15);
        card.setLayout(new BorderLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));
        card.setPreferredSize(new Dimension(280, 140));
        
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 15));
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
        
        valueLabel.setFont(new Font("Segoe UI", Font.BOLD, 32));
        valueLabel.setForeground(color);
        
        card.add(titleLabel, BorderLayout.NORTH);
        card.add(valueLabel, BorderLayout.CENTER);
        
        return card;
    }
    
    private JPanel createMenuButtons() {
        JPanel menuPanel = new JPanel(new GridLayout(2, 2, 20, 20));
        menuPanel.setOpaque(false);
        menuPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
        
        menuPanel.add(createMenuButton("Input Donasi", new Color(52, 152, 219), "Input"));
        menuPanel.add(createMenuButton("List Data", new Color(46, 204, 113), "ListData"));
        menuPanel.add(createMenuButton("Laporan", new Color(155, 89, 182), "Laporan"));
        menuPanel.add(createMenuButton("Keluar", new Color(231, 76, 60), null));
        
        return menuPanel;
    }
    
    private JButton createMenuButton(String text, Color color, String panelName) {
        RoundedButton btn = new RoundedButton(text);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 17));
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        btn.setPreferredSize(new Dimension(220, 90));
        
        btn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btn.setBackground(color.darker());
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                btn.setBackground(color);
            }
        });
        
        btn.addActionListener(e -> {
            if (panelName != null) {
                app.showPanel(panelName);
            } else {
                int confirm = JOptionPane.showConfirmDialog(app, 
                    "Yakin ingin keluar?", "Konfirmasi", 
                    JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
        });
        
        return btn;
    }
    
    public void refreshStats() {
        java.util.List<Donasi> list = app.getDonasiList();
        totalDonasiLabel.setText(String.valueOf(list.size()));
        
        Set<String> uniqueDonatur = new HashSet<>();
        double total = 0;
        
        for (Donasi d : list) {
            uniqueDonatur.add(d.getEmail());
            total += d.getJumlah();
        }
        
        totalDonaturLabel.setText(String.valueOf(uniqueDonatur.size()));
        totalNominalLabel.setText(String.format("Rp %.0f", total));
    }
}
