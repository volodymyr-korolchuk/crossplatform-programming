package MeetingManager;

import java.time.Duration;
import java.time.LocalTime;

public class Day {
    private LocalTime[] timeSegment = new LocalTime[2];

    public Day() {

    }

    public Day(LocalTime[] timeSegment) {
        this.timeSegment = timeSegment;
    }


    public LocalTime[] getSegment() {
        return timeSegment;
    }

    public void setSegment(LocalTime[] segment) {
        timeSegment = new LocalTime[2];
        timeSegment[0] = segment[0];
        timeSegment[1] = segment[1];
    }

    public boolean intersects(Day other) {
        return !this.timeSegment[1].isBefore(other.timeSegment[0]) &&
               !other.timeSegment[1].isBefore(this.timeSegment[0]);
    }

    public LocalTime[] getIntersect(Day other) {
        // Intersection start time
        LocalTime intersectionStart =
            this.timeSegment[0].isAfter(other.timeSegment[0])
                ? this.timeSegment[0]
                : other.timeSegment[0];

        // Intersection end time
        LocalTime intersectionEnd =
            this.timeSegment[1].isBefore(other.timeSegment[1])
                ? this.timeSegment[1]
                : other.timeSegment[1];

        LocalTime[] intersect = new LocalTime[2];
        intersect[0] = LocalTime.of(intersectionStart.getHour(), intersectionStart.getMinute());
        intersect[1] = LocalTime.of(intersectionEnd.getHour(), intersectionEnd.getMinute());

        return intersect;
    }

    public Duration intersectDuration(Day other) {
        // Intersection start time
        LocalTime intersectionStart =
            this.timeSegment[0].isAfter(other.timeSegment[0])
                ? this.timeSegment[0]
                : other.timeSegment[0];

        // Intersection end time
        LocalTime intersectionEnd =
            this.timeSegment[1].isBefore(other.timeSegment[1])
                ? this.timeSegment[1]
                : other.timeSegment[1];

        // Check if the start point is before the end point
        if (intersectionStart.isBefore(intersectionEnd)) {
            return Duration.between(intersectionStart, intersectionEnd);
        } else {
            // No intersection
            return Duration.ZERO;
        }
    }


}
