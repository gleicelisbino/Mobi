## Projeto Mobi 7

### Tecnologias 
* Back-end Java 8 <img alt="Kelly-Java" height="30" width="40" src="https://raw.githubusercontent.com/devicons/devicon/master/icons/java/java-plain.svg"> 
* Spring Boot
* Gradle
* Docker
* Redis
* Testes de integração para testar inserções

### Features
- [X] Endpoint para adicionar o Poi.
- [X] Endpoint para adicionar a base de dados em CSV do Poi.
- [X] Endpoint para adicionar a Posicao.
- [X] Endpoint para adicionar a base de dados em CSV da Posicao.
- [X] Endpoint para retornar a quantidade de tempo que os veículos
  passaram dentro de cada POI.
- [X] Swagger dos endpoints
- [X] Utilização da Spotless API para formatação.

 ### Arquitetura Hexagonal utilizada
- Core: Lógica central; possui regras de negócio, entidades...
- Infrastructure: Teconologias externas.
- Usecase: Casos de uso.
 
### Docker e script para automação
Scripts: docker-commands.bat e docker-commands.sh disponíveis.

### APIs de backend para inserção dos "Dados de Posição posicoes.csv"
Utilização de Multipart/form-data para "popular" o banco com o arquivo csv.
#### http://localhost:8080/positions/upload-file

### APIs de backend para inserção dos "Base de Dados de POI base_pois_def.csv"
Utilização de Multipart/form-data para "popular" o banco com o arquivo csv.
#### http://localhost:8080/pois/upload-file

### APIs de backend para inserção de novos dados de Posicionamento de Veículos
#### http://localhost:8080/positions/create
```bash
[
  {
    "placa": "TESTE001",
    "data_posicao": "Wed Dec 12 2018 00:04:03 GMT-0200 (Hora oficial do Brasil)",
    "velocidade": 0,
    "longitude": -51.469891,
    "latitude": -25.3649141,
    "ignicao": false
  },
  {
    "placa": "TESTE001",
    "data_posicao": "Wed Dec 12 2018 00:34:06 GMT-0200 (Hora oficial do Brasil)",
    "velocidade": 0,
    "longitude": -51.4699098,
    "latitude": -25.3649175,
    "ignicao": false
  }
]
```
### APIs de backend para inserção de novos dados de Pontos de Interesse
#### http://localhost:8080/pois/create
```bash
[  
 {
    "nome": "PONTO 1",
    "latitude": -25.56742701740896,
    "longitude": -51.47653363645077,
    "raio": 300
 }
]
```
### APIs de backend para retornar a quantidade de tempo que os veículos passaram dentro de cada POI.
#### http://localhost:8080/positions/vehicle-in-poi?poiNome=PONTO%202&placas=CAR0012
Otimizações:
- Utilização de CompletableFuture para acesso I/O paralelo.
- Utilização de Redis, pois o front irá realizar várias requisições idênticas de consulta.
  
### Design Patterns
- Prototype: possibilita a utilização de clonagem para minimizar problemas de concorrência.
- Strategy: possibilita a utilização de uma estratégia para lidar com a forma que os campos do arquivo CSV devem ser mapeados.

