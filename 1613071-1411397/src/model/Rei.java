package model;

public class Rei extends Peca
{
	Boolean movimentou = false;
	
	public Rei (Cor cor)
	{
		super(cor);
	}
	
	public Boolean getMovimentou()
	{
		return movimentou;
	}
	
	public void movimento (int xOrig, int yOrig, int xDest, int yDest, Peca[][] tabuleiro) throws MovIlegalExcecao
	{
		if ( xOrig == xDest && yOrig == yDest )
		{
			String str = "Proibido mover rei para essa localizacao";
			throw new MovIlegalExcecao(str);
		}
		if ( caminhoLivre(xOrig,yOrig,xDest,yDest,tabuleiro) )
		{
			// caso o movimento seja o roque, a torre tambem precisa realizar o movimento
			if ( roquePermitido(xOrig, yOrig, xDest, yDest, tabuleiro) )
			{
				if ( xDest > xOrig )
				{
					tabuleiro[7][yOrig].movimento(7, yOrig, 5, yDest, tabuleiro);
				}
				else
				{
					tabuleiro[0][yOrig].movimento(0, yOrig, 3, yDest, tabuleiro);
				}
			}
			realizaMov(xOrig, yOrig, xDest, yDest, tabuleiro);
			movimentou = true;
			
			movRealizado();
		}
		else
		{
			String str = "Proibido mover rei para essa localizacao";
			throw new MovIlegalExcecao(str);
		}
	}
	public boolean caminhoLivre (int xOrig, int yOrig, int xDest, int yDest, Peca[][] tabuleiro)
	{
		// verifica roque
		if ( roquePermitido(xOrig, yOrig, xDest, yDest, tabuleiro) )
		{
			return true;
		}
		// andar vertical
		if ( xOrig == xDest )
		{
			if ( Math.abs(yDest - yOrig) != 1 )
			{
				return false;
			}
		}
		// andar horizontal
		else if ( yOrig == yDest ) 
		{
			if ( (Math.abs(xDest - xOrig)) != 1 )
			{ 
				return false;
			}
		}
		// andar nas diagonais
		else if ( Math.abs(xDest - xOrig) != Math.abs(yDest - yOrig) || Math.abs(xDest - xOrig) != 1 )
		{
			return false;
		}
		//verificar se rei está sobre ataque
		if ( reiSobreAtaque(xDest, yDest, tabuleiro,tabuleiro[xOrig][yOrig].getCor()) )
		{
			return false;
		}
		//verificando se a posicao final contem uma peca da cor oposta
		if ( this.verificaUltimaCasa(xDest, yDest, tabuleiro[xOrig][yOrig].getCor(), tabuleiro) ) 
		{
			return true;
		}
		return false;
	}
	public boolean reiSobreAtaque(int xDest, int yDest, Peca[][] tabuleiro, Cor cor)
  	{
 		for ( int i = 0; i < 8; i++ )
 		{
 			for ( int j = 0; j < 8; j++ )
 			{
 				if ( tabuleiro[i][j] == null )
 				{
 					continue;
 				}
 				
 				Peca p = tabuleiro[i][j];
 				Cor corP = p.getCor();
 				
 				if ( cor == corP )
 				{
 					continue;
 				}
 				
 				if ( p instanceof Peao )
 				{
 					int m1;
 					if ( corP == Cor.branco )
 					{
 						m1 = 1;
 					}
 					else
 					{
 						m1 = -1;
 					}
 					if ( (yDest == j + m1) && ((xDest == i + 1) || (xDest == i - 1)) )
 					{
 						return true;
 						
 					}
 				}
 				else
 				{
 					if ( p.caminhoLivre ( i, j, xDest, yDest, tabuleiro ) )
 					{
 						return true;
 					}
 				}
 			}
 		}
  		return false;
  	}
	
