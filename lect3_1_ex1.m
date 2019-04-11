%
% An example for Lecture 3.1
%
clear
format long
xv=0:pi/20:pi/2; yv=sin(xv);
plot(xv,yv,'r*')
hold on
nplus1=length(xv);
a11=nplus1; a12=sum(xv); a22=sum(xv.^2);
A1=[a11 a12;a12 a22];
b1=sum(yv); b2=sum(xv.*yv);
b=[b1;b2];
a=inv(A1)*b;
P=a(1)+a(2)*xv;
err1=sum((yv-P).^2);
disp('least square error = '),err1
plot(xv,P,'k-.')
a23=sum(xv.^3);
a33=sum(xv.^4);
A2=[A1 [a22;a23];a22 a23 a33];
b3=sum(xv.^2.*yv);
b(3,1)=b3;
a=inv(A2)*b;
P=a(1)+a(2)*xv+a(3)*xv.^2;
err2=sum((yv-P).^2);
disp('least square error = '),err2
plot(xv,P,'b--')
hold off
title('* y=sin(x), 0<=x<=\pi/2, -. y=P_1(x), -- y=P_2(x)')
