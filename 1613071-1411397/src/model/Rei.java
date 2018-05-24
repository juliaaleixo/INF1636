package model;

public class Rei extends Peca
{
	public Rei (Cor cor)
	{
		super(cor);
	}
	public void movimento (int xOrig, int yOrig, int xDest, int yDest, Peca[][] tabuleiro) throws MovIlegalExcecao
	{
		if( xOrig == xDest && yOrig == yDest )
		{
			String str = "Proibido mover rei para essa localizacao";
			throw new MovIlegalExcecao(str);
		}
		if ( caminhoLivre(xOrig,yOrig,xDest,yDest,tabuleiro) )
		{
			realizaMov(xOrig, yOrig, xDest, yDest, tabuleiro);
		}
		else
		{
			String str = "Proibido mover rei para essa localizacao";
			throw new MovIlegalExcecao(str);
		}
	}
	public boolean caminhoLivre (int xOrig, int yOrig, int xDest, int yDest, Peca[][] tabuleiro)
	{
		//incompleta 
		
		// andar vertical
		if ( xOrig == xDest )
		{
			if ( Math.abs(yDest - yOrig) != 1 )
			{
				return false;
			}
		}
		// andar horizontal
		else if ( yOrig == yDest ) 
		{
			if ( (Math.abs(xDest - xOrig)) != 1 )
			{ 
				return false;
			}
		}
		// andar nas diagonais
		else if ( Math.abs(xDest - xOrig) != Math.abs(yDest - yOrig) || Math.abs(xDest - xOrig) != 1 )
		{
			return false;
		}
		//verificar se rei está sobre ataque
		//verificando se a posicao final contem uma peca da cor oposta
		if ( this.verificaUltimaCasa(xDest, yDest, tabuleiro[xOrig][yOrig].getCor(), tabuleiro) ) 
		{
			return true;
		}
		return false;
	}
}
