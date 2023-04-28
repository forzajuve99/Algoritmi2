package upo.graph.impl;

import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;

import upo.graph.base.VisitForest;
import upo.graph.base.WeightedGraph;

public class AdjListUndirWeight extends AdjListUndir implements upo.graph.base.WeightedGraph
{
	public AdjListUndirWeight()
	{
		super();
	}



	@Override
	public double getEdgeWeight(String sourceVertex, String targetVertex)
			throws IllegalArgumentException, NoSuchElementException
	{
		if (!containsVertex(sourceVertex) || !containsVertex(targetVertex))
			throw new IllegalArgumentException("Il vertice " + sourceVertex + " e/o " + targetVertex
					+ " non appartengono al grafo!");
		if (!containsEdge(sourceVertex, targetVertex))
			throw new NoSuchElementException(
					"L'arco " + sourceVertex + "-" + targetVertex + " non appartiene al grafo! ");

		double peso = 0;
		Set<Vertex> keys = new HashSet<>(adjacencyList.keySet());
		for (Vertex adjVertex : keys)
		{
			if (adjVertex.label == targetVertex)
			{
				peso = adjVertex.weight;
				break;
			}
		}

		return peso;
	}

	@Override
	public void setEdgeWeight(String sourceVertex, String targetVertex, double weight)
			throws IllegalArgumentException, NoSuchElementException
	{
		if (!containsVertex(sourceVertex) || !containsVertex(targetVertex))
			throw new IllegalArgumentException("Il vertice " + sourceVertex + " e/o " + targetVertex
					+ " non appartengono al grafo!");
		if (!containsEdge(sourceVertex, targetVertex))
			throw new NoSuchElementException(
					"L'arco " + sourceVertex + "-" + targetVertex + " non appartiene al grafo! ");

		Set<Vertex> sVertexs = adjacencyList.get(new Vertex(sourceVertex));
		Set<Vertex> tVertexs = adjacencyList.get(new Vertex(targetVertex));
		for (Vertex adjVertex : sVertexs)
		{
			if (adjVertex.label == targetVertex)
				adjVertex.weight = weight;
		}
		for (Vertex adjVertex : tVertexs)
		{
			if (adjVertex.label == sourceVertex)
				adjVertex.weight = weight;
		}


	}

	@Override
	public WeightedGraph getBellmanFordShortestPaths(String startingVertex)
			throws UnsupportedOperationException, IllegalArgumentException
	{
		throw new UnsupportedOperationException("Da non implementare");
	}

	@Override
	public WeightedGraph getDijkstraShortestPaths(String startingVertex)
			throws UnsupportedOperationException, IllegalArgumentException
	{
		throw new UnsupportedOperationException("Da non implementare");
	}

	@Override
	public WeightedGraph getPrimMST(String startingVertex)
			throws UnsupportedOperationException, IllegalArgumentException
	{
		throw new UnsupportedOperationException("Da non implementare");
	}

	@Override
	public WeightedGraph getKruskalMST() throws UnsupportedOperationException
	{
		throw new UnsupportedOperationException("Da non implementare");
	}

	@Override
	public WeightedGraph getFloydWarshallShortestPaths() throws UnsupportedOperationException
	{
		throw new UnsupportedOperationException("Da non implementare");
	}


}
