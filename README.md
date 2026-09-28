# SpeedFast - IntelliJ IDEA

Proyecto Java Swing + MySQL/JDBC preparado para abrir directamente en IntelliJ IDEA.

## Cómo abrir y ejecutar

1. Descomprime este ZIP.
2. Abre IntelliJ IDEA.
3. Selecciona **Open** y abre la carpeta `SpeedFast_IntelliJ`.
4. IntelliJ reconocerá `pom.xml` como proyecto Maven y descargará MySQL Connector/J.
5. Verifica que tengas un JDK 17 o superior.
6. Ejecuta:
   `src/main/java/org/example/Main.java`
   o la clase `org.example.Main`.

## Base de datos

Antes de usar las funciones que consultan/guardan información:

1. Abre MySQL.
2. Ejecuta `sql/speedfast_db.sql`.
3. Revisa `src/main/java/cl/duoc/conexion/ConexionBD.java`.
4. Si tu usuario/contraseña de MySQL son distintos, modifica:
   - USER
   - PASSWORD
5. La conexión está configurada para:
   `jdbc:mysql://localhost:3306/speedfast`

## Importante

El proyecto original tenía los fuentes en una carpeta no estándar para Maven (`src/Duoc/java`).
Se reorganizó a la estructura estándar `src/main/java` para que IntelliJ/Maven reconozca automáticamente los paquetes y permita ejecutar el `Main`.

El código Java se mantuvo sin cambiar su lógica.
