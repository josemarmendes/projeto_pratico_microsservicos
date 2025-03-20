Principais dependências instaladas para desenvolvimento do projeto:
  - Apache Maven 3.8.6
  - Jdk 17.0.14
  - Docker 24.0.2

Principais tecnologias e frameworks utilizados:

  - Spring Boot: é um framework baseado no Spring que simplifica a criação de aplicações Java, reduzindo a necessidade de configuração manual. Ele oferece inicialização rápida, configuração automática e incorpora boas práticas de desenvolvimento.

  - Spring Cloud Netflix: é um conjunto de ferramentas desenvolvidas pela Netflix para criar microsserviços escaláveis e resilientes. Inclui componentes como Eureka (serviço de descoberta), Ribbon (balanceamento de carga) e Hystrix (tolerância a falhas).

  - Spring Cloud OpenFeign: é uma biblioteca que simplifica a comunicação entre microsserviços utilizando REST. Ele permite a criação de clientes HTTP declarativos e integrados ao Spring Boot.

  - Spring Data JPA: é um subprojeto do Spring que simplifica a interação com bancos de dados relacionais utilizando a API Java Persistence (JPA). Ele permite a criação de repositórios com métodos padronizados, reduzindo a necessidade de consultas SQL manualmente escritas.

  - Spring WebFlux: é um framework reativo para construção de aplicações assíncronas e não bloqueantes, baseado no modelo de programação reativa. Ele é uma alternativa ao tradicional Spring MVC e foi projetado para fornecer maior escalabilidade e eficiência no processamento de requisições, especialmente em arquiteturas de microsserviços e sistemas de alto desempenho.

  - Hibernate: é um framework de ORM (Object-Relational Mapping) que facilita a persistência de objetos Java em bancos de dados relacionais. Ele reduz o uso de SQL direto ao utilizar uma abordagem baseada em entidades e anotações.

  - Lombok: é uma biblioteca Java que reduz a verbosidade do código eliminando a necessidade de escrever métodos como getters, setters e construtores manualmente, usando anotações.

  - RabbitMQ: é um message broker de alto desempenho baseado no protocolo AMQP (Advanced Message Queuing Protocol). Ele é amplamente usado para comunicação assíncrona entre microsserviços .

  - Docker: é uma plataforma de conteinerização que permite empacotar aplicações e suas dependências em containers leves e portáteis. É amplamente utilizado para desenvolvimento, teste e implantação de aplicações em ambientes escaláveis.

  - API Gateway: é um padrão arquitetural que atua como ponto de entrada único para um conjunto de microsserviços, gerenciando autenticação, roteamento e balanceamento de carga. No ecossistema Spring, o Spring Cloud Gateway é uma das soluções mais populares.

  - Keycloak: é um sistema de gerenciamento de identidade e acesso (IAM) de código aberto, que permite autenticação centralizada, SSO (Single Sign-On) e autorização baseada em funções (RBAC) para aplicações.

  - Insomnia: é uma ferramenta popular para testes de APIs REST e GraphQL. Ele permite a criação, organização e envio de requisições HTTP de maneira intuitiva, facilitando o desenvolvimento e depuração de serviços web. Além disso, oferece suporte a variáveis de ambiente, autenticação e exportação de coleções de requisições, tornando-se uma alternativa poderosa para desenvolvedores que precisam testar integrações de APIs de forma eficiente.

