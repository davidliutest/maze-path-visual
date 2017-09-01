package io;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

public class Mouse implements MouseListener, MouseMotionListener, MouseWheelListener{
	
	private boolean left, right;
	private int mx, my, wheel;

	@Override
	public void mousePressed(MouseEvent e) {
		if(e.getButton() == MouseEvent.BUTTON1)
			left = true;
		else if(e.getButton() == MouseEvent.BUTTON3)
			right = true;
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if(e.getButton() == MouseEvent.BUTTON1)
			left = false;
		if(e.getButton() == MouseEvent.BUTTON3)
			right = false;
	}
	 
	@Override
	public void mouseMoved(MouseEvent e) {
		mx = e.getX(); my = e.getY();
	}
	
	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		int notches = e.getWheelRotation();
		if(notches < 0) wheel++; else wheel--;
	}
	
	public boolean left() {return left;}
	public boolean right() {return right;}
	public int mx() {return mx;}
	public int my() {return my;}
	public int wheel() {return wheel;}
	public void wheelReset() {wheel = 0;}
	
	@Override
	public void mouseClicked(MouseEvent e) {}
	@Override
	public void mouseDragged(MouseEvent e) {}
	@Override
	public void mouseEntered(MouseEvent e) {}
	@Override
	public void mouseExited(MouseEvent e) {}

}
