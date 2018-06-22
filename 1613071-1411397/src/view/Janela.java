package view;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import model.Jogo;

public class Janela extends JFrame
{
	public Tabuleiro tabuleiro;
	public JMenuItem novoJogo, salvar, carregar;
	
	/**
	 * Inicializa a janela do jogo
	 * @param jogo: instancia do jogo
	 */
	public Janela(Jogo jogo)
	{
		tabuleiro = new Tabuleiro(jogo);
		
		setSize(405,450);
		
		setLocationRelativeTo(null);
		
		JMenuBar barraMenu = new JMenuBar();
		setJMenuBar(barraMenu);
		
		JMenu arquivoMenu = new JMenu("Arquivo");
		barraMenu.add(arquivoMenu);
		
		novoJogo = new JMenuItem("Novo jogo");
		salvar = new JMenuItem("Salvar partida");
		carregar = new JMenuItem("Carregar partida");
		
		JMenuItem[] itensArquivo = {novoJogo, salvar, carregar};
		adicionaMenus(arquivoMenu, itensArquivo);
		
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle("Xadrez");
		
		getContentPane().add(tabuleiro);
		
		this.setVisible(true);

	}
	
	/**
	 * 
	 * @param menu
	 * @param menuItems
	 */
	public void adicionaMenus (JMenu menu, JMenuItem[] menuItems) {
		
		for(JMenuItem item : menuItems) {
			menu.add(item);
		}
	}
}