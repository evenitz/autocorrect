package edu.brown.cs32.evenitz.autocorrect;

import java.util.ArrayList;

public class PrefixTreeNode {
    
    private PrefixTreeValue value;
    private boolean isWord;
    public PrefixTreeNode parent;
    public ArrayList<PrefixTreeNode> children;
    
    public PrefixTreeNode(String value) {
        this.value = new PrefixTreeValue(value);
        this.children = new ArrayList<PrefixTreeNode>();
    }
    
    /**
     * createChild
     * Creates a node with the given value and appends it to this
     * nodes children.
     * 
     * @param string the value of the new child
     */
    public void createChild(String string) {
        PrefixTreeNode child = new PrefixTreeNode(string);
        child.makeWord();
        this.addChild(child);
    }
    
    public PrefixTreeNode getChild(int index) {
        return this.children.get(index);
    }
    
    public PrefixTreeNode getParent() {
        return this.parent;
    }
    
    /**
     * addChild
     * Takes a node and appends it to this node's children
     * 
     * @param node the node to append
     */
    public void addChild(PrefixTreeNode node) {
        if (!node.getValue().equals("")) {
            node.setParent(this);
            this.children.add(node);
        }
    }
    
    /**
     * addChildren
     * a convenience method to add multiple children at once
     * 
     * @param newChildren an ArrayList of PrefixTreeNodes
     */
    private void addChildren(ArrayList<PrefixTreeNode> newChildren) {
        this.children = newChildren;
        
        for (PrefixTreeNode node : this.children) {
            node.setParent(this);
        }
    }
    
    public void removeChild(String str) {
        PrefixTreeNode oldNode = null;
        for (PrefixTreeNode node : this.children) {
            if (node.getValue().equals(str)) {
                node.setParent(null);
                oldNode = node;
                break;
            }
        }
        if (oldNode != null) {
            this.children.remove(oldNode);
        }
    }
    
    public void setParent(PrefixTreeNode node) {
        this.parent = node;
    }
    
    /**
     * split
     * splits this node around the given index. The first new node takes the value of
     * this nodes value from 0 to the index and replaces this node as its parents child.
     * The first child of this new node is the remaining value of this nodes value and
     * takes on this node's children. The second child of this node's replacement is a new
     * node with the value of the passed in string split around index. 
     * 
     * @param index
     * @param newWord
     */
    public void split(int index, String newWord) {
        PrefixTreeNode parent = this.getParent();
        
        PrefixTreeNode newPrefix = new PrefixTreeNode(this.getValue().substring(0, index));
        PrefixTreeNode oldChild = new PrefixTreeNode(this.getValue().substring(index));
        PrefixTreeNode newChild = new PrefixTreeNode(newWord.substring(index));
        newChild.makeWord();
        if (this.isWord()) {
            oldChild.makeWord();
        }
        oldChild.addChildren(this.children);
                                          
        if (newChild.getValue().compareTo(oldChild.getValue()) < 0) {
            newPrefix.addChild(newChild);
            newPrefix.addChild(oldChild);
        } else {
            newPrefix.addChild(oldChild);
            newPrefix.addChild(newChild);
        }        
        parent.addChild(newPrefix);
        
        parent.removeChild(this.getValue());
    }
    
    public boolean hasChildren() {
        return false;
    }
    
    public boolean isWord() {
        return this.isWord;
    }
    
    public void makeWord() {
        this.isWord = true;
    }
    
    /**
     * getWord
     * gets the word of this node by this node by appending all of this node's
     * ancestor's values to the beginning of this nodes value.
     * 
     * @return this string formed by this nodes value combined with its parent's
     */
    public String getWord() {
        PrefixTreeNode node = this.parent;
        String word = this.getValue();
        while (node != null) {
            word = node.getValue() + word;
            node = node.getParent();
        }
        return word;
    }
    
    /**
     * getNext
     * Gets the node that matches this node's children
     * 
     * @param  string the string to compare this nodes children with
     * @return a PrefixTreeNode is there is a match
     */
    public PrefixTreeNode getNext(String string) {
        for (PrefixTreeNode node : this.children) {
            if (node.isPrefixOf(string)) {
                return node;
            }
        }
        return null;
    }
    
    public String getValue() {
        return this.value.getValue();
    }
    
    public int childrenSize() {
        return this.children.size();
    }
    
    /**
     * isPrefixOf
     * determines whether this nodes value is a prefix of the given word
     * 
     * @param word
     * @return
     */
    public boolean isPrefixOf(String word) {
        return this.value.isPrefixOf(word);
    }
    
    public int isPartialPrefixOf(String word) {
        return this.value.isPartialPrefixOf(word);
    }
    
    /**
     * 
     * @param currentString
     * @param words
     */
    public void getChildrenWords(String currentString, ArrayList<String> words) {
        if (this.isWord()) {
            words.add(currentString);
        }
        for (PrefixTreeNode node : this.children) {
            node.getChildrenWords(currentString + node.getValue(), words);
        }
    }
    
    @Override
    public String toString() {
        String str = "{ " + this.getValue() + "";
        for (PrefixTreeNode node : this.children) {
            str += node.toString();
        }
        str += " }";
        return str;
    }
    
}
