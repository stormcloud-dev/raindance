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
package io.github.stormcloud_dev.raindance.object;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

public abstract class MapObject {

    private int x;
    private int y;
    private BufferedImage image;

    public MapObject(int x, int y, BufferedImage image) {
        this.x = x;
        this.y = y;
        this.image = image;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void paint(Graphics graphics) {
        graphics.drawImage(image, 0, 0, null);
    }

    public Map<String, Object> serialise() {
        Map<String, Object> serialised = new HashMap<>();
        serialised.put("x", getX());
        serialised.put("y", getY());
        serialised.put("type", "other");
        return serialised;
    }

    public static MapObject deserialise(Map<String, Object> serialised) {
        switch ((String) serialised.get("type")) {
            case "block": return Block.deserialise(serialised);
            case "block_boss_spawn": return BlockBossSpawn.deserialise(serialised);
            case "block_no_spawn": return BlockNoSpawn.deserialise(serialised);
            case "rope": return Rope.deserialise(serialised);
            default: return OtherObject.deserialise(serialised);
        }
    }

}
