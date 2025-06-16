# 🗓️ Sistema de Turnos - OO2 - Grupo 22

Proyecto desarrollado por el  **Grupo 22** para la materia **Orientación a Objetos 2 (OO2)** de la **Universidad Nacional de Lanús**.

Este sistema permite la gestión de turnos entre clientes, empleados, sucursales y servicios. Incluye validaciones, excepciones personalizadas y una interfaz web simple para la interacción con los datos.

---

## 🔽 ¿Cómo bajar el proyecto?

1. Asegurate de tener instalado **Git**, **Java 17 o superior**, y **Maven**.
2. Abrí una terminal y ejecutá los siguientes comandos para clonar el repositorio y posicionarte en la rama correcta:

git clone https://github.com/lucadelavia/SistemaTurnos-OO2-G22.git

3. Abrí el proyecto en tu IDE.

---

## 🛠️ Configuración previa

### 📌 1. Crear la base de datos

Debés tener instalado **MySQL** o **MariaDB**.

Luego, creá una base de datos llamada `sistematurnos` con el siguiente comando:

CREATE DATABASE sistematurnos;

Podés hacerlo desde consola o desde alguna herramienta gráfica como MySQL Workbench o DBeaver.

### 📌 2. Configurar `application.properties`

Ubicación: `src/main/resources/application.properties`

Revisá que el archivo tenga esta configuración y actualizá usuario y contraseña con tus datos locales:

spring.datasource.url=jdbc:mysql://localhost:3306/sistematurnos
spring.datasource.username=tu_usuario
spring.datasource.password=tu_contraseña
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.thymeleaf.cache=false

Variables requeridas:

JWT_SECRET=pEknuCzE+HPLvMdbKt3lgixHTfQkMPbczXYM6v8D76I=
EMAIL_PASSWORD=zsls jfrs brre vdvm

---

🧑‍💼 Usuario administrador
Para comenzar a operar el sistema es necesario tener un usuario ADMIN creado.

INSERT INTO usuario (nombre, apellido, email, password, direccion, dni, cuil, rol, estado, fecha_alta)
VALUES ('Admin', 'Sistema', 'admin@example.com', '$2a$10$hashdeejemplo', 'Dirección Admin', 12345678, '20-12345678-9', 'ADMIN', 1, NOW());

🙋‍♀️ Alta de clientes
Los clientes se registran desde el formulario web accediendo a /registro.

Una vez registrados, podrán iniciar sesión y reservar turnos desde la interfaz.

## ▶️ Cómo correr el proyecto

1. Ejecutá la clase principal `SistemaTurnosApplication.java` desde tu IDE.
2. Una vez iniciado, accedé a la aplicación en tu navegador:

http://localhost:8080


---

🧪 Funcionalidades principales
ABM de Clientes, Empleados, Servicios, Turnos y Sucursales
Asignación de empleados a especialidades
Alta de turnos con validación de disponibilidad horaria
Envío de confirmación por email (simulado desde consola o real mediante SMTP)
Manejo de errores con excepciones personalizadas
Soporte para vistas HTML (Thymeleaf) y API REST JSON
Autenticación y autorización con Spring Security
Generación de JWT y protección de rutas


---

## 📧 Configuración del envío de emails

El sistema incluye una funcionalidad para enviar **emails de confirmación de turnos** al cliente, usando un servicio de mailing con plantilla HTML (`turnoConfirmado.html`).

### 🔹 ¿Cómo funciona?

- Cuando se crea un turno correctamente, se ejecuta automáticamente un método que:
  - Verifica si el cliente tiene un email válido.
  - Carga la plantilla `turnoConfirmado.html`.
  - Envía un correo electrónico con los datos del turno.

### 🔹 ¿Qué se necesita?

Para que el envío de mails funcione realmente, debés agregar la configuración SMTP en `application.properties`.

### 🔹 Ejemplo de configuración para Gmail

spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=tu_email@gmail.com
spring.mail.password=tu_contraseña_de_aplicacion
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true

### 🔐 Crear una contraseña de aplicación en Gmail

Si usás una cuenta de Gmail y tenés la verificación en dos pasos activada, necesitás generar una contraseña de aplicación.

📌 Guía oficial de Google:  
👉 https://myaccount.google.com/apppasswords

🔒 Seguridad
Configuración con Spring Security y autenticación basada en formularios.

Login (/login) y logout (/logout)

JWT para endpoints protegidos

Roles: ADMIN, EMPLEADO, CLIENTE

📚 Swagger
El proyecto incluye documentación automática con Swagger:

📌 Cómo acceder
Una vez iniciado el proyecto, ingresá a:
http://localhost:8080/swagger-ui/index.html

🔧 Dependencia usada

<dependency>
  <groupId>org.springdoc</groupId>
  <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
  <version>2.8.6</version>
</dependency>

## ⚠️ Excepciones personalizadas

El sistema cuenta con una clase `GlobalExceptionHandler` anotada con `@ControllerAdvice`, que captura y gestiona los errores más comunes del negocio. Algunas de las excepciones definidas:

- `ClienteNoEncontradoException`
- `ClienteDuplicadoException`
- `EmpleadoNoEncontradoException`
- `TurnoNoEncontradoException`

Estas excepciones pueden ser mostradas como:
- **Vistas HTML** personalizadas (si usás `@Controller`)
- **Respuestas JSON** (si usás `@RestController`), con estado HTTP correspondiente

---

📂 Estructura del Proyecto

├── controller/           # Controladores REST
├── entity/               # Entidades JPA
├── service/              # Lógica de negocio
├── repository/           # Acceso a base de datos
├── exception/            # Excepciones personalizadas
├── dtos/                 # DTOs con record class
├── templates/            # Vistas Thymeleaf
├── static/               # Archivos estáticos (JS, CSS)
├── config/               # Seguridad, Swagger
├── resources/
│   ├── application.properties
│   └── data.sql          # (opcional) carga inicial
└── SistemaTurnosApplication.java
