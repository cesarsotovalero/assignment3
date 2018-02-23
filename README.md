# Answer to the assignment No.3

## Task description

> "The idea of this transformation is to remove useless code in an application. One way to start is to instrument the code in order to  > get the parts of the code that are executed when running a test suite. Once you have these parts, you can run a transformation that > removes the parts that are not used to run the test suite." 

## Approach

My solution consists in searching and removing the unused methods (those that are not executed by the test suite) from the Java classes in the application. The intuition behind this transformation is that uncovered methods could not be strictly required for proper execution of the application. So, programs can be analyzed post-compilation to remove unwanted features, while maintaining the functionality of features that we want to retain.

The implementation of the solution utilizes three main components:

1. [JaCoCo](http://jacoco.org/jacoco): an open-source toolkit for measuring and reporting Java code coverage  (it uses a Java agent to instrumet the `.class` files on-the-fly)
2. [Junco](https://github.com/marcelinorc/junco-provider): a Surefire provider that executes JUnit tests cases and calculates coverage for each test case using JaCoCo
3. [Spoon](http://spoon.gforge.inria.fr/): a Java library that enables the transformation and analysis of Java source code via AST navigation

First, we use Junco to get a connection to the JaCoCo agent, dump the current coverage information and resets the hit counters (by doing this, we may expect to obtain the coverage information solely for the executed test cases). Counters are derived from information contained in Java class files which basically are Java byte code instructions and debug information optionally embedded in class files. Once we have  the class coverage information for each test case, we use JaCoCo features to analyze the covered elements. The methods that are not covered by the test suite are saved and then we use Spoon to remove these methods from the class.

### Usage

- **Input:** the Java classes with its test suite
- **Ouput:** the class given as input, but without the methods that were not covered by the test suite

#### Example

The example class `Calculator.java` is located in `.\junco-provider-master\test-project\src\main\java\classes`. Once you complete the build of the Maven project `test-project`, the tests coverage reports will be generated in  `\junco-provider-master\test-project\target\site\junco`. At this point, you can run the `AssignmentRunner.java` which executes the class transformation. The transformed class `Calculator.java` is put in `test-project\output\classes`. We include  comments at the top of the transformed classes with the names of the uncovered methods that were removed.

If you change the location of the classess, you need to update the following paths in `SpoonProcessor.java`:  

```java
/**
* Path to the Junco coverage path.
*/
String coveragePath = "target\\site\\junco";

/**
* Path to the built classes
*/
String builtClassesPath = "target\\classes\\classes";


```

and also the parameters of the Spoon's launcher in `AssignmentRunner.java`:


```java
final String[] param = {
      "-i", "src/main/java/classes",
      "-o", "output/",
      "-p", "assignment3.SpoonProcessor",
      "-c"
};

```
## Future work
We can use many different criteria to decide what elements to remove from the program bytecode. JaCoCo implements a set of different counters to calculate coverage metrics (e.g., branch coverage, class coverage, coverage of individual lines, cyclomatic complexity, etc.). It could be interesting to study the performance of these metrics for removing rarely used programs' features. This give various benefits to the software development; e.g., increase software security, decrease code size, and gain in execution optimality.








