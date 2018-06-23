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

	/**
	 * Cria novo jogo e janela
	 */
	public TabuleiroController() 
	{
		jogo = new Jogo();
		janela = new Janela(jogo);

		Facade.getInstance().configuraObserverJogo(jogo,this);
		
		for (int i = 0; i < 8; i++) 
		{
			for (int j = 0; j < 8; j++) 
			{
				if (jogo.tabuleiro[i][j] != null)
				{
					Facade.getInstance().configuraObserverPeca(i, j, jogo, this);
				}
			}
		}

		janela.setVisible(true);

		janela.addMouseListener(this);

		janela.novoJogo.addActionListener(this);
		janela.salvar.addActionListener(this);
		janela.carregar.addActionListener(this);
	}
	
	/**
	 * Reseta tabuleiro quando se inicia novo jogo
	 */
	public void resetTabuleiro() {
		jogo = new Jogo();
		jogo.rodadaAtual = Cor.branco;
		pecaSelecionada = false;
		Facade.getInstance().configuraObserverJogo(jogo,this);

		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (jogo.tabuleiro[i][j] != null) {
					Facade.getInstance().configuraObserverPeca(i, j, jogo, this);
				}
			}
		}

	}
	/**
	 * Faz o movimento da peca, e verifica se com esse movimento algum rei ficou em xeque ou xeque mate
	 */
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

	/**
	 * Pega uma coordenada x da janela e a transforma em uma posicao x da matriz de pecas
	 * @param x: coordenada x
	 * @return
	 */
	public int getTileX(int x) {
		return (x - 3) / 50;
	}

	/**
	 * Pega uma coordenada y da janela e a transforma em uma posicao y da matriz de pecas
	 * @param y: coordenada y
	 * @return
	 */
	public int getTileY(int y) {
		return 7 - ((y - 48) / 50);
	}

	/**
	 * Configura o mouse pressionado
	 * Determina rodada atual e caso e primeiro ou segundo clique. 
	 * Caso seja o primeiro, mostra todas as possicoes possiveis daquela peca. 
	 * Caso seja o primeiro e queira mudar para outra peca, verifico se e da mesma cor.
	 * No segundo clique, o movimento da peca e realizado.
	 */
	public void mousePressed(MouseEvent e)
	{
		if (pecaSelecionada == false) 
		{
			xOrig = getTileX(e.getX());
			yOrig = getTileY(e.getY());

			if (jogo.tabuleiro[xOrig][yOrig] != null)
			{
				if (jogo.tabuleiro[xOrig][yOrig].getCor() != jogo.rodadaAtual) 
				{
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

	/**
	 * Percorre o tabuleiro, verificando para quais posicoes aquela peca pode se mover.
	 * @param tabuleiro: tabuleiro atual
	 * @param xOrig: coordenada x de origem da peca
	 * @param yOrig: coordenada y de origem da peca
	 * @return
	 */
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

	/**
	 * Percorre o tabuleiro, verificando todos os reis e indicando caso estao em xeque.
	 * @param tabuleiro: tabuleiro atual
	 * @return
	 */
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
	
	/**
	 * Atualiza observer no caso de uma promocao do peao acontecer
	 */
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
	/**
	 * Salva jogo, carrega jogo ja salvo e inicia novo jogo
	 */
	public void actionPerformed(ActionEvent e) {
		SalvamentoDao salvamento = SalvamentoDao.getInstance();
		if (e.getSource() == janela.salvar) {
			salvamento.salvarJogo(jogo, janela);
			
		} else if (e.getSource() == janela.carregar) {
			Jogo jogo = (Jogo) salvamento.carregarJogo(janela);

			if (jogo != null) {
				this.jogo = jogo;
				janela.tabuleiro.jogo = this.jogo;
				Facade.getInstance().configuraObserverJogo(jogo, this);
				//reconfigura observers das pecas 
				for (int i = 0; i < 8; i++) 
				{
					for (int j = 0; j < 8; j++) 
					{
						if (jogo.tabuleiro[i][j] != null) 
						{
							Facade.getInstance().configuraObserverPeca(i, j, jogo, this);
						}
					}
				}
				jogo.tabuleiroAtualizado();
			}
		} else if (e.getSource() == janela.novoJogo) {

			if (jogo.movimentado) {
				int result = JOptionPane.showConfirmDialog(janela,
						"Tem certeza que deseja comecar um novo jogo?\nO progresso atual sera perdido.",
						"Novo jogo", JOptionPane.YES_NO_OPTION);

				if (result == 0) {
					novoJogo();
				}
			}

		}
	}
	/**
	 * Cria novo jogo
	 */
	public void novoJogo()
	{
		this.jogo = new Jogo();
		resetTabuleiro();
		janela.tabuleiro.jogo = this.jogo;
		jogo.tabuleiroAtualizado();
	}
}
