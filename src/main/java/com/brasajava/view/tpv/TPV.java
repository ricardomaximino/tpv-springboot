/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.brasajava.view.tpv;

import com.brasajava.model.Cliente;
import com.brasajava.model.Cuenta;
import com.brasajava.model.Factura;
import com.brasajava.model.Grupo;
import com.brasajava.model.Persona;
import com.brasajava.model.Producto;
import com.brasajava.model.Usuario;
import com.brasajava.model.Venta;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.table.TableModel;

/**
 *
 * @author Ricardo
 */
public class TPV extends javax.swing.JFrame {

    private int ventas = 1;
    private int cantidad = 1;
    
    private Map<String, Producto> productos;
    private Map<String, Grupo> grupos;
    private Map<String, Cuenta> cuentas;

    private int generoHeight = 0;
    private final int generoXPosition = 0;
    private int generoYPosition = 0;
    private final List<JComponent> generoComponentList;
    private int generoCount = 0;

    private int productoHeight = 0;
    private int productoWidth = 0;
    private int productoXPosition = 0;
    private int productoYPosition = 0;
    private final List<JComponent> productoComponentList;
    private int productoCount = 0;

    private final int cuentaHeight = 0;
    private int cuentaXPosition = 0;
    private int cuentaYPosition = 0;
    private final List<JComponent> cuentaComponentList;
    private int cuentaCount = 0;

