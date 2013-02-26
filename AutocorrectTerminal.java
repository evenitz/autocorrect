package edu.brown.cs32.evenitz.autocorrect;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class AutocorrectTerminal {
    
    private Autocorrecter autocorrecter;
    private ArrayList<String> suggestions;
    
    public AutocorrectTerminal(Autocorrecter a) {
        this.autocorrecter = a;
        this.suggestions = new ArrayList<String>();
    }
    
    /**
     * loop
     * creates and manage the main loop for the terminal version of
     * this autocorrect. For each line the user inputs, it gets the 
     * current word and the previous word and gets the result from the autocorrect
     * object.
     */
    public void loop() {
        String line = " ";
        String word = "";
        String previous = "";
        String sentence = "";
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        System.out.println("Ready: ");
        
        while (!line.equals("")) {
            try {
                line = br.readLine().toLowerCase();
                String[] words = line.split("\\s+");
                word = words[words.length - 1];

                if (words.length > 1) {
                    previous = words[words.length - 2];
                }
                    
                sentence = line.substring(0, line.length() - word.length());
                            
                suggestions = autocorrecter.topSuggestions(word, previous, 5);
                this.printSuggestions(sentence);
            } catch (IOException ioe) {
                System.out.println("ERROR: IO error reading input");
                System.exit(1);
            }
        }
        
    }
    
    /**
     * printSuggestions
     * prints the suggestions for the users word, printing each suggestion
     * on its own line. The given string is printed before the suggestion.
     * 
     * @param sentence
     */
    public void printSuggestions(String sentence) {
        for (String s : this.suggestions) {
            System.out.println(sentence + s);
        }
    }
}
