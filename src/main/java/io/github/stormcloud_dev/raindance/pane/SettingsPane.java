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

import io.github.stormcloud_dev.raindance.RainDance;
import io.github.stormcloud_dev.raindance.component.LabeledSpinner;

import javax.swing.*;
import java.awt.*;

public class SettingsPane extends JPanel {

    private RainDance rainDance;

    private LabeledSpinner widthSpinner;
    private LabeledSpinner heightSpinner;

    public SettingsPane(RainDance rainDance) {
        this.rainDance = rainDance;
        widthSpinner = new LabeledSpinner("Width: ", new SpinnerNumberModel(640, 0, Integer.MAX_VALUE, 1));
        widthSpinner.addChangeListener(event -> {
            rainDance.getMapEditorPane().getMap().setWidth((int) widthSpinner.getValue());
            rainDance.removeScrollPane();
            rainDance.getMapEditorPane().setPreferredSize(new Dimension((int) widthSpinner.getValue(), (int) heightSpinner.getValue()));
            rainDance.addScrollPane();
            rainDance.revalidate();
            rainDance.repaint();
        });
        add(widthSpinner);
        heightSpinner = new LabeledSpinner("Height: ", new SpinnerNumberModel(480, 0, Integer.MAX_VALUE, 1));
        heightSpinner.addChangeListener(event -> {
            rainDance.getMapEditorPane().getMap().setHeight((int) heightSpinner.getValue());
            rainDance.removeScrollPane();
            rainDance.getMapEditorPane().setPreferredSize(new Dimension((int) widthSpinner.getValue(), (int) heightSpinner.getValue()));
            rainDance.addScrollPane();
            rainDance.revalidate();
            rainDance.repaint();
        });
        add(heightSpinner);
    }

}
