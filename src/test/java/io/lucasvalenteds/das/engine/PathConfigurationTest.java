package io.lucasvalenteds.das.engine;

import java.nio.file.Path;
import org.javatuples.Pair;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

class PathConfigurationTest {

    private final PathConfiguration manager = new PathConfiguration(
        new Pair<>("data/in", ".dat"),
        new Pair<>("data/out", ".done.dat")
    );

    @Test
    void testResolveReportName() {
        var input = Path.of("foo.dat");

        var output = manager.resolveReportFilename(input.getFileName());

        assertTrue(output.toString().endsWith(".done.dat"));
    }

    @Test
    void testHasDatExtension() {
        assertTrue(manager.hasInputExtension(Path.of("bar.dat")));
        assertFalse(manager.hasInputExtension(Path.of("bar.txt")));
        assertFalse(manager.hasInputExtension(Path.of("bar.dAt")));
        assertFalse(manager.hasInputExtension(Path.of("bar.dat.not")));
    }
}