#=============================
# variables and configuration
#=============================

SHELL = /bin/bash
MAIN = Main
CP = lib/*:$(CLASSPATH)
DEBUGFLAGS = -ea
RUNFLAGS =
ARGS = input.json distributed
#ARGS = input.json centralized

#=============================
#  build targets  
#=============================

all: build

build:
	if [ ! -d bin ]; then mkdir bin; fi
	javac -sourcepath src -d bin -cp $(CP) src/cs2510/$(MAIN).java

run: build
	java -cp bin:$(CP) $(RUNFLAGS) cs2510.$(MAIN) $(ARGS)

debug:
	java -cp bin:$(CP) $(DEBUGFLAGS) cs2510.$(MAIN) $(ARGS)

# target to run the centralized simulation
centralized: build
	java -cp bin:$(CP) $(RUNFLAGS) cs2510.$(MAIN) input.json centralized
	
# target to run the distributed simulation
distributed: build
	java -cp bin:$(CP) $(RUNFLAGS) cs2510.$(MAIN) input.json distributed

report:
	pdflatex report.tex

#=============================
#  other targets  
#=============================

# removes all classfiles
# and the bin directory
clean:
	rm -rf report.aux report.log
	rm -rf bin/

rebuild: clean build
