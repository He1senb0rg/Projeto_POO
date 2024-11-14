package pt.iscte.poo.objects;

import pt.iscte.poo.utils.Point2D;

public class Door extends Object{
    public Door(Point2D position) {
        super(position);
    }

    @Override
    public String getName() {
        return "DoorOpen";
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
