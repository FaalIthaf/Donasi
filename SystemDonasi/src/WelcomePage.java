import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.*;

public class WelcomePage extends JFrame {

    public WelcomePage() {
        setTitle("System Donation");
        setSize(1920, 1080);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        panel.add(Box.createVerticalGlue());

        ImageIcon icon = new ImageIcon(getClass().getResource("logo.png"));
        BufferedImage bufferedImage = removeBackground(icon.getImage());
        Image img = bufferedImage.getScaledInstance(150, 150, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(img);
        JLabel logoLabel = new JLabel(scaledIcon);
        logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(logoLabel);

        panel.add(Box.createVerticalStrut(20));

        JLabel label = new JLabel("Human & Empathy", SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 20));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(label);

        panel.add(Box.createVerticalStrut(20));

        panel.add(Box.createVerticalGlue());

        setContentPane(panel);
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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new WelcomePage().setVisible(true);
        });
    }
}
