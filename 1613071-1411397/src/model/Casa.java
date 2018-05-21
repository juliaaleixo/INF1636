package model;

public class Casa {
	
	final int x;
	final int y;
	private Peca peca;
	
	public Casa(int x, int y, Peca peca) {
		this.x = x;
		this.y = y;
		this.peca = peca;
		
	}
	
	public Peca getPeca () {
		return this.peca;
	}
	
	public void setPeca (Peca peca) {
		this.peca = peca;
	}
	
	
}
