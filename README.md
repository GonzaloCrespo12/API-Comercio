# 🛒 API Comercio - Sistema de Gestión Comercial

Esta es una API RESTful desarrollada con **Spring Boot** para la gestión de un comercio. Permite la administración completa de productos, categorías, ventas y detalles de ventas, implementando estrictas reglas de negocio y un sistema de seguridad basado en **JSON Web Tokens (JWT)**.

## 🛠️ Stack Tecnológico
* **Lenguaje:** Java 21
* **Framework:** Spring Boot 3.5.14
* **Gestor de Dependencias:** Maven
* **Base de Datos:** MySQL
* **Seguridad:** Spring Security + JWT
* **Testing:** JUnit 5, Mockito, MockMvc
* **Otras herramientas:** Lombok, Hibernate/JPA, MapStruct (Mapeo Manual de DTOs), Validation (JSR-380).

---

## 🏗️ Arquitectura del Proyecto
El proyecto respeta una arquitectura multicapa estricta, separando responsabilidades para un código limpio y mantenible:
* **`entity`**: Modelos de la base de datos mapeados con JPA.
* **`repository`**: Interfaces de acceso a datos (`JpaRepository`) con queries personalizadas.
* **`dto`**: Objetos de Transferencia de Datos (`Request` y `Response`) con validaciones.
* **`mapper`**: Lógica manual de conversión entre Entidades y DTOs.
* **`service`**: Capa central con la lógica de negocio y gestión de transacciones (`@Transactional`).
* **`controller`**: Puntos de entrada HTTP de la API REST.
* **`security`**: Filtros y utilidades para la generación y validación de tokens JWT.

---

## 📌 Reglas de Negocio Implementadas
Se implementaron exitosamente las 3 reglas de negocio obligatorias en la capa de Servicios:
1. **Gestión de Stock:** Al registrar un detalle de venta, se verifica y descuenta automáticamente el stock del producto (`DetalleVentaService`).
2. **Inmutabilidad de Ventas:** Una `Venta` en estado `COMPLETADA` no puede ser modificada, agregársele detalles, ni ser eliminada (`VentaService`).
3. **Baja Lógica de Productos:** Si un `Producto` intenta ser eliminado pero ya está asociado a ventas previas (violación de Foreign Key), el sistema captura la excepción y aplica una baja lógica (`activo = false`) en lugar de borrarlo físicamente (`ProductoService`).

---

## 🚀 Guía de Ejecución (Para el Evaluador)

### 1. Configuración de la Base de Datos
1. Asegúrese de tener un servidor MySQL corriendo (ej. XAMPP) en el puerto `3306`.
2. Cree una base de datos vacía llamada exactamente: `comercio_db`.
3. Spring Boot creará las tablas automáticamente al iniciar gracias a `spring.jpa.hibernate.ddl-auto=update`.

### 2. Levantar la Aplicación
Puede iniciar la aplicación desde su IDE (ej. VS Code o IntelliJ) ejecutando la clase `ApiComercioApplication.java`, o desde la terminal en la raíz del proyecto usando:

```bash
./mvnw spring-boot:run
```

La API estará disponible en http://localhost:8080.

### 3. Flujo de Pruebas Manuales (Thunder Client)
Siga este flujo:

1. Crear un usuario (Público):
   - `POST http://localhost:8080/api/auth/registro`
   - Body (JSON):
     ```json
     {"username": "profesor", "password": "123", "rol": "ADMIN"}
     ```

2. Iniciar Sesión (Público):
   - `POST http://localhost:8080/api/auth/login`
   - Body (JSON):
     ```json
     {"username": "profesor", "password": "123", "rol": "ADMIN"}
     ```

3. Copie el `token` devuelto en la respuesta.

4. Consumir Endpoints Protegidos:
   - Agregue en la cabecera (Header) de sus peticiones:
     `Authorization: Bearer <su_token_aqui>`.

5. Pruebe, por ejemplo:
   - `GET http://localhost:8080/api/productos?activo=true`

🧪 Testing Automatizado
El proyecto cuenta con una batería de pruebas automatizadas que cubren tanto la lógica de negocio (Servicios) como las respuestas HTTP (Controladores), utilizando el patrón AAA (Arrange, Act, Assert).

- **Unit Testing (Service):** Se usó Mockito para aislar la lógica y verificar el cumplimiento de las reglas de negocio (ej. falla de stock, bloqueo de eliminación, baja lógica).
- **Integration Testing (Controller):** Se usó MockMvc junto con `@WebMvcTest` y `@MockitoBean` (actualizado a Spring 3.5) para validar respuestas HTTP `200`, `201` y estructuras JSON saltándose temporalmente el filtro JWT para aislar la prueba.

Para ejecutar todos los tests, corra el siguiente comando en la terminal:

```bash
./mvnw test
```