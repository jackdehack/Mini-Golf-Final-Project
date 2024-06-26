import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Label;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public class MenuPanel extends JPanel {
	private Button startButton;
	private Button[] difficultyButtons;
	private Button[] levelButtons;
	BufferedImage MenuBG;
	Image superStar;
	int swings;
	Label totalSwings;
	BufferedImage emptyStar;
	BufferedImage fullStar;
	int[] levelScores = { 0, 0, 0, 0, 0 };
	


	BufferedImage[][] holeStars = new BufferedImage[5][3];

	public MenuPanel() {
		setLayout(null);
		// load the superstar image
		superStar = Toolkit.getDefaultToolkit().createImage(new File("superStar.gif").getAbsolutePath());

		// load the menu background image
		try {
			MenuBG = ImageIO.read(new File("MenuBG.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			emptyStar = ImageIO.read(new File("emptyStar.png"));
			fullStar = ImageIO.read(new File("fullStar.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		startButton = new Button("Start");
		startButton.setBounds(310, 440, 100, 50);
		add(startButton);
		// Difficulty buttons
		String[] difficulties = { "Easy", "Medium", "Hard" };
		difficultyButtons = new Button[3];
		for (int i = 0; i < difficulties.length; i++) {
			difficultyButtons[i] = new Button(difficulties[i]);
			difficultyButtons[i].setBounds(220 + i * 100, 60, 80, 50);
			add(difficultyButtons[i]);
		}
		// Level buttons
		levelButtons = new Button[5];
		for (int i = 0; i < 5; i++) {
			levelButtons[i] = new Button("Level " + (i + 1));
			levelButtons[i].setBounds(120 + (i % 5) * 100, 700, 80, 30);
			add(levelButtons[i]);
		}

		totalSwings = new Label("Total swings: " + swings);
		totalSwings.setBounds(310, 500, 100, 20);
		add(totalSwings);
	}

	public void refreshData() {
		remove(totalSwings);
		totalSwings = new Label("Total swings: " + swings);
		totalSwings.setBounds(310, 500, 100, 20);
		add(totalSwings);
	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		

		g.drawImage(MenuBG, 0 , 0, 720, 1280, null);
		
		for (int i = 0; i < levelButtons.length; i++) {
			int swingsForLevel = LevelHandler.holeSwings[i];
			int parForLevel = getParForLevel(i + 1);
			levelScores[i] = LevelHandler.calculateHoleStars(swingsForLevel, parForLevel);
		}

		for (int i = 0; i < levelButtons.length; i++) {
			int x = 129 + (i % 5) * 100;
			int y = 738;
			drawStars(g, x, y, levelScores[i]);
		}
		
	}
	// drawing stars logic
	private void drawStars(Graphics g, int x, int y, int score) {
		if(score == 4) {
			for (int i = 0; i < 3; i++) {
			g.drawImage(superStar, x + i * 20, y, 20, 20, this);
			}
			
		}
		
		if (score <= 3) {
			for (int i = 0; i < 3; i++) {
				if (i < score) {
					g.drawImage(fullStar, x + i * 20, y, 20, 20, null);
				} else {
					g.drawImage(emptyStar, x + i * 20, y, 20, 20, null);
				}
			}
		}
	}
	// get the par value for a given level
	private int getParForLevel(int level) {
		switch (level) {
		case 1:
			return 2;
		case 2:
			return 3;
		case 3:
			return 4;
		case 4:
			return 5;
		case 5:
			return 5;
		default:
			return 3;
		}
	}

	public Button getStartButton() {
		return startButton;
	}

	public Button[] getDifficultyButtons() {
		return difficultyButtons;
	}

	public Button[] getLevelButtons() {
		return levelButtons;
	}
}
