package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.LinkedList;

import algorithms.Dijkstra;
import model.MazeComponent;
import model.MazeModel;
import model.graph.Edge;
import model.graph.Node;
import view.MazeView;

public class MazeController implements ActionListener {

	MazeModel m;
	MazeView v;

	public MazeController(MazeModel m, MazeView v) {
		this.m = m;
		this.v = v;
		v.generateBtn.addActionListener(this);
		v.executeBtn.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(v.generateBtn)) {

			this.m = new MazeModel(Integer.parseInt(v.sizeTxt.getText()), Integer.parseInt(v.wallTxt.getText()));

			this.v.m = this.m;
			v.initGameBoardFieldLabels();
		}
		if (e.getSource().equals(v.executeBtn)) {

			this.applyDijkstra(v.wallCheckBox.isSelected(), v.moveDiagonalCheckBox.isSelected());
			v.initGameBoardFieldLabels();
		}

	}

	public void translateToNodesAndEdges(Node source, HashSet<Node> nodes, HashSet<Edge> edges,
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

					Node newNode = new Node(source.x - 1, source.y - 1, m.board[source.x - 1][source.y - 1]);
					Edge newEdge = new Edge(source, newNode);
					edges.add(newEdge);
					if (!nodes.contains(newNode))
						translateToNodesAndEdges(newNode, nodes, edges, move_diagonal);

				}
				// upper neighbours
				if (source.y + 1 < m.board.length) {
					Node newNode = new Node(source.x, source.y + 1, m.board[source.x][source.y + 1]);
					Edge newEdge = new Edge(source, newNode);
					edges.add(newEdge);
					if (!nodes.contains(newNode))
						translateToNodesAndEdges(newNode, nodes, edges, move_diagonal);

				}
			}

			// right neighbours
			if (source.x + 1 < m.board.length)

			{
				// lower neighbours
				if (source.y - 1 >= 0) {
					Node newNode = new Node(source.x + 1, source.y - 1, m.board[source.x + 1][source.y - 1]);
					Edge newEdge = new Edge(source, newNode);
					edges.add(newEdge);
					if (!nodes.contains(newNode))
						translateToNodesAndEdges(newNode, nodes, edges, move_diagonal);

				}

				// upper neighbours
				if (source.y + 1 < m.board.length) {
					Node newNode = new Node(source.x + 1, source.y + 1, m.board[source.x + 1][source.y + 1]);
					Edge newEdge = new Edge(source, newNode);
					edges.add(newEdge);
					if (!nodes.contains(newNode))
						translateToNodesAndEdges(newNode, nodes, edges, move_diagonal);

				}
			}
		}

		if (source.y - 1 >= 0) {

			Node newNode = new Node(source.x, source.y - 1, m.board[source.x][source.y - 1]);
			Edge newEdge = new Edge(source, newNode);
			edges.add(newEdge);
			if (!nodes.contains(newNode))
				translateToNodesAndEdges(newNode, nodes, edges, move_diagonal);

		}
		// directly above
		// x und y+1
		if (source.y + 1 < m.board.length) {

			Node newNode = new Node(source.x, source.y + 1, m.board[source.x][source.y + 1]);
			Edge newEdge = new Edge(source, newNode);

			edges.add(newEdge);
			if (!nodes.contains(newNode))
				translateToNodesAndEdges(newNode, nodes, edges, move_diagonal);
		}

		// directly left
		// x-1 und y
		if (source.x - 1 >= 0) {

			Node newNode = new Node(source.x - 1, source.y, m.board[source.x - 1][source.y]);
			Edge newEdge = new Edge(source, newNode);

			edges.add(newEdge);
			if (!nodes.contains(newNode))
				translateToNodesAndEdges(newNode, nodes, edges, move_diagonal);
		}
		// directly right
		// x+1 und y
		if (source.x + 1 < m.board.length) {

			Node newNode = new Node(source.x + 1, source.y, m.board[source.x + 1][source.y]);
			Edge newEdge = new Edge(source, newNode);

			edges.add(newEdge);
			if (!nodes.contains(newNode))
				translateToNodesAndEdges(newNode, nodes, edges, move_diagonal);

		}

	}

	public void applyDijkstra(boolean wallbreak, boolean diagonal) {
		System.out.println("running dykstra with wallbreak: " + wallbreak + " and diagonal: " + diagonal);
		Node root = new Node(0, 0, m.board[0][0]);
		HashSet<Node> nodes = new HashSet<Node>();
		HashSet<Edge> edges = new HashSet<Edge>();
		// nodes.add(root);
		translateToNodesAndEdges(root, nodes, edges, diagonal);
		System.out.println("number of nodes " + nodes.size());
		System.out.println("number of edges " + edges.size());

		Dijkstra d = new Dijkstra(nodes, edges, wallbreak);
		d.execute(root);
		LinkedList<Node> res = d
				.getPath(new Node(m.board.length - 1, m.board.length - 1, m.board[m.board.length - 1][m.board.length - 1]));

		if (res != null)
			for (Node n : res) {
				// System.out.println(n.x + " " + n.y);
				if (m.board[n.x][n.y] == MazeComponent.WALL) {
					System.out.println("BROKE A WALL!");
					m.board[n.x][n.y] = MazeComponent.BROKE_WALL;
				} else if (diagonal) {
					m.board[n.x][n.y] = MazeComponent.TRAVELLED_DIAGONAL;
				}

				else {
					m.board[n.x][n.y] = MazeComponent.TRAVELLED;
				}
			}

	}
}
