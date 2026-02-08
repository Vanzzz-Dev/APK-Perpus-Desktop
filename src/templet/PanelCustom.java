package templet;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import javax.swing.JPanel;

public class PanelCustom extends JPanel {

    private int roundTopLeft = 0;
    private int roundTopRight = 0;
    private int roundBottomLeft = 0;
    private int roundBottomRight = 0;

    public PanelCustom() {
        setOpaque(false);
    }

    public int getRoundTopLeft() {
        return roundTopLeft;
    }

    public void setRoundTopLeft(int roundTopLeft) {
        this.roundTopLeft = roundTopLeft;
        repaint();
    }

    public int getRoundTopRight() {
        return roundTopRight;
    }

    public void setRoundTopRight(int roundTopRight) {
        this.roundTopRight = roundTopRight;
        repaint();
    }

    public int getRoundBottomLeft() {
        return roundBottomLeft;
    }

    public void setRoundBottomLeft(int roundBottomLeft) {
        this.roundBottomLeft = roundBottomLeft;
        repaint();
    }

    public int getRoundBottomRight() {
        return roundBottomRight;
    }

    public void setRoundBottomRight(int roundBottomRight) {
        this.roundBottomRight = roundBottomRight;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        Graphics2D g2 = (Graphics2D) graphics.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(getBackground());

        Area area = new Area(new Rectangle2D.Double(0, 0, getWidth(), getHeight()));

        if (roundTopLeft > 0) {
            area.intersect(new Area(createRoundTopLeft()));
        }
        if (roundTopRight > 0) {
            area.intersect(new Area(createRoundTopRight()));
        }
        if (roundBottomLeft > 0) {
            area.intersect(new Area(createRoundBottomLeft()));
        }
        if (roundBottomRight > 0) {
            area.intersect(new Area(createRoundBottomRight()));
        }

        g2.fill(area);
        g2.dispose();
        super.paintComponent(graphics);
    }

    private Shape createRoundTopLeft() {
        int w = getWidth();
        int h = getHeight();
        int r = Math.min(Math.min(w, h), roundTopLeft);

        Area area = new Area(new RoundRectangle2D.Double(0, 0, w, h, r, r));
        area.add(new Area(new Rectangle2D.Double(r / 2, 0, w - r / 2, h)));
        area.add(new Area(new Rectangle2D.Double(0, r / 2, w, h - r / 2)));
        return area;
    }

    private Shape createRoundTopRight() {
        int w = getWidth();
        int h = getHeight();
        int r = Math.min(Math.min(w, h), roundTopRight);

        Area area = new Area(new RoundRectangle2D.Double(0, 0, w, h, r, r));
        area.add(new Area(new Rectangle2D.Double(0, 0, w - r / 2, h)));
        area.add(new Area(new Rectangle2D.Double(0, r / 2, w, h - r / 2)));
        return area;
    }

    private Shape createRoundBottomLeft() {
        int w = getWidth();
        int h = getHeight();
        int r = Math.min(Math.min(w, h), roundBottomLeft);

        Area area = new Area(new RoundRectangle2D.Double(0, 0, w, h, r, r));
        area.add(new Area(new Rectangle2D.Double(r / 2, 0, w - r / 2, h)));
        area.add(new Area(new Rectangle2D.Double(0, 0, w, h - r / 2)));
        return area;
    }

    private Shape createRoundBottomRight() {
        int w = getWidth();
        int h = getHeight();
        int r = Math.min(Math.min(w, h), roundBottomRight);

        Area area = new Area(new RoundRectangle2D.Double(0, 0, w, h, r, r));
        area.add(new Area(new Rectangle2D.Double(0, 0, w - r / 2, h)));
        area.add(new Area(new Rectangle2D.Double(0, 0, w, h - r / 2)));
        return area;
    }
}
