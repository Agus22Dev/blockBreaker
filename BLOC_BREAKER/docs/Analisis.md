# Análisis del juego (perspectiva del Ingeniero de Software)

Este documento analiza la arquitectura actual del proyecto y las modificaciones propuestas.

Código base
- El proyecto usa LibGDX para rendering y manejo de entrada.
- Estructura principal:
  - Módulo `core`: contiene la lógica del juego (`BlockBreakerGame`, bloques, pad, pelota).
  - Módulo `lwjgl3`: backend de escritorio y launcher.

Clases relevantes
- `AbstractBlock` (clase abstracta base) — provee posición, tamaño, color, estado y API `takeDamage()`.
- `SimpleBlock`, `StrongBlock`, `RegenerativeBlock` — implementaciones concretas.
- `Destructible` (interfaz) — define la API usada por objetos que pueden ser dañados.

Modificaciones propuestas (ejemplos)
- Añadir sistema de niveles con configuración desde archivo JSON.
- Añadir menú principal y sistema de pausa.
- Externalizar constantes (tamaños, puntajes) en una clase `Config`.

Decisiones de diseño
- Uso de una clase abstracta (`AbstractBlock`) para compartir comportamiento entre bloques (cumple GM1.4).
- `Destructible` como interfaz permite expresar el contrato de objetos que pueden recibir daño (cumple GM1.5).
- Se priorizaron cambios no intrusivos para mantener compilación y facilidad de prueba.

Riesgos y limitaciones
- Mezcla de `System.out.println` para logging — recomendable usar un logger en su lugar.
- Manejo de assets y resolución fija: actualmente el tamaño de cámara y valores están hardcodeados.
