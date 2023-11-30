# foreign-exchange-arbitrage-detect
The Bellman-Ford Algorithm is an algorithm used to
calculate the shortest path between the two nodes of a
graph. The Dijkstra algorithm is much more efficient but
Bellman-Ford here is preferred as it can even handle
negative edge weights.These negative-weight cycles represent arbitrage opportunities in the market and in theory allow us to make risk-free profit.

The application assigns currencies to different vertices with the edge weight representing the exchange rate. Since the algorithm finds the minimum distance the exchange rate will be transformed by taking its logarithm and multiplying it by -1. By doing so, we will be able to find the path with the maximum return.

## reference
https://www.ijisrt.com/assets/upload/files/IJISRT20MAY047.pdf?fbclid=IwAR0AMGnTlexrXmhc53pEvJ2mdrrXltJQD94iCDsyun4zFpRyO6SQdWSzmIE
