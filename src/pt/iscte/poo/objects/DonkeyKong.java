package pt.iscte.poo.objects;

import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.utils.Point2D;
import pt.iscte.poo.utils.Vector2D;

import java.util.List;

public class DonkeyKong extends Character implements Movable{
    public DonkeyKong(Point2D position) {
        super(position, 100, 25);
    }

    @Override
    public String getName() {
        return "DonkeyKong";
    }

    public Banana throwBanana(List<ImageTile> tiles){
        Banana banana = new Banana(this.getPosition());
        tiles.add(banana);
        return banana;
    }

    @Override
    public void move(Point2D direcao) {

    }
}
