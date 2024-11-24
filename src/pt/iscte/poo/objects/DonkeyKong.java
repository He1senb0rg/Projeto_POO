package pt.iscte.poo.objects;

import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.utils.Point2D;
import pt.iscte.poo.utils.Vector2D;

import java.util.List;
import java.util.Random;

public class DonkeyKong extends Character implements Movable{
    public DonkeyKong(Point2D position) {
        super(position, 100, 100, 25);
    }

    @Override
    public String getName() {
        return "DonkeyKong";
    }

    public Banana throwBanana(){
        return new Banana(this.getPosition());
    }

    public void simpleMove(List<ImageTile> tiles) {
        Random random = new Random();
        int directionDK = random.nextInt(2);
        Point2D newPosition = null;

        if (directionDK == 0) {
            newPosition = this.getPosition().plus(new Vector2D(-1, 0));

        }
        else {
            newPosition = this.getPosition().plus(new Vector2D(1, 0));
        }

        if (newPosition != null && this.validPosition(tiles, newPosition)) {
            newPosition = new Point2D(newPosition.getX() - this.getPosition().getX(),
                    newPosition.getY() - this.getPosition().getY());

            this.move(newPosition);
        }
    }

    public void advancedMove(List<ImageTile> tiles) {

    }
}
