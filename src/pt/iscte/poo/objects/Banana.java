package pt.iscte.poo.objects;

import pt.iscte.poo.utils.Point2D;

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

    @Override
    public String getName() {
        return "Banana";
    }

    @Override
    public int getLayer() {
        return 2;
    }
}
