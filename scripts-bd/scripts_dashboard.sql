-- =======================
-- Scripts Dashboard
-- =======================

-- =============================== Margem de Lucro (%) por Produto =======================================
-- MAIOR MARGEM
SELECT 	  nome
		, ROUND(((precoFinal - custoProducao) / precoFinal * 100), 2) AS margemLucro
FROM Produto
ORDER BY margemLucro DESC
LIMIT 1;

-- MENOR MARGEM
SELECT 	  nome
		, ROUND(((precoFinal - custoProducao) / precoFinal * 100), 2) AS margemLucro
FROM Produto
ORDER BY margemLucro ASC
LIMIT 1;
-- =======================================================================================================

-- ======================== Ranking de Produtos mais vendidos por Período ================================
SELECT    p.nome												AS nomeProduto
		, SUM(ip.quantidade) 									AS quantidadeVendida
        , ctp.nome												AS categoriaProduto
FROM Pedido pd 
JOIN Item_Pedido ip 											ON ip.fkPedido = pd.id
JOIN Produto p 													ON ip.fkProduto = p.id
JOIN Categoria_Produto ctp 										ON ctp.id = p.fkCategoriaProduto 
WHERE pd.dataPedido 											BETWEEN '2025-10-01' AND '2025-10-31'
AND pd.status 													= 'FINALIZADO'
GROUP BY p.id, p.nome, ctp.nome
ORDER BY SUM(ip.quantidade) DESC;
-- =======================================================================================================

-- =============================== Vendas Totais por Período =============================================
SELECT 	  COUNT(DISTINCT pd.id) 								AS quantidadePedidos
		, SUM(ip.quantidade) 									AS quantidadeProdutosVendidos
		, ROUND(SUM(ip.precoUnitario * ip.quantidade), 2) 			AS faturamento
		, ROUND(SUM(ip.custoProducao * ip.quantidade)) 			AS custos
        , DATE(pd.dataRetirada)									AS dataPedido
FROM Pedido pd
JOIN Item_Pedido ip 											ON ip.fkPedido = pd.id
JOIN Produto p 													ON p.id = ip.fkProduto
WHERE pd.dataPedido 											BETWEEN '2025-11-01' AND '2025-11-30'
AND pd.status 													= 'FINALIZADO'
GROUP BY DATE(pd.dataRetirada);
-- =======================================================================================================

-- ============================= Produto mais vendido no Período =========================================
SELECT p.nome 													AS produtoMaisVendido
FROM Pedido pd
JOIN Item_Pedido ip 											ON ip.fkPedido = pd.id
JOIN Produto p 													ON p.id = ip.fkProduto
WHERE pd.dataPedido 											BETWEEN '2025-10-01' AND '2025-10-31'
AND pd.status 													= 'FINALIZADO'
ORDER BY ip.quantidade DESC
LIMIT 1;
-- =======================================================================================================

-- ======================= Dia(s) da Semana com mais Vendas no Período ===================================
-- SELECT DAYNAME(pd.dataPedido) 									AS diaSemanaComMaisVendas
-- FROM Pedido pd
-- JOIN Item_Pedido ip 											ON ip.fkPedido = pd.id
-- JOIN Produto p 													ON p.id = ip.fkProduto
-- WHERE pd.dataPedido 											BETWEEN '2025-11-01' AND '2025-11-30'
--   AND pd.status													= 'FINALIZADO'
-- GROUP BY DAYNAME(pd.dataPedido), pd.dataPedido
-- ORDER BY COUNT(DISTINCT pd.id) DESC
-- LIMIT 1;

SELECT diaSemana, totalVendas
FROM (
    SELECT 
        DAYNAME(pd.dataPedido) AS diaSemana,
        COUNT(DISTINCT pd.id) AS totalVendas
    FROM Pedido pd
    JOIN Item_Pedido ip ON ip.fkPedido = pd.id
    WHERE pd.dataPedido BETWEEN '2025-11-01' AND '2025-11-30'
      AND pd.status = 'FINALIZADO'
    GROUP BY DAYNAME(pd.dataPedido)
) AS vendas
WHERE totalVendas = (
    SELECT MAX(totalVendas)
    FROM (
        SELECT 
            COUNT(DISTINCT pd.id) AS totalVendas
        FROM Pedido pd
        JOIN Item_Pedido ip ON ip.fkPedido = pd.id
        WHERE pd.dataPedido BETWEEN '2025-11-01' AND '2025-11-30'
          AND pd.status = 'FINALIZADO'
        GROUP BY DAYNAME(pd.dataPedido)
    ) AS max_vendas
);
-- =======================================================================================================