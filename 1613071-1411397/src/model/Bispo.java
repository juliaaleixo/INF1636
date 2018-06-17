package model;

import java.util.ArrayList;

public class Bispo extends Peca
{
	public Bispo (Cor cor)
	{
		super (cor);
	}
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
	public boolean caminhoLivre (int xOrig, int yOrig, int xDest, int yDest, Peca[][] tabuleiro)
	{
		Peca p = tabuleiro[xOrig][yOrig];
		
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
