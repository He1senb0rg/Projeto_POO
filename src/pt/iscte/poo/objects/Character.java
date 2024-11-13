package pt.iscte.poo.objects;

import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.utils.Point2D;

public abstract class Character implements ImageTile {
    Point2D position;

    public Character(Point2D position) {
        this.position = position;
    }

    @Override
    public abstract String getName();

    @Override
    public abstract Point2D getPosition();

    @Override
    public int getLayer() {
        return 2;
    };
}
