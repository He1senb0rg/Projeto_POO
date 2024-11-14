package pt.iscte.poo.game;

import pt.iscte.poo.gui.ImageGUI;
import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.objects.*;
import pt.iscte.poo.utils.Point2D;
import pt.iscte.poo.utils.Vector2D;

import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GameEngine {

	JumpMan jumpMan;
	List<ImageTile> tiles = new ArrayList<>();
	ImageGUI gui = ImageGUI.getInstance();
	
	public GameEngine() {
		readRoomFile("room0");

		//background
		for (int x = 0; x < 10; x++) {
			for (int y = 0; y < 10; y++) {
				Point2D position = new Point2D(x, y);
				tiles.add(new Background(position));
			}
		}

		gui.addImages(tiles);
	}

	public void readRoomFile(String room){
		try {
			Scanner fileScanner = new Scanner(new File("rooms/" + room + ".txt"));
			String nextRoom = fileScanner.nextLine(); // skip first line
			int y = 0;

			while (fileScanner.hasNextLine()) {
				String line = fileScanner.nextLine();
				for (int x = 0; x < line.length(); x++) {
					char c = line.charAt(x);

					Point2D position = new Point2D(x, y);

					switch (c) {
						case 'W': //wall
							tiles.add(new Wall(position));
							break;
						case 'S': //stairs
							tiles.add(new Stairs(position));
							break;
						case 'J': //jumpMan
							jumpMan = new JumpMan(position);
							tiles.add(jumpMan);
							break;
						case 'G': //donkeyKong
							DonkeyKong donkeyKong = new DonkeyKong(position);
							tiles.add(donkeyKong);
							break;
						case 's': //sword

							break;
						case '0': //door
							tiles.add(new Door(position));
							break;
						case 'T': //trap

							break;
						default:
							System.out.println("Unrecognised character: " + c);
					}
				}
				y++;
			}

			fileScanner.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
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
