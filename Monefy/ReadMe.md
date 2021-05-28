## N26 Test Automation Task

In this File I'm going to explain <b>How to configure </b> and <b>Run this Framework</b>

The tech stack used for developing this framework are:
1. **JAVA** as the programming language for writing test code
2. **Appium** as the automation tool for automating Android device
3. **Cucumber** as the BDD tool for developing the framework
4. **TestNg** as the unit test framework
5. **Maven** as the build tool
6. **Eclipse** as the preferred IDE for writing java code.
7. **Rest Assured** as the Library to automate API(s).
8. **Extent Report** as the reporting tool.

#### Getting Started
Setup your machine.
1. Install JDK 1.8 & set "JAVA_HOME" in environment variable
2. Install Eclipse
3. Install Node
4. Install Appium
5. Set "ANDROID_HOME" & "Node_Path" as a environment variable
6. Download and Set "Maven_Home" as a environment variable
7. Real Android Device: Make sure developer options and USB debugging is enabled in that device and connect via USB cable
8. Install Cucumber Eclipse plugin

#### Cloning & Importing the Project
1. Clone the project from ```git clone https://github.com/PriyaPramod/pramod-siddaramaiah.git```
2. Import the project (Monefy) in Eclipse ```File -> import -> General -> Existing Project into workspace -> Next -> Browse Project Location ```
3. Now click on ```Finsih``` wait until the Eclipse downloads all the dependencies
4. Right click on the project maven -> update project -> please wait till the project building completes

#### Running tests
``Note:`` Sometime JRE System Library might be referring the wrong Library, please do select the JDK path in Build Path ```
1. You can run the tests directly from the Eclipse, by right-clicking POM.xml and **maven test**.
2. After the execution Execution report will be generated with the time stap under "Reports" folder, right click on the monefy.html to see the detailed results.
3. Framework will executes on Both Mac & Win [Make sure the above mentioned tools are installed and configured].
4. For Mac: ```mvn clean install```
5. For Windows: ```mvn clean install```

---

## UI Test

### Monefy

#### [Test 1]::Validate Balance is calculated correctly
1. Adding the Income & Expenses and calculating is Balance is calculated correctly.

#### [Test 2]:: Validate user is able to select the range to see the financial status between the intervals
1. Adding the Income & Expenses and Selecting the intervals to check is APP shows the financial status of the selected time intervals.

### Monefy

#### [Test 1]::Add new Pet to Strore and Validating the response
1. Add new Pet to Store and Validating the response using ID, Category, Tags

#### [Test 2]::Delete the pet using ID and validate Pet deleted.
1. Creating a pet, validating, deleting & validating whether the pet deleted.

#### Reason to choose BDD framework approach 
1. BDD is meant to be collaborative. Behaviour Driven Development (BDD) framework helps to attain all the prospects of a technical or business team.
2. Universal language which is very easy to describe
3. Business Value
4. Partnership between Business Analysts, Stake-holders, QA Team and developers
5. Separation of Test Cases from Test Code
6. Inherent Re-usability
7. Aspect-Oriented Controls
8. Easier Reviews
9. Clarity, Steam-lining, Shift left
10. Maintainable 


