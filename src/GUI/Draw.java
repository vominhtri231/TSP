package GUI;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.JFileChooser;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;


import TSP.Map;

@SuppressWarnings("serial")
public class Draw extends JPanel implements MouseListener ,ActionListener
{
	private int x,y,type=0,r=10,typeMap=0;
	public int step=0;
	private MyPoint point1=new MyPoint(0,0),point2=new MyPoint(0,0);
	private MyData data ;
	private String path=null;
	private boolean drawResult=false,saved=true,drawStep=false;
	private MyPopupMenu popup;
	JMenuItem change,delete;
	public int[] result;
	public Draw()
	{
		data = new MyData();
		popup=new MyPopupMenu();
		delete=new JMenuItem("delete");
		change=new JMenuItem("change cost");
		delete.addActionListener(this);
		change.addActionListener(this);
		popup.add(delete);
		popup.add(change);
		
		this.addMouseListener(this);
	}
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		setBackground(Color.white);
		Graphics2D g2d = (Graphics2D) g;
		reDraw(g2d, true);

		if (drawResult)
		{
			if(!drawStep) 
			{
				drawResult(g2d,result);
			}
			else
			{
				drawResultStep(g2d);
			}
		}

	}
	
	
	public void mouseDragged(MouseEvent e) 
	{
	
	}	

	public void mouseMoved(MouseEvent e)
	{

	}

	public void mouseClicked(MouseEvent e) 
	{
		x = e.getX();
		y = e.getY();
		MyPoint p=new MyPoint(x,y);
		if(e.getButton()!=MouseEvent.BUTTON3)
		{
			if (type == 1) 
			{ 
				data.arrPoint.add(p);
				if(typeMap==2)
				{
					for(int i=0;i<data.arrPoint.size()-1;i++)
					{
						MyPoint a=data.arrPoint.get(i);
						double dist=Math.sqrt((a.x-p.x)*(a.x-p.x)+(a.y-p.y)*(a.y-p.y));
						Line l=new Line(i,data.arrPoint.size()-1,dist);
						data.arrLine.add(l);
						l=new Line(data.arrPoint.size()-1,i,dist);
						data.arrLine.add(l);
					}
				}
				drawResult=false;
				saved=false;
				repaint();
			}
		}
		else
		{
			int indp=indexPointContain(p),indl=indexLineContain(p);
			if(indp>=0||indl>=0) 
			{
				this.popup.show(Draw.this,x,y);
			}
		}
	}
	
	public void mouseEntered(MouseEvent e)
	{
		
	}
	
	public void mouseExited(MouseEvent e) 
	{
		
	}
	
	public void mousePressed(MouseEvent e) 
	{
		point1.set(e.getPoint());
	}

	public void mouseReleased(MouseEvent e)
	{
		point2.set(e.getPoint());
		int ind1=indexPointContain(point1),ind2=indexPointContain(point2);
		if(ind1>=0)
		{
			if(ind2>=0&&type==2&&ind1!=ind2)
			{
				int cost = showDialogCost(ind1,ind2);
				Line l=new Line(ind1,ind2,cost);
				data.arrLine.add(l);
				if(typeMap==1)
				{
					l=new Line(ind2,ind1,cost);
					data.arrLine.add(l);
				}
				drawResult=false;
				saved=false;
					
			}
			if(ind2<0&&type==3)
			{
				int dx=point2.x-point1.x,dy=point2.y-point1.y;
				data.arrPoint.get(ind1).move(dx, dy);
			}
		}
		repaint();
	}
	public void actionPerformed(ActionEvent e) 
	{
		String command=e.getActionCommand();
		int indexPoint=indexPointContain(popup.point),indexLine=indexLineContain(popup.point);
		if(command=="delete") 
		{
			if(indexPoint>=0)
			{
				String message = "Ban muon xoa diem " + indexPoint;
				int select = JOptionPane.showConfirmDialog(this, message,"Xoa diem", JOptionPane.OK_CANCEL_OPTION);
				if (select == 0) 
				{
					this.deletePoint(indexPoint);
				}
			}
			else 
			{
				
				Line line=data.arrLine.get(indexLine);
				String message = "Ban muon xoa duong tu "+ line.ind1 + " toi " + line.ind2+" ?";
				int select = JOptionPane.showConfirmDialog(this, message,"Xoa duong", JOptionPane.OK_CANCEL_OPTION);
				if (select == 0) 
				{
					this.deleteLine(indexLine);
			    }
			}
		}
		if(command=="change cost") 
		{
			if(indexLine>=0)
			{
				this.changeCost(indexLine);
			}
			
		}
		
	}
	
	public void init()
	{
		if(data.arrPoint.size()>0&&saved==false)
		{
			int select = JOptionPane.showConfirmDialog(this,"Ban co muon luu hinh nay ?"
					,"Xoa", JOptionPane.OK_CANCEL_OPTION);
			//System.out.println(select);
			if(select==0) 
			{
				this.save();
			}
			
			
		}
		data.clear();
		this.path=null;
		drawResult=false;
		type=0;
		this.repaint();
	}
	
	
	private void resetGraph(Graphics2D g2d)
	{
		g2d.setColor(Color.white);
		g2d.fillRect(0, 0, this.getX(), this.getY());
	}
	
	private void reDraw(Graphics2D g2d, boolean c) 
	{
		resetGraph(g2d);
		int i;
		for(i=0;i<data.arrPoint.size();i++)
		{
			data.arrPoint.get(i).draw(g2d, i);
		}
		for(i=0;i<data.arrLine.size();i++)
		{
			Line temp=data.arrLine.get(i);
			MyPoint a=data.arrPoint.get(temp.ind1),b=data.arrPoint.get(temp.ind2); 
			data.arrLine.get(i).drawLine(g2d,a,b,Color.black);
		}
	}
	
	
	public void drawResult(Graphics2D g2d, int[] res)
	{
		int i,ind1,ind2,j;
		for(i=0;i<res.length;i++)
		{
			ind1=res[i];
			ind2=res[(i+1)%res.length];
			for(j=0;j<data.arrLine.size();j++)
			{
				Line l=data.arrLine.get(j);
				if(l.ind1==ind1 && l.ind2==ind2) 
				{
					l.drawLine(g2d,data.arrPoint.get(ind1), data.arrPoint.get(ind2), Color.red);
				}
				
			}
		}
		repaint();
	}
	
	
	public void drawResultStep(Graphics2D g2d)
	{
		int i,ind1,ind2,j;
		for(i=0;i<=this.step;i++)
		{
			ind1=result[i];
			ind2=result[(i+1)%result.length];
			for(j=0;j<data.arrLine.size();j++)
			{
				Line l=data.arrLine.get(j);
				if(l.ind1==ind1 && l.ind2==ind2) 
				{
					l.drawLine(g2d,data.arrPoint.get(ind1), data.arrPoint.get(ind2), Color.BLUE);
				}
				
			}
		}
		repaint();
	}
	
	public void sentResult(int[] res)
	{
	
		if(res==null) 
		{
			JOptionPane.showMessageDialog(null,"Khong ton tai duong di thoa man");
			return ;
		}
		
		this.result=new int[res.length];
		for(int i=0;i<res.length;i++) 
		{
			result[i]=res[i];
		}
		drawResult=true;
		this.repaint();
	}
	
	private int showDialogCost(int indexBegin, int indexEnd) {
		int cost = 0;
		if ( indexEnd< data.arrPoint.size() && indexEnd != indexBegin)
		{
			String c = null;
			while (true)
			{
				c = JOptionPane.showInputDialog(null, "Input Cost from "+ indexBegin + " to " + indexEnd);
				if (c == null)
					break;
				cost = Integer.parseInt(c);
				
				if (cost > 0)
				{
					return cost;
				}
				
			}
		}
		return cost;
	}
	
	private int indexPointContain(MyPoint point)
	{
		int a,b,c=point.x,d=point.y;
		for (int i = 0; i < data.arrPoint.size(); i++) 
		{
			a=data.arrPoint.get(i).x;
			b=data.arrPoint.get(i).y;
			if ((a-c)*(a-c)+(b-d)*(b-d)<=r*r)
			{
				return i;
			}
		}
		return -1;
	}
	
	private int indexLineContain(MyPoint point)
	{
		for (int i = 0; i < data.arrLine.size(); i++) 
		{
			Line l=data.arrLine.get(i);
			if (checkHaveLine(data.arrPoint.get(l.ind1),data.arrPoint.get(l.ind2),point) )
			{
				return i;
			}
		}
		return -1;
	}
	
	private boolean checkHaveLine(MyPoint a, MyPoint b, MyPoint p)
	{	
		double  dx=a.x-b.x,dy=a.y-b.y,
				midx=(a.x+b.x)/2.0,midy=(a.y+b.y)/2.0,
				distanceab=Math.sqrt(dx*dx +dy*dy),
				tr1=Math.abs(dy*(p.x-a.x)-dx*(p.y-a.y)),
				tr2=Math.abs(dx*(p.x-midx)+dy*(p.y-midy));
		
		return tr1/distanceab<r&&tr2/distanceab<distanceab/2.0;
	}
	
	public void setType(int type)
	{
		this.type=type;
	}
	
	public Map convertToMap()
	{
	
		int num=data.arrPoint.size(),i,j;
		double[][] map=new double[num][num];
		for(i=0;i<num;i++)
			for(j=0;j<num;j++) 
				map[i][j]=0;
		for(i=0;i<data.arrLine.size();i++)
		{
			Line l=data.arrLine.get(i);
			int a=l.ind1,b=l.ind2;
			map[a][b]=l.cost;
		}
		return new Map(map);
	}
	
	private void deletePoint(int indexPoint)
	{
		for (int i = 0; i < data.arrLine.size(); i++) 
		{
			int a = data.arrLine.get(i).ind1;
			int b = data.arrLine.get(i).ind2;
	
			if (a == indexPoint || b == indexPoint) 
			{
				data.arrLine.remove(i);
				i--;
			} 
			else 
			{ 
				if (a > indexPoint) {
					data.arrLine.get(i).ind1-=1;
				}
				if (b > indexPoint) {
					data.arrLine.get(i).ind2-=1;
				}
			}
		}
		data.arrPoint.remove(indexPoint);
		saved=false;
		drawResult=false;
		this.repaint();
	}
	
	private void deleteLine(int indexLine) 
	{
		data.arrLine.remove(indexLine);
		saved=false;
		drawResult=false;
		this.repaint();
		
	}
	protected void changeCost(int indexLine) 
	{
		int cost = showDialogCost(data.arrLine.get(indexLine).ind1, data.arrLine.get(indexLine).ind2);
		if (cost > 0&&data.arrLine.get(indexLine).cost!=cost) {
			data.arrLine.get(indexLine).cost=cost;
			saved=false;
			drawResult=false;
		}
		this.repaint();
	}
	
	public void readFile() 
	{

		JFileChooser fc=new JFileChooser();
		fc.setDialogTitle("Doc file");
		int res=fc.showOpenDialog(this);
		if(res==JFileChooser.APPROVE_OPTION)
		{
			File f=fc.getSelectedFile();
			FileInputStream fi;
			try 
			{
				fi = new FileInputStream(f.getPath());
				ObjectInputStream oiStream = new ObjectInputStream(fi);
				Object ob =  oiStream.readObject();
				oiStream.close();
				MyData data=(MyData)ob;
				this.init();
				this.path=f.getPath();
				this.data=data.copy();
				repaint();
				saved=true;
			}  
			catch (IOException e) 
			{
				JOptionPane.showMessageDialog(null,
						"File phai co duoi .tsp", "Loi",
						JOptionPane.ERROR_MESSAGE);
			} catch (ClassNotFoundException e) 
			{
			}
		}
	}	
	
	public void save()
	{
		if(this.path==null)
		{
			JFileChooser fc = new JFileChooser();
			fc.setDialogTitle("Luu hinh");
			int select = fc.showSaveDialog(this);
			if (select == 0)
			{
				this.path = fc.getSelectedFile().getPath();
				if(path.length()<5||!path.substring(path.length()-4, path.length()).equals(".tsp")) 
				{
					path += ".tsp";
				}
			}
		}
				
			try 
			{
				FileOutputStream f = new FileOutputStream(path);
				ObjectOutputStream oStream = new ObjectOutputStream(f);	
				oStream.writeObject(this.data);
				oStream.close();
				JOptionPane.showMessageDialog(null, "Luu thanh cong", "Luu ",
						JOptionPane.INFORMATION_MESSAGE);
				saved=true;
			} 
			catch (IOException e) 
			{
				JOptionPane.showMessageDialog(null, "Loi\nLuu chua thanh cong", "Luu",
						JOptionPane.OK_OPTION);
			}
		
	}
	

	public void setTypeMap(int typeMap)
	{
		this.typeMap=typeMap;
	}
	
	public boolean getDrawResult()
	{
		return this.drawResult;
	}
	
	public void setRunStep(boolean a)
	{
		this.drawStep=a;
	}
		
}
