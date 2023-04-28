package upo.graph.impl;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AdjListTest {

    @Test
    public void testGetVertexIndex() {
        AdjListUndir graph = new AdjListUndir();
        int expectedOutput = 0;
        graph.addVertex("A");
        graph.addVertex("B");
        graph.addVertex("C");
        int actualOutput = graph.getVertexIndex("B");
        assertEquals(expectedOutput, actualOutput);
    }
    
    @Test
    public void testGetVertexLabel() {
        AdjListUndir graph = new AdjListUndir();
        String expectedOutput = "C";
        graph.addVertex("A");
        graph.addVertex("B");
        graph.addVertex("C");
        String actualOutput = graph.getVertexLabel(2);
        assertEquals(expectedOutput, actualOutput);
    }
    
    @Test
    public void testAddVertex() {
        AdjListUndir graph = new AdjListUndir();
        int expectedOutput = 1;
        graph.addVertex("A");
        int actualOutput = graph.addVertex("B");
        assertEquals(expectedOutput, actualOutput);
    }
}
