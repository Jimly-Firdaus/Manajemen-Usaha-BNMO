# Manajemen_Usaha_BNMO
Tugas Besar II IF2210 Pemrograman Berorientasi Objek
<br />

## Table of Contents
* [General Info](#general-information)
* [Requirements](#requirements)
* [Setup](#setup)
* [How To Run](#how-to-run)
* [Tech Stack](#tech-stack)
* [Project Structure](#project-structure)
* [Credits](#credits)

## General Information
Program untuk mensimulasikan suatu manajemen usaha.

## Requirements
[Amazon Corretto OpenJDK](https://docs.aws.amazon.com/corretto/latest/corretto-17-ug/downloads-list.html)

After installing `amazon corretto`, dont forget to add to system environment variables & update path (Replace JAVA_HOME value to amazon corretto open jdk path)

## Setup
After cloning this repo, run:
```bash
mvn clean install         # to enable lombok annotations and other stuff inside pom.xml
```

Install `Lombok Annotations Support for VS Code` if you are using VSCODE as IDE

## How To Run
1. Build the project (make sure you are in the same directory as pom.xml)
```bash
mvn clean install
```
2. Go to target/
```
cd target
```
3. Run the .jar
```
java -jar tubesOOP2-1.0-jar-with-dependecies.jar
```
or 
1. Type the following command in the terminal (make sure you are in the same directory as pom.xml)
```
.\run.bat
```

## Tech Stack
### Programming Language
* Java 8 and above

## Project Structure
```bash
Manajemen_Usaha_BNMO
│
├───.github/workflows
│    └── continoud-integration.yml
│
├───src
│    ├── main/java/com
│    │    ├── gui
│    │    │    ├── components
│    │    │    │    ├── BaseButton.java
│    │    │    │    ├── BaseCard.java
│    │    │    │    ├── BaseToggle.java
│    │    │    │    ├── holaLogo.gif
│    │    │    │    └── logo.png
│    │    │    │
│    │    │    ├── interfaces
│    │    │    │    ├── PageSwitcher.java
│    │    │    │    └── RouteListener.java
│    │    │    │
│    │    │    ├── pages
│    │    │    │    ├── AddProductInventory.java
│    │    │    │    ├── AddingProductNumber.java
│    │    │    │    ├── BillList.java
│    │    │    │    ├── InventoryManagement.java
│    │    │    │    ├── MainPage.java
│    │    │    │    ├── MemberDeactivationPage.java
│    │    │    │    ├── PageLaporan.java
│    │    │    │    ├── PageSettings.java
│    │    │    │    ├── PaymentHistoryPage.java
│    │    │    │    ├── PaymentPage.java
│    │    │    │    ├── ProductClick.java
│    │    │    │    ├── ProductDesc.java
│    │    │    │    ├── RegistrationPage.java
│    │    │    │    ├── UpdateInfoPage.java
│    │    │    │    ├── UserInfosPage.java
│    │    │    │    ├── UserPage.java
│    │    │    │    └── addCustomer.java
│    │    │    │
│    │    │    └── Router.java
│    │    │
│    │    ├── logic
│    │    │    ├── constant
│    │    │    │    ├── interfaces
│    │    │    │    │    └── IPayment.java
│    │    │    │    │
│    │    │    │    └── Payment.java
│    │    │    │
│    │    │    ├── feature
│    │    │    │    ├── interfaces
│    │    │    │    │    ├── IBill.java
│    │    │    │    │    └── Payable.java
│    │    │    │    │
│    │    │    │    ├── Bill.java
│    │    │    │    ├── Customer.java
│    │    │    │    ├── Inventory.java
│    │    │    │    ├── ListOfProduct.java
│    │    │    │    ├── Member.java
│    │    │    │    ├── Product.java
│    │    │    │    └── VIP.java
│    │    │    │
│    │    │    ├── output
│    │    │    │    └── Printer.java
│    │    │    │
│    │    │    └── store
│    │    │    │    ├── interfaces
│    │    │    │    │    ├── Parseable.java
│    │    │    │    │    └── Storeable.java
│    │    │    │    │
│    │    │    │    ├── DataStore.java
│    │    │    │    ├── ParserJSON.java
│    │    │    │    ├── ParserOBJ.java
│    │    │    │    └── ParserXML.java
│    │    │
│    │    ├── Launcher.java
│    │    └── Main.java
│    │
│    └── test
│         ├── java
│         │    ├── DataStoreTest.java
│         │    ├── InventoryTest.java
│         │    ├── PrinterTest.java
│         │    └── UserTest.java
│         │
│         └── testrun.bat
│
├───README.md
│
├───pom.xml
│
└───run.bat
```

## Credits
This project is implemented by:
1. Wilson Tansil (13521054)
2. Bill Clinton (13521064)
3. Husnia Munzayana (13521077)
4. Jimly Firdaus (13521102)
5. Asyifa Nurul Syafira (13521125)