# otest


Requirements:

DbSystem (1) --------> (*) WorkRequest

Three endpoints:
* saveDbSystem()
  * Create db system
  * return id ?
  
* createWorkRequest(dbSystemId)
  * create a request for db system by id
  * x seconds to process (0 <= x <= 60sec)
    * **assign random num on receiving request?** - yes
    * 5 requests max, reject, return error
    * when 5 request received - lock db
      * lock for 2 mins
      * **unlock on next request? or on timer?** - next request
      
* getWorkRequests(dbSystemId)
  * return the list of work request

