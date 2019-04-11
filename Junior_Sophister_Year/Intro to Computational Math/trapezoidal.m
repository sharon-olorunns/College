f = @(x)x*sin(x);
a = 0;
b = pi/2;
n = 100;
h = (b - a)/n;
s = 0.5*(f(a) + f(b));
for i = 1 : n-1
  s = s + f(a + i*h);
end
I = h * s