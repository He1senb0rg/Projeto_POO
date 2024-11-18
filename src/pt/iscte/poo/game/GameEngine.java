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
		//recolhe a informação de um ficheiro 'room' e gera a room baseado nesse ficheiro
		readRoomFile("room0.txt");

		gui.addImages(tiles);
	}

	public void readRoomFile(String room){
		//preenche o ecrã com o background
		for (int x = 0; x < 10; x++) {
			for (int y = 0; y < 10; y++) {
				Point2D position = new Point2D(x, y);
				tiles.add(new Background(position));
			}
		}

		try {
			Scanner fileScanner = new Scanner(new File("rooms/" + room));
			String firstLine = fileScanner.nextLine(); // skip first line
			String nextRoom = firstLine.split(";")[1];
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
						case 'P': //princess
							Princess princess = new Princess(position);
							tiles.add(princess);
							break;
						case 's': //sword
							tiles.add(new Sword(position));
							break;
						case 'H': //hammer
							tiles.add(new Hammer(position));
							break;
						case 'm': //meat
							tiles.add(new Meat(position));
							break;
						case '0': //door
							tiles.add(new Door(position, nextRoom));
							break;
						case 't': //trap
							tiles.add(new Trap(position));
							break;
						default:
							System.out.println("Unrecognised character: " + c);
					}
				}
				y++;
			}

			ImageGUI.getInstance().setStatusMessage("Health: " + jumpMan.getHealth() + " Damage: " + jumpMan.getDamage());
			fileScanner.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void keyPressed(int key) {
		Point2D newPosition = null;

		//recolhe a direção para a qual o jogador quer movimentar-se
		if(key == KeyEvent.VK_UP) {
			if (jumpMan.isOnStairs(tiles)) {
				newPosition = jumpMan.getPosition().plus(new Vector2D(0, -1));
			}
		}

		if(key == KeyEvent.VK_RIGHT) {
			if (jumpMan.getPosition().getX() < 9) {
				if(!jumpMan.isOnTopOfNothing(tiles)) {
					newPosition = jumpMan.getPosition().plus(new Vector2D(1, 0));
				}
			}
		}

		if(key == KeyEvent.VK_DOWN) {
			if (jumpMan.isOnStairs(tiles) || jumpMan.isOnTopOfStairs(tiles)) {
				newPosition = jumpMan.getPosition().plus(new Vector2D(0, 1));
			}
		}

		if(key == KeyEvent.VK_LEFT) {
			if (jumpMan.getPosition().getX() > 0) {
				if(!jumpMan.isOnTopOfNothing(tiles)) {
					newPosition = jumpMan.getPosition().plus(new Vector2D(-1, 0));
				}
			}
		}

		//verifica se a posição é válida (se não tem uma parede a frente) e move o jumpMan para essa posição se for válida
		if (newPosition != null && jumpMan.validPosition(tiles, newPosition)) {
			jumpMan.move(new Point2D(newPosition.getX() - jumpMan.getPosition().getX(),
					newPosition.getY() - jumpMan.getPosition().getY()));

			//verifica se o jumpMan foi contra uma trap
			jumpMan.collisionWithTrap(tiles);

			//verifica se o jumpMan foi contra uma carne
			jumpMan.collisionWithMeat(tiles);

			jumpMan.collisionWithSword(tiles);

			jumpMan.collisionWithHammer(tiles);

			//verifica se o jumpMan esta em cima de uma trap
			if (jumpMan.isOnTopOfTrap(tiles)) {
				ImageTile trap = jumpMan.getUnderTile(tiles);
				jumpMan.takeDamage(((Trap) trap).getDamage());
			}
		}

		//se nao tiver chao em baixo do jumpMan ele cai
		if (jumpMan.isOnTopOfNothing(tiles)) {
			jumpManFall(jumpMan, tiles);
		}
	}

	public void jumpManFall(JumpMan jumpMan, List<ImageTile> tiles) {
		Point2D supportTileBellow = jumpMan.nearstSupportTileBellow(tiles);

		if (supportTileBellow != null) {
			jumpMan.move(new Point2D(0, supportTileBellow.getY() - jumpMan.getPosition().getY() - 1));
		}
	}

	public void tick(int ticks) {
		Door door = jumpMan.collisionWithDoor(tiles);

		if (door != null) {
			changeRoom(door.getNextRoom());
		}
	}

	private void changeRoom(String nextRoom) {
		tiles.clear();
		readRoomFile(nextRoom);

		gui.clearImages();
		gui.addImages(tiles);
	}
}
