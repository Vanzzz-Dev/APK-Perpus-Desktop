package templet;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.List;
import java.util.ArrayList;
import javax.swing.JPasswordField;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicPasswordFieldUI;

public class PasswordFieldCustom extends JPasswordField {

    private PasswordFieldUI passwordUI;

    public PasswordFieldCustom() {
        passwordUI = new PasswordFieldUI(this);
        setUI(passwordUI);
    }

    private class PasswordFieldUI extends BasicPasswordFieldUI {

        private JPasswordField passwordField;
        private Border border;
        private int round = 15;
        private List<String> items = new ArrayList<>();

        public PasswordFieldUI(JPasswordField passwordField) {
            this.passwordField = passwordField;
            border = new Border(10);
            border.setRound(round);
            passwordField.setBorder(border);
            passwordField.setOpaque(false);
            passwordField.setSelectionColor(Color.GRAY);
            passwordField.setSelectedTextColor(Color.white);

            passwordField.addFocusListener(new FocusAdapter() {
                @Override
                public void focusGained(FocusEvent e) {
                    border.setColor(Color.CYAN);
                    passwordField.repaint();
                }

                @Override
                public void focusLost(FocusEvent e) {
                    border.setColor(Color.black);
                    passwordField.repaint();
                }
            });
        }

        public int getRound() {
            return round;
        }

        public void setRound(int round) {
            this.round = round;
            border.setRound(round);
            passwordField.repaint();
        }

        public List<String> getItems() {
            return items;
        }

        public void setItems(List<String> items) {
            this.items = items;
        }

        @Override
        protected void paintBackground(Graphics g) {
            if (passwordField.isOpaque()) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(passwordField.getBackground());
                g2.drawRoundRect(
                        0, 0,
                        passwordField.getWidth(),
                        passwordField.getHeight(),
                        round, round
                );
                g2.dispose();
            }
        }

        private class Border extends EmptyBorder {

            private Color focusColor = Color.cyan;
            private Color color = Color.black;
            private int round;

            public Border(int border) {
                super(border, border, border, border);
            }

            public Border() {
                this(5);
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
