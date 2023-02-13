package entity;

import engine.GameEngine;

public class CollisionChecker {

    GameEngine gp;

    public CollisionChecker(GameEngine gp) {
        this.gp = gp;
    }

    public void checkTile(Entity entity){

        int entityLeftWorldX = entity.worldX + entity.solidArea.x;
        int entityRightWorldX = entity.worldX + entity.solidArea.x + entity.solidArea.width;
        int entityTopWorldY = entity.worldY + entity.solidArea.y;
        int entityBottomWorldY = entity.worldY + entity.solidArea.y +entity.solidArea.height;

        int entityLeftRow = entityLeftWorldX / gp.tileSize;
        int entityRightRow = entityRightWorldX / gp.tileSize;
        int entityTopCol = entityTopWorldY / gp.tileSize;
        int entityBottomCol = entityBottomWorldY / gp.tileSize;

        int tileNum1, tileNum2;

        switch (entity.direction){
            case UP -> {
                entityTopCol = (entityTopWorldY - entity.speed) / gp.tileSize;
                tileNum1 = gp.tileManager.mapTileNumber[entityLeftRow][entityTopCol];
                tileNum2 = gp.tileManager.mapTileNumber[entityRightRow][entityTopCol];
                if (gp.tileManager.tile[tileNum1].collision || gp.tileManager.tile[tileNum2].collision){
                    entity.collisionOn = true;
                }
            }
            case DOWN -> {
                entityBottomCol = (entityBottomWorldY + entity.speed) / gp.tileSize;
                tileNum1 = gp.tileManager.mapTileNumber[entityLeftRow][entityBottomCol];
                tileNum2 = gp.tileManager.mapTileNumber[entityRightRow][entityBottomCol];
                if (gp.tileManager.tile[tileNum1].collision || gp.tileManager.tile[tileNum2].collision){
                    entity.collisionOn = true;
                }
            }
            case LEFT -> {
                entityLeftRow = (entityLeftWorldX - entity.speed) / gp.tileSize;
                tileNum1 = gp.tileManager.mapTileNumber[entityLeftRow][entityTopCol];
                tileNum2 = gp.tileManager.mapTileNumber[entityLeftRow][entityBottomCol];
                if (gp.tileManager.tile[tileNum1].collision || gp.tileManager.tile[tileNum2].collision){
                    entity.collisionOn = true;
                }
            }
            case RIGHT -> {
                entityRightRow = (entityRightWorldX + entity.speed) / gp.tileSize;
                tileNum1 = gp.tileManager.mapTileNumber[entityRightRow][entityTopCol];
                tileNum2 = gp.tileManager.mapTileNumber[entityRightRow][entityBottomCol];
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
                    case UP  -> {
                        entity.solidArea.y -= entity.speed;
                        index = getIndex(entity, player, index, i);
                    }
                    case DOWN  -> {
                        entity.solidArea.y += entity.speed;
                        index = getIndex(entity, player, index, i);
                    }
                    case LEFT  -> {
                        entity.solidArea.x -= entity.speed;
                        index = getIndex(entity, player, index, i);
                    }
                    case RIGHT  -> {
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
