package assignment3;

import org.jacoco.core.analysis.*;
import org.jacoco.core.tools.ExecFileLoader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

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
    protected HashMap<String, Collection<String>> process2() throws IOException {

        // the HashMap to return
        HashMap<String, Collection<String>> result = new HashMap<>();

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

//                System.out.println("Analyzing class: " + c.getName());
                Collection<IMethodCoverage> methods = c.getMethods();

                Collection<String> coveredMethodsName = new ArrayList<>();

                // foreach method in the class
                for (IMethodCoverage method : methods) {

//                    System.out.println("Analyzing method: " + method.getName());
                    switch (method.getMethodCounter().getStatus()) {
                        case 0:
//                            System.out.println("  ===> EMPTY");
                            break;
                        case 1:
//                            System.out.println("  ===> NOT_COVERED");
                            break;
                        case 3:
//                            System.out.println("  ===> PARTLY_COVERED");
                            coveredMethodsName.add(method.getName());
                            break;
                        case 2:
//                            System.out.println("  ===> FULLY_COVERED");
                            coveredMethodsName.add(method.getName());
                            break;
                    }
                }

                if (coveredMethodsName.size() > 0) {
                    String className = c.getName().split("/")[c.getName().split("/").length - 1];
                    result.put(className, coveredMethodsName);

                    System.out.println("\nAnalyzing class: " + c.getName().replace("/", "."));
                    // let's dump some other coverage information of the class
                    System.out.println("=== SUMMARY === ");
                    printCounter("instructions", c.getInstructionCounter());
                    printCounter("branches", c.getBranchCounter());
                    printCounter("lines", c.getLineCounter());
                    printCounter("methods", c.getMethodCounter());
                    printCounter("complexity", c.getComplexityCounter());

                }

            }

        }

        return result;
    }

    /**
     * Processes the coverage information
     */
    protected HashMap<String, Collection<String>> process3() throws IOException {

        HashMap<String, Collection<String>> result = new HashMap<>();

        File fcoverage = new File(coveragePath);
        if (!fcoverage.exists()) {
            throw new FileNotFoundException(fcoverage.getAbsolutePath());
        }

        for (File f : fcoverage.listFiles()) {
            //Obtain the coverage bundle
            if (f.isDirectory() || !f.getName().endsWith(".exec")) {
                continue;
            }
            ExecFileLoader loader = new ExecFileLoader();

            loader.load(f);
            final CoverageBuilder coverageBuilder = new CoverageBuilder();
            final Analyzer analyzer = new Analyzer(loader.getExecutionDataStore(), coverageBuilder);
            analyzer.analyzeAll(new File(builtClassesPath));

            Collection<String> coveredClassesName = new ArrayList<>();
            String n = f.getName();
            for (IClassCoverage c : coverageBuilder.getClasses()) {
                if (c.getClassCounter().getCoveredCount() > 0) {
                    String className = c.getName().replace("/", ".");
//                    if (bundle == null || bundle.size() == 0 || bundle.contains(className)) {
                    coveredClassesName.add(className);
//                    }
                }
            }
            if (coveredClassesName.size() > 0) {
                result.put(n.substring(0, n.lastIndexOf(".exec")), coveredClassesName);
            }
        }
        return result;
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
