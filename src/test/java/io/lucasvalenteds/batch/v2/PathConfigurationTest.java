package io.lucasvalenteds.batch.v2;

import java.nio.file.Path;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

class PathConfigurationTest {

    private final PathConfiguration manager = new PathConfiguration();

    @Test
    void testResolveReportName() {
        var input = Path.of("foo.dat");

        var output = manager.resolveReportFilename(input.getFileName());

        assertTrue(output.toString().endsWith(".done.dat"));
    }

    @Test
    void testHasDatExtension() {
        assertTrue(manager.hasDatExtension(Path.of("bar.dat")));
        assertFalse(manager.hasDatExtension(Path.of("bar.txt")));
        assertFalse(manager.hasDatExtension(Path.of("bar.dAt")));
        assertFalse(manager.hasDatExtension(Path.of("bar.dat.not")));
    }
}