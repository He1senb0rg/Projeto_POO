package pt.iscte.poo.objects;

import pt.iscte.poo.utils.Point2D;
import pt.iscte.poo.utils.Vector2D;

public class DonkeyKong extends Character implements Movable{
    public DonkeyKong(Point2D position) {
        super(position, 100, 100, 25);
    }

    @Override
    public String getName() {
        return "DonkeyKong";
    }

    public void throwBanana(){}

    @Override
    public void move(Point2D direcao) {

    }
}
