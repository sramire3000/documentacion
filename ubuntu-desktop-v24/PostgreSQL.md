# INSTAL POSTGRESQL

### Update Ubuntu
```
sudo apt update
```

### Download
```
https://www.postgresql.org/download/
https://www.postgresql.org/download/linux/ubuntu/
```

### Script Cerificado
```
sudo apt install curl ca-certificates
sudo install -d /usr/share/postgresql-common/pgdg
sudo curl -o /usr/share/postgresql-common/pgdg/apt.postgresql.org.asc --fail https://www.postgresql.org/media/keys/ACCC4CF8.asc
. /etc/os-release
sudo sh -c "echo 'deb [signed-by=/usr/share/postgresql-common/pgdg/apt.postgresql.org.asc] https://apt.postgresql.org/pub/repos/apt $VERSION_CODENAME-pgdg main' > /etc/apt/sources.list.d/pgdg.list"
sudo apt update
```

### Install PostgreSQL
```
sudo apt install postgresql-18
```

# Install PgAdmin

### Download
```
https://www.pgadmin.org/download/
https://www.pgadmin.org/download/pgadmin-4-apt/
```
### Install CURL
```
sudo apt install curl
```

### Install the public key for the repository (if not done previously):
```
curl -fsS https://www.pgadmin.org/static/packages_pgadmin_org.pub | sudo gpg --dearmor -o /usr/share/keyrings/packages-pgadmin-org.gpg
```

### Create the repository configuration file:
```
sudo sh -c 'echo "deb [signed-by=/usr/share/keyrings/packages-pgadmin-org.gpg] https://ftp.postgresql.org/pub/pgadmin/pgadmin4/apt/$(lsb_release -cs) pgadmin4 main" > /etc/apt/sources.list.d/pgadmin4.list && apt update'
```

### Install for desktop mode only:
```
sudo apt install pgadmin4-desktop
```

### Create Password
```
sudo -u postgres psql
```

### Change Password postgres#
```
alter user postgres with password 'password';
```

### Register Server
```
host Name = localhost
port      = 5432
username  = postgres
```


