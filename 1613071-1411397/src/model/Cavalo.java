package model;

public class Cavalo extends Peca
{

	public Cavalo (Cor cor)
	{
		super (cor);
	}
	public void movimento (int xOrig, int yOrig, int xDest, int yDest, Peca[][] tabuleiro) throws MovIlegalExcecao
	{
		if( xOrig == xDest && yOrig == yDest )
		{
			String str = "Proibido mover cavalo para essa localizacao";
			throw new MovIlegalExcecao(str);
		}
		if ( caminhoLivre(xOrig,yOrig,xDest,yDest,tabuleiro) )
		{
			realizaMov(xOrig, yOrig, xDest, yDest, tabuleiro);
		}
		else
		{
			String str = "Proibido mover cavalo para essa localizacao";
			throw new MovIlegalExcecao(str);
		}	
	}
	public boolean caminhoLivre (int xOrig, int yOrig, int xDest, int yDest, Peca[][] tabuleiro)
	{
		// se anda 2 em X anda 1 em Y e vice versa.
		// Se anda 2 em Y, X será xOrig-1 ou xOrig+1
		// Se anda 2 em X, Y será yOrig-1 ou yOrig+1
		// Nao importa a cor da peca, sempre anda em todas as direcoes em L
		Peca p = tabuleiro[xOrig][yOrig];
		
		// se andar 2 ou -2 em X && andar 1 ou -1 em Y -> permitido
		if (((xDest == xOrig + 2) || (xDest == xOrig - 2)) && ((yDest == yOrig + 1) || (yDest == yOrig - 1))) {
			if (this.verificaUltimaCasa(xDest, yDest, p.getCor(), tabuleiro)) {
				return true;
			}
		}
		// se andar 2 ou -2 em Y && andar 1 ou -1 em X -> permitido
		if (((yDest == yOrig + 2) || (yDest == yOrig - 2)) && ((xDest == xOrig + 1) || (xDest == xOrig - 1))) {
			if (this.verificaUltimaCasa(xDest, yDest, p.getCor(), tabuleiro)) {
				return true;
			}
		}
		return false;
	}
	
}
