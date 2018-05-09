/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment3;

import java.io.IOException;
import java.util.*;

public class PrintClassCoverage {

    public static void main(String[] args) throws IOException {

        /**
         * Path to the Junco coverage path.
         */
        String coveragePath = "target\\site\\junco";

        /**
         * Path to the built classes
         */
        String builtClassesPath = "target\\classes";

        JuncoProcessor p = new JuncoProcessor(Arrays.asList(new String[]{
            "classes.Calculator"}), coveragePath, builtClassesPath);

        HashMap<String, Collection<String>> coverage = p.process3();
        for (String k : coverage.keySet()) {
            System.out.println(k);
            for (String s : coverage.get(k)) {
                System.out.println(" - " + s);
            }
        }

    }

}
