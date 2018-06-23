package view;

import java.awt.Component;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;

public class TelaInicial extends JFrame
{
	public JButton novoJogo;
	public JButton carregarJogo;
	
	public TelaInicial()
	{
		setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		setSize(200,200);
		setLocationRelativeTo(null);
		
		novoJogo = new JButton("Novo Jogo");
		carregarJogo = new JButton("Carregar jogo");
		
		novoJogo.setAlignmentX(Component.CENTER_ALIGNMENT);
		carregarJogo.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		getContentPane().add(novoJogo);
		getContentPane().add(carregarJogo);
		
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle("Xadrez");
		
		this.setVisible(true);
	}
}
