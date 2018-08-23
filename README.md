Please read the documentation of the GitHub API at at <https://developer.github.com/v3/>. Suppose that you worked with a team that was tasked with implementing this specification.

1. What concerns would you have from a testing perspective? 
2. How would you go about tackling the QA for this work? 
3. What sort of tests would be worth describing or worth automating?
4. What tools would you use? 


Please select an endpoint to test and implement a test suite for that endpoint. You may choose a tech stack of your choice for the tests. Provide the URL of a public Git repo that contains the tests. Include documentation in your repository that contains your written answers to the questions above.


**What concerns would you have from a testing perspective?**

Answer: 

1.	There are lot of Components (Like Users, Repositories, Gists, Activity .. etc). Need to identify the flow of the entire system. 
2.	Identify dependencies between each of those components like  using any Architecture or data flow diagram
	* Eg : for testing commits API, you need to have repos first.
3.	Having test data is really important and how do we create them 
4.	As this system is designed lot of load, we need to do load testing to see how many requests that the server handle
5.	Security testing is really important as we don’t to compromise on the data.
6.	How do we test integration with 3rd party apps ?
7.	How do we test migration i.e ensuring all the data is migrated without data loss
8.	What are the SLAs(Service Level Agreements) on Search APIs (as its expensive operation its better that these are defined) 
9.	How do the user see health-check of Git Server 
10.	How to you test managing different branches, conflicts etc


**How would you go about tackling the QA for this work?**

Answer: 

1.	Read through API documentation, Understand them 
2.	Identify interdependencies and come up with order of APIs to test (Users, Repos, Projects, Organisations, Pull requests.  etc) 
3.	Identify ways to reuse the data in your testing 
4.	Then estimate the time taken for testing each of the component 
5.	Identify areas of automation which can help in regression testing like components that are already tested 
6.	Identify areas where load testing is needed
7.	Start with writing test suites, test cases - Focus on git functionality and efficiency, make sure to get proper response code and proper error message in case of errors
8.	Have positive and negative tests for tests. Validate if you are getting proper error code for invalid data
9.	Have a product owner and/or QA lead review 
10.	Once all the components are testing, plan for regression tests after bug fixes are tested (if any)
11.	Plan for production deployment

**What sort of tests would be worth describing or worth automating?**

Answer: 

1.	Generally automation testing is beneficial in terms of ROI (Return of Investment) in below cases 
	* Repetitive tests that run for multiple builds.
	* Tests that require multiple data sets.
	* Tests that tend to cause human error.
	* Frequently used functionality that introduces high risk conditions.
	* Tests that run on several different hardware or software platforms and configurations.
	* Tests that take a lot of effort and time when manual testing.
2.	In Git APIs, that are lot of places where we can try to automate to save and effort.  I am providing couple of examples where we can do it. 
	* There are some features that are common for git normal user and an enterprise user. Identify the common functionality and automate them. For the same test, you can try providing enterprise user credentials and normal user credentials 
	* Git provides 3 different ways of Authenticating the user (like Basic, OAuth2 sent in parameter, OAuth2 sent in header), you can try passing different authentications for the same test. 
	* Writing a utility for comparing SHA (as this is used in multiple places) 

**What tools would you use?**

Answer:

1.	For Manual testing APIs they are multiple tools in market. Popular among them are SOAP UI (Paid tool) & Post Man (Free tool) 
	* I personally prefer PostMan as it has rich features for testing APIs like (Providing Easy functionalities handy like Authentication, SetEnvironment, Predefined Variables, Ability to save collection, Share with team, by default it provides, status code, time taken for response and size of the response, Ability  to do prerequisite script and run validation tests on response json) 
	* PostMan has integration with jenkins for CI 
	* PostMan collections can be run using newman (where it will show some reports) after execution
2.	For Automation of APIs, we can use Rest Assured  & TestNG Libraries (using Java)  with has BDD style implementation of it has inbuilt functions to validate the response JSONs.  

**Testing the USERS GitHubAPI Endpoint - https://developer.github.com/v3/users/**

1.	Get a single user - GET /users/:username
2.	Get the authenticated user -  GET /user
3.	Update the authenticated user -  PATCH /user
4.	Get all users - GET /users

**Test Suite**

**Get a single user - GET /users/:username**

Tests: 

1.	Verify the user is able to get details about single user when valid authentication is used
	* Verify response code (200 OK) and response JSON 
2.	Verify the user is able get the details of the user using all 3 different authentication methods
	* Using Basic Authentication - By Sending base 64 of UserName and Password 
	* By Sending OAuth2 in header 
	* By Sending OAuth2 in parameter 
3.	Verify if you are able to get proper error message and status code when pass invalid or incorrect username/password 
4.	Verify if you are able to get proper error message and status code when pass invalid or incorrect username/password for Basic Auth
5.	Verify if you are able to get proper error message and status code when using invalid or incorrect Access Token 
6.	Verify the successful response JSON format is as per the documentation
7.	Verify email is populated in response JSON only if user set primary email address as “public” 
8.	Verify email field is set to null in response JSON if user set primary email address as not set to “public” 



**Get the authenticated user -  GET /user**

Tests:

1.	Verify the user is able to get details about single user when valid authentication is used
	* Verify response code (200 OK) and response JSON 
2.	Verify the user is able get the details of the user using all 3 different authentication methods
a.	Using Basic Authentication - By Sending base 64 of UserName and Password 
b.	By Sending OAuth2 in header 
c.	By Sending OAuth2 in parameter 
3.	Verify if you are able to get proper error message and status code when pass invalid or incorrect username/password 
4.	Verify if you are able to get proper error message and status code when pass invalid or incorrect username/password for Basic Auth
5.	Verify if you are able to get proper error message and status code when using invalid or incorrect Access Token 
6.	Verify the response JSON is as per the documentation when user is authenticated using “user” scope
7.	Verify the response JSON is as per the documentation when user is authenticated without “user” scope

**Update the authenticated user -  PATCH /user**

Tests:

1.	Verify the user is able to get update only these parameters in the documentation for a user
a.	Name 
b.	Email
c.	Blog
d.	Company
e.	Location
f.	Hireable
g.	Bio
2.	Verify the response JSON for the successful Update 
3.	Verify email is not present in the GET /user response even after updating email if the user’s primary email is not set to public 
4.	Verify if the API  is providing appropriate error message if the user is authenticated
5.	Verify that the other fields in the user response payload like (followers_url, site_admin, ..etc) are not updated even provided in the PATCH payload. 

**Get all users - GET /users**

Tests:

1.	Verify this API call with list all the users since the id 
2.	Verify the response JSON for GET/users is paginated as parameters provided (like page=0&per_page=100)
3.	Verify that the response JSON payload for the user is as per the documentation 
4.	Verify the API doesn’t return anything if since, page and per_page parameters are not provided. 
