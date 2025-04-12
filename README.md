TITULO DEL PROYECTO: VETERINARIA_APP
INSTITUTION: UNIVERSIDAD TECNOLOGICA NACIONAL FRRe
MATERIA: PROGRAMACION II
TURNO: Comisión 3 - Turno Noche.
AÑO: 2024

AUTOR: LUQUE ENCINA, FLORENCIA JAQUELINA
LEGAJO: 29235
DNI: 38768162

------------------------------------------------------------------------
-> DESCRIPCION DEL PROYECTO

El proyecto consiste en el desarrollo de un sistema de gestión diaria para una veterinaria. Este sistema tiene como objetivo optimizar las operaciones relacionadas 
con las atenciones a las mascotas y la administración de los clientes y veterinarios. Además, la veterinaria trabaja en colaboración con una ONG que brinda tránsito 
y cuidados a los animales callejeros atendidos en el lugar.

Ofrece las siguientes funcionalidades claves:
- Gestion de mascotas y personas:
    * Agregar nuevos registros: para los animales, se realiza la distinción de que se puedan ingresar "Mascotas" (es decir, tienen dueños y tienen sus datos completos) o 
      "Callejeros" (solicitando datos básicos como raza, género, peso y un nombre provisorio hasta su adopción).
    * Actualizar registros: modificar la información de clientes o mascotas según sea necesario.
    * Eliminar registros: borrar información obsoleta o incorrecta.
    * Consultar Registros: listar todos los registros con opciones para filtrar por nombre (y en el caso de personas, por nombre y apellido).
- Gestión de turnos:
    * Registrar y eliminar turnos de atención para las mascotas.
    * Consultar turnos asignados, con opciones para filtrar por veterinarios y prácticas médicas.

El sistema almacena todos los datos en memoria, utilizando HashSet como colección principal, y realiza la persistencia en archivos .txt separados por tipo de entidad, 
garantizando una organización clara y eficiente.


-> ESTRUCTURA DEL PROYECTO

El sistema está diseñado bajo los principios de POO, lo que asegura un modelo bien estructurado, reutilizable y extensible:
1) Uso de herencia y clases abstractas: 
    * La clase abstracta Animal organiza las características comunes de todas las mascotas (nombre, edad, género, peso, etc.).
    * La clase abstracta Persona estructura las propiedades comunes de clientes y veterinarios.

2) Subclases específicas:
    * Animales: Clases como Perro, Gato y Pajaro extienden de Animal, definiendo atributos específicos (como raza/especie y el estado de castración/alas cortadas).
    * Personas: Clases como Cliente y Veterinario extienden de Persona, adaptándose a sus roles específicos.

3) Interfaces: implemente las interfaces de GestionAnimal y GestionPersona para definir contratos claros para el registro y la consulta de datos. 
   Esto asegura consistencia en las operaciones básicas.

4) Enums: los utilice para modelar aspectos claves como:
    * Razas de perros y gatos y especies de pájaros
    * Género de animales/personas.
    * Prácticas Médicas, facilitando la selección intuitiva durante la asignación de turnos.
   Aportando los siguientes beneficios: 
    * Listar razas más comunes de perros y gatos, así como las especies más frecuentes de pájaros en Argentina.
    * Garantizar datos consistentes y evitar errores de ingreso.
    * Asegurando la posibilidad de que los usuarios especifiquen una raza o especie no incluida en la lista, con la opcion de "Otro".
    * Tambien aseguro la posibilidad de que no se quiera especificar el género mediante la opción de "Sin Especificar".

5) Gestores Específicos (gestión de negocio):
    * Gestor de Personas: Administra las operaciones de ABMC para clientes y veterinarios.
    * Gestor de Animales: Controla las operaciones de ABMC para mascotas.
    * Gestor de Turnos: Centraliza la asignación, consulta y eliminación de turnos médicos.

6) Interfaz Gráfica: desarrollada con JFrame, organiza las operaciones en secciones intuitivas:
    * Un "Menu Principal" permite seleccionar la entidad a gestionar (Turnos, Animales, Personas).
    * Segun el tipo de selección: redirecciona a la elección del "Tipo Animal" o "Tipo Persona" especifica a gestionar. 
    * Finalmente, permite la selección de la operación a realizar: "Agregar" o "Listar", dentro de esta última se puede gestionar la "Modificación", 
      "Eliminación" o "Busqueda por filtros".
    * En el caso de los "Turnos", tras seleccionarlo en el menu principal, directamente direcciona a la opcion de "Agregar" o "Listar", éste último ademas permite 
      la "Eliminación" o la "Busqueda por filtros".

-> FUNDAMENTACIÓN TÉCNICA
- Elección de HashSet:
    * Evita duplicados: al manejar entidades como turnos, clientes y mascotas, es fundamental poder evitar registros duplicados, algo que el HashSet garantiza automáticamente.
    * Búsqueda eficiente: ofrece operaciones de búsqueda, inserción y eliminación con una complejidad promedio, lo cual es ideal para el manejo dinámico de datos.
    * Modelo flexible: su capacidad para almacenar datos no ordenados es adecuada para registros en los que el orden de entrada no es crítico.

- Persistencia en .txt:
    * Permite al sistema mantener información entre sesiones.
    * Organización clara: Cada tipo de entidad tiene su propio archivo (BD_Perros.txt, BD_Turnos.txt, etc.), facilitando la organización y el mantenimiento.
    * Formato legible: al usar un formato plano (texto), los datos son fácilmente accesibles y depurables sin herramientas externas.
      
------------------------------------------------------------------------