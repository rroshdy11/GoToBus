Introduction
_____________________
GoTo Bus is a company for operating bus trips in Egypt, They have several trips per day all over Egypt.
They have one station in every Governorate so they need to reduce labor and human error by
digitalizing the way they operate. So they decided to request from your team to start developing a
BackEnd system that provides several Web Services that satisfies their business needs.
______________________________________________________________________________________________________________________________________
System Functionalities:
● System should provide user management subsystem for New User Registration and Login
● User can have two different roles Client and Administrator roles
● System provides a Station entity which describes the name of the station and its map
coordinates
● System provides a trip entity which describes the from and to stations, datetime of the trip and
number of seats
● Trip Entity Management is done by the Admin role only
● Users and Non-Users can search for trips using from and to stations and from and to dates
showing the trip details including number of seats available
● Logged in Users can book a trip if available seats exists
● User should get a notification asynchronously when the booking is done successfully
● Entities of the system: User, Station, Trip, Notification
Important Note: GoTo Bus is providing API references for the requested functionalities, You can find
them at the end of this document.



_________________________________________________________________________________________________________________________________________________

APIs Reference
__________________
Name: Create User
URL: /api/user
Method: POST
Parameters: None
Request
Body:
{
"username":"mohamed",
"password":"test123",
"full_name":"Mohamed Ahmedl",
“role”: “client”
}
Response
Status: 200 OK

_______________________________________

Name: Login
URL: /api/login
Method: POST
Parameters: None
Request
Body:
{
"username":"mohamed",
"password":"test123",
}
Response
Status: 200 OK (If correct)
Status: 400 (If not correct)

________________________________

Name: Create Station
URL: /api/station
Method: POST
Parameters: None
Request
Body:
{
"name":"Aswan",
"longitude":"23.5184815151",
"latitude":"21.51548481515"
}
Response
Status: 200 OK (If correct)
Status: 500 (If exception happened)
____________________________________

Name: Get Station
URL: /api/station/{id}
Method: GET
Parameters: id
Request
None
Response
Status: 200 OK (If correct)
Status: 500 (If exception happened)
Body:
{
"name":"Aswan",
"longitude":"23.5184815151",
"latitude":"21.51548481515"
}
______________________________________

Name: Create Trip
URL: /api/trip
Method: POST
Parameters: NONE
Request
Body:
{
"from_station":"Cairo",
"to_station": "Aswan",
"available_seats": 50,
"departure_time": "25/05/2022 15:00:00",
"arrival_time": "25/05/2022 20:00:00"
}
Response
Status: 200 OK (If correct)
Status: 500 (If exception happened)
___________________________________________

Name: Search Trips
URL: /api/searchtrips
Method: POST
Parameters: NONE
Request
Body:
{
"from_date": "25/05/2022",
"to_date": "28/05/2022",
"from_station":"<STATION_ID_OF_CAIRO>",
"to_station": "<STATION_ID_OF_ASWAN>",
}
Response
Status: 200 OK (If correct)
Status: 500 (If exception happened)
Body:
[
{
"trip_id": "1",
"from_station": "Cairo",
"to_station": "Aswan",
"departure_time": "26/05/2022 15:00:00",
"arrival_time": "27/05/2022 01:30:00",
"available_seats": 36
},
{
"trip_id": "2",
"from_station": "Cairo",
"to_station": "Aswan",
"departure_time": "26/05/2022 15:00:00",
"arrival_time": "27/05/2022 01:30:00",
"available_seats": 25
},
{
"trip_id": "1",
"from_station": "Cairo",
"to_station": "Aswan",
"departure_time": "26/05/2022 15:00:00",
"arrival_time": "27/05/2022 01:30:00",
"available_seats": 35
}
]

_________________________________________________

Name: Book a trip
URL: /api/booktrip
Method: POST
Parameters: NONE
Request
Body:
{
"trip_id": 2,
"user_id": 5
}
Response
Status: 200 OK (If correct)
Status: 500 (If exception happened)
____________________________________________________

Name: View User’s Trips
URL: /api/viewtrips/<user_id>
Method: GET
Parameters: User ID
Request
Body:
NONE
Response
Status: 200 OK (If correct)
Status: 500 (If exception happened)
Body:
[
{
"trip_id": "1",
"from_station": "Cairo",
"to_station": "Aswan",
"departure_time": "26/05/2022 15:00:00",
"arrival_time": "27/05/2022 01:30:00",
"available_seats": 36
}
]

_______________________________________________________

Name: Show user notifications
URL: /api/notifications/{user_id}
Method: GET
Parameters: User ID
Request
Body:
NONE
Response
Status: 200 OK (If correct)
Status: 500 (If exception happened)
Body:
[
{
"message": "You have booked trip from Cairo to Aswan successfully",
"notification_datetime": "23/05/2022 12:00:00"
},
{
"message": "Sorry, Trip Cairo to Luxor have no available seats",
"notification_datetime": "23/05/2022 12:05:00"
}
