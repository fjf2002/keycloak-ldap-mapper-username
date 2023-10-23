# Keycloak LDAP Attribute mapper for username

Uses the LDAP mail attribute to set the keycloak username.
Truncates at the '@' character, i. e. "user@domain.com" becomes "user".

Since most of this code is just inherited from UserAttributeLDAPStorageMapper, you MUST set

 * "User Model Attribute" to username,
 * "LDAP Attribute" to mail.
 
in the GUI.


## Build JAR

```bash
./gradlew assemble -Pdependency.keycloak.version=${KEYCLOAK_VERSION}
```

## Install
Copy the jar file (`./build/libs`) to the keycloak providers directory.

You need to rebuild and restart Keycloak (i. e. do not just restart with `--optimized` flag.).
