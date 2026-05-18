# Requisitos del Trabajo: API 1 (Principal – Completa)

## 📋 Contexto del Proyecto
* **Nombre de la API:** `api-comercio`
* **Tecnologías:** Spring Boot 3.5.14, Java 21, Maven
* **Base de Datos:** MySQL local (puerto 3306), Base de datos: `comercio_db`
* **Package Base:** `com.comercio.API_Comercio`
* **Descripción:** Sistema simple de gestión comercial con las siguientes características.

---

## 🛠️ Dependencias del Proyecto
* Spring Web
* Spring Data JPA
* Spring Security
* MySQL Driver
* Lombok
* Validation
* Spring Boot DevTools

---

## 🏗️ Arquitectura y Componentes Obligatorios

La aplicación debe estructurarse estrictamente en los siguientes paquetes bajo el package base:

* **`entity`**: Entidades JPA configuradas con Lombok.
* **`repository`**: Interfaces `JpaRepository` con al menos una query personalizada por entidad.
* **`dto`**: Objetos de transferencia de datos separados para `Request` y `Response`, incluyendo anotaciones de validación (`@Valid`).
* **`mapper`**: Clases para el mapeo manual entre Entidad ↔ DTO.
* **`service`**: Capa de lógica de negocio gestionada con `@Transactional`.
* **`controller`**: Endpoints REST que implementen `GET`, `POST`, `PUT` y `DELETE` usando `@PathVariable`, `@RequestParam` y `@RequestBody`.
* **`security`**: Configuración completa de seguridad con JWT (filtros, utilidades y configuración).
* **`exception`**: Manejador global de errores utilizando `@ControllerAdvice`.

---

## 🗄️ Modelo de Datos y Entidades

Las entidades deben crearse respetando el siguiente orden de dependencia jerárquica:

### 1. Categoria (Sin dependencias)
* **id**: `int` (PK, Auto-incremental)
* **nombre**: `varchar` (Unique, Not Null)
* **descripcion**: `varchar`

### 2. Usuario (Sin dependencias)
* **id**: `bigint` (PK, Auto-incremental)
* **username**: `varchar` (Unique, Not Null)
* **password**: `varchar` (Not Null)
* **rol**: `varchar` (Valores permitidos: `ADMIN` / `EMPLEADO`)
* **fecha_registro**: `datetime`

### 3. Producto (Depende de Categoria)
* **id**: `bigint` (PK, Auto-incremental)
* **nombre**: `varchar` (Not Null)
* **precio**: `decimal` (Not Null)
* **stock**: `int` (Not Null)
* **activo**: `bool` (Default: `true`)
* **creado_en**: `datetime`
* **categoria_id**: `int` (FK - Relación ManyToOne)

### 4. Venta (Depende de Usuario)
* **id**: `bigint` (PK, Auto-incremental)
* **fecha**: `datetime` (Not Null)
* **total**: `decimal` (Not Null)
* **estado**: `varchar` (Valores permitidos: `PENDIENTE` / `COMPLETADA` / `ANULADA`)
* **usuario_id**: `bigint` (FK - Relación ManyToOne)

### 5. DetalleVenta (Relación compleja, depende de Producto y Venta)
*Representa la relación ManyToMany compleja con atributos propios.*

* **id**: `bigint` (PK, Auto-incremental)
* **cantidad**: `int` (Not Null)
* **precio_unitario**: `decimal` (Not Null — congela el precio al momento de la venta)
* **subtotal**: `decimal` (Not Null)
* **producto_id**: `bigint` (FK)
* **venta_id**: `bigint` (FK)

### Resumen de Relaciones
* **Categoria** `OneToMany` → **Producto**
* **Usuario** `OneToMany` → **Venta**
* **Venta** + **Producto** → **DetalleVenta** *(Relación ManyToMany compleja con atributos propios)*

---

## ⚙️ Reglas de Negocio (Capa Service)

> ⚠️ **Importante:** Estas reglas deben validarse e implementarse rigurosamente en los servicios correspondientes antes de realizar operaciones en la base de datos.

1. **Gestión de Stock:** Al crear un `DetalleVenta`, verificar que haya stock suficiente y descontarlo automáticamente.
2. **Inmutabilidad de Ventas:** Una `Venta` en estado `COMPLETADA` no se puede eliminar ni modificar.
3. **Baja Lógica de Productos:** Un `Producto` con ventas asociadas no se elimina físicamente, solo se desactiva con `activo = false`.

---

## 🌐 Endpoints Mínimos Requeridos

Misma estructura de endpoints requerida para **Categoria**, **Venta** y **DetalleVenta**, tomando como base los siguientes métodos de **Producto** y **Autenticación**:

### Productos
* `GET /api/productos?activo=true` — Uso de QueryParam
* `GET /api/productos/{id}` — Uso de PathVariable
* `POST /api/productos` — Uso de RequestBody
* `PUT /api/productos/{id}`
* `DELETE /api/productos/{id}`

### Autenticación y Seguridad
* `POST /api/auth/registro`
* `POST /api/auth/login`

---

## 🧪 Requisitos de Testing

El desarrollo debe asegurar una buena cobertura general siguiendo los siguientes lineamientos:

* **Tests de Service:** Implementados con **JUnit 5 + Mockito** siguiendo el patrón **AAA** (*Arrange, Act, Assert*).
* **Tests de Controller:** Implementados utilizando `@WebMvcTest` + `MockMvc`.
* **Mínimo requerido:** Al menos un test por cada Service y Controller principal.

---

## 🪜 Orden de Construcción Estricto

> 🛑 **Regla de oro:** Verificar que la app levante sin errores antes de pasar al siguiente paso.

1. `application.properties` + levantar app vacía.
2. Entidades en orden de dependencia.
3. Repositories.
4. DTOs + Mappers.
5. Services.
6. Controllers.
7. Security JWT.
8. Tests.