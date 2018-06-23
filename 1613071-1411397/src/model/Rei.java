package model;

public class Rei extends Peca
{
	private static final long serialVersionUID = 1L;
	Boolean movimentou = false;
	
	public Rei (Cor cor)
	{
		super(cor);
	}
	
	public Boolean getMovimentou()
	{
		return movimentou;
	}
	
	public void setMovimentou (Boolean mov)
	{
		movimentou = mov;
	}
	/**
	 * Chama funcao de movimento do rei, caso seja possivel.
	 * Caso contrario, levanta uma excecao do tipo MovIlegalExcecao
	 */
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
			
			movimentoRealizado();
		}
		else
		{
			String str = "Proibido mover rei para essa localizacao";
			throw new MovIlegalExcecao(str);
		}
	}
	/**
	 * Verifica se existe um caminho livre entre as coordenadas de origem e destino, levando em consideracao
	 * as regras dos movimentos do rei.
	 */
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
	/**
	 * Verifica se rei no seu destino desejado ficaria sobre ataque. 
	 * @param xDest: coordenada x de destino
	 * @param yDest: coordenada y de destino
	 * @param tabuleiro: tabuleiro atual
	 * @param cor: cor do rei
	 * @return
	 */
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
	/**
	 * Verifica condicoes para roque. Caso seja permitido, retorna true
	 * @param xOrig: coordenada x de origem
	 * @param yOrig: coordenada y de origem
	 * @param xDest: coordenada x de destino
	 * @param yDest: coordenada y de destino
	 * @param tabuleiro: tabuleiro atual
	 * @return
	 */
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
	/**
	 * Verifica se rei esta em xeque.
	 * @param xOrig: coordenada x de origem do rei
	 * @param yOrig: coordenada y de origem do rei
	 * @param tabuleiro: tabuleiro atual
	 * @param cor: cor do rei
	 * @return
	 */
	public boolean xeque (int xOrig, int yOrig, Peca[][] tabuleiro, Cor cor)
	{
		//verifica se posicao atual do rei esta sobre ataque
		if ( reiSobreAtaque(xOrig, yOrig, tabuleiro, cor) )
		{
			return true;
		}
		return false;
	}
	/**
	 * Verifica se o rei esta em xeque mate.
	 * @param xOrig: coordenada x de origem do rei
	 * @param yOrig: coordenada y de origem do rei
	 * @param tabuleiro: tabuleiro atual
	 * @param cor: cor do rei
	 * @return
	 */
	public boolean xequeMate (int xOrig, int yOrig, Peca[][] tabuleiro, Cor cor)
	{
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
			
			//se rei pode ser protegido, nao esta em xeque mate
			if ( protegerRei(xOrig,yOrig,tabuleiro) )
			{
				return false;
			}
			return true;
		}
		return false;
	}
	/**
	 * Verifica se existe algum movimento de alguma peca que possa proteger o rei (tira-lo do xeque).
	 * @param xRei: coordenada x do rei
	 * @param yRei: coordenada y do rei
	 * @param tabuleiro: tabuleiro atual
	 * @return
	 */
	public boolean protegerRei(int xRei, int yRei, Peca[][] tabuleiro)
	{
		Peca[][] tabuleiroAuxiliar = new Peca[8][8]; 
		
		
		
		for ( int iOrig = 0; iOrig < 8; iOrig++ )
		{
			for ( int jOrig = 0; jOrig < 8; jOrig++ )
			{
				for ( int iDest = 0; iDest < 8; iDest++  )
				{
					for ( int jDest = 0; jDest < 8; jDest++ )
					{
						
						for (int i = 0; i< 8; i++) 
						{
							for (int j = 0; j< 8; j++) 
							{
								tabuleiroAuxiliar[i][j] = tabuleiro[i][j];
							}
						}
						
						if (tabuleiroAuxiliar[iOrig][jOrig] == null) 
						{
							continue;
						}
						boolean primMov = false; 
						boolean movimentouRei = false;
						boolean movimentouTorre = false;
						
						//gravar se o movimento do peao foi o primeiro para depois restaura-lo
						if ( tabuleiroAuxiliar[iOrig][jOrig] instanceof Peao )
						{
							if ( jDest == 7 || jDest == 0 )
							{
								Peca rei = tabuleiroAuxiliar[xRei][yRei];
								Cor cor = tabuleiroAuxiliar[iOrig][jOrig].getCor();
								
								if(!tabuleiro[iOrig][jOrig].caminhoLivre(iOrig, jOrig, iDest, jDest, tabuleiroAuxiliar))
								{
									continue;
								}
								tabuleiroAuxiliar[iOrig][jOrig] = null;
								tabuleiroAuxiliar[iDest][jDest] = new Rainha(cor);
								if ( ! ((Rei)rei).xeque(xRei,yRei,tabuleiroAuxiliar,rei.getCor()) )
								{
									return true;
								}
								
								tabuleiroAuxiliar[iDest][jDest] = new Cavalo(cor);
								if ( ! ((Rei)rei).xeque(xRei,yRei,tabuleiroAuxiliar,rei.getCor()) )
								{
									return true;
								}
								
								tabuleiroAuxiliar[iDest][jDest] = new Bispo(cor);
								if ( ! ((Rei)rei).xeque(xRei,yRei,tabuleiroAuxiliar,rei.getCor()) )
								{
									return true;
								}
								
								tabuleiroAuxiliar[iDest][jDest] = new Torre(cor);
								if ( ! ((Rei)rei).xeque(xRei,yRei,tabuleiroAuxiliar,rei.getCor()) )
								{
									return true;
								}
								continue;
							}
							else
							{
								primMov = ((Peao)tabuleiroAuxiliar[iOrig][jOrig]).getPrimeiroMov();
								try 
								{
									tabuleiroAuxiliar[iOrig][jOrig].movimento(iOrig, jOrig, iDest, jDest, tabuleiroAuxiliar);
								}
								catch (MovIlegalExcecao e)
								{
									continue;
								}
								((Peao)tabuleiroAuxiliar[iDest][jDest]).setPrimeiroMov(primMov);
							}
						}
						else if (tabuleiroAuxiliar[iOrig][jOrig] instanceof Torre) 
						{
							Peca p = tabuleiroAuxiliar[iOrig][jOrig];
							movimentouTorre = ((Torre)p).getMovimentou();
							try
							{
								p.movimento(iOrig, jOrig, iDest, jDest, tabuleiroAuxiliar);
								((Torre)p).setMovimentou(movimentouTorre);
							} 
							catch (MovIlegalExcecao e) 
							{
								((Torre)p).setMovimentou(movimentouTorre);
								continue;
							}
						}
						else
						{
							Peca rei = tabuleiroAuxiliar[xRei][yRei];
							try 
							{
								//caso seja instancia do rei, precisa restaurar caso ele ja tinha feito algum movimento
								if( tabuleiroAuxiliar[iOrig][jOrig] instanceof Rei )
								{
									movimentouRei = ((Rei)tabuleiroAuxiliar[iOrig][jOrig]).getMovimentou();
									
									//o movimento do roque depende se a torre tambem se movimentou
									if ( iDest == iOrig + 2 && jOrig == jDest)
									{
										Peca p = tabuleiroAuxiliar[7][jOrig];
										if ( p instanceof Torre )
										{
											movimentouTorre = ((Torre)p).getMovimentou();
											if ( ((Rei)tabuleiroAuxiliar[iOrig][jOrig]).roquePermitido(iOrig, jOrig, iDest, jDest, tabuleiroAuxiliar) )
											{
												tabuleiroAuxiliar[iOrig][jOrig].movimento(iOrig, jOrig, iDest, jDest, tabuleiroAuxiliar);
												((Rei)tabuleiroAuxiliar[iDest][jDest]).setMovimentou(movimentouRei);
												((Torre)tabuleiroAuxiliar[5][jDest]).setMovimentou(movimentouTorre);
											}
											else
											{
												tabuleiroAuxiliar[iOrig][jOrig].movimento(iOrig, jOrig, iDest, jDest, tabuleiroAuxiliar);
												((Rei)tabuleiroAuxiliar[iDest][jDest]).setMovimentou(movimentouRei);
											}
										}
										else
										{
											tabuleiroAuxiliar[iOrig][jOrig].movimento(iOrig, jOrig, iDest, jDest, tabuleiroAuxiliar);
											((Rei)tabuleiroAuxiliar[iDest][jDest]).setMovimentou(movimentouRei);
										}
									}
									else if ( iDest == iOrig - 2 && jOrig == jDest)
									{
										Peca p = tabuleiroAuxiliar[0][jOrig];
										if ( p instanceof Torre )
										{
											movimentouTorre = ((Torre)p).getMovimentou();
											if ( ((Rei)tabuleiroAuxiliar[iOrig][jOrig]).roquePermitido(iOrig, jOrig, iDest, jDest, tabuleiroAuxiliar) )
											{
												tabuleiroAuxiliar[iOrig][jOrig].movimento(iOrig, jOrig, iDest, jDest, tabuleiroAuxiliar);
												((Rei)tabuleiroAuxiliar[iDest][jDest]).setMovimentou(movimentouRei);
												((Torre)tabuleiroAuxiliar[3][iDest]).setMovimentou(movimentouTorre);
											}
											else
											{
												tabuleiroAuxiliar[iOrig][jOrig].movimento(iOrig, jOrig, iDest, jDest, tabuleiroAuxiliar);
												((Rei)tabuleiroAuxiliar[iDest][jDest]).setMovimentou(movimentouRei);
											}
										}
										else
										{
											tabuleiroAuxiliar[iOrig][jOrig].movimento(iOrig, jOrig, iDest, jDest, tabuleiroAuxiliar);
											((Rei)tabuleiroAuxiliar[iDest][jDest]).setMovimentou(movimentouRei);
										}
									}
									else 
									{
										tabuleiroAuxiliar[iOrig][jOrig].movimento(iOrig, jOrig, iDest, jDest, tabuleiroAuxiliar);
										((Rei)tabuleiroAuxiliar[iDest][jDest]).setMovimentou(movimentouRei);
									}
									
								}
								else
								{
									tabuleiroAuxiliar[iOrig][jOrig].movimento(iOrig, jOrig, iDest, jDest, tabuleiroAuxiliar);
								}
								// Se foi o rei que se moveu
								if (tabuleiroAuxiliar[iDest][jDest] == rei) 
								{ 
									if ( ! ((Rei)rei).xeque(iDest,jDest,tabuleiroAuxiliar,rei.getCor()) )
									{
										return true;
									}
									else 
									{
										continue;
									}
								}
							}
							catch (MovIlegalExcecao e)
							{
								continue;
							}
						}
					}
					//caso o rei que estava em xeque deixe de estar apos esse movimento, funcao retorna true
					Peca rei = tabuleiroAuxiliar[xRei][yRei];
					if (!(rei instanceof Rei)) 
					{
						continue;
					}
					if ( ! ((Rei)rei).xeque(xRei,yRei,tabuleiroAuxiliar,rei.getCor()) )
					{
						return true;
					}
				}
			}
		}
		return false;
	}
}

