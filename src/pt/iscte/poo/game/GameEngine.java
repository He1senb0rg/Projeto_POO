package pt.iscte.poo.game;

import pt.iscte.poo.gui.ImageGUI;
import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.objects.*;
import pt.iscte.poo.utils.Point2D;
import pt.iscte.poo.utils.Vector2D;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class GameEngine {
	JumpMan jumpMan;
	List<DonkeyKong> listDK = new ArrayList<>();
	Princess princess;
	List<ImageTile> tiles = new ArrayList<>();
	ImageGUI gui = ImageGUI.getInstance();
	int gameEnd;
	int level = 1;

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
			String nextRoom = "";
			int y = 0;

			String firstLine = fileScanner.nextLine(); // skip first line
			if (firstLine.startsWith("#")) {
				nextRoom = firstLine.split(";")[1];
			} else {
				readLine(firstLine, y, "");
				y++;
		}

			while (fileScanner.hasNextLine()) {
				String line = fileScanner.nextLine();
				readLine(line, y, nextRoom);
				y++;
			}

			ImageGUI.getInstance().setStatusMessage("Health: " + jumpMan.getHealth() + " Damage: " + jumpMan.getDamage());
			fileScanner.close();
		} catch (FileNotFoundException e) {
			System.out.println("Houve um erro ao carregar a sala: " + e.getMessage());
		}
	}

	public void readLine(String line, int y, String nextRoom) {
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
					listDK.add(donkeyKong);
					tiles.add(donkeyKong);
					break;
				case 'P': //princess
					princess = new Princess(position);
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
			}
		}
	}

	public void keyPressed(int key) {
		Point2D newPosition = null;

		//recolhe a direção para a qual o jogador quer movimentar-se
		if(key == KeyEvent.VK_UP) {
			if (jumpMan.isOnStairs(tiles)) {
				//guarda posicao q o jumpMan vai para depois verificar se esta posicao é valida
				newPosition = jumpMan.getPosition().plus(new Vector2D(0, -1));
			}
		}

		if(key == KeyEvent.VK_RIGHT) {
			if(!jumpMan.isFalling()) {
				//guarda posicao q o jumpMan vai para depois verificar se esta posicao é valida
				newPosition = jumpMan.getPosition().plus(new Vector2D(1, 0));
			}
		}

		if(key == KeyEvent.VK_DOWN) {
			if (jumpMan.isOnStairs(tiles) || jumpMan.isOnTopOfStairs(tiles)) {
				//guarda posicao q o jumpMan vai para depois verificar se esta posicao é valida
				newPosition = jumpMan.getPosition().plus(new Vector2D(0, 1));
			}
		}

		if(key == KeyEvent.VK_LEFT) {
			if(!jumpMan.isFalling()) {
				//guarda posicao q o jumpMan vai para depois verificar se esta posicao é valida
				newPosition = jumpMan.getPosition().plus(new Vector2D(-1, 0));
			}
		}

		DonkeyKong dkRemove = null;
		for (DonkeyKong dk : listDK) {
			if (dk != null && dk.getPosition().equals(newPosition)) {
				dk.takeDamage(jumpMan.getDamage());
				if (dk.getHealth() <= 0) {
					tiles.remove(dk);
					gui.removeImage(dk);
					dkRemove = dk;
					dk = null;
				}
			}
		}

		if (dkRemove != null) {
			listDK.remove(dkRemove);
		}

		if (princess != null && princess.getPosition().equals(newPosition)){
			gameEnd = ImageGUI.getInstance().getTicks();
			int finalScore = finalScore(10000, 50);

			gui.showMessage("Yippie", "Congratulations! You saved the princess!\nYour score:" + finalScore);

			jumpMan.showHighscores();
			jumpMan.saveHighscore(finalScore);

			System.exit(0);
		}

		//verifica se a posição é válida (se não tem uma parede a frente) e move o jumpMan para essa posição se for válida
		if (newPosition != null && jumpMan.validPosition(tiles, newPosition)) {
			jumpMan.move(new Point2D(newPosition.getX() - jumpMan.getPosition().getX(),
					newPosition.getY() - jumpMan.getPosition().getY()));

			ImageTile newTile = jumpMan.getTile(tiles);

			if (newTile != null) {
				if (newTile instanceof Banana){ // verifica se o jumpMan foi contra uma banana
					jumpMan.takeDamage(((Banana) newTile).getDamage());
					tiles.remove(newTile);
					gui.removeImage(newTile);
				}

				if (newTile instanceof Trap){ //verifica se o jumpMan foi contra uma trap
					jumpMan.takeDamage(((Trap) newTile).getDamage());
				}

				if (newTile instanceof Door){ //verifica se o jumpMan foi contra uma door
					changeRoom(((Door) newTile).getNextRoom());
				}

				if (newTile instanceof Meat){ //verifica se o jumpMan foi contra uma carne
					((Meat) newTile).interact(jumpMan);
					tiles.remove(newTile);
					gui.removeImage(newTile);
				}

				if (newTile instanceof Sword){ //verifica se o jumpMan foi contra uma espada
					((Sword) newTile).interact(jumpMan);
					tiles.remove(newTile);
					gui.removeImage(newTile);
				}

				if (newTile instanceof Hammer){ //verifica se o jumpMan foi contra um martelo
					((Hammer) newTile).interact(jumpMan);
					tiles.remove(newTile);
					gui.removeImage(newTile);
				}
			}

			//verifica se o jumpMan esta em cima de uma trap
			if (jumpMan.isOnTopOfTrap(tiles)) {
				ImageTile trap = jumpMan.getUnderTile(tiles);
				jumpMan.takeDamage(((Trap) trap).getDamage());
			}
		}
	}

	public void tick(int ticks) {
		//se nao tiver chao em baixo do jumpMan ele cai
		if (jumpMan.isOnTopOfNothing(tiles)) {
			Point2D supportTileBellow = jumpMan.nearstSupportTileBellow(tiles);

			jumpMan.setFalling(true);
			jumpMan.move(new Point2D(0, 1));

			if (jumpMan.getPosition().getY() == supportTileBellow.getY() - 1){
				jumpMan.setFalling(false);
			}
		}

		List<ImageTile> removeBananas = new ArrayList<>();
		for (DonkeyKong dk : listDK) {
			if (dk != null) {
				Point2D newDKposition = null;
				if (level <= 6){
					newDKposition = dk.simpleMove(tiles);
				}
				else{
					newDKposition = dk.advancedMove(tiles, jumpMan);
				}


				if (jumpMan.getPosition().equals(newDKposition)) {
					jumpMan.takeDamage(dk.getDamage());
					if (jumpMan.getHealth() <= 0) {
						tiles.remove(jumpMan);
						gui.removeImage(jumpMan);
						jumpMan = null;
					}
				}

				dk.throwBanana(tiles);
			}
		}


		for (ImageTile banana : tiles) {
			if (banana instanceof Banana){
				((Banana) banana).move(new Point2D(0, 1));
				if (banana.getPosition().equals(jumpMan.getPosition())) {
					jumpMan.takeDamage(((Banana) banana).getDamage());
					removeBananas.add(banana);
				}
				if (banana.getPosition().getY() >= 10){
					removeBananas.add(banana);
				}
			}
		}

		for (ImageTile banana : removeBananas) {
			tiles.remove(banana);
			ImageGUI.getInstance().removeImage(banana);
		}

	}

	private void changeRoom(String nextRoom) {
		tiles.clear();
		readRoomFile(nextRoom);
		level++;

		gui.clearImages();
		gui.addImages(tiles);
	}

	private int finalScore(int score, int penaltyPerSec){
		int finalScore = score - (gameEnd * penaltyPerSec);

		return Math.max(finalScore, 0);
	}
}
