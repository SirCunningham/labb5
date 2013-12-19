package labb5;

import javax.swing.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.ImageIO;

public class BackForwardButton extends JButton {

    BufferedImage buttonIcon;

    public BackForwardButton(String pathToIcon) {
        try {
            buttonIcon = ImageIO.read(new File(pathToIcon));
        } catch (IOException e) {
            System.err.println("Filen: " + pathToIcon + " kunde inte hittas.");
            e.printStackTrace();
        }
        this.setIcon(new ImageIcon(buttonIcon));
    }
}
