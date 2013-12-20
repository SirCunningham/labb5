package labb5;

import javax.swing.*;
import java.io.*;
import javax.imageio.ImageIO;

public class BackForwardButton extends JButton {

    public BackForwardButton(String pathToIcon) {
        try {
            setIcon(new ImageIcon(ImageIO.read(new File(pathToIcon))));
        } catch (IOException e) {
            System.err.println("Följande fil kunde inte hittas: " + pathToIcon);
            e.printStackTrace();
        }
    }

}
