import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import upo.graph.impl.AdjListUndir;

import upo.graph.base.*;

public class AdjListUndirTest
{

    private AdjListUndir graph;

    @BeforeEach
    public void setUp()
    {
        graph = new AdjListUndir();
    }

    @Test
    public void testAddVertex()
    {
        int index = graph.addVertex("A");
        Assertions.assertEquals(0, index);
        Assertions.assertTrue(graph.containsVertex("A"));
    }

    @Test
    public void testGetVertexIndex()
    {
        graph.addVertex("A");
        int index = graph.getVertexIndex("A");
        Assertions.assertEquals(0, index);
    }

    @Test
    public void testGetVertexLabel()
    {
        graph.addVertex("A");
        String label = graph.getVertexLabel(0);
        Assertions.assertEquals("A", label);
    }

    @Test
    public void testContainsVertex()
    {
        graph.addVertex("A");
        Assertions.assertTrue(graph.containsVertex("A"));
        Assertions.assertFalse(graph.containsVertex("B"));
    }

    @Test
    public void testRemoveVertex()
    {
        graph.addVertex("A");
        graph.addVertex("B");
        graph.addEdge("A", "B");
        graph.removeVertex("A");
        Assertions.assertFalse(graph.containsVertex("A"));
        Assertions.assertFalse(graph.containsEdge("A", "B"));
        Assertions.assertTrue(graph.containsVertex("B"));
    }

    @Test
    public void testAddEdge()
    {
        graph.addVertex("A");
        graph.addVertex("B");
        graph.addEdge("A", "B");
        Assertions.assertTrue(graph.containsEdge("A", "B"));
        Assertions.assertTrue(graph.containsEdge("B", "A"));
    }

    @Test
    public void testContainsEdge()
    {
        graph.addVertex("A");
        graph.addVertex("B");
        graph.addEdge("A", "B");
        Assertions.assertTrue(graph.containsEdge("A", "B"));
        Assertions.assertTrue(graph.containsEdge("B", "A"));
        Assertions.assertFalse(graph.containsEdge("A", "C"));
    }

    @Test
    public void testRemoveEdge()
    {
        graph.addVertex("A");
        graph.addVertex("B");
        graph.addEdge("A", "B");
        graph.removeEdge("A", "B");
        Assertions.assertFalse(graph.containsEdge("A", "B"));
        Assertions.assertFalse(graph.containsEdge("B", "A"));
    }

    @Test
    public void testGetAdjacent()
    {
        graph.addVertex("A");
        graph.addVertex("B");
        graph.addEdge("A", "B");
        Assertions.assertEquals(1, graph.getAdjacent("A").size());
        Assertions.assertEquals(1, graph.getAdjacent("B").size());
    }

    @Test
    public void testIsAdjacent()
    {
        graph.addVertex("A");
        graph.addVertex("B");
        graph.addEdge("A", "B");
        Assertions.assertTrue(graph.isAdjacent("A", "B"));
        Assertions.assertTrue(graph.isAdjacent("B", "A"));
        Assertions.assertFalse(graph.isAdjacent("A", "C"));
    }

    @Test
    public void testSize()
    {
        graph.addVertex("A");
        graph.addVertex("B");
        Assertions.assertEquals(2, graph.size());
    }

    @Test
    public void testIsDirected()
    {
        Assertions.assertFalse(graph.isDirected());
    }

}
