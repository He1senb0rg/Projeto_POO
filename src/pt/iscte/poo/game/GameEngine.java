package pt.iscte.poo.game;

import pt.iscte.poo.gui.ImageGUI;
import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.objects.*;
import pt.iscte.poo.utils.Point2D;
import pt.iscte.poo.utils.Vector2D;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class GameEngine {

	JumpMan jumpMan = new JumpMan(new Point2D(5, 8));
	DonkeyKong donkeyKong = new DonkeyKong(new Point2D(2, 8));
	List<ImageTile> tiles = new ArrayList<>();
	
	public GameEngine() {
		ImageGUI gui = ImageGUI.getInstance();


		for (int x = 0; x < 10; x++) {
			//Adicionar o background:
			for (int y = 0; y < 10; y++) {
				Point2D position = new Point2D(x, y);
				tiles.add(new Background(position));
			}

			//Adicionar o chão em baixo:
			tiles.add(new Wall(new Point2D(x, 9)));
		}

		tiles.add(new Stairs(new Point2D(7, 8)));
		tiles.add(new Stairs(new Point2D(7, 7)));
		tiles.add(jumpMan);
		tiles.add(donkeyKong);

		gui.addImages(tiles);
	}

	public void keyPressed(int key) {
		if(key == KeyEvent.VK_UP) {
			for (ImageTile tile : tiles){
				if (tile instanceof Stairs && jumpMan.getPosition().equals(tile.getPosition())) {
					jumpMan.move(new Vector2D(0, -1));
				}
			}
		}
		if(key == KeyEvent.VK_RIGHT) {
			if (jumpMan.getPosition().getX() < 9) {
				jumpMan.move(new Vector2D(1, 0));
			}
		}
		if(key == KeyEvent.VK_DOWN) {
			for (ImageTile tile : tiles){
				if ((tile instanceof Stairs && jumpMan.getPosition().equals(tile.getPosition()))) {
					jumpMan.move(new Vector2D(0, 1));
				}
			}
		}
		if(key == KeyEvent.VK_LEFT) {
			if (jumpMan.getPosition().getX() > 0) {
				jumpMan.move(new Vector2D(-1, 0));
			}

		}
	}

	public void tick(int ticks) {
		System.out.println("Tic tac.. " + ticks);
	}

}
