package SpaceGame;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import static SpaceGame.Game.*;
import static SpaceGame.Game.resetting;

public class Mouse implements MouseListener {
    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        int mx = e.getX();
        int my = e.getY();
        if (Game.STATE == Game.States.menu) {
            if (mx >= 258 && mx <= 378) {
                if (my >= 151 && my <= 211) {
                    Game.STATE = Game.States.game;
                    objects = new ArrayList<GameObject>();
                    for (int i = 0; i < N_INITIAL_ASTEROIDS; i++) {
                        objects.add(new Asteroid());
                    }
                    objects.add(ship);
                    ship.reset();
                    score = 0;
                    remainingAsteroids = N_INITIAL_ASTEROIDS;
                    lives = INITIAL_LIVES;
                    level = 1;
                    shipIsSafe = true;
                    resetting = false;
                }
                /*if (my >= 233 && my <= 292) {
                    Game.STATE = Game.States.game;
                    objects = new ArrayList<GameObject>();
                    for (int i = 0; i < N_INITIAL_ASTEROIDS; i++) {
                        objects.add(new Asteroid());
                    }
                    objects.add(ship);
                    ship.reset();
                    score = 0;
                    remainingAsteroids = N_INITIAL_ASTEROIDS;
                    lives = INITIAL_LIVES * 2;
                    level = 1;
                    shipIsSafe = true;
                    resetting = false;
                }*/
                if (my >= 272 && my <= 332) {
                    STATE = States.scores;
                }
                if (my >= 391 && my <= 451) {
                    System.exit(1);
                }
            }
        }
        else if (STATE == States.scores) {
            if (mx >= 258 && mx <= 378) {
                if (my >= 425 && my <= 485) {
                    STATE = States.menu;
                }
            }

        }

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
