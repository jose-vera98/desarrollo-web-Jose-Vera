# desarrollo-web-jose-Vera
Este repositorio incluye todas las entregas del curso de Desarrollo Web, las primeras entregas son en Flask con Front en JS, y la ultima entrega cambia el backend a Java

Autor: Jose Vera
Curso: Diseño y Desarrollo de Aplicaciones Web - CC5002
Profesor: José Urzúa


# Tarea 4

En este proyecto tenemos distintas carpetas, cada carpeta describe el codigo de la tarea. De esta forma siempre tuve acceso a los codigos anteriores por si queria obtener de estos (aunque nunca fue asi pero de esta forma me ordenaba mejor). 

Debido a que es incremental, decidi trabajar directamente sobre la Tarea4 sin cambiar ningun nombre para no tener que hacer refactors o complicarme de mas con las configuraciones del proyecto. 

En la carpeta titulada "Tarea4" esta todo lo correspondiente a esta tarea.

Como lo pide el enunciado, implemente un aplicacion para ver actividades recreativas utilizando **Spring Boot**, **Thymeleaf**, **JPA** y una base de datos **H2 en memoria**. Permite listar actividades realizadas y evaluar cada una con una nota del 1 al 7, a la vez que calcula el promedio en tiempo real sin recargar la página.

En la Tarea 5 se agregan 2 vistas para admin, ambas detras de una capa de inicio de sesion. Las credenciales son:
- User: admin
- Password: examen

Con esto se puede entrar en las nuevas funcionalidades: /admin-fotos y /log

En admin-fotos se tienen todas las fotos asociadas a alguna actividad. Se pueden eliminar las fotos, y te va a pedir una justificacion con un popup, para luego confirmar la eliminacion y agregarlo a la tabla log. 

Estas fotos eliminadas se pueden ver en log. En estas se pueden ver todas las que han sido eliminadas y por que fueron eliminadas. 

En la parte de las imagenes, deje una que no tiene que ver con la actividad ya que es justamente para eliminarlo porque esta mal asignado. 



## Funcionalidades implementadas

- Listado de fotos (ordenadas de la mas reciente a la mas antigua)
- Posibilidad de eliminar fotos
- Registro de fotos eliminadas (del mas reciente al mas antiguo)
- Seguridad e inicio de sesion con Spring Security
- Para facilitar la navegacion, agregue un boton para ir a ver el log luego de borrar una imagen y un link para ir a ver las fotos desde el log
- Tambien agregue un boton para ir de actividades a administrador de fotos, tambien para facilitar el acceso a las 


## Decisiones de diseño

Hubo dos decisiones que tuve que tomar al desarrollar la aplicacion: 

1. No usar el sql provisto: Igual que en la Tarea 4, no use el sql sino que lo genere desde una clase de Java replicando la tabla como a aparece en el mismo archivo

2. Que gestor de base de datos usar: Al igual que en la tarea 4, mantengo el uso de H2 para la gestion de bases de datos. Para mas detalles leer el Readme de la branch de la T4. 

3. Quite los whitelabel errors, ya que no son buena practica si ya la app deberia estar en produccion. Ademas de eso, agregue una redirecccion desde localhost:8080/ a localhost:8080/actividades, d forma que al abrir ya envie directamente a esa vista y no lance un 404 por defecto.


## Como ejecutar

1. Clonar el repositorio
2. Entrar en una terminal y ubicarse dentro de la carpeta "Tarea4"
3. Ejecutar: 

mvnw.cmd spring-boot:run  (en windows)

4. Ya se puede ver la aplicacion en localhost:8080/actividades
5. Tambien se puede acceder a las url /admin-fotos y /log haciendo inicio de sesion.

Nota: requiere Java 17 o superior y maven instalados.

## Notas: 

- Debido a que uso H2 en modo memoria, la base de datos se borra al apagar. Esto fue una decision de diseño para que el corrector pueda ver exactamente lo mismo que yo al ejecutar el servidor
- Los datos se cargan mediante un CommandLineRunner llamado DataLoader.java
- Al agregar spring secutiry empece a tener problemas de validacion, por lo que tuve que poner CSFR tokens tanto en admin-fotos como en actividades para que pudieran ser usadas. 



## En caso que quisiera implementar la conexion a PostgreSQL
Si bien no hice esto al final, para conectarlo es relativamente directo. Se debe sustituir la dependencia que conecta a H2 en pom.xml y sustituirla por una que conecte a postgresql. Luego ir a resources>aplication.properties y sustituir las lineas que conectan a H2 por las que conectarian a postgres, especificando usuario, puerto y contraseña. Finalmente, en caso que no exista se deberia crear una base de datos en PostgreSQL y correrla para que se pueda conectar correctamente. 


