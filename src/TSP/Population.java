package TSP;

import java.util.Random;



public class Population 
{
	
	Individual[] individuals;
	
	public Population(int populationSize,int size)
	{
		individuals=new Individual[populationSize];
		int i;
		for(i=0;i<populationSize;i++)
		{
			individuals[i]=new Individual(size);
		}
	}
	public Population copy()
	{
		int i;
		Population res=new Population(this.individuals.length,this.individuals[0].gen.length);
		for(i=0;i<this.individuals.length;i++)
		{
			res.individuals[i]=individuals[i].copy();
		}
		return res;
	}
	public void setup()
	{
		int i;
		
		for(i=0;i<individuals.length;i++) 
		{
			individuals[i].setup();
		}
	}
	
	public Individual best(double [][] map)
	{
		Individual res=individuals[0];
		int i;
		double len=res.fitness(map);
		for(i=0;i<individuals.length;i++) 
			if(individuals[i].fitness(map)<len)
			{
				len=individuals[i].fitness(map);
				res=individuals[i].copy();
			}
		return res;
	}
	public Population rand(int populationSize)
	{
		Population res=new Population(populationSize,this.individuals[0].gen.length);
		Random rand=new Random();
		int i,pos;
		for(i=0;i<populationSize;i++)
		{
			pos=rand.nextInt(this.individuals.length);
			res.individuals[i]=this.individuals[pos].copy();
		}
		return res;
	}
	
	public Population evolution(double[][] map)
	{
		Population res=new Population(this.individuals.length,this.individuals[0].gen.length);
		int i;
		
		
		for(i=0;i<res.individuals.length;i++)
		{
			Population fathers=this.rand(5),mothers=this.rand(5);
			Individual father=fathers.best(map),mother=mothers.best(map);
			res.individuals[i]=father.crossover(mother);
	
		}
		for(i=0;i<res.individuals.length;i++)
		{
			res.individuals[i].mutate(0.1);
		}
		return res;
	}
	
	public void out()
	{
		int i;
		for(i=0;i<individuals.length;i++)
		{
			individuals[i].out();
		}
	}
	
	
}
