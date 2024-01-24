import javax.swing.*;

public class Main {
    public static AppGUI gui;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            gui = new AppGUI();
            gui.setVisible(true);
        });

    }
}
