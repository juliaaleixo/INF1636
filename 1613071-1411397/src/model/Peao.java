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
			if(primeiroMov == true) {
				if((yDest == yOrig + 2 || yDest == yOrig + 1) && (xDest == xOrig)) {
					if(tabuleiro[xDest][yDest] == null) {
						tabuleiro[xDest][yDest] = p;
						tabuleiro[xOrig][yOrig] = null;
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
						tabuleiro[xDest][yDest] = p;
						tabuleiro[xOrig][yOrig] = null;
					} else {
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
						tabuleiro[xDest][yDest] = p;
						tabuleiro[xOrig][yOrig] = null;
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
						tabuleiro[xDest][yDest] = p;
						tabuleiro[xOrig][yOrig] = null;
					} else {
						String str = "Proibido mover peao para essa localizacao";
						throw new MovIlegalExcecao(str);
					}
				}
			}
		}
		
		
	}
	
	
}
