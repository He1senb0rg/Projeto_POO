package pt.iscte.poo.objects;

import pt.iscte.poo.utils.Point2D;

public class Meat extends Pickable{
    public Meat(Point2D position) {
        super(position);
    }

    @Override
    public String getName() {
        return "GoodMeat";
    }
}
