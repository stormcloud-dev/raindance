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
package io.github.stormcloud_dev.raindance;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import io.github.stormcloud_dev.raindance.map.Map;
import io.github.stormcloud_dev.raindance.pane.MapEditorPane;
import io.github.stormcloud_dev.raindance.pane.ObjectsPane;
import io.github.stormcloud_dev.raindance.pane.SettingsPane;
import io.github.stormcloud_dev.raindance.pane.ToolbarPane;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.Scanner;

import static java.awt.BorderLayout.*;

public class RainDance extends JFrame {

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | UnsupportedLookAndFeelException | IllegalAccessException exception) {
            exception.printStackTrace();
        }
        RainDance frame = new RainDance();
        EventQueue.invokeLater(() -> frame.setVisible(true));
    }

    private MapEditorPane mapEditorPane;
    private JScrollPane mapEditorScrollPane;
    private ToolbarPane toolbar;
    private ObjectsPane objectsPane;
    private SettingsPane settingsPane;

    public RainDance() {
        super("RainDance");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        mapEditorPane = new MapEditorPane(this);
        mapEditorScrollPane = new JScrollPane(mapEditorPane);
        add(mapEditorScrollPane, CENTER);
        toolbar = new ToolbarPane(this);
        add(toolbar, NORTH);
        JTabbedPane tabbedPane = new JTabbedPane();
        objectsPane = new ObjectsPane();
        tabbedPane.add("Objects", objectsPane);
        settingsPane = new SettingsPane(this);
        tabbedPane.add("Settings", settingsPane);
        tabbedPane.setPreferredSize(new Dimension(256, 320));
        add(tabbedPane, WEST);
        pack();
        setLocationRelativeTo(null);
    }

    public MapEditorPane getMapEditorPane() {
        return mapEditorPane;
    }

    public JScrollPane getMapEditorScrollPane() {
        return mapEditorScrollPane;
    }

    public ToolbarPane getToolbar() {
        return toolbar;
    }

    public ObjectsPane getObjectsPane() {
        return objectsPane;
    }

    public SettingsPane getSettingsPane() {
        return settingsPane;
    }

    public void saveWorld(File file) throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(getMapEditorPane().getMap().serialise());
        FileWriter writer = new FileWriter(file);
        writer.write(json);
        writer.close();
    }

    public void openWorld(File file) throws FileNotFoundException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        Scanner scanner = new Scanner(new FileInputStream(file));
        StringBuilder jsonBuilder = new StringBuilder();
        while (scanner.hasNextLine()) {
            jsonBuilder.append(scanner.nextLine()).append('\n');
        }
        java.util.Map mapData = gson.fromJson(jsonBuilder.toString(), new TypeToken<java.util.Map<String, Object>>(){}.getType());
        getMapEditorPane().setMap(Map.deserialise(mapData));
    }

    public void undo() {
        getMapEditorPane().undo();
    }

    public void removeScrollPane() {
        remove(getMapEditorScrollPane());
        mapEditorScrollPane = null;
    }

    public void addScrollPane() {
        mapEditorScrollPane = new JScrollPane(getMapEditorPane());
        add(getMapEditorScrollPane(), BorderLayout.CENTER);
    }

}
