# Block Breaker - Documentación Técnica

## Análisis del juego (perspectiva del Ingeniero de Software)

Este documento analiza la arquitectura actual del proyecto con todas las implementaciones de patrones de diseño.

### Código Base
- El proyecto usa LibGDX para rendering y manejo de entrada.
- Estructura principal:
  - Módulo `core`: contiene la lógica del juego (`BlockBreakerGame`, bloques, pad, pelota).
  - Módulo `lwjgl3`: backend de escritorio y launcher.

### Arquitectura y Patrones de Diseño Implementados

#### 1. Patrón Singleton - MusicManager
**Propósito**: Gestor centralizado de música del juego.

**Implementación**:
- Clase `MusicManager` con instancia única estática
- Constructor privado que previene instanciación externa
- Método `getInstance()` para acceso global
- Gestión de música del menú y playlist del juego

**Beneficios**:
- Control centralizado del audio
- Estado consistente de reproducción
- Evita conflictos de múltiples instancias

#### 2. Patrón Template Method - AbstractScreen
**Propósito**: Definir el flujo de actualización/renderizado común para todas las pantallas.

**Implementación**:
- Clase abstracta `AbstractScreen` con método template `renderScreen()` (final)
- Métodos abstractos: `update(delta)` y `render(batch, shape, font)`
- Implementaciones concretas: `GameScreen`, `MenuScreen`, `CreditsScreen`, `OptionsScreen`

**Beneficios**:
- Garantiza flujo de ejecución consistente
- Elimina código duplicado
- Facilita mantenimiento de pantallas

#### 3. Patrón Strategy - BallAppearance
**Propósito**: Cambiar apariencia de la pelota sin modificar su lógica.

**Implementación**:
- Interfaz `BallAppearance` con método `draw()`
- Estrategias concretas: `WhiteBallAppearance`, `HappyFaceBallAppearance`, `SoccerBallAppearance`
- `PingBall` delega el dibujo a la estrategia actual
- Cambio dinámico de apariencia mediante `updateAppearance()`

**Beneficios**:
- Separación de responsabilidades
- Fácil adición de nuevas apariencias
- Personalización en tiempo de ejecución

#### 4. Patrón Abstract Factory - BlockFactory
**Propósito**: Crear familias de bloques coherentes según dificultad.

**Implementación**:
- Interfaz `BlockFactory` con métodos de creación
- Fábricas concretas: `EasyLevelFactory`, `HardLevelFactory`
- Productos: `SimpleBlock`, `StrongBlock`, `RegenerativeBlock`
- `GameScreen` usa la fábrica para crear niveles

**Beneficios**:
- Coherencia entre bloques del mismo nivel
- Fácil adición de nuevas dificultades
- Desacoplamiento del código cliente

### Clases Principales

**Jerarquía de Bloques**:
- `AbstractBlock` (clase abstracta base) — provee posición, tamaño, color, estado y API `takeDamage()`.
- `SimpleBlock`, `StrongBlock`, `RegenerativeBlock` — implementaciones concretas.
- `Destructible` (interfaz) — define la API usada por objetos que pueden ser dañados.

**Sistema de Pantallas**:
- `AbstractScreen` — clase base con Template Method
- `GameScreen` — pantalla principal del juego
- `MenuScreen` — menú principal con opciones
- `CreditsScreen` — pantalla de créditos
- `OptionsScreen` — configuración de audio y apariencia

**Gestión de Recursos**:
- `MusicManager` (Singleton) — gestión centralizada de audio
- `GameSettings` (Singleton) — configuración global del juego
- `GameConfig` — constantes del juego centralizadas

### Decisiones de Diseño
- Uso de clases abstractas e interfaces para maximizar reutilización
- Patrones de diseño aplicados para mejorar mantenibilidad y extensibilidad
- Configuración centralizada en `GameConfig` para evitar números mágicos
- Sistema de logging estructurado en lugar de `System.out.println`
- Gestión profesional de audio con soporte para playlists

### Características Implementadas
✅ Sistema de menús completo (Inicio, Créditos, Opciones, Salir)  
✅ Gestión de música de fondo con playlists  
✅ Múltiples apariencias de pelota intercambiables  
✅ Sistema de niveles con diferentes dificultades  
✅ Power-ups (vida extra, ralentización de pelota)  
✅ Configuración de volumen de música  
✅ Pausa durante el juego  

### Diagrama UML
Los diagramas UML de cada patrón están disponibles en:
- `singleton_pattern.puml` / `Singleton Pattern - MusicManager.png`
- `template_method_pattern.puml` / `Template Method Pattern - AbstractScreen.png`
- `strategy_pattern.puml` / `Strategy Pattern - BallAppearance.png`
- `abstract_factory_pattern.puml` / `Abstract Factory Pattern - BlockFactory.png`

---

## Tutorial del juego — Block Breaker (perspectiva del jugador)

Bienvenido a Block Breaker. Este documento explica el objetivo, controles y las pantallas principales desde la perspectiva del jugador.

### Objetivo
- Destruir todos los bloques en pantalla usando la pelota y el pad.
- Cada bloque otorga puntos al ser destruido; algunos bloques requieren múltiples golpes o se regeneran.
- Avanza de nivel destruyendo todos los bloques.
- Los niveles alternan entre fácil y difícil automáticamente.

