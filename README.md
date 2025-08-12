# SmitedData
SmitedData is a small libary, which makes creating files easier

# Installation
*explained at https://jitpack.io/#SmitedReal/SmitedData*

## Steps


## MAVEN
Add this to your ``pom.xml`` file:
```xml
	<repositories>
		<repository>
		    <id>jitpack.io</id>
		    <url>https://jitpack.io</url>
		</repository>
	</repositories>
```
then, add this to your ``pom.xml`` file:
 ```xml
 <dependencies>
	 <dependency>
	     <groupId>com.github.SmitedReal</groupId>
	     <artifactId>SmitedData</artifactId>
	     <version>Tag</version>
	 </dependency>
</depencendies>
 ```
If you already have one dependency install, remove the
```xml
 <dependencies></dependencies>
```
from the file

## GRADLE
Add this to your ``build.gradle`` file.
```gradle
	dependencyResolutionManagement {
		repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
		repositories {
			mavenCentral()
			maven { url 'https://jitpack.io' }
		}
	}
```
Then, add this to your ``build.gradle`` file:
```gradle
	dependencies {
	        implementation 'com.github.SmitedReal:SmitedData:Tag'
	}
```

## Gradle.kts, sbt, leiningen
*All of these can be found on https://jitpack.io/#SmitedReal/SmitedData/1.2*

# Examples
All examples for Java can be found at https://github.com/SmitedReal/SmitedData/tree/master/src/main/java/smitedreal/smiteddata/examples