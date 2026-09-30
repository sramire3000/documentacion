# Ejemplo Diario de Estudio

## Prompt inicial
```
## Rol 
Actúa como desarrollador frontend senior que escribe código simple, claro y fácil de 
entender para alguien que está empezando a programar.

## Contexto 
Quiero crear desde cero "Diario de Estudio", una web para registrar mis sesiones de 
estudio y motivarme viendo mi racha de días seguidos estudiando. Esta es la primera 
versión y tiene que ser muy simple. La web se construirá poco a poco, así que ahora solo 
necesito una base limpia que funcione a la primera.

## Tarea 
Crea la web con estas funcionalidades:

1. Un formulario para registrar una sesión con: 
   - Fecha (por defecto hoy, pero editable para poder apuntar días anteriores) 
   - Tema (texto, obligatorio) 
   - Minutos (número mayor que 0, obligatorio)
2. La racha actual en grande, con un 
3. La lista de sesiones, de la más reciente a la más antigua.
4. Los datos guardados en localStorage para que no se pierdan al recargar.

## Restricciones y reglas 
Racha: - Un día cuenta si tiene al menos una sesión. - La racha son los días consecutivos con sesión que terminan hoy. - Si hoy todavía no he estudiado pero ayer sí, la racha sigue viva: no se rompe hasta que 
termina el día. - Usa siempre la fecha local del usuario, nunca UTC. 
Técnicas: - HTML, CSS y JavaScript, sin frameworks, sin librerías y sin compilar nada. - Solo tres archivos: index.html, styles.css y app.js. - Tiene que funcionar abriendo index.html con doble clic, sin servidor ni instalación. - No añadas nada que no aparezca en este mensaje. - Diseño limpio y moderno, que se vea bien en el móvil. - Todos los textos de la interfaz en español. 

## Formato de salida 
1. Crea los tres archivos directamente en la carpeta del proyecto. 
2. Al terminar, responde con: 
   - Un resumen de 3-4 líneas de lo que has creado. 
   - Los pasos para probarlo. 
   - Cualquier decisión que hayas tomado por tu cuenta y que yo deba revisar.
```

## Archivo "AGENTS.md"
```
# AGENTS.md — Diario de Estudio 
Web estática para registrar sesiones de estudio y motivarse viendo la racha de días 
seguidos. Proyecto didáctico: el código debe poder entenderlo alguien que empieza a 
programar. 
## Stack y estructura - HTML, CSS y JavaScript puros: sin frameworks, librerías, npm, bundler ni build. - `index.html` (estructura), `styles.css` (estilos), `app.js` (lógica y datos). - Debe funcionar abriendo `index.html` con doble clic (`file://`): nada de módulos ES 
(`type="module"`), `fetch` a archivos locales ni nada que requiera servidor. 
## Convenciones - Textos de la interfaz en español. - Código simple, nombres descriptivos y comentarios solo donde aporten. - Diseño limpio y responsive; cualquier pantalla nueva debe verse bien en el móvil. 
## Datos - localStorage, clave `diario-estudio-sesiones`: array de `{ date: "AAAA-MM-DD", topic, 
minutes }`. - Si cambias la forma de los datos, mantén compatibilidad con lo ya guardado o el usuario 
perderá sus sesiones. 
## Límites 
## Fechas y racha (fácil equivocarse) - Trabaja siempre con la fecha local del usuario. Nunca uses `toISOString()` ni `new 
Date("AAAA-MM-DD")`: se interpretan en UTC y desplazan el día. - Racha = días consecutivos con al menos 1 sesión que terminan hoy. Si hoy no hay sesión 
pero ayer sí, la racha sigue viva y se cuenta desde ayer. - Varias sesiones el mismo día cuentan como un solo día. Las fechas futuras no suman. 
## Forma de trabajar - Haz solo lo que se pide: no añadas funcionalidades por tu cuenta. - Cambios pequeños y enfocados; no reescribas lo que ya funciona. - Al terminar, resume qué has cambiado y cualquier decisión que deba revisar. - 
✅
 Siempre: respetar las reglas de fechas y racha, mantener los textos en español. - 
⚠
 Pregunta antes: crear archivos nuevos, cambiar el formato de los datos guardados. - 
�
�
 Nunca: añadir dependencias, frameworks o un paso de build. 
## Verificación - No hay tests ni lint. Probar abriendo `index.html` en el navegador. - Para empezar de cero: DevTools → Application → Local Storage → borrar la clave 
`diario-estudio-sesiones`.
```
