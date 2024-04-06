import java.util.*;
import java.util.stream.Collectors;

import static java.util.Comparator.comparingInt;

final class ChangeCalculator {

    private final List<Integer> currencyCoins;

    ChangeCalculator(final List<Integer> currencyCoins) {
        this.currencyCoins = currencyCoins;
        Collections.sort(currencyCoins);
    }

    List<Integer> computeMostEfficientChange(final int grandTotal) {
        throw new UnsupportedOperationException("Delete this statement and write your own implementation.");
    }

}
