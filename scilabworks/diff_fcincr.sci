//
// File    : diff_fcincr.sci
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
 

function fcvect=diff_fcincr(newdiff,presFcVect,fsamp)

	// Linear parameters
	supLim=500;
	infLim=-500;
	a=(supLim-infLim)./(-1.2);
	b=3.*(infLim+supLim);

	incrFc=a.*newdiff + b;

	newFc=presFcVect(length(presFcVect))+incrFc;

	// Fc clipping
	if newFc>6000 then
		newFc = 6000;
	elseif newFc<2000 then
		newFc = 2000;
	end

	aux1=presFcVect(length(presFcVect)).*ones(1,length(presFcVect));
	aux2=newFc.*ones(1,length(presFcVect));
	u=[aux1,aux2];

	if incrFc>0 then
		fcController=gain_controller(6e-3,6e-3,fsamp);
	else
		fcController=gain_controller(6e-3,20e-3,fsamp);
	end

	theWave=dsimul(fcController,u);
	for i=1:length(presFcVect)
		fcvect(i)=theWave(i+length(presFcVect));
	end


endfunction
