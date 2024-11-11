package pt.iscte.poo.game;

import pt.iscte.poo.gui.ImageGUI;
import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.objects.Background;
import pt.iscte.poo.objects.JumpMan;
import pt.iscte.poo.objects.Wall;
import pt.iscte.poo.utils.Point2D;
import pt.iscte.poo.utils.Vector2D;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class GameEngine {

	JumpMan jumpMan = new JumpMan(new Point2D(5, 8));
	
	public GameEngine() {
		ImageGUI gui = ImageGUI.getInstance();
		List<ImageTile> tiles = new ArrayList<>();

		for (int x = 0; x < 10; x++) {
			//Adicionar o background:
			for (int y = 0; y < 10; y++) {
				Point2D position = new Point2D(x, y);
				tiles.add(new Background(position));
			}

			//Adicionar o chão em baixo:
			tiles.add(new Wall(new Point2D(x, 9)));
		}


		tiles.add(jumpMan);

		gui.addImages(tiles);
	}

	public void keyPressed(int key) {
		if(key == KeyEvent.VK_UP) {
			System.out.println("User pressed UP");
		}
		if(key == KeyEvent.VK_RIGHT) {
			System.out.println("User pressed RIGHT");
		}
		if(key == KeyEvent.VK_DOWN) {
			System.out.println("User pressed DOWN");
		}
		if(key == KeyEvent.VK_LEFT) {
			System.out.println("User pressed LEFT");
		}
	}

	public void tick(int ticks) {
		System.out.println("Tic tac.. " + ticks);
	}

}
