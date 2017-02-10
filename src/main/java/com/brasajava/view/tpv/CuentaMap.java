package com.brasajava.view.tpv;

import com.brasajava.model.Producto;
import com.brasajava.model.Venta;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Ricardo
 */
public class CuentaMap {
    private Map<Producto, Venta> map;
    private Map<Venta, Integer> pointMap;

    public CuentaMap() {
        map = new HashMap<>();
        pointMap = new HashMap<>();
    }

    public Map<Producto, Venta> getMap() {
        return map;
    }

    public Map<Venta, Integer> getPointMap() {
        return pointMap;
    }
}
