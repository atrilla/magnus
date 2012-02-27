/*
 * File    : ConfigScreen.java
 * Created : 04-oct-2007
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
 * The class <it>ConfigScreen</it> defines the screen where all
 * the configuration elements will be placed.
 * @author Alexandre Trilla (atrilla@xtec.cat, st12809@salle.url.edu)
 */

public class ConfigScreen extends Frame {
	
	MyMenu mm;

    /**
     * This is the main method for configuring the <it>ConfigScreen</it>.
     * @param mmenu MyMenu: This parameter contains valuable information about the present
     *                      configuration.
     */

    public ConfigScreen(MyMenu mmenu) {
    	mm = mmenu;
    	setSize(460,280);
    	setResizable(false);
    	setBackground(new Color(0,174,0));
    	setLayout(new BorderLayout());
    	setTitle("MAGNUS Menú de Configuració");
    	Toolkit toolkit=Toolkit.getDefaultToolkit();
        URL url=getClass().getResource("icon.png");
        Image image=toolkit.getImage(url);
    	setIconImage(image);
    	
    	ScrollPane scrollPane=new ScrollPane(ScrollPane.SCROLLBARS_NEVER);
	    scrollPane.setSize(300, 100);
	    scrollPane.setBackground(new Color(0,128,0));
	    Canvas canvas = new ConfigCanvas();
	    canvas.setSize(300, 100);
	    scrollPane.add(canvas);
	    add(scrollPane,BorderLayout.NORTH);
	    
	    ConfigPanel panel = new ConfigPanel(this);
	    add(panel,BorderLayout.CENTER);
	    
    	addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                setVisible(false);
                mm.SetConfigClosing();
            }
        });
    }
   
}
