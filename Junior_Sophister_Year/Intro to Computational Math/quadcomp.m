function y=quadcomp(f,a,b,n,nodes)
h=(b-a)/n;
% obtain nodes from basic rule
compnodes=a+quadcompnodes(nodes,n)*h;
% obtain weights from basic rule
compweights=quadcompweights(nodes,n)*h;
% evaluate integrands at nodes
nnodes=length(compnodes);
values=zeros(nnodes,1);
for i=1:nnodes,
    values(i)=feval(f,compnodes(i));
end
% apply quadrature rule
y=compweights*values;
