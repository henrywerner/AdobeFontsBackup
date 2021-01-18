import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.beans.*;

// Uses a lot of borrowed code from https://docs.oracle.com/javase/tutorial/uiswing/components/progress.html

public class ProgressGUI extends JPanel implements ActionListener, PropertyChangeListener {

    private JProgressBar progressBar;
    private JButton startButton;
    private JTextArea taskOutput;
    //private Task task;

    public ProgressGUI() {
        super(new BorderLayout());

        //Create the demo's UI.
        startButton = new JButton("Start");
        startButton.setActionCommand("start");
        startButton.addActionListener(this);

        progressBar = new JProgressBar(0, 100); //TODO: change '100' to number of conversions
        progressBar.setValue(0);

        //Call setStringPainted now so that the progress bar height
        //stays the same whether or not the string is shown.
        progressBar.setStringPainted(true);

        taskOutput = new JTextArea(5, 30);
        taskOutput.setMargin(new Insets(5,5,5,5));
        taskOutput.setEditable(false);

        JPanel panel = new JPanel();
        panel.add(startButton);
        panel.add(progressBar);

        add(panel, BorderLayout.PAGE_START);
        add(new JScrollPane(taskOutput), BorderLayout.CENTER);
        setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
    }

    /**
     * Invoked when the user presses the start button.
     */
    public void actionPerformed(ActionEvent evt) {
        progressBar.setIndeterminate(true);
        startButton.setEnabled(false);

        //// Invoke task class for the conversion process ////

        //task = new Task();
        //task.addPropertyChangeListener(this);
        //task.execute();
    }

    /**
     * Invoked when task's progress property changes.
     */
    public void propertyChange(PropertyChangeEvent evt) {
        if ("progress" == evt.getPropertyName()) {
            int progress = (Integer) evt.getNewValue();
            progressBar.setIndeterminate(false);
            progressBar.setValue(progress);
            taskOutput.append(String.format(
                    "Completed %d%% of task.\n", progress));
        }
    }

    private static void spawnGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("BackupFonts");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Create and set up the content pane.
        JComponent newContentPane = new ProgressGUI();
        newContentPane.setOpaque(true); //content panes must be opaque
        frame.setContentPane(newContentPane);

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                spawnGUI();
            }
        });
    }
}
