% interpolate at equally spaced points on [-5,5]
x=-5:5;
% this is the function we're interpolating
% note use of . to operate on vectors
y=1./(1+x.^2);
% polyfit returns a row vector of coefficients of the polynomial of degree
% n that best fits the given (x,y) values
p=polyfit(x,y,10);
% for plotting, we use a larger number of points on [-5,5]
xs=-5:0.01:5;
ys=1./(1+xs.^2);
% polyval evaluates a polynomial (represented by a row vector of its
% coefficients) at given points
ps=polyval(p,xs);
% plot function
plot(xs,ys)
hold on
% plot polynomial
plot(xs,ps,'r--')
legend('1/(1+x^2)','p_{10}(x)')
% plot interpolation points to show they match
plot(x,y,'ro')
hold off
title('Runge''s example')
