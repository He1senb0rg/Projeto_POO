package pt.iscte.poo.objects;

import pt.iscte.poo.utils.Point2D;

public class Sword extends Pickable{
    private int damage;

    public Sword(Point2D position) {
        super(position);
        this.damage = 25;
    }

    @Override
    public String getName() {
        return "Sword";
    }

    @Override
    public void interact(Character character) {
        if (!this.isPicked()) {
            character.gainDamage(damage);
            setPicked(true);
        }
    }
}
