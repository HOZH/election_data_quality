# hozh-416


for detail informations check logs in my commits

for anyone made a change to the server side code, include your name in the author field to javadoc at the beginning of the code at the file you modified

don't merge anything into the master branch before notifyng me XD -> use side branch to develop your apporach.


finished 4/25: 

change coords to String type
add multiple border attr
change keys to String in uuid v4 format
warp returns in controller with ResponseEntity 
use http status in returns
changing tables’ names
change all the value of keys from auto-generated to manually assigned (excepted for unassigned precinctId at insertion)



finished 4/26: 
  get rid of ethnicity data table, use multiple fields instead
  fix logic for saving precinct /update neighbor precincts
  Exception handling in service components
  change naming in local variables I used in service components
  javadoc














todos:

  before code review:

    Hong:

      high priority:
        DI
        test

      low priority:
        figure out some unhandled exceptions
        Time stamp for log bag
        change data type for log bag
        fix logic for merging two precincts




maybe:
Maybe change maps to String format
Change coords to json format
Maybe change maps to json format





for the merge precincts api, you sent a list of two precincts,

first one is the primary p which will be udpated in the database, and second one is the secondary which will be delete from the data

the first precinct should already be merged in the client with all the attr except for adjacentPrecinctIds
for example, if population for first one is 2 , sec is 5
by the time you send the request, the population for first precinct should already be 7, (sec precinct's attr doesn't matter except for its adjacentPrecinctIds) so is other attrs 
The server is mainly dealing with the recursive realtions betwwen two adjacentPrecinctIds which need to be update bidirectionally


use savePrecinct for both update/add    don't include id (not precinct id, state id, it's id field) in a add operation
good convestion /api via post method for addition, via put method for modification

add delete change comment/other datas by sending a put request to savePrecinct api













data fomart convertion for json

    {


    #precinctId remove this line, id is a auto generated string type . only use when update a exsiting precinct(may throw error/mess up db if the precinct with input id is not in the databse, don't use it when add a precinct!!!!!), dont include this field in add precinct

	
	
	
	
		"precinctId":"4",
        "countyId": "cid",
        "stateId": "sid",
        "canonicalName": "foo-bar",
        "population": "22",
        "multipleBorder":false,
        "demographicDataModified":true
        ,
        
       "white":114,
      "africanAmerican":100,
      "asianPacific":200,
      "nativeAmerican":300,
      "others":400,
      "pacificIslanders":10,
   
    "electionData": {      "CONGRESSIONAL_16_REP":10,  "CONGRESSIONAL_18_REP":200,
      "PRESIDENTIAL_16_REP":300,
          "CONGRESSIONAL_16_DEM":100,
      "CONGRESSIONAL_18_DEM":200,
      "PRESIDENTIAL_16_DEM":300
    },
    
        "adjacentPrecinctIds": ["1","2","3"],
        "enclosingPrecinctIds":["4","5","id for precinct"],
        "logBag": {
          "1": "i dunno what i'm doing",
          "2": "the integer key is the id for each comment"
        },
        "ghost": false,
        
       "coordinates": "[ [ [37.1, -109.05], [41.1, -109.03],[41.1, -102.05], [37.11, -102.04]],[[37.29, -108.58],[40.71, -108.58],[40.71, -102.5], [37.29, -102.5]] ] "   
       
	
	
}



