/*
 * File    : PinkNoiseNumbers.java
 * Created : 26-Sep-2007
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


import java.lang.Math;
import java.util.Random;

public class PinkNoiseNumbers {

    private int max_key;
    private int key;
    private int[] white_values;
    private int range;
    private Random rand;

    public PinkNoiseNumbers() {
    	max_key = 0x1f;
	range = 327;
	key = 0;
	Random rand = new Random();
	white_values = new int[5];
	for (int i=0; i<5; i++) {
		white_values[i] = Math.abs(rand.nextInt()%(range/5));
	}
    }

    public int getNextValue() {
    	int last_key = key;
	int sum;

	key++;
	if (key > max_key) key = 0;
	// Exclusive-Or previous value with current value. This gives
	// a list of bits that have changed.
	int diff = last_key ^ key;
	rand = new Random();
	sum = 0;
	for (int i=0; i<5; i++) {
		// If bit changed get new random number for corresponding
		// white_value
		rand = new Random();
		if ((diff & (1 << i))>0) {
			white_values[i] = Math.abs(rand.nextInt()%(range/5));
		}
		sum += white_values[i];
	}
	return sum;
    }

    public static void main(String[] args) {
    	PinkNoiseNumbers png = new PinkNoiseNumbers();
	
	for (double i=0; i<704000; i++) {
		System.out.println(png.getNextValue());
	}
    }

}
