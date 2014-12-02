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
import io.github.stormcloud_dev.raindance.map.Map;
import io.github.stormcloud_dev.raindance.object.MapObject;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;
import java.util.Set;
import java.util.Stack;
import java.util.stream.Collectors;

import static javax.swing.SwingUtilities.isLeftMouseButton;
import static javax.swing.SwingUtilities.isRightMouseButton;

public class MapEditorPane extends JPanel {

    private RainDance rainDance;
    private Map map;
    private Stack<Runnable> undoableActions;

    public MapEditorPane(RainDance rainDance) {
        this.rainDance = rainDance;
        map = new Map(640, 480);
        undoableActions = new Stack<>();
        setPreferredSize(new Dimension(getMap().getWidth(), getMap().getHeight()));
        addMouseListener(new MouseInputAdapter() {
            @Override
            public void mouseClicked(MouseEvent event) {
                handleClick(event);
            }
        });
        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent event) {
                handleClick(event);
            }
        });
    }

    private void handleClick(MouseEvent event) {
        if (!rainDance.getToolbar().isLocked()) {
            if (isLeftMouseButton(event)) {
                int x = event.getX();
                int y = event.getY();
                if (!event.isAltDown()) {
                    int snapX = rainDance.getToolbar().getSnapX();
                    int snapY = rainDance.getToolbar().getSnapY();
                    x = (x / snapX) * snapX;
                    y = (y / snapY) * snapY;
                }
                MapObject object = rainDance.getObjectsPane().createObject(x, y);
                getMap().addObject(object);
                undoableActions.push(() -> getMap().removeObject(object));
            } else if (isRightMouseButton(event)) {
                int snapX = rainDance.getToolbar().getSnapX();
                int snapY = rainDance.getToolbar().getSnapY();
                int x = (event.getX() / snapX) * snapX;
                int y = (event.getY() / snapY) * snapY;
                Set<MapObject> objectsToDelete = getMap().getObjects().stream().filter(object -> object.getX() >= x && object.getX() < x + snapX && object.getY() >= y && object.getY() < y + snapY).collect(Collectors.toSet());
                getMap().removeObjects(objectsToDelete);
                undoableActions.push(() -> getMap().addObjects(objectsToDelete));
            }
            repaint();
        }
    }

    public Map getMap() {
        return map;
    }

    public void setMap(Map map) {
        this.map = map;
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        getMap().paint(graphics);
        if (rainDance.getToolbar().isGridEnabled()) {
            graphics.setColor(Color.WHITE);
            for (int x = 0; x < getMap().getWidth(); x += rainDance.getToolbar().getSnapX()) {
                graphics.drawLine(x, 0, x, getMap().getHeight());
            }
            for (int y = 0; y < getMap().getHeight(); y += rainDance.getToolbar().getSnapY()) {
                graphics.drawLine(0, y, getMap().getWidth(), y);
            }
        }
    }

    public void undo() {
        undoableActions.pop().run();
    }

    public void clearObjects() {
        java.util.List<MapObject> oldObjects = new ArrayList<>();
        oldObjects.addAll(getMap().getObjects());
        undoableActions.push(() -> {
            for (MapObject object : oldObjects) {
                getMap().addObject(object);
            }
        });
        getMap().clearObjects();
    }
}
