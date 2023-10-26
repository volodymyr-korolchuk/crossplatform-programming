import Helpers.FileHelper;
import Helpers.TokenizeHelper;
import MeetingManager.MeetingManager;

import java.time.ZoneId;

public class Main {
    public static void main(String[] args) {
        var manager = new MeetingManager();
        manager.start();
    }
}