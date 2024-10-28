import java.util.ArrayList;
import java.util.Arrays;
public class ComboSum {
    public static void main(String[] args) {

        System.out.println("Test 1: Target 3");
        int[] test1 = {1, 2, 3};
        int sum1 = 3;
        CombinationSum(test1, sum1);

        System.out.println("\n\nTest 2: No possible solutions");
        int[] test2 = {7, 6, 9};
        int sum2 = 4;
        CombinationSum(test2, sum2);

        System.out.println("\n\nTest 3: Larger Array and sum");

        int[] test3 = {4, 2, 3, 8, 7};
        int sum3= 16;
        CombinationSum(test3, sum3);

    } // END driver main

    /* Requires an array of positive, non-zero integers WITHOUT repeating numbers */
    public static void CombinationSum (int[] array, int target) {
        ArrayList<Integer> path = new  ArrayList<Integer>();

        Arrays.sort(array);

        System.out.println("Solutions which add to " + target + ":");
        // index zero for start since all numbers are valid candidates
        CombinationSum(path, array, target, 0);
        return;
    } // END combination sum (caller)

    /* Helper: Difference starts as target, and has numbers gradually subtracted from it, until it reaches zero
     * (meaning a path has been found), it runs out of possible combinations, or the difference is negative, meaning
     * the path overshot the target.  */
    public static void CombinationSum (ArrayList<Integer> path, int[] array, int difference, int index) {
        if (difference == 0){
            // print path
            PrintPath(path);

            return;
        }

        for (int i = index; i < array.length; i++) {
            if (difference - array[i] >= 0){

                path.add(array[i]);

                // recursive call with next valid option
                CombinationSum(path, array, difference - array[i], i);

                // backtracks by popping the number added to the stack (undoing the add action)
                path.remove(path.size() - 1);

            }
        }
    } // END combination sum

    public static void PrintPath(ArrayList<Integer> path) {
        int i;
        for (i = 0; i < path.size() - 1; i++) {
            System.out.print(path.get(i) + " + ");
        }
        System.out.print(path.get(i));
        System.out.println();
    }


} // END class