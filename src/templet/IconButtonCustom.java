package templet;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.Icon;
import javax.swing.JButton;

public class IconButtonCustom extends JButton {

    private boolean over;
    private Color fill;
    private Icon icon;

    private Color fillOver;
    private Color fillClick;
    private int strokeWidth;
    private int roundedCorner;

    public IconButtonCustom() {
        setBackground(new Color(52, 152, 219)); // background utama
        fillOver = new Color(58, 60, 61);
        fillClick = new Color(30, 92, 133);

        strokeWidth = 2;
        roundedCorner = 10;
        fill = getBackground();

        setOpaque(false);
        setBorder(null);
        setFocusPainted(false);
        setContentAreaFilled(false);
        setForeground(Color.WHITE);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                fill = fillOver;
                over = true;
                repaint();
            }

            @Override
            public void mousePressed(MouseEvent e) {
                fill = fillClick;
                repaint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                fill = over ? fillOver : getBackground();
                repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                fill = getBackground();
                over = false;
                repaint();
            }
        });
    }

    @Override
    public void setBackground(Color bg) {
        super.setBackground(bg);
        fill = bg;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int s = strokeWidth;
        int w = getWidth() - (2 * s);
        int h = getHeight() - (2 * s);

        g2d.setColor(fill);
        g2d.fillRoundRect(s, s, w, h, roundedCorner, roundedCorner);
        g2d.dispose();

        super.paintComponent(g);
    }
}
