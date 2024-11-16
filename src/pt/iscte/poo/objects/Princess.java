package pt.iscte.poo.objects;

import pt.iscte.poo.utils.Point2D;
import pt.iscte.poo.utils.Vector2D;

public class Princess extends Character{
    public Princess(Point2D position) {
        super(position);
    }

    @Override
    public String getName() {
        return "Princess";
    }
}
