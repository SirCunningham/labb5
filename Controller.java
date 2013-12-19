package labb5;

import javax.swing.*;
import java.net.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class Controller {

    private View view;
    private URL url;

    public static void main(String[] args) {
        new Controller(new View());
    }

    public Controller(final View view) {
        this.view = view;
        view.getTextField().addActionListener(new TextFieldListener());
        view.getBackButton().addActionListener(new BackButtonListener());
        view.getForwardButton().addActionListener(new ForwardButtonListener());
        view.getEditorPane().addHyperlinkListener(new HyperlinkListener() {

            public void hyperlinkUpdate(HyperlinkEvent e) {
                if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
                    try {
                        view.getEditorPane().setPage(e.getURL());
                    } catch (IOException ex) {
                        System.out.println("Kunde inte öppna URL: " + e.getURL());
                    }
                }
            }
        });
    }

    public class TextFieldListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            JTextField field = (JTextField) e.getSource();
            try {
                url = new URL(field.getText());
                if (url != null) {
                    view.getEditorPane().setPage(url);
                }
            } catch (IOException ex) {
                System.out.println("Det gick inte att läsa URL: " + url);
            }

        }
    }

    public class BackButtonListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            BackForwardButton button = (BackForwardButton) e.getSource();
        }
    }

    public class ForwardButtonListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            BackForwardButton button = (BackForwardButton) e.getSource();
        }
    }

    public class HistoryListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            HistoryButton button = (HistoryButton) e.getSource();

        }
    }
}
