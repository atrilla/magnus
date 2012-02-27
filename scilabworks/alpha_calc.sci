//
// File    : alpha_calc.sci
// Created : 23-May-2008
// By      : atrilla
// 
// Magnus - Computer mouse pointer controller through voice commands
// 
// Copyright (C) 2007 Alexandre Trilla &
// Departament d'Educacio de la Generalitat de Catalunya &
// Universitat Ramon Llull La Salle Enginyeria de Telecomunicacions
// 
// 
// This file is part of Magnus.
// 
// Magnus is free software: you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.
// 
// Magnus is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details (see the COPYING file).
// 
// You should have received a copy of the GNU General Public License
// along with Magnus.  If not, see <http://www.gnu.org/licenses/>.
 

function alph=alpha_calc(fc,fsamp)

	// Given the cut-off frequency (fc) and
	// the sampling frequency (fsamp)
	// the function returns the correspondent
	// value of alpha.
	//
	// Bear in mind that for wc=pi/2 the equation
	// tends to infinite.

	wc=(2.*%pi.*fc)./fsamp;
	var=poly(0,'var');
	p=cos(wc).*var.^2 - 2.*var + cos(wc);
	alph=min(real(roots(p)));

endfunction