Comandos úteis do Docker :
  - docker pull: baixa uma imagem docker da internet.
  - docker images: lista as imagens.
  - docker image rm <id ou nome da imagem>: deleta uma imagem.
  - docker run <nome da imagem>: inicializa um container a partir de uma imagem.
  - docker ps: mostra todos os containers docker rodando na sua instancia.
  - docker ps -a: mostra todos os containers, parados ou rodando na sua instância.
  - docker container rm <id ou nome do container>: deleta um container.
  - docker stop <id ou nome do container>: para um container (sem deletá-lo, você poderá reiniciá-lo novamente mais tarde.
  - docker start <id ou nome do container>: inicializa um container parado anteriormente.
  - docker logs <id ou nome do container>: visualizar os logs de um container

Inicializando o RabbitMQ
Antes de iniciar, o que é o RabbitMQ? É um servidor de mensageria open source desenvolvido em Erlang, implementado para trabalhar com mensagens em um protocolo
denominado Advanced Message Queuing Protocol (AMQP). Ele possibilita lidar com o tráfego de mensagens de forma rápida e confiável, além de ser compatível com diversas
linguagens de programação, possuir interface de administração nativa e ser multiplataforma. Em suma é um serviço de Mensageria bastante utilizado e bem simples de
aprender. Para iniciá-lo a partir do docker utilize o comando:
  - docker run -it --rm --name rabbitmq -p 5672:5672 -p 15672:15672 rabbitmq:4.0-management

Este comando irá inicializar um container a partir da versão 4.0 do RabbitMQ, na sua máquina HOST o painel irá escutar na porta 15672, logo, depois de iniciar o container, você pode acessar o painel através do endereço:
- http://localhost:15672
- Por padrão o painel do RabbitMQ pode ser acessado com o login e senha “guest”, ou seja, coloque “guest” tanto no login como na senha

Perceba que o RabbitMQ possui 2 portas que podem ser expostas pra fora do container: a 15672 e a 5672, a primeira é a porta onde acessamos o painel administrativo mostrado
acima e o segundo é a porta onde as aplicações se conectam ao serviço de mensageria do RabbitMQ, então é necessário expor as duas para conseguir utilizá-lo.

Keycloak:
- Patrocinado pela Red Hat, o Keycloak é um software open source de um servidor JBoss feito para trabalhar em conjunto com sua aplicação em implementações mais comuns de autenticação e autorização. Caso as configurações padrão não te atendam, existem várias
  configurações e customizações que podem ser feitas para adequar o funcionamento ao seu sistema.
- Para subir uma instância do Keycloak a partir do docker, utilizamos o seguinte comando:
  - docker run -p 8080:8080 -e KC_BOOTSTRAP_ADMIN_USERNAME=admin -e KC_BOOTSTRAP_ADMIN_PASSWORD=admin quay.io/keycloak/keycloak:26.1.4 start-dev

- Aqui neste comando docker run, temos um parâmetro diferente, que é o “-e”, este parâmetro significa variável de ambiente (e de enviroment). Neste container do Keycloak estamos passando 2 variável de ambiente para dentro do container, 
a variável “KC_BOOTSTRAP_ADMIN_USERNAME” que serve para indicar qual será o login do usuário administrador e a segunda variável é o “KC_BOOTSTRAP_ADMIN_PASSWORD”, que seta qual será a senha do administrador, 
guarde elas para acessar o painel do Keycloak. Normalmente o nome da imagem fica no final do comando docker run, porém,  a imagem deste comando é a seguinte: quay.io/keycloak/keycloak:26.1.4

- Logo após o nome da imagem temos o comando “start-dev”, este é um parâmetro de inicialização do container, ou seja, ele irá startar a partir da imagem e irá rodar em modo “dev” (desenvolvimento). Este é um parâmetro obrigatório.
Para acessar o painel do Keycloak acesse a porta 8080 da sua máquina HOST ou a porta que você indicou no parâmetro “-p”: http://localhost:8080

Sites e referências:
  
  Site oficial do Spring Cloud:
  - https://spring.io/projects/spring-cloud
  
  Site oficial do Spring Boot:
  - https://spring.io/projects/spring-boot
  
  Site oficial do RabbitMQ:
  - https://www.rabbitmq.com/
  
  Site oficial do Keycloak:
  - https://www.keycloak.org/
  
  Site oficial do Docker:
  - https://www.docker.com/

