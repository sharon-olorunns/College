%% First we will test that we can interpolate a polynomial exactly
f = @(x) 1 + x - x.^2;
x = [-1,0,1,2,3];
y = f(x);
z = linspace(-1,1,1001);
w = lagrange_weights(x);
pn = lagrange_eval_naive(z, x, y, w);

subplot(1,2,1)
plot(z,f(z) - pn);

subplot(1,2,2)
plot(z,f(z),z,pn)

%%
f = @(x) sin(x);

for n = 10:10:100
    x = linspace(-pi,pi,n);
    y = f(x);
    z = linspace(-pi,pi,1001);

    w = lagrange_weights(x);
    pn = lagrange_eval_naive(z, x, y, w);
    
    subplot(1,2,1)
    plot(z,f(z) - pn);
    
    subplot(1,2,2)
    plot(z,f(z),z,pn,x,y,'*')
    title(n)
    pause
end
