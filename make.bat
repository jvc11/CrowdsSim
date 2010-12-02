@echo off

REM ######## Variables
set CLASSP=.;lib\*
set MAIN=Main
set RUNFLAGS=
set DEBUGFLAGS=-ea
set ARGS=
REM #######################

if "%1" == "" goto make
if "%1" == "run" goto run
if "%1" == "debug" goto debug
if "%1" == "clean" goto clean
if "%1" == "report" goto report

REM # builds the project
REM ####################
:make
echo make
if not exist bin\ mkdir bin
javac -sourcepath src -d bin -classpath %CLASSP% src/cs2510/%MAIN%.java 
goto end

rem # runs the project
REM ##################
:run
java -cp bin;%CLASSP% %RUNFLAGS% cs2510.%MAIN% input2.json %ARGS%
goto end

rem # debug the project
REM ##################
:debug
java -cp bin;%CLASSP% %DEBUGFLAGS% cs2510.%MAIN% input2.json %ARGS%
goto end


rem # generates the latex report
REM ############################
:report
echo report
latex report.tex
goto end

rem # cleans generated files
REM ########################
:clean
echo clean
rmdir /s /q bin
goto end

:end
echo.
