package pt.iscte.poo.objects;

import pt.iscte.poo.utils.Point2D;

public class Stairs extends Object {
    public Stairs(Point2D position) {
        super(position);
    }

    @Override
    public String getName() {
        return "Stairs";
    }

    @Override
    public Point2D getPosition() {
        return position;
    }

    @Override
    public int getLayer() {
        return 1;
    }
}
