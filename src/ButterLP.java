/*
 * File    : ButterLP.java
 * Created : 8-Sep-2008
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

import edu.cmu.sphinx.frontend.BaseDataProcessor;
import edu.cmu.sphinx.frontend.Data;
import edu.cmu.sphinx.frontend.DataEndSignal;
import edu.cmu.sphinx.frontend.DataProcessingException;
import edu.cmu.sphinx.frontend.DoubleData;
import edu.cmu.sphinx.util.props.PropertyException;
import edu.cmu.sphinx.util.props.PropertySheet;
import edu.cmu.sphinx.util.props.PropertyType;
import edu.cmu.sphinx.util.props.Registry;

import java.lang.Math;

/**
 * A Low Pass Shelving Filter based on a 2n order Butterworth design.
 * This filter reduces the boost excess.
 */
public class ButterLP extends BaseDataProcessor {

    public void register(String name, Registry registry)
            throws PropertyException {
        super.register(name, registry);
    }


    public void newProperties(PropertySheet ps) throws PropertyException {
        super.newProperties(ps);
    }

    /**
     * Returns the next Data object being processed by this LPF, or
     * if it is a Signal, it is returned without modification.
     * 
     * @return the next available Data object, returns null if no Data object
     *         is available
     * 
     * @throws DataProcessingException
     *                 if there is a processing error
     */

    public Data getData() throws DataProcessingException {
        Data input = getPredecessor().getData();
        getTimer().start();
        if (input != null) {
            if (input instanceof DoubleData) {
                applyButterLP(((DoubleData) input).getValues());
            }
        }
        getTimer().stop();
        return input;
    }

    /**
     * Applies the Butterworth LP filter to the given Audio.
     * 
     * @param in double[]: audio data
     */

    private void applyButterLP(double[] in) {
	    double[] num = new double[3];
	    double[] den = new double[3];

	    num[0] = 0.3423570;
	    num[1] = 0.6847139;
	    num[2] = 0.3423570;
	    den[0] = 0.1780545;
	    den[1] = 0.1913733;
	    den[2] = 1;
	    GenericFilter lPF = new GenericFilter(num,den);
	    double[] filtResult = lPF.applyFilter(in);

	    for (int i=0; i<in.length; i++) {
	    	in[i] = filtResult[i];
	    }
    }

}
