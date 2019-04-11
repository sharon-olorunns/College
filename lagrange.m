function L = lagrange(x,xNodes)
% LAGRANGE - returns lagrange interpolating matrix L such that y=L*yNodes is the interpolating polynomial
%   L = lagrange(x,xNodes)
%
%   x-        vector of points at which to calculate interpolant
%   xNodes -  vector of positions through which interpolating polynomial passes
%
%   L:         - L matrix whose columns L(:,k) are the k't interpolating Lagrange polynomial evaluated at points x
%
   
   N = length(xNodes);
   x = x(:); % force to column vector
   xNodes = xNodes(:);
   e = ones(size(xNodes));
   
   A = 1./(eye(N) + (e*xNodes'-xNodes*e'));  % calculate difference matrix
   L = x*e' - ones(size(x))*xNodes';  % calculate L = x - xNodes matrix
   for  i= 1: length(x)
      L(i,:) = lagrangeWeights(L(i,:), A);
   end
   return 
      

function Lrow = lagrangeWeights(xMinusXNodes,A)
% LAGRANGEWEIGHTS - returns row vector of Lagrange polynomial weights L_k(x)
%         Lrow = lagrangeWeights(xMinusXnodes, A)
%  Helper function to return a row vector of weights for lagrange interpolation at point x
%
%  xMinusXNodes: - row vector of x-xNodes at scalar point x
%  A             - matrix of reciprocals A_ij = 1./(x_i - x_j) with 1 on the diagonal
%
   
  [ m, n ] = size(A);
  B = repmat(xMinusXNodes',1,n);
  B = eye(m) + ~eye(m).*B;
  Lrow = prod(B.*A);
      
