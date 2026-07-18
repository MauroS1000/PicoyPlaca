# Sistema de Validacion de Pico y Placa

Aplicacion web para verificar si un vehiculo puede circular en Quito, Ecuador, segun las restricciones de pico y placa.

## Tecnologias

- Backend: Spring Boot 3.5, Java 17, Maven
- Frontend: Angular 21, Bootstrap 5, TypeScript
- Contenedores: Docker
- Orquestacion: Kubernetes
- GitOps/CD: Argo CD
- DevSecOps: Trivy, Cosign, OWASP ZAP, SBOM/provenance de Docker Buildx

## Ejecucion Local

Backend:

```bash
cd picoyplaca
./mvnw spring-boot:run
```

Frontend:

```bash
cd pico-placa-frontend
npm ci
npm start
```

El frontend local usa `proxy.conf.json` para enviar `/api` al backend en `http://localhost:8081`.

API local: `http://localhost:8081/api/validar`

## API

```bash
curl -X POST "http://localhost:8081/api/validar" \
  -H "Content-Type: application/x-www-form-urlencoded" \
  -d "placa=PBC-1231&fechaHora=2026-07-20T07:30"
```

Respuesta:

```json
{
  "placa": "PBC-1231",
  "permitido": false,
  "mensaje": "NO puedes circular. Hoy es lunes y tu placa termina en 1. Horarios restringidos: 06:00-09:30 y 16:00-20:30"
}
```

Health check:

```bash
curl http://localhost:8081/api/health
```

## CI/CD con Argo CD

El flujo oficial esta definido en `.github/workflows/devsecops-pipeline.yml`.

1. GitHub Actions compila backend/frontend y ejecuta pruebas.
2. Trivy ejecuta analisis SAST, secretos, vulnerabilidades y configuracion Kubernetes.
3. Docker Buildx publica imagenes con tag del commit SHA.
4. Cosign firma las imagenes para integridad de artefactos.
5. El workflow actualiza `gitops/overlays/dev/kustomization.yaml`.
6. Argo CD sincroniza los manifiestos GitOps hacia Kubernetes.
7. OWASP ZAP ejecuta DAST contra la aplicacion desplegada.

La documentacion operativa esta en `docs/argocd-devsecops.md`.

## Estructura

```text
PicoyPlaca/
├── .github/workflows/devsecops-pipeline.yml
├── docs/
├── gitops/
│   ├── argocd/application.yaml
│   ├── base/
│   └── overlays/dev/
├── pico-placa-frontend/
└── picoyplaca/
```
