# 🗓️ Sistema de Turnos - OO2 - Grupo 22

## 📌 Tabla de Contenidos
- [Descripción](#-descripción)
- [Requisitos](#-requisitos)
- [Configuración](#-configuración)
  - [Variables de Entorno](#variables-de-entorno)
  - [Base de Datos](#base-de-datos)
- [Instalación](#-instalación)
- [Uso](#-uso)
  - [Datos Iniciales](#datos-iniciales)
  - [Usuarios de Prueba](#usuarios-de-prueba)
- [Funcionalidades](#-funcionalidades)
- [Documentación API](#-documentación-api)
- [Estructura del Proyecto](#-estructura-del-proyecto)
- [Contribución](#contribución)

---

## 🌟 Descripción
Sistema de gestión de turnos desarrollado para la materia **Orientación a Objetos 2** de la Universidad Nacional de Lanús. Permite la gestión completa de clientes, empleados, servicios y turnos con autenticación **JWT** y envío de confirmaciones por email.

---

## 💻 Requisitos
- Java 17+
- MySQL 8.0+ o MariaDB
- Maven 3.6+
- Git

---

## ⚙️ Configuración

### Variables de Entorno
Configurar en `src/main/resources/application.properties` o como variables del sistema:

```properties
# Database
mysql://localhost:3306/sistemaTurnos?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC
DB_USERNAME=root
DB_PASSWORD=root

# JWT
JWT_SECRET=pEknuCzE+HPLvMdbKt3lgixHTfQkMPbczXYM6v8D76I=

# Email (para confirmación de turnos)
EMAIL_USER=unlaturnos@gmail.com
EMAIL_PASSWORD=zsls jfrs brre vdvm

### Base de Datos  
Crear la base de datos ejecutando:

DB_URL=jdbc:mysql://localhost:3306/sistemaTurnos?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC;DB_USERNAME=root;DB_PASSWORD=root;EMAIL_USER=unlaturnos@gmail.com;EMAIL_PASSWORD=zsls jfrs brre vdvm;JWT_SECRET=pEknuCzE+HPLvMdbKt3lgixHTfQkMPbczXYM6v8D76I= 

```sql
CREATE DATABASE sistematurnos CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

El esquema se generará automáticamente al iniciar la aplicación gracias a la siguiente propiedad:

```
spring.jpa.hibernate.ddl-auto=update
```

---

## 🚀 Instalación

1. Clonar el repositorio:

```bash
git clone https://github.com/lucadelavia/SistemaTurnos-OO2-G22.git
cd SistemaTurnos-OO2-G22
```

2. Configurar las variables en `application.properties`.

3. Compilar y ejecutar:

```bash
mvn spring-boot:run
```

---

## 🖥️ Uso

### Datos Iniciales  
El sistema incluye un `DataSeeder` que carga datos de prueba al iniciar:

- 3 profesores (empleados)
- 3 alumnos (clientes)
- Especialidades y servicios básicos
- 1 establecimiento (UNLa) con 2 sucursales
- Turnos de prueba para cada alumno

### Usuarios de Prueba

| Rol      | Email                                      | Contraseña      |
|----------|--------------------------------------------|------------------|
| ADMIN    | admin@unla.edu.ar                          | admin123         |
| EMPLEADO | alejandravranic@unla.edu.ar                | alejandra123     |
| EMPLEADO | oscarruina@unla.edu.ar                     | oscar123         |
| EMPLEADO | gustavosiciliano@unla.edu.ar               | gustavo123       |
| CLIENTE  | mohamedvalentinab@alumno.unla.edu.ar       | valentina123     |
| CLIENTE  | lucadelavia@alumno.unla.edu.ar             | luca123          |
| CLIENTE  | svsdrubolini@alumno.unla.edu.ar            | sebastian123     |

---

## 🛠️ Funcionalidades

- ✅ ABM completo de todas las entidades  
- 🗓️ Sistema de turnos con validación de disponibilidad  
- 📧 Envío de confirmaciones por email  
- 🔐 Autenticación JWT  
- 👥 Roles y permisos (ADMIN, EMPLEADO, CLIENTE)  
- 📊 Dashboard administrativo  
- 📱 API REST completa  

---

## 📚 Documentación API

La documentación Swagger está disponible en:  
http://localhost:8080/swagger-ui/index.html

---

## 📂 Estructura del Proyecto

```
src/
├── main/
│   ├── java/
│   │   └── com/sistematurnos/
│   │       ├── config/          # Configuraciones
│   │       ├── controller/      # Controladores
│   │       ├── dto/             # Data Transfer Objects
│   │       ├── entity/          # Entidades JPA
│   │       ├── exception/       # Excepciones personalizadas
│   │       ├── repository/      # Repositorios
│   │       ├── service/         # Lógica de negocio
│   │       └── SistemaTurnosApplication.java
│   └── resources/
│       ├── static/              # CSS/JS
│       ├── templates/           # Vistas Thymeleaf
│       └── application.properties
```

