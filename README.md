## Desafio

Em Fortaleza, diversas empresas de produção de gás e derivados de petróleo estão instauradas no bairro Mucuripe, em Fortaleza, 
há mais de 30 anos.Visando manter a população local alerta sobre situações de riscos eminentes de incidentes nas mesmas, e
aproveitando-se da popularidade dos smartphones, as empresas decidiram, juntas, disponibilizarem um aplicativo que mantém a população avisada sobre tais riscos,
afim de emitir notificações que alertem quando situações de perigo estejam prestes a ocorrer nas empresas, garantindo o refúgio da
população para zonas fora de risco (à beira mar), bem como emitir alerta para o batalhão de corpo de bombeiros, situado nas proximidades.

O aplicativo, exibe uma lista de empresas cadastradas. Cada empresa reporta o status corrente da produção de seus produtos, assim que solicitado
pelo aplicativo, via requisição HTTP a uma API, que alimenta o aplicativo, com os seguintes níveis de perigo:

* **OK** : sem riscos na produção;
* **WARNING** : superaquecimento dos maquinários: equipes internas da empresa em questão são acionadas via aplicativo para investigar o caso; 
* **DANGER** : princípio de incêndio : população é notificada via aplicativo a sair de suas casas e buscar locais seguros corpo de bombeiros é alertado sobre incêndio.

Desenvolva uma API que forneça ao APP os reports de incidentes das empresas para os usuarios finais.

## Critérios:

A API deve considerar 3 tipos de usuário:

* *População:* moradores das comunidades nos entornos das fábricas;  
* *Sistema:* interfaces que monitoram os maquinários de produção das empresas cadastradas;
* *Admin:* administradores de sistema que podem atualizar a API.

**[1]** APP cujo o tipo de usuário final se enquadre em "População" pode apenas consultar a API (via HTTP GET).
Cenário de exemplo: APP consulta lista de empresas a cada x minutos, verificando empresas e status de incidentes. Usuário pode favoritar uma das empresas (como por exemplo a mais próxima de sua casa). Deste modo, a API consulta apenas o status daquela empresa. O APP, ao ser instalado e pedir permissão de acesso as informações basicas do usuário, pode cadastrar
o usuário na API via HTTP POST.

*Listar empresas:*

    GET /empresas
    [
    {
      "cnpj" : "03373373371264",
      "fantasia" : "Nacional Gas",
      "contato" : "8532630001",
      "nivelPerigo" : "OK"
    },
    {
      "cnpj" : "00073372371264",
      "fantasia" : "Petrolusa",
      "contato" : "8532630002",
      "nivelPerigo" : "OK"
    },
    {
      "cnpj" : "03373373371264",
      "fantasia" : "Texaco",
      "contato" : "8532630004",
      "nivelPerigo" : "WARNING",
      "comentario" : "Principio de incêndio próximo à refinaria B1",
      "status" : "RESOLVIDO"
    },
    {
      "cnpj" : "03373373371264",
      "fantasia" : "Tropigás",
      "contato" : "8532640002",
      "nivelPerigo" : "OK"
    },
    {
      "cnpj" : "03373373371264",
      "fantasia" : "Shell",
      "contato" : "8532640001",
      "nivelPerigo" : "OK"
    }
    ]

*Consultar empresa favorita:*

    GET /empresas/03373373371264
    {
      "cnpj" : "03373373371264",
      "fantasia" : "Shell",
      "nivelPerigo" : "OK",
      "comentario" : "Tudo certo"
    }
    
*Cadastrar novo usuario*

    POST /usuarios
    {
      "nome": "José Carlos",
      "email" : "josecarlos@etc.br",
      "tipo" : "POPULACAO"
    }

**[2]** APP cujo o tipo de usuário final se enquadre em "Admin" / "Sistema", pode consultar e atualizar as informações da API 
(via HTTP GET / POST / PUT / DELETE), informações tais como: lista de usuários cadastrados, criação de mensagens de alerta / atualização de mensagens de alerta, etc. Para diferenciar usuários, é interessante que o sistema forneça API key's ou algum outro meio para identificar que o usuráio do APP possui permissão de edições (PUT / POST / DELETE).
Cenário de exemplo: forçar mensagem de alerta via API, para que, quando consumidas pelos APP's, alertem aos usuários sobre incidentes. 
Sugestão de interface:

*Registrar incidente:*

    POST /empresas/{cnpj}
    {
    "nivelPerigo" : "DANGER",
    "comentario" : 'Explosão de caldeira no setor 1',
    "data" : "2019/02/05 00:15:30",
    "status" : "Em aberto"
    }

*Atualizar incidente:*

    PUT /empresas/{cnpj}
    {
      "idAlerta" : 23
      "nivelPerigo" : "DANGER",
      "comentario" : 'Explosão de caldeira no setor 1',
      "data" : "2019/02/05 00:15:30",
      "status" : "Resolvido",
      "dataResolucao" : "2019/02/05 00:17:15"
    }
    
*Registrar usuários da API:*

    POST /usuarios
    {
      "nome": "José Carlos",
      "email" : "josecarlos@etc.br",
      "tipo" : "POPULACAO"
    }

*Recuperar usuários que utilizizam a API:*
  
    GET /usuarios
    [
      {"id" : 1, 
      "nome": "José Carlos",
      "email" : "josecarlos@etc.br",
      "tipo" : "POPULACAO"
      },
      {"id" : 2, 
      "nome": "Patrícia Ramos",
      "email" : "pattyr@tal.br",
      "tipo" : "POPULACAO"
      },
      {"id" : 3, 
      "nome": "Zé Cantor",
      "email" : "zecantorr@texaco.co",
      "tipo" : "ADMIN"
      },
      {"id" : 4, 
      "nome": "SYS_SHELL",
      "email" : "system@shell.co",
      "tipo" : "SISTEMA"
      }
      ...
    ]

**[3]** Atributos nulos / vazios são suprimidos pela API. Exemplo: se status não possui comentario associado, então o campo 'comentario' não é retornado pela API.  

**[4]** Usuarios "Admin" / "Sistema" podem lista os reports das empresas, filtrando por empresa , nivelPerigo , status. 
*Obs:* Embora seja uma listagem de carater de leitura, esta busca não deve ser realizada por usuarios comum, para evitar possiveis sobrecargas nas bases.


## Tecnologias a serem avaliadas:

A API deve ser implementada considerando as seguintes tecnologias:

*   Java 8 ou superior;
*   Stack Spring (Boot / Data / Security / MVC ).
*   Cenarios de Testes (Junit4 ou +)

## Sugestões:

* Interceptar requisiçes e validar métodos - [HandlerInterceptor](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/HandlerInterceptor.html);

* Database in-memory - [H2](http://www.h2database.com/html/main.html);

* Validar permisses de acesso - [Pŕe/Post Authorize](http://websystique.com/spring-security/spring-security-4-method-security-using-preauthorize-postauthorize-secured-el/)

* [JSON Web Token](https://jwt.io/)
## Entrega

Enviar solução via GitHub (sugerido Fork neste projeto) e enviar para o email: adail.dux *arroba* gmail *dot* com
