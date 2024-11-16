package pt.iscte.poo.objects;

import pt.iscte.poo.utils.Point2D;

public class Hammer extends Pickable{
    public Hammer(Point2D position) {
        super(position);
    }

    @Override
    public String getName() {
        return "Hammer";
    }
}
