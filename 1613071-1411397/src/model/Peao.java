package model;

public class Peao extends Peca
{
	boolean primeiroMov = true;
	
	public Peao (Cor cor)
	{
		super (cor);
	}
	
	public void movimento (int xOrig, int yOrig, int xDest, int yDest, Peca[][] tabuleiro) throws MovIlegalExcecao
	{
		// andando para frente, caso nao haja peca no caminho
		if ( primeiroMov == true ) 
		{
				if ( caminhoLivre(xOrig,yOrig,xDest,yDest,tabuleiro) ) 
				{
					realizaMov (xOrig,yOrig,xDest,yDest,tabuleiro);
					this.primeiroMov = false;
				} 
				else 
				{
					String str = "Proibido mover peao para essa localizacao";
					throw new MovIlegalExcecao(str);
				}
		} 
		else {
			
				if(caminhoLivre(xOrig,yOrig,xDest,yDest,tabuleiro)) {
					realizaMov(xOrig,yOrig,xDest,yDest,tabuleiro);
				} 
				else {
					String str = "Proibido mover peao para essa localizacao";
					throw new MovIlegalExcecao(str);
				}
		}
	} 

	
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
		if((yDest == yOrig + m1) && ((xDest == xOrig + 1) || (xDest == xOrig - 1))) 
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
