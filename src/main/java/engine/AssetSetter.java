package engine;

import object.*;

import static engine.GameEngine.*;

public class AssetSetter {

    public void setObject(){
        set(0, new Key(), 23, 7);
        set(1, new Key(), 23, 40);
        set(2, new Key(), 38, 8);
        set(3, new Door(), 10, 12);
        set(4, new Door(), 8, 28);
        set(5, new Door(), 12, 23);
        set(6, new Chest(), 10, 9);
        set(7, new Boots(), 37, 42);
    }
    private void set(int index, SuperObject object, int worldX, int worldY){
        obj[index] = object;
        obj[index].worldX = worldX * tileSize;
        obj[index].worldY = worldY * tileSize;
    }

}
