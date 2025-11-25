window.onload = function() {   //<editor-fold desc="Changeable Configuration Block">
  // ordem desejada das tags/controllers (use os nomes exibidos no Swagger)
  var tagsOrder = [
    'Autenticação',
    'Usuários',
    'Dashboard',
    'Clientes',
    'Pedidos',
    'Itens de Pedido',
    'Detalhamento Pedido (Encomendas)',
    'Produtos',
    'Categorias de Produto',
    'Composições de Produto',
    'Receitas',
    'Tipos de Receita',
    'Ingredientes',
  ];

  // ordem desejada dos métodos (minúsculas)
  var methodsOrder = ['post', 'get', 'put', 'patch', 'delete'];

  window.ui = SwaggerUIBundle({
    url: window.location.pathname.replace(/\/swagger-ui\.html.*$/, '/v3/api-docs'),
    dom_id: '#swagger-ui',
    deepLinking: true,
    presets: [
      SwaggerUIBundle.presets.apis,
      SwaggerUIStandalonePreset
    ],
    plugins: [
      SwaggerUIBundle.plugins.DownloadUrl
    ],
    layout: "StandaloneLayout",
    configUrl: "/v3/api-docs/swagger-config",
    validatorUrl: "",
    // sorter customizado de tags (controllers)
    tagsSorter: function(tA, tB) {
      try {
        var nameA = (tA && (tA.name || tA)) || '';
        var nameB = (tB && (tB.name || tB)) || '';
        var iA = tagsOrder.indexOf(nameA);
        var iB = tagsOrder.indexOf(nameB);
        if (iA === -1 && iB === -1) return nameA.localeCompare(nameB);
        if (iA === -1) return 1;
        if (iB === -1) return -1;
        return iA - iB;
      } catch (e) {
        return 0;
      }
    },
    // sorter customizado de operações (métodos) já existente
    operationsSorter: function(opA, opB) {
      try {
        var mA = (opA.get && opA.get('method') || '').toLowerCase();
        var mB = (opB.get && opB.get('method') || '').toLowerCase();
        var iA = methodsOrder.indexOf(mA);
        var iB = methodsOrder.indexOf(mB);
        if (iA === -1 && iB === -1) return mA.localeCompare(mB);
        if (iA === -1) return 1;
        if (iB === -1) return -1;
        return iA - iB;
      } catch (e) {
        return 0;
      }
    }
  });

  ui.initOAuth({"clientId":"your-client-id","scopes":"openid profile email"});
  //</editor-fold>
};