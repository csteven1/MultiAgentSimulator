/*
    Copyright 2017 csteven1

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
*/
package masimulation;

import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.Dimension;
import javax.swing.JOptionPane;

/**
 * The starting window, requesting the initial specifications needed before producing the probability table.
 * Once completed, a new TableContainer is run and this frame disposed of.
 */
public class StartEntries extends JPanel implements ActionListener {

    JTextField[] field = new JTextField[3];
    JFrame frame;

    public StartEntries() {
        super();
        this.setPreferredSize(new Dimension(520, 145));

        String[] list = {"Number of Rooms, >1: ", "Number of People, >0: ", "Number of Entries (Length of dataset), >0: ", ""};

        JButton next = new JButton("Next: Probability Table");

        //create inner panel
        JPanel innerPanel = new JPanel(new GridLayout(0, 2, 5, 10));
        innerPanel.setPreferredSize(new Dimension(500, 130));

        JLabel[] labels = new JLabel[list.length];

        //Initially fill fields
        for (int i = 0; i < field.length; i++) {
            field[i] = new JTextField("");
        }

        //add labels and fields to panel
        for (int i = 0; i < list.length - 1; i++) {
            labels[i] = new JLabel(list[i], JLabel.TRAILING);
            innerPanel.add(labels[i]);
            labels[i].setLabelFor(field[i]);
            innerPanel.add(field[i]);
        }
        labels[list.length - 1] = new JLabel(list[list.length - 1], JLabel.TRAILING);   //empty label to push button to right of the panel
        innerPanel.add(labels[list.length - 1]);
        innerPanel.add(next);

        next.addActionListener(this);

        this.add(innerPanel);

        //create frame and add panel to it
        frame = new JFrame("Simulation Specifications");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);   //exit on close
        frame.add(this);   //add panel to frame

        //Display the window.
        frame.pack();   //size the frame
        frame.setLocationRelativeTo(null);  //center the frame
        frame.setVisible(true); //show it
    }

    public void actionPerformed(ActionEvent e) {
        String rString = field[0].getText();
        String pString = field[1].getText();
        String lString = field[2].getText();

        try {
            int r = Integer.parseInt(rString);
            int p = Integer.parseInt(pString);
            int l = Integer.parseInt(lString);
            if ((r < 2) || (p < 1) || (l < 1)) {
                JOptionPane.showMessageDialog(null, "Invalid entries. Please enter valid values", "Invalid Entries", JOptionPane.ERROR_MESSAGE);
            } else {
                javax.swing.SwingUtilities.invokeLater(new Runnable() { //if fields are valid, generate table model
                    public void run() {
                        new TableContainer(r, p, l);
                    }
                });
                frame.dispose();    //and dispose of current frame
            }
        } catch (NumberFormatException c) {
            JOptionPane.showMessageDialog(null, "Invalid entries. Please enter valid values", "Invalid Entries", JOptionPane.ERROR_MESSAGE);
        }
    }
}
