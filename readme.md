两者均可
```shell
mvn clean package -Pnative-debug

mvn clean package -Pnative-release
```

原理：
附加探针
``` shell
 java -agentlib:native-image-agent=config-output-dir=src/main/resources/META-INF/native-image/,experimental-class-define-support --enable-preview -jar
```