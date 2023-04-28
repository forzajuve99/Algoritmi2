package upo.graph.impl;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.Set;
import upo.graph.base.Graph;
import upo.graph.base.VisitForest;
import upo.graph.base.WeightedGraph;
import upo.graph.base.VisitForest.Color;
import upo.graph.base.VisitForest.VisitType;

public class AdjListUndir implements upo.graph.base.Graph
{
	protected static class Vertex
	{
		protected String label;
		protected double weight;

		public Vertex(String label)
		{
			this.label = label;
			this.weight = WeightedGraph.defaultEdgeWeight;
		}
	}

	protected Map<Vertex, Set<Vertex>> adjacencyList;

	public AdjListUndir()
	{
		this.adjacencyList = new HashMap<>();
	}
	
	@Override
	public int getVertexIndex(String label)
	{
		int i = 0;

		for (Vertex key : this.adjacencyList.keySet())
		{
			if (key.label.equals(label))
			{
				return i;
			}

			i++;
		}

		return -1;
	}

	@Override
	public String getVertexLabel(Integer index)
	{
		int i = 0;

		for (Vertex key : this.adjacencyList.keySet())
		{
			if (i == index)
			{
				return key.label;
			}

			i++;
		}

		return null;
	}

	@Override
	public int addVertex(String label)
	{
		this.adjacencyList.put(new Vertex(label), new HashSet<>());

		return this.size() - 1;
	}

	@Override
	public boolean containsVertex(String label)
	{
		return this.adjacencyList.containsKey(new Vertex(label));
	}

	@Override
	public void removeVertex(String label) throws NoSuchElementException
	{
		Vertex vertex = new Vertex(label);

		if (!this.adjacencyList.containsKey(vertex))
		{
			throw new NoSuchElementException("Vertice non appartiene al grafo");
		}

		this.adjacencyList.remove(vertex);
	}

	@Override
	public void addEdge(String sourceVertex, String targetVertex) throws IllegalArgumentException
	{
		if (!this.containsVertex(sourceVertex) || !this.containsVertex(targetVertex))
		{
			throw new IllegalArgumentException("Uno dei due vertici non appartiene al grafo");
		}
		Vertex sV = new Vertex(sourceVertex);
		Vertex tV = new Vertex(targetVertex);

		this.adjacencyList.get(sV).add(tV);
		this.adjacencyList.get(tV).add(sV);
	}

	@Override
	public boolean containsEdge(String sourceVertex, String targetVertex)
			throws IllegalArgumentException
	{
		if (!this.containsVertex(sourceVertex) || !this.containsVertex(targetVertex))
		{
			throw new IllegalArgumentException("Uno dei due vertici non appartiene al grafo");
		}

		return this.adjacencyList.get(new Vertex(sourceVertex)).contains(new Vertex(targetVertex));
	}

	@Override
	public void removeEdge(String sourceVertex, String targetVertex)
			throws IllegalArgumentException, NoSuchElementException
	{
		if (!this.containsEdge(sourceVertex, targetVertex))
		{
			throw new NoSuchElementException("L'arco non appartiene al grafo");
		}

		Vertex sV = new Vertex(sourceVertex);
		Vertex tV = new Vertex(targetVertex);

		this.adjacencyList.get(sV).remove(tV);
		this.adjacencyList.get(tV).remove(sV);
	}

	@Override
	public Set<String> getAdjacent(String vertex) throws NoSuchElementException
	{
		if (!containsVertex(vertex))
		{
			throw new NoSuchElementException("Vertice " + vertex + " non appartiene al grafo");
		}

		Set<String> adjLabels = new HashSet<String>();

		for (Vertex adjVertex : this.adjacencyList.get(new Vertex(vertex)))
		{
			adjLabels.add(adjVertex.label);
		}

		return adjLabels;
	}

	@Override
	public boolean isAdjacent(String targetVertex, String sourceVertex)
			throws IllegalArgumentException
	{
		return this.containsEdge(sourceVertex, targetVertex);
	}

	@Override
	public int size()
	{
		return this.adjacencyList.size();
	}

	@Override
	public boolean isDirected()
	{
		return false;
	}

	@Override
	public boolean isCyclic()
	{
		VisitForest cyForest = new VisitForest(this, VisitForest.VisitType.DFS_TOT);
		for (Vertex key : this.adjacencyList.keySet())
		{
			if (cyForest.getColor(key.label) == VisitForest.Color.WHITE
					&& ricIsCyclic(key.label, cyForest))
				return true;
		}
		return false;
	}

	protected boolean ricIsCyclic(String key, VisitForest cyForest)
	{
		cyForest.setColor(key, Color.GRAY);
		for (String k : getAdjacent(key))
		{
			if (cyForest.getColor(key) == VisitForest.Color.WHITE)
			{
				cyForest.setParent(k, key);
				if (ricIsCyclic(k, cyForest))
					return true;
			}
			else if (k != cyForest.getPartent(key))
				return true;
		}
		cyForest.setColor(key, VisitForest.Color.BLACK);
		return false;
	}

