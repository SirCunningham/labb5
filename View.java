package labb5;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;


public class View {
    private JFrame frame;
    private JTextField field;
    private JPanel panel;
    private BackForwardButton backButton;
    private BackForwardButton forwardButton;
    private final JEditorPane editorPane;
    private JScrollPane editorScrollPane;
    
    public static void main(String[] args) {
        new View();
    }
    
    public View() {
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        backButton = new BackForwardButton("<-");
        forwardButton = new BackForwardButton("->");
        field = new JTextField();
        editorPane = new JEditorPane();
        editorPane.setEditable(false);
        editorScrollPane = new JScrollPane(editorPane);
        editorScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        editorScrollPane.setPreferredSize(screenSize);
        
        panel.add(backButton);
        panel.add(forwardButton);
        panel.add(field);
        frame.add(BorderLayout.NORTH, panel);
        frame.add(BorderLayout.CENTER, editorScrollPane);
        frame.pack();
        frame.setVisible(true);
    }
    public BackForwardButton getBackButton() {
        return backButton;
    }
    public BackForwardButton getForwardButton() {
        return forwardButton;
    }
    public JTextField getTextField() {
        return field;
    }
    public JEditorPane getEditorPane() {
        return editorPane;
    }
}
