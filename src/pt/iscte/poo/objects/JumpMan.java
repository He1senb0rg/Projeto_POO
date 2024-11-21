package pt.iscte.poo.objects;

import pt.iscte.poo.gui.ImageGUI;
import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.utils.Point2D;
import pt.iscte.poo.utils.Vector2D;

import java.util.List;

public class JumpMan extends Character implements Movable {

    public JumpMan(Point2D position) {
        super(position, 100, 100, 20);
    }

    public void move(Point2D direction) {
        Point2D newPosition = this.getPosition().plus(new Vector2D(direction.getX(), direction.getY()));

        this.setPosition(newPosition);
    }

    public boolean isOnTopOfStairs(List<ImageTile> tiles){
        ImageTile underJumpManTile = getUnderTile(tiles);

        //verifica se o tile abaixo do jumpMan é uma escada
        if (underJumpManTile instanceof Stairs) {
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
        ImageTile underJumpManTile = getUnderTile(tiles);

        //verifica se existe um tile debaixo do jumpMan
        if (!(underJumpManTile instanceof Background)) {
            return false;
        }

        return true;
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

    public void collisionWithTrap(List<ImageTile> tiles) {
        for (ImageTile tile : tiles) {
            if (tile instanceof Trap && tile.getPosition().equals(this.getPosition())) {
                this.takeDamage(((Trap) tile).getDamage());
            }
        }
    }

    public boolean isOnTopOfTrap(List<ImageTile> tiles){
        ImageTile underJumpManTile = getUnderTile(tiles);

        //verifica se o tile abaixo do jumpMan é uma trap
        if (underJumpManTile instanceof Trap) {
            return true;
        }

        return false;
    }

    public ImageTile getUnderTile(List<ImageTile> tiles){
        Point2D underJumpMan = this.getPosition().plus(new Vector2D(0, 1)); //posicao abaixo do jumpMan
        ImageTile underJumpManTile = null;

        for (ImageTile tile : tiles) {
            //verifica o tile abaixo do jumpMan
            if (tile.getPosition().equals(underJumpMan)) {
                underJumpManTile = tile;
            }
        }

        return underJumpManTile;
    }

    public void collisionWithMeat(List<ImageTile> tiles) {
        for (ImageTile tile : tiles) {
            if (tile instanceof Meat && tile.getPosition().equals(this.getPosition())) {
                ((Meat) tile).interact(this);

                // Remove a carne do jogo após ser consumida
                tiles.remove(tile);
                ImageGUI.getInstance().removeImage(tile);
                break;
            }
        }
    }

    public void collisionWithSword(List<ImageTile> tiles) {
        for (ImageTile tile : tiles) {
            if (tile instanceof Sword && tile.getPosition().equals(this.getPosition())) {
                ((Sword) tile).interact(this);

                // Remove a espada do jogo após ser picked up
                tiles.remove(tile);
                ImageGUI.getInstance().removeImage(tile);
                break;
            }
        }
    }

    public void collisionWithHammer(List<ImageTile> tiles) {
        for (ImageTile tile : tiles) {
            if (tile instanceof Hammer && tile.getPosition().equals(this.getPosition())) {
                ((Hammer) tile).interact(this);

                // Remove o hammer do jogo após ser picked up
                tiles.remove(tile);
                ImageGUI.getInstance().removeImage(tile);
                break;
            }
        }
    }

    @Override
    public String getName() {
        return "JumpMan";
    }
}
