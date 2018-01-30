package TSP;
import TSP.Map;
public class FindAll 
{
	Map map;
	int[] res,fin;
	int first=0;
	double min=0;
	double dis=0;
	public FindAll(Map map)
	{
		this.map=map;
		res=new int[map.num];res[0]=0;
		fin=new int[map.num];fin[0]=0;
		
	}
	public int[] solve()
	{
		this.setNode(1);
		if( first!=0) return  fin;
		return null;
	}
	
	private void setNode(int pos)
	{ 
		int i;
		if(pos==map.num)
		{
			if( (first<1||(dis+map.map[res[map.num-1]][res[0]])<min) && map.map[res[map.num-1]][res[0]]<map.max) 
			{
				min=dis+map.map[res[map.num-1]][res[0]];
				first=1;
				for(i=1;i<map.num;i++) fin[i]=res[i];
			}
		}
		else
		{
			for(i=0;i<map.num;i++)
			{
				res[pos]=i;
				dis+=map.map[res[pos-1]][res[pos]];
				if(map.map[res[pos-1]][res[pos]]<map.max&&check(res,pos)&&(first<1||dis<min)) setNode(pos+1);
				dis-=map.map[res[pos-1]][i];
			} 
		}
			
	}
	
	private boolean check(int[] res,int k)
	{
		int i;
		for(i=0;i<k;i++) if(res[i]==res[k]) return false;
		return true;
	}
	// c(n)=? , c(n) la do phuc tap khi con n diem chua vao res[]
	// c(x)=x*c(x-1)+n-x
	// c(0)=n
	//=>c(n)=n*((n-1)*...+n)+..+1)+0
	//=>0(n!*n)
	//
	
}
