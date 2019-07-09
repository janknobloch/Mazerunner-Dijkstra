package model;

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

	public void translateToNodesAndEdges(Node source, LinkedList<Node> nodes, LinkedList<Edge> edges,
			boolean move_diagonal) {

//		if (nodes.contains(root))
//			return;
		nodes.add(source);
		// directly below
		// x und y-1

		if (move_diagonal) {
			// left neighbours
			if (source.x - 1 >= 0) {
				// lower neighbours
				if (source.y - 1 >= 0) {

					Node newNode = new Node(source.x - 1, source.y - 1, board[source.x - 1][source.y - 1]);
					Edge newEdge = new Edge(source, newNode);
					edges.add(newEdge);
					if (!nodes.contains(newNode))
						translateToNodesAndEdges(newNode, nodes, edges, move_diagonal);

				}
				// upper neighbours
				if (source.y + 1 < board.length) {
					Node newNode = new Node(source.x, source.y + 1, board[source.x][source.y + 1]);
					Edge newEdge = new Edge(source, newNode);
					edges.add(newEdge);
					if (!nodes.contains(newNode))
						translateToNodesAndEdges(newNode, nodes, edges, move_diagonal);

				}
			}

			// right neighbours
			if (source.x + 1 < board.length)

			{
				// lower neighbours
				if (source.y - 1 >= 0) {
					Node newNode = new Node(source.x + 1, source.y - 1, board[source.x + 1][source.y - 1]);
					Edge newEdge = new Edge(source, newNode);
					edges.add(newEdge);
					if (!nodes.contains(newNode))
						translateToNodesAndEdges(newNode, nodes, edges, move_diagonal);

				}

				// upper neighbours
				if (source.y + 1 < board.length) {
					Node newNode = new Node(source.x + 1, source.y + 1, board[source.x + 1][source.y + 1]);
					Edge newEdge = new Edge(source, newNode);
					edges.add(newEdge);
					if (!nodes.contains(newNode))
						translateToNodesAndEdges(newNode, nodes, edges, move_diagonal);

				}
			}
		}

		if (source.y - 1 >= 0) {

			Node newNode = new Node(source.x, source.y - 1, board[source.x][source.y - 1]);
			Edge newEdge = new Edge(source, newNode);
			edges.add(newEdge);
			if (!nodes.contains(newNode))
				translateToNodesAndEdges(newNode, nodes, edges, move_diagonal);

		}
		// directly above
		// x und y+1
		if (source.y + 1 < board.length) {

			Node newNode = new Node(source.x, source.y + 1, board[source.x][source.y + 1]);
			Edge newEdge = new Edge(source, newNode);

			edges.add(newEdge);
			if (!nodes.contains(newNode))
				translateToNodesAndEdges(newNode, nodes, edges, move_diagonal);
		}

		// directly left
		// x-1 und y
		if (source.x - 1 >= 0) {

			Node newNode = new Node(source.x - 1, source.y, board[source.x - 1][source.y]);
			Edge newEdge = new Edge(source, newNode);

			edges.add(newEdge);
			if (!nodes.contains(newNode))
				translateToNodesAndEdges(newNode, nodes, edges, move_diagonal);
		}
		// directly right
		// x+1 und y
		if (source.x + 1 < board.length) {

			Node newNode = new Node(source.x + 1, source.y, board[source.x + 1][source.y]);
			Edge newEdge = new Edge(source, newNode);

			edges.add(newEdge);
			if (!nodes.contains(newNode))
				translateToNodesAndEdges(newNode, nodes, edges, move_diagonal);

		}

	}

	public void applyDystkra(boolean wallbreak, boolean diagonal) {
		System.out.println("running dykstra with wallbreak"+wallbreak + " and diagonal: "+diagonal);
		Node root = new Node(0, 0, board[0][0]);
		LinkedList<Node> nodes = new LinkedList<Node>();
		LinkedList<Edge> edges = new LinkedList<Edge>();
		// nodes.add(root);
		translateToNodesAndEdges(root, nodes, edges, diagonal);
		System.out.println("number of nodes " + nodes.size());
		System.out.println("number of edges " + edges.size());

		Dijkstra d = new Dijkstra(nodes, edges, wallbreak);
		d.execute(root);
		LinkedList<Node> res = d
				.getPath(new Node(board.length - 1, board.length - 1, board[board.length - 1][board.length - 1]));

		if (res != null)
			for (Node n : res) {
				// System.out.println(n.x + " " + n.y);
				if (board[n.x][n.y] == MazeComponent.WALL) {
					System.out.println("BROKE A WALL!");
					board[n.x][n.y] = MazeComponent.BROKE_WALL;
				} else if (diagonal) {
					board[n.x][n.y] = MazeComponent.TRAVELLED_DIAGONAL;
				}

				else {
					board[n.x][n.y] = MazeComponent.TRAVELLED;
				}
			}

	}

}
