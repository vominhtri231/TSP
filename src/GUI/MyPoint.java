package GUI;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.io.Serializable;

@SuppressWarnings("serial")
public class MyPoint extends Point implements Serializable
{
	int r=10;
	
	public MyPoint(int x, int y) 
	{
		this.x=x;
		this.y=y;
	}
	
	public MyPoint copy()
	{
		MyPoint p=new MyPoint(this.x,this.y);
		return p;
	}
	public void set(Point p)
	{
		this.x=p.x;
		this.y=p.y;
	}
	public void move(int dx,int dy)
	{
		this.x+=dx;
		this.y+=dy;
	}
	public void draw(Graphics2D g, int index) 
	{
		drawPoint(g, index);
		drawIndex(g, index);
	}

	private void drawIndex(Graphics2D g, int index)
	{
		g.setColor(Color.black);
		int stringLen = (int) g.getFontMetrics().getStringBounds(String.valueOf(index), g).getWidth();
		int stringHeight = (int) g.getFontMetrics().getStringBounds(String.valueOf(index), g).getHeight();
		int startX = -stringLen/2 ;
		int startY = stringHeight/2 ;
		g.drawString(String.valueOf(index), (int) this.x+startX, (int) this.y+startY);
	}

	private void drawPoint(Graphics2D g, int index)
	{
		g.setColor(Color.ORANGE);
		g.setStroke(new BasicStroke(2));
		Ellipse2D.Float el = new Ellipse2D.Float(this.x-r,this.y-r,2*r,2*r);
		g.draw(el);
	}
}
