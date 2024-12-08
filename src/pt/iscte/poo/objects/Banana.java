package pt.iscte.poo.objects;

import pt.iscte.poo.utils.Point2D;
import pt.iscte.poo.utils.Vector2D;

public class Banana extends Object{
    private int damage;

    public Banana(Point2D position) {
        super(position);
        this.damage = 15;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public void move(Point2D direction) {
        //move a banana para a direcao q recebe como parametro
        Point2D newPosition = this.getPosition().plus(new Vector2D(direction.getX(), direction.getY()));

        this.setPosition(newPosition);
    }

    @Override
    public String getName() {
        return "Banana";
    }

    @Override
    public int getLayer() {
        return 3;
    }
}
