import Entities.Consumer;
import Entities.Factory;
import Entities.Producer;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AppGUI extends JFrame {
    private final ThreadTable threadTable = new ThreadTable(new ArrayList<>());

    private final List<JButton> controlButtons = new ArrayList<>();

    public static JTextArea eventLog = new JTextArea();

    private final Factory factory = new Factory(50, eventLog);

    private static class CustomThread {
        private final Thread thread;

        private boolean isSelected;

        public CustomThread(Thread thread, boolean isSelected) {
            this.thread = thread;
            this.isSelected = isSelected;
        }

        public Thread getThread() {
            return thread;
        }

        public boolean isSelected() {
            return isSelected;
        }

        public void setSelected(boolean selected) {
            this.isSelected = selected;
        }
    }

    private class ThreadTable extends AbstractTableModel {
        private final List<CustomThread> tableData;

        private final String[] columnNames = { "ID", "Name", "State", "Priority", "Selected" };

        public ThreadTable(List<CustomThread> tableData) {
            this.tableData = tableData;
        }

        public void addRow(CustomThread thread) {
            tableData.add(thread);
            fireTableRowsInserted(tableData.size() - 1, tableData.size() - 1);
        }

        public List<CustomThread> getSelectedThreads() {
            return tableData.stream()
                .filter(CustomThread::isSelected)
                .collect(Collectors.toList());
        }

        public void clearData() {
            tableData.clear();
            fireTableDataChanged();
        }

        @Override
        public int getRowCount() {
            return tableData.size();
        }

        @Override
        public int getColumnCount() {
            return columnNames.length;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            var thread = tableData.get(rowIndex);
            switch (columnIndex) {
                case 0 -> {
                    return thread.getThread().threadId();
                }
                case 1 -> {
                    return thread.getThread().getName();
                }
                case 2 -> {
                    return thread.getThread().getState();
                }
                case 3 -> {
                    return thread.getThread().getPriority();
                }
                case 4 -> {
                    return thread.isSelected;
                }
            }
            return null;
        }

        @Override
        public void setValueAt (Object value, int rowIndex, int columnIndex) {
            if (columnIndex == 4) {
                var thread = tableData.get(rowIndex);
                thread.setSelected((Boolean) value);
                fireTableCellUpdated(rowIndex, columnIndex);
            }
        }

        @Override
        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return columnIndex == 4;
        }

        @Override
        public Class<?> getColumnClass(int columnIndex) {
            return columnIndex == 4 ? Boolean.class : super.getColumnClass(columnIndex);
        }

        @Override
        public String getColumnName(int index) {
            return columnNames[index];
        }
    }

    private static class CheckBoxRenderer extends JCheckBox implements TableCellRenderer {

        public CheckBoxRenderer() {
            setHorizontalAlignment(JCheckBox.CENTER);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            setSelected((value != null && (boolean) value));
            return this;
        }
    }

    public AppGUI() {
        setTitle("Producer - Consumer Model");
        setSize(900, 500);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        JPanel tablePanel = new JPanel();
        tablePanel.setLayout(new FlowLayout());
        this.addTable(tablePanel);

        JScrollPane logScroll = new JScrollPane(eventLog);
        logScroll.setPreferredSize(new Dimension(400, 500));
        tablePanel.add(logScroll);
        mainPanel.add(tablePanel);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.gray);
        this.addButtons(buttonPanel);

        tablePanel.setLayout(new BoxLayout(tablePanel, BoxLayout.X_AXIS));
        mainPanel.add(buttonPanel);

        controlButtons.get(0).addActionListener(e -> {
            Thread newThread = new Thread(new Producer(factory));
            int randomPriority = Thread.MIN_PRIORITY + (int) (Math.random() * (Thread.MAX_PRIORITY - Thread.MIN_PRIORITY + 1));
            newThread.setPriority(randomPriority);

            var threadNumber = newThread.getName().charAt(newThread.getName().length() - 1);
            newThread.setName("Tr #" + threadNumber + " Producer");

            CustomThread newCustomThread = new CustomThread(newThread, false);
            threadTable.addRow(newCustomThread);
        });

        controlButtons.get(1).addActionListener(e -> {
            Thread newThread = new Thread(new Consumer(factory));
            int threadPriority = Thread.MIN_PRIORITY + (int) (Math.random() * (Thread.MAX_PRIORITY - Thread.MIN_PRIORITY + 1));
            newThread.setPriority(threadPriority);

            var threadNumber = newThread.getName().charAt(newThread.getName().length() - 1);
            newThread.setName("Tr #" + threadNumber + " Consumer");

            CustomThread newCustomThread = new CustomThread(newThread, false);
            threadTable.addRow(newCustomThread);
        });

        controlButtons.get(2).addActionListener(e -> {
            List<CustomThread> selectedThreads = threadTable.getSelectedThreads();
            eventLog.append("---> Selected threads started.\n");
            eventLog.append("---> " + selectedThreads.size() + " thread are now running.\n");

            for (CustomThread customThread : selectedThreads) {
                customThread.getThread().start();
                threadTable.fireTableDataChanged();
            }
        });

        controlButtons.get(3).addActionListener(e -> {
            this.threadTable.clearData();
            this.factory.isRunning = false;
            for(CustomThread customThread : threadTable.tableData) {
                try {
                    customThread.getThread().join();
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
            }
            threadTable.fireTableDataChanged();
            eventLog.append("---> All threads were stopped.\n" );
        });

        this.add(mainPanel);
    }

    private void addTable(JPanel panel) {
        JTable jtable = new JTable(threadTable);
        jtable.getColumnModel()
            .getColumn(4)
            .setCellRenderer(new CheckBoxRenderer());

        jtable.getColumnModel()
            .getColumn(4)
            .setCellEditor(new DefaultCellEditor(new JCheckBox()));

        JScrollPane scrollPane = new JScrollPane(jtable);
        jtable.setFillsViewportHeight(true);
        panel.add(scrollPane);
    }

    private void addButtons(JPanel panel) {
        controlButtons.add(new JButton("New Producer"));
        controlButtons.add(new JButton("New Consumer"));
        controlButtons.add(new JButton("Start Selected"));
        controlButtons.add(new JButton("Stop All"));

        controlButtons.get(2).setBackground(Color.green);
        controlButtons.get(3).setBackground(Color.red);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
        buttonPanel.setAlignmentY(Component.CENTER_ALIGNMENT);
        for(JButton button : controlButtons) {
            button.setMaximumSize(new Dimension(150, 50));
            button.setAlignmentX(Component.CENTER_ALIGNMENT);
            buttonPanel.add(button);
            buttonPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        }
        panel.add(buttonPanel);
    }
}
