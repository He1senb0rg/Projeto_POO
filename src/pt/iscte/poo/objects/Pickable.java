package pt.iscte.poo.objects;

import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.utils.Point2D;

public abstract class Pickable implements ImageTile, Interactive {
    private Point2D position;
    private boolean picked;

    public Pickable(Point2D position) {
        this.position = position;
        this.picked = false;
    }

    public boolean isPicked() {
        return picked;
    }

    public void setPicked(boolean picked) {
        this.picked = picked;
    }

    @Override
    public abstract String getName();

    @Override
    public Point2D getPosition() {
        return position;
    }

    @Override
    public int getLayer() {
        return 2;
    };
}