### Controles
**En el juego:**
- **← / →** (Flechas): Mover el pad
- **SPACE**: Lanzar la pelota cuando esté quieta
- **P**: Pausar/Reanudar el juego
- **ESC**: Volver al menú principal

**En el menú:**
- **↑ / ↓** (Flechas o W/S): Navegar entre opciones
- **ENTER**: Seleccionar opción
- **ESC**: Salir del juego

### Elementos del Juego

**Pad (Paleta)**:
- Controlado por el jugador para devolver la pelota
- Muévelo estratégicamente para dirigir la pelota

**Pelota**:
- Rebota en paredes, pad y bloques
- Colisionar con bloques les aplica daño
- Puede cambiar de apariencia (configurable en Opciones)
- Apariencias disponibles: Blanca, Carita Feliz, Balón de Fútbol

**Tipos de Bloques**:
- **Bloque Simple** (Azul): Se destruye con 1 golpe (10 puntos)
- **StrongBlock** (Verde): Requiere múltiples golpes (30 puntos). Cambia de color según el daño
- **RegenerativeBlock** (Naranja): Puede regenerarse si no recibe golpes rápidamente (40 puntos)

**Power-Ups**:
- **Vida Extra** (❤️): Aparece aleatoriamente al destruir bloques, otorga una vida adicional
- **Ralentización**: Reduce temporalmente la velocidad de la pelota

### Pantallas del Juego

**Menú Principal**:
- **INICIO**: Comenzar nueva partida
- **CREDITOS**: Ver información del equipo de desarrollo
- **OPCIONES**: Configurar volumen de música y apariencia de la pelota
- **SALIR**: Cerrar el juego

**Pantalla de Juego**:
- Muestra el área de juego con el pad en la parte inferior
- Bloques distribuidos en la parte superior
- HUD en la parte superior izquierda con:
  - Puntos actuales
  - Vidas restantes
  - Nivel actual

**Pantalla de Opciones**:
- Control de volumen de música (0-100%)
- Selección de apariencia de pelota
- Vista previa en tiempo real

**Pantalla de Pausa**:
- Aparece al presionar P durante el juego
- Opciones: Reanudar o Volver al Menú

### Sistema de Niveles

**Nivel Fácil** (impares: 1, 3, 5...):
- Más bloques simples
- Bloques fuertes menos resistentes
- Regeneración más lenta

**Nivel Difícil** (pares: 2, 4, 6...):
- Más bloques fuertes y regenerativos
- Mayor resistencia de bloques
- Regeneración más rápida

### Consejos de Juego
- Mantén la pelota en juego moviendo el pad estratégicamente
- Prioriza bloques regenerativos para evitar que se recuperen
- Usa los ángulos del pad para dirigir la pelota
- Recoge los power-ups de vida extra cuando aparezcan
- En niveles difíciles, enfócate en destruir bloques rápidamente

### Música
El juego cuenta con música de fondo:
- Música específica para el menú principal
- Playlist de música durante el juego (rotación automática)
- Control de volumen en Opciones

### Cómo Ejecutar

**Desde la terminal:**
1. Abrir una terminal en la carpeta `BLOC_BREAKER`
2. Ejecutar:

```bash
./gradlew.bat --project-dir . :lwjgl3:run
```

**Desde VS Code:**
- Abrir el proyecto en VS Code
- Ejecutar la tarea de Gradle: `lwjgl3:run`

### Requisitos del Sistema
- Java JDK 17 o superior
- Sistema operativo: Windows, macOS, o Linux
- Resolución mínima: 800x600

---

## Información para Desarrolladores

### Estructura del Proyecto
```
BLOC_BREAKER/
├── core/               # Lógica principal del juego
│   └── src/main/java/juego/progra/
│       ├── AbstractBlock.java
│       ├── AbstractScreen.java
│       ├── BallAppearance.java (interfaz)
│       ├── BlockFactory.java (interfaz)
│       ├── MusicManager.java (Singleton)
│       ├── GameScreen.java
│       ├── MenuScreen.java
│       └── ...
├── lwjgl3/            # Backend de escritorio
├── assets/            # Recursos (música, imágenes)
└── gradle/            # Configuración de Gradle
```

### Extensibilidad

**Agregar Nueva Apariencia de Pelota**:
1. Crear clase que implemente `BallAppearance`
2. Implementar método `draw()`
3. Agregar en `PingBall.updateAppearance()`

**Agregar Nuevo Nivel de Dificultad**:
1. Crear clase que implemente `BlockFactory`
2. Implementar métodos de creación de bloques
3. Agregar en `GameScreen.selectBlockFactory()`

**Agregar Nuevo Tipo de Bloque**:
1. Crear clase que extienda `AbstractBlock`
2. Implementar métodos `draw()` y `takeDamage()`
3. Actualizar fábricas para crear el nuevo bloque

### Testing
```bash
./gradlew.bat test
```

### Compilación
```bash
./gradlew.bat build
```

---

## Créditos
Proyecto desarrollado como parte del curso de Programación Orientada a Objetos.

**Patrones de Diseño Implementados**:
- Singleton (MusicManager, GameSettings)
- Template Method (AbstractScreen)
- Strategy (BallAppearance)
- Abstract Factory (BlockFactory)

**Tecnologías**:
- LibGDX Framework
- Java 17+
- Gradle Build System
