package edu.brown.cs32.evenitz.autocorrect;

import java.util.ArrayList;

public class PrefixTree {
    public PrefixTreeNode root;
    
    public PrefixTree() {
        this.root = new PrefixTreeNode("");
    }
    
    /**
     * addWord
     * Takes a String and adds that word to the tree
     * 
     * @param word the word to add
     */
    public void addWord(String word) {
        PrefixTreeNode node = this.findWord(word);
        PrefixTreeNode nextNode = null;
        String prefix = node.getWord();
        
        int len = prefix.length();
        int childrenCount = node.childrenSize();
        int wordLen = word.length();
        int splitIndex = -1;
        
        String newWord = word.substring(len, wordLen);

        for (int i = 0; i < childrenCount; i++) {
            nextNode = node.getChild(i);
            splitIndex = nextNode.isPartialPrefixOf(newWord);
            if (splitIndex > -1) {
                nextNode.split(splitIndex, newWord);
                break;
            }
        }        
        
        if (splitIndex == -1) {
            node.createChild(newWord);
        }
        
    }
    
    /**
     * containsWord
     * checks the tree to see if the given word is a member of this
     * tree
     * 
     * @param  word the String to search
     * @return true if the string is in the tree
     */
    public boolean containsWord(String word) {
        PrefixTreeNode node = this.findWord(word);
        return (node.isWord() && node.getWord().equals(word));
    }
    
    public PrefixTreeNode getRoot() {
        return this.root;
    }
    
    /**
     * 
     * @param word
     * @return
     */
    public PrefixTreeNode findWord(String word) {
        PrefixTreeNode currentNode = this.root;
        PrefixTreeNode previousNode = null;
        String currentWord = word;
        
        while(currentNode != null) {
            
            currentWord = currentWord.substring(currentNode.getValue().length());
            
            previousNode = currentNode;
           
            currentNode = previousNode.getNext(currentWord);

        }
        
        return previousNode;
    }
    
    /**
     * wordsForPrefix
     * Takes a String and returns all of the Strings for which the
     * passed in word is a prefix of.
     * 
     * @param prefix
     * @return
     */
    public ArrayList<String> wordsForPrefix(String prefix) {
        ArrayList<String> words = new ArrayList<String>();
        
        PrefixTreeNode best = this.findWord(prefix);
        int index = best.getWord().length();
        String remainingPrefix = prefix.substring(index, prefix.length());
        String bestString = best.getWord();
        
        if (!remainingPrefix.equals("")) {
            // is child a partial match?
            for (PrefixTreeNode node : best.children) {
                if ((index = node.isPartialPrefixOf(remainingPrefix)) > -1) {
                    bestString += node.getValue();
                    remainingPrefix = remainingPrefix.substring(index, remainingPrefix.length());
                    best = node;
                    break;
                }
            }
        }
        
        if (remainingPrefix.equals("")) {
            best.getChildrenWords(bestString, words);
        }
        return words;
    }
    
    /**
     * levenshteinDistanceWords
     * For a given String and distance, returns all of the words with a levenshtein
     * distance of the given distance.
     * 
     * @param word
     * @param distance
     * @return
     */
    public ArrayList<String> LevenshteinDistanceWords(String word, int distance) {
        ArrayList<String> words = this.wordsForPrefix("");
        ArrayList<String> results = new ArrayList<String>();
        int word_len = word.length();
        for (String w : words) {
            if (w.length() - distance <= word_len && w.length() + distance >= word_len) {
                if (this.levenshteinDistance(word, w) <= distance) {
                    results.add(w);
                }
            }
        }
        
        return results;
    }
    
    /**
     * 
     * @param word
     * @return
     */
    public ArrayList<String> wordSplits(String word) {
        ArrayList<String> results = new ArrayList<String>();
        
        String word1 = "";
        String word2 = "";
        
        int len = word.length();
        
        for (int i = 1; i < len - 1; i++) {
            word1 = word.substring(0, i);
            word2 = word.substring(i);
            
            if (this.containsWord(word1) && this.containsWord(word2)) {
                String twoWords = word1 + " " + word2;
                results.add(twoWords);
            }
        }
        
        return results;
    }
    
    /**
     * 
     * @param str1
     * @param str2
     * @return
     */
    public int levenshteinDistance(String str1, String str2) {
        int str1_len = str1.length();
        int str2_len = str2.length();
        int[][] matrix = new int[str1_len + 1][str2_len + 1];
        int minValue = 0;
        
        if (str1_len == 0) {
            return str2_len;
        }
        
        if (str2_len == 0) {
            return str1_len;
        }
        
        for (int i = 0; i <= str1_len; i++) {
            matrix[i][0] = i;
        }
        for (int j = 0; j <= str2_len; j++) {
            matrix[0][j] = j;
        }
        
        for (int i = 1; i <= str2_len; i++) {
            for (int j = 1; j <= str1_len; j++) {
                if (str1.charAt(j-1) == str2.charAt(i-1)) {
                    matrix[j][i] = matrix[j - 1][i - 1];
                } else {
                    minValue = Math.min(matrix[j-1][i], matrix[j][i-1]);
                    minValue = Math.min(minValue, matrix[j-1][i-1]);
                    matrix[j][i] = minValue + 1;
                }
            }
        }
        return matrix[str1_len][str2_len];
    }
    
    
    @Override
    public String toString() {
        return this.root.toString();
    }
}
