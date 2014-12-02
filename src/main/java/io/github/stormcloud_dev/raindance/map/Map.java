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
package io.github.stormcloud_dev.raindance.map;

import io.github.stormcloud_dev.raindance.object.MapObject;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class Map {

    private List<MapObject> objects;
    private int width;
    private int height;

    public Map(int width, int height) {
        this.width = width;
        this.height = height;
        objects = new ArrayList<>();
    }

    public void addObject(MapObject object) {
        for (MapObject existingObject : getObjects()) {
            if (object.getClass() == existingObject.getClass() && object.getX() == existingObject.getX() && object.getY() == existingObject.getY()) return;
        }
        getObjects().add(object);
    }

    public void removeObject(MapObject object) {
        getObjects().remove(object);
    }

    public void addObjects(Collection<MapObject> objects) {
        getObjects().addAll(objects);
    }

    public void removeObjects(Collection<MapObject> objects) {
        getObjects().removeAll(objects);
    }

    public Collection<MapObject> getObjects() {
        return objects;
    }

    public void clearObjects() {
        getObjects().clear();
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void paint(Graphics graphics) {
        graphics.setColor(Color.BLACK);
        graphics.fillRect(0, 0, getWidth(), getHeight());
        Graphics2D graphics2D = (Graphics2D) graphics;
        for (MapObject object : getObjects()) {
            graphics2D.translate(object.getX(), object.getY());
            object.paint(graphics);
            graphics2D.translate(-object.getX(), -object.getY());
        }
    }

    public java.util.Map<String, Object> serialise() {
        java.util.Map<String, Object> serialised = new HashMap<>();
        serialised.put("width", getWidth());
        serialised.put("height", getHeight());
        List<java.util.Map<String, Object>> objectData = getObjects().stream().map(MapObject::serialise).collect(Collectors.toList());
        serialised.put("objects", objectData);
        return serialised;
    }

    public static Map deserialise(java.util.Map<String, Object> serialised) {
        Map deserialised = new Map((int) ((double) serialised.get("width")), (int) ((double) serialised.get("height")));
        for (java.util.Map<String, Object> objectData : (List<java.util.Map<String, Object>>) serialised.get("objects")) {
            deserialised.addObject(MapObject.deserialise(objectData));
        }
        return deserialised;
    }

}
