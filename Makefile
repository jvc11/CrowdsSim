#=============================
# variables and configuration
#=============================

SHELL = /bin/bash
MAIN = Main
CP = lib/*:$(CLASSPATH)
DEBUGFLAGS = -ea
RUNFLAGS =
#ARGS = input.json distributed
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

debug: build
	java -cp bin:$(CP) $(DEBUGFLAGS) cs2510.$(MAIN) $(ARGS)

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
