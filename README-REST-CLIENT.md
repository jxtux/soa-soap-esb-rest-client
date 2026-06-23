# Cliente REST que consume el ESB

Este proyecto agrega un quinto contenedor llamado `rest-client-service`.

## Arquitectura final

```text
Cliente externo / Postman
        |
        | REST / JSON
        v
rest-client-service  puerto 8090
        |
        | REST / JSON + Authorization + X-Client-Certificate + X-Correlation-Id
        v
esb-service          puerto 8080
        |
        | SOAP / XML
        v
partidos-service        puerto 8081
selecciones-service     puerto 8082
estadisticas-service    puerto 8083
```

## Rol del cliente REST

El cliente REST no consume SOAP directamente. Su responsabilidad es actuar como consumidor REST del ESB:

- Expone endpoints REST para el consumidor final.
- Consume los endpoints REST del ESB.
- Envía al ESB el token Bearer configurado.
- Envía al ESB la cabecera técnica `X-Client-Certificate`.
- Propaga o genera `X-Correlation-Id` para trazabilidad.
- Maneja errores si el ESB no está disponible.
- Se despliega en su propio contenedor Docker.

## Servicios y puertos

| Servicio | Tipo | Puerto |
|---|---|---:|
| partidos-service | SOAP | 8081 |
| selecciones-service | SOAP | 8082 |
| estadisticas-service | SOAP | 8083 |
| esb-service | REST hacia afuera / SOAP hacia adentro | 8080 |
| rest-client-service | REST consumidor del ESB | 8090 |

## Levantar todo

Desde la carpeta raíz del proyecto:

```powershell
docker compose down --remove-orphans
docker compose up --build -d
```

Verifica:

```powershell
docker ps
```

Deben aparecer 5 contenedores:

```text
partidos-service
selecciones-service
estadisticas-service
esb-service
rest-client-service
```

## Probar el cliente REST

```powershell
curl.exe http://localhost:8090/api/client/health
```

```powershell
curl.exe http://localhost:8090/api/client/partidos
```

```powershell
curl.exe http://localhost:8090/api/client/partidos/P001/detalle
```

```powershell
curl.exe http://localhost:8090/api/client/selecciones/PER/resumen
```

```powershell
curl.exe http://localhost:8090/api/client/grupos/A/tabla
```

## Gobernanza desde el cliente REST

Estas rutas del cliente REST consumen las rutas admin del ESB usando el token admin configurado dentro del contenedor:

```powershell
curl.exe http://localhost:8090/api/client/admin/governance/routes
curl.exe http://localhost:8090/api/client/admin/governance/policies
curl.exe http://localhost:8090/api/client/admin/governance/audits
```

## Postman

Importa este archivo en Postman:

```text
requests/postman/soa-esb-rest-client.postman_collection.json
```

La colección incluye dos carpetas:

1. `Cliente REST - consume ESB`
2. `ESB directo - pruebas de seguridad`

## Configuración Docker del cliente

El `docker-compose.yml` configura el cliente así:

```yaml
rest-client-service:
  build: ./rest-client-service
  container_name: rest-client-service
  ports:
    - "8090:8090"
  environment:
    ESB_BASE_URL: http://esb-service:8080
    ESB_USER_TOKEN: esb-token-2026
    ESB_ADMIN_TOKEN: esb-admin-token-2026
    ESB_CLIENT_CERTIFICATE: SOA-CLIENT-CERT-2026
  depends_on:
    - esb-service
```

## Nota sobre seguridad

`X-Client-Certificate` es una simulación académica de certificado técnico. En producción se reemplazaría por HTTPS/mTLS con certificados reales X.509.
