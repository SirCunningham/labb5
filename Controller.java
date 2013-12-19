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
    private LinkedList<String> historyArray;
    private URL currentURL;
    private JList list;
    private static final int TYPE_START = 0;
    private static final int TYPE_NEW = 1;
    private static final int TYPE_BACKWARD = 2;
    private static final int TYPE_FORWARD = 3;

    public static void main(String[] args) {
        new Controller(new View());
    }

    public Controller(final View view) {
        this.view = view;
        list = new JList();
        historyArray = new LinkedList<String>();
        view.getTextField().addActionListener(new TextFieldListener());
        view.getBackButton().addActionListener(new BackButtonListener());
        view.getForwardButton().addActionListener(new ForwardButtonListener());
        view.getHistoryButton().addActionListener(new ButtonHistoryListener());
        list.addListSelectionListener(new ListHistoryListener());
        view.getEditorPane().addHyperlinkListener(new HyperlinkListener() {

            public void hyperlinkUpdate(HyperlinkEvent e) {
                if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
                    openURL(e.getURL().toString(), TYPE_NEW);
                }
            }
        });
        openURL("https://duckduckgo.com/lite", TYPE_START);
    }

    public void openURL(String str, int type) {
        try {
            URL url = new URL(str);
            //Kastar exception om vi inte kan connecta till en hemsida
            URLConnection con = url.openConnection();
            con.getInputStream();
            if (url != null) {
                historyArray.addFirst(str);
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
            JOptionPane.showMessageDialog(view.getEditorPane(), 
                    "URL:n är felaktig eller så är det problem med nätverket.");
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

    public class ButtonHistoryListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            String[] strArr = new String[historyArray.size()];
            strArr = historyArray.toArray(strArr);
            list.setListData(strArr);
            view.createHistoryDialog(list);
 
            
        }
    }
    
    public class ListHistoryListener implements ListSelectionListener {
        public void valueChanged(ListSelectionEvent e) {
            //Utan if-satsa => vi får event två gånger
            if (!e.getValueIsAdjusting()) {
                String selection = (String) list.getSelectedValue();
                openURL(selection, TYPE_FORWARD);
            }
        }
    }
     
}
