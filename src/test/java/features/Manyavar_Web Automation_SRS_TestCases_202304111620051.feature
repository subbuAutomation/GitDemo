Feature: Manyavar_Web Automation_SRS_TestCases_202304111620051
#Regression Type
#Correct Values = true
#Incorrect Values = false
#Illegal Values = false
#Invalid Values = false
#Boundary Values = false
#Edge Cases Values = false

@Test
@uida323845565
@set21
@test001
@Home_page
Scenario Outline: Search for different products and verify that the products are displayed.
Given I have access to application
When I entered Brand name in login page as '<Brand name1>'
And I clicked Search in login page
And I entered Product names in home screen as '<Product names2>'
And I clicked Seach icon in home screen
Then verify displayed Product page in home screen
And '<page>' is displayed with '<content>'

Examples:
|SlNo.|Brand name1|Product names2|page|content|
|00001|Manyavar|Casualshoes|NA|NA|

#Total No. of Test Cases : 5

@Test2
@uid1291529172
@set21
@test002
Scenario Outline: Search for product, verify that the price in listing page  and description page are same
Given I have access to application
When I entered Brand name in login page as '<Brand name1>'
And I clicked Search in login page
And I entered Product names in home screen as '<Product names2>'
And I clicked Seach icon in home screen
Then verify displayed Product list in product listing page
When I copied number Price in product listing page
And I clicked Product image in product listing page
Then verify copied number Discription price in discription page
And '<page>' is displayed with '<content>'

Examples:
|SlNo.|Brand name1|Product names2|page|content|
|1|Manyavar|saree|Current Screen|NA|


#Total No. of Test Cases : 6

@Test3
@uid368893081
@set21
@test003
Scenario Outline: In listing page, test for sort options. Further verify if the products are sorted low to high or high to low
Given I have access to application
When I entered Brand name in login page as '<Brand name1>'
And I clicked Search in login page
And I entered Product names in home screen as '<Product names>'
And I clicked Seach icon in home screen
Then verify displayed Product list in product listing page
When I mousehover Sort by in discription page
And I clicked Price high to low in discription page
Then verify high to low Cost in discription page
When I mousehover Sort by in discription page
And I clicked Price low to high in discription page
Then verify low to high low cost in discription page
And '<page>' is displayed with '<content>'

Examples:
|SlNo.|Brand name1|Product names|page|content|
|1|Manyavar|Shirts|Current Screen|NA|


#Total No. of Test Cases : 7

