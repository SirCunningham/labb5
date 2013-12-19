package labb5;

import javax.swing.*;
import java.net.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

public class Controller {

    private View view;
    private URL listUrl;
    private LinkedList<URL> listOfUrls;
    private int counter;

    public static void main(String[] args) {
        new Controller(new View());
    }

    public Controller(final View view) {
        this.view = view;
        listOfUrls = new LinkedList<URL>();
        view.getTextField().addActionListener(new TextFieldListener());
        view.getBackButton().addActionListener(new BackButtonListener());
        view.getForwardButton().addActionListener(new ForwardButtonListener());
        view.getEditorPane().addHyperlinkListener(new HyperlinkListener() {

            public void hyperlinkUpdate(HyperlinkEvent e) {
                if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
                    try {
                        URL url = e.getURL();
                        listOfUrls.addLast(url);
                        counter++;
                        view.getEditorPane().setPage(url);
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
                URL url = new URL(field.getText());
                if (url != null) {
                    listOfUrls.addLast(url);
                    counter++;
                    view.getEditorPane().setPage(url);
                }
            } catch (IOException ex) {
                System.err.println("Det gick inte att läsa URL: "
                        + field.getText());
                ex.printStackTrace();
            }

        }
    }

    public class BackButtonListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            if (counter > 0) {
                counter--;
                try {
                    view.getEditorPane().setPage(listOfUrls.get(counter));
                } catch (IOException ex) {
                    System.err.println("Det gick inte att läsa URL: "
                            + listOfUrls.get(counter));
                    ex.printStackTrace();
                }
            }

        }
    }

    public class ForwardButtonListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            if (counter < listOfUrls.size() - 1) {
                counter++;
                try {
                    view.getEditorPane().setPage(listOfUrls.get(counter));
                } catch (IOException ex) {
                    System.err.println("Det gick inte att läsa URL: "
                            + listOfUrls.get(counter));
                    ex.printStackTrace();
                }
            }
        }
    }

    public class HistoryListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            HistoryButton button = (HistoryButton) e.getSource();

        }
    }
}
