package pt.iscte.poo.objects;

import pt.iscte.poo.gui.ImageGUI;
import pt.iscte.poo.utils.Point2D;

public class Meat extends Pickable{
    private int health;

    public Meat(Point2D position) {
        super(position);
        this.health = 50;
    }

    @Override
    public String getName() {
        return "GoodMeat";
    }

    @Override
    public void interact(Character character) {
        if (!this.isPicked()) {
            character.gainHealth(health);
            setPicked(true);
            ImageGUI.getInstance().setStatusMessage("Jump Man gained +" + health + " health!");
        }
    }
}