	public Boolean roquePermitido (int xOrig, int yOrig, int xDest, int yDest, Peca[][] tabuleiro)
	{	
		//verificar se roque curto 
		if ( this.movimentou == false )
		{
			//roque direita
			if ( xDest == xOrig + 2 && yOrig == yDest)
			{
				Peca p = tabuleiro[7][yOrig];
				if ( p instanceof Torre )
				{
					// verificando se peca é torre e se já se movimentou
					if ( p.getCor() == this.getCor() && ((Torre) p).getMovimentou() == false )
					{
						//verificando se existe alguma peca entre a torre e o rei
						if( tabuleiro[xOrig+1][yOrig] == null && tabuleiro[xOrig+2][yOrig] == null )
						{
							return true;
						}
					}
				}
			}
			//roque esquerda
			if ( xDest == xOrig - 2 && yOrig == yDest)
			{
				Peca p = tabuleiro[0][yOrig];
				if ( p instanceof Torre )
				{
					// verificando se peca é torre e se já se movimentou
					if ( p.getCor() == this.getCor() && ((Torre) p).getMovimentou() == false )
					{
						//verificando se existe alguma peca entre a torre e o rei
						if( tabuleiro[xOrig-1][yOrig] == null && tabuleiro[xOrig-2][yOrig] == null && tabuleiro[xOrig-3][yOrig] == null)
						{
							return true;
						}
					}
				}
			}
		}
		return false;
	}
	public boolean xeque (int xOrig, int yOrig, Peca[][] tabuleiro, Cor cor)
	{
		//verifica se posicao atual do rei esta sobre ataque
		if ( reiSobreAtaque(xOrig, yOrig, tabuleiro, cor) )
		{
			return true;
		}
		return false;
	}
	public boolean xequeMate (int xOrig, int yOrig, Peca[][] tabuleiro, Cor cor)
	{
		//incompleta: tem que verificar se a peca que esta atacando n pode ser capturada e nenhuma peca pode se colocar na frente
		
		//verifica se posicao atual do rei esta sobre ataque
		if ( xeque(xOrig, yOrig, tabuleiro, cor) )
		{
			for ( int i = 0; i < 8; i++ )
			{
				for ( int j = 0; j < 8; j++ )
				{
					//verifica se o rei pode se movimentar
					if ( caminhoLivre(xOrig, yOrig, i, j, tabuleiro) == true )
					{
						return false;
					}
				}
			}
			if ( protegerRei(xOrig,yOrig,tabuleiro) )
			{
				return false;
			}
			return true;
		}
		return false;
	}
	public boolean protegerRei(int xRei, int yRei, Peca[][] tabuleiro)
	{
		Peca[][] tabuleiroAuxiliar = new Peca[8][8]; 
		
		for (int i = 0; i< 8; i++) 
		{
			for (int j = 0; j< 8; j++) 
			{
				tabuleiroAuxiliar[i][j] = tabuleiro[i][j];
			}
		}
		
		for ( int iOrig = 0; iOrig < 8; iOrig++ )
		{
			for ( int jOrig = 0; jOrig < 8; jOrig++ )
			{
				for ( int iDest = 0; iDest < 8; iDest++  )
				{
					for ( int jDest = 0; jDest < 8; jDest++ )
					{
						try
						{
							boolean primMov = false; 
							
							//gravar se o movimento do peao foi o primeiro para depois restaura-lo
							if ( tabuleiroAuxiliar[iOrig][jOrig] instanceof Peao)
							{
								primMov = ((Peao)tabuleiroAuxiliar[iOrig][jOrig]).getPrimeiroMov();
							}
							
							tabuleiroAuxiliar[iOrig][jOrig].movimento(iOrig, jOrig, iDest, jDest, tabuleiroAuxiliar);
							
							if ( tabuleiroAuxiliar[iDest][jDest] instanceof Peao)
							{
								((Peao)tabuleiroAuxiliar[iDest][jDest]).setPrimeiroMov(primMov);
							}
						}
						catch (MovIlegalExcecao e)
						{
							return false;
						}
						
						//caso o rei que estava em xeque deixe de estar apos esse movimento, funcao retorna true
						Peca rei = tabuleiroAuxiliar[xRei][yRei];
						if ( ! ((Rei)rei).xeque(xRei,yRei,tabuleiro,rei.getCor()) )
						{
							return true;
						}
					}
				}
			}
		}
		return false;
	}
}

