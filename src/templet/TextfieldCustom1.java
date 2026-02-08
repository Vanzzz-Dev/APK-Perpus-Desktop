package templet;

import java.awt.Color;
import java.awt.Component;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.metal.MetalTextFieldUI;

public class TextfieldCustom1 extends JTextField {

    private TextFieldUI textUI;
    private String placeholder = "";

    public TextfieldCustom1() {
        textUI = new TextFieldUI(this);
        setUI(textUI);
        setOpaque(false);
    }

    public void setPlaceholder(String text) {
        this.placeholder = text;
        repaint();
    }

    public String getPlaceholder() {
        return placeholder;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (placeholder == null || placeholder.isEmpty()) return;

        if (getText().isEmpty() && !isFocusOwner()) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(Color.GRAY);
            g2.setFont(getFont().deriveFont(14f));

            Insets insets = getInsets();
            FontMetrics fm = g2.getFontMetrics();
            int y = (getHeight() - fm.getHeight()) / 2 + fm.getAscent();

            g2.drawString(placeholder, insets.left + 5, y);
            g2.dispose();
        }
    }

    private class TextFieldUI extends MetalTextFieldUI {

        private JTextField textfield;
        private Border border;
        private int round = 10;
        private List<String> items = new ArrayList<>();

        public TextFieldUI(JTextField textfield) {
            this.textfield = textfield;
            border = new Border(10);
            border.setRound(round);
            textfield.setBorder(border);
            textfield.setOpaque(false);
            textfield.setSelectionColor(Color.cyan);
            textfield.setSelectedTextColor(Color.white);

            textfield.addFocusListener(new FocusAdapter() {
                @Override
                public void focusGained(FocusEvent e) {
                    border.setColor(Color.cyan);
                    textfield.repaint();
                }

                @Override
                public void focusLost(FocusEvent e) {
                    border.setColor(Color.black);
                    textfield.repaint();
                }
            });
        }

        public int getRound() {
            return round;
        }

        public void setRound(int round) {
            this.round = round;
            border.setRound(round);
            textfield.repaint();
        }

        public List<String> getItems() {
            return items;
        }

        public void setItems(List<String> items) {
            this.items = items;
        }

        @Override
        protected void paintBackground(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(textfield.getBackground());
            g2.fillRoundRect(0, 0, textfield.getWidth(), textfield.getHeight(), round, round);
            g2.dispose();
        }

        // ================= BORDER =================
        private class Border extends EmptyBorder {

            private Color focusColor = Color.cyan;
            private Color color = Color.black;
            private int round;

            public Border(int border) {
                super(border, border, border, border);
            }

            public void setColor(Color color) {
                this.color = color;
            }

            public void setRound(int round) {
                this.round = round;
            }

            @Override
            public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(c.isFocusOwner() ? focusColor : color);
                g2.drawRoundRect(x, y, width - 1, height - 1, round, round);
                g2.dispose();
            }
        }
    }
}
