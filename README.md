🅿️ - API Estacionamento

- Aluno: Reginaldo Santos Gomes

Este é um projeto de um sistema de estacionamento desenvolvido em Java utilizando o framework Spring Boot. O sistema permite o controle de entrada e saída de veículos, com recursos adicionais, como desconto para estadias longas e cobrança de multa por atraso.

📖 - História de Usuário

Como usuário do sistema, Pedro deseja ter um controle eficiente dos veículos estacionados em seu estacionamento. Ele precisa saber quais veículos estão estacionados, identificar o proprietário de cada veículo e registrar a data e hora de entrada e saída de cada um. Além disso, o sistema deve implementar a seguinte política de cobrança:

- A cada 50 horas pagas, o veículo recebe 5 horas de estacionamento gratuitas.
- O estacionamento possui 50 vagas para carros, 5 vagas para vans e 20 vagas para motos.
- O horário de atendimento é das 06:00 às 20:00 horas.
- Após o horário de atendimento, é cobrada uma multa por minuto de atraso.


🧠 - Funcionalidades Principais
- O sistema de estacionamento automatizado oferece as seguintes funcionalidades:

1 - Registro de Entrada: Permite registrar a entrada de um veículo no estacionamento, coletando as seguintes informações:

- Tipo de veículo (carro, van ou moto)
- Placa do veículo
- Nome do proprietário
- Data e hora de entrada

2 - Registro de Saída: Permite registrar a saída de um veículo do estacionamento, informando a placa do veículo e a data e hora de saída.

3 - Consulta de Veículos Estacionados: Permite verificar quais veículos estão atualmente estacionados no estacionamento, exibindo as seguintes informações para cada veículo:

- Tipo de veículo
- Placa do veículo
- Nome do proprietário
- Data e hora de entrada

4 - Cálculo de Cobrança: Calcula o valor a ser pago pelo cliente com base no tempo de estadia. Aplica o desconto de 5 horas grátis a cada 50 horas pagas.

💻 - Requisitos de Sistema
Para executar o sistema de estacionamento automatizado, você precisa ter os seguintes requisitos instalados em sua máquina:

- Java Development Kit (JDK) 8 ou superior
- Maven
- Banco de dados (por exemplo, MySQL, PostgreSQL) configurado corretamente
- IDE Java (recomendado: IntelliJ IDEA ou Eclipse)

⚙️ - Configuração e execução

Siga as etapas abaixo para configurar e executar o projeto do estacionamento automatizado:

1 - Clone o repositório do projeto do GitHub:

git clone https://github.com/Reginalld/Estacionamento.git

2 - Abra o projeto em sua IDE Java preferida.

3 - Configure as informações de banco de dados no arquivo application.properties para que o Spring Boot possa se conectar corretamente ao banco de dados escolhido.

4 - Compile e execute o projeto usando o Maven:
