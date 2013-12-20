package labb5;

import javax.swing.*;
import java.net.*;
import javax.swing.event.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

//Bugg: Skriv fel adress, backa i historiken, tryck på historikknappen...
public class Controller {

    private final View view;
    private LinkedList<URL> backArray;
    private LinkedList<URL> forwardArray;
    private URL currentURL;
    private final LinkedList<String> historyArray;
    private final JList list;
    private static final int TYPE_START = 0;
    private static final int TYPE_NEW = 1;
    private static final int TYPE_BACKWARD = 2;
    private static final int TYPE_FORWARD = 3;

    public Controller(final View view) {
        this.view = view;
        view.getBackButton().addActionListener(new BackButtonListener());
        view.getForwardButton().addActionListener(new ForwardButtonListener());
        view.getHistoryButton().addActionListener(new HistoryButtonListener());
        view.getTextField().addActionListener(new TextFieldListener());
        list = new JList();
        list.addListSelectionListener(new HistoryListListener());
        view.getEditorPane().addHyperlinkListener(new HyperlinkListener() {

            public void hyperlinkUpdate(HyperlinkEvent e) { //Varför anonym nu???
                if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
                    openURL(e.getURL().toString(), TYPE_NEW);
                }
            }
        });
        historyArray = new LinkedList<String>();
        openURL("https://duckduckgo.com/lite", TYPE_START);
    }

    public void openURL(String str, int type) {
        historyArray.addFirst(str);
        try {
            URL url = new URL(str);
            //Kastar exception om vi inte kan ansluta till sidan
            url.openConnection().getInputStream();
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

        } catch (IOException ex) {
            JOptionPane.showMessageDialog(view.getEditorPane(),
                    "URL:n är felaktig eller så är det problem med nätverket.");
            ex.printStackTrace();
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

    public class HistoryButtonListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            String[] strArr = new String[historyArray.size()];
            strArr = historyArray.toArray(strArr);
            list.setListData(strArr);
            view.createHistoryDialog(list);
        }
    }

    public class TextFieldListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            JTextField field = (JTextField) e.getSource();
            openURL(field.getText(), TYPE_NEW);
        }
    }

    public class HistoryListListener implements ListSelectionListener {

        public void valueChanged(ListSelectionEvent e) {
            //Utan if-satsa => vi får event två gånger
            if (!e.getValueIsAdjusting()) {
                String selection = (String) list.getSelectedValue();
                openURL(selection, TYPE_FORWARD);
            }
        }
    }

    public static void main(String[] args) {
        new Controller(new View());
    }
}


