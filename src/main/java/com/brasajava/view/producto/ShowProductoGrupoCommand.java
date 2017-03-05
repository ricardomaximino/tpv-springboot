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
 * Esta clase centraliza la responsabilidade de apresentar todos los frames
 * (que son prototype) de producto y grupo en el mainFrame y registrar las instancias
 * en el prototypeContext para que la applicacion tenga controle de todas las
 * instancias ya que spring no controla las instancias prototype.
 * @author Ricardo Maximino
 */
@Component
public class ShowProductoGrupoCommand {

    private final PrototypeContext prototypeContext;
    private JInternalFrame frame;
    private final JDesktopPane desktopPane;

    /**
     * Único constructor para crear una instancia de esta clase.
     * @param context del tipo org.springframework.context.ApplicationContext.
     * <p>Utilizando el context se pedirá una instancia de las clase:</p>
     * <ul>
     * <li>com.brasajava.util.PrototypeContext</li>
     * <li>com.brasajava.view.principal.MainFrame</li>
     * </ul> 
     */
    public ShowProductoGrupoCommand(ApplicationContext context) {
        this.prototypeContext = context.getBean(PrototypeContext.class);
        this.desktopPane = context.getBean(MainFrame.class).getDesktopPane();
    }

    /**
     * Este metodo es el responsable de crear el frame adecuado e apresentar el objeto
     * pasado por parametro en el mainFrame.
     * @param obj del tipo java.lang.Object se espero o una instancia de la 
     * clase com.brasajava.model.Producto o de la com.brasajava.model.Grupo.
     * <p>Esse metodo pide al spring una instancia del frame adecuado
     * lo configura con el objeto pasado por parameto y registra el frame
     * en el prototyeContext.</p>
     */
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
        this.frame = frame;
    }

    private void grupo(Object obj) {
        Grupo grupo = (Grupo) obj;
        GrupoView frame = prototypeContext.putGrupo(grupo);
        this.frame = frame;
    }
}
