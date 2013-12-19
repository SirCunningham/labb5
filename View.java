package labb5;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;

public class View {

    private JFrame frame;
    private JTextField field;
    private JPanel panel;
    private JList list;
    private BackForwardButton backButton;
    private BackForwardButton forwardButton;
    private final JEditorPane editorPane;
    private JScrollPane editorScrollPane;
    private HistoryButton historyButton;
    private JOptionPane historyPane;
    private JDialog historyDialog;
    private JFrame historyFrame;

    public static void main(String[] args) {
        new View();
    }

    public View() {
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        backButton = new BackForwardButton("back.png");
        forwardButton = new BackForwardButton("forward.png");
        historyButton = new HistoryButton();
        field = new JTextField();
        editorPane = new JEditorPane();
        editorPane.setEditable(false);
        editorScrollPane = new JScrollPane(editorPane);
        editorScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        editorScrollPane.setPreferredSize(screenSize);


        panel.add(backButton);
        panel.add(forwardButton);
        panel.add(historyButton);
        panel.add(field);
        frame.add(BorderLayout.NORTH, panel);
        frame.add(BorderLayout.CENTER, editorScrollPane);
        frame.pack();
        frame.setVisible(true);
    }
    public void createHistoryDialog(JList list) {
        JScrollPane scroller = new JScrollPane(list);
        scroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        list.setVisibleRowCount(20);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        historyFrame = new JFrame("Historik");
        historyFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        historyFrame.add(scroller);
        historyFrame.setVisible(true);
        historyFrame.pack();
    }
    
    public JOptionPane getHistoryPane() {
        return historyPane;
    }
    public JDialog getHistoryDialog() {
        return historyDialog;
    }

    public BackForwardButton getBackButton() {
        return backButton;
    }

    public BackForwardButton getForwardButton() {
        return forwardButton;
    }
    public HistoryButton getHistoryButton() {
        return historyButton;
    }

    public JTextField getTextField() {
        return field;
    }

    public JEditorPane getEditorPane() {
        return editorPane;
    }

}

