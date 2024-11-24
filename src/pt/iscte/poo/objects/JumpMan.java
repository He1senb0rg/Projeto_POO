package pt.iscte.poo.objects;

import pt.iscte.poo.gui.ImageGUI;
import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.utils.Point2D;
import pt.iscte.poo.utils.Vector2D;

import javax.swing.*;
import java.util.List;

public class JumpMan extends Character implements Movable {

    public JumpMan(Point2D position) {
        super(position, 100, 100, 20);
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

    public ImageTile getTile(List<ImageTile> tiles){
        for (ImageTile tile : tiles) {
            if (!(tile instanceof Background) && tile.getPosition().equals(this.getPosition())) {
                return tile;
            }
        }

        return null;
    }

    @Override
    public void takeDamage(int damage){
        this.setHealth(this.getHealth() - damage);
        ImageGUI.getInstance().setStatusMessage(this.getName() + " took " + damage + " damage! Health: " + getHealth() + " Damage: " + getDamage());

        if (getHealth() <= 0) {
            gameOver();
        }
    }

    private void gameOver() {
        JOptionPane.showMessageDialog(null,
                "JumpMan has died! Try again!",
                "Game Over",
                JOptionPane.INFORMATION_MESSAGE);

        System.exit(0);
    }

    @Override
    public String getName() {
        return "JumpMan";
    }
}
