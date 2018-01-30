package GUI;

import java.awt.Component;
import javax.swing.JPopupMenu;

@SuppressWarnings("serial")
public class MyPopupMenu extends JPopupMenu 
{
	public MyPoint point;

	public MyPopupMenu()
	{
	}

	public void show(Component invoker, int x, int y) {
		point = new MyPoint(x, y);
		super.show(invoker, x, y);
	}
}
