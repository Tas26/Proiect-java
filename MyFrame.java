package views;

import javax.sound.sampled.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class MyFrame extends JFrame implements ActionListener, Runnable {

    public static final int FRAME_WIDTH = 1280;
    public static final int FRAME_HEIGHT = 720;

    private GamePanel gp = new GamePanel();
    private final JPanel jp = new JPanel();
    private final JLabel newGame;
    private final JLabel gameOver;
    private Clip bgMusic;

    public MyFrame() throws IOException {

        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        setTitle("CatchGame");
        setResizable(false);

        jp.setBorder(new EmptyBorder(100, 100, 100, 100));
        jp.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.NORTH;

        newGame = new JLabel("<html><h1><strong><i>New Game</i></strong></h1><hr></html>");
        gameOver = new JLabel("<html><h1><strong><i>GAME OVER!</i></strong></h1><hr></html>");

        gameOver.setVisible(false);
        jp.add(gameOver, gbc);
        jp.add(newGame, gbc);

        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JPanel buttons = new JPanel(new GridBagLayout());
        JButton startButton = new JButton("<html><h1><strong>START GAME</strong></h1></html>");
        startButton.addActionListener(this);

        buttons.add(startButton);
        gbc.weighty = 1;
        jp.add(buttons, gbc);

        this.add(jp);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        startGame();
        jp.setVisible(false);
    }

    public void startGame() {
        this.add(gp);
        Thread thread = new Thread(gp.getSch());
        thread.start();

        File file = new File("D:/intellJ/Projekt V3/src/resources/sounds/bgmusic.wav");
        try {
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
            bgMusic = AudioSystem.getClip();
            bgMusic.open(audioStream);
            bgMusic.start();
        } catch (LineUnavailableException | UnsupportedAudioFileException | IOException e) {
            e.printStackTrace();
        }

    }

    public void GameOver() throws IOException {
        this.remove(gp);
        gp = new GamePanel();
        jp.setVisible(true);
    }

    @Override
    public void run() {
        while (true) {
            while (gp.getGo() > 0) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            try {
                GameOver();

                bgMusic.close();

                gameOver.setVisible(true);
                newGame.setVisible(false);

                File file = new File("D:/intellJ/Projekt V3/src/resources/sounds/gameover.wav");
                try {
                    AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
                    Clip clip = AudioSystem.getClip();
                    clip.open(audioStream);
                    clip.start();
                } catch (LineUnavailableException | UnsupportedAudioFileException | IOException e) {
                    e.printStackTrace();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
