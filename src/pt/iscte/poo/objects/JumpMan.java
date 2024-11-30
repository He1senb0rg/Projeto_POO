package pt.iscte.poo.objects;

import pt.iscte.poo.gui.ImageGUI;
import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.utils.Point2D;
import pt.iscte.poo.utils.Vector2D;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class JumpMan extends Character implements Movable {
    private boolean isFalling;

    public JumpMan(Point2D position) {
        super(position, 100, 100, 20);
        this.isFalling = false;
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
            if (!(tile instanceof Background) && !(tile instanceof JumpMan) && !(tile instanceof Stairs) && tile.getPosition().equals(this.getPosition())) {
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

        showHighscores();
        System.exit(0);
    }

    public void showHighscores(){
        System.out.println("Your Highscore: \n");
        List<Highscore> listHighscores = new ArrayList<>();

        try {
            Scanner fileScanner = new Scanner(new File("highscore/highscores.txt"));

            if (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                String name = line.split(": ")[0];
                int score = Integer.parseInt(line.split(": ")[1]);
                listHighscores.add(new Highscore(score, name));
            }

            Collections.sort(listHighscores);

            if (!listHighscores.isEmpty()) {
                for (Highscore highscore : listHighscores) {
                    System.out.println(listHighscores.indexOf(highscore) + ". " + highscore);
                }
            }
            else {
                System.out.println("No highscores found");
            }

            fileScanner.close();
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        if (listHighscores.size() > 10) {
            listHighscores.subList(0, 10);
        }

        saveHighscore(123);
    }

    public void saveHighscore(int score){
        Scanner scanner = new Scanner(System.in);
        System.out.println("What's your name?");
        String name = scanner.nextLine();

        try {
            PrintWriter fileWriter = new PrintWriter("highscore/highscores.txt");
            Highscore highscore = new Highscore(score, name);
            fileWriter.println(highscore);
            fileWriter.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    @Override
    public String getName() {
        return "JumpMan";
    }

    public boolean isFalling() {
        return isFalling;
    }

    public void setFalling(boolean falling) {
        isFalling = falling;
    }
}