	@Override
	public boolean isDAG()
	{
		return isCyclic() && isDirected();
	}

	@Override
	public VisitForest getBFSTree(String startingVertex)
			throws UnsupportedOperationException, IllegalArgumentException
	{
		if (!containsVertex(startingVertex))
		{
			throw new IllegalArgumentException("Il vertice non appartiene al grafo");
		}

		Queue<String> queue = new LinkedList<String>();
		VisitForest forest = new VisitForest(this, VisitType.BFS);
		forest.setColor(startingVertex, VisitForest.Color.GRAY);
		forest.setDistance(startingVertex, 0);
		queue.add(startingVertex);
		while (!queue.isEmpty())
		{
			String label = queue.peek();
			Set<String> adjLabel = getAdjacent(label);
			for (String key : adjLabel)
			{
				if (forest.getColor(key) == VisitForest.Color.WHITE)
				{
					forest.setColor(key, VisitForest.Color.GRAY);
					forest.setParent(key, label);
					queue.add(key);
				}
			}

			forest.setColor(label, VisitForest.Color.BLACK);
			queue.remove();
		}

		return forest;
	}

	@Override
	public VisitForest getDFSTree(String startingVertex)
			throws UnsupportedOperationException, IllegalArgumentException
	{
		if (!containsVertex(startingVertex))
		{
			throw new IllegalArgumentException("Il vertice non appartiene al grafo");
		}

		int time = 0;
		VisitForest forest = new VisitForest(this, VisitForest.VisitType.DFS);
		ricGetDFSForest(forest, startingVertex, time);
		return forest;

	}

	private void ricGetDFSForest(VisitForest forest, String startingVertex, int time)
	{
		forest.setColor(startingVertex, VisitForest.Color.GRAY);
		forest.setStartTime(startingVertex, time);
		time++;
		Set<String> adjVertices = getAdjacent(startingVertex);
		for (String label : adjVertices)
		{
			if (forest.getColor(label) == VisitForest.Color.WHITE)
			{
				forest.setParent(label, startingVertex);
				ricGetDFSForest(forest, startingVertex, time);
			}
		}

		forest.setColor(startingVertex, VisitForest.Color.BLACK);
		forest.setEndTime(startingVertex, time);
		time++;
	}


	@Override
	public VisitForest getDFSTOTForest(String startingVertex)
			throws UnsupportedOperationException, IllegalArgumentException
	{
		ArrayList<String> vertices = new ArrayList<>();
		vertices.add(0, startingVertex);

		Set<Vertex> keys = new HashSet<>(adjacencyList.keySet());
		keys.remove(startingVertex);

		for (Vertex key : keys)
		{
			vertices.add(key.label);
		}

		return getDFSTOTForest((String[]) vertices.toArray());

	}

	@Override
	public VisitForest getDFSTOTForest(String[] vertexOrdering)
			throws UnsupportedOperationException, IllegalArgumentException
	{
		VisitForest forest = new VisitForest(this, VisitForest.VisitType.DFS_TOT);
		int time = 0;

		for (String vertex : vertexOrdering)
		{
			if (!containsVertex(vertex))
			{
				throw new IllegalArgumentException("Il vertice non appartiene al grafo");
			}


			ricGetDFSForest(forest, vertex, time);
		}

		return forest;
	}

	@Override
	public String[] topologicalSort() throws UnsupportedOperationException
	{
		throw new UnsupportedOperationException(
				"Il grafo non risulta essere un DAG, metodo topologicalSort() non supportato!");
	}

	@Override
	public Set<Set<String>> stronglyConnectedComponents() throws UnsupportedOperationException
	{
		throw new UnsupportedOperationException(
				"Il grafo non risulta essere orientato, metodo stronglyConnectedComponents() non supportato!");
	}

	@Override
	public Set<Set<String>> connectedComponents() throws UnsupportedOperationException
	{
		Set<Set<String>> cc = new HashSet<Set<String>>();
		VisitForest forest = new VisitForest(this, VisitForest.VisitType.BFS);
		for (Vertex key : adjacencyList.keySet())
		{
			if (forest.getColor(key.label) == VisitForest.Color.WHITE)
			{
				forest = getBFSTree(key.label);
				Set<String> ccTemp = new HashSet<String>();
				for (Vertex key2 : adjacencyList.keySet())
				{
					if (forest.getColor(key2.label) == VisitForest.Color.BLACK)
						ccTemp.add(key2.label);
				}
				cc.add(ccTemp);
			}
		}

		return cc;
	}

	@Override
	public boolean equals(Object o)
	{
		if (!(o instanceof Graph))
			return false;
		if (o instanceof WeightedGraph)
			return false;
		Graph object = (Graph) o;
		if (this.size() == object.size())
		{
			for (Vertex key : adjacencyList.keySet())
			{
				Set<String> adjo = this.getAdjacent(key.label);
				Set<String> adjObject = object.getAdjacent(key.label);
				if (!(adjo.equals(adjObject)))
					return false;
			}
			return true;
		}
		else
		{
			return false;
		}
	}

}
