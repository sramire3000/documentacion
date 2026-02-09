# INSATALL POSTGRESQL

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


