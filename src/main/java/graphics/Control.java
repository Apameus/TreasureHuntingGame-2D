package graphics;

import entity.Direction;

import java.util.List;

public enum Control {
    MOVE_UP,
    MOVE_DOWN,
    MOVE_LEFT,
    MOVE_RIGHT;

    public Direction direction(){
        return switch (this){
            case MOVE_UP -> Direction.UP;
            case MOVE_DOWN -> Direction.DOWN;
            case MOVE_LEFT -> Direction.LEFT;
            case MOVE_RIGHT -> Direction.RIGHT;
        };
    }

    public static final List<Control> CONTROLS = List.of(Control.values());
}
