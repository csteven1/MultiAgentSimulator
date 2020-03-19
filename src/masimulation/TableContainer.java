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
import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Random;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import java.io.Writer;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import javax.swing.ToolTipManager;

/** Container for the BuildTable panel and execution buttons.
*   A simulation is executed on a 'perform' button press, using
*   the sim() and runSim() methods, after validating table values.
*/
public class TableContainer extends JPanel implements ActionListener {

    JButton save, perform;
    BuildTable x;
    int r, p, l;
    JFileChooser fc;
    File file;

    public TableContainer(int r, int p, int l) {
        super();

        this.r = r;
        this.p = p;
        this.l = l;

        JPanel contentPane = new JPanel(new BorderLayout());

        this.x = new BuildTable(r);
        JPanel y = new JPanel();
        this.save = new JButton("Set Save Location");
        this.perform = new JButton("Perform Simulation");
        perform.setEnabled(false);

        y.add(save);
        y.add(perform);

        //Create a file chooser
        fc = new JFileChooser();

        save.addActionListener(this);
        perform.addActionListener(this);

        perform.setToolTipText("Set save location first");

        contentPane.add(x, BorderLayout.CENTER);
        contentPane.add(y, BorderLayout.SOUTH);
        contentPane.setPreferredSize(new Dimension(1100, 500));

        //create frame and add panel to it
        JFrame frame = new JFrame("Movement Probabilities");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(contentPane);

        //Display the window.
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == save) {
            int returnVal = fc.showSaveDialog(TableContainer.this);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                file = fc.getSelectedFile();
            }
            if (file != null) {
                perform.setEnabled(true);
                ToolTipManager.sharedInstance().setEnabled(false);  //disable tooltip on perform button
            }
        } else if (e.getSource() == perform) {
            boolean t = false;
            //loop through total column to find incorrect values
            for (int i = 0; i < r; i++) {
                if (Double.parseDouble(String.valueOf(x.table.getValueAt(i, r + 1))) != 1) {
                    t = true;
                }
            }
            if (t == true) {
                JOptionPane.showMessageDialog(null, "Incorrect values entered in table, all row totals must equal 1", "Incorrect Table Values", JOptionPane.ERROR_MESSAGE);
            } else {
                sim();  //perform simulation with inputted values
            }
        } else {
            System.err.println("Error, wrong button pressed");
        }
    }

    //get values from table and perform simulation
    public void sim() {
        //create table
        double[][] probMatrix = new double[r][r];

        //fill probability matrix with values from table
        for (int i = 0; i < r; i++) {
            for (int j = 0; j < r; j++) {
                probMatrix[i][j] = Double.parseDouble(String.valueOf(x.table.getValueAt(i, j + 1)));
            }
        }
        int[] person = new int[p];
        int[] room = new int[r];

        //calculate room populations based on people's locations
        for (int a = 0; a < room.length; a++) {
            for (int b = 0; b < person.length; b++) {
                if (person[b] == a) {
                    room[a]++;
                }
            }
        }
        Writer writer = null;
        try {
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file + ".csv")));
            //print column headings
            for (int i = 0; i < r; i++) {
                writer.write("Room" + (i + 1) + ",");
            }
            writer.write("\n");

            //loop to print room populations during simulation
            for (int i = 0; i < l; i++) {   //loop through length of dataset
                for (int j = 0; j < p; j++) {   //loop through each person
                    room[person[j]]--;  //take person out of room
                    person[j] = runSim(person[j], probMatrix); // runSim to find new room for person
                    room[person[j]]++; // update room count with this person
                }
                for (int j = 0; j < r; j++) {
                    writer.write(room[j] + ",");
                }
                writer.write("\n");
            }
        } catch (IOException ex) {
            System.err.println(ex);
        } finally {
            try {
                writer.close();
                JOptionPane.showMessageDialog(null, "Simulation performed successfully", "Simulation Successful", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception ex) {
                System.err.println(ex);
            }
        }
    }

    /**
     * Called once per person. n is the current room location of the person,
     * matrix is the index of the matrix for the specific time period. Returns
     * their new room location.
     */
    public int runSim(int n, double[][] matrix) {
        Random rand = new Random();

        double target = rand.nextDouble();	//selects double between 0 and 1 
        double current = 1;

        //Calculate movements between rooms
        for (int i = r - 1; i >= 0; i--) {
            current -= matrix[n][i];
            System.out.printf("Current: %.2f\n", current);
            if (target >= current) {
                System.out.printf("Result: arr[%d]: value: %.2f\n", i, matrix[n][i]);
                return i;   //return new location
            }
        }
        return 0;
    }
}
