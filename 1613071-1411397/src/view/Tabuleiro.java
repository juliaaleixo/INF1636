package view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import model.Bispo;
import model.Cavalo;
import model.Cor;
import model.Jogo;
import model.Peao;
import model.Peca;
import model.Rainha;
import model.Rei;
import model.Torre;

public class Tabuleiro extends JPanel implements Observer 
{
	public Jogo jogo;
	Boolean posicoesPossiveis[][] = null;

	BufferedImage imagemPeaoBranco, imagemPeaoPreto;
	BufferedImage imagemCavaloBranco, imagemCavaloPreto;
	BufferedImage imagemBispoBranco, imagemBispoPreto;
	BufferedImage imagemRainhaBranca, imagemRainhaPreta;
	BufferedImage imagemReiBranco, imagemReiPreto;
	BufferedImage imagemTorreBranca, imagemTorrePreta;

	/**
	 * Carrega as imagens das pecas
	 * @param jogo: instancia do jogo
	 */
	public Tabuleiro (Jogo jogo) 
	{
		this.jogo = jogo;

		try 
		{
			imagemPeaoBranco = ImageIO.read(new File("Pecas/b_peao.png"));
			imagemPeaoPreto = ImageIO.read(new File("Pecas/p_peao.png"));
			imagemCavaloBranco = ImageIO.read(new File("Pecas/b_cavalo.png"));
			imagemCavaloPreto = ImageIO.read(new File("Pecas/p_cavalo.png"));
			imagemBispoBranco = ImageIO.read(new File("Pecas/b_bispo.png"));
			imagemBispoPreto = ImageIO.read(new File("Pecas/p_bispo.png"));
			imagemRainhaBranca = ImageIO.read(new File("Pecas/b_dama.png"));
			imagemRainhaPreta = ImageIO.read(new File("Pecas/p_dama.png"));
			imagemReiBranco = ImageIO.read(new File("Pecas/b_rei.png"));
			imagemReiPreto = ImageIO.read(new File("Pecas/p_rei.png"));
			imagemTorreBranca = ImageIO.read(new File("Pecas/b_torre.png"));
			imagemTorrePreta = ImageIO.read(new File("Pecas/p_torre.png"));
		} 
		catch (IOException e) 
		{
			System.out.println(e.getMessage());
			System.exit(1);
		}
	}
	/**
	 * Pinta as casas do tabuleiro, e insere as imagens das pecas.
	 * Caso exista alguma posicao possivel da peca (quando e o primeiro clique),
	 * ilumina essas casas.
	 */
	public void paintComponent(Graphics g) 
	{
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;

		int tamanho = 50;

		// criando as casas do tabuleiro
		for (int i = 0; i < 8; i++) 
		{
			for (int j = 0; j < 8; j++) 
			{
				Rectangle2D casa = new Rectangle2D.Double (i * tamanho, j * tamanho, tamanho, tamanho);

				if ((i + j) % 2 == 1) 
				{
					g2d.setPaint(Color.BLACK);
				} 
				else 
				{
					g2d.setPaint(Color.WHITE);
				}
				g2d.fill(casa);
			}
		}

		// iluminando as casas de movimentos possiveis
		if (posicoesPossiveis != null) 
		{
			for (int i = 0; i < 8; i++) 
			{
				for (int j = 0; j < 8; j++) 
				{
					if (posicoesPossiveis[i][j] == true) 
					{
						Rectangle2D contorno = new Rectangle2D.Double( i * tamanho, (7 - j) * tamanho, tamanho, tamanho);

						g2d.setStroke(new BasicStroke(3));
						g2d.setPaint(Color.green);
						g2d.draw(contorno);
					}
				}
			}
		}

		// inserindo as imagens das peças
		for (int i = 0; i < 8; i++) 
		{
			for (int j = 0; j < 8; j++) 
			{
				Peca p = jogo.getPeca(i, j);
				
				if (p instanceof Peao) 
				{
					if (p.getCor() == Cor.preto) 
					{
						g.drawImage(imagemPeaoPreto, i * tamanho, (7 - j) * tamanho, tamanho, tamanho, null);
					} 
					else 
					{
						g.drawImage(imagemPeaoBranco, i * tamanho, (7 - j) * tamanho, tamanho, tamanho, null);
					}
				}
				if (p instanceof Torre) 
				{
					if (p.getCor() == Cor.preto) 
					{
						g.drawImage(imagemTorrePreta, i * tamanho, (7 - j) * tamanho, tamanho, tamanho, null);
					} 
					else 
					{
						g.drawImage(imagemTorreBranca, i * tamanho, (7 - j) * tamanho, tamanho, tamanho, null);
					}
				}
				if (p instanceof Bispo) 
				{
					if (p.getCor() == Cor.preto)
					{
						g.drawImage(imagemBispoPreto, i * tamanho, (7 - j) * tamanho, tamanho, tamanho, null);
					}
					else 
					{
						g.drawImage(imagemBispoBranco, i * tamanho, (7 - j) * tamanho, tamanho, tamanho, null);
					}
				}
				if (p instanceof Rei) 
				{
					if (p.getCor() == Cor.preto)
					{
						g.drawImage(imagemReiPreto, i * tamanho, (7 - j) * tamanho, tamanho, tamanho, null);
					} 
					else 
					{
						g.drawImage(imagemReiBranco, i * tamanho, (7 - j) * tamanho, tamanho, tamanho, null);
					}
				}
				if (p instanceof Rainha) 
				{
					if (p.getCor() == Cor.preto) 
					{
						g.drawImage(imagemRainhaPreta, i * tamanho, (7 - j) * tamanho, tamanho, tamanho, null);
					} 
					else
					{
						g.drawImage(imagemRainhaBranca, i * tamanho, (7 - j) * tamanho, tamanho, tamanho, null);
					}
				}
				if (p instanceof Cavalo) 
				{
					if (p.getCor() == Cor.preto)
					{
						g.drawImage(imagemCavaloPreto, i * tamanho, (7 - j) * tamanho, tamanho, tamanho, null);
					} 
					else 
					{
						g.drawImage(imagemCavaloBranco, i * tamanho, (7 - j) * tamanho, tamanho, tamanho, null);
					}
				}
			}
		}
	}
	/**
	 * @param posicoesPossiveis: matriz de booleanos com as posicoes possiveis de uma peca
	 */
	public void setPosicoesPossiveis(Boolean[][] posicoesPossiveis)
	{
		this.posicoesPossiveis = posicoesPossiveis;
		repaint();
	}
	/**
	 * Atualiza o tabuleiro, repintando-o.
	 */
	public void update(Observable o, Object arg) 
	{
		if (((ArrayList<Object>) arg).get(0).equals("repaint")) 
		{
			repaint();
		}
	}
}
