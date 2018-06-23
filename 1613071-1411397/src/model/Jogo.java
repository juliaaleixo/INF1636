package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Observable;

public class Jogo extends Observable implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	public Peca tabuleiro [][] = new Peca[8][8];
	public boolean movimentado = false;
	public Cor rodadaAtual = Cor.branco;
	
	public Peca getPeca (int x, int y)
	{
		return tabuleiro[x][y];
	}
	
	/**
	 * Inicia um jogo, inserindo as pecas de cada cor em seus devidos lugares no tabuleiro.
	 */
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
		
	}
	
	/**
	 * Verifica se, apos um movimento, rei vai ficar em xeque.
	 * @param xOrig: coordenada x da origem
	 * @param yOrig: coordenada y da origem
	 * @param xDest: coordenada x do destino
	 * @param yDest: coordenada y do destino
	 * @return
	 */
	public boolean reiDesprotegido( int xOrig, int yOrig, int xDest, int yDest )
	{
		// criar um tabuleiro para verificar se, com o movimento feito, rei vai ficar em xeque
		Peca[][] tabuleiroAuxiliar = new Peca[8][8]; 
		
		for (int i = 0; i< 8; i++) 
		{
			for (int j = 0; j< 8; j++) 
			{
				tabuleiroAuxiliar[i][j] = tabuleiro[i][j];
			}
		}
		 
		boolean primMov = false;
		boolean movimentouRei = false;
		boolean movimentouTorre = false;
		
		try
		{
			//gravar se o movimento do peao foi o primeiro para depois restaura-lo
			if ( tabuleiroAuxiliar[xOrig][yOrig] instanceof Peao )
			{
				if ( yDest == 0 || yDest == 7 )
				{
					Cor cor = tabuleiroAuxiliar[xOrig][yOrig].getCor();
					tabuleiroAuxiliar[xOrig][yOrig] = null;
					
					tabuleiroAuxiliar[xDest][yDest] = new Rainha(cor);
					if ( testaXeque(tabuleiroAuxiliar,xDest,yDest) )
					{
						return true;
					}
					
					tabuleiroAuxiliar[xDest][yDest] = new Torre(cor);
					if ( testaXeque(tabuleiroAuxiliar,xDest,yDest) )
					{
						return true;
					}
					
					tabuleiroAuxiliar[xDest][yDest] = new Bispo(cor);
					if ( testaXeque(tabuleiroAuxiliar,xDest,yDest) )
					{
						return true;
					}
					
					tabuleiroAuxiliar[xDest][yDest] = new Torre(cor);
					if ( testaXeque(tabuleiroAuxiliar,xDest,yDest) )
					{
						return true;
					}
					return false;
				}
				else
				{
					primMov = ((Peao)tabuleiroAuxiliar[xOrig][yOrig]).getPrimeiroMov();
					tabuleiroAuxiliar[xOrig][yOrig].movimento(xOrig, yOrig, xDest, yDest, tabuleiroAuxiliar);
					((Peao)tabuleiroAuxiliar[xDest][yDest]).setPrimeiroMov(primMov);
				}
			}
			else 
			{
				//caso seja instancia do rei, precisa restaurar caso ele ja tinha feito algum movimento
				if( tabuleiroAuxiliar[xOrig][yOrig] instanceof Rei )
				{
					movimentouRei = ((Rei)tabuleiroAuxiliar[xOrig][yOrig]).getMovimentou();
					
					//o movimento do roque depende se a torre tambem se movimentou
					if ( xDest == xOrig + 2 && yOrig == yDest)
					{
						Peca p = tabuleiro[7][yOrig];
						if ( p instanceof Torre )
						{
							movimentouTorre = ((Torre)p).getMovimentou();
							tabuleiroAuxiliar[xOrig][yOrig].movimento(xOrig, yOrig, xDest, yDest, tabuleiroAuxiliar);
							((Rei)tabuleiroAuxiliar[xDest][yDest]).setMovimentou(movimentouRei);
							if ( ((Rei)tabuleiroAuxiliar[xDest][yDest]).roquePermitido(xOrig, yOrig, xDest, yDest, tabuleiro) )
							{
								((Torre)tabuleiro[5][yDest]).setMovimentou(movimentouTorre);
							}
						}
						else
						{
							tabuleiroAuxiliar[xOrig][yOrig].movimento(xOrig, yOrig, xDest, yDest, tabuleiroAuxiliar);
							((Rei)tabuleiroAuxiliar[xDest][yDest]).setMovimentou(movimentouRei);
						}
					}
					else if ( xDest == xOrig - 2 && yOrig == yDest)
					{
						Peca p = tabuleiro[0][yOrig];
						if ( p instanceof Torre )
						{
							movimentouTorre = ((Torre)p).getMovimentou();
							tabuleiroAuxiliar[xOrig][yOrig].movimento(xOrig, yOrig, xDest, yDest, tabuleiroAuxiliar);
							((Rei)tabuleiroAuxiliar[xDest][yDest]).setMovimentou(movimentouRei);
							if ( ((Rei)tabuleiroAuxiliar[xDest][yDest]).roquePermitido(xOrig, yOrig, xDest, yDest, tabuleiro) )
							{
								((Torre)tabuleiro[3][yDest]).setMovimentou(movimentouTorre);
							}
						}
						else
						{
							tabuleiroAuxiliar[xOrig][yOrig].movimento(xOrig, yOrig, xDest, yDest, tabuleiroAuxiliar);
							((Rei)tabuleiroAuxiliar[xDest][yDest]).setMovimentou(movimentouRei);
						}
					}
					else 
					{
						tabuleiroAuxiliar[xOrig][yOrig].movimento(xOrig, yOrig, xDest, yDest, tabuleiroAuxiliar);
						((Rei)tabuleiroAuxiliar[xDest][yDest]).setMovimentou(movimentouRei);
					}
					
				}
				else
				{
					tabuleiroAuxiliar[xOrig][yOrig].movimento(xOrig, yOrig, xDest, yDest, tabuleiroAuxiliar);
				}
				
			}
		
		}
		catch (MovIlegalExcecao e)
		{
			return false;
		}
		return testaXeque(tabuleiroAuxiliar,xDest,yDest);
	}
	
	/**
	 * 
	 * @param tabuleiroAuxiliar
	 * @param xDest
	 * @param yDest
	 * @return
	 */
	public boolean testaXeque ( Peca [][] tabuleiroAuxiliar, int xDest, int yDest )
	{
		for ( int i = 0; i < 8; i++ )
		{
			for ( int j = 0; j < 8; j++ )
			{
				if ( tabuleiroAuxiliar[i][j] != null )
				{
					Peca p = tabuleiroAuxiliar[i][j];
					Cor corP = p.getCor();
			  
					if ( p instanceof Rei ) 
					{
						if ( ((Rei) p).xeque(i, j, tabuleiroAuxiliar, corP) )
						{
							if ( corP == tabuleiroAuxiliar[xDest][yDest].getCor() )
							{
								return true;
							}
						}
					}
				}
			}
		}
		return false;
	}
	public void tabuleiroAtualizado()
	{
		ArrayList<Object> obj = new ArrayList<Object>();
		obj.add("repaint");
	
		this.setChanged();
		this.notifyObservers(obj);
	}
}
