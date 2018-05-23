package controller;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import model.Jogo;
import model.MovIlegalExcecao;
import view.Janela;

public class TabuleiroController implements MouseListener
{
	int xOrig, xDest, yOrig, yDest; 
	boolean pecaSelecionada; //indica se a peca ja foi selecionada (para ver se é peca de origem ou destino)
	Jogo jogo;
	Janela janela;
	
	public TabuleiroController()
	{
		jogo = new Jogo();
		janela = new Janela(jogo);
		janela.setVisible(true);	
		
		janela.addMouseListener(this);
		
	}
	
	private void movimentaPeca()
	{
		try 
		{
			jogo.tabuleiro[xOrig][yOrig].movimento(xOrig, yOrig, xDest, yDest, jogo.tabuleiro);
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
		
		// debug purposes
	    String str = "Mouse Pressed - x: " + e.getX() + " y: " + e.getY();
	    System.out.println(str);
	    System.out.println(String.valueOf(getTileX(e.getX())) + " " + String.valueOf(getTileY(e.getY())));
	   
		if ( pecaSelecionada == false )
		{
			xOrig = getTileX(e.getX());
			yOrig = getTileY(e.getY());
			
			if ( jogo.tabuleiro[xOrig][yOrig] != null )
			{
				pecaSelecionada = true;
			}
		}
		else
		{
			xDest = getTileX(e.getX());
			yDest = getTileY(e.getY());
			movimentaPeca();
			
			pecaSelecionada = false;
		}
		
	  }
	
	  public void mouseReleased(MouseEvent e)
	  {
//		clickX = e.getX();
//		clickY = e.getY();
//		String str = "Mouse Released";
//		System.out.println(str);

	  }
	  
	  public void mouseClicked(MouseEvent e)
	  {
//	    clickX = e.getX();
//	    clickY = e.getY();
//	    String str = "Mouse Clicked";
//	    System.out.println(str);
	  }
	  
	  public void mouseEntered(MouseEvent e)
	  {
	  }
	  
	  public void mouseExited(MouseEvent e)
	  {
	  }
	
}
