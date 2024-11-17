package pt.iscte.poo.objects;

import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.utils.Point2D;

public class Trap extends Object {
    private int damage;

    public Trap(Point2D position) {
        super(position);
        this.damage = 25;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    @Override
    public String getName() {
        return "Trap";
    }

    @Override
    public int getLayer() {
        return 1;
    }
}
