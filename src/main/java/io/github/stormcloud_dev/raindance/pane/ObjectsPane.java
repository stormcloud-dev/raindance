/*
 *   Copyright 2014 StormCloud Development Group
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */
package io.github.stormcloud_dev.raindance.pane;

import io.github.stormcloud_dev.raindance.object.MapObject;
import io.github.stormcloud_dev.raindance.object.ObjectType;

import javax.swing.*;

import static javax.swing.BoxLayout.Y_AXIS;

public class ObjectsPane extends JPanel {

    private JComboBox<ObjectType> comboBox;
    private JTextField txtOtherType;

    public ObjectsPane() {
        setLayout(new BoxLayout(this, Y_AXIS));
        comboBox = new JComboBox<>(ObjectType.values());
        comboBox.addActionListener(event -> {
            if (comboBox.getSelectedItem() == ObjectType.OTHER)
                txtOtherType.setEnabled(true);
            else
                txtOtherType.setEnabled(false);
        });
        add(comboBox);
        txtOtherType = new JTextField();
        txtOtherType.setEnabled(false);
        add(txtOtherType);
    }

    public MapObject createObject(int x, int y) {
        return ((ObjectType) comboBox.getSelectedItem()).create(x, y, txtOtherType.getText());
    }

}
