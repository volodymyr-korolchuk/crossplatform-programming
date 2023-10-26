package MeetingManager;

import Helpers.FileHelper;
import Helpers.PopulationHelper;
import Helpers.TokenizeHelper;

import java.time.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class MeetingManager {
    private final List<Participant> allParticipants = new ArrayList<>();

    private final List<Participant> selectedParticipants = new ArrayList<>();

    private int duration;

    public MeetingManager() {
        FileHelper fh = new FileHelper();
        TokenizeHelper th = new TokenizeHelper();
        PopulationHelper ph = new PopulationHelper();

        List<String> stringList = fh.getFileContent("input.txt");
        List<List<String>> participantData = new ArrayList<>();

        for (var str : stringList) {
            participantData.add(th.tokenizeString(str, " "));
        }

        ph.populateParticipantList(allParticipants, participantData);
        selectedParticipants.addAll(allParticipants);

        duration = 1;
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
            System.out.println("\nChoice: ");

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
                "\n4 - Display all participants" +
                "\n5 - Exit"
            );
            System.out.println("\nChoice: ");

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
                    case 5 -> exit = true;
                    default -> {
                    }
                }
            } catch (Exception exception) {
                System.out.println(exception.getMessage());
                return;
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
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter new meeting duration: ");

        String choiceString = scanner.nextLine().trim();
        System.out.println();
        if (choiceString.isEmpty()) return;

        try {
            int choice = Integer.parseInt(choiceString);
            if (choice <= 0 || choice >= 24) {
                System.out.println("[ Invalid duration value ]");
                return;
            }
            duration = choice;
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }

    private void GetMaxDuration() {


    }

    private void FormMeeting() {
        // convert all participants` time data to local time zone

        List<Participant> participants = new ArrayList<>();
        // deep list copy
        for (Participant ptp : selectedParticipants) {
            participants.add(new Participant(
                ptp.getFirstName(),
                ptp.getLastName(),
                ptp.getZoneID(),
                ptp.getSchedule()
            ));
        }

        // time data conversion
        ZoneId localTimeZone = ZoneId.systemDefault();
        for (var ptp: participants) {
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

        int size = participants.size();
        boolean breakCond = false;
        boolean isFound = false;
        HashMap<LocalTime[], DayOfWeek> meetingTimes = new HashMap<>();

        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < size - 1; j++) {
                // maybe remove later
                if (
                        participants.get(j).getSchedule()[i]
                                .intersectDuration(participants.get(j + 1).getSchedule()[i]
                                ).toHours() < duration)
                {
                    break;
                }
                else
                {
                    commonIntersect = participants.get(j).getSchedule()[i]
                            .getIntersect(participants.get(j + 1).getSchedule()[i]);
                }
                /*if (
                    participants.get(j).getSchedule()[i]
                    .intersects(participants.get(j + 1).getSchedule()[i])
                )
                {

                }*/
                for (int k = 2; k < size; k++) {
                    if (
                        participants.get(k).getSchedule()[i]
                        .intersectDuration(new Day(commonIntersect))
                    .toHours() < duration)
                    {
                        breakCond = true;
                        break;
                    }
                    if (k == size - 1) {
                        // all candidates can meet at the same time segment
                        isFound = true;
                    }
                }
                if (breakCond) break;
            }
            if (isFound) {
                meetingTimes.put(commonIntersect, DayOfWeek.values()[i]);
                isFound = false;
            }
        }

        if (!meetingTimes.isEmpty()) {
            System.out.println("[ Suitable times for a meeting ]");
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
