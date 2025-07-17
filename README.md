Autor: Jose Vera
Curso: Diseño y Desarrollo de Aplicaciones Web - CC5002
Profesor: José Urzúa


# Tarea 4

En este proyecto tenemos distintas carpetas, cada carpeta describe el codigo de la tarea. De esta forma siempre tuve acceso a los codigos anteriores por si queria obtener de estos (aunque nunca fue asi pero de esta forma me ordenaba mejor)



En la carpeta titulada "Tarea4" esta todo lo correspondiente a dicha tarea.

Como lo pide el enunciado, implemente un aplicacion para ver actividades recreativas utilizando **Spring Boot**, **Thymeleaf**, **JPA** y una base de datos **H2 en memoria**. Permite listar actividades realizadas y evaluar cada una con una nota del 1 al 7, a la vez que calcula el promedio en tiempo real sin recargar la página.

## Funcionalidades implementadas

- Listado de actividades
- Visualización de las columnas: ID, Fecha Inicio, Sector, Nombre, Tema y Nota promedio
- Un boto para evaluar cdaa actividad, con validación de la nota entre 1 y 7
- Envío de notas mediante JavaScript asincrónico usando `fetch`
- Actualización automática del promedio cuando se carga una nueva evaluación
- Ademas, para poder testear cree un archivo para que haga una carga de datos de prueba al iniciar la aplicación

## Decisiones de diseño

Hubo dos decisiones que tuve que tomar al desarrollar la aplicacion: 

1. No usar el sql provisto: En el ZIP se nos incluia un archivo sql para crear la tabla notas. Pero decidi no usarlo ya que me parecio que era mas correcto usar una clase para modelar notas asi como lo hice con actividades. Lo que hice fue revisar el sql y replicarlo en java, de forma que la tabla se cree con la misma estructura pero sin usar dicho archivo provisto. 

2. Que gestor de base de datos usar: Para gestionar la base de datos, inicialmente pense en usar postgres. Pero luego considerando mis opciones decidi usar H2. Esto debido a que es mas ligera, mas sencilla de implementar y testear (sobre todo si se tiene u archivo de carga de datos como tengo yo) y porque considere que en esta tarea se quiere evaluar el correcto uso de JPA mas que de conectar a una base de datos especifica.
Pense en luego cambiar a postgres, pero me entro la duda de si los correctores tendrian una base de datos configurada para poder testearlo o no. Entonces decidi quedarme con H2 por simplicidad y porque considero que cumple con todo lo que pide la tarea.


## Como ejecutar

1. Clonar el repositorio
2. Entrar en una terminal y ubicarse dentro de la carpeta "Tarea4"
3. Ejecutar: 

mvnw.cmd spring-boot:run  (en windows)

4. Ya se puede ver la aplicacion en localhost:8080/actividades

Nota: requiere Java 17 o superior.

## Notas: 

- Debido a que uso H2 en modo memoria, la base de datos se borra al apagar. Esto fue una decision de diseño para que el corrector pueda ver exactamente lo mismo que yo al ejecutar el servidor
- Los datos se cargan mediante un CommandLineRunner llamado DataLoader.java



## En caso que quisiera implementar la conexion a PostgreSQL
Si bien no hice esto al final, para conectarlo es relativamente directo. Se debe sustituir la dependencia que conecta a H2 en pom.xml y sustituirla por una que conecte a postgresql. Luego ir a resources>aplication.properties y sustituir las lineas que conectan a H2 por las que conectarian a postgres, especificando usuario, puerto y contraseña. Finalmente, en caso que no exista se deberia crear una base de datos en PostgreSQL y correrla para que se pueda conectar correctamente. 


