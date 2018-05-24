package model;

public abstract class Peca 
{
	private Cor cor;
	
	public abstract void movimento (int xOrig, int yOrig, int xDest, int yDest, Peca[][] tabuleiro) throws MovIlegalExcecao;
	public abstract boolean caminhoLivre(int xOrig, int yOrig, int xDest, int yDest, Peca[][] tabuleiro); 
	
	public Peca (Cor cor) 
	{
		this.cor = cor;
	}
	public Cor getCor() 
	{
		return cor;
	}
	
	protected static void realizaMov (int xOrig, int yOrig, int xDest, int yDest, Peca[][] tabuleiro) {
		
		Peca p = tabuleiro[xOrig][yOrig];
		
		tabuleiro[xDest][yDest] = p;
		tabuleiro[xOrig][yOrig] = null;
	}
	
	public void verificaRainha(int xDest, int yDest, Peca[][] tabuleiro) {
		Peca p = tabuleiro[xDest][yDest];
		
		if(p instanceof Rainha) {
			Rainha.decrementaQtdRainhas();
		}
	}
	
	protected boolean verificaUltimaCasa (int xDest, int yDest, Cor corP, Peca[][] tabuleiro) {
		
		if ( tabuleiro[xDest][yDest] != null )
		{
			if ( tabuleiro[xDest][yDest].getCor() != corP )
			{
				this.verificaRainha(xDest, yDest, tabuleiro);
			} else {
				return false;
			}
		}
		return true;
	}
}
