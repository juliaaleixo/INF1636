package controller;

import java.util.ArrayList;
import java.util.Observer;

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
	
	/**
	 * Faz a promocao do peao
	 * @param x: coordenada x do peao a ser promovido no tabuleiro
	 * @param y: coordenada y do peao a ser promovido do tabuleiro
	 * @param jogo: instancia do jogo
	 */
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
			
			configuraObserverPeca(x, y, jogo, c);
			jogo.tabuleiro[x][y].movimentoRealizado();
			
			return;
		}
		promocao(x, y,jogo);
	}
	public void alertaEmpate()
	{
		int input = JOptionPane.showConfirmDialog(null, "Deseja comecar novo jogo?", "Empate", JOptionPane.YES_NO_OPTION);
		// opcao: nao iniciar novo jogo
		if (input == 1) 
		{
      		c.janela.dispose();
       		c.janela.setVisible(false);
		}
		//iniciar novo jogo	
		else
		{
			c.novoJogo();
		}
	}
	public void alertaXeque()
	{
		JOptionPane.showMessageDialog(null, "Xeque");
	}
	public void alertaXequeMate()
	{
		int input = JOptionPane.showConfirmDialog(null, "Deseja comecar novo jogo?", "Xeque Mate", JOptionPane.YES_NO_OPTION);
		
		// opcao: nao iniciar novo jogo
		if (input == 1) 
		{
      		c.janela.dispose();
       		c.janela.setVisible(false);
		}
		//iniciar novo jogo	
		else
		{
			c.novoJogo();
		}
	}
	
	/**
	 * Configura observer para cada peca
	 * @param x: coordenada x da peca
	 * @param y: coordenada y da peca
	 * @param jogo: instancia do jogo 
	 * @param controlador: controlador que observa a peca na posicao (x,y) do tabuleiro
	 */
	public void configuraObserverPeca(int x, int y, Jogo jogo, TabuleiroController controlador)
	{
		jogo.tabuleiro[x][y].addObserver(controlador);
		jogo.tabuleiro[x][y].addObserver(controlador.janela.tabuleiro);
	}
	
	/**
	 * Configura um observador no jogo
	 * @param jogo: instancia do jogo
	 * @param controlador: instancia do controlador
	 */
	public void configuraObserverJogo(Jogo jogo, TabuleiroController controlador)
	{
		jogo.addObserver(controlador.janela.tabuleiro);
	}
	
}
