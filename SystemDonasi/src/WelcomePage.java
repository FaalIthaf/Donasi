import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import javax.swing.*;

public class WelcomePage extends JFrame {

    public WelcomePage() {
        setTitle("System Donation");
        setSize(1920, 1080);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panelWelcomePage = new JPanel();
        panelWelcomePage.setLayout(new BoxLayout(panelWelcomePage, BoxLayout.Y_AXIS));
        // Warm background color suitable for donation theme
        panelWelcomePage.setBackground(new Color(255, 248, 220)); // Light cream/warm background

        panelWelcomePage.add(Box.createVerticalGlue());

        ImageIcon icon = new ImageIcon(getClass().getResource("logo.png"));
        BufferedImage bufferedImage = removeBackground(icon.getImage());
        Image img = bufferedImage.getScaledInstance(150, 150, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(img);
        JLabel logoLabel = new JLabel(scaledIcon);
        logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelWelcomePage.add(logoLabel);

        panelWelcomePage.add(Box.createVerticalStrut(20));

        JLabel label = new JLabel("Human & Empathy", SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 28));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        label.setForeground(new Color(139, 69, 19)); // Brown color for warmth and trust
        panelWelcomePage.add(label);

        panelWelcomePage.add(Box.createVerticalStrut(10));

        // Add subtitle for donation theme
        JLabel subtitleLabel = new JLabel("Sedikit dari kita, besar bagi mereka", SwingConstants.CENTER);
        subtitleLabel.setFont(new Font("Arial", Font.ITALIC, 16));
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        subtitleLabel.setForeground(new Color(102, 51, 0)); // Dark brown
        panelWelcomePage.add(subtitleLabel);

        panelWelcomePage.add(Box.createVerticalStrut(30));

        JButton loginButton = new JButton("Berdonasi Sekarang");
        loginButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginButton.setFont(new Font("Arial", Font.BOLD, 18));
        loginButton.setBackground(new Color(34, 139, 34)); // Forest green for trust and nature
        loginButton.setForeground(Color.WHITE);
        loginButton.setFocusPainted(false);
        loginButton.setBorder(BorderFactory.createEmptyBorder(15, 30, 15, 30));
        loginButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        // Add hover effect
        loginButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                loginButton.setBackground(new Color(0, 100, 0)); // Darker green on hover
            }
            @Override
            public void mouseExited(MouseEvent e) {
                loginButton.setBackground(new Color(34, 139, 34)); // Back to original green
            }
        });
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new LoginPage().setVisible(true);
                dispose(); // Close the welcome page
            }
        });
        panelWelcomePage.add(loginButton);

        panelWelcomePage.add(Box.createVerticalGlue());

        setContentPane(panelWelcomePage);
    }

    private BufferedImage removeBackground(Image image) {
        BufferedImage bufferedImage = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = bufferedImage.createGraphics();
        g2d.drawImage(image, 0, 0, null);
        g2d.dispose();

        int white = Color.WHITE.getRGB();
        for (int x = 0; x < bufferedImage.getWidth(); x++) {
            for (int y = 0; y < bufferedImage.getHeight(); y++) {
                int rgb = bufferedImage.getRGB(x, y);
                if (rgb == white) {
                    bufferedImage.setRGB(x, y, 0x00000000);
                }
            }
        }
        return bufferedImage;
    }

}