    /**
     * Creates new form TPV
     */
    public TPV() {
        generoComponentList = new ArrayList<>();
        productoComponentList = new ArrayList<>();
        cuentaComponentList = new ArrayList<>();
        productos = new HashMap<>();
        grupos = new HashMap<>();
        initComponents();
        setColumnModel();
        GeneroModel miModel = new GeneroModel();
        miModel.setCuentaPanel(panelCuenta);
        miModel.setGrupoPanel(panelGenero);
        miModel.setProductoPanel(panelProducto);
        miModel.setLabelTotal(lblTotalValue);
        miModel.setModel((CajaTableModel)tabla.getModel());
        miModel.setGrupoList(createGruops());
        miModel.crear();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton32 = new javax.swing.JButton();
        panelToolBar = new javax.swing.JPanel();
        jButton2 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        scrollCuenta = new javax.swing.JScrollPane();
        panelCuenta = new javax.swing.JPanel();
        scrollGenero = new javax.swing.JScrollPane();
        panelGenero = new javax.swing.JPanel();
        panelCaja = new javax.swing.JPanel();
        panelTeclado = new javax.swing.JPanel();
        btn1 = new javax.swing.JButton();
        btn2 = new javax.swing.JButton();
        btn3 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        btn4 = new javax.swing.JButton();
        btn5 = new javax.swing.JButton();
        btn6 = new javax.swing.JButton();
        btnMas = new javax.swing.JButton();
        btn7 = new javax.swing.JButton();
        btn8 = new javax.swing.JButton();
        btn9 = new javax.swing.JButton();
        btnMenos = new javax.swing.JButton();
        btn0 = new javax.swing.JButton();
        btnComa = new javax.swing.JButton();
        btnBorrar = new javax.swing.JButton();
        btnPagar = new javax.swing.JButton();
        btnPagarRapido = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        lblTotalLabel = new javax.swing.JLabel();
        lblTotalValue = new javax.swing.JLabel();
        scrollTabla = new javax.swing.JScrollPane();
        tabla = new javax.swing.JTable();
        scrollProducto = new javax.swing.JScrollPane();
        panelProducto = new javax.swing.JPanel();

        jButton32.setText("jButton32");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        panelToolBar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jButton2.setText("Adiciona Producto");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton1.setText("Adiciona Genero");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton3.setText("Adicionar Cuenta");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setText("Adicionar Venta");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton5.setText("Automatico");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelToolBarLayout = new javax.swing.GroupLayout(panelToolBar);
        panelToolBar.setLayout(panelToolBarLayout);
        panelToolBarLayout.setHorizontalGroup(
            panelToolBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelToolBarLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton5)
                .addGap(565, 565, 565))
        );
        panelToolBarLayout.setVerticalGroup(
            panelToolBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelToolBarLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelToolBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2)
                    .addComponent(jButton1)
                    .addComponent(jButton3)
                    .addComponent(jButton4)
                    .addComponent(jButton5))
                .addContainerGap(66, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout panelCuentaLayout = new javax.swing.GroupLayout(panelCuenta);
        panelCuenta.setLayout(panelCuentaLayout);
        panelCuentaLayout.setHorizontalGroup(
            panelCuentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1114, Short.MAX_VALUE)
        );
        panelCuentaLayout.setVerticalGroup(
            panelCuentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 205, Short.MAX_VALUE)
        );

        scrollCuenta.setViewportView(panelCuenta);

        scrollGenero.setPreferredSize(new java.awt.Dimension(200, 100));

        panelGenero.setMaximumSize(new java.awt.Dimension(100, 32767));
        panelGenero.setPreferredSize(new java.awt.Dimension(200, 400));

        javax.swing.GroupLayout panelGeneroLayout = new javax.swing.GroupLayout(panelGenero);
        panelGenero.setLayout(panelGeneroLayout);
        panelGeneroLayout.setHorizontalGroup(
            panelGeneroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 204, Short.MAX_VALUE)
        );
        panelGeneroLayout.setVerticalGroup(
            panelGeneroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, Short.MAX_VALUE, Short.MAX_VALUE)
        );

        scrollGenero.setViewportView(panelGenero);

        panelCaja.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        panelTeclado.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        btn1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btn1.setText("1");
        btn1.setPreferredSize(new java.awt.Dimension(89, 23));

        btn2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btn2.setText("2");
        btn2.setPreferredSize(new java.awt.Dimension(89, 23));

        btn3.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btn3.setText("3");
        btn3.setPreferredSize(new java.awt.Dimension(89, 23));

        jButton7.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jButton7.setText("X");
        jButton7.setPreferredSize(new java.awt.Dimension(89, 23));

        btn4.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btn4.setText("4");

        btn5.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btn5.setText("5");

        btn6.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btn6.setText("6");

        btnMas.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btnMas.setText("+");

        btn7.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btn7.setText("7");

        btn8.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btn8.setText("8");

        btn9.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btn9.setText("9");

        btnMenos.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btnMenos.setText("-");

        btn0.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btn0.setText("0");

        btnComa.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btnComa.setText(",");

        btnBorrar.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnBorrar.setText("BORRAR");

        btnPagar.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnPagar.setText("PAGAR");

        btnPagarRapido.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnPagarRapido.setText("PAGAR RAPIDO");

        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        lblTotalLabel.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        lblTotalLabel.setText("TOTAL:");

        lblTotalValue.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        lblTotalValue.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblTotalValue.setText("0,00");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTotalLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblTotalValue, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblTotalLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblTotalValue, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout panelTecladoLayout = new javax.swing.GroupLayout(panelTeclado);
        panelTeclado.setLayout(panelTecladoLayout);
        panelTecladoLayout.setHorizontalGroup(
            panelTecladoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelTecladoLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(panelTecladoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(panelTecladoLayout.createSequentialGroup()
                        .addComponent(btn1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelTecladoLayout.createSequentialGroup()
                        .addComponent(btn4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnMas))
                    .addGroup(panelTecladoLayout.createSequentialGroup()
                        .addComponent(btn7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnMenos))
                    .addGroup(panelTecladoLayout.createSequentialGroup()
                        .addGroup(panelTecladoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnPagar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btn0, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelTecladoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(panelTecladoLayout.createSequentialGroup()
                                .addComponent(btnComa)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnBorrar))
                            .addComponent(btnPagarRapido, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)))))
        );

        panelTecladoLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btnBorrar, btnComa, btnMas, btnMenos, jButton7});

        panelTecladoLayout.setVerticalGroup(
            panelTecladoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelTecladoLayout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(panelTecladoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btn1, javax.swing.GroupLayout.DEFAULT_SIZE, 83, Short.MAX_VALUE)
                    .addGroup(panelTecladoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btn3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btn2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jButton7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelTecladoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn4)
                    .addComponent(btn5)
                    .addComponent(btn6)
                    .addComponent(btnMas))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelTecladoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn7)
                    .addComponent(btn8)
                    .addComponent(btn9)
                    .addComponent(btnMenos))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelTecladoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelTecladoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btn0)
                        .addComponent(btnComa))
                    .addComponent(btnBorrar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelTecladoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnPagar)
                    .addComponent(btnPagarRapido)))
        );

        panelTecladoLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btn0, btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btnBorrar, btnComa, btnMas, btnMenos, btnPagar, btnPagarRapido, jButton7});

        tabla.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        tabla.setModel(getModel());
        scrollTabla.setViewportView(tabla);

        javax.swing.GroupLayout panelCajaLayout = new javax.swing.GroupLayout(panelCaja);
        panelCaja.setLayout(panelCajaLayout);
        panelCajaLayout.setHorizontalGroup(
            panelCajaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelCajaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelCajaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(scrollTabla, javax.swing.GroupLayout.PREFERRED_SIZE, 378, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(panelTeclado, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        panelCajaLayout.setVerticalGroup(
            panelCajaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelCajaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(scrollTabla, javax.swing.GroupLayout.DEFAULT_SIZE, 80, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelTeclado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout panelProductoLayout = new javax.swing.GroupLayout(panelProducto);
        panelProducto.setLayout(panelProductoLayout);
        panelProductoLayout.setHorizontalGroup(
            panelProductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 510, Short.MAX_VALUE)
        );
        panelProductoLayout.setVerticalGroup(
            panelProductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 446, Short.MAX_VALUE)
        );

        scrollProducto.setViewportView(panelProducto);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelToolBar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(panelCaja, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(scrollProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(scrollGenero, javax.swing.GroupLayout.PREFERRED_SIZE, 223, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(scrollCuenta, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelToolBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(scrollProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addComponent(scrollGenero, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(scrollCuenta, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(panelCaja, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        JButton generoButton = new JButton(generoCount + "");
        generoButton.setSize(200, 200);
        generoComponentList.add(generoButton);
        reorganizaGenero();
        generoCount++;
    }//GEN-LAST:event_jButton1ActionPerformed

    private void reorganizaGenero() {
        generoYPosition = 0;
        generoHeight = 0;
        panelGenero.removeAll();

        for (Component c : generoComponentList) {
            c.setLocation(generoXPosition, generoYPosition);
            panelGenero.add(c);
            generoYPosition += c.getHeight();

            if (panelGenero.getHeight() < generoYPosition) {
                int w = panelGenero.getWidth();

                panelGenero.setMaximumSize(new java.awt.Dimension(200, 32767));
                panelGenero.setPreferredSize(new java.awt.Dimension(200, generoYPosition));

                GroupLayout panelLayout = (GroupLayout) panelGenero.getLayout();
                panelGenero.setLayout(panelLayout);
                panelLayout.setHorizontalGroup(
                        panelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGap(0, w, Short.MAX_VALUE)
                );
                panelLayout.setVerticalGroup(
                        panelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGap(0, generoYPosition, Short.MAX_VALUE)
                );
            }
        }
        panelGenero.repaint();
    }
    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        JButton b = new JButton(productoCount + "");
        b.setSize(200, 200);
        productoComponentList.add(b);
        reoganizaProducto();
        productoCount++;


    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        JButton b = new JButton(cuentaCount + "");
        b.setSize(200, 200);
        cuentaComponentList.add(b);
        reorganizaCuenta();
        cuentaCount++;
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        addVenta();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        generoComponentList.clear();
        for (Grupo g : createGruops()) {
            JButton generoButton = new JButton(g.getNombre());
            generoButton.setSize(200, 200);
            generoButton.addActionListener(this::generoAction);
            generoComponentList.add(generoButton);
            generoButton.setActionCommand(g.getNombre());
            grupos.put(g.getNombre(), g);
        }
        reorganizaGenero();
    }//GEN-LAST:event_jButton5ActionPerformed

    private void generoAction(ActionEvent e) {
        Grupo g = grupos.get(((JButton) e.getSource()).getActionCommand());
        productoComponentList.clear();
        for (Producto p : g.getProductos()) {
            JButton b = new JButton(p.getName());
            b.addActionListener(this::productoAction);
            b.setActionCommand(p.getName());
            b.setSize(200, 200);
            productoComponentList.add(b);
            reoganizaProducto();
            productos.put(p.getName(), p);
        }
    }
    private void productoAction(ActionEvent e){
        Producto p = productos.get(((JButton)e.getSource()).getActionCommand());
        CajaTableModel model = (CajaTableModel)tabla.getModel();
        Cuenta c = model.getCuenta();
        List<Venta> list = c.getVentas();
        Venta v = new Venta();
        v.setCuenta(c);
        v.setVenta(cantidad, p);
        list.add(v);
        
        BigDecimal total = new BigDecimal(0).setScale(2, BigDecimal.ROUND_HALF_DOWN);
        for (Venta venta : list) {
            total = total.add(venta.getTotal().setScale(2, BigDecimal.ROUND_HALF_DOWN));
        }
        lblTotalValue.setText(total.toString());
        model.fireTableDataChanged();
    }

    private void reorganizaCuenta() {
        cuentaYPosition = 0;
        cuentaXPosition = 0;
        panelCuenta.removeAll();

        for (Component c : cuentaComponentList) {
            c.setLocation(cuentaXPosition, cuentaYPosition);
            panelCuenta.add(c);
            cuentaXPosition += c.getWidth();

            if (panelCuenta.getHeight() < cuentaXPosition) {
                int h = panelCuenta.getHeight();

                panelCuenta.setMaximumSize(new Dimension(32767, 172));
                panelCuenta.setPreferredSize(new Dimension(cuentaXPosition, 172));

                GroupLayout panelLayout = (GroupLayout) panelCuenta.getLayout();
                panelCuenta.setLayout(panelLayout);
                panelLayout.setHorizontalGroup(
                        panelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGap(0, cuentaXPosition, Short.MAX_VALUE)
                );
                panelLayout.setVerticalGroup(
                        panelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGap(0, h, Short.MAX_VALUE)
                );
            }
        }
        panelCuenta.repaint();
    }

    private void reoganizaProducto() {
        productoXPosition = 0;
        productoYPosition = 0;
        productoHeight = 0;
        productoWidth = 0;
        panelProducto.removeAll();

        for (Component c : productoComponentList) {
            c.setLocation(productoXPosition, productoYPosition);
            panelProducto.add(c);

            productoXPosition += c.getWidth();

            if (panelProducto.getWidth() < productoXPosition + c.getWidth()) {
                productoYPosition += c.getHeight();
                productoXPosition = 0;
            }

            if (panelProducto.getHeight() < productoYPosition + c.getHeight()) {
                int w = panelProducto.getWidth();

                GroupLayout panelProductoLayout = (GroupLayout) panelProducto.getLayout();
                panelProducto.setLayout(panelProductoLayout);
                panelProductoLayout.setHorizontalGroup(
                        panelProductoLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGap(0, w, Short.MAX_VALUE)
                );
                panelProductoLayout.setVerticalGroup(
                        panelProductoLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGap(0, productoYPosition + c.getHeight(), Short.MAX_VALUE)
                );
            }
        }
        panelProducto.repaint();
    }

    private TableModel getModel() {
        TableModel model = new CajaTableModel();
        return model;
    }

    private void setColumnModel() {
        for (int i = 0; i < 4; i++) {
            switch (i) {
                case 0:
                    tabla.getColumnModel().getColumn(i).setPreferredWidth(5);
                    break;
                case 1:
                    tabla.getColumnModel().getColumn(i).setPreferredWidth(200);
                    break;
                case 2:
                    tabla.getColumnModel().getColumn(i).setPreferredWidth(20);
                    break;
                case 3:
                    tabla.getColumnModel().getColumn(i).setPreferredWidth(20);
                    break;
            }
        }

    }

    public void addVenta() {
        CajaTableModel model = (CajaTableModel) tabla.getModel();

        Persona usu = new Usuario();
        usu.setNombre("Ricardo Maximino");

        Persona cli = new Cliente();
        cli.setNombre("Africa");

        Producto p = new Producto();
        p.setActivo(true);
        p.setAlmacen(200);
        p.setCusto(BigDecimal.valueOf(0.95));
        p.setDescripcion("Bote de Coca Cola de 330ml");
        p.setId(1);
        p.setIva(20);
        p.setMargen(30);
        p.setName("Coca-Cola");
        p.setPrecioMasIva(new BigDecimal(1.73).setScale(2, BigDecimal.ROUND_HALF_DOWN));
        p.setPrecioSinIva(BigDecimal.valueOf(1.05));

        Grupo g = new Grupo();
        g.setDescripcion("Refrescos de todas las clases");
        g.setId(1);
        g.setNombre("Refrescos");
        g.getProductos().add(p);
        p.getGrupos().add(g);

        Factura f = new Factura();
        f.setAbierta(true);
        f.setId(1);
        f.setPagada(false);
        f.setUsuario(usu);
        f.setCliente(cli);

        Cuenta c = model.getCuenta();
        c.setFactura(f);
        c.setId(1);
        c.setName("Cuenta UNO");

        f.getCuentas().add(c);

        Venta v = new Venta();
        v.setVenta(ventas, p);
        v.setId(ventas);
        v.setCuenta(c);

        c.getVentas().add(v);

        BigDecimal total = new BigDecimal(0).setScale(2, BigDecimal.ROUND_HALF_DOWN);
        for (Venta venta : c.getVentas()) {
            total = total.add(venta.getTotal().setScale(2, BigDecimal.ROUND_HALF_DOWN));
        }
        ventas++;

        lblTotalValue.setText(total.toString());
        model.fireTableDataChanged();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(TPV.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TPV.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TPV.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TPV.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TPV().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn0;
    private javax.swing.JButton btn1;
    private javax.swing.JButton btn2;
    private javax.swing.JButton btn3;
    private javax.swing.JButton btn4;
    private javax.swing.JButton btn5;
    private javax.swing.JButton btn6;
    private javax.swing.JButton btn7;
    private javax.swing.JButton btn8;
    private javax.swing.JButton btn9;
    private javax.swing.JButton btnBorrar;
    private javax.swing.JButton btnComa;
    private javax.swing.JButton btnMas;
    private javax.swing.JButton btnMenos;
    private javax.swing.JButton btnPagar;
    private javax.swing.JButton btnPagarRapido;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton32;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lblTotalLabel;
    private javax.swing.JLabel lblTotalValue;
    private javax.swing.JPanel panelCaja;
    private javax.swing.JPanel panelCuenta;
    private javax.swing.JPanel panelGenero;
    private javax.swing.JPanel panelProducto;
    private javax.swing.JPanel panelTeclado;
    private javax.swing.JPanel panelToolBar;
    private javax.swing.JScrollPane scrollCuenta;
    private javax.swing.JScrollPane scrollGenero;
    private javax.swing.JScrollPane scrollProducto;
    private javax.swing.JScrollPane scrollTabla;
    private javax.swing.JTable tabla;
    // End of variables declaration//GEN-END:variables

    private List<Grupo> createGruops() {
        Producto pro1 = new Producto();
        pro1.setId(1);
        pro1.setName("Coca-Cola");
        pro1.setPrecioMasIva(new BigDecimal(1.33).setScale(2, BigDecimal.ROUND_HALF_DOWN));

        Producto pro2 = new Producto();
        pro2.setId(2);
        pro2.setName("Fanta Laranja");
        pro2.setPrecioMasIva(new BigDecimal(1.33).setScale(2, BigDecimal.ROUND_HALF_DOWN));

        Producto pro3 = new Producto();
        pro3.setId(3);
        pro3.setName("Dolly");
        pro3.setPrecioMasIva(new BigDecimal(1.13).setScale(2, BigDecimal.ROUND_HALF_DOWN));

        Grupo refrescos = new Grupo();
        refrescos.setId(1);
        refrescos.setNombre("Refrescos");
        refrescos.getProductos().add(pro1);
        refrescos.getProductos().add(pro2);
        refrescos.getProductos().add(pro3);

        Producto pro21 = new Producto();
        pro21.setId(21);
        pro21.setName("Solo");
        pro21.setPrecioMasIva(new BigDecimal(1).setScale(2, BigDecimal.ROUND_HALF_DOWN));

        Producto pro22 = new Producto();
        pro22.setId(22);
        pro22.setName("Cortado");
        pro22.setPrecioMasIva(new BigDecimal(1).setScale(2, BigDecimal.ROUND_HALF_DOWN));

        Producto pro23 = new Producto();
        pro23.setId(23);
        pro23.setName("Cafe con Leite");
        pro23.setPrecioMasIva(new BigDecimal(1.20).setScale(2, BigDecimal.ROUND_HALF_DOWN));

        Producto pro24 = new Producto();
        pro24.setId(24);
        pro24.setName("Solo largo");
        pro24.setPrecioMasIva(new BigDecimal(1).setScale(2, BigDecimal.ROUND_HALF_DOWN));

        Producto pro25 = new Producto();
        pro25.setId(25);
        pro25.setName("Bombom");
        pro25.setPrecioMasIva(new BigDecimal(1.2).setScale(2, BigDecimal.ROUND_HALF_DOWN));

        Producto pro26 = new Producto();
        pro26.setId(26);
        pro26.setName("Cafe con Leite");
        pro26.setPrecioMasIva(new BigDecimal(1.20).setScale(2, BigDecimal.ROUND_HALF_DOWN));
        
        Grupo cafes = new Grupo();
        cafes.setId(2);
        cafes.setNombre("Cafes");
        cafes.getProductos().add(pro21);
        cafes.getProductos().add(pro22);
        cafes.getProductos().add(pro23);
        cafes.getProductos().add(pro24);
        cafes.getProductos().add(pro25);
        cafes.getProductos().add(pro26);

        
        Producto pro31 = new Producto();
        pro31.setId(31);
        pro31.setName("1/2 Tostada con Aceite");
        pro31.setPrecioMasIva(new BigDecimal(0.60).setScale(2, BigDecimal.ROUND_HALF_DOWN));

        Producto pro32 = new Producto();
        pro32.setId(32);
        pro32.setName("1/2 Tostada con Fiambre");
        pro32.setPrecioMasIva(new BigDecimal(1.10).setScale(2, BigDecimal.ROUND_HALF_DOWN));

        Producto pro33 = new Producto();
        pro33.setId(33);
        pro33.setName("1/2 Tostada con doble Fiambre");
        pro33.setPrecioMasIva(new BigDecimal(1.13).setScale(2, BigDecimal.ROUND_HALF_DOWN));

        Grupo tostadas = new Grupo();
        tostadas.setId(3);
        tostadas.setNombre("Tostadas");
        tostadas.getProductos().add(pro31);
        tostadas.getProductos().add(pro32);
        tostadas.getProductos().add(pro33);

        List<Grupo> generos = new ArrayList();
        generos.add(refrescos);
        generos.add(cafes);
        generos.add(tostadas);

        return generos;
    }
}
