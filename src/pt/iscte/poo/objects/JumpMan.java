package pt.iscte.poo.objects;

import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.utils.Point2D;
import pt.iscte.poo.utils.Vector2D;

import java.util.List;

public class JumpMan extends Character implements Movable {
    private int health;
    private int damage;

    public JumpMan(Point2D position) {
        super(position);
        this.health = 100;
        this.damage = 20;
    }

    public void move(Point2D direction) {
        Point2D newPosition = this.getPosition().plus(new Vector2D(direction.getX(), direction.getY()));

        this.setPosition(newPosition);
    }

    public boolean isOnTopOfStairs(List<ImageTile> tiles){
        Point2D underJumpMan = this.getPosition().plus(new Vector2D(0, 1)); //posicao abaixo do jumpMan
        ImageTile underJumpManTile = null;

        for (ImageTile tile : tiles) {
            //verifica se o tile abaixo do jumpMan é uma escada
            if (tile instanceof Stairs && tile.getPosition().equals(underJumpMan)) {
                underJumpManTile = tile;
            }
        }

        if (underJumpManTile != null) {
            return true;
        }

        return false;
    }

    public boolean isOnStairs(List<ImageTile> tiles) {
        for (ImageTile tile : tiles) {
            //verifica se o tile onde o jumpMan está é uma escada
            if (tile instanceof Stairs && tile.getPosition().equals(this.getPosition())) {
                return true;
            }
        }

        return false;
    }

    public boolean validPosition(List<ImageTile> tiles, Point2D position) {
        for (ImageTile tile : tiles) {
            if (tile instanceof Wall && tile.getPosition().equals(position)) {
                return false;
            }
        }

        return true;
    }

    public boolean isOnTopOfNothing(List<ImageTile> tiles) {
        Point2D underJumpMan = this.getPosition().plus(new Vector2D(0, 1)); //posicao abaixo do jumpMan
        ImageTile underJumpManTile = null;

        for (ImageTile tile : tiles) {
            //verifica se existe um tile abaixo do jumpMan
            if (!(tile instanceof Background) && tile.getPosition().equals(underJumpMan)) {
                underJumpManTile = tile;
            }
        }

        if (underJumpManTile == null) {
            return true;
        }

        return false;
    }

    public Point2D nearstSupportTileBellow(List<ImageTile> tiles) {
        Point2D position = this.getPosition();

        for (int y = position.getY() + 1; y < 10; y++) { // Itera pelas posições abaixo
            Point2D positionBellow = new Point2D(position.getX(), y);

            for (ImageTile tile : tiles) {
                if ((tile instanceof Wall || tile instanceof Stairs|| tile instanceof Trap) && tile.getPosition().equals(positionBellow)) {
                    return positionBellow;
                }
            }
        }

        return null;
    }

    public Door collisionWithDoor(List<ImageTile> tiles) {
        for (ImageTile tile : tiles) {
            if (tile instanceof Door && tile.getPosition().equals(this.getPosition())) {
                return (Door) tile;
            }
        }
        return null;
    }

    @Override
    public String getName() {
        return "JumpMan";
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }
}
