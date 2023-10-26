package MeetingManager;

import java.time.DayOfWeek;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

public class Participant {
    private String firstName;
    private String lastName;
    private ZoneId zoneID;
    private Day[] schedule = new Day[]
    {
        new Day(),
        new Day(),
        new Day(),
        new Day(),
        new Day(),
        new Day(),
        new Day()
    };

    public Participant(String firstName, String lastName, ZoneId zoneID) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.zoneID = zoneID;
    }

    public Participant(String firstName, String lastName, ZoneId zoneID, Day[] schedule) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.zoneID = zoneID;
        for (int i = 0; i < schedule.length; i++) {
            this.schedule[i].setSegment(schedule[i].getSegment());
        }
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public ZoneId getZoneID() {
        return zoneID;
    }

    public Day[] getSchedule() {
        return schedule;
    }

    public void displayData() {
        System.out.printf("%n%s %s %s %n",
            getFirstName(),
            getLastName(),
            getZoneID()
        );

        for (int i = 0; i < 7; i++) {
            System.out.printf("%-9s - from %s to %s %n",
                DayOfWeek.values()[i],
                getSchedule()[i].getSegment()[0],
                getSchedule()[i].getSegment()[1]
            );
        }
    }

}
