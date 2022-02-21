package views;

import controllers.BeerScheduler;
import models.Beer;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class GamePanel extends JPanel implements ActionListener, KeyListener {

    public static final int CRATE_SPEED = 6;

    Timer t = new Timer(5, this);

    private int x = 640, y = 525, velx = 0, vely = 0;
    private final BufferedImage img;
    private final BufferedImage bg;
    private final BeerScheduler sch;
    private final JLabel sc;
    private final HeartPanel hp = new HeartPanel();
    private int score;
    private int go;

    public GamePanel() throws IOException {
        t.start();

        go = 3;
        img = ImageIO.read(new File("D:/intellJ/Projekt V3/src/resources/images/2beer1.png"));
        bg = ImageIO.read(new File("D:/intellJ/Projekt V3/src/resources/images/bg.jpg"));

        sch = new BeerScheduler();

        sc = new JLabel("Score : " + score);
        sc.setFont(new Font("Verdana", Font.BOLD, 20));
        sc.setForeground(Color.WHITE);
        sc.setBounds(0, 0, 150, 50);
        this.add(sc);

        hp.setOpaque(false);
        hp.setBounds(1100, 0, 165, 55);
        this.add(hp);

        this.setLayout(null);
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
    }

    @Override
    public void paintComponent(Graphics g) {
        if (go != 0) {

            super.paintComponent(g);

            g.drawImage(bg, 0, 0, null);

            int length = sch.getList().size();

            for (int i = 0; i < length; i++) {
                Beer beer = sch.getList().get(i);
                int beerX = beer.getX();
                int beerY = beer.getY();
                BufferedImage beerImg = beer.getImg();

                g.drawImage(beerImg, beerX, beerY, null);
            }

            g.drawImage(img, x, y, null);
            sc.setText("Score: " + score);

            hitBeer();
        } else {
            this.removeAll();
            this.revalidate();
            this.repaint();
        }

    }

    public void hitBeer() {
        int listLenght = sch.getList().size();
        for (int i = 0; i < listLenght; i++) {
            Beer beer = sch.getList().get(i);
            int beerBottom = beer.getY() + 150;
            int crateTop = this.y;

            if (beerBottom >= crateTop) {
                int crateLeft = x;
                int crateRight = x + 225;
                int beerLeft = beer.getX();
                int beerRight = beer.getX() + 40;

                if ((beerLeft > crateLeft && beerLeft < crateRight) || (beerRight > crateLeft && beerRight < crateRight)) {
                    score = score + 1;

                    // play sound effect
                    File file = new File("D:/intellJ/Projekt V3/src/resources/sounds/scoreUP.wav");
                    try {
                        AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
                        Clip clip = AudioSystem.getClip();
                        clip.open(audioStream);
                        clip.start();
                    } catch (LineUnavailableException | UnsupportedAudioFileException | IOException e) {
                        e.printStackTrace();
                    }

                    sch.removeBeer(i);
                    beer.setCatch();
                    listLenght -= 1;

                }
            }

            int beerY = beer.getY();
            int crateBottom = this.y;
            if (beerY > crateBottom) {
                sch.removeBeer(i);
                beer.setCatch();
                listLenght -= 1;
                go -= 1;
                hp.setLife(go);
                if (go > 0) {
                    hp.repaint();
                }
            }
        }
    }

    public void actionPerformed(ActionEvent e) {
        repaint();
        x += velx;
        y += vely;
        if (x > 1075) {
            x = 1075;
        }
        if (x < 0) {
            x = 0;
        }
    }

    public void right() {
        velx = CRATE_SPEED;
        vely = 0;
    }

    public void left() {
        velx = -CRATE_SPEED;
        vely = 0;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        if (code == KeyEvent.VK_RIGHT) {
            right();
        }
        if (code == KeyEvent.VK_LEFT) {
            left();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    public BeerScheduler getSch() {
        return sch;
    }

    public int getGo() {
        return go;
    }
}
