package edu.brown.cs32.evenitz.autocorrect;

import java.util.ArrayList;

import javax.swing.JComponent;
import javax.swing.JFrame;

public class Main {
    public static void main(String[] args) {
        
        int len = args.length;
        int led = 0;
        boolean gui = false;
        boolean prefix = false;
        boolean whitespace = false;
        boolean smart = false;
        ArrayList<String> fileNames = new ArrayList<String>();
        
        for (int i = 0; i < len; i++) {
            String arg = args[i]; 
            if (arg.equals("--led")) {
                led = 2;
                i++;
            } else if (arg.equals("--prefix")) {
                prefix = true;
            } else if (arg.equals("--whitespace")) {
                whitespace = true;
            } else if (arg.equals("--smart")) {
                smart = true;
            }  else if (arg.equals("--gui")) {
                gui = true;
            } else {
                fileNames.add(arg);
            }
        }
        
        Autocorrecter autocorrecter = new Autocorrecter("/Users/ethanvenitz/Documents/workspace/Autocorrect/sherlock.txt", prefix, whitespace, led, smart);
        
        fileNames.add("/Users/ethanvenitz/Documents/workspace/Autocorrect/sherlock.txt");
        
        /* Launches the gui version */
        if (gui) {
            JFrame frame = new JFrame("autocorrect");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            JComponent newContentPane = new AutocorrectPanel(autocorrecter);
            newContentPane.setOpaque(true);
            frame.setContentPane(newContentPane);
            
            frame.pack();
            frame.setVisible(true);
        } 
        /* Launches the terminal version */
        else {
            AutocorrectTerminal terminal = new AutocorrectTerminal(autocorrecter);
            terminal.loop();
        }
    }
}
