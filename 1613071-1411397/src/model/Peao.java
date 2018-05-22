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
		
		if(p.getCor() == Cor.branco) {
			
			// capturando uma peca nas diagonais
			if((yDest == yOrig + 1) && ((xDest == xOrig + 1) || (xDest == xOrig - 1))) {
				if(tabuleiro[xDest][yDest] != null) {
					realizaMov(xOrig,yOrig,xDest,yDest,tabuleiro);
					//TODO
				}
			}
			// andando para frente, caso nao haja peca no caminho
			else if(primeiroMov == true) {
				if((yDest == yOrig + 2 || yDest == yOrig + 1) && (xDest == xOrig)) {
					if(tabuleiro[xDest][yDest] == null) {
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
				if((yDest == yOrig - 1) && (xDest == xOrig)) {
					if(tabuleiro[xDest][yDest] == null) {
						realizaMov(xOrig,yOrig,xDest,yDest,tabuleiro);
					} 
					else {
						String str = "Proibido mover peao para essa localizacao";
						throw new MovIlegalExcecao(str);
					}
				}
			}
		} 
		else {
			if(primeiroMov == true) {
				if((yDest == yOrig - 2 || yDest == yOrig - 1) && (xDest == xOrig)) {
					if(tabuleiro[xDest][yDest] == null) {
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
				if((yDest == yOrig + 1) && (xDest == xOrig)) {
					if(tabuleiro[xDest][yDest] == null) {
						realizaMov(xOrig,yOrig,xDest,yDest,tabuleiro);
					} 
					else {
						String str = "Proibido mover peao para essa localizacao";
						throw new MovIlegalExcecao(str);
					}
				}
			}
		}
		
		
	}
	
	// classe j� herda esse m�todo, j� que ele � static e protected (s� classes filhas e do pacote podem usar)
	/*
	private void realizaMov (int xOrig, int yOrig, int xDest, int yDest, Peca[][] tabuleiro) {
		
		Peca p = tabuleiro[xOrig][yOrig];
		
		tabuleiro[xDest][yDest] = p;
		tabuleiro[xOrig][yOrig] = null;
	
	}
	*/
	
}
