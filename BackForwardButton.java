package labb5;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;

public class BackForwardButton extends JButton{
    private String name;
    
    public BackForwardButton(String name) {
        this.name = name;
        setText(name);
    }
}
