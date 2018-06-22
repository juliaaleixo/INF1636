package model;

public class Bispo extends Peca
{
	private static final long serialVersionUID = 1L;
	public Bispo (Cor cor)
	{
		super (cor);
	}
	/**
	 * Chama funcao de movimento do bispo, caso seja possivel.
	 * Caso contrario, levanta uma excecao do tipo MovIlegalExcecao
	 */
	public void movimento (int xOrig, int yOrig, int xDest, int yDest, Peca[][] tabuleiro) throws MovIlegalExcecao
	{
		if( xOrig == xDest && yOrig == yDest )
		{
			String str = "Proibido mover bispo para essa localizacao";
			throw new MovIlegalExcecao(str);
		}
		if ( caminhoLivre(xOrig,yOrig,xDest,yDest,tabuleiro) )
		{
			realizaMov(xOrig, yOrig, xDest, yDest, tabuleiro);
			
			movRealizado();
		}
		else
		{
			String str = "Proibido mover bispo para essa localizacao";
			throw new MovIlegalExcecao(str);
		}	
	}
	/**
	 * Verifica se existe um caminho livre entre as coordenadas de origem e destino, levando em consideracao
	 * as regras dos movimentos do bispo. 
	 */
	public boolean caminhoLivre (int xOrig, int yOrig, int xDest, int yDest, Peca[][] tabuleiro)
	{
		
		// andar nas diagonais
		if ( Math.abs(xDest - xOrig) == Math.abs(yDest - yOrig) )
		{
			
			for ( int i = 1 ; i < (Math.abs(xDest - xOrig)); i++ )
			{ 
				int passoX = i;
				int passoY = i;
				
				if ( xDest < xOrig )
				{
					passoX = passoX * (-1);
				}
				if ( yDest < yOrig )
				{
					passoY = passoY * (-1);
				}
				if ( tabuleiro[xOrig+passoX][yOrig+passoY] != null )
				{
					return false;
				}
			}
		}
		else
		{
			return false;
		}
		if ( verificaUltimaCasa (xDest, yDest, tabuleiro[xOrig][yOrig].getCor(), tabuleiro) )
		{
			return true;
		}
		return false;
	}
}
