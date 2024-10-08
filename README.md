Purpose(s):
1) Create something that a D&D Dungeon Master can use to:
    - Track hit points, conditions, etc of players and creatures -- if one creature/player has concentration and is tied to a condition, then both are tracked
    - Track round and initiative
    - Possibility of adding automated rolling of dice
2) Just to create a project for display as part of my portfolio.

Suggested mechanism for getting the project running (NOTE: writing this from memory and I tend to gloss over some instructions that some people may need more details on -- because I am doing this from memory, some of these instructions are high level and may not make much sense unless you are already familiar with this sort of thing):
- Install Chocolatey or Homebrew (ie this is a Package Manager - Chocolatey is Windows, Homebrew is Mac)
- Install Docker via Chocolatey/Homebrew
- Install Terraform via Chocolatey/Homebrew
- Install Postman via Chocolatey/Homebrew
- Start Docker Desktop
- Download this project
- mvnw clean package -Dtest="CreatureControllerContainerTests"
- mvnw spring-boot:run
- docker network inspect dnd-battle_dnd-battle-network
- look for the dnd-database entry and take note of the IP address
- in Docker Desktop, click the link under Port(s) for pgadmin (ie 5051:5050)
- login to pgadmin using credentials specified in compose.yaml for dnd-battle-pgadmin
- open Query editor in pgadmin and insert the data from data-postgresql.sql
- open Postman
- import collection located in the project postman directory
- begin testing!

NOTE: I realize this is going to be a huge project, but I have never let anything like that stop me.  How do you eat an elephant? One bite at a time.
NOTE: There really isn't much functionality to the project at this time.  So far, I have really just been setting the project up for future success.  However, it does have the ability to look at a sample list of creatures.
NOTE: The command above [mvnw clean package -Dtest="CreatureControllerContainerTests"] contains the -Dtest because the project is currently set to use Testcontainers as the testing style -- if we leave that off, then the build fails because it tries to run ALL tests. The other tests are for the embedded H2 database while running not in a container

Currently there are two branches for this code:
1) Non-Reactive branch is the "main" branch
2) Reactive branch is the "reactive" branch

Current Features:
1) Terraform creation of the Docker infrastrusture
2) Docker (ie Dockerfile and compose.yaml)
3) Testing leveraging JUnit in 4 different Test Driven Development (TDD) styles
  - Standard way that developers normally test (ie all beans autoconfigured, web server, all requests routed through the web server, etc)
  - Mock MVC (ie all beans autoconfigured, no web server, all requests routed directly to the endpoint, etc)
  - Web MVC (ie beans are manually configured and responses are configured via Mockito, no web server, all requests routed directly to the endpoint, etc)
  - Testcontainers (ie Docker)
4) Automatic database initialization of the schema (ie it does NOT automatically initialize the data -- that must be done manually)
5) Postman collection of requests

Future Features:
1) TLS
2) Loose coupling via Kafka/RabbitMQ
3) Add additional testing style for Behavior Driven Development (BDD) leveraging Cucumber
4) Registering an account
5) Logging into application
6) Adding players to an account
7) Creating battles associated with an account
8) Creating a UI/UX
