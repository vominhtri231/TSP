package TSP;

import java.util.*;

public class Individual 
{
	
	int[] gen;
	
	public Individual(int num)
	{
		gen=new int[num];
	}
	
	public void setup()
	{
		Random rand=new Random();
		int i=0,k;
		for(;i<gen.length;) 
		{
			
			gen[i]=rand.nextInt(gen.length);
			for(k=0;k<i&&gen[k]!=gen[i];k++);
			if(k<i) continue;
			i++;
		}
		
	}
	
	public Individual copy()
	{
		Individual res=new Individual(this.gen.length);
		for(int i=0;i<this.gen.length;i++) res.gen[i]=this.gen[i];
		return res;
	}
	
	public double fitness(double [][] map)
	{
		double res=0;
		int i;
		for(i=0;i<gen.length;i++)
		{
			res+=map[gen[i]][gen[(i+1)%gen.length]];
		}
		return res;
	}
	
	public Individual crossover(Individual mother)
	{
		Random rand=new Random();
		int mi,end,start,fi,t,temp;
		start=rand.nextInt(gen.length);
		end=rand.nextInt(gen.length);
		if(end<start)
		{
			temp=start;
			start=end;
			end=temp;
		}	
		
		mi=end+1;
		mi%=gen.length;
		fi=0;

		for(;mi!=start&&fi<gen.length;)
		{
			temp=(fi+end+1)%gen.length;
			for(t=start;t!=mi&& mother.gen[t]!=this.gen[temp];t=(t+1)%gen.length) ;
			if(t==mi)
			{
				mother.gen[mi]=this.gen[temp];
				mi++;
				mi%=gen.length;
			}
			fi++;
		}
		return mother;
	}
	
	public void mutate(double rate)
	{
		Random rand=new Random();
		if(rand.nextDouble()<rate)
		{
			int i,j,t;
			i=rand.nextInt(gen.length);
			j=rand.nextInt(gen.length);
			t=gen[i];
			gen[i]=gen[j];
			gen[j]=t;
		}
	}
	
	public void out()
	{
		int i;
		System.out.print("It is :");
		for(i=0;i<gen.length;i++)
			System.out.print(gen[i]+"  ");
		System.out.println();
	}
	
}
