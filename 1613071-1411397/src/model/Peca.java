package model;

public abstract class Peca 
{
	private Cor cor;
	public abstract void movimento(int xDest,int yDest);
	
	public Peca (Cor cor) 
	{
		this.cor = cor;
	}
	public Cor getCor() 
	{
		return cor;
	}
}
