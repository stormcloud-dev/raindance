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

import java.awt.image.BufferedImage;
import java.util.Map;

import static io.github.stormcloud_dev.raindance.object.ObjectType.ROPE;

public class Rope extends MapObject {

    public Rope(int x, int y, BufferedImage image) {
        super(x, y, image);
    }

    @Override
    public Map<String, Object> serialise() {
        Map<String, Object> serialised = super.serialise();
        serialised.put("type", "rope");
        return serialised;
    }

    public static Rope deserialise(Map<String, Object> serialised) {
        return ROPE.create((int) ((double) serialised.get("x")), (int) ((double) serialised.get("y")));
    }

}
