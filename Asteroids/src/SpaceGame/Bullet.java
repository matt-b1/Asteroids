package SpaceGame;

import utilities.Vector2D;

import java.awt.*;


public class Bullet extends GameObject {
    private double lifetime;
    public static final int RADIUS = 2;
    public static final int BULLET_LIFE = 50;

    boolean firedByShip;

    public Bullet(Vector2D pos, Vector2D vel, boolean byShip) {
        super(pos, vel, RADIUS);
        this.lifetime = BULLET_LIFE;
        firedByShip = byShip;
    }

    @Override
    public void update() {
        super.update();
        lifetime -= 1;
        if (lifetime <= 0) dead = true;
    }

    @Override
    public void draw(Graphics2D g)
    {g.setColor(Color.WHITE);
        g.fillOval((int) pos.x-RADIUS, (int) pos.y-RADIUS, 2*RADIUS, 2*RADIUS);
    }

    @Override
    public boolean canHit(GameObject other) {
        return other.getClass() == Ship.class || other.getClass() == Bullet.class;
    }

    @Override
    public void hit() {
        dead = true;

    }
}
