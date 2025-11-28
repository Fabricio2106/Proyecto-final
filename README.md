 TechSolution API

## Descripción del Proyecto
TechSolutions S.A. ha desarrollado una **plataforma web para la gestión de ventas, inventario y reportes financieros**, orientada a pymes peruanas y latinoamericanas.
La aplicación permite:
- Procesar pagos con múltiples pasarelas (PayPal, Yape, Plin).
- Control de acceso seguro a reportes financieros.
- Gestión de inventario con alertas automáticas por bajo stock.
- Registro y reversión de acciones de pedidos.
- Aplicación de políticas de precios flexibles.
- Navegación eficiente en catálogos de productos.
## Patrones de Diseño Aplicados
| Funcionalidad | Patrón | Descripción |
|---------------|--------|------------|
| Pagos en línea | Adapter | Integra múltiples pasarelas de pago. |
| Control de reportes | Proxy | Protege acceso según roles. |
| Inventario | Observer | Notifica si el stock cae por debajo del mínimo. |
| Procesamiento de pedidos | Command + Memento | Permite registrar y revertir acciones. |
| Políticas de precios | Strategy | Permite aplicar distintas estrategias de precios. |
| Catálogo de productos | Iterator | Permite recorrer productos de forma eficiente. |
## Controladores
| Controlador | Estado | Notas |
|-------------|--------|-------|
| ProductController | Funcional | CRUD completo + notificaciones stock bajo. |
| CustomerController | Funcional | CRUD básico. |
| OrderController | Funcional | Creación, procesamiento, cancelación, descuentos; historial de acciones parcialmente implementado. |
| PaymentController | Funcional | Procesamiento de pagos y actualización de estado. |
| UserController | Parcial | CRUD básico, control de acceso parcialmente implementado. |
| ReportController | Parcial | Proxy aplicado, acceso controlado, algunas pruebas funcionan. |
## Endpoints Principales
### Producto
**POST /api/products**
```json
{
  "producto": "Mouse Inalámbrico",
  "descripción": "Mouse ergonómico",
  "precio": 50.0,
  "stock": 50,
  "categoria": "Electrónica",
  "stockMinimo": 5
}
Cliente
POST /customers
json
Copiar código
{
  "nombre": "Juan Perez",
  "correo": "juan@mail.com"
}
Orden
POST /orders

json
Copiar código
{
  "orderDate": "2025-11-27T22:12:10.777",
  "status": "PENDIENTE",
  "customer": { "id": 1 },
  "payment": { "amount": 250.0 },
  "orderItems": [
    { "quantity": 2, "product": { "id": 1 } },
    { "quantity": 1, "product": { "id": 2 } }
  ]
}
Pago
PUT /payments/{paymentId}/estado?estado=PAGADO
POST /payments/order/{orderId}

json
Copiar código
{
  "monto": 1500,
  "metodo": "yape"
}


Swagger /v3/api-docs
Para explorar y probar la API:

Swagger UI: http://localhost:8080/swagger-ui.html

JSON de documentación: http://localhost:8080/v3/api-docs

Requisitos Previos
Java 17+

Maven 3.8+

Base de datos PostgreSQL (o H2 para pruebas)

IDE recomendado: VSCode o IntelliJ IDEA

Cómo Ejecutar
Clonar el repositorio:

bash
Copiar código
git clone https://github.com/Fabricio2106/Proyecto-final.git
cd gestion_app
Configurar application.properties con la base de datos.

Ejecutar:

bash
Copiar código
mvn clean install
mvn spring-boot:run
Probar endpoints en Swagger UI:
http://localhost:8080/swagger-ui.html

Consideraciones
User y Report Controller funcionan parcialmente.

Pasarelas de pago son simuladas; pueden integrarse reales agregando la lógica en PaymentGateway.

Notificaciones de stock bajo usan Observer (ProductSubject).

Equipo de Desarrollo