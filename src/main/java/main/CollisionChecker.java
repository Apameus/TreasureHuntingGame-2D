package main;

import entity.Entity;

public class CollisionChecker {

    GamePanel gp;

    public CollisionChecker(GamePanel gp) {
        this.gp = gp;
    }

    public void checkTile(Entity entity){
        int entityLeftWorldX = entity.worldX + entity.solidArea.x;
        int entityRightWorldX = entity.worldX + entity.solidArea.x + entity.solidArea.width;
        int entityTopWorldY = entity.worldY + entity.solidArea.y;
        int entityBottomWorldY = entity.worldY + entity.solidArea.y +entity.solidArea.height;

        int entityLeftCol = entityLeftWorldX / gp.tileSize;
        int entityRightCol = entityRightWorldX / gp.tileSize;
        int entityTopRow = entityTopWorldY / gp.tileSize;
        int entityBottomRow = entityBottomWorldY / gp.tileSize;

        int tileNum1, tileNum2;

        switch (entity.direction){
            case "up" -> {
                entityTopRow = (entityTopWorldY - entity.speed) / gp.tileSize;
                tileNum1 = gp.tileManager.mapTileNumber[entityLeftCol][entityTopRow];
                tileNum2 = gp.tileManager.mapTileNumber[entityRightCol][entityTopRow];
                if (gp.tileManager.tile[tileNum1].collision || gp.tileManager.tile[tileNum2].collision){
                    entity.collisionOn = true;
                }
            }
            case "down" -> {
                entityBottomRow = (entityBottomWorldY + entity.speed) / gp.tileSize;
                tileNum1 = gp.tileManager.mapTileNumber[entityLeftCol][entityBottomRow];
                tileNum2 = gp.tileManager.mapTileNumber[entityRightCol][entityBottomRow];
                if (gp.tileManager.tile[tileNum1].collision || gp.tileManager.tile[tileNum2].collision){
                    entity.collisionOn = true;
                }
            }
            case "left" -> {
                entityLeftCol = (entityLeftWorldX - entity.speed) / gp.tileSize;
                tileNum1 = gp.tileManager.mapTileNumber[entityLeftCol][entityTopRow];
                tileNum2 = gp.tileManager.mapTileNumber[entityLeftCol][entityBottomRow];
                if (gp.tileManager.tile[tileNum1].collision || gp.tileManager.tile[tileNum2].collision){
                    entity.collisionOn = true;
                }
            }
            case "right" -> {
                entityRightCol = (entityRightWorldX + entity.speed) / gp.tileSize;
                tileNum1 = gp.tileManager.mapTileNumber[entityRightCol][entityTopRow];
                tileNum2 = gp.tileManager.mapTileNumber[entityRightCol][entityBottomRow];
                if (gp.tileManager.tile[tileNum1].collision || gp.tileManager.tile[tileNum2].collision){
                    entity.collisionOn = true;
                }
            }
        }
    }

    public int checkObject(Entity entity, boolean player){

        int index = 999;

        for (int i = 0; i < gp.obj.length; i++) {
            if (gp.obj[i] != null){
                // GET ENTITY SOLID AREA POSITION
                entity.solidArea.x = entity.worldX + entity.solidArea.x;
                entity.solidArea.y = entity.worldY + entity.solidArea.y;
                // GET OBJECT SOLID AREA POSITION
                gp.obj[i].solidArea.x = gp.obj[i].worldX + gp.obj[i].solidArea.x;
                gp.obj[i].solidArea.y = gp.obj[i].worldY + gp.obj[i].solidArea.y;
                switch (entity.direction){
                    case "up"  -> {
                        entity.solidArea.y -= entity.speed;
                        index = getIndex(entity, player, index, i);
                    }
                    case "down"  -> {
                        entity.solidArea.y += entity.speed;
                        index = getIndex(entity, player, index, i);
                    }
                    case "left"  -> {
                        entity.solidArea.x -= entity.speed;
                        index = getIndex(entity, player, index, i);
                    }
                    case "right"  -> {
                        entity.solidArea.x += entity.speed;
                        index = getIndex(entity, player, index, i);
                    }
                }
                entity.solidArea.x = entity.solidAreaDefaultX;
                entity.solidArea.y = entity.solidAreaDefaultY;
                gp.obj[i].solidArea.x = gp.obj[i].solidAreaDefaultX;
                gp.obj[i].solidArea.y = gp.obj[i].solidAreaDefaultY;
            }
        }

        return index;
    }

    private int getIndex(Entity entity, boolean player, int index, int i) {
        if (entity.solidArea.intersects(gp.obj[i].solidArea)){
            if (gp.obj[i].collision){
                entity.collisionOn = true;
            }
            if (player){
                index = i;
            }
        }
        return index;
    }
}
