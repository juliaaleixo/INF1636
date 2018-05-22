package controller;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import model.Jogo;
import model.MovIlegalExcecao;
import view.Janela;

public class TabuleiroController implements MouseListener
{
	int clickX, clickY;
	
	public TabuleiroController()
	{
		Jogo jogo = new Jogo();
		Janela janela = new Janela(jogo);
		janela.setVisible(true);	
		
		janela.addMouseListener(this);
		
		try 
		{
			jogo.tabuleiro[2][1].movimento(2, 1, 2, 3, jogo.tabuleiro);
		} 
		catch (MovIlegalExcecao e)
		{
			return;
		}
		
		janela.tabuleiro.repaint();
	}
	
	// Pega uma coordenada da janela X e a transforma numa posicao X da matriz de pe�as
	public int getTileX (int x) {
		return (x-3)/50;
	}
	
	// Pega uma coordenada da janela Y e a transforma numa posicao Y da matriz de pe�as
	public int getTileY (int y) {
		return 7 - ((y-26)/50);
	}
	
	public void mousePressed(MouseEvent e)
	{
		clickX = e.getX();
	    clickY = e.getY();
	    
	    // debug purposes
	    String str = "Mouse Pressed - x: " + clickX + " y: " + clickY;
	    System.out.println(str);
	    System.out.println(String.valueOf(getTileX(clickX)) + " " + String.valueOf(getTileY(clickY)));
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
//		    clickX = e.getX();
//		    clickY = e.getY();
//		    String str = "Mouse Entered";
//		    System.out.println(str);
	    
	  }
	  
	  public void mouseExited(MouseEvent e)
	  {
//		    clickX = e.getX();
//		    clickY = e.getY();
//		    String str = "Mouse Exited";
//		    System.out.println(str);
	  }
	
}
