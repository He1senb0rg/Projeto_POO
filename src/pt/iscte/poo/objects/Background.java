package pt.iscte.poo.objects;

import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.utils.Point2D;

public class Background extends Object {
    public Background(Point2D position) {
        super(position);
    }

    @Override
    public String getName() {
        return "Background";
    }

    @Override
    public int getLayer() {
        return 0;
    }


}
