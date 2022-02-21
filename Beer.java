package models;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Beer implements Runnable {

    private final int x;
    private int y;
    private final int vely;
    private boolean catched;
    private BufferedImage img;

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public BufferedImage getImg() {
        return img;
    }


    public Beer(int x, int vely) {

        this.x = x;
        this.y = -150;
        this.vely = vely;
        this.catched = false;

        try {
            img = ImageIO.read(new File("D:/intellJ/Projekt V3/src/resources/images/bottle2.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void run() {
        while (!this.catched) {
            this.y += vely;

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void setCatch() {
        this.catched = true;
    }
}
