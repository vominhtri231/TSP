package GUI;


import java.io.Serializable;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class MyData  implements Serializable
{	
	public ArrayList<MyPoint> arrPoint;
	public ArrayList<Line> arrLine;
	public MyData() 
	{
		arrPoint = new ArrayList<MyPoint>();
		arrLine = new ArrayList<Line>();
	}
	public void clear()
	{
		arrPoint.clear();
		arrLine.clear();
	}
	public MyData copy()
	{
		MyData res=new MyData();
		for(int i=0;i<this.arrPoint.size();i++)
		{
			res.arrPoint.add(this.arrPoint.get(i));
		}
		for(int i=0;i<this.arrLine.size();i++)
		{
			res.arrLine.add(this.arrLine.get(i));
		}
		return res;
		
	}

}
