package GUI;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import TSP.TSP;

import java.awt.*;
import java.awt.event.*;

@SuppressWarnings("serial")
public  class Setup extends JFrame implements ActionListener
{
	private JButton fileBut,pointBut,newBut,runBut,runStep,lineBut,moveBut,saveBut;
	JRadioButton dir,undir,real;
	private JPanel selectPanel;
	private Draw draw;
	private int type=1;
	private TSP tsp;
	private JComboBox<String> option;
	public Setup()
	{		
		tsp=new TSP();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new BorderLayout(5, 5));
		this.setTitle("TSP");
	
		JPanel temp=new JPanel();
		temp.add(creatOpion());
		this.add(creatDraw(),BorderLayout.CENTER);
		this.add(temp,BorderLayout.WEST);		
		setSize(650, 300);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}
	
	private JButton createButton(String lable) 
	{
		JButton but = new JButton(lable);
		but.addActionListener(this);
		return but;
	}
	
	private JPanel creatOpion() 
	{
		
		String[] list={"Vet can","Qui hoach dong","Tien hoa"};
		option=new JComboBox<String>(list);
		option.addActionListener(this);
		option.setEditable(false);
		option.setMaximumRowCount(3);
		
		dir=new JRadioButton("co huong");
		undir=new JRadioButton("vo huong");
		real=new JRadioButton("thuc");
		ButtonGroup group =new ButtonGroup();
		dir.addActionListener(this);
		undir.addActionListener(this);
		real.addActionListener(this);
		group.add(dir);
		group.add(undir);
		group.add(real);
		
		
		JPanel  panel= new JPanel( new GridLayout(3,1,5,5));
		JPanel	panel1=new JPanel(new GridLayout(3, 1));
		panel1.setBorder(new EmptyBorder(0, 15, 8, 5));
		JPanel	panel2=new JPanel(new GridLayout(1, 1));
		panel2.setBorder(new EmptyBorder(0, 15, 8, 5));
		JPanel	panel3=new JPanel(new GridLayout(1, 1));
		panel3.setBorder(new EmptyBorder(0, 15, 8, 5));
		
		panel1.add(dir,true);
		panel1.add(undir,false);
		panel1.add(real,false);
		
		panel2.add(option);
		
		panel3.add(runBut=createButton("run"));
		panel3.add(runStep= createButton("runStep"));
		JPanel vector= new JPanel(new BorderLayout());
		vector.setBorder(new TitledBorder("Vector"));
		vector.add(panel1);
		
		JPanel method= new JPanel(new BorderLayout());
		method.setBorder(new TitledBorder("Thuat toan"));
		method.add(panel2);
		
		JPanel run= new JPanel(new BorderLayout());
		run.setBorder(new TitledBorder("Run"));
		run.add(panel3);
		
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.add(vector);
		panel.add(method);
		panel.add(run);
		
		panel.setSize(new Dimension(200,300));
		
		return panel;
	}
	
	

	private JPanel creatDraw() 
	{
		selectPanel=new JPanel();
		selectPanel.setLayout(new BoxLayout(selectPanel, BoxLayout.Y_AXIS));
		selectPanel.add(fileBut= createButton("nhap file"));
		selectPanel.add(newBut = createButton(" tao moi "));
		selectPanel.add(pointBut=createButton(" tao diem"));
		selectPanel.add(lineBut= createButton(" ke duong"));
		selectPanel.add(moveBut= createButton("di chuyen"));
		selectPanel.add(saveBut= createButton(" luu hinh"));
		
		draw=new Draw();
		
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.add(selectPanel, BorderLayout.WEST);
		panel.add(draw, BorderLayout.CENTER);
		return panel;
	}


	@Override
	public void actionPerformed(ActionEvent e) 
	{
	
		if(e.getSource()==pointBut)
		{
			draw.setType(1);
		}
		if(e.getSource()==lineBut)
		{
			draw.setType(2);
		}
		if(e.getSource()==moveBut)
		{
			draw.setType(3);
		}
		if(e.getSource()==newBut)
		{
				draw.init();
		}
		if(e.getSource()==fileBut)
		{
			draw.readFile();
		}
		if(e.getSource()==saveBut)
		{
			draw.save();
		}
		if(e.getSource()==option)
		{
			String var=(String)option.getSelectedItem();
			if(var.equals("Vet can"))
			{
				type=1;
			}
			if(var.equals("Qui hoach dong"))
			{
				type=2;
			}
			if(var.equals("Tien hoa"))
			{
				type=3;
			}
		}
		if(e.getSource()==dir)
		{
			draw.setTypeMap(0);
		}
		if(e.getSource()==undir)
		{
			draw.setTypeMap(1);
		}
		if(e.getSource()==real)
		{
			draw.setTypeMap(2);
		}
		if(e.getSource()==runBut)
		{
			
			JFrame wait=new JFrame();
			JPanel panel=new JPanel(new BorderLayout());
			panel.add(new JLabel("      xin cho ... "),BorderLayout.CENTER);
			wait.getContentPane().add(panel);
			wait.setSize(30, 100);
			wait.setLocationRelativeTo(null);
			
			wait.setVisible(true);
			
			int [] res;
			tsp.setType(type);
			res=tsp.solve(draw.convertToMap());
			draw.sentResult(res);
			
			wait.dispose();
			draw.setRunStep(false);

		}
		if(e.getSource()==runStep)
		{
			if(!draw.getDrawResult()) 
			{
				JFrame wait=new JFrame();
				JPanel panel=new JPanel(new BorderLayout());
				panel.add(new JLabel("      xin cho ... "),BorderLayout.CENTER);
				wait.getContentPane().add(panel);
				wait.setSize(30, 100);
				wait.setLocationRelativeTo(null);
				
				wait.setVisible(true);
				
				int [] res;
				tsp.setType(type);
				res=tsp.solve(draw.convertToMap());
				draw.sentResult(res);
				draw.step=0;
				
				wait.dispose();
				
			}
			else
			{
			
				draw.step+=1;
				draw.step%=draw.result.length;
				
			}
			draw.setRunStep(true);
		}
	}
}
