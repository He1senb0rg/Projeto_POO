package pt.iscte.poo.objects;

import pt.iscte.poo.utils.Point2D;

public class DonkeyKong extends Character{
    public DonkeyKong(Point2D position) {
        super(position);
    }

    @Override
    public String getName() {
        return "DonkeyKong";
    }

    @Override
    public Point2D getPosition() {
        return position;
    }
}
