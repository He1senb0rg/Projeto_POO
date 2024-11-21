package pt.iscte.poo.objects;

import pt.iscte.poo.utils.Point2D;

public class Hammer extends Pickable{
    private int damage;

    public Hammer(Point2D position) {
        super(position);
        this.damage = 30;
    }

    @Override
    public String getName() {
        return "Hammer";
    }

    @Override
    public void interact(Character character) {
        if (!this.isPicked()) {
            character.gainDamage(damage);
            setPicked(true);
        }
    }
}
