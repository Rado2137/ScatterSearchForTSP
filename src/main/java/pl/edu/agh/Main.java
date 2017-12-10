package pl.edu.agh;

import org.uma.jmetal.algorithm.impl.AbstractEvolutionaryAlgorithm;
import org.uma.jmetal.algorithm.impl.ScatterSearch;
import org.uma.jmetal.algorithm.multiobjective.abyss.ABYSS;
import org.uma.jmetal.algorithm.multiobjective.abyss.ABYSSBuilder;
import org.uma.jmetal.algorithm.singleobjective.geneticalgorithm.GeneticAlgorithmBuilder;
import org.uma.jmetal.operator.CrossoverOperator;
import org.uma.jmetal.operator.MutationOperator;
import org.uma.jmetal.operator.SelectionOperator;
import org.uma.jmetal.operator.impl.crossover.DifferentialEvolutionCrossover;
import org.uma.jmetal.operator.impl.crossover.PMXCrossover;
import org.uma.jmetal.operator.impl.mutation.PermutationSwapMutation;
import org.uma.jmetal.operator.impl.mutation.PolynomialMutation;
import org.uma.jmetal.operator.impl.selection.BinaryTournamentSelection;
import org.uma.jmetal.problem.DoubleProblem;
import org.uma.jmetal.problem.PermutationProblem;
import org.uma.jmetal.problem.singleobjective.TSP;
import org.uma.jmetal.solution.DoubleSolution;
import org.uma.jmetal.solution.PermutationSolution;
import org.uma.jmetal.util.AlgorithmRunner;
import org.uma.jmetal.util.JMetalLogger;
import org.uma.jmetal.util.comparator.RankingAndCrowdingDistanceComparator;
import org.uma.jmetal.util.fileoutput.SolutionListOutput;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {
        DoubleProblem problem;
        ScatterSearch<DoubleSolution, ScatterSearch.ReferenceSetDefinition, Character.Subset> algorithm;
        CrossoverOperator<DoubleSolution> crossover;
        MutationOperator<DoubleSolution> mutation;
//        SelectionOperator<List<PermutationSolution<Integer>>, PermutationSolution<Integer>> selection;

        JMetalLogger.logger.info("TSP");

        problem = (DoubleProblem) new TSP("/tsp/kroA100.tsp");

        crossover = new DifferentialEvolutionCrossover() ;
        mutation = new PolynomialMutation();

//        algorithm = new ABYSSBuilder(problem, null)
//                .setPopulationSize(100)
//                .setMaxEvaluations(100000)
//                .setCrossoverOperator(crossover)
//                .setMutationOperator(mutation)
//                //TODO here inject local search for improvement
////                .setImprovementOperator()
//                .build();

        algorithm = new ScatterSearch<>();

        AlgorithmRunner algorithmRunner = new AlgorithmRunner.Executor(algorithm)
                .execute() ;

        List<DoubleSolution> solution = algorithm.getResult() ;

        long computingTime = algorithmRunner.getComputingTime() ;

        new SolutionListOutput(solution)
                .print();

        JMetalLogger.logger.info("Total execution time: " + computingTime + "ms");
    }

}
