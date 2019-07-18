package algorithms;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import model.MazeComponent;
import model.graph.Edge;
import model.graph.Node;

public class Dijkstra {

	private Set<Node> nodes = null;
	private Set<Edge> edges = null;
	Set<Node> settledNodes;
	Set<Node> unSettledNodes;
	Map<Node, Node> predecessors;
	Map<Node, Integer> distance; 
	private boolean wallbreak;

	public Dijkstra(Set<Node> nodes, Set<Edge> edge, boolean wallbreak) {
		this.nodes = nodes;
		this.edges = edge;
		this.wallbreak = wallbreak;
	}

	public void execute(Node root) {

		settledNodes = new HashSet<Node>();
		unSettledNodes = new HashSet<Node>();
		distance = new HashMap<Node, Integer>();
		predecessors = new HashMap<Node, Node>();
		distance.put(root, 0);
		unSettledNodes.add(root);
		while (unSettledNodes.size() > 0) {
			Node node = getMinimum(unSettledNodes);
			settledNodes.add(node);
			unSettledNodes.remove(node);
			findMinimalDistances(node);
		}
	}

	/**
	 * finds the minimal distances from adjecent nodes if a new minimum is found the
	 * map is updated with the same node and the new distance value when a minimum
	 * is found it is added to the predecessors list and its target is added to the
	 * unsetteled nodes again.
	 * 
	 * @param node
	 */

	private void findMinimalDistances(Node node) {
		List<Node> adjacentNodes = getNeighbors(node);
		for (Node target : adjacentNodes) {
			if (getShortestDistance(target) > getShortestDistance(node) + getDistance(node, target)) {
				distance.put(target, getShortestDistance(node) + getDistance(node, target));
				predecessors.put(target, node);
				unSettledNodes.add(target);
			}
		}
	}

	/**
	 * based on two given input nodes the cost is looked up by retrieving the cost
	 * from the edge
	 * 
	 * @param source
	 * @param destination
	 * @return
	 */

	private int getDistance(Node node, Node target) {
		for (Edge edge : edges) {
			if (edge.source.equals(node) && edge.destination.equals(target)) {
				return edge.cost;
			}
		}
		return -1;

	}

	/**
	 * retrieves all neighbours for a given source node by checking all edges. If
	 * the node has been found as a source and its destination has not been settled
	 * we add this unresolved neighbour to our list.
	 * 
	 * @param node
	 * @return a list of unresolved nodes as neighbours given of a input source node
	 */

	private List<Node> getNeighbors(Node node) {
		List<Node> neighbors = new ArrayList<Node>();
		for (Edge edge : edges) {
			if (edge.source.equals(node) && !isSettled(edge.destination)) {
				if (edge.destination.mazeComponent == MazeComponent.WALL) {
					if (wallbreak) {
						neighbors.add(edge.destination);
					}
				} else {
					neighbors.add(edge.destination);
				}
			}
		}
		return neighbors;
	}

	/**
	 * For a given set of nodes we are looking the node which has the minimal cost
	 * starting traversing from the root node.
	 * 
	 * @param nodes
	 * @return
	 */
	private Node getMinimum(Set<Node> nodes) {
		Node minimum = null;
		for (Node vertex : nodes) {
			if (minimum == null) {
				minimum = vertex;
			} else {
				if (getShortestDistance(vertex) < getShortestDistance(minimum)) {
					minimum = vertex;
				}
			}
		}
		return minimum;
	}

	/**
	 * checks if a node is part of the settled nodes
	 * 
	 * @param node
	 * @return
	 */
	private boolean isSettled(Node node) {
		return settledNodes.contains(node);
	}

	/**
	 * Looks up the minimum distance from the Root node to to the input node if
	 * there is no distance stored yet, we set the max value, else we return the
	 * distance.
	 * 
	 * @param destination
	 * @return
	 */

	private int getShortestDistance(Node destination) {
		Integer d = distance.get(destination);
		if (d == null) {
			return Integer.MAX_VALUE;
		} else {
			return d;
		}
	}

	/**
	 * Returns a Path of nodes to traverse to reach our destination We start from
	 * our target and traverse backwards by looking up predecessors If we cant find
	 * any predecessors for our initial step we return null if we can find
	 * predecessors we go the way of predecessors as long as we reach our root
	 * element Afterwards we reverse our path list and return it.
	 * 
	 * @param target
	 * @return
	 */
	public LinkedList<Node> getPath(Node target) {
		LinkedList<Node> path = new LinkedList<Node>();
		Node step = target;
		// check if a path exists
		if (predecessors.get(step) == null) {
			return null;
		}
		path.add(step);
		while (predecessors.get(step) != null) {
			step = predecessors.get(step);
			path.add(step);
		}

		Collections.reverse(path);
		return path;
	}

}
