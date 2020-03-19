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
import javax.swing.DefaultCellEditor;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

/**
 * Cell format editor for double input between 0 and 1 only.
 */
public class DoubleEditor extends DefaultCellEditor {

    DoubleEditor() {
        super(new JTextField());
    }

    public boolean stopCellEditing() {
        boolean i = true;

        while (i == true) {
            try {
                double d = Double.parseDouble((String) getCellEditorValue());
                if (d < 0 || d > 1) {
                    ErrorMessage();
                    return false;
                }
                i = false;
            } catch (NumberFormatException n) {
                ErrorMessage();
                return false;
            }
        }
        return super.stopCellEditing();
    }

    public void ErrorMessage() {
        JTextField textField = (JTextField) getComponent();
        textField.setBorder(new LineBorder(Color.red));
        textField.selectAll();
        textField.requestFocusInWindow();

        JOptionPane.showMessageDialog(
                null,
                "Please enter value between 0 and 1",
                "Incorrect Input", JOptionPane.ERROR_MESSAGE);
    }
}
