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
change all the value of keys from auto-generated to manually assigned (excepted for)







todos:

  before code review:

    Hong:

      high priority:
        fix logic for saving precinct /update neighbor precincts
        Exception handling in service components
        change naming in local variables I used in service components

      low priority:
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


    #id remove this line, id is a auto generated string type in uuid v4 format. only use when update a exsiting precinct(may throw error/mess up db if the precinct with input id is not in the databse, don't use it when add a precinct!!!!!), dont include this field in add precinct
    "id": "98c5ff0e-ade9-48fd-b9da-2d5781b8d2c4",
    "canonicalName": "foo-bar",
    "population": 22,
    "ghost": false,
    "multipleBorder": false,
    "stateId": null,
    "countyId": null,

    #coordinates is now a string type
    "coordinates": "[ [ [37.1, -109.05], [41.1, -109.03],[41.1, -102.05], [37.11, -102.04]],[[37.29, -108.58],[40.71, -108.58],[40.71, -102.5], [37.29, -102.5]] ] ",
    "ethnicityData": null,
    "county": {
        "ethnicityData": null,
        "id": "should be a uuid v4 in the string format, cid",
        "canonicalName": null,
        "stateId": null,
        "state": {
            "id": "should be a uuid v4 in the string format, sid",
            "canonicalName": null,
            "counties": null
        }
    },
    "electionData": null,
    "adjacentPrecinctIds": [
        "1",
        "2",
        "3",
        "id for precinct which should be in uuid v4 format"
    ],
    "enclosingPrecinctIds": [
        "4",
        "5",
        "id for precinct which should be in uuid v4 format"
    ],
    "logBag": {
        "1": "i dunno what i'm doing",
        "2": "the integer key is the id for each comment"
    }
}




