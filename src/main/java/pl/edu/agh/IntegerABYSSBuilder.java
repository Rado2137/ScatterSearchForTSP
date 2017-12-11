package pl.edu.agh;

import org.uma.jmetal.algorithm.multiobjective.abyss.ABYSS;
import org.uma.jmetal.operator.CrossoverOperator;
import org.uma.jmetal.operator.LocalSearchOperator;
import org.uma.jmetal.operator.MutationOperator;
import org.uma.jmetal.operator.impl.crossover.PMXCrossover;
import org.uma.jmetal.operator.impl.crossover.TwoPointCrossover;
import org.uma.jmetal.operator.impl.localsearch.ArchiveMutationLocalSearch;
import org.uma.jmetal.operator.impl.mutation.IntegerPolynomialMutation;
import org.uma.jmetal.operator.impl.mutation.PermutationSwapMutation;
import org.uma.jmetal.operator.impl.mutation.PolynomialMutation;
import org.uma.jmetal.problem.impl.AbstractIntegerPermutationProblem;
import org.uma.jmetal.solution.DoubleSolution;
import org.uma.jmetal.solution.PermutationSolution;
import org.uma.jmetal.util.AlgorithmBuilder;
import org.uma.jmetal.util.archive.Archive;
import org.uma.jmetal.util.archive.impl.CrowdingDistanceArchive;

/**
 *  @author Cristobal Barba
 */
public class IntegerABYSSBuilder implements AlgorithmBuilder<IntegerABYSS> {
    private AbstractIntegerPermutationProblem problem  ;
    private CrossoverOperator crossoverOperator   ;
    protected LocalSearchOperator<PermutationSolution<Integer>> improvementOperator ;

    private MutationOperator<PermutationSolution<Integer>> mutationOperator;
    private int numberOfSubranges;
    private int populationSize;
    private int refSet1Size;
    private int refSet2Size;
    private int archiveSize;
    private int maxEvaluations;
    private CrowdingDistanceArchive<PermutationSolution<Integer>> archive;

    public IntegerABYSSBuilder(AbstractIntegerPermutationProblem problem, Archive<PermutationSolution<Integer>> archive){
        this.populationSize = 20;
        this.maxEvaluations = 40;
        this.archiveSize = 100;
        this.refSet1Size = 10;
        this.refSet2Size = 10;
        this.numberOfSubranges = 4;
        this.problem = problem;
        double crossoverProbability = 0.9;
        double distributionIndex=20.0;
        this.crossoverOperator = new PMXCrossover(crossoverProbability);
        double mutationProbability= 1.0/problem.getNumberOfVariables();
        this.mutationOperator = new PermutationSwapMutation<>(mutationProbability);
        int improvementRounds= 1;
        this.archive =(CrowdingDistanceArchive<PermutationSolution<Integer>>)archive;
        this.improvementOperator = new ArchiveMutationLocalSearch<PermutationSolution<Integer>>(improvementRounds,mutationOperator,this.archive,problem);
    }

    @Override
    public IntegerABYSS build() {
        return new IntegerABYSS(problem, maxEvaluations, populationSize,refSet1Size,refSet2Size,archiveSize,
                archive, improvementOperator, crossoverOperator, numberOfSubranges);
    }
}