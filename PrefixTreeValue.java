package edu.brown.cs32.evenitz.autocorrect;

public class PrefixTreeValue {
    private String value;
    
    public PrefixTreeValue(String val) {
        this.value = val;
    }
    
    public String getValue() {
        return this.value;
    }
    
    /**
     * isPrefixOf
     * determines whether the nodes value is a prefix of the passed in
     * string.
     * 
     * @param string
     * @return true if all of the characters in this nodes value have a match
     * in the passed in string.
     */
    public boolean isPrefixOf(String string) {
        char []prefix = this.value.toCharArray();
        char []stringArray = string.toCharArray();
        
        int len = prefix.length;
        int strLen = stringArray.length;
        
        // if string is longer then this.value cant be a prefix
        if (len > strLen) {
            return false;
        }
        
        for (int i = 0; i < len; i++) {
            if (prefix[i] != stringArray[i]) {
                return false;
            }
        }
        
        return true;
    }
    
    /**
     * isPartialPrefixOf
     * Given a string, determines the index where the string and this
     * nodes value stop sharing characters in common.
     * 
     * @param  a string to compare against this node's value
     * @return the index after the last shared character
     */
    public int isPartialPrefixOf(String string) {
        char []prefix = this.value.toCharArray();
        char []stringArray = string.toCharArray();
        
        int len = prefix.length;
        int strLen = stringArray.length;
        
        int index = -1;
        
        for (int i = 0; i < len && i < strLen; i++) {
            if (prefix[i] == stringArray[i]) {
                index = i + 1;
            } else {
                break;
            }
        }
        
        return index;
    }
    
    /**
     * removePrefix
     * If this node's value is a prefix of the passed in string,
     * this method returns a string with the nodes value removed
     * from the string.
     * 
     * @param  the word to remove the prefix from
     * @return a string without this nodes value.
     */
    public String removePrefix(String word) {
        return word.substring(this.value.length());
    }
    
}
