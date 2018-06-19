package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import controller.TabuleiroController;
import dao.SalvamentoDao;
import model.Jogo;

public class Janela extends JFrame implements ActionListener
{
	public Tabuleiro tabuleiro;
	JMenuItem novoJogo, salvar, carregar;
	private TabuleiroController controller;
	
	public Janela(Jogo jogo, TabuleiroController controller)
	{
		this.controller = controller;
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
		
		novoJogo.addActionListener(this);
		salvar.addActionListener(this);
		carregar.addActionListener(this);
		
		JMenuItem[] itensArquivo = {novoJogo, salvar, carregar};
		adicionaMenus(arquivoMenu, itensArquivo);
		
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle("Xadrez");
		
		getContentPane().add(tabuleiro);
		
		this.setVisible(true);

	}
	
	public void adicionaMenus (JMenu menu, JMenuItem[] menuItems) {
		
		for(JMenuItem item : menuItems) {
			menu.add(item);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		SalvamentoDao salvamento = SalvamentoDao.getInstance();
		if (e.getSource() == salvar) {
			System.out.println("Salvando jogo atual...");
			salvamento.salvarJogo(tabuleiro.jogo, this);
		} else if (e.getSource() == carregar){
			System.out.println("Carregando jogo salvo...");
			Jogo jogo = (Jogo) salvamento.carregarJogo(this);
			
			if (jogo != null) {
				
				tabuleiro.jogo = jogo;
				controller.jogo = jogo;
				tabuleiro.repaint();
			}
		} else if (e.getSource() == novoJogo) {
			
			if(tabuleiro.jogo.movimentado) {
				int result = JOptionPane.showConfirmDialog(this, "Tem certeza que deseja começar um novo jogo?\nO progresso atual será perdido.", "Novo jogo", JOptionPane.YES_NO_OPTION);
			
				if(result == 0) {
					controller.resetTabuleiro();
					tabuleiro.jogo = new Jogo();
					tabuleiro.repaint();
				}
			}
		
		}
	}
}