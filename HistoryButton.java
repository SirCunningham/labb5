package labb5;

import javax.swing.*;

public class HistoryButton extends JButton {

    public HistoryButton(String text) {
        setFont(getFont().deriveFont(13.0f));
        setText(text);
    }

    public HistoryButton() {
        this("Historik");
    }

}
