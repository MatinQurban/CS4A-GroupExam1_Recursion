import java.util.ArrayList;


public class PermOfString {
    public static void run(String[] args) {
        String test1 = "cat";
        ArrayList<String> result1 = PermutationOfAString(test1);
        PrintList(result1);
        System.out.println();

        String test2 = "word";
        ArrayList<String> result2 = PermutationOfAString(test2);
        PrintList(result2);
        System.out.println();

        String test3 = "012";
        ArrayList<String> result3 = PermutationOfAString(test3);
        PrintList(result3);
        System.out.println();


    } // end main

    public static ArrayList<String> PermutationOfAString(String str) {
        ArrayList<String> list = new ArrayList<>();
        char[] arr = str.toCharArray();

        System.out.println("Permutations of " + str + ": ");
        Perm(arr, list, 0);

        return list;
    } // END PermutationOfAString

    public static void Perm(char[] arr, ArrayList<String> list, int index) {

        if(index == arr.length) {
            list.add(String.valueOf(arr));
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

    public static void PrintList(ArrayList<String> list) {
        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i));
        }
    } // END PrintList

} // END SolutionOne