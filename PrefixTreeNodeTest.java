package edu.brown.cs32.evenitz.autocorrect;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

public class PrefixTreeNodeTest {

    
    @Test
    public void getWordNoParent() {
        PrefixTreeNode node = new PrefixTreeNode("hello");
        assertTrue(node.getWord().equals("hello"));
    }
    
    @Test
    public void getWordOneParent() {
        PrefixTreeNode parent = new PrefixTreeNode("he");
        PrefixTreeNode node = new PrefixTreeNode("llo");
        parent.addChild(node);
        assertTrue(node.getWord().equals("hello"));
    }
    
    @Test
    public void getWordTwoParents() {
        PrefixTreeNode parent = new PrefixTreeNode("he");
        PrefixTreeNode node = new PrefixTreeNode("l");
        PrefixTreeNode node2 = new PrefixTreeNode("lo");
        node.addChild(node2);
        parent.addChild(node);
        assertTrue(node2.getWord().equals("hello"));
    }
    
    @Test
    public void isWord() {
        PrefixTreeNode node = new PrefixTreeNode("hello");
        node.makeWord();
        
        assertTrue(node.isWord());
    }
    
    @Test
    public void isNotWord() {
        PrefixTreeNode node = new PrefixTreeNode("hel");
        
        assertTrue(!node.isWord());
    }
    
    @Test
    public void getNextSingleOption() {
        PrefixTreeNode root = new PrefixTreeNode("");
        PrefixTreeNode node = new PrefixTreeNode("water");
        root.addChild(node);
        
        assertTrue(root.getNext("water").equals(node));
    }
    
    @Test
    public void getNextTwoOptions() {
        PrefixTreeNode root = new PrefixTreeNode("");
        PrefixTreeNode node1 = new PrefixTreeNode("st");
        PrefixTreeNode node2 = new PrefixTreeNode("wa");
        root.addChild(node1);
        root.addChild(node2);
        
        assertTrue(root.getNext("water").equals(node2));
    }
    
    @Test
    public void getNextTwoWrongOptions() {
        PrefixTreeNode root = new PrefixTreeNode("");
        PrefixTreeNode node1 = new PrefixTreeNode("st");
        PrefixTreeNode node2 = new PrefixTreeNode("wa");
        root.addChild(node1);
        root.addChild(node2);
        
        assertTrue(root.getNext("hello") == null);
    }
    
    @Test
    public void addChildToRoot() {
        PrefixTreeNode root = new PrefixTreeNode("");
        root.createChild("word");
        
        assertTrue(root.toString().equals("{ { word } }"));
    }
    
    @Test
    public void addMultipleChildrenToRoot() {
        PrefixTreeNode root = new PrefixTreeNode("");
        root.createChild("word");
        root.createChild("house");
        root.createChild("science");
        
        assertTrue(root.toString().equals("{ { word }{ house }{ science } }"));
    }
    
    @Test
    public void splitSingleWord() {
        PrefixTreeNode root = new PrefixTreeNode("");
        PrefixTreeNode node1 = new PrefixTreeNode("team");
        root.addChild(node1);
        
        node1.split(2, "test");
        assertTrue(root.toString().equals("{ { te{ am }{ st } } }"));
    }
    
    @Test
    public void splitIntoPrefix() {
        PrefixTreeNode root = new PrefixTreeNode("");
        PrefixTreeNode node1 = new PrefixTreeNode("mailbox");
        root.addChild(node1);
        
        node1.split(4, "mail");
        
        assertTrue(root.toString().equals("{ { mail{ box } } }"));
    }
    
    @Test
    public void splitWithChildren() {
        PrefixTreeNode root = new PrefixTreeNode("");
        PrefixTreeNode node1 = new PrefixTreeNode("te");
        PrefixTreeNode node2 = new PrefixTreeNode("st");
        PrefixTreeNode node3 = new PrefixTreeNode("am");
        node1.addChild(node2);
        node1.addChild(node3);
        root.addChild(node1);
        
        node1.split(1, "toast");
        assertTrue(root.toString().equals("{ { t{ e{ st }{ am } }{ oast } } }"));
    }
    
    @Test
    public void wordFromTop() {
        PrefixTreeNode node = new PrefixTreeNode("");
        PrefixTreeNode node1 = new PrefixTreeNode("word");
        node1.makeWord();
        node.addChild(node1);
        ArrayList<String> words = new ArrayList<String>();
        node.getChildrenWords("", words);
        System.out.println(words);
        assertTrue(words.toString().equals("[word]"));
    }
    
    @Test public void wordsFromNode() {
        PrefixTreeNode node = new PrefixTreeNode("");
        PrefixTreeNode node1 = new PrefixTreeNode("w");
        PrefixTreeNode node2 = new PrefixTreeNode("ord");
        PrefixTreeNode node3 = new PrefixTreeNode("rite");
        node2.makeWord();
        node3.makeWord();
        node1.addChild(node2);
        node1.addChild(node3);
        node.addChild(node1);
        ArrayList<String> words = new ArrayList<String>();
        node1.getChildrenWords("w", words);
        System.out.println(words);
        assertTrue(words.toString().equals("[word, write]"));
    }
    
    
}
