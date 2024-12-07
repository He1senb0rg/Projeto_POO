package pt.iscte.poo.game;

import pt.iscte.poo.gui.ImageGUI;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class Main {

	public static void main(String[] args) {
		System.out.println("---------------------------------");
		System.out.println("|          Donkey Kong          |");
		System.out.println("|                               |");
		System.out.println("| A princesa precisa de ajuda!  |");
		System.out.println("| Derrota o Donkey Kong para    |");
		System.out.println("| Resgatares a princesa!        |");
		System.out.println("|                               |");
		System.out.println("| Enfrenta vários níveis        |");
		System.out.println("| desafiadores até chegares     |");
		System.out.println("| ao final e recuperares o      |");
		System.out.println("| amor da tua vida!             |");
		System.out.println("|                               |");
		System.out.println("|          BOA SORTE!!!         |");
		System.out.println("---------------------------------");

		ImageGUI gui = ImageGUI.getInstance();
		GameEngine engine = new GameEngine();
		gui.setEngine(engine);
		gui.go();

		//tocar musica de background
		try {
			File arquivo = new File("music/DonkeyKongTheme.wav");
			AudioInputStream audioStream = AudioSystem.getAudioInputStream(arquivo);
			Clip clip = AudioSystem.getClip();
			clip.open(audioStream);
			clip.loop(Clip.LOOP_CONTINUOUSLY); //meter a musica em loop
			clip.start();
		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
			System.out.println("Houve um erro ao reproduzir a musica: " + e.getMessage());
		}

		while(true) {
			gui.update();
		}
	}
}
