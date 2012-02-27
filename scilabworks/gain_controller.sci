//
// File    : gain_controller.sci
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
 

function Mzss=gain_controller(tau,tr,fsamp)

	// Returns a controller which responds
	// with the attributes that have been
	// passed, the exponential canstant of the
	// system (tau) and the rise time (tr).

	chi=(-0.8+sqrt(0.8.^2 + 4.*2.5.*(tr./tau)))./5;
	wo=(0.8 + 2.5.*chi)./tr;

	s=poly(0,'s');
	Msprima = (wo.^2)./(s.^2 + 2.*chi.*wo.*s + wo.^2);
	Ms = syslin('c',Msprima);

	Mzss = cls2dls(tf2ss(Ms),1./fsamp);

endfunction
