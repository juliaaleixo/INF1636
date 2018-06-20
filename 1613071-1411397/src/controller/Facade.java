package controller;

import javax.swing.JOptionPane;

import model.Bispo;
import model.Cavalo;
import model.Jogo;
import model.Peca;
import model.Rainha;
import model.Torre;

public class Facade 
{
	private static Facade instance = null;
	private TabuleiroController c;
	
	private Facade () 
	{
	}
	
	public static Facade getInstance() 
	{
		if ( instance == null ) 
		{
			instance = new Facade();
		}
		return instance;
	}
	
	public void iniciaJogo()
	{
		c = new TabuleiroController();
	}
	
	public void promocao(int x, int y, Jogo jogo) 
	{
		Object[] possibilities = { "Rainha", "Torre", "Bispo", "Cavalo" };
		
		String s = (String) JOptionPane.showInputDialog (null,
				"Escolha a nova peca:\n", "Promocao do Peao",
				JOptionPane.PLAIN_MESSAGE, null, possibilities, "Rainha");

		if ( (s != null) && (s.length() > 0) ) 
		{
			Peca p = jogo.tabuleiro[x][y];

			if (s.equals("Cavalo")) 
			{
				jogo.tabuleiro[x][y] = new Cavalo(jogo.rodadaAtual);
			} 
			else if (s.equals("Torre")) 
			{
				jogo.tabuleiro[x][y] = new Torre(jogo.rodadaAtual);
			} 
			else if (s.equals("Bispo"))
			{
				jogo.tabuleiro[x][y] = new Bispo(jogo.rodadaAtual);
			} 
			else 
			{
				jogo.tabuleiro[x][y] = new Rainha(jogo.rodadaAtual);
			}
			
			jogo.tabuleiro[x][y].addObserver(c);
			jogo.tabuleiro[x][y].addObserver(c.janela.tabuleiro);
			jogo.tabuleiro[x][y].movRealizado();
			
			return;
		}
		promocao(x, y,jogo);
	}
	public void alertaXeque()
	{
		JOptionPane.showMessageDialog(null, "Xeque");
	}
	public void alertaXequeMate()
	{
		JOptionPane.showMessageDialog(null, "Xeque Mate");
	}
}
