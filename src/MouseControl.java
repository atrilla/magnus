/*
 * File    : MouseControl.java
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

import java.awt.Dimension;
import java.awt.Robot;
import java.awt.AWTException;
import java.awt.Toolkit;
import java.awt.event.InputEvent;

/**
 * The class <it>MouseControl</it> is in charge of the control of the mouse.
 * @author Alexandre Trilla (atrilla@xtec.cat, st12809@salle.url.edu)
 */

public class MouseControl {
	
	Robot rob;
	String action;
	int xcoord,ycoord,accint;
	boolean quietpointer,dragflag,firststop,modusmouse;
	Dimension dim;
	double factor;
	
	/**
	 * This is the <it>MouseControl</it> constructor.
	 * @throws AWTException
	 */

	public MouseControl() throws AWTException {
		rob = new Robot();
		action = "stop";
		quietpointer = true;
		accint = 0;
		factor = 0.0001;
		dragflag = false;
		firststop = false;
	}
	
	/**
	 * This method sets the acceleration factor.
	 * @param acc String: acceleration parameter.
	 */

	public void SetFactor(String acc) {
		if (acc.equals("none")) factor = 0;
		if (acc.equals("moderate")) factor = 0.0001;
		if (acc.equals("slow")) factor = 0.00002;
		if (acc.equals("fast")) factor = 0.0005;
	}
	
	/**
	 * This method sets the initial pointer position.
	 */

	public void SetInitPointer() {
		dim = Toolkit.getDefaultToolkit().getScreenSize();
		xcoord = dim.width/2;
		ycoord = dim.height/2;
		rob.mouseMove(xcoord,ycoord);
	}
	
	/**
	 * This method sets the mouse pointer to any position indicated.
	 * @param x int: X position.
	 * @param y int: Y position.
	 */

	public void SetMousePointer(int x,int y) {
		rob.mouseMove(x,y);
	}
	
	/**
	 * This methos sets the new action.
	 @param resultText String: the action that has been recognized.
	 */
	 
	public void NewAction(String resultText) {
		if (resultText.endsWith("sleep")) {
			if (action.equals("stop")) {
				action = "sleep";
			}
		}
		if (resultText.endsWith("change")) {
			action = "change";
		}
		if (resultText.endsWith("right")) {
			action = "right";
		}
		if (resultText.endsWith("left")) {
			action = "left";
		}
		if (resultText.endsWith("up")) {
			action = "up";
		}
		if (resultText.endsWith("down")) {
			action = "down";
		}
		if (resultText.endsWith("stop")) {
			action = "stop";
		}
		if (resultText.endsWith("click")) {
			if (action.equals("stop")) {
				action = "click";
			}
		}
		if (resultText.endsWith("double")) {
			if (action.equals("stop")) {
				action = "double";
			}
		}
		if (resultText.endsWith("drag")) {
			if (action.equals("stop")) {
				action = "drag";
			}
		}
		if (resultText.endsWith("menu")) {
			if (action.equals("stop")) {
				action = "menu";
			}
		}
		if (resultText.endsWith("mouse")) {
			if (action.equals("stop")) {
				action = "mouse";
				modusmouse = true;
			}
		}
		if (resultText.endsWith("keyboard")) {
			if (action.equals("stop")) {
				action = "keyboard";
				modusmouse = false;
			}
		}
	}
	
	/**
	 * This method executes the action that has been set.
	 */

