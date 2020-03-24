package com.example.demo.dao;

import com.example.demo.model.Precinct;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@Repository("fakeDao")
public class FakePrecinctDao implements PrecinctDao {

    private static HashMap<UUID, Precinct> DB = new HashMap<>();


    @Override
    public int insertPrecinct(UUID id, Precinct p) {
//        DB.put(id, formPrecinctFromObj(p));
        DB.put(id, p);


        return 0;

    }

    @Override
    public Precinct selectPrecinctById(UUID id) {
        return DB.get(id);

    }

    @Override
    public int deletePrecinctById(UUID id) {

        Precinct personMaybe = selectPrecinctById(id);
        if (personMaybe == null) return 1;
        else {

            DB.remove(id);

            return 0;

        }
    }


    @Override
    public int updatePrecinctById(UUID id, Precinct precinct) {


        if (DB.get(id) != null) {
            DB.put(id, precinct);
            return 0;

        }

        System.out.println("record not found");

        return 1;


    }


    @Override
    public List<Precinct> selectAllPrecincts() {

//        return DB.values().stream().collect(Collectors.toList());

        return new ArrayList<>(DB.values());


    }
}
