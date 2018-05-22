package view;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;

public class Janela extends JFrame implements MouseListener
{
	int clickX, clickY;
	
	public Janela()
	{
		Tabuleiro tabuleiro = new Tabuleiro();
		
		setSize(400,423);
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle("Xadrez");
		
		getContentPane().add(tabuleiro);
		addMouseListener(this);

	}
	public static void main(String []args)
	{
		Janela j = new Janela();
		j.setVisible(true);
		System.out.println("Printando da main");
		

	}
	
	public void mousePressed(MouseEvent e)
	{
		clickX = e.getX();
	    clickY = e.getY();
	    String str = "Mouse Pressed - x: " + clickX + " y: " + clickY;
	    System.out.println(str);
	  }
	  public void mouseReleased(MouseEvent e)
	  {
		clickX = e.getX();
		clickY = e.getY();
		String str = "Mouse Released";
		System.out.println(str);

	  }
	  public void mouseClicked(MouseEvent e)
	  {
	    clickX = e.getX();
	    clickY = e.getY();
	    String str = "Mouse Clicked";
	    System.out.println(str);

	  }
	  public void mouseEntered(MouseEvent e)
	  {
//		
//	    clickX = e.getX();
//	    clickY = e.getY();
//	    String str = "Mouse Entered";
//	    System.out.println(str);
	    
	  }
	  public void mouseExited(MouseEvent e)
	  {
//	    clickX = e.getX();
//	    clickY = e.getY();
//	    String str = "Mouse Exited";
//	    System.out.println(str);
	  }
}