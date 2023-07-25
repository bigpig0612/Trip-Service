# Trip_Service

## Description

Coding exercise for Littlepay interview.

## Technology and Library

- Java 17
- Spring Boot
- Maven
- OpenCSV
- lombok
- JUnit
- Mockito

## Assumption

+ Calculates and processes trips based on the tab records
+ Each trip requires a matching Tab ON and Tab OFF with the same PAN, CompanyID, and BusID.
+ If any of PAN, CompanyID, or BusID is different, it will be considered as two separate trips.
+ Adds an ON_KNOW status to mark Tab OFFs that couldn't be matched with any Tab ON.
+ Only support 3 stops, which are named Stop1, Stop2 and Stop3

## Trip creation logic design

1. Retrieve all Tab records and group them into a Map<String, List<Tab>> based on the combination of PAN_CompanyID_BusID as the key. The List<Tab> within each group should be sorted in reverse order based on DateTimeUTC.
2. Iterate through the Map<String, List<Tab>> where each key-value pair represents a collection of Tab ON and Tab OFF records for a specific PAN operating a bus service for a particular company.
3. Traverse through each List<Tab> in the value set. Since the List is sorted in reverse order by time, we need to pair up Tab records to construct a Trip. This includes handling the following scenarios:

   | currentTab status | previousTab status |                         Trip status                          |
      | :---------------: | :----------------: | :----------------------------------------------------------: |
   |        OFF        |         ON         | 1 trip will be created based on these 2 tabs, the status is COMPLETE |
   |        ON         |         ON         |    2 trip will be created, the status is both incomplete     |
   |        OFF        |        OFF         | 1 trip will be created based on current tab, the status is unknown |


## Directory Structure
```
com.littpepay.tripservice
├── model
│   ├──Tab.java
│   ├──Trip.java
├── processor
│   ├──TripProcessor.java
├── repository
│   ├──TabRepository.java
│   ├──TripRepository.java
├── service
│   ├──TabService.java
│   ├──TripService.java
├── util
│   ├──CsvHelper.java
│   ├──LocalDateTimeConverter.java
│   ├──TrimFilter.java
└── TripServiceApplication.java
```

## Application Run

**On Mac or Linux**:

```sh
./mvnw spring-boot:run
```

**On Windows**:

```sh
mvnw.cmd spring-boot:run
```