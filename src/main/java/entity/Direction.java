package entity;

public enum Direction {
    UP,
    DOWN,
    LEFT,
    RIGHT;

    public void move(Entity entity){
        switch (this){
            case UP -> entity.worldY -= entity.speed;
            case DOWN -> entity.worldY += entity.speed;
            case LEFT -> entity.worldX -= entity.speed;
            case RIGHT -> entity.worldX += entity.speed;
        }
    }
}
