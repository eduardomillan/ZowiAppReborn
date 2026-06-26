# ZowiAppReborn - Plan de Proyecto y Seguimiento (Estado a 2026-06-26)

## 1) Objetivo del proyecto
Recuperar la app Android de Zowi para que:
- Compile de forma estable con el toolchain actual.
- Arranque sin crashear en emulador/dispositivo modernos (API 33/34+).
- Mantenga funcionalidad principal mientras se corrigen artefactos heredados/decompilados.

## 2) Estado actual (resumen ejecutivo)
- Build: OK (`assembleDebug` exitoso en validaciones recientes).
- Install: OK (`adb install -r` exitoso).
- Launch inicial: OK (la app llega y se mantiene en `WelcomeViewActivity` en primer plano).
- Crash de arranque original: mitigado.
- Riesgo residual: existen muchas rutas de navegación aún no recorridas tras los últimos cambios; pueden aparecer nuevos fallos en pantallas profundas.

## 3) Problema técnico principal detectado
En gran parte del código proveniente de decompilado/histórico, hay referencias directas a IDs de recursos (`R.layout`, `R.id`, `R.color`, `R.integer`, `R.dimen`, `R.drawable`, etc.) que no siempre coinciden con el estado real de recursos en runtime.

Esto provocaba errores del tipo:
- `Resources$NotFoundException`
- `InflateException`
- `NullPointerException` tras `findViewById(...)` devolviendo `null`

## 4) Estrategia aplicada
Se adoptó una estrategia incremental y segura:
- Resolver recursos por nombre en runtime con `getIdentifier(...)` + fallback al `R.*` original.
- Introducir helpers reutilizables para evitar duplicación de lógica.
- Corregir primero ruta crítica de arranque (Splash/Welcome/MakerBox), luego extender a patrones similares.
- Validar cada iteración con ciclo corto: build -> install -> launch -> revisión de `logcat`.

## 5) Qué se ha hecho hasta ahora (alto nivel)
### 5.1 Infraestructura de despliegue y depuración
- Ajustes de tareas para automatizar emulador/build/install/launch.
- Secuencias de espera más robustas para evitar carreras de ADB/Package Manager.

### 5.2 Estabilización de arranque en Android moderno
- Mitigación en reporter de errores (Bugsnag) para evitar crash de registro de receiver en API recientes.

### 5.3 Refactor de resolución de recursos
- Nuevo enfoque de resolución dinámica en actividades base y utilidades de recursos.
- Migración de pantallas y componentes clave para no depender exclusivamente de IDs estáticos decompilados.

### 5.4 Cadena Welcome/MakerBox
- Correcciones en `MakerBox` y familia `MakerBoxDialog*`:
  - Layouts/IDs/drawables/anims/dimens con resolución por nombre.
  - Fixes defensivos ante `null` en vistas/listeners.
  - Corrección de manejo de `TypedArray` (evitar doble recycle).
- Resultado: Welcome deja de crashear en el smoke test actual.

## 6) Convenciones para colaboradores (a partir de ahora)
- Al tocar pantallas heredadas, priorizar resolución por nombre para recursos críticos de runtime.
- Mantener fallback a `R.*` cuando exista.
- Evitar cambios masivos sin validación incremental.
- Tras cada bloque de cambios, ejecutar siempre:
  1. `./gradlew assembleDebug`
  2. `adb install -r .../app-debug.apk`
  3. Lanzamiento y revisión de `AndroidRuntime` en `logcat`

## 7) Plan de trabajo recomendado (siguientes hitos)
### Hito A - Cobertura de navegación principal
- Recorrer flujo desde Welcome hacia Home/Settings/Wizard/Interactive.
- Registrar y corregir nuevos crashes por prioridad (bloqueantes primero).

### Hito B - Barrido preventivo de patrones repetidos
- Detectar usos directos sensibles de recursos en actividades/componentes de interacción.
- Aplicar helpers de resolución dinámica en puntos de alto riesgo.

### Hito C - Endurecimiento de calidad
- Añadir checklist de smoke test por pantalla crítica.
- Documentar rutas validadas y pendientes en cada PR.

### Hito D - Limpieza técnica progresiva
- Reducir deuda de código decompilado en zonas tocadas.
- Mantener compatibilidad sin introducir regresiones visuales/funcionales.

## 8) Riesgos abiertos
- Posibles fallos en rutas no ejecutadas todavía.
- Inconsistencias de recursos heredados en módulos menos usados.
- Diferencias entre emulador y hardware real (timing/BT/permisos).

## 9) Definición de "listo" para esta fase
Se considerará cerrada esta fase cuando:
- El arranque sea estable y repetible.
- Se complete al menos un recorrido funcional básico sin crash (Welcome -> flujo interactivo -> retorno).
- Se disponga de registro claro de rutas probadas y pendientes.

## 10) Registro breve de verificación reciente
- Build debug: exitoso.
- Instalación APK: exitosa.
- Proceso vivo tras lanzamiento: sí.
- Actividad en foreground en última comprobación: `WelcomeViewActivity`.

---
Documento pensado para onboarding rápido de colaboradores y coordinación de próximas iteraciones.
