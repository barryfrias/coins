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
        return findChange(currencyCoins, grandTotal);
    }

    /**
     * Finds the most efficient change for the given total using a subset of currency coins.
     *
     * @param coins the list of currency coins
     * @param total the total amount for which change is to be computed
     * @return the list of coins representing the most efficient change
     */
    private static List<Integer> findChange(List<Integer> coins, int total) {
        final SortedSet<List<Integer>> results = new TreeSet<>(Comparator.comparingInt(List::size));

        coins.stream()
            .map(i -> coins.subList(0, coins.indexOf(i) + 1)) // Create subsets of coins
            .map(subsetCoins -> calculateChange(subsetCoins, total)) // Calculate change for each subset
            .filter(change -> !change.isEmpty()) // Filter out empty change lists
            .forEach(results::add); // Add non-empty change lists to the results set

        if (results.isEmpty()) {
            throw new IllegalArgumentException("The total " + total + " cannot be represented in the given currency.");
        }

        return results.first();
    }

    /**
     * Calculates the change for the given total using a subset of currency coins.
     *
     * @param coins the list of currency coins to be used
     * @param total the total amount for which change is to be computed
     * @return the list of coins representing the change for the given total
     */
    private static List<Integer> calculateChange(final List<Integer> coins, final int total) {
        final Map<Integer, List<Integer>> resultsMap = new HashMap<>(total); // results cache

        // Calculate change for each coin denomination using lambda expressions
        coins.forEach(coin -> {
            for (int j = coin; j <= total; j++) {
                if (!resultsMap.getOrDefault(j - coin, of()).isEmpty() || j - coin == 0) {
                    final List<Integer> newChange = new ArrayList<>(resultsMap.getOrDefault(j - coin, of()));
                    newChange.add(coin);
                    resultsMap.put(j, newChange);
                }
            }
        });

        return resultsMap.getOrDefault(total, of()); // Return the list representing the change for the given total
    }
}