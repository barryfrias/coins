import java.util.*;

import static java.util.List.of;

/**
 * A class to calculate the most efficient change for a given total using a given set of currency coins.
 */
final public class ChangeCalculator {

    /** The list of currency coins available. */
    private final List<Integer> currencyCoins;

    /**
     * Constructs a {@code ChangeCalculator} object with the specified currency coins.
     *
     * @param currencyCoins the list of currency coins
     */
    public ChangeCalculator(final List<Integer> currencyCoins) {
        this.currencyCoins = currencyCoins;
        Collections.sort(currencyCoins);
    }

    /**
     * Computes the most efficient change for the given total.
     *
     * @param grandTotal the total amount for which change is to be computed
     * @return the list of coins representing the most efficient change
     * @throws IllegalArgumentException if the total is negative or cannot be represented with the given coins
     */
    public List<Integer> computeMostEfficientChange(final int grandTotal) {
        if (grandTotal < 0) {
            throw new IllegalArgumentException("Negative totals are not allowed.");
        }

        if(grandTotal == 0) {
            return of();
        }

        // Find the most efficient change using dynamic programming
        var result = findChange(currencyCoins, grandTotal);

        if (result.isEmpty()) {
            throw new IllegalArgumentException("The total " + grandTotal + " cannot be represented in the given currency.");
        }

        return result;
    }

    /**
     * Finds the most efficient change for the given total using a subset of currency coins.
     *
     * @param coins the list of currency coins
     * @param total the total amount for which change is to be computed
     * @return the list of coins representing the most efficient change
     */
    private static List<Integer> findChange(List<Integer> coins, int total) {
        final Map<Integer, List<Integer>> resultsMap = new HashMap<>(total); // results cache
        System.out.println("total=" + total + ", denominations=" + coins);
        // Calculate change for each coin denomination using lambda expressions
        coins.forEach(coin -> {
            for (int j = coin; j <= total; j++) {
//                System.out.println("j=" + j + ", coin="+ coin + ", j-coin=" + (j-coin));
//                System.out.println("current map: " + resultsMap);
                if (resultsMap.containsKey(j - coin) || j - coin == 0) {
                    final List<Integer> newChange = new ArrayList<>(resultsMap.getOrDefault(j - coin, of()));
//                    System.out.println("found: " + newChange);
                    newChange.add(coin);
                    if(!resultsMap.containsKey(j) || resultsMap.get(j).size() > newChange.size()) {
                        resultsMap.put(j, newChange);
                    }
//                   System.out.println("updated map: " + resultsMap);
                }
            }
        });

        return resultsMap.getOrDefault(total, of());
    }
}