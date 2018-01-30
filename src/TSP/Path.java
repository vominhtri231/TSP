package TSP;
import java.util.ArrayList;


public class Path 
	{
		
		int[] path;
		
		public Path(int num)
		{
			path=new int[num];
		}
		
		public ArrayList<Path> go(int step,Map map)
		{
			ArrayList<Path> x = new ArrayList<Path>();
			int i,j;
			for(i=0;i<path.length;i++)
			{
				path[step]=i;
				if(check(step,map)) 
				{
					Path cop=new Path(path.length);
					for(j=0;j<path.length;j++) cop.path[j]=this.path[j];
					x.add(0,cop);
				}
			}
			return x;
		}
		
		private boolean check(int step,Map map) 
		{
			int i;
			for(i=0;i<step;i++) if(path[i]==path[step]) return false;
			return map.map[path[step-1]][path[step]]<map.max;
		}
		
		public double len(Map map)
		{
			int i;
			double len=0;
			for(i=0;i<path.length;i++)
			{
				len+=map.map[path[i]][path[(i+1)%path.length]];
			}
			return len;
		}
		
		public void out()
		{
			int i;
			System.out.print("It is :");
			for(i=0;i<path.length;i++)
				System.out.print(path[i]+"  ");
			System.out.println();
		}
		
	}