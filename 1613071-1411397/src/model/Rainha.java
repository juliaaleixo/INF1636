

package model;

public class Rainha extends Peca
{
	private static final long serialVersionUID = 1L;
	public Rainha (Cor cor)
	{
		super(cor);
	}
	
	/**
	 * Chama funcao de movimento da rainha, caso seja possivel.
	 * Caso contrario, levanta uma excecao do tipo MovIlegalExcecao
	 */
	public void movimento (int xOrig, int yOrig, int xDest, int yDest, Peca[][] tabuleiro) throws MovIlegalExcecao
	{
		if( xOrig == xDest && yOrig == yDest )
		{
			String str = "Proibido mover rainha para essa localizacao";
			throw new MovIlegalExcecao(str);
		}
		if ( caminhoLivre(xOrig,yOrig,xDest,yDest,tabuleiro) )
		{
			realizaMov(xOrig, yOrig, xDest, yDest, tabuleiro);
			
			movimentoRealizado();
		}
		else
		{
			String str = "Proibido mover rainha para essa localizacao";
			throw new MovIlegalExcecao(str);
		}
		
	}
	/**
	 * Verifica se existe um caminho livre entre as coordenadas de origem e destino, levando em consideracao
	 * as regras dos movimentos da rainha
	 */
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
		// andar nas diagonais
		else if ( Math.abs(xDest - xOrig) == Math.abs(yDest - yOrig) )
		{
			
			for ( int i = 1 ; i < (Math.abs(xDest - xOrig)); i++ )
			{ 
				int passoX = i;
				int passoY = i;
				
				if ( xDest < xOrig )
				{
					passoX = passoX * (-1);
				}
				if( yDest < yOrig )
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
