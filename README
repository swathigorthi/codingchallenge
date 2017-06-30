Assumptions:
============
1) UserId exists only once in the users.txt file
2) GroupId exists only once in the groups.txt file
3) The 3 use cases when a user does not have a group are: 
	a) When no group is specified for the user
	b) When the group specified for the user does not exists in the groups.txt file
	c) When the group specified for the user exists in the groups.txt file , but does not contain the user

Design Decisions:
================
1) groups.txt has the major information useful in determining whether a user has group or not. So, this file is being read first before the piping begins
2) Once a user is read from users.txt file, we have all the information ready to check if that user has a group or not. 
The job of performing this check and printing the user id is assigned to another thread(Processor) while the main thread keeps reading from the users.txt and populating the user buffer.
3) The parallel thread needs to know when the input processing is done. This is attained through the flag in the processor class. 


	
Test Cases:
==========
1) user2 is an example for use case a)
2) user8 is an example of use case b)
3) user3, user4 & user6 are examples for user case c)

