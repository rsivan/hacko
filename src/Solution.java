import java.io.*;
import java.math.*;
import java.security.*;
import java.text.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.function.*;
import java.util.regex.*;
import java.util.stream.*;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

class Result {

    /*
     * Complete the 'queensAttack' function below.
     *
     * The function is expected to return an INTEGER.
     * The function accepts following parameters:
     *  1. INTEGER n
     *  2. INTEGER k
     *  3. INTEGER r_q
     *  4. INTEGER c_q
     *  5. 2D_INTEGER_ARRAY obstacles
     */

    public static int queensAttack(int n, int k, int r_q, int c_q, List<List<Integer>> obstacles) {
        // Write your code here

        int rightSteps = n - c_q;
        int leftSteps = c_q - 1;
        int upSteps = n - r_q;
        int downSteps = r_q - 1;
        int upRightSteps = Math.min(rightSteps, upSteps);
        int downRightSteps = Math.min(rightSteps, downSteps);
        int downLeftSteps = Math.min(leftSteps, downSteps);
        int upLeftSteps = Math.min(leftSteps, upSteps);

        for (List<Integer> obs : obstacles) {
            final int row = obs.get(0);
            final int col = obs.get(1);

            if (row > r_q) {
                if (col == c_q) {
                    // up
                    upSteps = Math.min(upSteps, row - r_q - 1);
                } else if (col > c_q) {
                    if (row - r_q == col - c_q) {
                        // upRight diag
                        upRightSteps = Math.min(upRightSteps, row - r_q - 1);
                    }
                } else {
                    // col < c_q
                    if (row - r_q == c_q - col) {
                        // upLeft diag
                        upLeftSteps = Math.min(upLeftSteps, row - r_q - 1);
                    }
                }
            } else if (row == r_q) {
                if (col > c_q) {
                    // right
                    rightSteps = Math.min(rightSteps, col - c_q - 1);
                } else {
                    // left
                    leftSteps = Math.min(leftSteps, c_q - col - 1);
                }
            } else {
                // row < r_q
                if (col == c_q) {
                    // down
                    downSteps = Math.min(downSteps, r_q - row - 1);
                } else if (col > c_q) {
                    if (r_q - row == col - c_q) {
                        // downRight diag
                        downRightSteps = Math.min(downRightSteps, r_q - row - 1);
                    }
                } else {
                    // col < c_q
                    if (r_q - row == c_q - col) {
                        // downLeft diag
                        downLeftSteps = Math.min(downLeftSteps, r_q - row - 1);
                    }
                }
            }
        }

        return upSteps + upRightSteps + upLeftSteps + downSteps + downRightSteps + downLeftSteps + rightSteps + leftSteps;
    }

}

public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        String[] firstMultipleInput = bufferedReader.readLine().replaceAll("\\s+$", "").split(" ");

        int n = Integer.parseInt(firstMultipleInput[0]);

        int k = Integer.parseInt(firstMultipleInput[1]);

        String[] secondMultipleInput = bufferedReader.readLine().replaceAll("\\s+$", "").split(" ");

        int r_q = Integer.parseInt(secondMultipleInput[0]);

        int c_q = Integer.parseInt(secondMultipleInput[1]);

        List<List<Integer>> obstacles = new ArrayList<>();

        IntStream.range(0, k).forEach(i -> {
            try {
                obstacles.add(
                        Stream.of(bufferedReader.readLine().replaceAll("\\s+$", "").split(" "))
                                .map(Integer::parseInt)
                                .collect(toList())
                );
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        int result = Result.queensAttack(n, k, r_q, c_q, obstacles);

        bufferedWriter.write(String.valueOf(result));
        bufferedWriter.newLine();

        bufferedReader.close();
        bufferedWriter.close();
    }
}
