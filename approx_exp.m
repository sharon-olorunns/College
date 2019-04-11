%approximate e^(-x) by a partial sum of a series
%
clear
format long
xinput=input('type a positive value x for e^(-x) ');
eps=input('type in an accuracy requirement and press enter ');
n=0;
s=0;
bn=1;
xpower=1;
fact=1;
while bn>eps
   s=s+(-1)^n*bn;
   n=n+1;
   bn=bn/n*xinput;
end  
disp('Output: (1) N; (2) approximation of pi; (3) b_(N+1)')
[ n-1; s; bn ]