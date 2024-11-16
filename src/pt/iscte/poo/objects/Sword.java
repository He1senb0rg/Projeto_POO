package pt.iscte.poo.objects;

import pt.iscte.poo.utils.Point2D;

public class Sword extends Pickable{
    public Sword(Point2D position) {
        super(position);
    }

    @Override
    public String getName() {
        return "Sword";
    }
}
