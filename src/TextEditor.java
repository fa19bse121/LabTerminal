
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Stack;
import javax.swing.event.DocumentListener;
import javax.swing.event.DocumentEvent;




import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;



public class TextEditor extends JFrame implements ActionListener {
    private JTextArea textArea;
    private Stack<TextEditorStates> states;
    private JButton undoButton;
    private JButton redoButton;


    private TextEditorStates lastPoppedState;


    public TextEditor() {
        //this is the constructor of the texteditor class
        setTitle("Design Patterns Terminal Lab");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        lastPoppedState = null;


        textArea = new JTextArea();
        states = new Stack<TextEditorStates>();

        undoButton = new JButton("Undo");
        undoButton.addActionListener(this);
        redoButton = new JButton("Redo");
        redoButton.addActionListener(this);
        redoButton.setEnabled(false);
        undoButton.setEnabled(false);


        JPanel buttonPanel = new JPanel();
        buttonPanel.add(undoButton);
        buttonPanel.add(redoButton);

        add(textArea, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        textArea.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) {
                saveState();
            }

            public void removeUpdate(DocumentEvent e) {
                saveState();
            }

            public void changedUpdate(DocumentEvent e) {
                saveState();
            }
        });

    }

    private void saveState() {
        if (lastPoppedState != null) {
            states.push(lastPoppedState);
            lastPoppedState = null;
        }
        states.push(new TextEditorStates(textArea.getText()));
        redoButton.setEnabled(false);

        undoButton.setEnabled(true);
        redoButton.setEnabled(false);


    }


    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == undoButton) {
            if (!states.empty()) {
                lastPoppedState = states.pop();
                textArea.setText(lastPoppedState.getState());
                redoButton.setEnabled(true);
            } else {
                undoButton.setEnabled(false);
            }
        } else if (e.getSource() == redoButton) {
            if (lastPoppedState != null) {
                textArea.setText(lastPoppedState.getState());
                lastPoppedState = null;
                redoButton.setEnabled(false);
            } else {
                redoButton.setEnabled(false);
            }
        }
    }
}
