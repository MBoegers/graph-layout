package de.boeg.graph.layout;

import de.boeg.graph.layout.domain.Node;
import de.boeg.graph.layout.domain.StringEdge;
import de.boeg.graph.layout.domain.StringNode;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

@DisplayName("The directed force layout should")
public class GraphLayoutTest {

    @Test
    @DisplayName("fail if destination nodes is missing")
    void missingEdges() {
        // setup
        var first = new StringNode();
        var second = new StringNode();
        var missing = new StringNode();

        var firstEdge = new StringEdge(first, second);
        var secondEdge = new StringEdge(first, missing);

        var nodes = List.of(first, second);
        var edges = List.of(firstEdge, secondEdge);

        // execute
        var layout = LayoutFactory.newInstance()
                .forced()
                .nodes(nodes)
                .edges(edges)
                .build();

        // check
        Assertions.assertThrows(IllegalArgumentException.class, layout::layout, "Layout should fail if an edge leads to a not existent node");
    }

    @Test
    @DisplayName("change values on layout")
    void setToRandomAtStart() {
        // setup
        var first = new StringNode();
        var second = new StringNode();

        var firstEdge = new StringEdge(first, second);

        var nodes = List.of(first, second);
        var edges = List.of(firstEdge);

        // execute
        LayoutFactory.newInstance()
                .forced()
                .nodes(nodes)
                .edges(edges)
                .height(100)
                .width(100)
                .build();

        // check
        List<Executable> checks = new ArrayList<>();
        checks.add(() -> Assertions.assertNotEquals(0d, first.getX(), "x coordinate of first node should have been updated"));
        checks.add(() -> Assertions.assertNotEquals(0d, first.getY(), "y coordinate of first node  should have been updated"));
        checks.add(() -> Assertions.assertNotEquals(0d, second.getX(), "x coordinate of second node should have been updated"));
        checks.add(() -> Assertions.assertNotEquals(0d, second.getY(), "y coordinate of second node should have been updated"));

        Assertions.assertAll(checks);
    }

    @Test
    @DisplayName("change values on layout")
    void changeOnLayout() {
        // setup
        var first = new StringNode();
        var second = new StringNode();

        var firstEdge = new StringEdge(first, second);

        var nodes = List.of(first, second);
        var edges = List.of(firstEdge);

        var layout = LayoutFactory.newInstance()
                .forced()
                .nodes(nodes)
                .edges(edges)
                .height(100)
                .width(100)
                .build();

        double firstX = first.getX();
        double firstY = first.getY();
        double secondX = second.getX();
        double secondY = second.getY();

        // execute
        layout.layout();

        // check
        List<Executable> checks = new ArrayList<>();
        checks.add(() -> Assertions.assertNotEquals(firstX, first.getX(), "x coordinate of first node should have been updated"));
        checks.add(() -> Assertions.assertNotEquals(firstY, first.getY(), "y coordinate of first node  should have been updated"));
        checks.add(() -> Assertions.assertNotEquals(secondX, second.getX(), "x coordinate of second node should have been updated"));
        checks.add(() -> Assertions.assertNotEquals(secondY, second.getY(), "y coordinate of second node should have been updated"));
        checks.add(() -> Assertions.assertEquals(first.getX(), firstEdge.getX1(), "x coordinate of the edge should equal first nodes"));
        checks.add(() -> Assertions.assertEquals(first.getY(), firstEdge.getY1(), "x coordinate of the edge should equal first nodes"));
        checks.add(() -> Assertions.assertEquals(second.getX(), firstEdge.getX2(), "x coordinate of the edge should equal first nodes"));
        checks.add(() -> Assertions.assertEquals(second.getY(), firstEdge.getY2(), "x coordinate of the edge should equal first nodes"));

        Assertions.assertAll(checks);
    }

    @Test
    @DisplayName("not have colliding nodes after 10 iterations")
    void layout10Times() {
        // setup
        var first = new StringNode();
        var second = new StringNode();
        var third = new StringNode();

        var firstEdge = new StringEdge(first, second);
        var secondEdge = new StringEdge(first, third);
        var thirdEdge = new StringEdge(third, first);


        var nodes = List.of(first, second, third);
        var edges = List.of(firstEdge, secondEdge, thirdEdge);

        var layout = LayoutFactory.newInstance()
                .forced()
                .nodes(nodes)
                .edges(edges)
                .height(100)
                .width(100)
                .build();

        // execute
        for (int i = 0; i < 100; i++) {
            layout.layout();
        }

        //test
        BiFunction<Node, Node, Boolean> same = (a, b) -> a.getX() == b.getX() && a.getY() == b.getY();
        List<Executable> checks = new ArrayList<>();
        checks.add(() -> Assertions.assertFalse(same.apply(first, second), "first and second should not collide"));
        checks.add(() -> Assertions.assertFalse(same.apply(second, third), "second and thrid should not collide"));
        checks.add(() -> Assertions.assertFalse(same.apply(third, first), "thrid and first should not collide"));

        Assertions.assertAll(checks);
    }
}
