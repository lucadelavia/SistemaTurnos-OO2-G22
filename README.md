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


---

## ▶️ Cómo correr el proyecto

1. Ejecutá la clase principal `SistemaTurnosApplication.java` desde tu IDE.
2. Una vez iniciado, accedé a la aplicación en tu navegador:

http://localhost:8080


---

## 🧪 Funcionalidades principales

- ABM de **Clientes**, **Empleados**, **Servicios**, **Turnos** y **Sucursales**
- Asignación de **empleados a especialidades**
- **Alta de turnos** con validación de disponibilidad horaria
- **Envío de confirmación por email** (simulado desde consola)
- **Manejo de errores** con excepciones personalizadas
- Soporte para **vistas HTML** (Thymeleaf) o **REST API** (JSON)

---


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

## 📂 Estructura del proyecto

├── controller/ # Controladores (web o REST)
├── entity/ # Entidades del dominio (JPA)
├── service/ # Lógica de negocio
├── repository/ # Acceso a base de datos (Spring Data JPA)
├── exception/ # Excepciones personalizadas
├── templates/ # Vistas Thymeleaf (HTML)
├── static/ # Archivos estáticos (CSS, JS)
├── resources/
│ ├── application.properties
│ └── data.sql # (opcional) carga de datos iniciales
└── SistemaTurnosApplication.java
