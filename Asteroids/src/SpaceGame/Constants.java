package SpaceGame;
import utilities.ImageManager;
import utilities.JEasyFrameFull;

import java.awt.*;
import java.io.IOException;

public class Constants {
    public static int FRAME_HEIGHT = 480;
    public static int FRAME_WIDTH = 640;
    public static final int SCORE_PANEL_HEIGHT = 30;
    public static int WORLD_FACTOR = 2;
    public static int WORLD_WIDTH = WORLD_FACTOR * FRAME_WIDTH;
    public static int WORLD_HEIGHT = WORLD_FACTOR * FRAME_HEIGHT;
    public static final Dimension FRAME_SIZE = new Dimension(FRAME_WIDTH, FRAME_HEIGHT+SCORE_PANEL_HEIGHT);
    public static final int FPS = 40;
    public static final double DT = 1.0/FPS;
    public static Image ASTEROID1, SPACE1;

    static{
        try{
            ASTEROID1 = ImageManager.loadImage("asteroid1");
            SPACE1 = ImageManager.loadImage("space1");

        }
        catch (IOException e){
            e.printStackTrace();

        }
    }

    public static void setFullScreenDimensions() {
        FRAME_HEIGHT = JEasyFrameFull.HEIGHT;
        FRAME_WIDTH = JEasyFrameFull.WIDTH;
        WORLD_WIDTH = WORLD_FACTOR * FRAME_WIDTH;
        WORLD_HEIGHT = WORLD_FACTOR * FRAME_HEIGHT;
    }


}
