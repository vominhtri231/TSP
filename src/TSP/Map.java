package TSP;
import java.io.*;

public class Map {
	double [][] map;
	int num;
	double max;
	int optionNum=0;
	public Map(File f) throws IOException
	{
		int i=0,k,l,j;
		String s;
		double x;
		FileReader freader = new FileReader(f);
		BufferedReader reader=new BufferedReader(freader);
		
		s=reader.readLine();
		num=Integer.parseInt(s);
		map=new double[num][num];
		max=0;
		for(;i<num;i++)
		{
			l=-1;k=-1;
			s=reader.readLine();
			for(j=0;j<num;j++)
			{
				for(k = l+1; k <s.length()&&s.charAt( k )==' ';k++);
				for(l = k; l <s.length()&&s.charAt( l )!=' ';l++);
				x=Double.parseDouble(s.substring(k,l));
				map[i][j]=x;
				max+=x;
			}
		}
		for(i=0;i<num;i++)
		{
			for(j=0;j<num;j++)
				if(map[i][j]==0) 
					map[i][j]=max;
		}
		freader.close();
	}
	public Map(double[][] map) {
		int i,j;
		double max=0;
		this.num=map.length;
		this.map=new double[num][num];
		for(i=0;i<num;i++)
			for(j=0;j<num;j++)
			{
				this.map[i][j]=map[i][j];
				max+=map[i][j];
			}
		this.max=max;
		for(i=0;i<num;i++)
			for(j=0;j<num;j++)
				if(this.map[i][j]==0) 
					this.map[i][j]=max;
	}
	public void out()
	{
		System.out.println();
		int i,j;
		for(i=0;i<num;i++)
		{
			for(j=0;j<num;j++)
			{
				System.out.print(map[i][j]+  "   ");
			}
			System.out.println();
		}
	}

}
