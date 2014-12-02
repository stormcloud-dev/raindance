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

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public enum ObjectType {

    BLOCK("Block", (x, y, data) -> new Block(x, y, getBlockImage())),
    BLOCK_BOSS_SPAWN("Block (Boss Spawn)", (x, y, data) -> new BlockBossSpawn(x, y, getBlockBossSpawnImage())),
    BLOCK_NO_SPAWN("Block (No spawn)", (x, y, data) -> new BlockNoSpawn(x, y, getBlockNoSpawnImage())),
    ROPE("Rope", (x, y, data) -> new Rope(x, y, getRopeImage())),
    OTHER("Other", (x, y, data) -> new OtherObject(x, y, getOtherImage(), (String) data[0]));
    
    private static BufferedImage blockImage;
    private static BufferedImage blockBossSpawnImage;
    private static BufferedImage blockNoSpawnImage;
    private static BufferedImage ropeImage;
    private static BufferedImage otherImage;
    
    private static BufferedImage getBlockImage() {
        if (blockImage == null) try {
            blockImage = ImageIO.read(ObjectType.class.getResourceAsStream("/block.png"));
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        return blockImage;
    }

    private static BufferedImage getBlockBossSpawnImage() {
        if (blockBossSpawnImage == null) try {
            blockBossSpawnImage = ImageIO.read(ObjectType.class.getResourceAsStream("/block_boss_spawn.png"));
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        return blockBossSpawnImage;
    }

    private static BufferedImage getBlockNoSpawnImage() {
        if (blockNoSpawnImage == null) try {
            blockNoSpawnImage = ImageIO.read(ObjectType.class.getResourceAsStream("/block_no_spawn.png"));
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        return blockNoSpawnImage;
    }

    private static BufferedImage getRopeImage() {
        if (ropeImage == null) try {
            ropeImage = ImageIO.read(ObjectType.class.getResourceAsStream("/rope.png"));
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        return ropeImage;
    }

    private static BufferedImage getOtherImage() {
        if (otherImage == null) try {
            otherImage = ImageIO.read(ObjectType.class.getResourceAsStream("/unknown.png"));
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        return otherImage;
    }

    private interface ObjectInitialiser<T extends MapObject> {
        public T initialize(int x, int y, Object... data);
    }

    private String name;
    private ObjectInitialiser initialiser;

    private ObjectType(String name, ObjectInitialiser initialiser) {
        this.name = name;
        this.initialiser = initialiser;
    }

    @Override
    public String toString() {
        return name;
    }

    public <T> T create(int x, int y, Object... data) {
        return (T) initialiser.initialize(x, y, data);
    }

}
