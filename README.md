# NMBS Is A Joke - Twitter Bot

NMBS Is A Joke is a Twitter bot written in Java using the Spring Boot Framework.

## Create war-file to deploy on a server
1. Add following line in the main pom.xml-file: \
`<packaging>war</packaging>`
2. Run the following maven command: \
`mvn package` or `mvn clean install` (even better)

## Raspberry Pi deployment
1. Connect via SSH: \
`ssh pi@[rpi IP address]`
2. Update the package index: \
`sudo apt update`
3. Update your rpi to the latest version (not mandatory): \
`sudo apt full-upgrade -y`
4. Install Apache: \
`sudo apt install apache2`
5. Install Java: \
`sudo apt install default-jdk`
6. Verify the Java installation: \
`java -version` \
\
The version will be shown as the following:
```
openjdk version "11.0.16" 2022-07-19
OpenJDK Runtime Environment (build 11.0.16+8-post-Debian-1deb11u1)
OpenJDK 64-Bit Server VM (build 11.0.16+8-post-Debian-1deb11u1, mixed mode)
```
***Attention***: this means that you cannot compile NMBS Is A Joke with a higher SDK version than 11 (lower is ok) <br/>

7. Copy your war-file to a folder to the raspberry pi. This can be easily done by using a file transfer application like FileZilla. <br/> 
   This can also be done using the command SCP over SSH ([How to](https://howchoo.com/pi/how-to-transfer-files-to-the-raspberry-pi)).
8. Create a new directory under war like the following: \
`sudo mkdir /var/nmbs_is_a_joke`
9. Move your copied war-file to the newly created folder. In following command we assume it is copied on the Desktop: \
`sudo mv /home/pi/Desktop/nmbs_is_a_joke-0.0.1-SNAPSHOT.war /var/nmbs_is_a_joke`
10. You can now launch the application: \
`java -jar /var/nmbs_is_a_joke/nmbs_is_a_joke-0.0.1-SNAPSHOT.war` <br/>

***Attention***: the application will close together with the SSH connection. See the following section to create an automated service to avoid this!

### Creating a service

These steps have been found on the following [Medium post from Stuart Yee](https://medium.com/geekculture/turn-your-raspberry-pi-into-a-server-to-run-your-java-spring-mvc-app-862214279587). <br/>
https://ubiq.co/tech-blog/how-to-change-port-number-in-apache-in-ubuntu/