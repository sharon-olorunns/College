function w = lagrange_weights(x)

% initialize the weight to 1
w = ones(size(x));

% loop through the weights
for k = 1:length(x)
    % loop through the points to compute denominator
    for i = 1:length(x)
        % excluding the point k
        if i ~= k
            w(k) = w(k) * (x(k) - x(i));
        end
    end
    
    % weight is inverse of the product
    w(k) = 1/w(k);
end