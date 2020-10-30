package net.avdw.cli.graph.contribution;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Main {
    public static void main(String[] args) throws ParseException {

        String dateHeader = "      Jan   Feb   Mar   Apr   May   June   Jul   Aug   Sep   Oct   Nov   Dec";
        String character = "◼";

        List<String> sunList = new ArrayList<>(Collections.singletonList("Sun "));
        List<String> monList = new ArrayList<>(Collections.singletonList("Mon "));
        List<String> tueList = new ArrayList<>(Collections.singletonList("Tue "));
        List<String> wedList = new ArrayList<>(Collections.singletonList("Wed "));
        List<String> thuList = new ArrayList<>(Collections.singletonList("Thu "));
        List<String> friList = new ArrayList<>(Collections.singletonList("Fri "));
        List<String> satList = new ArrayList<>(Collections.singletonList("Sat "));

        int year = 2020;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date endDate = simpleDateFormat.parse(String.format("%d-01-01", year + 1));

        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(simpleDateFormat.parse(String.format("%d-01-01", year)));
        switch (calendar.get(Calendar.DAY_OF_WEEK)) {
            case Calendar.SATURDAY:
                friList.add("▫");
            case Calendar.FRIDAY:
                thuList.add("▫");
            case Calendar.THURSDAY:
                wedList.add("▫");
            case Calendar.WEDNESDAY:
                tueList.add("▫");
            case Calendar.TUESDAY:
                monList.add("▫");
            case Calendar.MONDAY:
                sunList.add("▫");
            case Calendar.SUNDAY:
                break;
        }

        while (calendar.getTime().before(endDate)) {
            switch (calendar.get(Calendar.DAY_OF_WEEK)) {
                case Calendar.SUNDAY -> sunList.add(character);
                case Calendar.MONDAY -> monList.add(character);
                case Calendar.TUESDAY -> tueList.add(character);
                case Calendar.WEDNESDAY -> wedList.add(character);
                case Calendar.THURSDAY -> thuList.add(character);
                case Calendar.FRIDAY -> friList.add(character);
                case Calendar.SATURDAY -> satList.add(character);
            }
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }

        switch (calendar.get(Calendar.DAY_OF_WEEK)) {
            case Calendar.SUNDAY:
                break;
            case Calendar.MONDAY:
                monList.add("▫");
            case Calendar.TUESDAY:
                tueList.add("▫");
            case Calendar.WEDNESDAY:
                wedList.add("▫");
            case Calendar.THURSDAY:
                thuList.add("▫");
            case Calendar.FRIDAY:
                friList.add("▫");
            case Calendar.SATURDAY:
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

        System.out.printf("    235 <metric>s in the last year%30s %s%s%s%s More%n", "Less", character, character, character, character);
    }
}
