import java.io.*;
import java.util.*;
import java.util.Map.Entry;

public class RealEstate {

    private static final Scanner scanner = new Scanner(System.in);

    /*
     * Complete the realEstateBroker function below.
     */
    static int realEstateBroker(int[][] clientsArray, int[][] housesArray) {

        // houses sorted by increasing area
        TreeMap<Integer, Integer> housesMinPriceByArea = new TreeMap<>();

        // clients sorted by increasing min area
        TreeMap<Integer, Integer> clientsMaxPriceByMinArea = new TreeMap<>();

        Arrays.stream(clientsArray).forEach(c -> clientsMaxPriceByMinArea.put(c[0], c[1]));
        Arrays.stream(housesArray).forEach(h -> housesMinPriceByArea.put(h[0], h[1]));

        int total = 0;
        for (Entry<Integer, Integer> houseEntry : housesMinPriceByArea.entrySet()) {
            // find the lowest-requirenment client for this house area
            int houseArea = houseEntry.getKey();
            int housePrice = houseEntry.getValue();
            var optClientEntry = clientsMaxPriceByMinArea.headMap(houseArea, true).
                    entrySet().stream().
                    filter(cle -> housePrice <= cle.getValue()).
                    // find who is willing to pay more for this house
                    min(Comparator.comparingInt(Entry::getValue));
            if (optClientEntry.isPresent()) {
                ++total;
                clientsMaxPriceByMinArea.remove(optClientEntry.get().getKey());
            }
        }
        return total;
    }

    public static void main(String[] args) throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        String[] nm = scanner.nextLine().split(" ");

        int n = Integer.parseInt(nm[0].trim());

        int m = Integer.parseInt(nm[1].trim());

        int[][] clients = new int[n][2];

        for (int clientsRowItr = 0; clientsRowItr < n; clientsRowItr++) {
            String[] clientsRowItems = scanner.nextLine().split(" ");

            for (int clientsColumnItr = 0; clientsColumnItr < 2; clientsColumnItr++) {
                int clientsItem = Integer.parseInt(clientsRowItems[clientsColumnItr].trim());
                clients[clientsRowItr][clientsColumnItr] = clientsItem;
            }
        }

        int[][] houses = new int[m][2];

        for (int housesRowItr = 0; housesRowItr < m; housesRowItr++) {
            String[] housesRowItems = scanner.nextLine().split(" ");

            for (int housesColumnItr = 0; housesColumnItr < 2; housesColumnItr++) {
                int housesItem = Integer.parseInt(housesRowItems[housesColumnItr].trim());
                houses[housesRowItr][housesColumnItr] = housesItem;
            }
        }

        int result = realEstateBroker(clients, houses);

        bufferedWriter.write(String.valueOf(result));
        bufferedWriter.newLine();

        bufferedWriter.close();
    }
}
