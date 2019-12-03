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

@DisplayName("The GraphLayoutFactory should")
public class GraphLayoutFactoryTest {

    @Test
    @DisplayName("generate a directed force layout if requested")
    void generateforcedLayout() {
        var layout = LayoutFactory.newInstance()
                .forced()
                .build();

        Assertions.assertTrue(layout instanceof ForceDirectedLayout, "Layouter should be of type forced");
    }

    @Test
    @DisplayName("throw exception if build is called without setting the type")
    void failGenerateLayout() {
        var layoutFactory = LayoutFactory.newInstance();

        Assertions.assertThrows(IllegalStateException.class, layoutFactory::build, "build should fail if no type is provided");
    }

    @Test
    @DisplayName("generate a layout with configured nodes")
    void generateWithNodes() {

        Collection nodes = List.of(new StringNode(), new StringNode());

        var layout = LayoutFactory.newInstance()
                .forced()
                .nodes(nodes)
                .build();

        Assertions.assertEquals(layout.getNodes(), nodes, "Node in layout manager should be the same");
    }
}
