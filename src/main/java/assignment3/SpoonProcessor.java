package assignment3;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;
import spoon.processing.AbstractProcessor;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtMethod;

/**
 * This class removes the methods that are not covered by the test suite in a
 * given class. It uses Spoon to do the code transformation and Junco to
 * retrieve the coverage results
 *
 * @author Cesar Soto-Valero
 */
public class SpoonProcessor extends AbstractProcessor<CtClass> {

    /**
     * Path to the Junco coverage path.
     */
    String coveragePath = "target\\site\\junco";

    /**
     * Path to the built classes
     */
    String builtClassesPath = "target\\classes\\classes";

    /**
     * a list with the name of the methods that are not covered by the test suit
     */
    ArrayList<String> notCoveredMethodNames;

    JuncoProcessor p = new JuncoProcessor(Arrays.asList(new String[]{
        "classes.Calculator"}), coveragePath, builtClassesPath);

    /**
     * Constructor that gets the coverage information of the test suit for the
     * bundle
     *
     * @throws IOException
     */
    public SpoonProcessor() throws IOException {

        notCoveredMethodNames = p.process();

    }

    @Override
    public void process(CtClass ctClass) {
        // get all the methods in the class
        Set<CtMethod> methods = ctClass.getMethods();
        // for each method in the class
        for (CtMethod method : methods) {
            // check if the method is in the list 
            if (notCoveredMethodNames.contains(method.getSimpleName())) {
                // remove the method from the class
                ctClass.removeMethod(method);
                // add a comment at the top of the class with the name of the removed method/s
                ctClass.addComment(getFactory().Core().createComment().setContent("method \"" + method.getSimpleName() + "\" was removed from this class because it was not covered by the test suite"));
            }
        }
    }
}
