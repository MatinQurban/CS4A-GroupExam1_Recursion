// import java.util.ArrayList;

import java.util.HashSet;
import java.util.Set;


public class PermOfString {
    public static void main(String[] args) {
        // String test1 = "cat";
        // ArrayList<String> result1 = PermutationOfAString(test1);
        // PrintList(result1);
        // System.out.println();

        // String test2 = "word";
        // ArrayList<String> result2 = PermutationOfAString(test2);
        // PrintList(result2);
        // System.out.println();

        // String test3 = "012";
        // ArrayList<String> result3 = PermutationOfAString(test3);
        // PrintList(result3);
        // System.out.println();
        String test4 = "wordd";
        Set<String> result4 = PermutationOfAString(test4);
        PrintList(result4);
        System.out.println();


    } // end main

    public static Set<String> PermutationOfAString(String str) {
        Set<String> list = new HashSet<>();
        char[] arr = str.toCharArray();

        System.out.println("Permutations of " + str + ": ");
        Perm(arr, list, 0);

        return list;
    } // END PermutationOfAString

    public static void Perm(char[] arr, Set<String> list, int index) {

        String element = String.valueOf(arr);

        if(index == arr.length &&  list.contains(element) == false) {
            list.add(element);
        }

        for (int i = index; i < arr.length; i++) {
            Swap(index, i, arr);

            Perm(arr, list, index + 1);

            Swap(index, i, arr);
        }

    } // END Perm (helper)

    public static void Swap(int index1, int index2, char[] arr) {
        char temp = arr[index1];
        arr[index1] = arr[index2];
        arr[index2] = temp;
    } // END Swap

    public static void PrintList(Set<String> list) {
        int i = 1;
        for (String element: list)
        {
            System.out.println(i + ". " + element);
            i++;
        }
    } // END PrintList

} // END SolutionOne