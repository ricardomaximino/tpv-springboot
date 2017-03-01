package com.brasajava.view.tpv;

import com.brasajava.model.Producto;
import com.brasajava.model.Venta;
import java.util.HashMap;
import java.util.Map;

/**
 *  Esta clase el para ayudar a controlar el posicionamento del scrollPanel
 * en la clase TPV y para evitar que añadindo un mismo producto se crie una nueva
 * venta en lugar de incrementar la cantidad en la venta yá existente.
 * @author Ricardo Maximino
 */
public class CuentaMap {
    private Map<Producto, Venta> map;
    private Map<Venta, Integer> pointMap;

    /**
     * Único constructor para instanciar esta clase.
     */
    public CuentaMap() {
        map = new HashMap<>();
        pointMap = new HashMap<>();
    }

    /**
     * Retorna un mapa de venta que lleva como llave un producto para que 
     * cuando se añada un producto caso ya exista en la cuenta que simplesmente
     * se incremente la cantidad.
     * @return del tipo java.util.Map&lt;Producto,Venta&gt;.
     */
    public Map<Producto, Venta> getMap() {
        return map;
    }

    /**
     * Retorna un map de Integer que lleva como llave una venta para que se registre
     * el posicionamento del scrolPane en cada venta que se crie.
     * @return del tipo java.util.Map&lt;Venta, Integer&gt;.
     */
    public Map<Venta, Integer> getPointMap() {
        return pointMap;
    }
}
