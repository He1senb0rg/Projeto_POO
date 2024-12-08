package pt.iscte.poo.objects;

import pt.iscte.poo.gui.ImageGUI;
import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.utils.Point2D;
import pt.iscte.poo.utils.Vector2D;

import java.util.List;
import java.util.Random;

public class DonkeyKong extends Character implements Movable{
    public DonkeyKong(Point2D position) {
        super(position, 100, 100, 25);
    }

    @Override
    public String getName() {
        return "DonkeyKong";
    }

    public void throwBanana(List<ImageTile> tiles){
        //atirar banana
        Banana banana = new Banana(this.getPosition());
        tiles.add(banana); //adiciona a banana aos tiles do jogo
        ImageGUI.getInstance().addImage(banana);
    }

    public Point2D simpleMove(List<ImageTile> tiles) {
        //movimento do donkey kong nos primeiros niveis
        //movimenta se aleatoriamente, vai de um lado pro outro
        Random random = new Random();
        int directionDK = random.nextInt(2); //random entre 0 e 1
        Point2D newPosition = null;

        //se for 0 dk vai pra esquerda
        if (directionDK == 0) {
            newPosition = this.getPosition().plus(new Vector2D(-1, 0)); //guarda a position q o dk vai se mover

        }
        else { //se for dk vai pra direita
            newPosition = this.getPosition().plus(new Vector2D(1, 0)); //guarda a position q o dk vai se mover
        }

        //valida a position do dk
        if (newPosition != null && this.validPosition(tiles, newPosition)) {
            newPosition = new Point2D(newPosition.getX() - this.getPosition().getX(),
                    newPosition.getY() - this.getPosition().getY());

            this.move(newPosition); //move o dk para essa position
        }

        //da return da position do dk memo se ele não se mexeu para a position q queria pq n estava valida (isto é pra dps verificar a colisao com o jumpMan)
        return this.getPosition().plus(new Vector2D(newPosition.getX(), newPosition.getY()));
    }

    public Point2D advancedMove(List<ImageTile> tiles, JumpMan jumpMan) {
        //movimento do donkey kong nos niveis mais avançados
        //o donkey kong segue o jumpMan
        Point2D newPosition = null;

        //se o jumpMan estiver a direita o dk, mexe o dk para a direita
        if (jumpMan.getPosition().getX() > this.getPosition().getX()) {
            newPosition = this.getPosition().plus(new Vector2D(1, 0));
        }
        //se o jumpMan estiver a esquerda o dk, mexe o dk para a esquerda
        else if (jumpMan.getPosition().getX() < this.getPosition().getX()) {
            newPosition = this.getPosition().plus(new Vector2D(-1, 0));
        }
        ////se o jumpMan estiver mesmo debaixo do dk ent o donkey kong n se mexe
        else {
            newPosition = this.getPosition();
        }

        //valida a posicao do dk
        if (newPosition != null && this.validPosition(tiles, newPosition)) {
            newPosition = new Point2D(newPosition.getX() - this.getPosition().getX(),
                    newPosition.getY() - this.getPosition().getY());

            this.move(newPosition);
        }

        //da return da position do dk memo se ele não se mexeu para a position q queria pq n estava valida (isto é pra dps verificar a colisao com o jumpMan)
        return this.getPosition().plus(new Vector2D(newPosition.getX(), newPosition.getY()));
    }
}
