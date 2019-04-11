% First we will test that we can interpolate a polynomial exactly
f = @(x) 1 + x - x.^2;
x = [-1,0,1];
y = f(x);
z = linspace(-1,1,1001);
w = lagrange_weights(x);
pn = lagrange_eval_naive(z, x, y, w);
plot(z,f(z) - pn);