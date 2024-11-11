package pt.iscte.poo.objects;

import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.utils.Point2D;

public class JumpMan implements ImageTile {
    private Point2D position;

    public JumpMan(Point2D position) {
        this.position = position;
    }

    @Override
    public String getName() {
        return "JumpMan";
    }

    @Override
    public Point2D getPosition() {
        return position;
    }

    @Override
    public int getLayer() {
        return 2;
    }
}
