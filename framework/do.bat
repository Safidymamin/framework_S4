cd classes
javac -cp lib\* -d . *.java

xcopy /E etu2003 classes
xcopy /E utils classes

cd classes
jar -cf files.jar .

cd ..\..\Test-framework

xcopy ..\classes\classes\files.jar .\WEB-INF\lib\

jar -cvf monappli.war .

move monappli.war ..

cd ..
xcopy monappli.war "C:\Program Files\Apache Software Foundation\Tomcat 9.0\webapps\"


