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
package io.github.stormcloud_dev.raindance.component;

import javax.swing.*;
import javax.swing.event.ChangeListener;

import static javax.swing.BoxLayout.X_AXIS;

public class LabeledSpinner extends JPanel {

    private JLabel label;
    private JSpinner spinner;

    public LabeledSpinner(String labelText, SpinnerModel spinnerModel) {
        setLayout(new BoxLayout(this, X_AXIS));
        label = new JLabel(labelText);
        add(label);
        spinner = new JSpinner(spinnerModel);
        label.setLabelFor(spinner);
        add(spinner);
    }

    public String getText() {
        return label.getText();
    }

    public void setText(String text) {
        label.setText(text);
    }

    public Object getValue() {
        return spinner.getValue();
    }

    public void addChangeListener(ChangeListener listener) {
        spinner.addChangeListener(listener);
    }

}
