package edu.brown.cs32.evenitz.autocorrect;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;

public class StandardSuggester implements Suggester{
    private PrefixTree tree;
    private boolean prefix;
    private boolean whitespace;
    private int led;
    
    HashMap<String, Integer> unigrams;
    HashMap<String, Integer> bigrams;
    
    public StandardSuggester(String fileName, boolean prefix, boolean whitespace, int led) {
        this.tree = new PrefixTree();
        this.unigrams = new HashMap<String, Integer>();
        this.bigrams = new HashMap<String, Integer>();
        
        this.prefix = prefix;
        this.whitespace = whitespace;
        this.led = led;
        
        this.addFile(fileName);
    }
    
    /**
     * suggestionsForPrefix
     * generates a hashset of suggestions for a given word
     * 
     * @param prefix   the user inputted word
     * @param previous the previous word
     */
    public HashSet<Suggestion> suggestionsForPrefix(String prefix, String previous) {
        HashSet<String> resultsSet = new HashSet<String>();
        
        if (this.led > 0) {
            resultsSet.addAll(tree.LevenshteinDistanceWords(prefix, this.led));
        }
        
        if (this.whitespace) {
            resultsSet.addAll(tree.wordSplits(prefix));
        }
        
        HashSet<Suggestion> words = new HashSet<Suggestion>();
        
        setAddSuggestions(words, resultsSet, previous, false);
               
        if (this.prefix) {
            HashSet<String> prefixSet = new HashSet<String>();
            prefixSet.addAll(tree.wordsForPrefix(prefix));
            setAddSuggestions(words, prefixSet, previous, true);
        }
        System.out.println(words);
        return words;
    }
    
    /**
     * setAddSuggestions
     * Takes a set of words and adds them to the set of suggestions
     * 
     * @param suggestions
     * @param words
     * @param previous
     * @param isPrefix
     */
    private void setAddSuggestions(HashSet<Suggestion> suggestions, HashSet<String> words, String previous, boolean isPrefix) {
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
    
    /**
     * addFile
     * Takes a string representing a file name and parses the file
     * for words to add to the prefix tree and unigrams and bigrams to
     * add to their respective hashes.
     * 
     * @param fileName
     */
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
    
    /**
     * addUnigram
     * adds a unigram string to the unigram hashmap
     * 
     * @param unigram
     */
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
    
    /**
     * addBigram
     * takes two words and adds the bigram to the bigram hashset
     * 
     * @param s1 the first word
     * @param s2 the second word
     */
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
    
    Integer getUnigram(String key) {
        return this.unigrams.get(key);
    }
    
    Integer getBigram(String key) {
        return this.bigrams.get(key);
    }
    
}
