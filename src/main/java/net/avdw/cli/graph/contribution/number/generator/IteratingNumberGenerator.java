package net.avdw.cli.graph.contribution.number.generator;

import java.util.List;

public class IteratingNumberGenerator implements NumberGenerator {
    private final List<Double> numbers;
    private final boolean wrap;
    private int currentIdx = -1;
    private int direction = 1;

    public IteratingNumberGenerator(final List<Double> numbers, final boolean wrap) {
        this.numbers = numbers;
        this.wrap = wrap;
    }

    /**
     * Generate a value.
     *
     * @return a value following the implementation rule
     */
    @Override
    public Double nextValue() {
        currentIdx += direction;

        if (currentIdx < 0 || currentIdx == numbers.size()) {
            if (wrap) {
                currentIdx %= numbers.size();
            } else {
                direction *= -1;
                currentIdx += 2 * direction;
            }
        }

        return numbers.get(currentIdx);
    }
}
