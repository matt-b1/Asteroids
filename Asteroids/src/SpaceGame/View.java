package SpaceGame;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;

import static SpaceGame.Constants.*;
import static SpaceGame.Game.STATE;


public class View extends JComponent {
    private Game game;
    Image im = SPACE1;
    AffineTransform bgTransf;
    int topScore;


    public View(Game game) {
        this.game = game;
        double imWidth = im.getWidth(null);
        double imHeight = im.getHeight(null);
        double stretchX = imWidth > FRAME_WIDTH ? 1 : FRAME_WIDTH / imWidth;
        double stretchY = imHeight > FRAME_HEIGHT ? 1 : FRAME_HEIGHT / imHeight;
        bgTransf = new AffineTransform();
        bgTransf.scale(stretchX, stretchY);
    }

    public void paintComponent(Graphics g0) {
        Graphics2D g = (Graphics2D) g0;
        g.drawImage(im, bgTransf, null);
        if ( Game.STATE == Game.States.menu) {
            Rectangle playButton = new Rectangle(Constants.FRAME_WIDTH / 2 - 70, Constants.FRAME_HEIGHT / 2 - 120, 120, 60);
            //public Rectangle coopButton = new Rectangle(Constants.FRAME_WIDTH / 2 - 70, Constants.FRAME_HEIGHT / 2 - 40, 120, 60);
            Rectangle scoreButton = new Rectangle(Constants.FRAME_WIDTH / 2 - 70, Constants.FRAME_HEIGHT / 2, 120, 60);
            Rectangle quitButton = new Rectangle(Constants.FRAME_WIDTH / 2 - 70, Constants.FRAME_HEIGHT / 2 + 120, 120, 60);
            Graphics2D g2d = (Graphics2D) g;
            Font font1 = new Font("arial", Font.BOLD,50);
            g.setFont(font1);
            g.setColor(Color.white);
            g.drawString("Asteroids", Constants.FRAME_WIDTH/2 - 130, Constants.FRAME_HEIGHT/2 - 170);
            Font font2 = new Font("arial", Font.BOLD, 26);
            g.setFont(font2);
            g.drawString("Play", playButton.x + 30, playButton.y + 38);
            //g.drawString("Co-op", coopButton.x + 22, coopButton.y + 38);
            g.drawString("Scores", scoreButton.x + 16, scoreButton.y + 38);
            g.drawString("Quit", quitButton.x + 30, quitButton.y + 38);
            g2d.draw(playButton);
            //g2d.draw(coopButton);
            g2d.draw(scoreButton);
            g2d.draw(quitButton);
        }
        else if (Game.STATE == Game.States.game) {
                synchronized (Game.class) {
                    for (GameObject ob : game.objects) {
                        ob.draw(g);
                }
                g.setColor(Color.white);
                g.drawRect(0, getHeight() - SCORE_PANEL_HEIGHT, getWidth(), SCORE_PANEL_HEIGHT);
                g.setFont(new Font("Dialog", Font.BOLD, 12));
                g.drawString("Score: " + Integer.toString(game.getScore()), 10, getHeight() - SCORE_PANEL_HEIGHT / 3);
                g.drawString("Lives: " + Integer.toString(game.getLives()), 200, getHeight() - SCORE_PANEL_HEIGHT / 3);
                g.drawString("Level: " + Integer.toString(game.getLevel()), 400, getHeight() - SCORE_PANEL_HEIGHT / 3);
            }
        }
        else if (STATE == Game.States.scores) {
            Rectangle backButton = new Rectangle(Constants.FRAME_WIDTH / 2 - 70, Constants.FRAME_HEIGHT / 2 + 150, 120, 60);
            Graphics2D g2d = (Graphics2D) g;
            Font font1 = new Font("Dialog", Font.BOLD, 40);
            g.setFont(font1);
            g.setColor(Color.white);
            g.drawString("High Scores", FRAME_WIDTH/3 - 20, FRAME_HEIGHT/5 - 20);
            g.drawString("1. " + Integer.parseInt(game.highScore[0]), FRAME_WIDTH/3 - 130, FRAME_HEIGHT/5 + 50);
            g.drawString("2. " + Integer.parseInt(game.highScore[1]), FRAME_WIDTH/3 - 130, FRAME_HEIGHT/5 + 100);
            g.drawString("3. " + Integer.parseInt(game.highScore[2]), FRAME_WIDTH/3 - 130, FRAME_HEIGHT/5 + 150);
            g.drawString("4. " + Integer.parseInt(game.highScore[3]), FRAME_WIDTH/3 - 130, FRAME_HEIGHT/5 + 200);
            g.drawString("5. " + Integer.parseInt(game.highScore[4]), FRAME_WIDTH/3 - 130, FRAME_HEIGHT/5 + 250);
            Font font2 = new Font("arial", Font.BOLD, 26);
            g.setFont(font2);
            g.drawString("Back", backButton.x + 30, backButton.y + 38);
            g2d.draw(backButton);
        }
        else if (STATE == Game.States.gameover) {
            game.objects.clear();
            topScore = Integer.parseInt(game.highScore[0]);
            Font font1 = new Font("Dialog", Font.BOLD,50);
            g.setFont(font1);
            g.setColor(Color.white);
            g.drawString("Game Over", Constants.FRAME_WIDTH/3 - 20, Constants.FRAME_HEIGHT/5 - 25);
            g.drawString("Your score was: " + game.getScore(), Constants.FRAME_WIDTH/3 - 130, Constants.FRAME_HEIGHT/5 + 160);
            Font font2 = new Font("Dialog", Font.BOLD,20);
            g.setFont(font2);
            g.drawString("Top score to beat: " + topScore, Constants.FRAME_WIDTH/3 - 10, Constants.FRAME_HEIGHT/5 + 200);
            g.drawString("Press enter to continue...", Constants.FRAME_WIDTH/3 - 20, Constants.FRAME_HEIGHT/5 + 250);
        }
    }

    public Dimension getPreferredSize() {
        return Constants.FRAME_SIZE;
    }
}
