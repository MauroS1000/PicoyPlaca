# Etapa 1: Compilación del código de Angular
FROM node:18-alpine AS build
WORKDIR /app
COPY package*.json ./
RUN npm install
COPY . .
RUN npm run build --configuration=production

# Etapa 2: Servidor web Nginx para servir el contenido estático
FROM nginx:alpine
COPY --from=build /app/dist/pico-placa-frontend/browser /usr/share/nginx/html
EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]
