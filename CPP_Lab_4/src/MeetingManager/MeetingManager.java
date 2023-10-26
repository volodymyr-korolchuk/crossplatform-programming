package MeetingManager;

import Helpers.FileHelper;
import Helpers.PopulationHelper;
import Helpers.TokenizeHelper;

import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class MeetingManager {

    private final List<Participant> selectedParticipants = new ArrayList<>();

    private List<Participant> zonedParticipants = new ArrayList<>();

    private final HashMap<LocalTime[], DayOfWeek> meetingTimes = new HashMap<>();

    private Duration duration;

    public MeetingManager() {
        FileHelper fh = new FileHelper();
        TokenizeHelper th = new TokenizeHelper();
        PopulationHelper ph = new PopulationHelper();

        List<String> stringList = fh.getFileContent("input.txt");
        List<List<String>> participantData = new ArrayList<>();

        for (var str : stringList) {
            participantData.add(th.tokenizeString(str, " :"));
        }

        ph.populateParticipantList(selectedParticipants, participantData);
        duration = Duration.parse("PT1H30M");
    }

    public void start() {
        int choice;
        boolean exit = false;
        Scanner scanner = new Scanner(System.in);

        while(!exit) {
            System.out.println("\n\t--- Menu ---");
            System.out.println(
                "\n1 - Create new meeting" +
                "\n2 - Modify available participants" +
                "\n3 - Exit"
            );
            System.out.print("\nChoice: ");

            String choiceString = scanner.nextLine().trim();
            System.out.println();
            if (choiceString.isEmpty()) continue;
            if (choiceString.equals("exit")) break;

            try {
                choice = Integer.parseInt(choiceString);
                switch (choice) {
                    case 1 -> createNewMeeting();
                    case 2 -> modifyParticipants();
                    case 3 -> exit = true;
                    default -> {
                    }
                }
            } catch (Exception exception) {
                System.out.println(exception.getMessage());
                return;
            }

        }
    }

    public void createNewMeeting() {
        int choice;
        boolean exit = false;
        Scanner scanner = new Scanner(System.in);

        while(!exit) {
            System.out.println("\n\t--- Menu ---");
            System.out.println(
                "\n1 - Change meeting duration" +
                "\n2 - Get max possible duration" +
                "\n3 - Form meeting" +
                "\n4 - Display participant list" +
                "\n5 - Display time shifted participant list" +
                "\n6 - Exit"
            );
            System.out.print("\nChoice: ");

            String choiceString = scanner.nextLine().trim();
            System.out.println();
            if (choiceString.isEmpty()) continue;
            if (choiceString.equals("exit")) break;

            try {
                choice = Integer.parseInt(choiceString);
                switch (choice) {
                    case 1 -> ChangeDuration();
                    case 2 -> GetMaxDuration();
                    case 3 -> FormMeeting();
                    case 4 -> DisplayParticipants();
                    case 5 -> DisplayConvertedParticipants();
                    case 6 -> exit = true;
                    default -> {
                    }
                }
            } catch (Exception exception) {
                System.out.println(exception.getMessage());
                return;
            }
        }
    }

    private void DisplayConvertedParticipants() {
        System.out.println("\n\t --- Time shifted participants --- \n");
        for (var ptp : zonedParticipants) {
            System.out.printf("%n%s %s %s %n",
                ptp.getFirstName(),
                ptp.getLastName(),
                ptp.getZoneID()
            );

            for (int i = 0; i < 7; i++) {
                System.out.printf("%-9s - from %s to %s %n",
                    DayOfWeek.values()[i],
                    ptp.getSchedule()[i].getSegment()[0],
                    ptp.getSchedule()[i].getSegment()[1]
                );
            }
        }
    }

    public void modifyParticipants() {}

    private void DisplayParticipants() {
        for (var ptp : selectedParticipants) {
            ptp.displayData();
        }
    }

    private void ChangeDuration() {
        System.out.println("Current duration is: " + duration.toHours() + "h " + duration.toMinutesPart() + "m");
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter hours: ");
        String hours = scanner.nextLine().trim();
        if (hours.isEmpty()) return;

        System.out.print("Enter minutes: ");
        String minutes = scanner.nextLine().trim();
        System.out.println();
        if (minutes.isEmpty()) return;

        try {
            int hoursValue = Integer.parseInt(hours);
            int minutesValue = Integer.parseInt(minutes);
            if ((hoursValue < 0 || hoursValue >= 24) || (minutesValue < 0 || minutesValue >= 60)) {
                System.out.println("[ Invalid duration value ]");
                return;
            }
            duration = Duration.parse("PT" + hoursValue + "H" + minutesValue + "M");
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }

    private void GetMaxDuration() {
        if (meetingTimes.isEmpty()) {
            System.out.println("[ No max duration. Form a meeting first ]");
            return;
        }

        Duration maxDuration = Duration.parse("PT0H0M");
        System.out.println("\t --- Suitable times for a meeting --- \n");
        for (var time : meetingTimes.keySet()) {
            System.out.printf(
                "%-9s From: %s To: %s %n",
                meetingTimes.get(time),
                time[0],
                time[1]
            );
        }

        for (var key : meetingTimes.keySet()) {
            Duration currDuration = Duration.between(key[0], key[1]);
            if (maxDuration.compareTo(currDuration) < 0) {
               maxDuration = currDuration;
            }
        }
        System.out.println("\nMax meeting duration is: " + maxDuration.toHours() + "h " + maxDuration.toMinutesPart() + "m");
    }

    private void FormMeeting() {
        // convert all participants` time data to local time zone
        meetingTimes.clear();
        zonedParticipants.clear();
        zonedParticipants = new ArrayList<>();

        // deep list copy
        for (Participant ptp : selectedParticipants) {
            zonedParticipants.add(new Participant(
                ptp.getFirstName(),
                ptp.getLastName(),
                ptp.getZoneID(),
                ptp.getSchedule()
            ));
        }

        // time data conversion
        ZoneId localTimeZone = ZoneId.systemDefault();
        for (var ptp: zonedParticipants) {
            for (var day : ptp.getSchedule()) {
                day.setSegment(
                    convertToOtherZone(
                        day.getSegment(),
                        ptp.getZoneID(),
                        localTimeZone
                    )
                );
            }
        }

        // time when all the participants will be able to join
        LocalTime[] commonIntersect = new LocalTime[2];
        commonIntersect[0] = LocalTime.MIN;
        commonIntersect[1] = LocalTime.MIN;

        int size = zonedParticipants.size();
        boolean notCompatibleFound = false;
        boolean isFound = false;

        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < size - 1; j++) {
                // check if two first participants` availability hours intersect and if the length of this segment
                // satisfies the minimal duration constraint
                Duration intersectDuration = zonedParticipants.get(j).getSchedule()[i]
                        .intersectDuration(zonedParticipants.get(j + 1).getSchedule()[i]);

                if (intersectDuration.compareTo(duration) < 0) {
                    break;
                } else {
                    // check if two first participants can join at the same time
                    commonIntersect = zonedParticipants.get(j).getSchedule()[i]
                    .getIntersect(zonedParticipants.get(j + 1).getSchedule()[i]);
                }

                for (int k = 0; k < size; k++) {
                    intersectDuration = (new Day(commonIntersect).intersectDuration(zonedParticipants.get(k).getSchedule()[i]));

                    if (intersectDuration.compareTo(duration) < 0) {
                        // found a participant that can`t join
                        notCompatibleFound = true;
                        break;
                    } else {
                        commonIntersect = (new Day(commonIntersect)).getIntersect(zonedParticipants.get(k).getSchedule()[i]);
                    }

                    if (k == size - 1) {
                        // all candidates can meet at the same time segment
                        isFound = true;
                    }
                }
                // if found not compatible - stop checking current day
                if (notCompatibleFound) break;
            }
            // if meeting time found - add to meeting times list
            if (isFound) {
                meetingTimes.put(commonIntersect, DayOfWeek.values()[i]);
                isFound = false;
            }
        }

        if (!meetingTimes.isEmpty()) {
            System.out.println("\t --- Suitable times for a meeting --- \n");
            for (var time : meetingTimes.keySet()) {
                System.out.printf(
                    "%-9s From: %s To: %s %n",
                    meetingTimes.get(time),
                    time[0],
                    time[1]
                );
            }
        } else {
            System.out.println("[ Cannot form a meeting ]");
        }
    }

    private LocalTime[] convertToOtherZone(LocalTime[] segment, ZoneId ptpTimeZone, ZoneId targetZone) {
        LocalTime start = segment[0];
        LocalTime end = segment[1];

        ZonedDateTime sourceStartZdt = ZonedDateTime.of(
            LocalDate.now(),
            LocalTime.of(
                start.getHour(),
                start.getMinute()),
            ptpTimeZone
        );

        ZonedDateTime sourceEndZdt = ZonedDateTime.of(
            LocalDate.now(),
            LocalTime.of(
                end.getHour(),
                end.getMinute()),
            ptpTimeZone
        );

        ZonedDateTime convertedStartZdt = sourceStartZdt.withZoneSameInstant(targetZone);
        ZonedDateTime convertedEndZdt = sourceEndZdt.withZoneSameInstant(targetZone);

        LocalTime[] convertedSegment = new LocalTime[2];

        convertedSegment[0] = convertedStartZdt.toLocalTime();
        convertedSegment[1] = convertedEndZdt.toLocalTime();

        return convertedSegment;
    }

}
