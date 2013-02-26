package edu.brown.cs32.evenitz.autocorrect;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class StandardSuggester implements Suggester{
    private PrefixTree tree;
    HashMap<String, Integer> unigrams;
    HashMap<String, Integer> bigrams;
    
    public StandardSuggester(String fileName) {
        this.tree = new PrefixTree();
        this.unigrams = new HashMap<String, Integer>();
        this.bigrams = new HashMap<String, Integer>();
        this.addFile(fileName);
    }
    
    public ArrayList<Suggestion> suggestionsForPrefix(String prefix, String previous) {
        HashSet<String> resultsSet = new HashSet<String>();
        
       // resultsSet.addAll(tree.wordsForPrefix(prefix));
        resultsSet.addAll(tree.LevenshteinDistanceWords(prefix, 2));
        resultsSet.addAll(tree.wordSplits(prefix));
        ArrayList<Suggestion> words = new ArrayList<Suggestion>();
        
        arrayAddSuggestions(words, resultsSet, previous, false);
        
        HashSet<String> prefixSet = new HashSet<String>();
        prefixSet.addAll(tree.wordsForPrefix(prefix));
        arrayAddSuggestions(words, prefixSet, previous, true);
        return words;
    }
    
    private void arrayAddSuggestions(ArrayList<Suggestion> suggestions, HashSet<String> words, String previous, boolean isPrefix) {
        int uniCount = 0;
        int biCount = 0;
        for (String w : words) {
            String bigram = previous + " " + w;
            
            if (this.unigrams.get(w) != null) {
                uniCount = this.unigrams.get(w);
            }
            
            if (this.bigrams.get(bigram) != null) {
                biCount = this.bigrams.get(bigram);
            }
            
            suggestions.add(new SmartSuggestion(w, uniCount, biCount, isPrefix));
        }
    }
    
    public void addFile(String fileName) {
        
        try {
            CorpusParser reader = new CorpusParser(fileName);
            String[] grams;
            while (reader.hasNext()) {
                grams = reader.next();
                this.tree.addWord(grams[0]);
                this.addUnigram(grams[0]);
                this.addBigram(grams[0], grams[1]);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    } 
    
    public void addUnigram(String unigram) {
        Integer count = 1;
        if (unigram.trim().equals("")) {
            return;
        }
        if (this.unigrams.containsKey(unigram)) {
            count += this.unigrams.get(unigram);
        }
        this.unigrams.put(unigram, count);
    }
    
    public void addBigram(String s1, String s2) {
        Integer count = 1;
        String str1 = s1.trim();
        String str2 = s2.trim();
        if (str1.equals("") || str2.equals("")) {
            return;
        }
        String bigram = str1 + " " + str2;
        
        if (this.bigrams.containsKey(bigram)) {
            count += this.bigrams.get(bigram);
        }
        
        this.bigrams.put(bigram, count);
    }
    
}
