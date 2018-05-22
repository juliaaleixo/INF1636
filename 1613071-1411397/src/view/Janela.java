package view;

import javax.swing.JFrame;

public class Janela extends JFrame
{
	public Janela()
	{
		Tabuleiro tabuleiro = new Tabuleiro();
		
		setSize(400,423);
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle("Xadrez");
		
		getContentPane().add(tabuleiro);
	}
	public static void main(String []args)
	{
		Janela j = new Janela();
		j.setVisible(true);
	}
}