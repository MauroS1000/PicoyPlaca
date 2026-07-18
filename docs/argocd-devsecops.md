# Argo CD y DevSecOps

Este proyecto usa GitHub Actions para integracion continua y Argo CD para despliegue continuo GitOps.

## Flujo

1. GitHub Actions ejecuta pruebas del backend y build del frontend.
2. Trivy escanea codigo, secretos, dependencias y manifiestos Kubernetes.
3. Docker Buildx publica imagenes del backend y frontend en Docker Hub.
4. Las imagenes se etiquetan con el SHA del commit para trazabilidad.
5. Cosign firma las imagenes publicadas.
6. El workflow actualiza `gitops/overlays/dev/kustomization.yaml`.
7. Argo CD sincroniza el cluster desde `gitops/overlays/dev`.
8. OWASP ZAP ejecuta DAST sobre el frontend desplegado.

## Instalacion de Argo CD

```bash
kubectl create namespace argocd
kubectl apply -n argocd --server-side --force-conflicts -f https://raw.githubusercontent.com/argoproj/argo-cd/stable/manifests/install.yaml
kubectl -n argocd rollout status deployment/argocd-repo-server
kubectl -n argocd rollout status statefulset/argocd-application-controller
```

## Registrar la Aplicacion

```bash
kubectl apply -f gitops/argocd/application.yaml
kubectl -n argocd get application picoyplaca-dev
```

## Verificar Despliegue

```bash
kubectl -n picoyplaca get pods
kubectl -n picoyplaca get svc
kubectl -n picoyplaca rollout status deployment/picoyplaca-backend
kubectl -n picoyplaca rollout status deployment/picoyplaca-frontend
```

El estado esperado en Argo CD es `Synced` y `Healthy`.
