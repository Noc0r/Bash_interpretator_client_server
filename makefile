Sep=	/
GUI=	src$(Sep)GUI
Logic=	src$(Sep)Logic
Styles=	$(GUI)$(Sep)Styles
Funct=	$(Logic)$(Sep)Functionals
Structs=	src$(Sep)Structs
GUI1=	bin$(Sep)GUI
Logic1=	bin$(Sep)Logic
Styles1=	$(GUI1)$(Sep)Styles
Funct1=	$(Logic1)$(Sep)Functionals
Structs1=	bin$(Sep)Structs
JC=	javac	-g	-d	bin	-sourcepath	src

SRC11=	$(GUI)$(Sep)ClientWorkWindow.java
SRC12=	$(GUI)$(Sep)GraphicsClient.java
SRC13=	$(GUI)$(Sep)GraphicsServer.java
SRC14=	$(GUI)$(Sep)GraphicsMenu.java
SRC15=	$(GUI)$(Sep)HostWindow.java
SRC16=	$(GUI)$(Sep)InputDialog.java
SRC17=	$(Styles)$(Sep)Style.java

SRC21=	$(Logic)$(Sep)Administrator.java
SRC22=	$(Logic)$(Sep)BashExecutor.java
SRC23=	$(Logic)$(Sep)Client.java
SRC24=	$(Logic)$(Sep)Server.java
SRC25=	$(Funct)$(Sep)ServerFunctional.java
SRC26=	$(Funct)$(Sep)AdminFunctional.java

SRC31=	$(Structs)$(Sep)Configuration.java
SRC32=	$(Structs)$(Sep)ServerInfo.java


OBJ11=	$(GUI1)$(Sep)ClientWorkWindow.java
OBJ12=	$(GUI1)$(Sep)GraphicsClient.java
OBJ13=	$(GUI1)$(Sep)GraphicsServer.java
OBJ14=	$(GUI1)$(Sep)GraphicsMenu.java
OBJ15=	$(GUI1)$(Sep)HostWindow.java
OBJ16=	$(GUI1)$(Sep)InputDialog.java
OBJ17=	$(Styles1)$(Sep)Style.java

OBJ21=	$(Logic1)$(Sep)Administrator.java
OBJ22=	$(Logic1)$(Sep)BashExecutor.java
OBJ23=	$(Logic1)$(Sep)Client.java
OBJ24=	$(Logic1)$(Sep)Server.java
OBJ25=	$(Funct1)$(Sep)ServerFunctional.java
OBJ26=	$(Funct1)$(Sep)AdminFunctional.java

OBJ31=	$(Structs1)$(Sep)Configuration.java
OBJ32=	$(Structs1)$(Sep)ServerInfo.java

OBJ=	$(OBJ11)	\
	$(OBJ12)	\
	$(OBJ13)	\
	$(OBJ14)	\
	$(OBJ15)	\
	$(OBJ16)	\
	$(OBJ17)	\
	$(OBJ21)	\
	$(OBJ22)	\
	$(OBJ23)	\
	$(OBJ24)	\
	$(OBJ25)	\
	$(OBJ26)	\
	$(OBJ31)	\
	$(OBJ32)	\

compile:
	$(JC) $(SRC31)
	$(JC) $(SRC32)
	$(JC) $(SRC17)
	$(JC) $(SRC22)
	$(JC) $(SRC26)
	$(JC) $(SRC21)
	$(JC) $(SRC25)
	$(JC) $(SRC24)
	$(JC) $(SRC23)
	$(JC) $(SRC16)
	$(JC) $(SRC15)
	$(JC) $(SRC11)
	$(JC) $(SRC12)
	$(JC) $(SRC13)
	$(JC) $(SRC14)

run:	compile
	java	-classpath	bin	GUI.GraphicsMenu

clean:
	for	%%f	in	($(OBJ))	do	del	%%f
	del	$(TARG).jar
