package tf.gui;

import static javax.swing.JComponent.setDefaultLocale;
import static javax.swing.SwingUtilities.invokeLater;
import static javax.swing.UIManager.getSystemLookAndFeelClassName;
import static javax.swing.UIManager.setLookAndFeel;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;

/**
 * Created by
 * Daniel Roesch
 * as "Nils Darmstrong".
 * -----------------
 * For "TextFormatter",
 * on 08.04.2017, 16:06.
 */
public class TextFormatterGUI extends JFrame {

    // TODO: NEEDS CHECKUP FOR ESCAPE CHARACTERS! CURRENTLY  NOT PRINTED IF NO JAVA SYNTAX IS USED!
    // can easily be altered if escape characters get an added backslash at the beginning.

    private JPanel centerPanel, topPanel, replacePanel, inPanel, textInPanel, outPanel, buttonPanel;
    private JLabel infoLabel, inLabel, outLabel, replaceLabel, withLabel;
    private JTextField inField, outField;
    private JTextArea inArea, outArea;
    private JScrollPane inScroll, outScroll;
    private JButton goButton, clearButton, outToClipboardButton, reuseTextButton, exitButton;
    private JRadioButton caseSensitiveRB;

    /**
     * constructor method to create the TextFormatterGUI object.
     */
    private TextFormatterGUI(){
        super("Text Formatter");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        setResizable(false);

        initComponents();
        addShitUp();
        addAction();

        setSize(600, 400);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    /**
     * method to initialize all the components that are defined in the scope.
     */
    private void initComponents(){
        centerPanel = new JPanel(new BorderLayout(10, 10));
        topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        replacePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        inPanel = new JPanel(new BorderLayout(10, 10));
        textInPanel = new JPanel(new BorderLayout(10, 10));
        outPanel = new JPanel(new BorderLayout(10, 10));
        buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        infoLabel = new JLabel("Insert a text that needs characters or substrings altered:");
        inLabel = new JLabel(" Input   ");
        outLabel = new JLabel(" Output");
        replaceLabel = new JLabel("Replace ");
        withLabel = new JLabel(" with ");
        inField = new JTextField(15);
        outField = new JTextField(15);
        inArea = new JTextArea(6, 30);
        inArea.setLineWrap(true);
        inArea.setWrapStyleWord(true);
        outArea = new JTextArea(6, 30);
        outArea.setLineWrap(true);
        outArea.setWrapStyleWord(true);
        inScroll = new JScrollPane(inArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        outScroll = new JScrollPane(outArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        goButton = new JButton("Go");
        clearButton = new JButton("Clear");
        outToClipboardButton = new JButton("Copy to Clipboard");
        outToClipboardButton.setEnabled(false);
        reuseTextButton = new JButton("Reuse output");
        reuseTextButton.setEnabled(false);
        exitButton = new JButton("Exit");
        caseSensitiveRB = new JRadioButton("replace case sensitive");
    }

    /**
     * method to add all components together.
     */
    private void addShitUp(){
        this.add(topPanel, "North");
        this.add(centerPanel, "Center");
        this.add(buttonPanel, "South");
        topPanel.add(infoLabel);
        centerPanel.add(inPanel, "North");
        centerPanel.add(outPanel, "Center");
        inPanel.add(textInPanel, "Center");
        inPanel.add(replacePanel, "South");
        outPanel.add(outLabel, "West");
        outPanel.add(outScroll, "Center");
        textInPanel.add(inLabel, "West");
        textInPanel.add(inScroll, "Center");
        replacePanel.add(replaceLabel);
        replacePanel.add(inField);
        replacePanel.add(withLabel);
        replacePanel.add(outField);
        replacePanel.add(caseSensitiveRB);
        buttonPanel.add(goButton);
        buttonPanel.add(outToClipboardButton);
        buttonPanel.add(reuseTextButton);
        buttonPanel.add(clearButton);
        buttonPanel.add(exitButton);
    }

    /**
     * method to add the action listeners for the buttons.
     */
    private void addAction(){
        goButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String inFieldStr = inField.getText();
                String outFieldStr = outField.getText();
                String inputText = inArea.getText();
                boolean caseSensitive = caseSensitiveRB.isSelected();
                if(inFieldStr.length() == 1 && outFieldStr.length() == 1){
                    outArea.setText(replaceSingleChar(inFieldStr.charAt(0),
                        outFieldStr.charAt(0), inputText, caseSensitive));
                } else {
                    outArea.setText(replaceSubstring(inFieldStr, outFieldStr,
                        inputText, caseSensitive));
                }
                if(!outArea.getText().equals("")){
                    outToClipboardButton.setEnabled(true);
                    reuseTextButton.setEnabled(true);
                }
            }
        });
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                inField.setText("");
                outField.setText("");
                inArea.setText("");
                outArea.setText("");
                outToClipboardButton.setEnabled(false);
                reuseTextButton.setEnabled(false);
            }
        });
        outToClipboardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                StringSelection stringSelection = new StringSelection(outArea.getText());
                Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                clipboard.setContents(stringSelection, null);
            }
        });
        reuseTextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                inArea.setText(outArea.getText());
                inField.setText("");
                outField.setText("");
                outArea.setText("");
                reuseTextButton.setEnabled(false);
                outToClipboardButton.setEnabled(false);
            }
        });
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }

    /**
     * method to change unwanted chars in a given string to a desired replacement char.
     * @param unwanted the unwanted char.
     * @param replacement the replacement char.
     * @param str the String that needs it's chars altered.
     * @param caseSensitive boolean true if only case sensitive letter will be changed, false else.
     * @return the String with the changed chars.
     */
    public static String replaceSingleChar(char unwanted, char replacement, String str,
        boolean caseSensitive){
        String replacedString = "";
        if(caseSensitive){
            StringBuilder replacedStringBuilder = new StringBuilder();
            for(int i = 0; i < str.length(); i++){
                if(str.charAt(i) != unwanted){
                    replacedStringBuilder.append(str.charAt(i));
                } else {
                    replacedStringBuilder.append(replacement);
                }
            }
            replacedString = replacedStringBuilder.toString();
        } else {    // case insensitive.
            char other;
            if(Character.isUpperCase(unwanted)){
                other = Character.toLowerCase(unwanted);
            } else {
                other = Character.toUpperCase(unwanted);
            }
            for(int i = 0; i < str.length(); i++){
                if(str.charAt(i) != unwanted && str.charAt(i) != other){
                    replacedString += str.charAt(i);
                } else {
                    replacedString += replacement;
                }
            }
        }
        return replacedString;
    }

    public static String replaceSubstring(String unwanted, String replacement, String string,
        boolean caseSensitive){
        if(!caseSensitive){
            unwanted = "(?i)" + unwanted;
        }
        Pattern p = Pattern.compile(unwanted);
        Matcher m = p.matcher(string);
        StringBuffer stringBuffer = new StringBuffer();
        while (m.find()){
            m.appendReplacement(stringBuffer, replacement);
        }
        m.appendTail(stringBuffer);
        return stringBuffer.toString();
    }

    /**
     * main method to get everything going. starts the gui within the designated thread.
     * @param args .
     */
    public static void main(String[] args){
        try{
            setLookAndFeel(getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException |
            UnsupportedLookAndFeelException | IllegalAccessException e){
            e.printStackTrace();
        }
        setDefaultLocale(Locale.ENGLISH);
        invokeLater(new Runnable(){
            @Override
            public void run(){
                new TextFormatterGUI();
            }
        });

    }

}
