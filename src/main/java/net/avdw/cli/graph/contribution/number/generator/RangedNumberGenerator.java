package net.avdw.cli.graph.contribution.number.generator;

public class RangedNumberGenerator implements NumberGenerator {
    private final Double max;
    private final Double min;
    private final RandomGenerator randomGenerator;

    RangedNumberGenerator(final Double min, final Double max, final RandomGenerator randomGenerator) {
        this.min = min;
        this.max = max;
        this.randomGenerator = randomGenerator;
    }

    /**
     * Generate a value.
     *
     * @return a value following the implementation rule
     */
    @Override
    public Double nextValue() {
        return randomGenerator.nextBetween(min, max);
    }
}
