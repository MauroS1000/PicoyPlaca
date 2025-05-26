# Sistema de Validación de Pico y Placa 

Aplicación web para verificar si un vehículo puede circular en Quito, Ecuador, según las restricciones de pico y placa.

## Requerimientos Cumplidos 
- **Consulta por placa y fecha/hora** mediante formulario web.
- **Validación de fecha/hora** (no puede ser anterior al momento actual).
- **Lógica de negocio en backend** (Spring Boot) con respuestas estructuradas en JSON.
- **Comunicación REST** entre frontend (Angular) y backend.
- **Listo para producción** con configuración CORS y manejo de errores.

## Tecnologías Utilizadas 
- **Frontend**: Angular 19, Bootstrap 5, TypeScript
- **Backend**: Spring Boot 3.2, Java 17, Maven
- **Herramientas**: Postman (pruebas API), Git

## Instalación y Ejecución 

### Prerrequisitos
- Java 17+ ([Descargar](https://adoptium.net/))
- Node.js 18+ y npm ([Descargar](https://nodejs.org/))
- Angular CLI 19+ (`npm install -g @angular/cli`)

Pasos:

Backend (Spring Boot):

bash
cd backend
./mvnw spring-boot:run
API disponible en: http://localhost:8080/api/validar

Frontend (Angular):

bash
cd frontend
npm install
ng serve
Aplicación disponible en: http://localhost:4200

Configuración ⚙
Variable	Valor por Defecto	Descripción
BACKEND_URL	http://localhost:8080	URL del backend Spring Boot
CORS_ORIGINS	http://localhost:4200	Orígenes permitidos para CORS
Uso de la API 
Ejemplo con curl:
bash
curl -X POST "http://localhost:8080/api/validar" \
  -d "placa=ABC-1234&fechaHora=2024-05-20T07:30"
Respuesta Exitosa:
json
{
  "placa": "ABC-1234",
  "permitido": false,
  "mensaje": "NO puedes circular. Restricción aplicada."
}
Despliegue en Producción 
Frontend:

bash
cd frontend
ng build --prod
Los archivos estáticos se generan en dist/

Backend:

```bash
cd backend
./mvnw clean package
java -jar target/*.jar
Docker (opcional):

dockerfile
# Backend
FROM eclipse-temurin:17-jdk
COPY target/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
Estructura del Proyecto 
pico-y-placa/
├── picoyplaca/               # Código Spring Boot
│   ├── src/
│   └── pom.xml
├── pico-placa-frontend/              # Código Angular
│   ├── src/
│   └── package.json
└── README.md
