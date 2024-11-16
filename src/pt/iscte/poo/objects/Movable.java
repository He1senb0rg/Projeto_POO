package pt.iscte.poo.objects;

import pt.iscte.poo.utils.Point2D;
import pt.iscte.poo.utils.Vector2D;

public interface Movable {
    void move(Point2D direcao);
    Point2D getPosition();
    void setPosition(Point2D position);
}
