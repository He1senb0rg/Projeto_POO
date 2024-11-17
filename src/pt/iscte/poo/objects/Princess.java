package pt.iscte.poo.objects;

import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.utils.Point2D;
import pt.iscte.poo.utils.Vector2D;

public class Princess implements ImageTile {
    private Point2D position;

    public Princess(Point2D position) {
        this.position = position;
    }

    @Override
    public String getName() {
        return "Princess";
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
