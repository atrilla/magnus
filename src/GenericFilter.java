/*
 * File    : GenericFilter.java
 * Created : 5-Sep-2008
 * By      : atrilla
 *
 * Magnus - Computer mouse pointer controller through voice commands
 *
 * Copyright (C) 2007 Alexandre Trilla &
 * Departament d'Educacio de la Generalitat de Catalunya &
 * Universitat Ramon Llull La Salle Enginyeria de Telecomunicacions
 *
 *
 * This file is part of Magnus.
 *
 * Magnus is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Magnus is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details (see the COPYING file).
 *
 * You should have received a copy of the GNU General Public License
 * along with Magnus.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

/**
 * A generic filter implementation. IIR type. A FIR type can be obtained
 * setting b_i=0.
 * Y(z)/X(z) = (num[0] + num[1]z + num[2]z^2) / (den[0] + den[1]z + den[2]z^2)
 * following the Scilab notation.
 */

public class GenericFilter {

	private double[] xPrior;
	private double[] yPrior;
	
	private int numPriorsX;
	private int numPriorsY;

	private double[] num;
	private double[] den;

	/**
	 * GenericFilter constructor.
	 *
	 * @param num double[]: Numerator.
	 * @param den double[]: Denominator.
	 */

	public GenericFilter(double[] num, double[] den) {
		this.num = new double[num.length];
		this.den = new double[den.length];

		for (int i=0; i<num.length; i++) {
			this.num[i] = num[i];
		}
		for (int i=0; i<den.length; i++) {
			this.den[i] = den[i];
		}

		numPriorsY = den.length-1;
		numPriorsX = num.length-1;

		yPrior = new double[numPriorsY];
		for (int i=0; i<numPriorsY; i++) {
			yPrior[i] = 0.0;
		}
		xPrior = new double[numPriorsX];
		for (int i=0; i<numPriorsX; i++) {
			xPrior[i] = 0.0;
		}
	}

	/**
	 * Processes the incoming data through the filter.
	 *
	 * @param in double[]: Incoming signal.
	 */

	public double[] applyFilter(double[] in) {
		double[] result = new double[in.length];
		int i=0;
		int j=0; // iterators

		for (i=0; i<in.length; i++) {
			result[i]=0;
			for (j=0; j<numPriorsY; j++) {
				result[i]=result[i]-den[1-j]*yPrior[j];
			}
			result[i]=result[i]+num[numPriorsX]*in[i];
			for (j=0; j<numPriorsX; j++) {
				result[i]=result[i]+num[1-j]*xPrior[j];
			}
			result[i]=(1/den[numPriorsY])*result[i];

			// Refresh priors
			for (j=numPriorsY-1; j>0; j--) {
				yPrior[j]=yPrior[j-1];
			}
			yPrior[0]=result[i];
			for (j=numPriorsX-1; j>0; j--) {
				xPrior[j]=xPrior[j-1];
			}
			xPrior[0]=in[i];
		}
		return result;
	}

}
