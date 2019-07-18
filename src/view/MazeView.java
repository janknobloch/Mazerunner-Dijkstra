package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

import model.MazeModel;

public class MazeView {
	public MazeModel m;
	JFrame f;
	JPanel gamePanel;
	JLabel[][] gameBoardFieldLabels;
	public JButton generateBtn;
	public JButton executeBtn;

	public JTextField sizeTxt;
	JLabel sizeFieldLabel;

	public JTextField wallTxt;
	JLabel wallLabel;

	public JCheckBox wallCheckBox;
	public JCheckBox moveDiagonalCheckBox;

	JLabel wallOptionLabel;
	JLabel moveDiagonalLabel;

	public MazeView(MazeModel m) {
		this.m = m;
		this.f = new JFrame();
		this.gamePanel = new JPanel();

		gameBoardFieldLabels = new JLabel[m.board.length][m.board.length];
		gamePanel.setLayout(new GridLayout(m.board.length, m.board.length));
		initGameBoardFieldLabels();

		f.setLayout(new BorderLayout());
		f.add(gamePanel, BorderLayout.CENTER);
		f.setTitle("Dysktra Path Simulator");

		generateBtn = new JButton("generate");

		sizeTxt = new JTextField("50");
		sizeTxt.setMinimumSize(new Dimension(50, 30));
		sizeTxt.setPreferredSize(new Dimension(50, 30));
		sizeFieldLabel = new JLabel("Field Dimensions");

		wallTxt = new JTextField("5");
		wallTxt.setMinimumSize(new Dimension(50, 30));
		wallTxt.setPreferredSize(new Dimension(50, 30));
		wallLabel = new JLabel("Walls percentage");
		executeBtn = new JButton("walk tree");

		wallCheckBox = new JCheckBox();
		wallOptionLabel = new JLabel("breakWalls?");

		moveDiagonalCheckBox = new JCheckBox();
		moveDiagonalLabel = new JLabel("move diagonal?");

		gamePanel.setSize(600, 600);
		gamePanel.setPreferredSize(new Dimension(600, 600));
		f.setSize(800, 600);
		f.setVisible(true);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel buttons = initButtonsPanel();

		f.add(buttons, BorderLayout.SOUTH);
		f.pack();
	}

	private JPanel initButtonsPanel() {
		JPanel buttons = new JPanel();
		buttons.setLayout(new FlowLayout());
		buttons.add(generateBtn);
		buttons.add(sizeFieldLabel);
		buttons.add(sizeTxt);
		buttons.add(wallLabel);
		buttons.add(wallTxt);
		buttons.add(executeBtn);
		buttons.add(wallOptionLabel);
		buttons.add(wallCheckBox);
		buttons.add(moveDiagonalLabel);
		buttons.add(moveDiagonalCheckBox);
		return buttons;
	}

	public void initGameBoardFieldLabels() {

		gameBoardFieldLabels = new JLabel[m.board.length][m.board.length];
		gamePanel.setLayout(new GridLayout(m.board.length, m.board.length));
		gamePanel.removeAll();
		for (int x = 0; x < gameBoardFieldLabels.length; x++) {
			for (int y = 0; y < gameBoardFieldLabels.length; y++) {

				JLabel thumb = new JLabel();

				thumb.setPreferredSize(new Dimension(20, 20));
				thumb.setOpaque(true);
				thumb.setBorder(new LineBorder(Color.DARK_GRAY));
				switch (m.board[x][y]) {
				case WALL:// Wall
					thumb.setBackground(Color.GRAY);
					break;
				case PATH:// Path
					thumb.setBackground(Color.GREEN);
					break;
				case START:// Start
					thumb.setBackground(Color.BLUE);
					break;
				case END:// End
					thumb.setBackground(Color.YELLOW);
					break;
				case TRAVELLED:// Travelled
					thumb.setBackground(Color.MAGENTA);
					break;
				case BROKE_WALL:// Broken wall
					thumb.setBackground(Color.ORANGE);
					break;
				case TRAVELLED_DIAGONAL:// Broken wall
					thumb.setBackground(Color.CYAN);
					break;
				default:
					break;
				}

				gamePanel.add(thumb, x, y);

			}
		}

		
		gamePanel.validate();
		gamePanel.repaint();
		f.validate();
		f.repaint();

	}

}
