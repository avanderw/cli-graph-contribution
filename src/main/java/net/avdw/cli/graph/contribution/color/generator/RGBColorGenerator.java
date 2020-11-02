package net.avdw.cli.graph.contribution.color.generator;

import net.avdw.cli.graph.contribution.color.RGB;
import net.avdw.cli.graph.contribution.number.generator.NumberGenerator;

public class RGBColorGenerator implements ColorGenerator {
    private final NumberGenerator blueGenerator;
    private final NumberGenerator greenGenerator;
    private final NumberGenerator redGenerator;

    RGBColorGenerator(final NumberGenerator redGenerator, final NumberGenerator greenGenerator, final NumberGenerator blueGenerator) {
        this.redGenerator = redGenerator;
        this.greenGenerator = greenGenerator;
        this.blueGenerator = blueGenerator;
    }

    /**
     * Generate a color in the RGB format.
     * R in range [0..1]
     * G in range [0..1]
     * B in range [0..1]
     *
     * @return color in RGB
     */
    @Override
    public RGB generateRGB() {
        double red = Math.max(0, Math.min(1, redGenerator.nextValue()));
        double green = Math.max(0, Math.min(1, greenGenerator.nextValue()));
        double blue = Math.max(0, Math.min(1, blueGenerator.nextValue()));
        return new RGB(red, green, blue);
    }
}
