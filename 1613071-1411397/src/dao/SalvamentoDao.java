package dao;

import java.awt.Component;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;


// singleton
public class SalvamentoDao {
	
	private static SalvamentoDao instance = null;
	
	private SalvamentoDao () {
	}
	
	public static SalvamentoDao getInstance() {
		if(instance == null) {
			instance = new SalvamentoDao();
		}
		
		return instance;
	}
	/**
	 * Salva jogo atual
	 * @param objeto
	 * @param frame
	 */
	public void salvarJogo(Object objeto, Component frame) {

		String nomeArquivo = null;
		File dir = null;
		
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("Arquivo de xadrez","txt"));
		
		int result = fileChooser.showSaveDialog(frame);
		
		if (result == JFileChooser.CANCEL_OPTION) {
			return;
		}
		if (result == JFileChooser.APPROVE_OPTION) {
			nomeArquivo = fileChooser.getSelectedFile().getName();
			dir = fileChooser.getCurrentDirectory();
		}
		
		if (!nomeArquivo.endsWith(".txt")) {
			if(nomeArquivo.contains(".")) {
				JOptionPane.showMessageDialog(frame, "Caracter invalido", "Nao foi possivel salvar", JOptionPane.WARNING_MESSAGE);
				return;
			}
			nomeArquivo = nomeArquivo + ".txt";
		}
		File outputFile = new File(dir, nomeArquivo);
    		final String caminho = dir + nomeArquivo;
    	
        try {
        	
        	FileOutputStream arquivo = new FileOutputStream(outputFile);
        	ObjectOutputStream stream = new ObjectOutputStream(arquivo);
        	
        	//salvo o objeto
        	stream.writeObject(objeto);

        	stream.close();

        } catch (Exception exc) {
        	exc.printStackTrace();
        }
        
	}
	/**
	 * Carrega um jogo ja salvo.
	 * @param frame
	 * @return
	 */
	public Object carregarJogo(Component frame) {

		Object obj = null;
		String nomeArquivo = null;
		File dir = null;
		
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("Arquivo de xadrez","txt"));
		
		int result = fileChooser.showOpenDialog(frame);
		
		if (result == JFileChooser.CANCEL_OPTION) {
			return null;
		}
		if (result == JFileChooser.APPROVE_OPTION) {
			nomeArquivo = fileChooser.getSelectedFile().getName();
			dir = fileChooser.getCurrentDirectory();
		}
		
		if (!nomeArquivo.endsWith(".txt")) {
			JOptionPane.showMessageDialog(frame, "Nao foi possivel abrir o arquivo.", "Erro ao abrir arquivo de xadrez", JOptionPane.ERROR_MESSAGE);
			return null;
		}
		
		File outputFile = new File(dir, nomeArquivo);
    	
        try {
        	
        	FileInputStream arquivo = new FileInputStream(outputFile);
        	ObjectInputStream stream = new ObjectInputStream(arquivo);
        	
        	//Deserializo o objeto
        	obj = stream.readObject();

        	stream.close();

        } catch (Exception exc) {
        	JOptionPane.showMessageDialog(frame, "Nao foi possivel abrir o arquivo.", "Erro ao abrir arquivo de xadrez", JOptionPane.ERROR_MESSAGE);
        	exc.printStackTrace();
        }
        
        return obj;
        
	}
	
}
