# Argo CD GitOps Implementation Plan

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:subagent-driven-development (recommended) or superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** Add Argo CD based GitOps deployment and DevSecOps hardening for the Pico y Placa project.

**Architecture:** GitHub Actions remains responsible for build, tests, security scanning, image publishing, SBOM/provenance, signing, and GitOps tag updates. Argo CD owns Kubernetes deployment by syncing the declarative manifests under `gitops/overlays/dev`.

**Tech Stack:** Spring Boot 3.5, Angular 21, Docker, Kubernetes, Kustomize, Argo CD, Trivy, Cosign, OWASP ZAP.

---

### Task 1: Backend Probe and Pico y Placa Tests

**Files:**
- Create: `picoyplaca/src/test/java/com/mauro/picoyplaca/service/PicoPlacaServiceTest.java`
- Create: `picoyplaca/src/test/java/com/mauro/picoyplaca/controller/PicoPlacaControllerTest.java`
- Modify: `picoyplaca/src/main/java/com/mauro/picoyplaca/service/PicoPlacaService.java`
- Modify: `picoyplaca/src/main/java/com/mauro/picoyplaca/controller/PicoPlacaController.java`

- [x] Add failing tests for inclusive time windows and `/api/health`.
- [x] Run focused tests and confirm expected failures.
- [x] Implement inclusive time-window logic and health endpoint.
- [x] Re-run focused tests and confirm they pass.

### Task 2: Runtime Configuration

**Files:**
- Create: `picoyplaca/src/main/java/com/mauro/picoyplaca/config/CorsConfig.java`
- Modify: `picoyplaca/src/main/resources/application.properties`
- Modify: `pico-placa-frontend/src/app/services/placa.service.ts`
- Create: `pico-placa-frontend/nginx.conf`
- Modify: `pico-placa-frontend/Dockerfile`

- [x] Move CORS origins to `CORS_ALLOWED_ORIGINS`.
- [x] Use relative frontend API path `/api/validar`.
- [x] Add Nginx reverse proxy to the backend Kubernetes service.

### Task 3: GitOps and Argo CD

**Files:**
- Create: `gitops/base/*.yaml`
- Create: `gitops/overlays/dev/kustomization.yaml`
- Create: `gitops/argocd/application.yaml`

- [x] Create namespace, deployments, services, probes, resources, and container security contexts.
- [x] Create Kustomize dev overlay with image tags.
- [x] Create Argo CD `Application` with auto-sync, prune, self-heal, and namespace creation.

### Task 4: DevSecOps Pipeline

**Files:**
- Modify: `.github/workflows/devsecops-pipeline.yml`

- [x] Build and test backend.
- [x] Build and audit frontend dependencies.
- [x] Run Trivy SAST, secret, vulnerability, and config scans.
- [x] Build and push Docker images tagged by commit SHA.
- [x] Generate SBOM/provenance and sign images with Cosign.
- [x] Update GitOps desired state.
- [x] Deploy with Argo CD and run OWASP ZAP DAST.

### Task 5: Hygiene and Documentation

**Files:**
- Create: `.gitignore`
- Modify: `picoyplaca/Dockerfile`
- Create: `docs/argocd-devsecops.md`

- [x] Remove generated Maven artifacts from Git tracking.
- [x] Document Argo CD installation, registration, and verification commands.
- [x] Verify backend tests, frontend build, audit gate, and rendered Kustomize manifests.
