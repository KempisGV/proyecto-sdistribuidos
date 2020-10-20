# proyecto-sdistribuidos
# Integrantes:
* Kempis Guerrero
* Mathias Loor

# Instrucciones para compilar y ejecutar la aplicación en Ubuntu Server
1. Ejecutar el comando `sudo ifconfig enp0s8 10.10.10.X`(x es el numero del nodo) para configurar la red interna de la máquina virtual, la máquina que hará de reportero deberá tener la ip 10.10.10.1 y la verificador 10.10.10.2
2. Instalar el jdk de java utilizando el comando `sudo apt install default-jdk`.
3. Para comprobar que java está instalado ejecutar el comando `javac`.
4. Una vez ubicados en la carpeta del proyecto en la máquina virtual que hará de Reportero nos movemos a la ruta src/proyecto y se necesita agregar al classpath la libreria json-simple-1.1.jar, para esto se utilizará el comando `export CLASSPATH=json-simple-1.1.jar:$CLASSPATH`.
5. Luego ejecutamos el comando `javac Reportero.java` para compilar el archivo, luego para ejecutarlo usamos `java Reportero.java` 
6. Para la ejecución del Verificador debemos ingresar en la máquina virtual que hará tomará el rol de Verificador a la ubicación src/proyecto/proyecto, una vez aquí debemos ejecutar el comando `export CLASSPATH=json-simple-1.1.jar:$CLASSPATH`.
7. Luego nos dirigimos hacia atrás con `cd ..` y ejecutamos el comando `javac proyecto/*.java`, después para ejecutar el Verificador usamos `java proyecto/Verificador`
8. Una vez realizado los pasos se podrá visualizar en el Verificador como empieza a hacer lecturas y se intenta conectar al Reportero, en caso de no poder conectarse al este, mandará un mensaje y seguirá haciendo lecturas. Una vez se conecte al reportero empezará a enviarle información.
9. El módulo Verificador irá generando reportes con el formato "reporte1.json, reporte2.json..." donde almacena la información generada por cada sensor y recolectada por el agente colector, además irá llenando el historial de lecturas donde almacena timestamps de cada lectura realizada.
10. El módulo Reportero irá llenando los archivos llamados "reporthum.json", "reporttemp.json" y "reportrad.json" en los que se irá generando un reporte actualizado de las lecturas de cada sensor, todo esta información está basada en los datos que el módulo verificador le envía. Además se generan en estos archivos medicinoes del promedio, la lectura máxima y minima de cada sensor.

# Referencias
* https://www.youtube.com/watch?v=4OtAyd98EQ8
* https://stackoverflow.com/questions/28807919/how-to-check-if-a-jsonarray-is-empty-in-java
* https://stackoverflow.com/questions/47412889/add-json-object-into-existing-json-array-using-java
* https://www.youtube.com/watch?v=GEYrZ8R5KRw
* https://stackoverflow.com/questions/16707816/ftp-client-server-model-for-file-transfer-in-java
* https://stackoverflow.com/questions/44464218/append-text-field-data-to-an-existing-json-file-in-java/52158545
* https://stackoverflow.com/questions/20365885/how-do-i-run-java-program-with-multiple-classes-from-cmd
* https://www.youtube.com/watch?v=4OtAyd98EQ8
