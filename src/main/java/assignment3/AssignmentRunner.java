package assignment3;

import java.io.IOException;
import spoon.Launcher;

/**
 * This class executes the transformation
 *
 * @author Cesar Soto-Valero
 */
public class AssignmentRunner {

    public static void main(String[] args) throws IOException {

        final String[] param = {
            "-i", "src/main/java/org",
            "-o", "output/",
            "-p", "assignment3.SpoonProcessor",
            "-c"
        };

        final Launcher launcher = new Launcher();
        launcher.setArgs(param);
        launcher.run();

    }

}
