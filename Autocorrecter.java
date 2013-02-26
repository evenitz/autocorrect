package edu.brown.cs32.evenitz.autocorrect;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JComponent;
import javax.swing.JFrame;

public class Autocorrecter {
     PrefixTree tree;
    private Suggester suggester;
    private Ranker ranker;
     HashMap<String, Integer> unigrams;
     HashMap<String, Integer> bigrams;
    
    public Autocorrecter(String fileName) {
        this.suggester = new StandardSuggester(fileName);
        this.ranker = new SmartRanker();
        //this.ranker = new StandardRanker();
    }
    
    public ArrayList<Suggestion> suggestionsForPrefix(String prefix, String previous) {
        ArrayList<Suggestion> results = suggester.suggestionsForPrefix(prefix, previous);
        results = ranker.rankSuggestions(prefix, results);
        return results;
    }
    
    public ArrayList<String> topSuggestions(String prefix, String previous, int number) {
        ArrayList<String> words = new ArrayList<String>();
        ArrayList<Suggestion> suggestions = this.suggestionsForPrefix(prefix, previous);
        
        for (int i = 0; i < number; i++) {
            if (i >= suggestions.size()) {
                break;
            }
            System.out.println(suggestions.get(i));
            words.add(suggestions.get(i).getWord());
        }
        
        return words;
    }
    
    public static void main(String[] args) {
        boolean gui = true;
        Autocorrecter autocorrecter = new Autocorrecter("/Users/ethanvenitz/Documents/workspace/Autocorrect/sherlock.txt");
        System.out.println("Done");
        String line = "  ";
        String word = "";
        String previous = "";
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        if (gui) {
            JFrame frame = new JFrame("autocorrect");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            JComponent newContentPane = new AutocorrectPanel(autocorrecter);
            newContentPane.setOpaque(true);
            frame.setContentPane(newContentPane);
            
            frame.pack();
            frame.setVisible(true);
        } else {
        
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
