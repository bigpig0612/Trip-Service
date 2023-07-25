# Trip_Service

## Description

## Technology and Library

## Assumption

+ Calculates and processes trips based on the tab records
+ Each trip requires a matching Tab ON and Tab OFF with the same PAN, CompanyID, and BusID.
+ If any of PAN, CompanyID, or BusID is different, it will be considered as two separate trips.
+ Adds an ON_KNOW status to mark Tab OFFs that couldn't be matched with any Tab ON.

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

## Quick Start

## TODO Features
