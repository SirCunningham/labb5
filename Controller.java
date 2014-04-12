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
        view.getEditorPane().addHyperlinkListener(new LinkListener());
        historyArray = new LinkedList<>();
        openURL("https://duckduckgo.com/lite", TYPE_START);
    }

    public final void openURL(String str, int type) {
        try {
            if (str != null) {
                URL url = new URL(str);
                //Kastar exception om vi inte kan ansluta till sidan
                url.openConnection().getInputStream();
                view.getEditorPane().setPage(url);
                view.getTextField().setText(url.toString());
                switch (type) {
                    case TYPE_START:
                        backArray = new LinkedList<>();
                        forwardArray = new LinkedList<>();
                        break;
                    case TYPE_NEW:
                        backArray.addLast(currentURL);
                        forwardArray = new LinkedList<>();
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
                if (historyArray.isEmpty()) {
                    historyArray.addFirst(str);
                } else if (!historyArray.getFirst().equals(str)) {
                    historyArray.addFirst(str);
                }
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(view.getHistoryFrame(),
                    "URL:n är felaktig eller så är det problem med nätverket.");
            ex.printStackTrace();
        }
    }

    public class BackButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            openURL(backArray.removeLast().toString(), TYPE_BACKWARD);
        }
    }

    public class ForwardButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            openURL(forwardArray.removeFirst().toString(), TYPE_FORWARD);
        }
    }

    public class HistoryButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            list.setListData(historyArray.toArray(new String[historyArray.size()]));
            view.createHistoryDialog(list);
        }
    }

    public class TextFieldListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            JTextField field = (JTextField) e.getSource();
            openURL(field.getText(), TYPE_NEW);
        }
    
    }

    public class HistoryListListener implements ListSelectionListener {

        @Override
        public void valueChanged(ListSelectionEvent e) {
            //Utan if-satsa => vi får event två gånger
            if (!e.getValueIsAdjusting()) {
                String selection = (String) list.getSelectedValue();
                openURL(selection, TYPE_FORWARD);
                view.getHistoryFrame().dispose();
            }
        }
    }

    public class LinkListener implements HyperlinkListener {

        @Override
        public void hyperlinkUpdate(HyperlinkEvent e) {
            if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
                openURL(e.getURL().toString(), TYPE_NEW);
            }
        }
    
    }

    public static void main(String[] args) {
        new Controller(new View());
    }
}