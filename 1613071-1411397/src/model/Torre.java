package model;

public class Torre extends Peca
{
	public Torre (Cor cor)
	{
		super(cor);
	}
	public void movimento (int xOrig, int yOrig, int xDest, int yDest, Peca[][] tabuleiro) throws MovIlegalExcecao
	{
		if( xOrig == xDest && yOrig == yDest )
		{
			String str = "Proibido mover torre para essa localizacao";
			throw new MovIlegalExcecao(str);
		}
		if ( caminhoLivre(xOrig,yOrig,xDest,yDest,tabuleiro) )
		{
			realizaMov(xOrig, yOrig, xDest, yDest, tabuleiro);
		}
		else
		{
			String str = "Proibido mover torre para essa localizacao";
			throw new MovIlegalExcecao(str);
		}
	}
	public boolean caminhoLivre (int xOrig, int yOrig, int xDest, int yDest, Peca[][] tabuleiro)
	{
		// andar vertical
		if ( xOrig == xDest )
		{
			for ( int i = 1 ; i < (Math.abs(yDest - yOrig)); i++ )
			{ 
				//sinal do passo muda dependendo do sentido do movimento (descendo: negativo, subindo: positivo)
				int passo = i;
				if ( yDest < yOrig )
				{
					passo = passo * (-1);
				}
				if ( tabuleiro[xOrig][yOrig+passo] != null )
				{
					//System.out.println(xOrig);
					//System.out.println(yOrig+passo);
					return false;
				}
			}
		}
		// andar horizontal
		else if ( yOrig == yDest ) 
		{
			for ( int i = 1 ; i < (Math.abs(xDest - xOrig)); i++ )
			{ 
				int passo = i;
				if ( xDest < xOrig )
				{
					passo = passo * (-1);
				}
				if ( tabuleiro[xOrig+passo][yOrig] != null )
				{
					return false;
				}
			}
		}
		else
		{
			return false;
		}
		//verificando se a posicao final contem uma peca da cor oposta
		if ( this.verificaUltimaCasa(xDest, yDest, tabuleiro[xOrig][yOrig].getCor(), tabuleiro) ) 
		{
			return true;
		}
		return false;
	}
}
