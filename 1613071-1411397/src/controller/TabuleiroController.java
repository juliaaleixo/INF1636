package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JOptionPane;

import dao.SalvamentoDao;
import model.Bispo;
import model.Cavalo;
import model.Cor;
import model.Jogo;
import model.MovIlegalExcecao;

import model.Peca;
import model.Rainha;
import model.Rei;
import model.Torre;
import view.Janela;

public class TabuleiroController implements MouseListener, Observer, ActionListener 
{
	int xOrig, xDest, yOrig, yDest;
	boolean pecaSelecionada; // indica se a peca ja foi selecionada (para ver se é peca de origem ou destino)
	public Jogo jogo;
	Janela janela;

	public TabuleiroController() 
	{
		jogo = new Jogo();
		janela = new Janela(jogo);

		for (int i = 0; i < 8; i++) 
		{
			for (int j = 0; j < 8; j++) 
			{
				if (jogo.tabuleiro[i][j] != null)
				{
					Facade.getInstance().configuraObserver(i, j, jogo, this);
				}
			}
		}

		janela.setVisible(true);

		janela.addMouseListener(this);

		janela.novoJogo.addActionListener(this);
		janela.salvar.addActionListener(this);
		janela.carregar.addActionListener(this);
	}

	public void resetTabuleiro() {
		jogo = new Jogo();
		jogo.rodadaAtual = Cor.branco;
		pecaSelecionada = false;

		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (jogo.tabuleiro[i][j] != null) {
					Facade.getInstance().configuraObserver(i, j, jogo, this);
				}
			}
		}

	}

	private void movimentaPeca() 
	{
		janela.tabuleiro.jogo = jogo;
		
		if (!jogo.movimentado) 
		{
			jogo.movimentado = true;
		}
		try 
		{
			jogo.tabuleiro[xOrig][yOrig].movimento(xOrig, yOrig, xDest, yDest, jogo.tabuleiro);

			if (jogo.rodadaAtual == Cor.branco)
			{
				jogo.rodadaAtual = Cor.preto;
			} 
			else 
			{
				jogo.rodadaAtual = Cor.branco;
			}

			Boolean[][] reisEmXeque = reisEmXeque(jogo.tabuleiro);
			for (int i = 0; i < 8; i++)
			{
				for (int j = 0; j < 8; j++) 
				{
					if ( reisEmXeque[i][j] == true ) 
					{
						Peca rei = jogo.tabuleiro[i][j];
						
						if ( ((Rei)rei).xequeMate(i,j,jogo.tabuleiro,rei.getCor()) == true )
						{
							Facade.getInstance().alertaXequeMate();
						}
						else
						{
							Facade.getInstance().alertaXeque();
						}
					}
				}
			}
		} 
		catch (MovIlegalExcecao e) 
		{
			return;
		}
	}

	// Pega uma coordenada da janela X e a transforma numa posicao X da matriz
	// de pe�as
	public int getTileX(int x) {
		return (x - 3) / 50;
	}

	// Pega uma coordenada da janela Y e a transforma numa posicao Y da matriz
	// de pe�as
	public int getTileY(int y) {
		return 7 - ((y - 48) / 50);
	}

	public void mousePressed(MouseEvent e)
	{
		System.out.println("X: " + e.getX());
		System.out.println("X: " + e.getY());

		if (pecaSelecionada == false) 
		{
			xOrig = getTileX(e.getX());
			yOrig = getTileY(e.getY());

			if (jogo.tabuleiro[xOrig][yOrig] != null)
			{
				if (jogo.tabuleiro[xOrig][yOrig].getCor() != jogo.rodadaAtual) 
				{
					System.out.println("A jogada e dxs " + jogo.rodadaAtual.toString());
					return;
				}
				pecaSelecionada = true;

				Boolean[][] posicoes = posicoesPossiveis(jogo.tabuleiro, xOrig, yOrig);
				janela.tabuleiro.setPosicoesPossiveis(posicoes);

			}
		} 
		else 
		{
			int x = getTileX(e.getX());
			int y = getTileY(e.getY());

			if ( jogo.tabuleiro[x][y] != null )
			{
				if ( jogo.tabuleiro[x][y].getCor() == jogo.tabuleiro[xOrig][yOrig].getCor() )
				{
					xOrig = x;
					yOrig = y;

					Boolean[][] posicoes = posicoesPossiveis(jogo.tabuleiro, xOrig, yOrig);
					janela.tabuleiro.setPosicoesPossiveis(posicoes);
				} 
				else 
				{
					xDest = x;
					yDest = y;

					if ( !jogo.reiDesprotegido(xOrig, yOrig, xDest, yDest) )
					{
						janela.tabuleiro.setPosicoesPossiveis(null);
						movimentaPeca();

						pecaSelecionada = false;
					}
				}

			} 
			else
			{
				xDest = x;
				yDest = y;

				if ( !jogo.reiDesprotegido(xOrig, yOrig, xDest, yDest) ) 
				{
					janela.tabuleiro.setPosicoesPossiveis(null);
					movimentaPeca();

					pecaSelecionada = false;
				}
			}

		}

	}

	public void mouseReleased(MouseEvent e) {}
	public void mouseClicked(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}

	public Boolean[][] posicoesPossiveis(Peca[][] tabuleiro, int xOrig, int yOrig) 
	{
		Boolean posicoesPossiveis[][] = new Boolean[8][8];

		Peca p = tabuleiro[xOrig][yOrig];
		Cor cor = p.getCor();

		for (int i = 0; i < 8; i++) 
		{
			for (int j = 0; j < 8; j++) 
			{

				if (p.caminhoLivre(xOrig, yOrig, i, j, tabuleiro) && !jogo.reiDesprotegido(xOrig, yOrig, i, j)) 
				{
					posicoesPossiveis[i][j] = true;
				} 
				else 
				{
					posicoesPossiveis[i][j] = false;
				}
			}
		}
		return posicoesPossiveis;
	}

	public Boolean[][] reisEmXeque(Peca[][] tabuleiro) 
	{
		Boolean reisEmXeque[][] = new Boolean[8][8];

		for (int i = 0; i < 8; i++) 
		{
			for (int j = 0; j < 8; j++) 
			{
				if (tabuleiro[i][j] != null) 
				{
					Peca p = tabuleiro[i][j];

					if (p instanceof Rei) 
					{
						if (((Rei) p).xeque(i, j, tabuleiro, p.getCor()))
						{
							reisEmXeque[i][j] = true;
							continue;
						}
					}
				}
				reisEmXeque[i][j] = false;
			}
		}
		return reisEmXeque;
	}

	public void update(Observable o, Object arg) 
	{
		if (((ArrayList<Object>) arg).get(0).equals("promocao")) 
		{
			int x = (int) ((ArrayList<Object>) arg).get(1);
			int y = (int) ((ArrayList<Object>) arg).get(2);

			Facade.getInstance().promocao(x, y,jogo);
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		SalvamentoDao salvamento = SalvamentoDao.getInstance();
		if (e.getSource() == janela.salvar) {
			System.out.println("Salvando jogo atual...");
			salvamento.salvarJogo(jogo, janela);
			
		} else if (e.getSource() == janela.carregar) {
			System.out.println("Carregando jogo salvo...");
			Jogo jogo = (Jogo) salvamento.carregarJogo(janela);

			if (jogo != null) {

				this.jogo = jogo;
				janela.tabuleiro.jogo = this.jogo;
				janela.tabuleiro.repaint();
			}
		} else if (e.getSource() == janela.novoJogo) {

			if (jogo.movimentado) {
				int result = JOptionPane.showConfirmDialog(janela,
						"Tem certeza que deseja comecar um novo jogo?\nO progresso atual sera perdido.",
						"Novo jogo", JOptionPane.YES_NO_OPTION);

				if (result == 0) {
					resetTabuleiro();
					this.jogo = new Jogo();
					janela.tabuleiro.jogo = this.jogo;
					janela.tabuleiro.repaint();
				}
			}

		}
	}
}
