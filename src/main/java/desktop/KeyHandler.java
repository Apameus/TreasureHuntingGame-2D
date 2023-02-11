package desktop;

import graphics.Control;
import graphics.Input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.BitSet;
import java.util.Map;

import static graphics.Control.*;
import static java.awt.event.KeyEvent.*;

public class KeyHandler implements KeyListener, Input {

    public final Map<Integer, Control> keyMap;
    public final BitSet bitSet;

    public KeyHandler() {
        bitSet = new BitSet();
        keyMap = Map.of(
                VK_W, MOVE_UP,
                VK_S, MOVE_DOWN,
                VK_A, MOVE_LEFT,
                VK_D, MOVE_RIGHT
        );
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        set(e.getKeyCode(), true);
    }

    @Override
    public void keyReleased(KeyEvent e) {
       set(e.getKeyCode(), false);
    }

    private void set(int keyCode, boolean active) {
        Control control = keyMap.get(keyCode);
        if (control != null){
            bitSet.set(control.ordinal(), active);
        }
    }

    @Override
    public boolean isActive(Control control) {
        return bitSet.get(control.ordinal());
    }
}
