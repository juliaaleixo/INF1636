package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import dao.SalvamentoDao;
import model.Jogo;
import view.TelaInicial;

public class TelaInicialController 
{
	TelaInicial telaIni;
	
	public TelaInicialController()
	{
		telaIni = new TelaInicial();
		
		telaIni.novoJogo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				novoJogo();
			}
		});

		telaIni.carregarJogo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				carregarJogo();
			}
		});
		
	}
	private void novoJogo()
	{
		Facade.getInstance().iniciaJogo();
	}
	private void carregarJogo()
	{
		Facade.getInstance().iniciaJogoCarregado();
	}
	
	public void fecharTela()
	{
		telaIni.setVisible(false);
		telaIni.dispose();
	}

}
