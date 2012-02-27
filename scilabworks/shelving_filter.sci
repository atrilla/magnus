//
// File    : shelving_filter.sci
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

function sh_filter=shelving_filter(fc,fsamp,gzero,gpi)

	// Given the cut-off frequency (fc), the
	// sampling frequency (fsamp), the gain at zero
	// frequancy (gzero) and the gain at half the
	// sampling frequency (gpi) it returns a shelving
	// filter in the 'z' domain.
	//
	// Note that according to the values given to
	// 'gzero' and 'gpi' the resulting filter can
	// be a HPF or a LPF.

	z=poly(0,'z');
	alph=alpha_calc(fc,fsamp);
	Az=(alph - z.^(-1))./(1 - alph.*z.^(-1));
	SHFz=(gpi./2).*(1 + Az) + (gzero./2).*(1 - Az);

	sh_filter=SHFz;

endfunction
