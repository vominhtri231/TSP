package TSP;

public class Generic
{
	Population population;
	Map map;
	int populationSize,gener;
	public Generic(Map map,int populationSize,int gener)
	{
		
		this.map=map;
		population=new Population(populationSize,map.num);
		population.setup();
		this.populationSize=populationSize;
		this.gener=gener;
		
	}
	public int[] solve()
	{
		int i;
		Population temp;
		for(i=0;i<gener;i++) 
		{
			
			temp=population.evolution(map.map);
			population=temp.copy();
		}
		Individual res=population.best(map.map);
		if(res.fitness(map.map)<=map.max) return res.gen;
		return null;
	}
}
