# Tarea 2 - Desarrollo de Aplicaciones Web  
Nombre: José Vera  
Curso: CC5002 - 2025-1  

## Descripción del proyecto

En este proyecto se encuentra la solución a la Tarea 3 del curso de Apps Web.

Como no entregué la T1, el formato sigue siendo bastante crudo, y decidí enfocarme principalmente en implementar correctamente las funcionalidades solicitadas.

En cuanto a la Tarea 2, no logré corregir los errores pendientes, como la falta de paginación en el listado de actividades, y tampoco pude solucionar los problemas de codificación que provocaron errores con tildes en algunas inserciones. Esto puede generar errores muy puntuales que la verdad no me tome el tiempo de solucionar.

Respecto a la Tarea 3, implementé los gráficos y el sistema de comentarios. Sin embargo, no alcancé a revisar en detalle el correcto funcionamiento visual de los gráficos. Puede que tenga alguna falla. Con respecto a los comentarios, no logré solucionar un problema que me surgio donde el primer comentario no se muestra automáticamente tras enviarlo, hay que recargar la patgina. Pero si ya hay un comentario, se recargan automaticamwnte.

Tampoco me dio tiempo de revisar las validaciones de html.

 Espero que los errores menores no afecten mucho la evaluación, y agradezco cualquier feedback que me permita mejorar en la tarea 4.

## Cómo correr el proyecto
Mi recomendacion seria seguir los siguientes pasos, aunque no es necesario al 100% para que funcione.

1. Crear entorno virtual:
   python -m venv venv  
   source venv/bin/activate  (Linux/macOS)  
   venv\Scripts\activate  (Windows)

2. Instalar dependencias:
   pip install -r requirements.txt

3. Crear base de datos `cc5002` y cargar:
   - region-comuna.sql
   - tarea2.sql
   Esot estan ubicados en /static/sql

4. Ejecutar servidor:
   python app.py


## Decisiones importantes

- Se pedia que tuviera de 0 a 5 medios de contacto, por lo que agregue un boton para que se genere hasta 5 campos de contacto. Ademas se pueden repetir los tipos de contacto, ya que por ejemplo puede ser organizado por dos personas y ambas dan su whatsapp
- El contacto es opcional; no se guarda en la base de datos ni se genera una instancia si no se ingresa.
- Se utiliza una sola plantilla dinámica `detalle.html` para todas las actividades.
- Las fotos se suben a /static/fotos/, con renombramiento si el nombre ya existe.
- La paginación no fue implementada por falta de tiempo.

Para la tarea 3: 
-Se utilizó la librería Highcharts para los gráficos, ya que fue recomendada en el enunciado y permite una integración sencilla con JavaScript y Flask.

-Las consultas a la base de datos se realizaron usando SQL crudo con text(), sin usar ORM, para tener mayor control sobre agregaciones, fechas y uniones. y la verdad me manejo mas con sql.


## Validación HTML y CSS

Se detectaron múltiples errores al usar validator.w3.org y jigsaw.w3.org.  
Tras investigar un poco, llegue a la conclusion que la mayoría de los errores eran causados por código Jinja2 (`{{ ... }}`, `{% ... %}`) presente en los archivos `.html`, el cual entiendo que no es comprendido por los validadores al no estar procesado aún.  
No encontre como validar estos html, y probablemente se me haya pasado algun error no relacionado con esto. Igual acepto feedback de como deberia haber manejado estas instancias o si es que Jinja2 no era la forma adecuada de manejar esos html.

## Estructura del proyecto

.
|--- app.py  
|--- models.py  
|--- requirements.txt  
|--- static/  
|   |--- css/  
|   |   |--- estilos.css  
|   |--- js/  
|   |   |--- validaciones.js  
|   |   |--- estadisticas.js  
|   |   |--- comentarios.js  
|   |--- fotos/  
|   |--- sql/  
|       |--- tarea2.sql  
|       |--- region-comuna.sql  
|       |--- tabla-comentario.sql  
|--- templates/  
|   |--- index.html  
|   |--- formulario.html  
|   |--- listado.html  
|   |--- detalle.html  
|   |--- estadisticas.html  
|--- README.md


