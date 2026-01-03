# Docker Composer SQL SERVER 2019


### 2017
```
docker run -d -e "ACCEPT_EULA=Y" -e "MSSQL_SA_PASSWORD=Password123" --network network_dev --name server-mssql-2017 -p 1433:1433 mcr.microsoft.com/mssql/server:2017-latest 
```

### Docker command 2019
```
docker run -d -e "ACCEPT_EULA=Y" -e "MSSQL_SA_PASSWORD=Password123" --network network_dev --name server-mssql-2019 -p 1433:1433 rapidfort/microsoft-sql-server-2019-ib:latest 
```


