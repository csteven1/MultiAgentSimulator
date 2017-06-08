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

import java.awt.Dimension;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableColumn;

/**
 *  Panel holding the build of the movement probability table
 */
public class BuildTable extends JPanel {

    protected JTable table;

    public BuildTable(int r) {
        super();

        //Set Columnn Names according to number of rooms (+2 for row names and total)
        String[] columnNames = new String[r + 2];
        columnNames[0] = "";
        for (int i = 1; i < r + 1; i++) {
            columnNames[i] = "To Room " + String.valueOf(i);
        }
        columnNames[columnNames.length - 1] = "Total";

        //set number of cells according to number of rooms 
        Object[][] data = new Object[r][r + 2];   //Object[rows][columns]

        //Set Row Names
        for (int i = 0; i < r; i++) {
            data[i][0] = "From Room " + String.valueOf(i + 1);
        }

        double init = 0;    //intial value to fill table

        //fill initial table
        for (int i = 0; i < r; i++) {   //i=row, j=column
            for (int j = 1; j < r + 1; j++) {
                data[i][j] = init;
            }
        }

        //fill initial total column to avoid nullPointerException
        for (int i = 0; i < r; i++) {
            data[i][r + 1] = init;
        }

        //calculate initial total column
        for (int i = 0; i < r; i++) {
            for (int j = 1; j < r + 1; j++) {
                double t = (Double) data[i][j];
                data[i][r + 1] = (Double) data[i][r + 1] + t;
            }
        }

        this.table = new JTable(new CustomTableModel(data, columnNames, r));    //create table using customtablemodel

        table.setPreferredScrollableViewportSize(new Dimension(1090, 420));
        table.setFillsViewportHeight(true);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        //set width of first column
        TableColumn firstCol = table.getColumnModel().getColumn(0);
        firstCol.setPreferredWidth(90);

        //use custom editor for input cells
        TableCellEditor editCells = new DoubleEditor();
        for (int i = 1; i < r + 1; i++) {
            table.getColumnModel().getColumn(i).setCellEditor(editCells);
        }

        //use custom renderer for total column
        table.setDefaultRenderer(Object.class, new TotalRenderer(r));

        //Create the scroll pane and add the table to it.
        JScrollPane scrollPane = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        add(scrollPane);
    }
}
