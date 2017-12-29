package pl.edu.agh;

import org.uma.jmetal.operator.LocalSearchOperator;
import org.uma.jmetal.problem.ConstrainedProblem;
import org.uma.jmetal.problem.Problem;
import org.uma.jmetal.solution.PermutationSolution;
import org.uma.jmetal.solution.Solution;
import org.uma.jmetal.util.archive.Archive;
import org.uma.jmetal.util.comparator.DominanceComparator;
import org.uma.jmetal.util.comparator.impl.OverallConstraintViolationComparator;

import java.util.Comparator;
import java.util.List;

/**
 * @author Radoslaw Lesniewski
 * Created on 2017-12-19.
 */
public class MultiNeighbourhoodSearch<S extends Solution<?>> implements LocalSearchOperator<S> {
    private final Archive<S> archive;
    private Problem<S> problem;
    private int improvementRounds;
    private Comparator<S> constraintComparator;
    private Comparator<S> dominanceComparator;

    private NeighbourhoodGenerator<S> mutationOperator;
    private int evaluations;

    private int numberOfImprovements;
    private int numberOfNonComparableSolutions;

    public MultiNeighbourhoodSearch(int improvementRounds, Archive<S> archive, Problem<S> problem) {
        this.problem = problem;
        this.archive = archive;
        this.mutationOperator = new NeighbourhoodGenerator<S>(improvementRounds);
        this.improvementRounds = improvementRounds;
        dominanceComparator = new DominanceComparator<S>();
        constraintComparator = new OverallConstraintViolationComparator<S>();

        numberOfImprovements = 0;
        numberOfNonComparableSolutions = 0;
    }

    public S execute(S solution) {
        int i = 0;
        int best;
        evaluations = 0;
        numberOfNonComparableSolutions = 0;

        int rounds = improvementRounds;

        while (i < rounds) {
            List<PermutationSolution<S>> mutatedSolutions = mutationOperator.execute((PermutationSolution<S>) solution.copy());
            for (PermutationSolution<S> mutatedSolution : mutatedSolutions) {

                if (problem.getNumberOfConstraints() > 0) {
                    ((ConstrainedProblem<S>) problem).evaluateConstraints((S) mutatedSolution);
                    best = constraintComparator.compare((S) mutatedSolution, solution);
                    if (best == 0) {
                        problem.evaluate((S) mutatedSolution);
                        evaluations++;
                        best = dominanceComparator.compare((S) mutatedSolution, solution);
                    } else if (best == -1) {
                        problem.evaluate((S) mutatedSolution);
                        evaluations++;
                    }
                } else {
                    problem.evaluate((S) mutatedSolution);
                    evaluations++;
                    best = dominanceComparator.compare((S) mutatedSolution, solution);
                }
                if (best == -1) {
                    solution = (S) mutatedSolution;
                    numberOfImprovements++;
                } else if (best == 1) {
                    ;
                } else {
                    numberOfNonComparableSolutions++;
                    archive.add((S) mutatedSolution);
                }
            }
            i++;
        }
        return (S) solution.copy();
    }

    @Override
    public int getEvaluations() {
        return evaluations;
    }

    @Override
    public int getNumberOfImprovements() {
        return numberOfImprovements;
    }

    @Override
    public int getNumberOfNonComparableSolutions() {
        return numberOfNonComparableSolutions;
    }
}
