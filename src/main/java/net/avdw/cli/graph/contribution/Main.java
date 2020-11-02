package net.avdw.cli.graph.contribution;

import net.avdw.cli.graph.contribution.color.ColorConverter;
import net.avdw.cli.graph.contribution.color.ColorInterpolator;
import net.avdw.cli.graph.contribution.number.Interpolation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args)  {
        Map<LocalDate, Integer> dateMetricMap = new HashMap<>();
        dateMetricMap.put(LocalDate.of(2020, 7, 5), 32);
        dateMetricMap.put(LocalDate.of(2020, 3, 8), 16);
        dateMetricMap.put(LocalDate.of(2020, 8, 3), 4);
        dateMetricMap.put(LocalDate.of(2020, 2, 25), 24);
        dateMetricMap.put(LocalDate.of(2020, 1, 15), 16);
        print(LocalDate.now().plusYears(-1), dateMetricMap, 0xa5d6a7, 0x2e7d32, "metrics");
    }

    private static void print(final LocalDate fromDate, final Map<LocalDate, Integer> dateMetricMap, final int fromColor, final int toColor, final String metrics) {
        ColorConverter colorConverter = new ColorConverter();
        ColorInterpolator colorInterpolator = new ColorInterpolator(colorConverter);

        List<String> dateHeaderList = new ArrayList<>(Arrays.asList("Jan,Feb,Mar,Apr,May,June,Jul,Aug,Sep,Oct,Nov,Dec".split(",")));
        int shift = fromDate.getMonthValue();
        for (int i = 1; i < shift; i++) {
            dateHeaderList.add(dateHeaderList.remove(0));
        }
        String dateHeader = String.format("%s", dateHeaderList.stream().reduce("    ", (a, b) -> a + String.format("  %s ", b)));

        int total = dateMetricMap.values().stream().mapToInt(i -> i).sum();
        int maxMetric = dateMetricMap.values().stream().max(Comparator.naturalOrder()).orElseThrow();
        String character = "◼";

        List<String> sunList = new ArrayList<>(Collections.singletonList("Sun "));
        List<String> monList = new ArrayList<>(Collections.singletonList("Mon "));
        List<String> tueList = new ArrayList<>(Collections.singletonList("Tue "));
        List<String> wedList = new ArrayList<>(Collections.singletonList("Wed "));
        List<String> thuList = new ArrayList<>(Collections.singletonList("Thu "));
        List<String> friList = new ArrayList<>(Collections.singletonList("Fri "));
        List<String> satList = new ArrayList<>(Collections.singletonList("Sat "));

        switch (fromDate.getDayOfWeek().getValue()) {
            case 6: // Saturday
                friList.add("▫");
            case 5: // Friday
                thuList.add("▫");
            case 4: // Thursday
                wedList.add("▫");
            case 3: // Wednesday
                tueList.add("▫");
            case 2: // Tuesday
                monList.add("▫");
            case 1: // Monday
                sunList.add("▫");
            case 7: // Sunday
                break;
        }

        LocalDate endDate = fromDate.plusYears(1);
        LocalDate runningDate = fromDate;
        while (runningDate.isBefore(endDate)) {
            int color;
            if (dateMetricMap.containsKey(runningDate)) {
                color = colorInterpolator.interpolate(fromColor, toColor, dateMetricMap.get(runningDate).doubleValue() / maxMetric, Interpolation.SINE_EASE_OUT);
            } else {
                color = 0x999999;
            }
            String ansi = colorConverter.hexToAnsiFg(color);
            switch (runningDate.getDayOfWeek().getValue()) {
                case 7 -> sunList.add(String.format("%s%s\u001b[0m", ansi, character));
                case 1 -> monList.add(String.format("%s%s\u001b[0m", ansi, character));
                case 2 -> tueList.add(String.format("%s%s\u001b[0m", ansi, character));
                case 3 -> wedList.add(String.format("%s%s\u001b[0m", ansi, character));
                case 4 -> thuList.add(String.format("%s%s\u001b[0m", ansi, character));
                case 5 -> friList.add(String.format("%s%s\u001b[0m", ansi, character));
                case 6 -> satList.add(String.format("%s%s\u001b[0m", ansi, character));
            }
            runningDate = runningDate.plusDays(1);
        }

        switch (runningDate.getDayOfWeek().getValue()) {
            case 7: // Sunday
                break;
            case 1: // Monday
                monList.add("▫");
            case 2: // Tuesday
                tueList.add("▫");
            case 3: // Wednesday
                wedList.add("▫");
            case 4: // Thursday
                thuList.add("▫");
            case 5: // Friday
                friList.add("▫");
            case 6: // Saturday
                satList.add("▫");
        }

        System.out.println(dateHeader);
        System.out.println(String.join("", sunList));
        System.out.println(String.join("", monList));
        System.out.println(String.join("", tueList));
        System.out.println(String.join("", wedList));
        System.out.println(String.join("", thuList));
        System.out.println(String.join("", friList));
        System.out.println(String.join("", satList));

        String moreString = String.format("%sLess %s%s%s%s%s%s%s More\u001b[0m",
                colorConverter.hexToAnsiFg(colorInterpolator.interpolate(fromColor, toColor, 0., Interpolation.LINEAR)), character,
                colorConverter.hexToAnsiFg(colorInterpolator.interpolate(fromColor, toColor, .33, Interpolation.LINEAR)), character,
                colorConverter.hexToAnsiFg(colorInterpolator.interpolate(fromColor, toColor, .66, Interpolation.LINEAR)), character,
                colorConverter.hexToAnsiFg(colorInterpolator.interpolate(fromColor, toColor, 1., Interpolation.LINEAR)), character
        );

        System.out.printf("    %3s %s in the last year                          %s%n", total, metrics, moreString);
    }
}
