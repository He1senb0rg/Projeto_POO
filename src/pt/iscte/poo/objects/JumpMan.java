package pt.iscte.poo.objects;

import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.utils.Point2D;
import pt.iscte.poo.utils.Vector2D;

public class JumpMan extends Character {
    public JumpMan(Point2D position) {
        super(position);
    }

    public void move(Vector2D direction) {
        this.position = position.plus(direction);
    }

    @Override
    public String getName() {
        return "JumpMan";
    }

    @Override
    public Point2D getPosition() {
        return position;
    }
}
