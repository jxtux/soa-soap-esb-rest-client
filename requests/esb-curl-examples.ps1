# Probar ESB sin seguridad
curl http://localhost:8080/api/esb/health
curl http://localhost:8080/api/esb/auth/token

# Cabeceras requeridas para rutas de negocio
$headersUser = @{
  "Authorization" = "Bearer esb-token-2026"
  "X-Client-Certificate" = "SOA-CLIENT-CERT-2026"
  "X-Correlation-Id" = "demo-001"
}

$headersAdmin = @{
  "Authorization" = "Bearer esb-admin-token-2026"
  "X-Client-Certificate" = "SOA-CLIENT-CERT-2026"
  "X-Correlation-Id" = "demo-admin-001"
}

Invoke-RestMethod -Uri "http://localhost:8080/api/esb/catalog" -Headers $headersUser
Invoke-RestMethod -Uri "http://localhost:8080/api/esb/partidos" -Headers $headersUser
Invoke-RestMethod -Uri "http://localhost:8080/api/esb/partidos/P001/detalle" -Headers $headersUser
Invoke-RestMethod -Uri "http://localhost:8080/api/esb/selecciones/PER/resumen" -Headers $headersUser
Invoke-RestMethod -Uri "http://localhost:8080/api/esb/grupos/A/tabla" -Headers $headersUser
Invoke-RestMethod -Uri "http://localhost:8080/api/esb/governance/policies" -Headers $headersAdmin
Invoke-RestMethod -Uri "http://localhost:8080/api/esb/governance/audits" -Headers $headersAdmin
