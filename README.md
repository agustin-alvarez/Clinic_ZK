Veterinary Clinical Management
==============================

This project uses ZK to manage Veterinary Clinical and iText for to create PDF documents, Hichcharts JS for to show Charts.

##Manual de instalación
En esta sección describiremos los pasos para la instalación correcta de la aplicación.

### A.1. Instalación en el lado del servidor
Los requerimientos de software en este caso son más exigentes, además hay que
realizar la configuración del servidor. Para ello, tenemos que tener instalado el siguiente software:
```
JRE (Java Runtime Environment).
Apache Tomcat 7.0.
MySQL.
```

### A.1.1. JRE (Java Runtime Environment)
Seguiremos los siguientes pasos:
```
1. Descargarse el JRE correspondiente al sistema operativo y 
   a la arquitectura del servidor desde la página de Oracle: 
   http://www.oracle.com/technetwork/es/java/javase/downloads/index.html.
2. Procedemos a su instalación según los pasos indicados en la propia página.
```

### A.1.2. MySQL
A continuación describiremos los pasos para realizar una instalación y configuración 
correcta del sistema de gestión de base de datos MySQL:

1. Descargarlo de la página de MySQL según el sistema operativo del servidor:
http://dev.mysql.com/downloads/mysql/.
2. Procedemos a la instalación, si el sistema operativo es Windows, 
   ésta se lleva a cabo como cualquiera otra con el asistente de instalación.
3. Indicar la contraseña para Administrador.
4. Creamos la base de datos: CREATE database clinica.
5. En nuestra aplicación tendremos que configurar los datos de acceso a 
   la base de datos, esto se realiza en los ficheros DataSource.java:

    private static final String url = "jdbc:mysql://url/clinica";
    private static final String user = "root";
    private static final String pwd = "password";



