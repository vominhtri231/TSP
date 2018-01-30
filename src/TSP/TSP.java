package TSP;


public class TSP 
{ 	
	int type;
	FindAll findAll;
	Dynamic2 dynamic;
	Generic generic;
	
	public TSP()
	{
	}
	public void setType(int type)
	{
		this.type=type;
	}
	public int[] solve(Map map)
	{
		if(type==1) 
		{
			findAll=new FindAll(map);
			return findAll.solve();
		}
		
		if(type==2) 
		{
			dynamic=new Dynamic2(map);
			return dynamic.solve();
		}
		
		if(type==3)
		{	
			generic=new Generic(map,100,20);
			return generic.solve();
		}
		return null;
	}
}
