package assignment3;

import org.jacoco.core.analysis.*;
import org.jacoco.core.tools.ExecFileLoader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import org.objectweb.asm.ClassReader;

/**
 * This class performs the test coverage analysis. It uses Junco to dump the
 * current coverage information and JaCoCo to calculate the class coverage
 * information for each test case.
 *
 * @author Cesar Soto-Valero
 */
public class JuncoProcessor {

    /**
     * Path to the bundle
     */
    private final Collection<String> bundle;

    /**
     * Path to the Junco coverage
     */
    private final String coveragePath;

    /**
     * Path to the built classes
     */
    private final String builtClassesPath;

    /**
     * Creates the test dependency extractor
     *
     * @param bundle Path to the bundle
     * @param coveragePath Path to the Junco coverage
     * @param builtClassesPath Path to the built classes
     */
    public JuncoProcessor(Collection<String> bundle, String coveragePath, String builtClassesPath) {
        this.bundle = bundle;
        this.coveragePath = coveragePath;
        this.builtClassesPath = builtClassesPath;
    }

    /**
     * Processes the coverage information
     *
     * @return A list with the name of the methods that are not covered by the
     * test suit
     *
     * @throws java.io.IOException
     */
    protected ArrayList<String> process() throws IOException {

        // the list to return
        ArrayList<String> notCoveredMethodNames = new ArrayList<>();

        // check the coverage files
        File fcoverage = new File(coveragePath);
        if (!fcoverage.exists()) {
            throw new FileNotFoundException(fcoverage.getAbsolutePath());
        }

        // foreach file in the coverage files
        for (File f : fcoverage.listFiles()) {

            //Obtain the coverage bundle
            if (f.isDirectory() || !f.getName().endsWith(".exec")) {
                continue;
            }
            ExecFileLoader loader = new ExecFileLoader();

            // load the .exec file and create the analyzer
            loader.load(f);
            final CoverageBuilder coverageBuilder = new CoverageBuilder();

            final Analyzer analyzer = new Analyzer(loader.getExecutionDataStore(), coverageBuilder);

            analyzer.analyzeAll(new File(builtClassesPath));

            // foreach class in the coverage builder
            for (IClassCoverage c : coverageBuilder.getClasses()) {

                System.out.println("Analyzing class: " + c.getName());

                Collection<IMethodCoverage> methods = c.getMethods();

                // foreach method in the class
                for (IMethodCoverage method : methods) {

                    System.out.println("Analyzing method: " + method.getName());

                    switch (method.getMethodCounter().getStatus()) {
                        case 0:
                            System.out.println("  ===> EMPTY");
                            break;
                        case 1:
                            System.out.println("  ===> NOT_COVERED");
                            notCoveredMethodNames.add(method.getName());
                            break;
                        case 3:
                            System.out.println("  ===> PARTLY_COVERED");
                            break;
                        case 2:
                            System.out.println("  ===> FULLY_COVERED");
                            break;
                    }
                }

                // let's dump some other coverage information of the class
                System.out.println("\n=== SUMMARY === ");
                printCounter("instructions", c.getInstructionCounter());
                printCounter("branches", c.getBranchCounter());
                printCounter("lines", c.getLineCounter());
                printCounter("methods", c.getMethodCounter());
                printCounter("complexity", c.getComplexityCounter());
                System.out.println("\n");
            }

        }

        return notCoveredMethodNames;
    }

    /**
     * Processes the coverage information
     *
     * @return A list with the name of the methods that are not covered by the
     * test suit
     *
     * @throws java.io.IOException
     */
    protected ArrayList<String> process2() throws IOException {

        // the list to return
        ArrayList<String> notCoveredMethodNames = new ArrayList<>();

        // check the coverage files
        File fcoverage = new File(coveragePath);
        if (!fcoverage.exists()) {
            throw new FileNotFoundException(fcoverage.getAbsolutePath());
        }

        // foreach file in the coverage files
        for (File f : fcoverage.listFiles()) {

            //Obtain the coverage bundle
            if (f.isDirectory() || !f.getName().endsWith(".exec")) {
                continue;
            }
            ExecFileLoader loader = new ExecFileLoader();

            // load the .exec file and create the analyzer
            loader.load(f);
            final CoverageBuilder coverageBuilder = new CoverageBuilder();

            final Analyzer analyzer = new Analyzer(loader.getExecutionDataStore(), coverageBuilder);

            analyzer.analyzeAll(new File(builtClassesPath));

            // foreach class in the coverage builder
            for (IClassCoverage c : coverageBuilder.getClasses()) {

                System.out.println("Analyzing class: " + c.getName());

                Collection<IMethodCoverage> methods = c.getMethods();

                // foreach method in the class
                for (IMethodCoverage method : methods) {

                    System.out.println("Analyzing method: " + method.getName());

                    switch (method.getMethodCounter().getStatus()) {
                        case 0:
                            System.out.println("  ===> EMPTY");
                            break;
                        case 1:
                            System.out.println("  ===> NOT_COVERED");
                            notCoveredMethodNames.add(method.getName());
                            break;
                        case 3:
                            System.out.println("  ===> PARTLY_COVERED");
                            break;
                        case 2:
                            System.out.println("  ===> FULLY_COVERED");
                            break;
                    }
                }

                // let's dump some other coverage information of the class
                System.out.println("\n=== SUMMARY === ");
                printCounter("instructions", c.getInstructionCounter());
                printCounter("branches", c.getBranchCounter());
                printCounter("lines", c.getLineCounter());
                printCounter("methods", c.getMethodCounter());
                printCounter("complexity", c.getComplexityCounter());
                System.out.println("\n");
            }

        }

        return notCoveredMethodNames;
    }

    /**
     * Prints the coverage information of the counter
     *
     * @param unit Coverage criterion
     * @param counter The current coverage counter
     */
    private void printCounter(final String unit, final ICounter counter) {
        final Integer missed = counter.getMissedCount();
        final Integer total = counter.getTotalCount();
        System.out.printf("%s of %s %s missed%n", missed, total, unit);
    }

}
