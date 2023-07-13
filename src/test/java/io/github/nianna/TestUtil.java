package io.github.nianna;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class TestUtil {

    public static List<String> loadPlPatterns() {
        try {
            Path patternsPath = Path.of(TestUtil.class.getResource("/hyph_pl_PL.dic").toURI());
            return Files.readAllLines(patternsPath);
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

}
