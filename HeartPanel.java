package views;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class HeartPanel extends JPanel {
    private int life = 3;
    int x;
    int y;
    private BufferedImage fH;
    private BufferedImage eH;

    public HeartPanel() {

        try {
            fH = ImageIO.read(new File("D:/intellJ/Projekt V3/src/resources/images/fullHeart1.png"));
            eH = ImageIO.read(new File("D:/intellJ/Projekt V3/src/resources/images/emptyHeart1.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        x = 0;
        for (int i = 0; i < 3; i++) {
            if (i < this.getLife()) {
                g.drawImage(fH, x, y, null);
            } else {
                g.drawImage(eH, x, y, null);
            }
            x = x + 55;
        }
    }

    public int getLife() {
        return life;
    }

    public void setLife(int life) {
        this.life = life;
    }
}
