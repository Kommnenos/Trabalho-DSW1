# Trabalho-DSW1 - DCMotors

Proposta: Criação de um sistema de venda de automóveis, possuindo operações relativas a clientes, administradores e lojas.

# Descrição:
Sistema que engloba lojas, clientes e administradores. Possuindo fluxos específicos para cada um dos papéis disponíveis.

As lojas podem cadastrar excluir e editar veículos

Os clientes podem visualizar e fazer propostas de aquisição dos veículos 

Os administradores podem cadastrar, editar ou excluir lojas ou clientes

# Arquitetura - MVC:
Pastas divididas em modelo, visão e controlador.

# Divisão de tarefas:
- Lucas Gabriel Velloso: Criação da classe de Veiculo, Das entidade abstrata, fluxo de upload de arquivos.
- Gabriel Henrique Rodrigues: Criação da classe de Proposta, Cliente, fluxo de login, autenticação e criação de propostas
- Pedro Lealdini: Criação da interface inicial, homepage, fluxo de cadastro, listagem de veículos e disparo de email.

# Passos para execução:
 - Criar um database no PostgreSQL com o nome "DCMotors"
 - Configurar o arquivo application.properties com usuário e senha corretos do PostgreSQL
 - Executar 'mvn spring-boot:run' na raiz do projeto

# Observação:
- Implementados todos os pontos do arquivo de requisitos, com exceção do envio de contrapropostas e link da reunião (ainda em desenvolvimento)
