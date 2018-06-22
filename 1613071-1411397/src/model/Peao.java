package model;

import java.util.ArrayList;

public class Peao extends Peca
{
	private static final long serialVersionUID = 1L;
	boolean primeiroMov = true;
	
	public Peao (Cor cor)
	{
		super (cor);
	}
	public boolean getPrimeiroMov()
	{
		return primeiroMov;
	}
	public void setPrimeiroMov( boolean mov )
	{
		this.primeiroMov = mov;
	}
	/**
	 * Chama funcao de movimento do peao, caso seja possivel.
	 * Caso contrario, levanta uma excecao do tipo MovIlegalExcecao
	 */
	public void movimento (int xOrig, int yOrig, int xDest, int yDest, Peca[][] tabuleiro) throws MovIlegalExcecao
	{
		if ( primeiroMov == true ) 
		{
				if ( caminhoLivre(xOrig,yOrig,xDest,yDest,tabuleiro) ) 
				{
					realizaMov (xOrig,yOrig,xDest,yDest,tabuleiro);
					this.primeiroMov = false;
					
					movimentoRealizado();
				} 
				else 
				{
					String str = "Proibido mover peao para essa localizacao";
					throw new MovIlegalExcecao(str);
				}
		} 
		else 
		{
			if ( caminhoLivre(xOrig,yOrig,xDest,yDest,tabuleiro) ) 
			{
				realizaMov(xOrig,yOrig,xDest,yDest,tabuleiro);
				movimentoRealizado();
				
				//verificar se peao pode ser promovido
				if ( this.getCor() == Cor.branco )
				{
					if ( yDest == 7 )
					{
						ArrayList<Object> obj = new ArrayList<Object>();
						obj.add("promocao");
						obj.add(xDest);
						obj.add(yDest);
						
						this.setChanged();
						this.notifyObservers(obj);
					}
				}
				else
				{
					if ( yDest == 0 )
					{
						ArrayList<Object> obj = new ArrayList<Object>();
						obj.add("promocao");
						obj.add(xDest);
						obj.add(yDest);
						
						this.setChanged();
						this.notifyObservers(obj);
					}
				}
			} 
			else 
			{
				String str = "Proibido mover peao para essa localizacao";
				throw new MovIlegalExcecao(str);
			}
		}
	} 

	/**
	 * Verifica se existe um caminho livre entre as coordenadas de origem e destino, levando em consideracao
	 * as regras dos movimentos do peao.
	 */
	public boolean caminhoLivre (int xOrig, int yOrig, int xDest, int yDest, Peca[][] tabuleiro)
	{
		int m1, m2;
		
		if ( tabuleiro[xOrig][yOrig].getCor() == Cor.branco )
		{
			m1 = 1;
			m2 = 2;
		}
		else
		{
			m1 = -1;
			m2 = -2;
		}
		
		//verificando se é possivel comer uma peça
		if( (yDest == yOrig + m1) && ((xDest == xOrig + 1) || (xDest == xOrig - 1)) ) 
		{
			Peca p = tabuleiro[xOrig][yOrig];
			if(tabuleiro[xDest][yDest] != null && tabuleiro[xDest][yDest].getCor() != p.getCor())
			{
				return true;
			}
		}
		if ( primeiroMov )
		{
			if ( yDest == yOrig + m2 && xDest == xOrig )
			{
				if ( tabuleiro[xDest][yDest] == null && tabuleiro[xDest][yOrig+m1] == null ) 
				{
					return true;
				}
			}
			else if ( yDest == yOrig + m1 && xDest == xOrig )
			{
				if ( tabuleiro[xDest][yDest] == null ) 
				{
					return true;
				}
			}
			return false;
		}
		else 
		{
			if ( (yDest == yOrig + m1) && (xDest == xOrig) ) 
			{
				if ( tabuleiro[xDest][yDest] == null ) 
				{
					return true;
				}
			}
		}
		return false;
	}
	
}
