package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Observable;

public abstract class Peca extends Observable implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	private Cor cor;
	
	public abstract void movimento (int xOrig, int yOrig, int xDest, int yDest, Peca[][] tabuleiro) throws MovIlegalExcecao;
	public abstract boolean caminhoLivre(int xOrig, int yOrig, int xDest, int yDest, Peca[][] tabuleiro); 
	
	public Peca (Cor cor) 
	{
		this.cor = cor;
	}
	public Cor getCor() 
	{
		return cor;
	}
	/**
	 * Realiza o movimento da peca.
	 * É chamada depois de veficiar se o movimento e possivel. 
	 * @param xOrig: a coordenada x de origem da peca
	 * @param yOrig: a coordenada y de origem da peca
	 * @param xDest: a coordenada x de destino da peca
	 * @param yDest: a coordenada y de destino da peca
	 * @param tabuleiro: o tabuleiro atual
	 */
	protected static void realizaMov (int xOrig, int yOrig, int xDest, int yDest, Peca[][] tabuleiro)
	{
		Peca p = tabuleiro[xOrig][yOrig];
		
		tabuleiro[xDest][yDest] = p;
		tabuleiro[xOrig][yOrig] = null;
	}
	/**
	 * Verifica a ultima casa no percurso que a peca deseja andar. 
	 * Caso esteja vazia ou a casa tenha uma peca de uma cor diferente que a sua,
	 * a funcao retorna true
	 * @param xDest: a coordenada x do destino
	 * @param yDest: a coordenada y do destino
	 * @param corP: a cor da peca que deseja se movimentar para essa posicao no tabuleiro
	 * @param tabuleiro: tabuleiro atual
	 * @return
	 */
	protected boolean verificaUltimaCasa (int xDest, int yDest, Cor corP, Peca[][] tabuleiro) 
	{
		if ( tabuleiro[xDest][yDest] != null )
		{
			if ( tabuleiro[xDest][yDest].getCor() != corP )
			{
				return true;
			} 
			else
			{
				return false;
			}
		}
		return true;
	}
	/**
	 * Apos um movimento ser realizado, notifica observadores para tabuleiro ser repintado.
	 */
	public void movRealizado()
	{
		ArrayList<Object> obj = new ArrayList<Object>();
		obj.add("repaint");
	
		this.setChanged();
		this.notifyObservers(obj);
	}
}
