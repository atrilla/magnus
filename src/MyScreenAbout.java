/*
 * File    : MyScreenAbout.java
 * Created : 14-Feb-2008
 * By      : atrilla
 *
 * MAGNUS - Computer mouse pointer controller through voice commands
 *
 * Copyright (C) 2007 Alexandre Trilla &
 * Departament d'Educacio de la Generalitat de Catalunya &
 * Universitat Ramon Llull La Salle Enginyeria de Telecomunicacions
 *
 *
 * This file is part of MAGNUS.
 *
 * MAGNUS is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * MAGNUS is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details (see the COPYING file).
 *
 * You should have received a copy of the GNU General Public License
 * along with MAGNUS.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Label;
import java.awt.MediaTracker;
import java.awt.Panel;
import java.awt.ScrollPane;
import java.awt.TextArea;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.URL;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

/**
 * The class <it>MyScreenAbout</it> defines the screen where all
 * the information about Magnus will be placed.
 * @author Alexandre Trilla (atrilla@xtec.cat, st12809@salle.url.edu)
 */

public class MyScreenAbout extends Frame {
	
    /**
     * This is the main method for configuring the <it>MyScreenAbout</it>.
     */

    public MyScreenAbout() { //constructor
    	setSize(470, 600);
    	setTitle("informació sobre el MAGNUS");
    	setResizable(false);
    	Toolkit toolkit=Toolkit.getDefaultToolkit();
	    URL url=getClass().getResource("icon.png");
	    Image image=toolkit.getImage(url);
	    setIconImage(image);
  	    ScrollPane scrollPane=new ScrollPane(ScrollPane.SCROLLBARS_NEVER);
  	    scrollPane.setSize(470, 148);
  	    scrollPane.setBackground(new Color(0,128,0));
  	    Canvas canvas=new MyCanvasAbout();
  	    canvas.setSize(470, 148);
  	    scrollPane.add(canvas);
  	    add(scrollPane,BorderLayout.NORTH);
  	    
  	    ScrollPane infoscroll = new ScrollPane(ScrollPane.SCROLLBARS_AS_NEEDED);
  	    infoscroll.setSize(470,400);
  	    infoscroll.setBackground(new Color(255,255,255));
  	    Panel info = new Panel();
  	    info.setLayout(new GridLayout(56,1));
  	    infoscroll.add(info);

  	    Label ts1 = new Label("MAGNUS 0.1.1");
  	    ts1.setFont(new Font("SansSerif",Font.BOLD,10));
  	    ts1.setForeground(new Color(0,0,0));
  	    ts1.setBackground(new Color(255,255,255));
  	    info.add(ts1);
  	    
  	    Label ts2 = new Label("http://magnusproject.wordpress.com");
	    ts2.setFont(new Font("SansSerif",Font.PLAIN,10));
	    ts2.setForeground(new Color(0,0,255));
	    ts2.setBackground(new Color(255,255,255));
	    info.add(ts2);
	    
	    Label ts3 = new Label(" ");
	    ts3.setFont(new Font("SansSerif",Font.PLAIN,10));
	    ts3.setForeground(new Color(0,0,255));
	    ts3.setBackground(new Color(255,255,255));
	    info.add(ts3);
	    
	    Label ts4 = new Label("Alexandre Trilla Castelló");
	    ts4.setFont(new Font("SansSerif",Font.PLAIN,10));
	    ts4.setForeground(new Color(0,0,0));
	    ts4.setBackground(new Color(255,255,255));
	    info.add(ts4);
	    
	    Label ts5 = new Label("          http://www.salle.url.edu/~atrilla/");
	    ts5.setFont(new Font("SansSerif",Font.PLAIN,10));
	    ts5.setForeground(new Color(0,0,255));
	    ts5.setBackground(new Color(255,255,255));
	    info.add(ts5);
	    
	    Label ts7 = new Label("Departament d'Educació de la Generalitat de Catalunya");
	    ts7.setFont(new Font("SansSerif",Font.PLAIN,10));
	    ts7.setForeground(new Color(0,0,0));
	    ts7.setBackground(new Color(255,255,255));
	    info.add(ts7);
	    
	    Label ts8 = new Label("          http://www.xtec.cat");
	    ts8.setFont(new Font("SansSerif",Font.PLAIN,10));
	    ts8.setForeground(new Color(0,0,255));
	    ts8.setBackground(new Color(255,255,255));
	    info.add(ts8);
	    
	    Label ts9 = new Label("Universitat Ramon Llull La Salle Enginyeria de Telecomunicació");
	    ts9.setFont(new Font("SansSerif",Font.PLAIN,10));
	    ts9.setForeground(new Color(0,0,0));
	    ts9.setBackground(new Color(255,255,255));
	    info.add(ts9);
	    
	    Label ts10 = new Label("          http://www.salle.url.edu");
	    ts10.setFont(new Font("SansSerif",Font.PLAIN,10));
	    ts10.setForeground(new Color(0,0,255));
	    ts10.setBackground(new Color(255,255,255));
	    info.add(ts10);
	    
	    Label ts11 = new Label(" ");
	    ts11.setFont(new Font("SansSerif",Font.PLAIN,10));
	    ts11.setForeground(new Color(0,0,0));
	    ts11.setBackground(new Color(255,255,255));
	    info.add(ts11);
	    
	    Label ts111 = new Label(" ");
	    ts111.setFont(new Font("SansSerif",Font.PLAIN,10));
	    ts111.setForeground(new Color(0,0,255));
	    ts111.setBackground(new Color(255,255,255));
	    info.add(ts111);
	    
	    DesenvCanvas dc = new DesenvCanvas();
	    info.add(dc);
	    
	    Label ts17 = new Label("MAGNUS ha estat desenvolupat per Alexandre Trilla");
	    ts17.setFont(new Font("SansSerif",Font.PLAIN,10));
	    ts17.setForeground(new Color(0,0,0));
	    ts17.setBackground(new Color(255,255,255));
	    info.add(ts17);
	    
	    Label ts18 = new Label("pel Servei de Tecnologies per l'Aprenentatge i el");
	    ts18.setFont(new Font("SansSerif",Font.PLAIN,10));
	    ts18.setForeground(new Color(0,0,0));
	    ts18.setBackground(new Color(255,255,255));
	    info.add(ts18);
	    
	    Label ts19 = new Label("Coneixement del Departament d'Educació de la");
	    ts19.setFont(new Font("SansSerif",Font.PLAIN,10));
	    ts19.setForeground(new Color(0,0,0));
	    ts19.setBackground(new Color(255,255,255));
	    info.add(ts19);
	    
	    Label ts20 = new Label("Generalitat de Catalunya.");
	    ts20.setFont(new Font("SansSerif",Font.PLAIN,10));
	    ts20.setForeground(new Color(0,0,0));
	    ts20.setBackground(new Color(255,255,255));
	    info.add(ts20);
	    
	    Label ts21 = new Label(" ");
	    ts21.setFont(new Font("SansSerif",Font.PLAIN,10));
	    ts21.setForeground(new Color(0,0,0));
	    ts21.setBackground(new Color(255,255,255));
	    info.add(ts21);
	    
	    Label ts22 = new Label("Alhora, MAGNUS ha constituït el Projecte Final de");
	    ts22.setFont(new Font("SansSerif",Font.PLAIN,10));
	    ts22.setForeground(new Color(0,0,0));
	    ts22.setBackground(new Color(255,255,255));
	    info.add(ts22);
	    
	    Label ts23 = new Label("Carrera de l'autor, havent cursat d'Enginyeria");
	    ts23.setFont(new Font("SansSerif",Font.PLAIN,10));
	    ts23.setForeground(new Color(0,0,0));
	    ts23.setBackground(new Color(255,255,255));
	    info.add(ts23);
	    
	    Label ts24 = new Label("Superior de Telecomunicacions a la Universitat");
	    ts24.setFont(new Font("SansSerif",Font.PLAIN,10));
	    ts24.setForeground(new Color(0,0,0));
	    ts24.setBackground(new Color(255,255,255));
	    info.add(ts24);
	    
	    Label ts25 = new Label("Ramon Llull La Salle de Barcelona.");
	    ts25.setFont(new Font("SansSerif",Font.PLAIN,10));
	    ts25.setForeground(new Color(0,0,0));
	    ts25.setBackground(new Color(255,255,255));
	    info.add(ts25);
	    
	    Label ts26 = new Label(" ");
	    ts26.setFont(new Font("SansSerif",Font.PLAIN,10));
	    ts26.setForeground(new Color(0,0,0));
	    ts26.setBackground(new Color(255,255,255));
	    info.add(ts26);
	    
	    Label ts261 = new Label(" ");
	    ts261.setFont(new Font("SansSerif",Font.PLAIN,10));
	    ts261.setForeground(new Color(0,0,0));
	    ts261.setBackground(new Color(255,255,255));
	    info.add(ts261);
	    
	    CredenCanvas cc = new CredenCanvas();
	    info.add(cc);
	    
	    Label ts12 = new Label("Aquest producte inclou programari desenvolupat per:");
	    ts12.setFont(new Font("SansSerif",Font.PLAIN,10));
	    ts12.setForeground(new Color(0,0,0));
	    ts12.setBackground(new Color(255,255,255));
	    info.add(ts12);
	    
	    Label ts121 = new Label(" ");
	    ts121.setFont(new Font("SansSerif",Font.PLAIN,10));
	    ts121.setForeground(new Color(0,0,0));
	    ts121.setBackground(new Color(255,255,255));
	    info.add(ts121);
	    
	    Label ts13 = new Label("Sphinx-4");
	    ts13.setFont(new Font("SansSerif",Font.BOLD,10));
	    ts13.setForeground(new Color(0,0,0));
	    ts13.setBackground(new Color(255,255,255));
	    info.add(ts13);
	    
	    Label ts14 = new Label("Carnegie Mellon University");
	    ts14.setFont(new Font("SansSerif",Font.PLAIN,10));
	    ts14.setForeground(new Color(0,0,0));
	    ts14.setBackground(new Color(255,255,255));
	    info.add(ts14);
	    
	    Label ts142 = new Label("          http://cmusphinx.sourceforge.net/sphinx4/");
	    ts142.setFont(new Font("SansSerif",Font.PLAIN,10));
	    ts142.setForeground(new Color(0,0,255));
	    ts142.setBackground(new Color(255,255,255));
	    info.add(ts142);
	    
	    Label ts141 = new Label(" ");
	    ts141.setFont(new Font("SansSerif",Font.PLAIN,10));
	    ts141.setForeground(new Color(0,0,0));
	    ts141.setBackground(new Color(255,255,255));
	    info.add(ts141);
	    
	    Label ts15 = new Label("Jdom");
	    ts15.setFont(new Font("SansSerif",Font.BOLD,10));
	    ts15.setForeground(new Color(0,0,0));
	    ts15.setBackground(new Color(255,255,255));
	    info.add(ts15);
	    
	    Label ts16 = new Label("The JDOM Project");
	    ts16.setFont(new Font("SansSerif",Font.PLAIN,10));
	    ts16.setForeground(new Color(0,0,0));
	    ts16.setBackground(new Color(255,255,255));
	    info.add(ts16);
	    
	    Label ts162 = new Label("          http://www.jdom.org");
	    ts162.setFont(new Font("SansSerif",Font.PLAIN,10));
	    ts162.setForeground(new Color(0,0,255));
	    ts162.setBackground(new Color(255,255,255));
	    info.add(ts162);
	    
	    Label ts161 = new Label(" ");
	    ts161.setFont(new Font("SansSerif",Font.PLAIN,10));
	    ts161.setForeground(new Color(0,0,0));
	    ts161.setBackground(new Color(255,255,255));
	    info.add(ts161);
	    
	    Label ts163 = new Label(" ");
	    ts163.setFont(new Font("SansSerif",Font.PLAIN,10));
	    ts163.setForeground(new Color(0,0,0));
	    ts163.setBackground(new Color(255,255,255));
	    info.add(ts163);
	    
	    LicCanvas lc = new LicCanvas();
	    info.add(lc);
	    
	    Label ts64 = new Label("Copyright (C) 2007 Alexandre Trilla &");
	    ts64.setFont(new Font("SansSerif",Font.PLAIN,10));
	    ts64.setForeground(new Color(0,0,0));
	    ts64.setBackground(new Color(255,255,255));
	    info.add(ts64);
	    
	    Label ts65 = new Label("Departament d'Educació de la Generalitat de Catalunya &");
	    ts65.setFont(new Font("SansSerif",Font.PLAIN,10));
	    ts65.setForeground(new Color(0,0,0));
	    ts65.setBackground(new Color(255,255,255));
	    info.add(ts65);
	    
	    Label ts66 = new Label("Universitat Ramon Llull La Salle Enginyeria de Telecomunicació");
	    ts66.setFont(new Font("SansSerif",Font.PLAIN,10));
	    ts66.setForeground(new Color(0,0,0));
	    ts66.setBackground(new Color(255,255,255));
	    info.add(ts66);
	    
	    Label ts67 = new Label(" ");
	    ts67.setFont(new Font("SansSerif",Font.PLAIN,10));
	    ts67.setForeground(new Color(0,0,0));
	    ts67.setBackground(new Color(255,255,255));
	    info.add(ts67);
  	    
	    Label ts68 = new Label("Aquest programa és lliure. Podeu distribuir-lo i/o modificar-");
	    ts68.setFont(new Font("SansSerif",Font.PLAIN,10));
	    ts68.setForeground(new Color(0,0,0));
	    ts68.setBackground(new Color(255,255,255));
	    info.add(ts68);
	    
	    Label ts69 = new Label("lo conforme a les disposicions de la Llicència Pública General");
	    ts69.setFont(new Font("SansSerif",Font.PLAIN,10));
	    ts69.setForeground(new Color(0,0,0));
	    ts69.setBackground(new Color(255,255,255));
	    info.add(ts69);
	    
	    Label ts70 = new Label("de GNU (GPL), publicada per la Free Software Foundation, ja");
	    ts70.setFont(new Font("SansSerif",Font.PLAIN,10));
	    ts70.setForeground(new Color(0,0,0));
	    ts70.setBackground(new Color(255,255,255));
	    info.add(ts70);
	    
	    Label ts71 = new Label("sigui la versió 3 de la llicència o qualsevol versió posterior.");
	    ts71.setFont(new Font("SansSerif",Font.PLAIN,10));
	    ts71.setForeground(new Color(0,0,0));
	    ts71.setBackground(new Color(255,255,255));
	    info.add(ts71);
	    
	    Label ts711 = new Label(" ");
	    ts711.setFont(new Font("SansSerif",Font.PLAIN,10));
	    ts711.setForeground(new Color(0,0,0));
	    ts711.setBackground(new Color(255,255,255));
	    info.add(ts711);
	    
	    Label ts72 = new Label("Aquest programa es distribueix amb la intenció de resultar");
	    ts72.setFont(new Font("SansSerif",Font.PLAIN,10));
	    ts72.setForeground(new Color(0,0,0));
	    ts72.setBackground(new Color(255,255,255));
	    info.add(ts72);
	    
	    Label ts73 = new Label("útil, però SENSE CAP GARANTIA, fins i tot sense la garantia");
	    ts73.setFont(new Font("SansSerif",Font.PLAIN,10));
	    ts73.setForeground(new Color(0,0,0));
	    ts73.setBackground(new Color(255,255,255));
	    info.add(ts73);
	    
	    Label ts74 = new Label("implícita de COMERCIABILITAT o CONVENIÈNCIA PER A UN");
	    ts74.setFont(new Font("SansSerif",Font.PLAIN,10));
	    ts74.setForeground(new Color(0,0,0));
	    ts74.setBackground(new Color(255,255,255));
	    info.add(ts74);
	    
	    Label ts75 = new Label("PROPÒSIT PARTICULAR. Per a més detalls, vegeu la Llicència");
	    ts75.setFont(new Font("SansSerif",Font.PLAIN,10));
	    ts75.setForeground(new Color(0,0,0));
	    ts75.setBackground(new Color(255,255,255));
	    info.add(ts75);
	    
	    Label ts76 = new Label("Pública General de GNU.");
	    ts76.setFont(new Font("SansSerif",Font.PLAIN,10));
	    ts76.setForeground(new Color(0,0,0));
	    ts76.setBackground(new Color(255,255,255));
	    info.add(ts76);
	    
	    Label ts77 = new Label(" ");
	    ts77.setFont(new Font("SansSerif",Font.PLAIN,10));
	    ts77.setForeground(new Color(0,0,0));
	    ts77.setBackground(new Color(255,255,255));
	    info.add(ts77);
	    
	    Label ts771 = new Label(" ");
	    ts771.setFont(new Font("SansSerif",Font.PLAIN,10));
	    ts771.setForeground(new Color(0,0,0));
	    ts771.setBackground(new Color(255,255,255));
	    info.add(ts771);
	    
	    SupCanvas supc = new SupCanvas();
	    info.add(supc);
	    
	    Label ts1234 = new Label("Desenvolupat en col·laboració amb Enginyeria i");
	    ts1234.setFont(new Font("SansSerif",Font.PLAIN,10));
	    ts1234.setForeground(new Color(0,0,0));
	    ts1234.setBackground(new Color(255,255,255));
	    info.add(ts1234);
	    
	    Label ts12345 = new Label("Arquitectura La Salle (Universitat Ramon Llull)");
	    ts12345.setFont(new Font("SansSerif",Font.PLAIN,10));
	    ts12345.setForeground(new Color(0,0,0));
	    ts12345.setBackground(new Color(255,255,255));
	    info.add(ts12345);
	    
	    SalleCanvas sallec = new SalleCanvas();
	    //info.add(sallec);
	    add(sallec,BorderLayout.SOUTH);
	    
	    add(infoscroll,BorderLayout.CENTER);
  	    
  	    addWindowListener(new WindowAdapter() {

    /**
     * This method catches the closing event and react accordingly.
     * @param e WindowEvent: the window closing event.
     */

            public void windowClosing(WindowEvent e) {
            	setVisible(false);
            }
        });
    }
    
}
