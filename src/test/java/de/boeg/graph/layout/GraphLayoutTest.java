package de.boeg.graph.layout;

import de.boeg.graph.layout.domain.Node;
import de.boeg.graph.layout.domain.StringEdge;
import de.boeg.graph.layout.domain.StringNode;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.BiFunction;

@DisplayName("The directed force layout should")
public class GraphLayoutTest {

    @Test
    @DisplayName("fail if destination nodes is missing")
    void missingEdges() {
        var first = new StringNode();
        var second = new StringNode();
        var missing = new StringNode();

        var firstEdge = new StringEdge(first, second);
        var secondEdge = new StringEdge(first, missing);

        Collection nodes = List.of(first, second);
        Collection edges = List.of(firstEdge, secondEdge);

        var layout = LayoutFactory.newInstance()
                .forced()
                .nodes(nodes)
                .edges(edges)
                .build();


        Assertions.assertThrows(IllegalArgumentException.class, layout::layout, "Layout should fail if an edge leads to a not existent node");
    }

    @Test
    @DisplayName("change values on layout")
    void generateEdges() {
        var first = new StringNode();
        var second = new StringNode();

        var firstEdge = new StringEdge(first, second);

        Collection nodes = List.of(first, second);
        Collection edges = List.of(firstEdge);

        var layout = LayoutFactory.newInstance()
                .forced()
                .nodes(nodes)
                .edges(edges)
                .height(100)
                .width(100)
                .build();

        layout.layout();

        List<Executable> checks = new ArrayList<>();
        checks.add(() -> Assertions.assertNotEquals(0d, first.getX(), "x coordinate of first node should have been updated"));
        checks.add(() -> Assertions.assertNotEquals(0d, first.getY(), "y coordinate of first node  should have been updated"));
        checks.add(() -> Assertions.assertNotEquals(0d, second.getX(), "x coordinate of second node should have been updated"));
        checks.add(() -> Assertions.assertNotEquals(0d, second.getY(), "y coordinate of second node should have been updated"));

        Assertions.assertAll(checks);
    }

    @Test
    @DisplayName("not have colliding nodes after 10 iterations")
    void layout() {
        var first = new StringNode();
        var second = new StringNode();
        var third = new StringNode();

        var firstEdge = new StringEdge(first, second);
        var secondEdge = new StringEdge(first, third);
        var thirdEdge = new StringEdge(third, first);


        Collection nodes = List.of(first, second, third);
        Collection edges = List.of(firstEdge, secondEdge, thirdEdge);

        var layout = LayoutFactory.newInstance()
                .forced()
                .nodes(nodes)
                .edges(edges)
                .height(100)
                .width(100)
                .build();

        for(int i = 0; i < 100; i++) {
            layout.layout();
        }

        BiFunction<Node, Node, Boolean> same = (a,b) -> a.getX() == b.getX() && a.getY() == b.getY();

        Assertions.assertAll(
                () -> Assertions.assertFalse(same.apply(first,second), "first and second should not collide"),
                () -> Assertions.assertFalse(same.apply(second,third), "second and thrid should not collide"),
                () -> Assertions.assertFalse(same.apply(third,first), "thrid and first should not collide")
        );
    }
}
