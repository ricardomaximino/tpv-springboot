package com.brasajava.view.producto;

import com.brasajava.model.Grupo;
import com.brasajava.model.Producto;
import com.brasajava.util.PrototypeContext;
import com.brasajava.view.principal.MainFrame;
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 *
 * @author Ricardo
 */
@Component
public class ShowProductoGrupoCommand {

    private final PrototypeContext prototypeContext;
    private JInternalFrame frame;
    private final JDesktopPane desktopPane;

    public ShowProductoGrupoCommand(ApplicationContext context) {
        this.prototypeContext = context.getBean(PrototypeContext.class);
        this.desktopPane = context.getBean(MainFrame.class).getDesktopPane();
    }

    public void show(Object obj) {

        //Producto
        if (obj instanceof Producto) {
            producto(obj);
        } else if (obj instanceof Grupo) {
            grupo(obj);
        }

        if (frame != null) {
            frame.setVisible(true);
        }
        desktopPane.setSelectedFrame(frame);
        desktopPane.getDesktopManager().deiconifyFrame(frame);
        desktopPane.getDesktopManager().activateFrame(frame);
        if (frame != null) {
            frame.moveToFront();
        }
    }

    private void producto(Object obj) {
        Producto producto = (Producto) obj;
        ProductoView frame = prototypeContext.putProducto(producto);
        frame.setProducto(producto);
        this.frame = frame;
    }

    private void grupo(Object obj) {
        Grupo grupo = (Grupo) obj;
        GrupoView frame = prototypeContext.putGrupo(grupo);
        frame.setGrupo(grupo);
        this.frame = frame;
    }
}
