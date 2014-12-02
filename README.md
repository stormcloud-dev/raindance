RainDance
==========

RainDance is a JSON map editor for use with StormCloud - it's used by the developers to easily recreate the Risk of Rain maps

Developing
----------

### Getting the code

Clone this repository with ```git``` first of all - the clone URL should be on the right hand side of this page.

### Setting up your environment

RainDance is written using [Java 8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html) - so you'll need JDK 8 to work on it.
We recommend using [IntelliJ IDEA](https://www.jetbrains.com/idea/) to work on RainDance (though of course Eclipse or NetBeans should work too)
Using IntelliJ IDEA, import the project from Gradle and synchronize with the ```build.gradle```
Create a Run Configuration, set the main class to ```RainDance```, the classpath to your ```raindance``` module, and the rest should be set by default. You should now be set to run the application from within IntelliJ.
If you want to make IntelliJ build with Gradle instead of the default compiler, you can switch the Make task for a Gradle goal with the command line directive ```shadowJar```

Building
--------

StormCloud is currently build with [Gradle](http://www.gradle.org/)
To build the StormCloud jar, use ```gradle shadowJar``` - this will create ```raindance-x.x-all.jar``` in the ```build/libs``` directory.

Running
-------

As with any other command-line Java application, run RainDance with:

```
java -Xms512M -Xmx1024M -jar raindance-x.x-all.jar
```

(Of course, you can change max/min stack size according to the amount of resources you have available and desired performance)

Contributing
------------

There isn't much that needs to be done here. See the [StormCloud](https://github.com/stormcloud-dev/stormcloud/) repository instead.

### Programmers

If you wish to help, first see if there is an issue you wish to solve. If so, fork the project and make a pull request including your changes. If not, raise a ticket with your issue or feature request.

### Technical writers

Open a pull request with any changes you wish to add to JavaDocs - these are much appreciated and help people both developing on the project itself, and third-party extensions.
