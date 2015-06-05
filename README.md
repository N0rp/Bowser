# Bowser

## Open in IntelliJ

* Open Intellij and have Gradle plugin installed
* Click on File>New>Project From Existing Sources
* Select build.gradle in the root directory

## Run Configuration
To run the application you need to include the Leap libraries in your java library path.
Download the Leap SDK and unzip it <somelocation>. Then add the following vm options to your run configuration:
-Djava.library.path=<somelocation>

More information about which Leap Motion Libraries you require can be found in the Leap Motion
(https://developer.leapmotion.com/documentation/java/devguide/Project_Setup.html)[Dev Guide].