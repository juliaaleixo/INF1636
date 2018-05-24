package view;

import javax.swing.JFrame;

import model.Jogo;

public class Janela extends JFrame 
{
	public Tabuleiro tabuleiro;
	
	public Janela(Jogo jogo)
	{
		tabuleiro = new Tabuleiro(jogo);
		
		setSize(400,422);
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle("Xadrez");
		
		getContentPane().add(tabuleiro);

	}
	
}