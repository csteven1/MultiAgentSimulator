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

import javax.swing.table.AbstractTableModel;

class CustomTableModel extends AbstractTableModel {

    private String[] columnNames;
    private Object[][] data;
    private int r;

    public CustomTableModel(Object[][] d, String[] cNames, int r) {
        data = d;
        columnNames = cNames;
        this.r = r;
    }

    public int getColumnCount() {
        return columnNames.length;
    }

    public int getRowCount() {
        return data.length;
    }

    public String getColumnName(int col) {
        return columnNames[col];
    }

    public Object getValueAt(int row, int col) {
        return data[row][col];
    }

    //Set editable cells
    public boolean isCellEditable(int row, int col) {
        return !(col < 1 || col > r);
    }

    //round cell value and calculate new value of the row's total
    public void setValueAt(Object value, int row, int col) {

        double t = (double) Math.round(Double.parseDouble(String.valueOf(value)) * 100000d) / 100000d;   //round input value to 5 decimel places
        data[row][col] = t; //set value in edited cell

        double tData = 0;

        //loop to calculate new total
        for (int i = 0; i < r; i++) {
            tData += Double.parseDouble(String.valueOf(data[row][i + 1]));
        }
        double total = (double) Math.round(tData * 100000d) / 100000d;   //round total value to 5 decimel places
        data[row][r + 1] = total; //set value in total cell

        fireTableRowsUpdated(row, row);  //update row
    }
}
