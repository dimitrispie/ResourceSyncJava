A generic ResourceSync Java library for implementing the ResourceSync functionality to DSpace platform.

Compile the library with:

  `mvn package`

Install the ResourceSync Java library in DSpace:

`mvn install:install-file -Dfile=resourcesync-1.1-SNAPSHOT.jar -DgroupId=org.dspace \
-DartifactId=resourcesync -Dversion=1.1-SNAPSHOT -Dpackaging=jar`
