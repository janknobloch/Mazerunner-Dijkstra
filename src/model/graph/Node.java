package model.graph;
import model.MazeComponent;

public class Node {
	public int x;
	public int y;
	public MazeComponent mazeComponent;

	public Node(int x, int y, MazeComponent mazeComponent) {
		this.x = x;
		this.y = y;
		this.mazeComponent = mazeComponent;
	}

	public int getCost() {
		switch (mazeComponent) {
		case PATH:
			return 1;

		case WALL:
			return 50;

		case START:
			return 0;

		case END:
			return 0;

		}
		return -1;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + x;
		result = prime * result + y;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Node other = (Node) obj;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}
	
	
}
