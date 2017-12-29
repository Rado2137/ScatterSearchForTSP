package pl.edu.agh;

import org.uma.jmetal.solution.PermutationSolution;
import org.uma.jmetal.util.JMetalException;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Radoslaw Lesniewski
 * Created on 2017-12-29.
 */
public class NeighbourhoodGenerator<T> {
    private int improvementRounds;

    public NeighbourhoodGenerator(int improvementRounds) {
        this.improvementRounds = improvementRounds;
    }

    public List<PermutationSolution<T>> execute(PermutationSolution<T> solution) {
        if (null == solution) {
            throw new JMetalException("Null parameter");
        }
        List<PermutationSolution<T>> listOfSolutions = new LinkedList<>();

        int permutationLength;
        permutationLength = solution.getNumberOfVariables();

        int index = 0;

        if ((permutationLength != 0) && (permutationLength != 1) && improvementRounds != 1) {
            while (index < permutationLength) {

                int pos1 = index;
                for (int i = 0; i < permutationLength; i++) {
                    int pos2 = i;

                    if (pos2 != index) {
                        PermutationSolution<T> solutionCopy = (PermutationSolution<T>) solution.copy();
                        T temp = solutionCopy.getVariableValue(pos1);
                        solutionCopy.setVariableValue(pos1, solutionCopy.getVariableValue(pos2));
                        solutionCopy.setVariableValue(pos2, temp);
                        listOfSolutions.add(solutionCopy);
                    }

                }

                index++;
            }
        }
        return listOfSolutions;
    }

}
