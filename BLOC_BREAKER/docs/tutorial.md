# Tutorial del juego — Block Breaker (perspectiva del jugador)

Bienvenido a Block Breaker. Este documento explica el objetivo, controles y las pantallas principales desde la perspectiva del jugador.

Objetivo
- Destruir todos los bloques en pantalla usando la pelota y el pad.
- Cada bloque otorga puntos al ser destruido; algunos bloques requieren múltiples golpes o se regeneran.

Controles
- Izquierda / Derecha: mover el pad (flechas izquierda/derecha del teclado).
- Barra espaciadora (SPACE): lanzar la pelota cuando esté quieta.

Elementos del juego
- Pad: controlado por el jugador para devolver la pelota.
- Pelota: rebota en paredes, pad y bloques; colisionar con bloques les aplica daño.
- Bloques:
  - Bloque simple: se destruye con 1 golpe (10 puntos).
  - StrongBlock: requiere varios golpes (30 puntos al destruirlo). Muestra mensajes en consola.
  - RegenerativeBlock: puede regenerarse si no recibe golpes consecutivos rápidamente; otorga más puntos.

Interfaz y pantallas (maquetas)
- Pantalla principal (in-game): muestra el área de juego con el pad abajo, bloques en la parte superior, y HUD con Puntos, Vidas y Nivel.
- No hay menús complejos implementados; la UI principal se encuentra en la clase `BlockBreakerGame.dibujaTextos()` que dibuja el HUD.

Consejos de juego
- Mantén la pelota en juego moviendo el pad y lanzando la pelota con SPACE.
- Prioriza bloques regenerativos y fuertes para maximizar puntos.

Cómo ejecutar
1. Abrir una terminal en la carpeta `BLOC_BREAKER`.
2. Ejecutar:

```
./gradlew.bat --project-dir . :lwjgl3:run
```

Notas
- Mensajes de estado (puntos, golpes) se imprimen por consola con `System.out.println` en las clases de bloque.
