package Helpers;

import MeetingManager.Day;
import MeetingManager.Participant;

import java.time.LocalTime;
import java.time.ZoneId;
import java.util.List;

public class PopulationHelper {
    public void populateParticipantList(List<Participant> participants, List<List<String>> stringList) {
        for (var ptp : stringList) {
            Day[] schedule = new Day[7];
            for (int i = 2, j = 0; i < ptp.size() - 2; i += 2, j++) {
                schedule[j] = new Day();

                schedule[j].setSegment(
                    new LocalTime[] {
                        LocalTime.of(
                            Integer.parseInt(ptp.get(i)),
                            0
                        ),
                        LocalTime.of(
                            Integer.parseInt(ptp.get(i + 1)),
                            0
                        )
                    }
                );
            }

            participants.add(
                new Participant(
                    ptp.get(0),
                    ptp.get(1),
                    ZoneId.of(ptp.get(ptp.size() - 1)),
                    schedule
                )
            );
        }
    }
}
