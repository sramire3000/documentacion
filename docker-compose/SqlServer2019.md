# Docker Composer SQL SERVER 2019

### Docker command
```
docker run -d -e "ACCEPT_EULA=Y" -e "MSSQL_SA_PASSWORD=Password123" --network network_dev --name my-mssql-server -p 1433:1433 rapidfort/microsoft-sql-server-2019-ib:latest 
```
