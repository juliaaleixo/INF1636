

package model;

public class Rainha extends Peca
{
	private static int qtdRainhas = 0;
	
	public Rainha (Cor cor)
	{
		super(cor);
		qtdRainhas++;
	}

	public static void decrementaQtdRainhas () {
		qtdRainhas--;
	}
	
	public int getQtdRainhas () {
		return qtdRainhas;
	}
	
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
		}
		else
		{
			String str = "Proibido mover rainha para essa localizacao";
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
		//verificando se a posicao final contem uma peca da cor oposta
		if ( this.verificaUltimaCasa(xDest, yDest, tabuleiro[xOrig][yOrig].getCor(), tabuleiro) ) 
		{
			return true;
		}
		return false;
	}
	
}
