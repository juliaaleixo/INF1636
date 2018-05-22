package view;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;

import model.Jogo;

public class Janela extends JFrame 
{
	public Tabuleiro tabuleiro;
	
	public Janela(Jogo jogo)
	{
		tabuleiro = new Tabuleiro(jogo);
		
		setSize(400,423);
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle("Xadrez");
		
		getContentPane().add(tabuleiro);

	}
	
}