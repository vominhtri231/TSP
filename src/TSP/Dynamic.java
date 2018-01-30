package TSP;

import java.util.ArrayList;
import TSP.Map;



public class Dynamic 
{
	
	Map map;
	ArrayList<Path> paths;
	
	public Dynamic(Map map)
	{
		this.map=map;
		paths=new ArrayList<Path>();
		set();
	}
	
	public int[] solve()
	{
		int step;
		for(step=1;step<map.num;step++)
		{
			go(step);
		}
		Path res=this.select();
		return res.path;
	}
	
	
	private void set()
	{
		Path x=new Path(map.num);
		x.path[0]=0;
		paths.add(x);
	}
	
	private void go(int step)
	{
		Path a;
		int i,j,size=paths.size();
		for(i=0;i<size;i++)
		{
			a=paths.remove(0);
			ArrayList<Path> newpaths=a.go(step,map);
			for(j=0;j<newpaths.size();j++) paths.add(newpaths.get(j));
		}
	}
	
	private Path select()
	{
		int i;
		Path best = null,x;
		for(i=0;i<paths.size();i++) 
		{
			x=paths.get(i);
			if(map.map[x.path[map.num-1]][x.path[0]]<map.max&&(best==null||x.len(map)<best.len(map))) best=x;
		}
		return best;
	}
	
	
	
	
}

