# nodejs-aes-java
node.js使用aes-256-cbc加密，java端进行解密操作
 java端依赖jar包
 
[commons-codec-1.10.jar](http://central.maven.org/maven2/commons-codec/commons-codec/1.10/commons-codec-1.10.jar)

[bcprov-jdk15on-1.57.jar](http://www.bouncycastle.org/latest_releases.html)

由于```java```端jdk限制秘钥长度最长为128位，而aes-256-cbc所需秘钥长度为256，故需要去oracle官网下载两个jar包替换jre下原有jar包。

[Java Cryptography Extension (JCE) Unlimited Strength Jurisdiction Policy Files 8](http://www.oracle.com/technetwork/java/javase/downloads/jce8-download-2133166.html)

下载之后替换%JAVA_HOME%/jre/lib/security 下的包即可




