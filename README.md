# CardsAndClients
In ConnectFactory.class change username in this line:
```
public static final String jdbcUsername = "yourUsername";
```
- Start PostgreSql server
- Create Database cards_clients_01
- Add in Idea or Eclipse Database -> Data source -> PostgreSql
```
Driver: PostgreSql
Host: localhost
Port: 5432
UserName: system username
Password: not necessary
URL: jdbc:postgresql://localhost:5432/cards_clients_01
```

Then start CreateTables.class:
```
Path: postgre-db/src/main/java/com/z8q/postgredb/CreateTables.java
```

In Application.class you can choose the way to store data (txt or PostgreSql).
