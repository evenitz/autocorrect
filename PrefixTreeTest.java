package edu.brown.cs32.evenitz.autocorrect;

import static org.junit.Assert.*;

import org.junit.Test;

public class PrefixTreeTest {

    @Test
    public void findSingleWord() {
        PrefixTree tree = new PrefixTree();
        tree.getRoot().createChild("computer");
        
        assertTrue(tree.findWord("computer").getValue().equals("computer"));
    }
    
    @Test
    public void noWordReturnRoot() {
        PrefixTree tree = new PrefixTree();
        tree.getRoot().createChild("computer");
        
        assertTrue(tree.findWord("science").getValue().equals("")); 
    }
    
    @Test
    public void multipleAtRoot() {
        PrefixTree tree = new PrefixTree();
        tree.getRoot().createChild("animal");
        tree.getRoot().createChild("computer");
        
        assertTrue(tree.findWord("computer").getValue().equals("computer"));
    }
    
    @Test
    public void findOneLevelDeepExists() {
        PrefixTree tree = new PrefixTree();
        tree.getRoot().createChild("comp");
        tree.getRoot().getChild(0).createChild("uter");
        
        assertTrue(tree.findWord("computer").getValue().equals("uter"));
    }
    
    @Test
    public void multiplePathsNoFullMatch() {
        PrefixTree tree = new PrefixTree();
        tree.getRoot().createChild("co");
        tree.getRoot().createChild("ani");
        tree.getRoot().getChild(1).createChild("mal");
        tree.getRoot().getChild(0).createChild("rn");
        tree.getRoot().getChild(0).createChild("mput");
        tree.getRoot().getChild(0).getChild(1).createChild("ation");
        tree.getRoot().getChild(0).getChild(1).createChild("er");
        
        assertTrue(tree.findWord("computing").getValue().equals("mput"));   
    }
    
    @Test
    public void multiplePathsExists() {
        PrefixTree tree = new PrefixTree();
        tree.getRoot().createChild("co");
        tree.getRoot().createChild("ani");
        tree.getRoot().getChild(1).createChild("mal");
        tree.getRoot().getChild(0).createChild("rn");
        tree.getRoot().getChild(0).createChild("mput");
        tree.getRoot().getChild(0).getChild(1).createChild("ation");
        tree.getRoot().getChild(0).getChild(1).createChild("er");
        
        
        assertTrue(tree.findWord("computer").getValue().equals("er"));
    }
    
    @Test
    public void addSingleWord() {
        PrefixTree tree = new PrefixTree();
        
        tree.addWord("test");
        assertTrue(tree.getRoot().toString().equals("{ { test } }"));
    }
    
    @Test
    public void addTwoRootWords() {
        PrefixTree tree = new PrefixTree();
        
        tree.addWord("test");
        tree.addWord("water");
        assertTrue(tree.getRoot().toString().equals("{ { test }{ water } }"));
    }
    
    @Test
    public void addPrefix() {
        PrefixTree tree = new PrefixTree();
        
        tree.addWord("fast");
        tree.addWord("faster");
        
        assertTrue(tree.getRoot().toString().equals("{ { fast{ er } } }"));
    }
    
    @Test
    public void addWordThatIsPrefix() {
        PrefixTree tree = new PrefixTree();
        
        tree.addWord("faster"); 
        tree.addWord("fast");
        
        assertTrue(tree.getRoot().toString().equals("{ { fast{ er } } }"));
    }
    
    @Test
    public void splitWordInTwo() {
        PrefixTree tree = new PrefixTree();
        
        tree.addWord("test");
        tree.addWord("team");
        
        assertTrue(tree.getRoot().toString().equals("{ { te{ am }{ st } } }"));
    }
    
    @Test
    public void splitWordWithChildren() {
        PrefixTree tree = new PrefixTree();
        
        tree.addWord("test");
        tree.addWord("team");
        tree.addWord("toast");
        
        assertTrue(tree.getRoot().toString().equals("{ { t{ e{ am }{ st } }{ oast } } }"));
    
    }
    
    @Test
    public void addSevenWords() {
        PrefixTree tree = new PrefixTree();
        
        tree.addWord("romane");
        tree.addWord("romanus");
        tree.addWord("romulus");
        tree.addWord("rubens");
        tree.addWord("ruber");
        tree.addWord("rubicon");
        tree.addWord("rubicundus");
        
        assertTrue(tree.getRoot().toString().equals("{ { r{ om{ an{ e }{ us } }{ ulus } }{ ub{ e{ ns }{ r } }{ ic{ on }{ undus } } } } }"));
    }
    
