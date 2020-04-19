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