	public void DoAction() {
		if (modusmouse==true) {
			if (action.equals("right")) {
				accint++;
				if (factor!=0) {
					xcoord = xcoord + (int)Math.round((double)accint*(double)accint*factor);
				} else {
					xcoord++;
				}
			}
			if (action.equals("left")) {
				accint++;
				if (factor!=0) {
					xcoord = xcoord - (int)Math.round((double)accint*(double)accint*factor);
				} else {
					xcoord--;
				}
			}
			if (action.equals("up")) {
				accint++;
				if (factor!=0) {
					ycoord = ycoord - (int)Math.round((double)accint*(double)accint*factor);
				} else {
					ycoord--;
				}
			}
			if (action.equals("down")) {
				accint++;
				if (factor!=0) {
					ycoord = ycoord + (int)Math.round((double)accint*(double)accint*factor);
				} else {
					ycoord++;
				}
			}
			if (action.equals("stop")) {
				quietpointer = true;
				if (firststop==false) {			// in order no to direct the pointer to the down left corner
					xcoord = xcoord + 5;
					ycoord = ycoord + 5;
					if (xcoord>dim.width) {
						xcoord = dim.width;
					}
					if (xcoord<0) {
						xcoord = 0;
					}
					if (ycoord>dim.height) {
						ycoord = dim.height;
					}
					if (ycoord<0) {
						ycoord = 0;
					}
					firststop = true;
				}
				accint = 0;
			}
			if (action.equals("click")) {
				rob.mousePress(InputEvent.BUTTON1_MASK);
				rob.delay(100);
				rob.mouseRelease(InputEvent.BUTTON1_MASK);
				action = "stop";
			}
			
			if (action.equals("double")) {
				rob.mousePress(InputEvent.BUTTON1_MASK);
				rob.delay(100);
				rob.mouseRelease(InputEvent.BUTTON1_MASK);
				rob.delay(100);
				rob.mousePress(InputEvent.BUTTON1_MASK);
				rob.delay(100);
				rob.mouseRelease(InputEvent.BUTTON1_MASK);
				action = "stop";
			}
			
			if (action.equals("menu")) {
				rob.mousePress(InputEvent.BUTTON3_MASK);
				rob.delay(100);
				rob.mouseRelease(InputEvent.BUTTON3_MASK);
				action = "stop";
			}
			
			if (action.equals("drag")) {
				if (dragflag==false) {
					dragflag = true;
					rob.mousePress(InputEvent.BUTTON1_MASK);
				} else {
					dragflag = false;
					rob.mouseRelease(InputEvent.BUTTON1_MASK);
				}
				
				action = "stop";
			}

			if (action.equals("right")||action.equals("left")||action.equals("up")||action.equals("down")) {
				quietpointer = false;
				firststop = false;
				if (xcoord>dim.width) {
					xcoord = dim.width;
					action = "stop";
				}
				if (xcoord<0) {
					xcoord = 0;
					action = "stop";
				}
				if (ycoord>dim.height) {
					ycoord = dim.height;
					action = "stop";
				}
				if (ycoord<0) {
					ycoord = 0;
					action = "stop";
				}
				rob.delay(7);
				rob.mouseMove(xcoord, ycoord);
			}
		} else {									// modus keyboard
			if (action.equals("change")) {
				rob.keyPress(18);
				rob.delay(100);
				rob.keyPress(9);
				rob.delay(100);
				rob.keyRelease(9);
				rob.keyRelease(18);
				action = "stop";
			}
			if (action.equals("right")) {
				rob.keyPress(39);
				rob.delay(100);
				rob.keyRelease(39);
				action = "stop";
			}
			if (action.equals("left")) {
				rob.keyPress(37);
				rob.delay(100);
				rob.keyRelease(37);
				action = "stop";
			}
			if (action.equals("up")) {
				rob.keyPress(38);
				rob.delay(100);
				rob.keyRelease(38);
				action = "stop";
			}
			if (action.equals("down")) {
				rob.keyPress(40);
				rob.delay(100);
				rob.keyRelease(40);
				action = "stop";
			}
			if (action.equals("click")) {
				rob.keyPress(10);
				rob.delay(100);
				rob.keyRelease(10);
				action = "stop";
			}
		}
		
		if (action.equals("mouse")) {
			action = "stop";
		}
		
		if (action.equals("keyboard")) {
			action = "stop";
		}
	}
	
	/**
	 * This function indicates whether the pointer is quiet or not.
	 * @return TRUE if the pointer is quiet. FALSE otherwise.
	 */

	public boolean QuietPointer() {
		
		return quietpointer;
	}
	
	/**
	 * This function retrieves the X position.
	 * @return The X position.
	 */ 

	public int GetX() {
		return xcoord;
	}
	
	/**
	 * This function retrieves the Y position.
	 * @return The Y position.
	 */

	public int GetY() {
		return ycoord;
	}
	
	/**
	 * This method sets the pointer to the coordinated saved by a prior process.
	 */

	public void RestorePosition() {
		rob.mouseMove(xcoord,ycoord);
	}

}
