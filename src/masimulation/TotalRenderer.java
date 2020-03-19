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

import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 * Cell renderer for total column.
 */
class TotalRenderer extends DefaultTableCellRenderer {

    int r;

    public TotalRenderer(int r) {
        this.r = r;
    }

    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Component editor = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        if (column > r) {
            if (Double.parseDouble(String.valueOf(value)) == 1) {
                editor.setBackground(Color.green);
            } else {
                editor.setBackground(Color.red);
            }
        } else {
            editor.setBackground(Color.white);
        }
        return editor;
    }
}
