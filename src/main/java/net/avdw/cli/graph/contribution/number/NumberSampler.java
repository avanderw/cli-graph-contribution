package net.avdw.cli.graph.contribution.number;

import java.util.ArrayList;
import java.util.List;

public class NumberSampler {
    private final NumberInterpolater numberInterpolater;
    private final NumberNormaliser numberNormaliser;

    NumberSampler() {
        this(new NumberNormaliser(), new NumberInterpolater());
    }

    public NumberSampler(final NumberInterpolater numberInterpolater) {
        this(new NumberNormaliser(), numberInterpolater);
    }

    /**
     * @param numberNormaliser   the way in which to normalise the sample iteration
     * @param numberInterpolater the way in which to interpolate the numbers
     */
    public NumberSampler(final NumberNormaliser numberNormaliser, final NumberInterpolater numberInterpolater) {
        this.numberNormaliser = numberNormaliser;
        this.numberInterpolater = numberInterpolater;
    }

    /**
     * Sample a list of numbers from a range. Apply an interpolation function to the sampling.
     *
     * @param start       the start of the range
     * @param end         the end of the range
     * @param sampleCount the amount of samples to collect
     * @return a list of numbers samples from the range
     */
    public List<Double> sample(final double start, final double end, final int sampleCount) {
        List<Double> sampleList = new ArrayList<>();
        for (int i = 0; i < sampleCount; i++) {
            double weight = numberNormaliser.normalise(i, sampleCount - 1);
            sampleList.add(numberInterpolater.interpolate(start, end, weight));
        }

        return sampleList;
    }
}
