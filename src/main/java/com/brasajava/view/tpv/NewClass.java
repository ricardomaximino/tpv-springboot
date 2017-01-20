package com.brasajava.view.tpv;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;


public class NewClass extends JFrame{
    JScrollPane scrollPane;
    JPanel panel;        
    JButton button;
    private int count;
    
    public NewClass(){
        this.setSize(900, 900);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.add(create(),BorderLayout.CENTER);
        button = new JButton("Adicionar");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buttonMetodo(e);
            }
        });
        button.setBounds(0,70,100,50);
        this.getContentPane().add(button,BorderLayout.SOUTH);
    }
    private void buttonMetodo(ActionEvent e){
        button = new JButton(count + "");
        button.setBounds(0, count * 50, 50, 50);
        panel.setSize(panel.getWidth(), panel.getHeight()+ 50);
        panel.add(button);
        count++;
        panel.repaint();
    }
    private JScrollPane create(){
        panel = new JPanel();
        panel.setBounds(0, 0, 400, 50);
        panel.setBackground(Color.yellow);
        scrollPane = new JScrollPane(panel);
        return scrollPane;
    }
    public static void main(String[] args) {
        JFrame f = new NewClass();
        f.setVisible(true);
    }
    
}
