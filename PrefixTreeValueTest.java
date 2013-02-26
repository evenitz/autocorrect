package edu.brown.cs32.evenitz.autocorrect;

import static org.junit.Assert.*;

import org.junit.Test;

public class PrefixTreeValueTest {

    
    @Test
    public void isPrefixOf() {
        PrefixTreeValue treeVal = new PrefixTreeValue("pre");
        assertTrue(treeVal.isPrefixOf("prefix"));
    }
    
    @Test
    public void isNotPrefixOf() {
        PrefixTreeValue treeVal = new PrefixTreeValue("pre");
        assertTrue(!treeVal.isPrefixOf("suffix"));
    }
    
    @Test
    public void isNotPrefixOfWithLaterMatch() {
        PrefixTreeValue treeVal = new PrefixTreeValue("pre");
        assertTrue(!treeVal.isPrefixOf("aprefix"));
    }
    
    @Test
    public void isMatchPrefix() {
        PrefixTreeValue treeVal = new PrefixTreeValue("word");
        assertTrue(treeVal.isPrefixOf("word"));
    }
    
    @Test
    public void isPrefixTooLong() {
        PrefixTreeValue treeVal = new PrefixTreeValue("prefix");
        assertTrue(!treeVal.isPrefixOf("pref"));
    }
    
    @Test
    public void isPartialPrefix() {
        PrefixTreeValue treeVal = new PrefixTreeValue("prefix");
        System.out.println(treeVal.isPartialPrefixOf("prepared"));
        assertTrue(treeVal.isPartialPrefixOf("prepared") == 3);
    }
    
    @Test
    public void isNotPartialPrefix() {
        PrefixTreeValue treeVal = new PrefixTreeValue("prefix");
        assertTrue(treeVal.isPartialPrefixOf("notAPrefix") == -1);
    }
    
    @Test
    public void partialPrefixWrongFirstLetter() {
        PrefixTreeValue treeVal = new PrefixTreeValue("prefix");
        assertTrue(treeVal.isPartialPrefixOf("aprefix") == -1);
    }
    
    @Test
    public void partialPrefixEmptyString() {
        PrefixTreeValue treeVal = new PrefixTreeValue("word");
        assertTrue(treeVal.isPartialPrefixOf("") == -1);
    }
    
    @Test
    public void removePartialPrefix(){
        PrefixTreeValue treeVal = new PrefixTreeValue("wo");
        assertTrue(treeVal.removePrefix("word").equals("rd"));
    }
    
    @Test
    public void removeAllPrefix() {
        PrefixTreeValue treeVal = new PrefixTreeValue("word");
        assertTrue(treeVal.removePrefix("word").equals(""));
    }
    
    
}
