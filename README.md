# java-callflow-01

Test project for java callflow analysis

## Development

### Requirements

* Java 17
* Apache Maven 3.9+

### Build

```bash
mvn clean package

# or to build the distribution package
mvn clean package -Pdist
```

### Run

```bash
unzip -d target/dist target/jenkins-examples-callflow-*-dist.zip
java -jar target/dist/jenkins-examples-callflow-*/lib/jenkins-examples-callflow-1-SNAPSHOT.jar
```

## Reachability Analysis

### Test with IQ CLI

```bash
# build the distribution package
mvn clean package -Pdist

# run the analysis
java -jar <path-to>/nexus-iq-cli-*.jar \
-a admin:admin123 -s http://localhost:8070 -t build -i local-iq-app \
-ra -rn org.sonatype.lifecycle.jenkins.examples.callflow \
target/**/*.zip
```

### Test with Jenkins

In a test Jenkins instance, create a new pipeline job and use the `Jenkinsfile` present in the root folder.
