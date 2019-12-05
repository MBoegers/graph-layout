# Graph Layout
Basic graph layout factory, currently supporting forced directed layout derived from https://github.com/chaangliu/ForceDirectedLayout

## Usage
To the layout algorithm is an iterative one, so multiple calls in the layout method produce better results.
Everybody should test what number of iterations suits the most.

### Getting a layout instance
Use the `LayoutFactory` to setup an instance, currently only the so called force directed layout algorithm is implemented.
```java
/* minimal usage example of the LayoutFactory */
 GraphLayout layout = LayoutFactory.newInstance()
                        .forced() // set type to force directed layouting
                        .width(1000) // set width of the layout space
                        .height(1000) // set height of the layout space
                        .edges(edges) // set edges to layout
                        .nodes(nodes) // set nodes to layout
                        .build(); // assemble instance
```

### Layout your nodes
To be able to plug your nodes in the layout algorithm simply extend `Node`and `Edge` from the package `de.boeg.graph.layout.domain`.
Nodes generic identifier should implement `Object.hashCode()` and `Object.equals()` because the layout works with hashes.
The layout works with references to the nodes, meaning that the changes to the coordinates are directly written into the node instances.

### Layout Algorithms
Currently only one algorithm is implemented

#### Force Direct Layout
The main idea is that each node is handled as a magnet so they are forced to diverge, while the edges are considered to be springs which pull adjacent nodes together.
Natural clusters of adjacent nodes are the result. 
See Wikipedias [Force-Directed graph drawing](https://en.wikipedia.org/wiki/Force-directed_graph_drawing) page to get a deeper understanding.

The `LayoutFactory` provides three methods that are force layout specific.
1.  `LayoutFactory.withEjectFactors(int e1, int e2)` to configure the ejecting force factor for nodes, the first one is the usualy used one (default: 6). The second is used whenever the distance is smaller that 30 (default: 5) . 
2.  `LayoutFactory.withMaxDeltas(int x, int y)` to configure the maximal amount of distance added per iteration (default: x=3, y=3)
3.  `LayoutFactory.withCondenseFactor(int c)` to configure the factor for the edges to pull back the nodes (default: 3)
