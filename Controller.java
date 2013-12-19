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
    private LinkedList<URL> backArray;
    private LinkedList<URL> forwardArray;
    private URL currentURL;
    
    private static final int TYPE_START = 0;
    private static final int TYPE_NEW = 1;
    private static final int TYPE_BACKWARD = 2;
    private static final int TYPE_FORWARD = 3;

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
                    openURL(e.getURL().toString(), TYPE_NEW);
                }
            }
        });
        openURL("https://duckduckgo.com", TYPE_START);
    }

    public void openURL(String str, int type) {
        try {
            URL url = new URL(str);
            if (url != null) {
                view.getEditorPane().setPage(url);
                view.getTextField().setText(url.toString());
                switch (type) {
                    case TYPE_START:
                        backArray = new LinkedList<URL>();
                        forwardArray = new LinkedList<URL>();
                        break;
                    case TYPE_NEW:
                        backArray.addLast(currentURL);
                        forwardArray = new LinkedList<URL>();
                        break;
                    case TYPE_BACKWARD:
                        forwardArray.addFirst(currentURL);
                        break;
                    case TYPE_FORWARD:
                        backArray.addLast(currentURL);
                        break;
                }
                currentURL = url;
                if (backArray.size() == 0) {
                    view.getBackButton().setEnabled(false);
                } else {
                    view.getBackButton().setEnabled(true);
                }
                if (forwardArray.size() == 0) {
                    view.getForwardButton().setEnabled(false);
                } else {
                    view.getForwardButton().setEnabled(true);
                }
            }
        } catch (IOException ex) {
            System.err.println("Det gick inte att läsa URL: " + str);
            ex.printStackTrace();
        }
    }

    public class TextFieldListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            JTextField field = (JTextField) e.getSource();
            openURL(field.getText(), TYPE_NEW);
        }
    }

    public class BackButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            openURL(backArray.removeLast().toString(), TYPE_BACKWARD);
        }
    }

    public class ForwardButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            openURL(forwardArray.removeFirst().toString(), TYPE_FORWARD);
        }
    }

    public class HistoryListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            HistoryButton button = (HistoryButton) e.getSource();
        }
    }

}
