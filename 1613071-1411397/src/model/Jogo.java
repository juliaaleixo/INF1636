package model;

public class Jogo 
{
	Peca tabuleiro [][] = new Peca[8][8];
	
	public Peca getPeca (int x, int y)
	{
		return tabuleiro[x][y];
	}
	
	public Jogo()
	{
		for (int i = 0; i < 8; i++)
		{
			for (int j = 0; j < 8; j++)
			{
				tabuleiro[i][j] = null;
			}
		}
		
		//posicionando os peoes
		for (int i = 0; i < 8; i++)
		{
			tabuleiro[i][1] = new Peao(Cor.branco);
			tabuleiro[i][6] = new Peao(Cor.preto);
		}
		
		//posicionando as torres
		tabuleiro[0][0] = new Torre(Cor.branco);
		tabuleiro[7][0] = new Torre(Cor.branco);
		tabuleiro[0][7] = new Torre(Cor.preto);
		tabuleiro[7][7] = new Torre(Cor.preto);
		
		//posicionando os cavalos
		tabuleiro[1][0] = new Cavalo(Cor.branco);
		tabuleiro[6][0] = new Cavalo(Cor.branco);
		tabuleiro[1][7] = new Cavalo(Cor.preto);
		tabuleiro[6][7] = new Cavalo(Cor.preto);
				
		//posicionando os bispos
		tabuleiro[2][0] = new Bispo(Cor.branco);
		tabuleiro[5][0] = new Bispo(Cor.branco);
		tabuleiro[2][7] = new Bispo(Cor.preto);
		tabuleiro[5][7] = new Bispo(Cor.preto);
		
		//posicionando as rainhas
		tabuleiro[3][0] = new Rainha(Cor.branco);
		tabuleiro[3][7] = new Rainha(Cor.preto);
	
		//posicionando os reis
		tabuleiro[4][0] = new Rei(Cor.branco);
		tabuleiro[4][7] = new Rei(Cor.preto);
		
		try {
			tabuleiro[2][1].movimento(2, 1, 2, 2, tabuleiro);
		} catch (MovIlegalExcecao e){
			return;
		}
	}
}
