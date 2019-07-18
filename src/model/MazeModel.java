package model;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Random;

import algorithms.Dijkstra;
import model.graph.Edge;
import model.graph.Node;

public class MazeModel {
	public MazeComponent[][] board;
	int dimension;
	double wall_percentage;

	public MazeModel(int dimension, int wall_percentage) {
		board = new MazeComponent[dimension][dimension];

		int max = dimension * dimension;
		this.wall_percentage = max * (wall_percentage / 100.00);
		this.dimension = dimension;
		System.out.println("generating maze with: dimension " + dimension + "x" + dimension + " using walls:"
				+ wall_percentage + "%");
		randomBoardInit();
	}

	private void randomBoardInit() {

		for (int x = 0; x < board.length; x++) {
			for (int y = 0; y < board.length; y++) {
				// adding a path everywhere
				board[x][y] = MazeComponent.PATH;

			}
		}

		while (wall_percentage > 0) {
			int xCoord = new Random().nextInt(board.length);
			int yCoord = new Random().nextInt(board.length);

			if (board[xCoord][yCoord] != MazeComponent.WALL) {
				if (wall_percentage > 0) {
					board[xCoord][yCoord] = MazeComponent.WALL;
					wall_percentage--;
				}
			}

		}

		board[0][0] = MazeComponent.START;
		board[board.length - 1][board.length - 1] = MazeComponent.END;

	}

	

}