    @Test
    public void wordsFromRoot() {
        PrefixTree tree = new PrefixTree();
        tree.addWord("word");
        tree.addWord("dictionary");
        tree.addWord("unrelated");
        assertTrue(tree.wordsForPrefix("").toString().equals("[word, dictionary, unrelated]"));
    }
    
    @Test
    public void wordsFromOneLevel() {
        PrefixTree tree = new PrefixTree();
        tree.addWord("word");
        tree.addWord("dictionary");
        tree.addWord("unrelated");
        tree.addWord("write");
        tree.addWord("willow");
        assertTrue(tree.wordsForPrefix("w").toString().equals("[word, write, willow]"));
    }
    
    @Test
    public void allWordsFromOneLevel() {
        PrefixTree tree = new PrefixTree();
        tree.addWord("word");
        tree.addWord("dictionary");
        tree.addWord("unrelated");
        tree.addWord("write");
        tree.addWord("willow");
        System.out.println(tree.wordsForPrefix(""));
        assertTrue(tree.wordsForPrefix("").toString().equals("[dictionary, unrelated, word, write, willow]"));
    }
    
    @Test
    public void levenshteinDistanceZero() {
        PrefixTree tree = new PrefixTree();
        assertTrue(tree.levenshteinDistance("word", "word") == 0);
    }
    
    @Test
    public void levenshteinDistanceOneInsertBeginning() {
        PrefixTree tree = new PrefixTree();
        System.out.println("INSTERTONE");
        System.out.println(tree.levenshteinDistance("word", "ord"));
        assertTrue(tree.levenshteinDistance("word", "ord") == 1);
    }
    
    @Test
    public void levenshteinDistanceOneInsertMiddle() {
        PrefixTree tree = new PrefixTree();
        assertTrue(tree.levenshteinDistance("word", "wod") == 1);
    }
    
    @Test
    public void levenshteinDistanceTwoInsertBeginning() {
        PrefixTree tree = new PrefixTree();
        assertTrue(tree.levenshteinDistance("word", "wd") == 2);
    }
    
    @Test
    public void levenshteinDistanceDeleteOne() {
        PrefixTree tree = new PrefixTree();
        assertTrue(tree.levenshteinDistance("word", "words") == 1);
    }
    
    @Test
    public void levenshteinDistanceReplaceTwo() {
        PrefixTree tree = new PrefixTree();
        assertTrue(tree.levenshteinDistance("word", "wssd") == 2);
    }
    
    @Test
    public void levenshteinDistanceThreeEdits() {
        PrefixTree tree = new PrefixTree();
        assertTrue(tree.levenshteinDistance("kitten", "sitting") == 3);
    }
    
    @Test
    public void levenshteinWordsZeroDistance() {
        PrefixTree tree = new PrefixTree();
        tree.addWord("word");
        tree.addWord("water");
        tree.addWord("hello");
        tree.addWord("example");
        
        assertTrue(tree.LevenshteinDistanceWords("word", 0).toString().equals("[word]"));
    }
    
    @Test
    public void levenshteinWordsOneDistance() {
        PrefixTree tree = new PrefixTree();
        tree.addWord("word");
        tree.addWord("water");
        tree.addWord("hello");
        tree.addWord("example");
        
        assertTrue(tree.LevenshteinDistanceWords("word", 1).toString().equals("[word]"));
    }
    
    @Test
    public void levenshteinWordsTwoDistance() {
        PrefixTree tree = new PrefixTree();
        tree.addWord("word");
        tree.addWord("words");
        tree.addWord("worded");
        tree.addWord("wordeds");
        assertTrue(tree.LevenshteinDistanceWords("wordd", 2).toString().equals("[word, words, worded, wordeds]"));
    }
    
    @Test
    public void treeHasWord() {
        PrefixTree tree = new PrefixTree();
        tree.addWord("fish");
        tree.addWord("fishing");
        assertTrue(tree.containsWord("fish"));
    }
    
    @Test
    public void treeDoesntHaveWordButIsPrefixOfWord() {
        PrefixTree tree = new PrefixTree();
        tree.addWord("fishes");
        tree.addWord("fishing");
        assertTrue(!tree.containsWord("fish"));
    }
    
    @Test
    public void wordHasNoSubWords() {
        PrefixTree tree = new PrefixTree();
        tree.addWord("fishing");
        tree.addWord("waterfall");
        tree.addWord("snowing");
        
        assertTrue(tree.wordSplits("waterfall").toString().equals("[]"));
    }
    
    @Test
    public void wordHasTwoSubwords() {
        PrefixTree tree = new PrefixTree();
        tree.addWord("water");
        tree.addWord("fall");
        
        assertTrue(tree.wordSplits("waterfall").toString().equals("[water, fall]"));
    }
}
