package com.devau.hotel.repository.specification;

import com.devau.hotel.entity.RoomBooking;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;

public class RoomBookingSpecification {

    public static Specification<RoomBooking> searchByName(String name){
        return ((root, query, cb) -> cb.like(root.get("nameSurname"), "%" + name + "%"));
    }
    public static Specification<RoomBooking> searchNameAndPrice(){
        return ((root, query, cb) ->cb.equal(root.get("sumPrice"),1000) );
    }

}
