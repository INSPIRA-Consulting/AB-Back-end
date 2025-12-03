-- =======================
-- Scripts Dashboard
-- =======================

-- =============================== Margem de Lucro (%) por Produto =======================================
-- MENOR MARGEM
SELECT 	  nome
		, ROUND(((precoFinal - custoProducao) / precoFinal * 100), 2) AS margemLucro
FROM Produto
WHERE precoFinal > 0
ORDER BY margemLucro ASC
LIMIT 1;

-- MAIOR MARGEM
SELECT 	  nome
		, ROUND(((precoFinal - custoProducao) / precoFinal * 100), 2) AS margemLucro
FROM Produto
ORDER BY margemLucro DESC
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
		, ROUND(SUM(ip.precoUnitario * ip.quantidade), 2) 		AS faturamento
		, ROUND(SUM(ip.custoProducao * ip.quantidade)) 			AS custos
        , DATE(pd.dataRetirada)									AS dataPedido
FROM Pedido pd
JOIN Item_Pedido ip 											ON ip.fkPedido = pd.id
JOIN Produto p 													ON p.id = ip.fkProduto
WHERE pd.dataPedido 											BETWEEN '2025-11-01' AND '2025-11-30'
AND pd.status 													= 'FINALIZADO'
GROUP BY DATE(pd.dataRetirada);
-- =======================================================================================================

-- ============================= Produto mais Vendido no Período =========================================
SELECT p.nome 													AS produtoMaisVendido
FROM Pedido pd
JOIN Item_Pedido ip 											ON ip.fkPedido = pd.id
JOIN Produto p 													ON p.id = ip.fkProduto
WHERE pd.dataPedido 											BETWEEN '2025-10-01' AND '2025-10-31'
AND pd.status 													= 'FINALIZADO'
GROUP BY p.id, p.nome
ORDER BY SUM(ip.quantidade) DESC
LIMIT 1;
-- =======================================================================================================

-- ======================================= Vendas por Dia da Semana ======================================
SELECT    CASE WEEKDAY(pd.dataPedido)
				WHEN 0 THEN 'Segunda-feira'
				WHEN 1 THEN 'Terça-feira'
				WHEN 2 THEN 'Quarta-feira'
				WHEN 3 THEN 'Quinta-feira'
				WHEN 4 THEN 'Sexta-feira'
				WHEN 5 THEN 'Sábado'
				WHEN 6 THEN 'Domingo'
		  END diaSemana
		, COUNT(pd.id) qtdVendas
FROM Pedido pd
WHERE pd.dataPedido BETWEEN '2025-10-01' AND '2025-10-31'
	AND pd.status = 'FINALIZADO'
GROUP BY  WEEKDAY(pd.dataPedido)
		, CASE WEEKDAY(pd.dataPedido)
				 WHEN 0 THEN 'Segunda-feira'
				 WHEN 1 THEN 'Terça-feira'
				 WHEN 2 THEN 'Quarta-feira'
				 WHEN 3 THEN 'Quinta-feira'
				 WHEN 4 THEN 'Sexta-feira'
				 WHEN 5 THEN 'Sábado'
				 WHEN 6 THEN 'Domingo'
		  END
ORDER BY WEEKDAY(pd.dataPedido);
-- =======================================================================================================

-- ==================== Recomendações de Produtos para os Feriados por Categoria de Produto ==============
SELECT    p.nome
		, cp.nome
FROM Pedido pd
JOIN Item_Pedido ip 
	ON ip.fkPedido = pd.id
JOIN Produto p 
	ON ip.fkProduto = p.id
JOIN Categoria_Produto cp 
	ON p.fkCategoriaProduto = cp.id
WHERE pd.status = 'FINALIZADO'
        AND pd.dataPedido BETWEEN '2024-12-18' AND '2024-12-25'				-- Semana do Feriado (D-7)
        AND p.id = (
            SELECT p2.id
            FROM Pedido pd2
            JOIN Item_Pedido ip2 ON ip2.fkPedido = pd2.id
            JOIN Produto p2 ON ip2.fkProduto = p2.id
            WHERE pd2.status = 'FINALIZADO'
            AND p2.fkCategoriaProduto = cp.id
            AND pd2.dataPedido BETWEEN '2024-12-18' AND '2024-12-25'		-- Semana do Feriado (D-7)
            GROUP BY p2.id
            ORDER BY SUM(ip2.quantidade) DESC
            LIMIT 1
        )
GROUP BY cp.id, p.id, p.nome, cp.nome;
-- =======================================================================================================