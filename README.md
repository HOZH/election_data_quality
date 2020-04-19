# hozh-416


for the merge precincts api, you sent a list of two precincts,

first one is the primary p which will be udpated in the database, and second one is the secondary which will be delete from the data

the first precinct should already be merged in the client with all the attr except for adjacentPrecinctIds
for example, if population for first one is 2 , sec is 5
by the time you send the request, the population for first precinct should already be 7, (sec precinct's attr doesn't matter except for its adjacentPrecinctIds) so is other attrs 
The server is mainly dealing with the recursive realtions betwwen two adjacentPrecinctIds which need to be update bidirectionally


use savePrecinct for both update/add    don't include id (not precinct id, state id, it's id field) in a add operation
good convestion /api via post method for addition, via put method for modification

add delete change comment/other datas by sending a put request to savePrecinct api


select state no yet implemented since no data is available at the moment


data fomart convertion for json


{


    #id remove this line, id is a auto increasing long type. only use when update a exsiting precinct(may throw error/mess up db if the precinct with input id is not in the databse, don't use it when add a precinct!!!!!), dont include this field in add precinct
    "districtId": "a integer string here, like 1 or 2 or 3. all the districtid are unique to each other, it means no two district will have same id when they are in diff state",
    "countyId": "c id string",
    "stateId": "a integer string here, like 1 or 2 or 3, we can make convention about what state is mapping to each key later like 1 is for new york and 2 is for florida",
    "canonicalName": "string",
    "population": 22,
    "ethnicityMap": {"WHITE":199,
      "AFRICAN_AMERICAN":100,
      "ASIAN_PACIFIC":200,
      "HISPANIC":300,
      "NATIVE":400,
      "OTHER":200
    },
    "electionMap": {      "CONGRESSIONAL_16_REP":10,  "CONGRESSIONAL_18_REP":200,
      "PRESIDENTIAL_16_REP":300,
          "CONGRESSIONAL_16_DEM":100,
      "CONGRESSIONAL_18_DEM":200,
      "PRESIDENTIAL_16_DEM":300
    },

    "adjacentPrecinctIds": [
   
 
      ],
     "logBag": {
      "1":"something went wrong",
      "2": "the integer key is the id for each comment"
    },
    "ghost":false,
     "coordinates": [
      
      [
        [1.1,2.2],
        [3.3,4.4]
        ]
     ]
}