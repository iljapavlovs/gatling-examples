
# Run via Maven Gatling Plugin
https://gatling.io/docs/current/extensions/maven_plugin/ 

```mvn gatling:test```

OR 

```
mvn gatling:test -Dgatling.simulationClass=simulations.PerfomanceSimulation
```


### Redefine env properties in Gatling
* https://devqa.io/parameterize-gatling-variables/
* https://gatling.io/docs/current/cookbook/passing_parameters/
```
<plugin>
    <groupId>io.gatling</groupId>
    <artifactId>gatling-maven-plugin</artifactId>
    <version>${gatling-maven-plugin.version}</version>
    <configuration>
        <simulationClass>${gatling.simulations.package}.${gatling.simulation.name}</simulationClass>
        <jvmArgs>
            <jvmArg>-DBASE_URL=${BASE_URL}</jvmArg>
              ...  
        </jvmArgs>
    </configuration>
</plugin>
```

# Caveats
Check versioning of Scala, Java and Gatling for compatible versions

## Check Scala version which comes with Gatling
```
mvn dependency:tree
```

## Add Scala as a dependency in Maven
```
 <dependency>
     <groupId>org.scala-lang</groupId>
     <artifactId>scala-library</artifactId>
     <version>${scala.version}</version>
 </dependency>
```



## Compile Scala with a plugin

By default, the gatling-maven-plugin takes care of compiling your Scala code, so you can directly run mvn gatling:test.

Then, for some reason, you might want to have the scala-maven-plugin take care of compiling.

Make sure to properly configure it, in particular set testSourceDirectory to point to the directory that contains your Gatling classes, typically:
```
<build>
  <testSourceDirectory>src/test/scala</testSourceDirectory>
</build>
```
Then, you should disable the Gatling compiler so you don’t compile twice:
```
<configuration>
  <disableCompiler>true</disableCompiler>
</configuration>
```

Plugin config:
```
      <plugin>
        <groupId>net.alchim31.maven</groupId>
        <artifactId>scala-maven-plugin</artifactId>
        <version>${scala-maven-plugin.version}</version>
        <executions>
          <execution>
            <goals>
              <goal>testCompile</goal>
            </goals>
            <configuration>
              <jvmArgs>
                <jvmArg>-Xss100M</jvmArg>
              </jvmArgs>
              <args>
                <arg>-target:jvm-1.8</arg>
                <arg>-deprecation</arg>
                <arg>-feature</arg>
                <arg>-unchecked</arg>
                <arg>-language:implicitConversions</arg>
                <arg>-language:postfixOps</arg>
              </args>
            </configuration>
          </execution>
        </executions>
      </plugin>
```


### Introduction to Load Testing with Gatling

Source-code from the Introduction to Load Testing with [Gatling](http://gatling.io) articles.

<li>The first article can be found here: https://www.ivankrizsan.se/2016/04/16/introduction-to-load-testing-with-gatling-part-1/</li>
<li>The second article can be found here: https://www.ivankrizsan.se/2016/04/24/introduction-to-load-testing-with-gatling-part-2/</li>
<li>The third artcile can be found here: https://www.ivankrizsan.se/2016/04/26/introduction-to-load-testing-with-gatling-part-3/</li>
<li>The fourth and final article can be found here: https://www.ivankrizsan.se/2016/05/06/introduction-to-load-testing-with-gatling-part-4/</li>
