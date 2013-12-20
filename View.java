package labb5;

import javax.swing.*;
import java.awt.*;

public class View {

    private final BackForwardButton backButton;
    private final BackForwardButton forwardButton;
    private final JButton historyButton;
    private final JTextField field;
    private final JEditorPane editorPane;
    private JDialog historyDialog;

    public View() {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        backButton = new BackForwardButton("back.png");
        forwardButton = new BackForwardButton("forward.png");
        historyButton = new JButton();
        historyButton.setText("Historik");
        field = new JTextField();
        editorPane = new JEditorPane();
        editorPane.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(editorPane);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        scrollPane.setPreferredSize(screenSize);

        panel.add(backButton);
        panel.add(forwardButton);
        panel.add(historyButton);
        panel.add(field);
        frame.add(BorderLayout.NORTH, panel);
        frame.add(BorderLayout.CENTER, scrollPane);
        frame.pack();
        frame.setVisible(true);
    }

    public void createHistoryDialog(JList list) {
        JScrollPane scroller = new JScrollPane(list);
        scroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        list.setVisibleRowCount(20);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JFrame historyFrame = new JFrame("Historik");
        historyFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        historyFrame.add(scroller);
        historyFrame.pack();
        historyFrame.setVisible(true);
    }

    public BackForwardButton getBackButton() {
        return backButton;
    }

    public BackForwardButton getForwardButton() {
        return forwardButton;
    }

    public JButton getHistoryButton() {
        return historyButton;
    }

    public JTextField getTextField() {
        return field;
    }

    public JEditorPane getEditorPane() {
        return editorPane;
    }

    public JDialog getHistoryDialog() {
        return historyDialog;
    }

    public static void main(String[] args) {
        new View();
    }

}
