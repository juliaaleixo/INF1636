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
		Peca p = tabuleiro[xOrig][yOrig];
		Cor corP = p.getCor();
		//caso peca for branca, movimentos sao somados; c.c. sao subtraidos
		int m1, m2;
		if ( p.getCor() == Cor.branco )
		{
			m1 = 1;
			m2 = 2;
		}
		else
		{
			m1 = -1;
			m2 = -2;
		}
		
		// capturando uma peca nas diagonais
		if((yDest == yOrig + m1) && ((xDest == xOrig + 1) || (xDest == xOrig - 1))) {
			if(tabuleiro[xDest][yDest] != null && tabuleiro[xDest][yDest].getCor() != corP) {
				this.verificaRainha(xDest, yDest, tabuleiro);
				realizaMov(xOrig,yOrig,xDest,yDest,tabuleiro);
			}
		}
		
		// andando para frente, caso nao haja peca no caminho
		else if(primeiroMov == true) {
			if((yDest == yOrig + m2 || yDest == yOrig + m1) && (xDest == xOrig)) {
				if(caminhoLivre(xOrig,yOrig,xDest,yDest,tabuleiro)) {
					realizaMov(xOrig,yOrig,xDest,yDest,tabuleiro);
					this.primeiroMov = false;
				} 
				else {
					String str = "Proibido mover peao para essa localizacao";
					throw new MovIlegalExcecao(str);
				}
			}
		} 
		else {
			if((yDest == yOrig + m1) && (xDest == xOrig)) {
				if(caminhoLivre(xOrig,yOrig,xDest,yDest,tabuleiro)) {
					realizaMov(xOrig,yOrig,xDest,yDest,tabuleiro);
				} 
				else {
					String str = "Proibido mover peao para essa localizacao";
					throw new MovIlegalExcecao(str);
				}
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
			if ( tabuleiro[xDest][yDest] == null ) 
			{
				return true;
			}
		}
		return false;
	}
	
}
