#NANOXML = /net/raquella/ldc/redes/nanoxml/java/nanoxml-lite-2.2.3.jar
NANOXML = nanoxml-lite-2.2.3.jar
CLASSPATH = $(NANOXML):.
JAVAC = gcj
JAVAC_OPTS = -classpath $(CLASSPATH)

all : nodo cliente

nodo: Nodo.o Par.o Tripla.o Buscar.o nanoxml-lite-2.2.3.o
	$(JAVAC) $(JAVAC_OPTS) --main=Nodo -o nodo Nodo.o Par.o Tripla.o Buscar.o nanoxml-lite-2.2.3.o

cliente: Cliente.o Par.o Tripla.o Buscar.o nanoxml-lite-2.2.3.o
	$(JAVAC) $(JAVAC_OPTS) --main=Cliente -o cliente Cliente.o Par.o Tripla.o Buscar.o nanoxml-lite-2.2.3.o

nanoxml-lite-2.2.3.o: $(NANOXML)
	$(JAVAC) $(JAVAC_OPTS) -c $(NANOXML)
	objcopy -L '_ZGr8_$$_dummy' nanoxml-lite-2.2.3.o

Cliente.o: Cliente.java
	$(JAVAC) $(JAVAC_OPTS) -c Cliente.java
	objcopy -L '_ZGr8_$$_dummy' Cliente.o

Nodo.o: Nodo.java
	$(JAVAC) $(JAVAC_OPTS) -c Nodo.java
	objcopy -L '_ZGr8_$$_dummy' Nodo.o

Buscar.o: Buscar.java
	$(JAVAC) $(JAVAC_OPTS) -c Buscar.java
	objcopy -L '_ZGr8_$$_dummy' Buscar.o

Tripla.o : Tripla.java
	$(JAVAC) $(JAVAC_OPTS) -c Tripla.java
	objcopy -L '_ZGr8_$$_dummy' Tripla.o

Par.o : Par.java
	$(JAVAC) $(JAVAC_OPTS) -c Par.java
	objcopy -L '_ZGr8_$$_dummy' Par.o

clean:
	rm -rf *.o nodo cliente
