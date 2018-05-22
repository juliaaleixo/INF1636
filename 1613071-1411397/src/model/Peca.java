package model;

public abstract class Peca 
{
	private Cor cor;
	public abstract void movimento (int xOrig, int yOrig, int xDest, int yDest, Peca[][] tabuleiro) throws MovIlegalExcecao;
	
	public Peca (Cor cor) 
	{
		this.cor = cor;
	}
	public Cor getCor() 
	{
		return cor;
	}
}
