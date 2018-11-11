# Spring Boot + Spring Security + OAuth2

Example Spring Boot + Hibernate + Spring Security + OAuth2 project for demonstration purposes. 

## Getting started
### Prerequisites:
- Java 8
- Maven
- H2/PostgreSQL

It is possible to run application in one of two profiles:
- h2
- postgres

depending on database engine chose for testing. 

To enable cache statistics `dev` profile needs to be turned on.

### Testing database schema
![database-schema](src/main/docs/db_schema.png)

### Authentication

```
curl -X POST \
  http://localhost:8080/oauth/token \
  -H 'authorization: Basic c3ByaW5nLXNlY3VyaXR5LW9hdXRoMi1yZWFkLXdyaXRlLWNsaWVudDpzcHJpbmctc2VjdXJpdHktb2F1dGgyLXJlYWQtd3JpdGUtY2xpZW50LXBhc3N3b3JkMTIzNA==' \
  -F grant_type=password \
  -F username=admin \
  -F password=admin1234 \
  -F client_id=spring-security-oauth2-read-write-client
```
### The authorization: Basic hash comes from 
###   SELECT client_id 
###   FROM oauth_client_details
### Then -> : clientHash = client_id + ":" + client_id + "-" + "password1234"
### and base64 those values like this
### byte[] encodedBytes = Base64.encodeBase64("spring-security-oauth2-read-client:spring-security-oauth2-read-client-password1234".getBytes());
### print("encodedBytes to read: " + new String(encodedBytes));
### -> c3ByaW5nLXNlY3VyaXR5LW9hdXRoMi1yZWFkLWNsaWVudDpzcHJpbmctc2VjdXJpdHktb2F1dGgyLXJlYWQtY2xpZW50LXBhc3N3b3JkMTIzNA==
###  encodedBytes = Base64.encodeBase64("spring-security-oauth2-read-write-client:spring-security-oauth2-read-write-client-password1234".getBytes());
###  print("encodedBytes to read/write: " + new String(encodedBytes));
### -> c3ByaW5nLXNlY3VyaXR5LW9hdXRoMi1yZWFkLXdyaXRlLWNsaWVudDpzcHJpbmctc2VjdXJpdHktb2F1dGgyLXJlYWQtd3JpdGUtY2xpZW50LXBhc3N3b3JkMTIzNA==
### The field client_secret in the table oauth_client_details comes from hashing the password for the basic auth. In this case,
### Encoders enc = new Encoders(); 
### enc.userPasswordEncoder().encode(spring-security-oauth2-read-client-password1234) and 
### enc.oauthClientPasswordEncoder().encode(spring-security-oauth2-read-write-client-password1234)
### resulting in
### "$2a$04$WGq2P9egiOYoOFemBRfsiO9qTcyJtNRnPKNBl5tokP7IP.eZn93km"
### "$2a$04$soeOR.QFmClXeFIrhJVLWOQxfHjsJLSpWrU1iGxcMGdu.a5hvfY4W"
###
### 
### 
### 
### Accessing secured endpoints

```
curl -X GET \
  http://localhost:8080/secured/company/ \
  -H 'authorization: Bearer e6631caa-bcf9-433c-8e54-3511fa55816d'
```

```
CORS fix
https://stackoverflow.com/questions/43228345/spring-crossorigin-issue-no-access-control-allow-origin-header-is-present-on-th/43534475
```
