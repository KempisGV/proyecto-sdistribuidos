# proyecto-sdistribuidos
# Integrantes:
* Kempis Guerrero
* Mathias Loor

# Instrucciones para compilar y ejecutar la aplicación en Ubuntu Server
1. Ejecutar el comando `sudo ifconfig enp0s8 10.10.10.X`(x es el numero del nodo) para configurar la red interna de la máquina virtual.
2. Instalar el jdk de java utilizando el comando `sudo apt install default-jdk`.
3. Para comprobar que java está instalado ejecutar el comando `javac`.
4. Se necesita agregar al classpath la libreria json-simple-1.1.jar, para esto se deberá mover en la terminal al la carpeta src/proyecto donde ejecutará el comando `export CLASSPATH=json-simple-1.1.jar:$CLASSPATH`
4. Antes de la ejecución del programa al archivo Verificador.java se le debe modificar la dirección 10.10.10.1 por la que le corresponde a la máquina que hace de verificador.
5. Compilar los archivos .java en cada máquina correspondiente con el comando `javac`, por ejemplo `javac Reportero.java`.
6. Ejecutar Verificador.java y Reportero.java en cada máquina correspondiente con el comando `java`, por ejemplo `java Verificador.java`.
7. Una vez realizado los pasos se podrá visualizar en el Verificador como empieza a hacer lecturas y se intenta conectar al Reportero, en caso de no poder conectarse al reportero mandará un mensaje y seguirá haciendo lecturas. Una vez se conecte al reportero empezará a enviarle información a este.
8. El módulo Verificador irá generando reportes con el formato "reporte1.json, reporte2.json..." donde almacena la información generada por cada sensor y recolectada por el agente colector, además irá llenando el historial de lecturas donde almacena timestamps de cada lectura realizada.
9. El módulo Reportero irá llenando los archivos llamados "reporthum.json", "reporttemp.json" y "reportrad.json" en los que se irá generando un reporte actualizado de las lecturas de cada sensor, todo esta información está basada en los datos que el módulo verificador le envía. Además se generan en estos archivos medicinoes del promedio, la lectura máxima y minima de cada sensor.

# Referencias
* https://www.youtube.com/watch?v=4OtAyd98EQ8
* https://stackoverflow.com/questions/28807919/how-to-check-if-a-jsonarray-is-empty-in-java
* https://stackoverflow.com/questions/47412889/add-json-object-into-existing-json-array-using-java
* https://www.youtube.com/watch?v=GEYrZ8R5KRw
* https://stackoverflow.com/questions/16707816/ftp-client-server-model-for-file-transfer-in-java
* https://stackoverflow.com/questions/44464218/append-text-field-data-to-an-existing-json-file-in-java/52158545
