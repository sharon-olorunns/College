function pn = lagrange_eval_naive(z, x, y, w)

pn = zeros(size(z));

% sum over data points
for k = 1:length(x)
    
    % compute the Lk Lagrange Polynomial via product
    Lk = 1;
    for i = 1:length(x)
        if i ~= k
            Lk = Lk .* (z - x(i));
        end
    end
    Lk = w(k)*Lk;
  
    % add the kth term onto the cummulative sum
    pn = pn + y(k) * Lk;
end