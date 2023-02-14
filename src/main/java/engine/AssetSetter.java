package engine;

import object.*;

public class AssetSetter {
    GameEngine gp;

    public AssetSetter(GameEngine gp) {
        this.gp = gp;
    }

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
        gp.obj[index] = object;
        gp.obj[index].worldX = worldX * gp.tileSize;
        gp.obj[index].worldY = worldY * gp.tileSize;
    }

}
