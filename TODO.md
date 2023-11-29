TODO tasks:

GENERAL:
-
MIDDLE:
- metrics monitoring research and impl (Prometheus, Graphana)
- add prometheus to docker.compose
- enable actuator metrics to only prometheus 

GATEWAY:
-
LOW:
- do cors configuration programmatically, because it is better to get CentralizedSwagger Origin from Discovery

CENTRALIZED SWAGGER: 
-
HIGH:
MIDDLE:
LOW:

GRPC-COMMON:
- LOW:
add dependency versions to gradle.properties

USERS-SERVICE:
MIDDLE:
- change apis in a way to get Identification from header
LOW:
- liquibase & jOOQ working together


- security
     security properties (auth service, user service) from one place
     write login date to db
     issue method logic
     send request to user service if needed
     grpc
     test if needed
- login api test
- kibana
- add retry to all microservices while send request
- mock-processing
- mock-billing
- security
- find a way to create database automatically
