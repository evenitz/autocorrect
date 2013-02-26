package edu.brown.cs32.evenitz.autocorrect;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class AutocorrectTerminal {
    
    private Autocorrecter autocorrecter;
    
    public AutocorrectTerminal(Autocorrecter a) {
        this.autocorrecter = a;
    }
    
    public void loop() {
        String line = " ";
        String word = "";
        String previous = "";
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        while(true) {
            while (!line.equals("")) {
                try {
                    line = br.readLine().toLowerCase();
                    String[] words = line.split("\\s+");
                    word = words[words.length - 1];
                    if (words.length > 1) {
                        previous = words[words.length - 2];
                    }
                    System.out.println(autocorrecter.suggestionsForPrefix(word, previous));
                    System.out.println(autocorrecter.topSuggestions(word, previous, 5));
                } catch (IOException ioe) {
                    System.out.println("ERROR: IO error reading input");
                    System.exit(1);
                }
            }
        }
    }
}
