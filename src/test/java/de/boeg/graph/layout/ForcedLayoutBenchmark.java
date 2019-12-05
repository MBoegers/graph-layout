package de.boeg.graph.layout;

import de.boeg.graph.layout.domain.Edge;
import de.boeg.graph.layout.domain.Node;
import de.boeg.graph.layout.domain.StringEdge;
import de.boeg.graph.layout.domain.StringNode;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Benchmark)
@Fork(value = 2, jvmArgs = {"-Xms2G", "-Xmx2G"})
public class ForcedLayoutBenchmark {

    @Param({"5", "10", "100"})
    private int nodeCount;

    @Param({"5", "10", "100"})
    private int edgeCount;

    @Param({"1", "10", "100"})
    private int runs;

    private GraphLayout layout;


    public static void main(String[] args) throws RunnerException {

        Options opt = new OptionsBuilder()
                .include(ForcedLayoutBenchmark.class.getSimpleName())
                .forks(1)
                .build();

        new Runner(opt).run();
    }

    @Setup
    public void setup() {
        List<Node> nodes = new ArrayList<>(nodeCount);
        List<Edge> edges = new ArrayList<>(edgeCount);

        // generate nodes
        for(int i = 0; i < nodeCount; i++) {
            nodes.add(new StringNode());
        }

        // generate random links
        Random random = new Random(System.currentTimeMillis());
        for(int i = 0; i < edgeCount; i++) {
            int from = random.nextInt(nodeCount);
            int to = random.nextInt(nodeCount);

            edges.add(new StringEdge(nodes.get(from), nodes.get(to)));
        }

        layout = LayoutFactory.newInstance()
                .forced()
                .width(1000)
                .height(1000)
                .edges(edges)
                .nodes(nodes)
                .build();
    }

    @Benchmark
    public void tenRuns(Blackhole sink) {
        for(int i =0; i < runs; i++){
            layout.layout();
            sink.consume(layout.getNodes());
        }
    }
}
