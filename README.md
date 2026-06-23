# SOA + SOAP + Docker - Servicios base

Este proyecto contiene 3 servicios independientes con estilo microservicios, pero expuestos por SOAP para que luego un ESB pueda registrarlos, enrutar, transformar y orquestar la integración SOA.

## Servicios

| Servicio | Puerto | WSDL |
|---|---:|---|
| Partidos Service | 8081 | http://localhost:8081/ws/partidos.wsdl |
| Selecciones Service | 8082 | http://localhost:8082/ws/selecciones.wsdl |
| Estadísticas Service | 8083 | http://localhost:8083/ws/estadisticas.wsdl |

## Ejecutar con Docker

```bash
docker compose up --build
```

Verificar salud:

```bash
curl http://localhost:8081/actuator/health
curl http://localhost:8082/actuator/health
curl http://localhost:8083/actuator/health
```

## Probar WSDL en navegador

- http://localhost:8081/ws/partidos.wsdl
- http://localhost:8082/ws/selecciones.wsdl
- http://localhost:8083/ws/estadisticas.wsdl

## Probar SOAP con curl

### Partidos por selección

```bash
curl -X POST http://localhost:8081/ws   -H "Content-Type: text/xml; charset=utf-8"   -d @requests/get-partidos-by-seleccion.xml
```

### Selección por id

```bash
curl -X POST http://localhost:8082/ws   -H "Content-Type: text/xml; charset=utf-8"   -d @requests/get-seleccion-by-id.xml
```

### Estadísticas por partido

```bash
curl -X POST http://localhost:8083/ws   -H "Content-Type: text/xml; charset=utf-8"   -d @requests/get-estadisticas-by-partido.xml
```

## Apagar

```bash
docker compose down
```

## ESB agregado

El proyecto ahora incluye un cuarto contenedor:

```text
esb-service: puerto 8080
```

Este ESB expone rutas REST en `/api/esb` y consume internamente los 3 servicios SOAP:

```text
REST client -> ESB REST -> SOAP Partidos/Selecciones/Estadísticas
```

Ver detalles en `README-ESB.md`.

## Cliente REST agregado

Se agregó `rest-client-service` en el puerto `8090`. Este servicio consume el ESB por REST y no llama directamente a los servicios SOAP.

Documentación adicional: `README-REST-CLIENT.md`.

Colección Postman: `requests/postman/soa-esb-rest-client.postman_collection.json`.
