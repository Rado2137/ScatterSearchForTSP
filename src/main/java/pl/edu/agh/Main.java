package pl.edu.agh;

import org.uma.jmetal.problem.singleobjective.TSP;
import org.uma.jmetal.solution.PermutationSolution;
import org.uma.jmetal.util.AlgorithmRunner;
import org.uma.jmetal.util.JMetalLogger;
import org.uma.jmetal.util.archive.impl.CrowdingDistanceArchive;
import org.uma.jmetal.util.fileoutput.SolutionListOutput;

import java.io.IOException;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {
        TSP problem;
        IntegerABYSS algorithm;
        JMetalLogger.logger.info("TSP");

        problem = new TSP("/tsp/kroA100.tsp");

        algorithm = new IntegerABYSSBuilder(problem, new CrowdingDistanceArchive<>(10)).build();

        AlgorithmRunner algorithmRunner = new AlgorithmRunner.Executor(algorithm)
                .execute();

        List<PermutationSolution<Integer>> solution = algorithm.getResult();

        long computingTime = algorithmRunner.getComputingTime();

        new SolutionListOutput(solution)
                .print();

        JMetalLogger.logger.info("Total execution time: " + computingTime + "ms");
    }

}
