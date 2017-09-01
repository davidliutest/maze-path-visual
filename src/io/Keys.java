package io;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Keys implements KeyListener{
	
	private boolean[] keys;
	public boolean u, d, l, r;
	
	public Keys() {
		keys = new boolean[1000];
	}
	
	public void tick() {
		u = keys[KeyEvent.VK_UP];
		d = keys[KeyEvent.VK_DOWN];
		l = keys[KeyEvent.VK_LEFT];
		r = keys[KeyEvent.VK_RIGHT];
	}
	
	@Override
	public void keyPressed(KeyEvent e) {keys[e.getKeyCode()] = true;}
	@Override
	public void keyReleased(KeyEvent e) {keys[e.getKeyCode()] = false;}
	@Override
	public void keyTyped(KeyEvent e) {}

}
