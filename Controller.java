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
                    openURL(e.getURL().toString(), true);
                }
            }
        });
        openURL("https://duckduckgo.com", true);
    }

    public void openURL(String str, boolean adder) {
        try {
            URL url = new URL(str);
            if (url != null) {
                view.getEditorPane().setPage(url);
                view.getTextField().setText(url.toString());
                if (adder) {
                    listOfUrls.addLast(url);
                    counter++;
                }
                updateButtons();
            }
        } catch (IOException ex) {
            System.err.println("Det gick inte att läsa URL: "
                    + str);
            ex.printStackTrace();
        }
    }
    
    public class TextFieldListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            JTextField field = (JTextField) e.getSource();
            openURL(field.getText(), true);
        }
    }
    
    public void updateButtons() {
        if (counter == 0) {
            view.getBackButton().setEnabled(false);
        } else {
            view.getBackButton().setEnabled(true);
        }
        if (counter >= listOfUrls.size() - 1) {
            view.getForwardButton().setEnabled(false);
        } else {
            view.getForwardButton().setEnabled(true);
        }
    }

    public class BackButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (counter > 0) {
                counter--;
                openURL(listOfUrls.get(counter).toString(), false);
            }
        }
    }

    public class ForwardButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (counter < listOfUrls.size() - 1) {
                counter++;
                openURL(listOfUrls.get(counter).toString(), false);
            }
        }
    }

    public class HistoryListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            HistoryButton button = (HistoryButton) e.getSource();
        }
    }

}
