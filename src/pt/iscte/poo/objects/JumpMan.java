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
        super(position, 900, 900, 20);
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

    //verifica o tile mais proximo do jumpMan quando ele cai
    public Point2D nearstSupportTileBellow(List<ImageTile> tiles) {
        Point2D position = this.getPosition();

        for (int y = position.getY() + 1; y < 10; y++) { //itera pelas posições abaixo
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

    //saber qual o tile onde o jumpMan esta
    public ImageTile getTile(List<ImageTile> tiles){
        for (ImageTile tile : tiles) {
            if (!(tile instanceof Background) && !(tile instanceof JumpMan) && !(tile instanceof Stairs) && tile.getPosition().equals(this.getPosition())) {
                return tile;
            }
        }

        return null;
    }

    //override o metodo do takeDamage para meter o metodo de gameOver
    @Override
    public void takeDamage(int damage){
        this.setHealth(this.getHealth() - damage);
        ImageGUI.getInstance().setStatusMessage(this.getName() + " took " + damage + " damage! Health: " + getHealth() + " Damage: " + getDamage());

        //mata o jumpMan
        if (getHealth() <= 0) {
            gameOver();
        }
    }

    //fim do jogo
    private void gameOver() {
        ImageGUI.getInstance().showMessage("Game Over", "JumpMan has died! Try again!");
        showHighscores(); //mostra os highscores
        System.exit(0);
    }

    public void showHighscores(){
        List<Highscore> listHighscores = new ArrayList<>();
        StringBuilder strmsg = new StringBuilder();

        //le o ficheiro de highscores
        try {
            Scanner fileScanner = new Scanner(new File("highscore/highscores.txt"));

            //coloca os highscores do ficheiro na lista de highscores
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                String name = line.split(": ")[0];
                int score = Integer.parseInt(line.split(": ")[1]);
                listHighscores.add(new Highscore(score, name));
            }

            //sort dos highscores
            Collections.sort(listHighscores);

            fileScanner.close();
        }
        catch (FileNotFoundException e) { //caso haja algum problema a ler o ficheiro
            System.out.println("Houve um erro ao carregar os highscores: " + e.getMessage());
        }

        //divide a lista para apenas aparecer os primeiros 10 highscores
        if (listHighscores.size() > 10) {
            listHighscores = listHighscores.subList(0, 10);
        }

        //guarda os highscores no strmsg pra dps mostrar no ecra
        if (!listHighscores.isEmpty()) {
            strmsg.append("Top 10 Highscores:\n");
            for (Highscore highscore : listHighscores) {
                strmsg.append(listHighscores.indexOf(highscore) + 1).append(". ").append(highscore).append("\n");
            }
        }
        else { //se não houver highscores
            System.out.println("No highscores found");
        }

        //da display dos highscores
        ImageGUI.getInstance().showMessage("Highscores", strmsg.toString());
    }

    public void saveHighscore(int score){
        List<Highscore> listHighscores = new ArrayList<>();

        //le o ficheiro de highscores
        try {
            Scanner fileScanner = new Scanner(new File("highscore/highscores.txt"));

            //coloca os highscores do ficheiro na lista de highscores
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                String name = line.split(": ")[0];
                int scoreLine = Integer.parseInt(line.split(": ")[1]);
                listHighscores.add(new Highscore(scoreLine, name));
            }

            //sort dos highscores
            Collections.sort(listHighscores);

            fileScanner.close();
        }
        catch (FileNotFoundException e) {//caso haja algum problema a ler o ficheiro
            System.out.println("Houve um erro ao carregar os highscores: " + e.getMessage());
        }

        //pede o nome ao player
        String name = null;

        while (true) {
            name = ImageGUI.getInstance().showInputDialog("Save Highscore", "What's your name?");

            if (name == null || name.isEmpty() || name.contains(":")) {
                ImageGUI.getInstance().showMessage("Error", "Error, invalid name.");
            }
            else {
                break;
            }
        }

        //escreve no ficheiro de highscores
        try {
            PrintWriter fileWriter = new PrintWriter("highscore/highscores.txt");
            Highscore highscore = new Highscore(score, name);
            listHighscores.add(highscore);

            //sort dos highscores
            Collections.sort(listHighscores);

            //divide a lista para apenas aparecer os primeiros 10 highscores
            if (listHighscores.size() > 10) {
                listHighscores = listHighscores.subList(0, 10);
            }

            //escreve no ficheiro os highscores
            for (Highscore h : listHighscores) {
                fileWriter.println(h);
            }

            fileWriter.close();
        } catch (FileNotFoundException e) {//caso haja algum problema a escrever o ficheiro
            System.out.println("Houve um erro ao escrever os highscores: " + e.getMessage());
        }

        showHighscores();
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
