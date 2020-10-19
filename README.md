# proyecto-sdistribuidos
# Integrantes:
* Kempis Guerrero
* Mathias Loor

# Instrucciones para compilar y ejecutar la aplicación en Ubuntu Server
1. Ejecutar el comando `sudo ifconfig enp0s8 10.10.10.X`(x es el numero del nodo) para configurar la red interna de la máquina virtual.
2. Instalar el jdk de java utilizando el comando `sudo apt install default-jdk`.
3. Para comprobar que java está instalado ejecutar el comando `javac`.
4. Antes de la ejecución del programa al archivo Verificador.java se le debe modificar la dirección 10.10.10.1 por la que le corresponde a la máquina que hace de verificador.
5. Compilar los archivos Ver.java y TCPServer.java en cada máquina correspondiente con el comando `javac`, por ejemplo `javac TCPCLient.java`.
6. Ejecutar primero TCPServer.java y posteriormente el TCPClient.java con el comando `java`, por ejemplo `java TCPServer`.
7. Una vez realizado los pasos se podrá visualizar en la pantalla del servidor como el se muestran varias letras 'X' dependiendo del número que el cliente le envía al servidor (rango del 1-10).

# Referencias
* El entregable fue basado en el código encontrado en el repositorio https://github.com/thomasWeise/distributedComputingExamples/tree/master/sockets/java
