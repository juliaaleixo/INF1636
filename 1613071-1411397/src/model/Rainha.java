package model;

public class Rainha extends Peca
{
	
	public Rainha (Cor cor)
	{
		super (cor);
	}
	public void movimento (int xOrig, int yOrig, int xDest, int yDest, Peca[][] tabuleiro)
	{
		Peca r = tabuleiro[xOrig][yOrig];
		
		if (r.getCor() == Cor.branco)
		{
			// andar p frente e tras
			if (xOrig == xDest && Math.abs(yDest-yOrig) < 8)
			{
				
			}
			// andar nas diagonais
			
		}
		else
		{
			
		}
	}
	
}
