package entity;

import engine.GameEngine;
import engine.GameEngine.*;

import static engine.GameEngine.*;

public class CollisionChecker {

    public void checkTile(Entity entity){

        int entityLeftWorldX = entity.worldX + entity.solidArea.x;
        int entityRightWorldX = entity.worldX + entity.solidArea.x + entity.solidArea.width;
        int entityTopWorldY = entity.worldY + entity.solidArea.y;
        int entityBottomWorldY = entity.worldY + entity.solidArea.y +entity.solidArea.height;

        int entityLeftRow = entityLeftWorldX / tileSize;
        int entityRightRow = entityRightWorldX / tileSize;
        int entityTopCol = entityTopWorldY / tileSize;
        int entityBottomCol = entityBottomWorldY / tileSize;

        int tileNum1, tileNum2;


        switch (entity.direction){
            case UP -> {
                entityTopCol = (entityTopWorldY - entity.speed) / tileSize;
                tileNum1 = tileManager.mapTileNumber[entityLeftRow][entityTopCol];
                tileNum2 = tileManager.mapTileNumber[entityRightRow][entityTopCol];
                if (tileManager.tile[tileNum1].collision || tileManager.tile[tileNum2].collision){
                    entity.collisionOn = true;
                }
            }
            case DOWN -> {
                entityBottomCol = (entityBottomWorldY + entity.speed) / tileSize;
                tileNum1 = tileManager.mapTileNumber[entityLeftRow][entityBottomCol];
                tileNum2 = tileManager.mapTileNumber[entityRightRow][entityBottomCol];
                if (tileManager.tile[tileNum1].collision || tileManager.tile[tileNum2].collision){
                    entity.collisionOn = true;
                }
            }
            case LEFT -> {
                entityLeftRow = (entityLeftWorldX - entity.speed) / tileSize;
                tileNum1 = tileManager.mapTileNumber[entityLeftRow][entityTopCol];
                tileNum2 = tileManager.mapTileNumber[entityLeftRow][entityBottomCol];
                if (tileManager.tile[tileNum1].collision || tileManager.tile[tileNum2].collision){
                    entity.collisionOn = true;
                }
            }
            case RIGHT -> {
                entityRightRow = (entityRightWorldX + entity.speed) / tileSize;
                tileNum1 = tileManager.mapTileNumber[entityRightRow][entityTopCol];
                tileNum2 = tileManager.mapTileNumber[entityRightRow][entityBottomCol];
                if (tileManager.tile[tileNum1].collision || tileManager.tile[tileNum2].collision){
                    entity.collisionOn = true;
                }
            }
        }
    }

    public int checkObject(Entity entity, boolean player){

        int index = 999;

        for (int i = 0; i < obj.length; i++) {
            if (obj[i] != null){
                // GET ENTITY SOLID AREA POSITION
                entity.solidArea.x = entity.worldX + entity.solidArea.x;
                entity.solidArea.y = entity.worldY + entity.solidArea.y;
                // GET OBJECT SOLID AREA POSITION
                obj[i].solidArea.x = obj[i].worldX + obj[i].solidArea.x;
                obj[i].solidArea.y = obj[i].worldY + obj[i].solidArea.y;
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
                obj[i].solidArea.x = obj[i].solidAreaDefaultX;
                obj[i].solidArea.y = obj[i].solidAreaDefaultY;
            }
        }
        return index;
    }

    private int getIndex(Entity entity, boolean player, int index, int i) {
        if (entity.solidArea.intersects(obj[i].solidArea)){
            if (obj[i].collision){
                entity.collisionOn = true;
            }
            if (player){
                index = i;
            }
        }
        return index;
    }
}
