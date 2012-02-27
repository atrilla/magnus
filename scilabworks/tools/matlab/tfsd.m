function y = tfsd(x),
% tfsd(x)
%
% REPRESENTACIÓ DE LA TFSD{x(n)} en Mòdul i Argument en funció de la
% freqüència normalitzada: 
%
%	fn = 0   (component contínua)
%	fn = 0.5 (freqüència de Nyquist)
%	fn = 1   (freqüència de mostratge)
 


Lx = length(x);
xx=x;
if (Lx>1024),

	xx = x(1:1024);
	N = floor(Lx/1024);
	x(Lx + 1:(N+1)*1024) = zeros(1,(N+1)*1024 - Lx);
	for k = 1:N,
		xx = xx + x(k*1024:(k+1)*1024 - 1);
	end

end

f = (0:1023)/1023;
Fx = fft(xx,1024);
subplot(211);
plot(f,abs(Fx)); grid on;
ejeX=['   0  ';' pi/8 ';' pi/4 ';' 3pi/8';' pi/2 ';' 5pi/8';' 3pi/4';' 7pi/8';'  pi  ';' 9pi/8';' 5pi/4';'11pi/8';' 3pi/2';'13pi/8';' 7pi/4';'15pi/8';' 2pi  ']; %MODIFICADO (by Pol)
set(gca,'XTick',[0 1/16 1/8 3/16 1/4 5/16 3/8 7/16 1/2 9/16 5/8 11/16 3/4 13/16 7/8 15/16 1]); %MODIFICADO (by Pol)
set(gca,'XTickLabel',ejeX); %MODIFICADO (by Pol)
title('Mòdul de la TFSD');
xlabel('Freqüència normalitzada');
subplot(212);
plot(f,angle(Fx)); grid on;
set(gca,'XTick',[0 1/16 1/8 3/16 1/4 5/16 3/8 7/16 1/2 9/16 5/8 11/16 3/4 13/16 7/8 15/16 1]); %MODIFICADO (by Pol)
set(gca,'XTickLabel',ejeX); %MODIFICADO (by Pol)
title('Argument de la TFSD');
xlabel('Freqüència normalitzada');
%zoom on
