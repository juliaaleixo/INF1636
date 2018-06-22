package model;


public class Cavalo extends Peca
{
	private static final long serialVersionUID = 1L;
	public Cavalo (Cor cor)
	{
		super (cor);
	}
	
	/**
	 * Chama funcao de movimento do cavalo, caso seja possivel.
	 * Caso contrario, levanta uma excecao do tipo MovIlegalExcecao
	 */
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
			
			movRealizado();
		}
		else
		{
			String str = "Proibido mover cavalo para essa localizacao";
			throw new MovIlegalExcecao(str);
		}	
	}
	/**
	 * Verifica se existe um caminho livre entre as coordenadas de origem e destino, levando em consideracao
	 * as regras dos movimentos do cavalo. 
	 */
	public boolean caminhoLivre (int xOrig, int yOrig, int xDest, int yDest, Peca[][] tabuleiro)
	{
		// se anda 2 em X anda 1 em Y e vice versa.
		// Se anda 2 em Y, X ser� xOrig-1 ou xOrig+1
		// Se anda 2 em X, Y ser� yOrig-1 ou yOrig+1
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
