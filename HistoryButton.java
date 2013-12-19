package labb5;

import javax.swing.*;
import java.net.*;

import java.util.*;

public class HistoryButton extends JButton {

    private String text;

    public HistoryButton(String text) {
        setText(text);
    }

    public HistoryButton() {
        this("Historia");
    }
}
