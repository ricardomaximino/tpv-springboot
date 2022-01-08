package com.brasajava.view.principal;

import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import java.awt.BorderLayout;
import javax.swing.ImageIcon;

/**
 * Esta classe implementa la interfaz com.brasajava.view.MainFrame y extende
 * javax.swing.JFrame, esta classe representa el frame principal de la 
 * aplicacion.
 * @author Ricardo Maximino
 */
public class MainFrame extends javax.swing.JFrame {
    private JDesktopPane desktopPane;
    

    /**
     * Constructor sin argumentos para instanciar esta classe.
     */
    public MainFrame() {
        iniciar();
    }

    /**
     * Retorna el valor de la variable global desktopPane.
     * @return del tipo javax.swing.JDesktopPane.
     */
    public JDesktopPane getDesktopPane() {
        return desktopPane;
    }



    private void iniciar() {
        desktopPane = new JDesktopPane();
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        this.setSize(600,600);
        this.setLocation(50, 30);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setIconImage(new ImageIcon(this.getClass().getResource("/images/icon.png")).getImage());
        this.add(desktopPane,BorderLayout.CENTER);
        this.setVisible(false);
        pack();
    }
}
