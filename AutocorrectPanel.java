package edu.brown.cs32.evenitz.autocorrect;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.event.*;

public class AutocorrectPanel extends JPanel implements ListSelectionListener {
    
    private JList list;
    private DefaultListModel listModel;
    private JTextArea textField; 
   
    private Autocorrecter autocorrecter;
    
    
    private static final long serialVersionUID = 1L;

    public AutocorrectPanel(Autocorrecter autocorrecter) {
        super(new BorderLayout());
        this.autocorrecter = autocorrecter;
        listModel = new DefaultListModel();
        
        list = new JList(listModel);
        list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        list.setSelectedIndex(0);
        list.addListSelectionListener(this);
        list.setVisibleRowCount(5);
        JScrollPane listScrollPane = new JScrollPane(list);
        JPanel textPanel = new JPanel();
        textField = new JTextArea(5, 30);
        TypeListener textListener = new TypeListener();
        textField.getDocument().addDocumentListener(textListener);
        textPanel.add(textField);
        
        add(textPanel, BorderLayout.NORTH);
        add(listScrollPane, BorderLayout.CENTER);
        
        textField.getInputMap().put( KeyStroke.getKeyStroke( "ENTER" ), "doEnterAction" );
        textField.getActionMap().put( "doEnterAction", new EnterAction());
        
        textField.getInputMap().put( KeyStroke.getKeyStroke( "DOWN" ), "doUpAction" );
        textField.getActionMap().put( "doUpAction", new UpAction());
        
        textField.getInputMap().put( KeyStroke.getKeyStroke( "UP" ), "doDownAction" );
        textField.getActionMap().put( "doDownAction", new DownAction());
    }
    
    class EnterAction extends AbstractAction {
        private static final long serialVersionUID = 1L;

        @Override
        public void actionPerformed(ActionEvent arg0) {
            
            if (listModel.size() < 1) {
                return;
            }
            
            String[] words = textField.getText().split("\\s+");
            int index = list.getSelectedIndex();
            
            if (index < 0) {
                words[words.length - 1] = listModel.firstElement().toString();
            } else {
                words[words.length - 1] = listModel.elementAt(index).toString();
            }
            
            String newLine = "";
            
            for (String s : words) {
                newLine += s;
                newLine += " ";
            }
            newLine.trim();
            textField.setText(newLine);
            listModel.removeAllElements();
        }
        
    }
    
    class UpAction extends AbstractAction {
        private static final long serialVersionUID = 1L;

        @Override
        public void actionPerformed(ActionEvent arg0) {
            int index = list.getSelectedIndex() + 1;
            index = index > listModel.getSize() ? listModel.getSize() : index;
            list.setSelectedIndex(index);
        }
    }
    
    class DownAction extends AbstractAction {
        private static final long serialVersionUID = 1L;

        @Override
        public void actionPerformed(ActionEvent arg0) {
            int index = list.getSelectedIndex() - 1;
            index = index > 0 ? index : 0;
            list.setSelectedIndex(index);
        }
    }
    
    class TypeListener implements ActionListener, DocumentListener {
        public void actionPerformed(ActionEvent e) {
            
        }

        @Override
        public void changedUpdate(DocumentEvent arg0) {
            updateList();
        }

        @Override
        public void insertUpdate(DocumentEvent arg0) {
            updateList();
        }

        @Override
        public void removeUpdate(DocumentEvent arg0) {
            updateList();
        }
        
        public void updateList() {
            listModel.removeAllElements();
            String[] words = textField.getText().toLowerCase().split("\\s+");
            
            int len = words.length;
            String word = words[len - 1];
            String previous = "";
            if (len > 1) {
                previous = words[len - 2];
            }
            
            
            ArrayList<String> suggestions = autocorrecter.topSuggestions(word, previous, 5);
            
            for (String s : suggestions) {
                listModel.addElement(s);
            }
        }
    }
    

    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (e.getValueIsAdjusting() == false) {
            
        }
    }
}
