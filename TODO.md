TODO tasks:

GENERAL:
-
MIDDLE:
- metrics monitoring research and impl (Prometheus, Graphana)
- configure prometheus for multi-module project
- enable actuator metrics to only prometheus 
- test prometheus alerts to send alert to some email
- do prometheus research like pros and cons
- discover services automatically in prometheus.yml

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
try to add dependency versions to gradle.properties

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
