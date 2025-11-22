package com.github.daniel.resumob3.application;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;

public class ExploradorDeArquivosSwing {
    
    public File selecionarArquivo(String titulo, String descricao, String extensao) {
        // Garante que o Look and Feel do sistema seja usado
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            // Se não conseguir definir, continua com o padrão
            System.err.println("Aviso: Não foi possível definir o Look and Feel do sistema.");
        }

        // Cria o JFileChooser
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle(titulo);
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

        FileNameExtensionFilter filter = new FileNameExtensionFilter(descricao, extensao);
        fileChooser.setFileFilter(filter);

        // Mostra o diálogo de forma modal
        int retorno = fileChooser.showOpenDialog(null);

        if (retorno == JFileChooser.APPROVE_OPTION) {
            return fileChooser.getSelectedFile();
        } else {
            return null;
        }
    }
}
