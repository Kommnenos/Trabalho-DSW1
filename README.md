# Trabalho-DSW1 - DCMotors

Proposta: Criacao de um sistema de venda de automoveis, possuido operacoes relativas a clientes, administradores e lojas.

# Descricao:
Sistema que engloba lojas, clientes e administradores. Possuindo fluxos especificos para cada um dos papeis disponiveis.

As lojas podem cadastrar excluir e editar veiculos

Os clientes podem visualizar e fazer prostas de aquisicao dos veiculos veiculos

Os administradores podem cadastrar, editar ou excluir lojas ou clientes

# Aquitetura - MVC:
Pastas dividias em modelo, visao e controlador.

# Divisao de tarefas:
- Lucas Gabriel Velloso: Criacao da classe de Veiculo, Das entidade abstrata, fluxo de upload de arquivos.
- Gabriel Henrique Rodrigues: Criacao da classe de Proposta, Cliente, fluxo de login, autenticacao e criacao de propostas
- Pedro Lealdini: Criacao da interface inicial, homepage, fluxo de cadastro e listagem de veiculos e disparo de email.

# Comando para execucao:
 - mvn spring-boot:run
