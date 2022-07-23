package SpaceGame;

import utilities.SoundManager;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Keys extends KeyAdapter implements Controller {
 Action action;
 public Keys(){
     action = new Action();
 }

    @Override
    public Action action() {
        return action;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (Game.STATE == Game.States.game) {
            switch (key) {
                case KeyEvent.VK_UP:
                    action.thrust = 1;
                    SoundManager.play(SoundManager.thrust);
                    break;
                case KeyEvent.VK_LEFT:
                    action.turn = -1;
                    break;
                case KeyEvent.VK_RIGHT:
                    action.turn = 1;
                    break;
                case KeyEvent.VK_SPACE:
                    action.shoot = true;
                    break;
            }
        }
        if (Game.STATE == Game.States.gameover) {
            switch (key) {
                case KeyEvent.VK_ENTER:
                    Game.STATE = Game.States.menu;
                    break;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        if (Game.STATE == Game.States.game || Game.STATE == Game.States.gameover) {
            switch (key) {
                    case KeyEvent.VK_UP:
                        action.thrust = 0;
                        break;
                    case KeyEvent.VK_LEFT:
                        action.turn = 0;
                        break;
                    case KeyEvent.VK_RIGHT:
                        action.turn = 0;
                        break;
                    case KeyEvent.VK_SPACE:
                        action.shoot = false;
                        break;
            }
        }
    }
}

