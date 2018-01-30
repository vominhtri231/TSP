package GUI;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Line2D;
import java.io.Serializable;

@SuppressWarnings("serial")
public class Line implements Serializable
{
	double cost;
	int r=10;
	int ind1,ind2;
	public Line(int p1,int p2,double cost)
	{
		cost=Math.round(cost * 10.0) / 10.0;
		this.cost=cost;
		ind1=p1;
		ind2=p2;
	}
	public void drawLine(Graphics2D g,MyPoint point1,MyPoint point2,Color color)
	{		
		
		String c = "";
		if (cost <= 0) 
		{
			c = "";
		} 
		else
			c = String.valueOf(cost);
		g.setColor(color);
		g.setStroke(new BasicStroke(2));
		double theta = Math.atan2(point2.y - point1.y, point2.x - point1.x);
		double x=r * Math.cos(theta), y=r * Math.sin(theta);
		g.draw(new Line2D.Double(point1.x+x,point1.y+y,point2.x-x,point2.y-y));
		drawArrow(g, theta, point2.x-x,  point2.y-y);
		g.drawString(c, (int) (Math.abs(point1.x + point2.x) / 2),
				(int) (point1.y + point2.y) / 2);
	}
	private void drawArrow(Graphics2D g, double theta, double x0, double y0)
	{
		double len=10,phi=Math.PI/6;
		g.setStroke(new BasicStroke(2));
		double x = x0 - len* Math.cos(theta + phi);
		double y = y0 - len* Math.sin(theta + phi);
		g.draw(new Line2D.Double(x0, y0, x, y));
		x = x0 -  len* Math.cos(theta - phi);
		y = y0 -  len* Math.sin(theta - phi);
		g.draw(new Line2D.Double(x0, y0, x, y));
		
	}
	public boolean havePoint(Point point) {
		
		return false;
	}
	
}
