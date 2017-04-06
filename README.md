# Java-AES
Java AES tool for encrypting/decrypting data using streams.

## Usage:

### Encrypt
```java
AES.encrypt("myPassword", new FileInputStream("inputFile.txt"), new FileOutputStream("encryptred.aes"));
```

### Decrypt
```java
AES.decrypt("myPassword", new FileInputStream("encryptred.aes"), new FileOutputStream("decrypted.txt"));
```

## Licence:
The MIT License (MIT)
