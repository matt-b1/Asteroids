package SpaceGame;

import utilities.JEasyFrame;
import utilities.JEasyFrameFull;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

import static SpaceGame.Constants.FPS;
import static SpaceGame.Constants.setFullScreenDimensions;

public class Game {
    public static final int N_INITIAL_ASTEROIDS = 5;
    public static final int INITIAL_LIVES = 3;
    public static final int INITIAL_SAFETY_DURATION = 3000; // millisecs
    public static boolean shipIsSafe; // within initial safety period?
    public static List<GameObject> objects;
    public static Ship ship;
    Keys ctrl;
    Mouse mouse;
    public static int score, lives, level, remainingAsteroids;
    View view;
    boolean ended;
    long gameStartTime;  // start time for whole game
    long startTime;  // start time for current level/life
    public static boolean resetting;
    public String[] highScore = {"0","0","0","0","0"};
    public static States STATE = States.menu;
    public enum States {
        menu,
        game,
        scores,
        gameover
    };

    public Game(boolean fullScreen) {
        if (fullScreen) setFullScreenDimensions();
        view = new View(this);
        objects = new ArrayList<GameObject>();
        for (int i = 0; i < N_INITIAL_ASTEROIDS; i++) {
            objects.add(new Asteroid());
        }
        ctrl = new Keys();
        mouse = new Mouse();
        ship = new Ship(ctrl);
        JFrame frame = fullScreen ? new JEasyFrameFull(view) : new JEasyFrame(view, "Asteroids");
        frame.setResizable(false);
        frame.addKeyListener(ctrl);
        frame.addMouseListener(mouse);
    }

    public static void main(String[] args) {
        Game game = new Game(false);
        game.gameLoop();
    }

    public void gameLoop() {
        long DTMS = Math.round(1000 / FPS); // delay in millisecs
        gameStartTime = startTime = System.currentTimeMillis();
        while (STATE != null) {
            long time0 = System.currentTimeMillis();
            update();
            view.repaint();
            long timeToSleep = time0 + DTMS - System.currentTimeMillis();
            try {
                Thread.sleep(timeToSleep);
            } catch (Exception e) {
            }
        }
    }

    public void incScore(int inc) {
        score += inc;
    }

    public int getScore() {
        return score;
    }

    public int getLives() {
        return lives;
    }

    public int getLevel() {
        return level;
    }

    public boolean reset(boolean newlevel) {
        objects.clear();
        if (newlevel) level++;
        else
            lives--;
            score -= 100;
        if (lives == 0)
            return false;
        for (int i = 0; i < N_INITIAL_ASTEROIDS + (level - 1) * 5; i++) {
            objects.add(new Asteroid());
        }
        remainingAsteroids = N_INITIAL_ASTEROIDS + (level - 1) * 5;
        ship.reset();
        objects.add(ship);
        try {
            Thread.sleep(2000);
        } catch (Exception e) {
        }
        shipIsSafe = true;
        startTime = System.currentTimeMillis();
        return true;

    }


    public void update() {
        if (STATE == States.game) {
            if (shipIsSafe) {
                shipIsSafe = System.currentTimeMillis() < startTime + INITIAL_SAFETY_DURATION;
            } else
                for (int i = 0; i < objects.size(); i++) {
                    for (int j = i + 1; j < objects.size(); j++) {
                        objects.get(i).collisionHandling(objects.get(j));
                    }
                }
            ended = true;
            List<GameObject> alive = new ArrayList<>();
            for (GameObject o : objects) {
                if (!o.dead) {
                    o.update();
                    alive.add(o);
                }
                else if (o==ship){
                    resetting = true;
                    break;
                }
                else updateScore(o);
            }
            if (ship.bullet != null) {
                alive.add(ship.bullet);
                ship.bullet = null;
            }
            synchronized (Game.class) {
                if (remainingAsteroids==0)
                    reset(true);
                    else if (resetting) {
                    ended = !reset(false);
                    resetting = false;
                }
                else {
                objects.clear();
                for (GameObject o : alive) objects.add(o);
                }
                if (lives == 0) {
                    objects.clear();
                    STATE = States.gameover;
                    if (getScore() > Integer.parseInt(highScore[0])) {
                        for(int i = 4; i > 0; i--) {
                            highScore[i] = highScore[i - 1];
                        }
                        highScore[0] = String.valueOf(getScore());
                    } else if (getScore() < Integer.parseInt(highScore[0]) && (getScore() > Integer.parseInt(highScore[1]))) {
                        for(int i = 4; i > 1; i--) {
                            highScore [i] = highScore [i-1];
                        }
                        highScore [1] = String.valueOf(getScore());
                    }
                    else if (getScore() < Integer.parseInt(highScore[1]) && (getScore() > Integer.parseInt(highScore[2]))) {
                        for(int i = 4; i > 2; i--) {
                            highScore [i] = highScore [i-1];
                        }
                        highScore[2] = String.valueOf(getScore());
                    } else if (getScore() < Integer.parseInt(highScore[2]) && (getScore() > Integer.parseInt(highScore[3]))) {
                        for(int i = 4; i > 3; i--) {
                            highScore [i] = highScore [i-1];
                        }
                        highScore[3] = String.valueOf(getScore());
                    } else if (getScore() < Integer.parseInt(highScore[3]) && (getScore() > Integer.parseInt(highScore[4]))) {
                        highScore[4] = String.valueOf(getScore());
                    }
                }
            }
        }
    }

    public void updateScore(GameObject o) {
        if (o.getClass() == Asteroid.class) {
            score += 100;
            remainingAsteroids -= 1;
        }
    }

}

