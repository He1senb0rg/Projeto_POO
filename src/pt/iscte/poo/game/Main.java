package pt.iscte.poo.game;

import pt.iscte.poo.gui.ImageGUI;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class Main {

	public static void main(String[] args) {
		StringBuilder strmsg = new StringBuilder();
		strmsg.append("---------------------------------\n");
		strmsg.append("|          Donkey Kong          |\n");
		strmsg.append("|                               |\n");
		strmsg.append("| A princesa precisa de ajuda!  |\n");
		strmsg.append("| Derrota o Donkey Kong para    |\n");
		strmsg.append("| Resgatares a princesa!        |\n");
		strmsg.append("|                               |\n");
		strmsg.append("| Enfrenta vários níveis        |\n");
		strmsg.append("| desafiadores até chegares     |\n");
		strmsg.append("| ao final e recuperares o      |\n");
		strmsg.append("| amor da tua vida!             |\n");
		strmsg.append("|                               |\n");
		strmsg.append("|          BOA SORTE!!!         |\n");
		strmsg.append("---------------------------------\n");

		ImageGUI gui = ImageGUI.getInstance();
		//gui.showMessage("Donkey Kong", strmsg); n funciona
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
