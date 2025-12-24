import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import javax.swing.*;

public class LoginPage extends JFrame {

    private JRadioButton adminRadio;
    private JRadioButton donaturRadio;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton backButton;

    public LoginPage() {
        setTitle("Login Page - System Donation");
        setSize(1920, 1080);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Main panel with gradient-like background
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(240, 248, 255));

        // Top panel for logo and title
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        topPanel.setBackground(new Color(240, 248, 255));
        topPanel.setBorder(BorderFactory.createEmptyBorder(30, 50, 20, 50));

        // Logo
        ImageIcon icon = new ImageIcon(getClass().getResource("logo.png"));
        BufferedImage bufferedImage = removeBackground(icon.getImage());
        Image img = bufferedImage.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(img);
        JLabel logoLabel = new JLabel(scaledIcon);
        logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        topPanel.add(logoLabel);
        topPanel.add(Box.createVerticalStrut(15));

        // Title
        JLabel titleLabel = new JLabel("System Donation");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setForeground(new Color(0, 51, 102));
        topPanel.add(titleLabel);
        topPanel.add(Box.createVerticalStrut(10));

        // Subtitle
        JLabel subtitleLabel = new JLabel("Silakan login untuk melanjutkan");
        subtitleLabel.setFont(new Font("Arial", Font.ITALIC, 14));
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        subtitleLabel.setForeground(new Color(102, 102, 102));
        topPanel.add(subtitleLabel);

        mainPanel.add(topPanel, BorderLayout.NORTH);

        // Center panel for form - Card-like design
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBackground(new Color(240, 248, 255));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(20, 100, 20, 100));

        // Form card
        JPanel formCard = new JPanel();
        formCard.setLayout(new BoxLayout(formCard, BoxLayout.Y_AXIS));
        formCard.setBackground(Color.WHITE);
        formCard.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
            BorderFactory.createEmptyBorder(30, 40, 30, 40)));
        formCard.setMaximumSize(new Dimension(450, 400));

        // Login type selection
        JPanel typePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        typePanel.setBackground(Color.WHITE);
        typePanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));

        JLabel typeLabel = new JLabel("Pilih Tipe Login:");
        typeLabel.setFont(new Font("Arial", Font.BOLD, 16));
        typeLabel.setForeground(new Color(0, 51, 102));
        typePanel.add(typeLabel);

        formCard.add(typePanel);

        JPanel radioPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        radioPanel.setBackground(Color.WHITE);

        adminRadio = new JRadioButton("Admin");
        adminRadio.setFont(new Font("Arial", Font.PLAIN, 14));
        adminRadio.setBackground(Color.WHITE);
        adminRadio.setSelected(true);

        donaturRadio = new JRadioButton("Donatur");
        donaturRadio.setFont(new Font("Arial", Font.PLAIN, 14));
        donaturRadio.setBackground(Color.WHITE);

        ButtonGroup group = new ButtonGroup();
        group.add(adminRadio);
        group.add(donaturRadio);

        radioPanel.add(adminRadio);
        radioPanel.add(Box.createHorizontalStrut(20));
        radioPanel.add(donaturRadio);

        formCard.add(radioPanel);
        formCard.add(Box.createVerticalStrut(25));

        JPanel usernamePanel = new JPanel(new BorderLayout());
        usernamePanel.setBackground(Color.WHITE);
        usernamePanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0));

        JLabel userIcon = new JLabel("Username: ");
        userIcon.setFont(new Font("Arial", Font.PLAIN, 16));
        userIcon.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 5));
        usernamePanel.add(userIcon, BorderLayout.WEST);

        usernameField = new JTextField(20);
        usernameField.setFont(new Font("Arial", Font.PLAIN, 14));
        usernameField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(0, 123, 255)),
            BorderFactory.createEmptyBorder(8, 10, 8, 10)));
        usernameField.setBackground(new Color(248, 249, 250));
        usernamePanel.add(usernameField, BorderLayout.CENTER);

        formCard.add(usernamePanel);
        formCard.add(Box.createVerticalStrut(20));

        JPanel passwordPanel = new JPanel(new BorderLayout());
        passwordPanel.setBackground(Color.WHITE);
        passwordPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0));

        JLabel passIcon = new JLabel("Password: ");
        passIcon.setFont(new Font("Arial", Font.PLAIN, 16));
        passIcon.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 5));
        passwordPanel.add(passIcon, BorderLayout.WEST);

        passwordField = new JPasswordField(20);
        passwordField.setFont(new Font("Arial", Font.PLAIN, 14));
        passwordField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(0, 123, 255)),
            BorderFactory.createEmptyBorder(8, 10, 8, 10)));
        passwordField.setBackground(new Color(248, 249, 250));
        passwordPanel.add(passwordField, BorderLayout.CENTER);

        formCard.add(passwordPanel);
        formCard.add(Box.createVerticalStrut(35));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        buttonPanel.setBackground(Color.WHITE);

        backButton = new JButton("KEMBALI");
        backButton.setFont(new Font("Arial", Font.PLAIN, 14));
        backButton.setBackground(new Color(108, 117, 125));
        backButton.setForeground(Color.WHITE);
        backButton.setFocusPainted(false);
        backButton.setBorder(BorderFactory.createEmptyBorder(12, 25, 12, 25));
        backButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        backButton.addActionListener(e -> {
            new WelcomePage().setVisible(true);
            dispose();
        });
        buttonPanel.add(backButton);

        loginButton = new JButton("LOGIN");
        loginButton.setFont(new Font("Arial", Font.BOLD, 14));
        loginButton.setBackground(new Color(0, 123, 255));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFocusPainted(false);
        loginButton.setBorder(BorderFactory.createEmptyBorder(12, 30, 12, 30));
        loginButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                boolean isAdmin = adminRadio.isSelected();
                boolean loginSuccess = false;

                if (isAdmin) {
                    if ("FaalIthaf".equals(username) && "123".equals(password)) {
                        loginSuccess = true;
                        JOptionPane.showMessageDialog(LoginPage.this, "Login Admin Berhasil!");
                    }
                } else {
                    loginSuccess = true;
                    JOptionPane.showMessageDialog(LoginPage.this, "Login Donatur Berhasil!");
                }

                if (!loginSuccess) {
                    JOptionPane.showMessageDialog(LoginPage.this, "Username atau Password salah!");
                } else {
                    dispose(); 
                }
            }
        });
        buttonPanel.add(loginButton);

        formCard.add(buttonPanel);

        centerPanel.add(formCard);

        mainPanel.add(centerPanel, BorderLayout.CENTER);
        setContentPane(mainPanel);
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
