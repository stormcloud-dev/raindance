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

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.io.IOException;

import static java.lang.Integer.MAX_VALUE;
import static javax.swing.JFileChooser.APPROVE_OPTION;
import static javax.swing.JOptionPane.showMessageDialog;

public class ToolbarPane extends JPanel {

    private RainDance rainDance;
    private JToggleButton btnLock;
    private JSpinner snapXSpinner;
    private JSpinner snapYSpinner;
    private JToggleButton btnGrid;

    public ToolbarPane(RainDance rainDance) {
        this.rainDance = rainDance;
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        try {
            setupComponents();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    private void setupComponents() throws IOException {
        setupSaveButton();
        setupOpenButton();
        setupUndoButton();
        setupClearButton();
        setupLockButton();
        add(new JLabel("Snap X:"));
        snapXSpinner = new JSpinner(new SpinnerNumberModel(16, 0, MAX_VALUE, 1));
        snapXSpinner.addChangeListener(event -> rainDance.getMapEditorPane().repaint());
        add(snapXSpinner);
        add(new JLabel("Snap Y:"));
        snapYSpinner = new JSpinner(new SpinnerNumberModel(16, 0, MAX_VALUE, 1));
        snapYSpinner.addChangeListener(event -> rainDance.getMapEditorPane().repaint());
        add(snapYSpinner);
        setupGridButton();
    }

    private void setupSaveButton() throws IOException {
        JButton btnSave = new JButton(new ImageIcon(ImageIO.read(getClass().getResourceAsStream("/save.png"))));
        btnSave.addActionListener(event -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileFilter(new FileNameExtensionFilter("JSON file", "json"));
            if (fileChooser.showSaveDialog(null) == APPROVE_OPTION) {
                File saveFile = fileChooser.getSelectedFile();
                if (!saveFile.getName().endsWith(".json")) {
                    saveFile = new File(saveFile.getParent(), saveFile.getName() + ".json");
                }
                try {
                    rainDance.saveWorld(saveFile);
                } catch (IOException exception) {
                    showMessageDialog(null, exception.getMessage());
                    exception.printStackTrace();
                }
            }
        });
        btnSave.setToolTipText("Save");
        add(btnSave);
    }

    private void setupOpenButton() throws IOException {
        JButton btnOpen = new JButton(new ImageIcon(ImageIO.read(getClass().getResourceAsStream("/open.png"))));
        btnOpen.addActionListener(event -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileFilter(new FileNameExtensionFilter("JSON file", "json"));
            if (fileChooser.showOpenDialog(null) == APPROVE_OPTION) {
                File openFile = fileChooser.getSelectedFile();
                try {
                    rainDance.openWorld(openFile);
                    rainDance.getMapEditorPane().repaint();
                } catch (IOException exception) {
                    showMessageDialog(null, exception.getMessage());
                    exception.printStackTrace();
                }
            }
        });
        btnOpen.setToolTipText("Open");
        add(btnOpen);
    }

    private void setupUndoButton() throws IOException {
        JButton btnUndo = new JButton(new ImageIcon(ImageIO.read(getClass().getResourceAsStream("/undo.png"))));
        btnUndo.addActionListener(event -> {
            rainDance.undo();
            rainDance.getMapEditorPane().repaint();
        });
        btnUndo.setToolTipText("Undo");
        add(btnUndo);
    }

    private void setupClearButton() throws IOException {
        JButton btnClear = new JButton(new ImageIcon(ImageIO.read(getClass().getResourceAsStream("/clear.png"))));
        btnClear.addActionListener(event -> {
            rainDance.getMapEditorPane().clearObjects();
            rainDance.getMapEditorPane().repaint();
        });
        btnClear.setToolTipText("Clear objects");
        add(btnClear);
    }

    private void setupLockButton() throws IOException {
        btnLock = new JToggleButton();
        btnLock.setIcon(new ImageIcon(ImageIO.read(getClass().getResourceAsStream("/lock.png"))));
        btnLock.setSelectedIcon(new ImageIcon(ImageIO.read(getClass().getResourceAsStream("/unlock.png"))));
        btnLock.setToolTipText("Lock");
        btnLock.addChangeListener(event -> {
            if (btnLock.isSelected())
                btnLock.setToolTipText("Unlock");
            else
                btnLock.setToolTipText("Lock");
        });
        add(btnLock);
    }

    private void setupGridButton() throws IOException {
        btnGrid = new JToggleButton();
        btnGrid.setIcon(new ImageIcon(ImageIO.read(getClass().getResourceAsStream("/grid.png"))));
        btnGrid.setToolTipText("Toggle grid");
        btnGrid.setSelected(true);
        btnGrid.addActionListener(event -> rainDance.getMapEditorPane().repaint());
        add(btnGrid);
    }

    public boolean isLocked() {
        return btnLock.isSelected();
    }

    public boolean isGridEnabled() {
        return btnGrid.isSelected();
    }

    public int getSnapX() {
        return (int) snapXSpinner.getValue();
    }

    public int getSnapY() {
        return (int) snapYSpinner.getValue();
    }

}
