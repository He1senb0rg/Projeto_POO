package pt.iscte.poo.objects;

import pt.iscte.poo.gui.ImageGUI;
import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.utils.Point2D;
import pt.iscte.poo.utils.Vector2D;

import javax.swing.*;
import java.util.List;

public abstract class Character implements ImageTile, Movable {
    private Point2D position;
    private int health;
    private int maxHealth;
    private int damage;

    public Character(Point2D position, int health, int maxHealth, int damage) {
        this.position = position;
        this.health = health;
        this.maxHealth = maxHealth;
        this.damage = damage;
    }

    @Override
    public abstract String getName();

    @Override
    public Point2D getPosition(){
        return position;
    }

    public void setPosition(Point2D pos){
        this.position = pos;
    }

    @Override
    public int getLayer() {
        return 2;
    };

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public void takeDamage(int damage) {
        this.health -= damage;
        ImageGUI.getInstance().setStatusMessage(this.getName() + " took " + damage + " damage! Health: " + getHealth() + " Damage: " + getDamage());
    }

    public void gainHealth(int health){
        this.health += health;
        if (this.getHealth() > this.getMaxHealth()){
            this.setHealth(this.getMaxHealth());
        }
        ImageGUI.getInstance().setStatusMessage(this.getName() + " gained +" + health + " health! Health: " + getHealth() + " Damage: " + getDamage());
    }

    public void gainDamage(int damage){
        this.damage += damage;
        ImageGUI.getInstance().setStatusMessage(this.getName() + " gained +" + damage + " damage! Health: " + getHealth() + " Damage: " + getDamage());
    }

    public void move(Point2D direction) {
        Point2D newPosition = this.getPosition().plus(new Vector2D(direction.getX(), direction.getY()));

        this.setPosition(newPosition);
    }

    public boolean validPosition(List<ImageTile> tiles, Point2D position) {
        if (position.getX() < 0 || position.getX() > 9){
            return false;
        }

        for (ImageTile tile : tiles) {
            if ((tile instanceof Wall || tile instanceof Character) && tile.getPosition().equals(position)) {
                return false;
            }
        }

        return true;
    }
}
