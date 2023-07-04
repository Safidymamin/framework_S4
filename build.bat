cd framework/
    javac -d . *.java
    jar -cf fw.jar .
    move fw.jar "D:\ITU\Framework\framework_S4\test-framework\WEB-INF\lib"
cd ..

cd test-framework/WEB-INF/classes
    javac -cp ../lib/fw.jar -d . *.java
cd ../../..

cd test-framework/
    jar -cf test-servlet.war *
    move test-servlet.war "C:\Program Files\Apache Software Foundation\Tomcat 10.0\webapps\test-framework.war"
cd ..