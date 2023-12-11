TODO tasks:

GENERAL:
-
MIDDLE:
- metrics monitoring research  (Prometheus, Graphana)
- configure prometheus for multi-module project
- enable actuator metrics to only prometheus 
- test prometheus alerts to send alert to some email
- do prometheus and graphana research like pros and cons
- graphana JVM dashboard and integration with prometheus in yaml like predefined config possible?
- discover services automatically in prometheus.yml
- SonarQube check to all projects

BANK-MOCK:
MEDIUM:
add validation

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


payment service
bank mock service
kafka + notification service

TECH STACK:
AWS deploy + Jenkins - Jmeter        
prometheus + grafana                 
mongodb, elk                            
kafka                                   
Zipkin                                         
k8s,ingress                                
sonarqube                                
Cache (Redis)                                   
Resilence4j2 + Fault tolerance                 
Go                                          
ChatGPT                               
Machine learning model                 