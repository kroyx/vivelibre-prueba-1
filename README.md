# Prueba Técnica Vivelibre

Este proyecto contiene la solución propuesta a la primera parte de la
prueba técnica de backend de Vivelibre

Para la creación del proyecto se ha usado:
- Java 21
- Spring Boot 3
- Docker

### Despliegue con Docker
Para desplegar la aplicación en un contenedor Docker se proporciona
el archivo `compose.yaml` con la configuración necesaria para el despliegue.

Bastaría con ejecutar el siguiente comando:

```bash
docker compose up -d
```

### Creación de la imagen Docker
Para la creación de la imagen Docker de la aplicación se proporcionan
dos opciones:

1. Crear la imagen partiendo del Dockerfile proporcionado:
```
docker build -t [<USUARIO>/]<NOMBRE_IMAGEN>[:TAG] <DOCKERFILE_PATH>
```
2. Usar Spring Boot Maven plugin:
```bash
./mvnw spring-boot:build-image
```
