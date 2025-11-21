# INSTRUCCIONES PARA AGREGAR MÚSICA

Para que el sistema de música dual funcione completamente, necesitas agregar dos archivos de audio en formato OGG:

## 1. Música del Menú
- **Ubicación:** `assets/music/menu.ogg`
- **Uso:** Se reproduce en el menú principal, pantalla de créditos y opciones
- **Recomendación:** Una música más tranquila o ambiental

## 2. Música del Juego
- **Ubicación:** `assets/music/bg.ogg`
- **Uso:** Se reproduce durante la partida
- **Recomendación:** Una música más energética y dinámica

## Cómo convertir archivos de audio a OGG:

### Opción 1: Usar un convertidor online
1. Busca "mp3 to ogg converter" en Google
2. Sube tu archivo MP3/WAV
3. Descarga el archivo convertido como .ogg

### Opción 2: Usar software (Audacity - gratis)
1. Descarga Audacity (https://www.audacityteam.org/)
2. Abre tu archivo de audio
3. Archivo → Exportar → Exportar como OGG

### Opción 3: FFmpeg (línea de comandos)
```bash
ffmpeg -i input.mp3 -c:a libvorbis -q:a 4 output.ogg
```

## Sitios para descargar música libre de derechos:
- https://freesound.org/
- https://incompetech.com/music/
- https://freemusicarchive.org/
- https://www.bensound.com/

## IMPORTANTE:
- Si no agregas los archivos, el juego funcionará sin música (no habrá errores)
- Los archivos DEBEN estar en formato OGG
- Los nombres DEBEN ser exactamente `menu.ogg` y `bg.ogg`
- Deben estar en la carpeta `assets/music/`
