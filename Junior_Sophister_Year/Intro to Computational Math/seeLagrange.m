function  L = seeLagrange(xNodes,varargin)
% SEELAGRANGE - creates a plot showing k lagrange interpolating polynomials
%      L = seeLagrange(xNodes,k)
%      L = seeLagrange(xNodes)  shows all
%
%    xNodes: vector of nodes of interpolant
%    k:      vector of indices which interpolating functions to plot
%    L:      a matrix whos columns are lagrange polynomials
%    this version calls Lagrange for to calculate L
   
   N = length(xNodes);  % total number of lagrange interpolating polynomials
   if nargin == 1  % check to see if index k is passed in 
       k=1:N;
   else
       k=varargin{1};
   end
   
   xNodes = xNodes(:); % force to column vector
   x=linspace(min(xNodes),max(xNodes));

   L = lagrange(x,xNodes);
   yNodes=eye(N);
   
   figure;
   plot(x,L(:,k),'LineWidth',[2]);
   hold on;
   plot(xNodes,yNodes(:,k),'o','LineWidth',[2]);
   grid;
   set(gca,'fontweight','bold','fontsize',[14]);
   xlabel('x');
   ylabel('y');
   title(sprintf('Lagrange Interpolating Polynomials: n=%d',N));
   legendStr={};
   for i=1:length(k);
       legendStr{i}=sprintf('k=%d',k(i));
   end
   legend(legendStr,'Location','BestOutside');
   
