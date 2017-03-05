package com.brasajava.view.menu;

import com.brasajava.view.principal.MainFrame;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import org.springframework.stereotype.Component;

/**
 * Esta clase auxilia el JDesktopPane a organizar y cerrar los JInternalFrames. 
 * @author Ricardo Maximino
 */
@Component
public class DesktopController {

    private final JDesktopPane desktopPane;
    private int x;
    private int y;

    /**
     * Único Contructor para instanciar esta clase.
     *
     * @param mainFrame del tipo com.brasajava.view.MainFrame.
     */
    public DesktopController(MainFrame mainFrame) {
        this.desktopPane = mainFrame.getDesktopPane();

    }

    /**
     * Cierra todos los JInternalFrames del JDesktopPane.
     */
    public void cerrarTodosInternalFrames() {
        for (JInternalFrame frame : desktopPane.getAllFrames()) {
            frame.dispose();
        }
    }

    /**
     * Cierra el JInternalFrame selecionado.
     */
    public void cerrarSelectedInternalFrame() {
        JInternalFrame frame = desktopPane.getSelectedFrame();
        if(frame != null){
            frame.dispose();
        }
    }

    /**
     * Organiza los JInternalFrames en el estilo cascada.
     */
    public void cascada() {
        restartXY();
        List<JInternalFrame> list = new ArrayList<>();
        for (JInternalFrame frame : desktopPane.getAllFrames()) {
            list.add(frame);
            //frame.setLocation(getPositionX(), getPositionY());
        }
        for(int i = list.size();i>0;i--){
            list.get(i-1).setLocation(getPositionX(), getPositionY());
        }
    }

    private int getPositionX() {
        x += 30;
        if (desktopPane.getWidth() - 800 < x) {
            x = 0;
        }
        return x;
    }

    private int getPositionY() {
        y += 30;
        if (this.desktopPane.getHeight() - 120 < y) {
            y = 0;
        }
        return y;
    }

    private void restartXY() {
        x = y = 0;
    }
}
