package com.example.demo.service;


import com.example.demo.dao.PrecinctDao;
import com.example.demo.dao.StateDao;
import com.example.demo.model.Precinct;
import com.example.demo.model.State;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PrecinctService {


    private final PrecinctDao precinctDao;
    private final StateDao stateDao;

    @Autowired
    public PrecinctService(PrecinctDao precinctDao,StateDao stateDao) {
        this.precinctDao = precinctDao;
        this.stateDao=stateDao;
    }


    public Precinct savePrecinct(Precinct precinct) {


        if (precinct.getId() == null) {

            var tempState = stateDao.findById(precinct.getStateId()).orElse(null);
            if(tempState==null)
            {
                tempState = new State();
                tempState.setId(precinct.getStateId());
                stateDao.save(tempState);
//                stateDao.flush();
            }

            System.err.println(tempState);
            precinct.setState(tempState);


            System.out.println("\n\n\n\n\n\n\n\n\n\n");
            var result = precinctDao.save(precinct);



            System.err.println(precinct.getAdjacentPrecinctIds());
            System.err.println(precinct.getCoordinates().toString());


            precinctDao.findAllById(precinct.getAdjacentPrecinctIds()).forEach(e -> {

                if (!e.getAdjacentPrecinctIds().contains(result.getId())) {
                    e.getAdjacentPrecinctIds().add(result.getId());

                    precinctDao.save(e);


                }

            });


            return result;
        } else {

            var oldPrecinct = precinctDao.findById(precinct.getId()).orElse(null);

            if (oldPrecinct.getAdjacentPrecinctIds().containsAll(precinct.getAdjacentPrecinctIds()) && oldPrecinct.getAdjacentPrecinctIds().size() == precinct.getAdjacentPrecinctIds().size())
                return precinctDao.save(precinct);


            else


                return updateNeighbors(precinct);
        }
    }


    public Precinct updateNeighbors(Precinct newPrecinct) {

        var oldPrecinct = precinctDao.findById(newPrecinct.getId()).orElse(null);


        ArrayList<Long> deleted = new ArrayList(oldPrecinct.getAdjacentPrecinctIds());
        ArrayList<Long> added = new ArrayList(newPrecinct.getAdjacentPrecinctIds());

        deleted.remove(new ArrayList(newPrecinct.getAdjacentPrecinctIds()));
        added.remove(new ArrayList(oldPrecinct.getAdjacentPrecinctIds()));


        for (var i : precinctDao.findAllById(deleted)) {

            //fixme may throw exception here
            i.getAdjacentPrecinctIds().remove(i.getAdjacentPrecinctIds().indexOf(newPrecinct.getId()));
            precinctDao.save(i);
        }


        for (var i : precinctDao.findAllById(added)) {

            //fixme may throw exception here
            i.getAdjacentPrecinctIds().add(newPrecinct.getId());
            precinctDao.save(i);

        }

        return precinctDao.save(newPrecinct);


    }

    public void deletePrecinctById(Long id) {


        precinctDao.deleteById(id);


//        return precinctDao.deletePrecinctById(id);

    }


    public Precinct selectPrecinctById(Long id) {
        return precinctDao.findById(id).orElse(null);
    }

    public List<Precinct> selectAllPrecincts() {
        return precinctDao.findAll();


    }

    public Precinct mergePrecincts(List<Precinct> precincts) {
        //fixme dunno what to do for now

        Precinct merged = precincts.get(0);
        Precinct placeholder = precincts.get(1);

//        List<Long> toRemove = new ArrayList<>();

        placeholder.getAdjacentPrecinctIds().forEach(e -> {


                    if (!merged.getAdjacentPrecinctIds().contains(e)) {

                        if (!e.equals(merged.getId()))
                            merged.getAdjacentPrecinctIds().add(e);

                        var temp = precinctDao.findById(e).orElse(null);
                        temp.getAdjacentPrecinctIds().add(merged.getId());
                        temp.getAdjacentPrecinctIds().remove(placeholder.getId());

                        precinctDao.save(temp);


                    }




                }

        );

        merged.getAdjacentPrecinctIds().remove(placeholder.getId());

        precinctDao.deleteById(placeholder.getId());

        return precinctDao.save(merged);

    }


}
