package com.github.daniel.resumob3.application;

import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        // Garante que o Swing seja executado na Event Dispatch Thread (EDT)
        if (SwingUtilities.isEventDispatchThread()) {
            // Se já estamos na EDT, executa diretamente
            executarAplicacao();
        } else {
            // Se não estamos na EDT, executa na EDT e aguarda
            try {
                SwingUtilities.invokeAndWait(Main::executarAplicacao);
            } catch (Exception e) {
                System.err.println("Erro ao inicializar interface gráfica: " + e.getMessage());
                e.printStackTrace();
                System.exit(1);
            }
        }
    }

    private static void executarAplicacao() {
        try {
            EfetivaResumoNegociacoes.efetivaOperacao();
        } catch (Exception e) {
            System.err.println("Erro ao executar aplicação: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    }
}

