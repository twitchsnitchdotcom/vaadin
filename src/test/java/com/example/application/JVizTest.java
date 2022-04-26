package com.example.application;

import guru.nidi.graphviz.attribute.Color;
import guru.nidi.graphviz.attribute.Font;
import guru.nidi.graphviz.attribute.Rank;
import guru.nidi.graphviz.attribute.Style;
import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.Graphviz;
import guru.nidi.graphviz.model.Graph;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static guru.nidi.graphviz.attribute.Attributes.attr;
import static guru.nidi.graphviz.attribute.Rank.RankDir.LEFT_TO_RIGHT;
import static guru.nidi.graphviz.model.Factory.graph;
import static guru.nidi.graphviz.model.Factory.node;
import static guru.nidi.graphviz.model.Link.to;

public class JVizTest {
    @Test
public void test() throws IOException {
    Graph g = graph("example1").directed()
            .graphAttr().with(Rank.dir(LEFT_TO_RIGHT))
            .nodeAttr().with(Font.name("arial"))
            .linkAttr().with("class", "link-class")
            .with(
                    node("jamie").with(Color.BLUE1).link(node("johnny")),
                    node("johnny").link(
                            to(node("dave")).with(attr("weight", 10), Style.DASHED)
                    )
            );
    Graphviz.fromGraph(g).height(100).render(Format.PNG).toFile(new File("example/jamie.png"));
}
}
