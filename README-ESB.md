# ESB Service - Integración SOA

Este servicio agrega un ESB en Java 21 + Spring Boot + Docker.

## Responsabilidades implementadas

- Enrutamiento / Routing: decide qué servicio SOAP llamar según la ruta REST.
- Transformación: transforma REST/JSON a SOAP/XML y SOAP/XML a REST/JSON.
- Mediación de protocolos: recibe HTTP REST y consume SOAP sobre HTTP.
- Seguridad técnica: exige token Bearer y cabecera `X-Client-Certificate`.
- Exposición de servicios: expone endpoints REST centralizados en `/api/esb`.
- Autenticación: valida token estático de usuario o administrador.
- Autorización: rutas de gobernanza requieren token ADMIN.
- Certificados / tokens: simula certificado técnico con `X-Client-Certificate` y token Bearer.
- Logs: registra entrada, salida, ruta y duración.
- Trazabilidad: usa `X-Correlation-Id` y lo propaga a los servicios SOAP.
- Errores y reintentos: reintenta llamadas SOAP ante fallas temporales y devuelve errores JSON.
- Gobernanza: expone catálogo, rutas, políticas y auditoría.

## Tokens de prueba

Usuario:

```text
Authorization: Bearer esb-token-2026
X-Client-Certificate: SOA-CLIENT-CERT-2026
```

Administrador:

```text
Authorization: Bearer esb-admin-token-2026
X-Client-Certificate: SOA-CLIENT-CERT-2026
```

## Endpoints principales

```text
GET http://localhost:8080/api/esb/health
GET http://localhost:8080/api/esb/auth/token
GET http://localhost:8080/api/esb/catalog
GET http://localhost:8080/api/esb/partidos
GET http://localhost:8080/api/esb/partidos/P001/detalle
GET http://localhost:8080/api/esb/selecciones/PER/resumen
GET http://localhost:8080/api/esb/selecciones/PER/partidos
GET http://localhost:8080/api/esb/estadisticas/partido/P001
GET http://localhost:8080/api/esb/grupos/A/tabla
GET http://localhost:8080/api/esb/governance/policies
GET http://localhost:8080/api/esb/governance/audits
```

## Levantar todo

```powershell
docker compose down
docker compose up --build -d
docker ps
```

Deben quedar corriendo 4 contenedores:

```text
partidos-service        8081
selecciones-service     8082
estadisticas-service    8083
esb-service             8080
```


## Nota de corrección

El ESB incluye la dependencia `jackson-databind` porque el filtro de seguridad `TokenSecurityFilter` usa `ObjectMapper` para devolver errores JSON cuando faltan tokens o certificados.
