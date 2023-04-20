# Manajemen_Usaha_BNMO
Tugas Besar II IF2210 Pemrograman Berorientasi Objek

# Prerequisites
[Maven v3.6.x](https://maven.apache.org/download.cgi)

[Amazon Corretto OpenJDK](https://docs.aws.amazon.com/corretto/latest/corretto-17-ug/downloads-list.html)

After install `amazon corretto` dont forget to add to system env variables & update path (Replace JAVA_HOME value to amazon corretto open jdk path)
# Project setup
After cloning this repo, run:
```bash
mvn clean install         # to enable lombok annotations and other stuff inside pom.xml
```
## Install `Lombok Annotations Support for VS Code` if you are using VSCODE as IDE

# How to run
1. Build the project (make sure you are in the same dir as pom.xml)
```bash
mvn clean test javafx:run
```
2. Go to target/
```
cd target
```
3. Run the .jar
```
java -jar tubesOOP2-1.0-jar-with-dependecies.jar
```

# Filename (aside composables files)
All in PascalCase format

# Java variables & function name convention
All in camelCase. Constant are in SCREAMING_SNAKE_CASE

## Note: Remember to add comments for readability
