package TSP;


import java.util.ArrayList;

import TSP.Map;



public class Dynamic2 
{
	
	Map map;
	double[][] paths;
	//path[i][j] độ dài đường đi từ 0
	//,qua tất cả các điểm trong tập hợp i,kết thúc ở j
	int[] sets;
	//các tập hợp dưới dạng nhị phân của các điểm từ 1->n-1 => 0->n-2
	//vd 34=10010=>(2,5)
	int[][] mem;
	
	public Dynamic2(Map map)
	{
		this.map=map;
	}
	
	public int[] solve()
	{
		paths=new double [map.num-1][1<<(map.num-1)];
		mem=new int[map.num-1][1<<(map.num-1)];

		init();
		
		for(int i=1;i<map.num-1;i++)
		{
			add_node_tosets();
			add_paths();
		}
		
		return select();
		
	}
	

	private int[] select()
	{
		int s=(1<<(map.num-1))-1;
		double value=map.max;
		int mem=-1;
			
		for(int k=0;k<map.num-1;k++)
		{
			int sub=s-(1<<k);
			double tempval=map.map[k+1][0] 
					+ paths[k][sub];
			if(tempval<value)
			{
				value=tempval;
				mem=k;
			}
		}
		
		if(value>map.max)  return null;
		
		int[] res=new int[map.num];
		res[map.num-1]=mem+1;res[0]=0;
		
		int pos=map.num-2;
		while(pos>0)
		{
			s-=1<<mem;
			mem=this.mem[mem][s];
			res[pos]=mem+1;
			pos--;
		}
		return res;
	}

	private void add_paths() 
	{
		for(int i=0;i<sets.length;i++)
		{
			long temp=sets[i];
			boolean[] bits=new boolean[map.num-1];
			int pos=0;
			
			while(temp>0)
			{
				if(temp%2>0) bits[pos]=true;
				pos++;
				temp/=2;
			}
			
			
			for(int j=0;j<map.num-1;j++)
			{
				if(!bits[j])
				{
					//paths[j][sets[i]]=?
					double value=map.max;
					mem[j][ sets[i]]=-1;
					
					
					for(int k=0;k<map.num-1;k++)
					{
						if(bits[k])
						{
							// k is before j
							
							int sub=sets[i]-(1<<k);
							double tempval=map.map[k+1][j+1] + paths[k][sub];
							if(tempval<value)
							{
								value=tempval;
								mem[j][ sets[i]]=k;
							}
						}
					}
					
					paths[j][ sets[i]]=value;
				}
			}
				
			
			
			
		}
		
	}


	private void add_node_tosets() 
	{
		// chỉ thêm vào các điểm phía sau bit 1 cuối cùng để tránh trùng lặp
		ArrayList<Integer> temp=new ArrayList<Integer>();
		
		
		for(int i=0;i<sets.length;i++)
		{
			int t=sets[i];
			for(int j=map.num-2;(1<<j)>t&&j>=0;j--)
			{
				temp.add(t+(1<<j));
			}
		}
		
		sets=new int[temp.size()];
		for(int i=0;i<temp.size();i++)
		{
			sets[i]=temp.get(i);
		}		
	}

	private void init()
	{
		sets=new int[1];
		sets[0]=0;
		
		for(int i=1;i<map.num;i++) 
		{
			paths[i-1][0]=map.map[0][i];
		}
		
		
	}
	
	
	
	
	
	
}

