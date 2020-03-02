package com.devau.hotel.service;

import com.devau.hotel.entity.RoomBooking;
import com.devau.hotel.repository.RoomBookingRepository;
import com.devau.hotel.repository.RoomTypeRepository;
import com.devau.hotel.repository.specification.RoomBookingSpecification;
import com.devau.hotel.service.base.RoomBookingService;
import com.devau.hotel.service.dto.RoomBookingQueryModel;
import com.devau.hotel.service.dto.RoomBookingReportQueryModel;
import com.devau.hotel.service.dto.RoomBookingTypeQueryModel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Tuple;
import javax.persistence.criteria.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static java.time.temporal.ChronoUnit.DAYS;

@Transactional(readOnly = true)
@Service
public class RoomBookingServiceImpl implements RoomBookingService {
    private final RoomBookingRepository roomBookingRepository;
    private final RoomTypeRepository roomTypeRepository;
    private final EntityManager entityManager;

    public RoomBookingServiceImpl(RoomBookingRepository roomBookingRepository, RoomTypeRepository roomTypeRepository, EntityManager entityManager) {
        this.roomBookingRepository = roomBookingRepository;
        this.roomTypeRepository = roomTypeRepository;
        this.entityManager = entityManager;
    }

    @Override
    public List<RoomBooking> findAll() {
        return this.roomBookingRepository.findAll();
    }

    @Override
    public RoomBooking findById(Integer id) {
        return this.roomBookingRepository.findById(id).get();
    }

    @Transactional
    @Override
    public RoomBooking save(RoomBooking roomBooking) {

        LocalDate date1 = LocalDate.parse(roomBooking.getStartDate());
        LocalDate date2 = LocalDate.parse(roomBooking.getEndDate());
        long dayDiffPoint = DAYS.between(date1, date2);
        if (dayDiffPoint <= 0) {
            throw new RuntimeException("Başlangıç Tarihi Bitiş Tarihinden Küçük veya Eşit olamaz !");
        }
        if (roomBooking.getSumPrice() == null || roomBooking.getSumPrice() == 0) {
            throw new RuntimeException("Ücret Boş Olamaz.");
        }

        if (!this.findAvaibleRoom(roomBooking.getStartDate(), roomBooking.getEndDate(), roomBooking.getRoomId())) {
            throw new RuntimeException("Oda Müsait değil");
        }
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDate localDate = LocalDate.now();
        long dayDiffPointer = DAYS.between(localDate, date1);

        if (dayDiffPointer < 0) {
            throw new RuntimeException("Geçmişe dönük rezervasyon yapılamaz.");
        }

        return this.roomBookingRepository.save(roomBooking);
    }
    @Transactional
    @Override
    public void deleteById(Integer reservationId) {
        this.roomBookingRepository.deleteById(reservationId);

    }
    @Transactional
    @Override
    public void update(RoomBooking roomBooking, Integer id) {
        if (roomBooking.getRoomId() != id) {
            throw new RuntimeException("id ler eşleşmedi");
        }
        this.roomBookingRepository.save(roomBooking);
    }

    @Override
    public Double operation(String startDate, String endDate, Integer roomTypeId) {
        Double operation = 0.0;
        LocalDate date1 = LocalDate.parse(startDate);
        LocalDate date2 = LocalDate.parse(endDate);
        long dayDiffPoint = DAYS.between(date1, date2);

        if (dayDiffPoint > 0) {
            operation = (double) (roomTypeRepository.findById(roomTypeId).get().getPrice() * dayDiffPoint);
        } else throw new RuntimeException("Başlangıç Tarihi Bitiş Tarihinden Küçük veya Eşit olamaz !");


        return operation;

    }

    @Override
    public boolean findAvaibleRoom(String startDate, String endDate, Integer roomNumber) {

    /*    List<RoomBooking> roomBookings = this.roomBookingRepository.findAvaibleRoom(startDate, endDate, roomNumber);

        return roomBookings.isEmpty();*/

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        //Donus icin
        CriteriaQuery<RoomBooking> filter = criteriaBuilder.createQuery(RoomBooking.class);
        //sql-from
        Root<RoomBooking> root = filter.from(RoomBooking.class);
        //where RoomBookng.sumSprice = 10
        Predicate roomNumber1 = criteriaBuilder.equal(root.get("roomId"), roomNumber);
        Predicate startDate1 = criteriaBuilder.between(root.get("startDate"), startDate, endDate);
        Predicate startDate2 = criteriaBuilder.between(root.get("endDate"), startDate, endDate);
        Predicate startDate12 = criteriaBuilder.or(startDate1, startDate2);
        Predicate roomDateNo = criteriaBuilder.and(roomNumber1, startDate12);
        Predicate startDateCompareFromTable = criteriaBuilder.lessThanOrEqualTo(root.get("startDate"), startDate);
        Predicate endDateCompareFromTable = criteriaBuilder.greaterThanOrEqualTo(root.get("startDate"), endDate);
        Predicate dateCompareFromTable = criteriaBuilder.and(startDateCompareFromTable, endDateCompareFromTable);
        Predicate startDateCompare2FromTable = criteriaBuilder.lessThanOrEqualTo(root.get("endDate"), startDate);
        Predicate endDateCompare2FromTable = criteriaBuilder.greaterThanOrEqualTo(root.get("endDate"), endDate);
        Predicate dateCompare2FromTable = criteriaBuilder.and(startDateCompare2FromTable, endDateCompare2FromTable);
        Predicate dateFromTable = criteriaBuilder.or(dateCompareFromTable, dateCompare2FromTable);
        Predicate result = criteriaBuilder.or(roomDateNo, dateFromTable);
        filter.where(criteriaBuilder.and(result));
        List<RoomBooking> roomBooking = entityManager.createQuery(filter).getResultList();
        return roomBooking.isEmpty();


/*
        select a from RoomBooking a where a.roomId = :roomNumber and ((:startDate BETWEEN a.startDate and a.endDate) or (:endDate BETWEEN a.startDate and a.endDate))
        or((a.startDate between :startDate and :endDate)or(a.endDate between :startDate and :endDate))
*/

    }

    @Override
    public List<RoomBooking> findByNameSurname(String nameSurname) {
        /* return this.roomBookingRepository.findByNameSurname(nameSurname);*/

        CriteriaBuilder ca = entityManager.getCriteriaBuilder();
        //Donus icin
        CriteriaQuery<RoomBooking> cq = ca.createQuery(RoomBooking.class);
        //sql-from
        Root<RoomBooking> root = cq.from(RoomBooking.class);
        //where RoomBookng.sumSprice = 10
        Predicate searchField = ca.like(root.get("nameSurname"), "%" + nameSurname + "%");
        cq.where(ca.and(searchField));

        return entityManager.createQuery(cq).getResultList();

    }

    @Override
    public List<RoomBooking> findByNameSurnameWithSpec(String nameSurname) {
        return this.roomBookingRepository.findAll(RoomBookingSpecification.searchByName(nameSurname));
    }

    @Override
    public List<String> jpaTest() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        //Donus icin
        CriteriaQuery<String> roomBookingCriteriaQuery = criteriaBuilder.createQuery(String.class);
        //sql-from
        Root<RoomBooking> root = roomBookingCriteriaQuery.from(RoomBooking.class);
//        Selection<?> selection=root.get()
        roomBookingCriteriaQuery.select(root.get("nameSurname"));
        //where RoomBookng.sumSprice = 10
        Predicate sumPriceEquals = criteriaBuilder.equal(root.get("sumPrice"), 1000);
//        Predicate nameSurnameEquals = cb.equal(root.get("nameSurname"), "Ahmet");
//        order by
//        roomBookingCriteriaQuery.orderBy(criteriaBuilder.asc(root.get("nameSurname")/*, nameSurnameEquals*/));

        roomBookingCriteriaQuery.where(criteriaBuilder.and(sumPriceEquals/*, nameSurnameEquals*/));

        return entityManager.createQuery(roomBookingCriteriaQuery).getResultList();

    }
    @Override
    public List<RoomBooking> jpaTestWithSpec() {
        return this.roomBookingRepository.findAll(RoomBookingSpecification.searchNameAndPrice());
    }


    @Override
    public List<RoomBookingQueryModel> jpaTest2() {
        //Yardımcı Sınıf kullanarak bir den fazla veri getirme
        //oluşturmak için kullanılan builder
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        //Donus icin
        CriteriaQuery<RoomBookingQueryModel> roomBookingCriteriaQuery = criteriaBuilder.createQuery(RoomBookingQueryModel.class);
        //sql-from
        Root<RoomBooking> root = roomBookingCriteriaQuery.from(RoomBooking.class);
//        Selection<?> selection=root.get()
        roomBookingCriteriaQuery.multiselect(root.get("nameSurname"), root.get("tc"), root.get("email"));
        //where RoomBookng.sumSprice = 10
        Predicate sumPriceEquals = criteriaBuilder.equal(root.get("sumPrice"), 1000);
//        Predicate nameSurnameEquals = cb.equal(root.get("nameSurname"), "Ahmet");
        roomBookingCriteriaQuery.where(criteriaBuilder.and(sumPriceEquals/*, nameSurnameEquals*/));

        return entityManager.createQuery(roomBookingCriteriaQuery).getResultList();
    }


    @Override
    public List<RoomBookingQueryModel> jpaTestWithTuple() {
        //oluşturmak için kullanılan builder
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        //Donus icin
        CriteriaQuery<Tuple> roomBookingCriteriaQuery = criteriaBuilder.createTupleQuery();

        //sql-from
        Root<RoomBooking> root = roomBookingCriteriaQuery.from(RoomBooking.class);
//        Selection<?> selection=root.get()
        roomBookingCriteriaQuery.multiselect(root.get("nameSurname"), root.get("tc"), root.get("email"));
        //where RoomBookng.sumSprice = 10
        Predicate sumPriceEquals = criteriaBuilder.equal(root.get("sumPrice"), 1000);
/*
        Predicate nameSurnameEquals = cb.equal(root.get("nameSurname"), "Ahmet");
*/
        roomBookingCriteriaQuery.where(criteriaBuilder.and(sumPriceEquals/*, nameSurnameEquals*/));
        List<Tuple> tupleList = entityManager.createQuery(roomBookingCriteriaQuery).getResultList();

        List<RoomBookingQueryModel> roomBookingQueryModels = new ArrayList<>();

        for (Tuple item : tupleList) {
            roomBookingQueryModels.add(new RoomBookingQueryModel(item.get(0, String.class), item.get(1, String.class), item.get(2, String.class)));
        }

        return roomBookingQueryModels;
    }

    @Override
    public List<RoomBookingReportQueryModel> jpaTestWithTuple1() {
        //oluşturmak için kullanılan builder
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        //Donus icin
        CriteriaQuery<Tuple> roomBookingCriteriaQuery = criteriaBuilder.createTupleQuery();

        //sql-from
        Root<RoomBooking> root = roomBookingCriteriaQuery.from(RoomBooking.class);
//        Selection<?> selection=root.get()
        roomBookingCriteriaQuery.multiselect(root.get("nameSurname"), root.get("roomType").get("type"));
        //where RoomBookng.sumSprice = 10
        Predicate sumPriceEquals = criteriaBuilder.equal(root.get("sumPrice"), 1000);
/*
        Predicate nameSurnameEquals = cb.equal(root.get("nameSurname"), "Ahmet");
*/
        roomBookingCriteriaQuery.where(criteriaBuilder.and(sumPriceEquals/*, nameSurnameEquals*/));
        List<Tuple> tupleList = entityManager.createQuery(roomBookingCriteriaQuery).getResultList();
        List<RoomBookingReportQueryModel> roomBookingReportQueryModels = new ArrayList<>();
        for (Tuple item : tupleList) {
            roomBookingReportQueryModels.add(new RoomBookingReportQueryModel(item.get(0, String.class), item.get(1, String.class)));
        }


        return roomBookingReportQueryModels;
    }

    @Override
    public List<RoomBookingTypeQueryModel> jpaTestWithTuple2() {
        //selection
        //group by
        //count kullanarak Roombooking Tablosunda  roomtype sayılarını bulmak gerek.
        //oluşturmak için kullanılan builder
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        //Donus icin
        CriteriaQuery<Tuple> roomBookingCriteriaQuery = criteriaBuilder.createTupleQuery();
        //sql-from
        Root<RoomBooking> root = roomBookingCriteriaQuery.from(RoomBooking.class);

        roomBookingCriteriaQuery.multiselect(root.get("roomTypeId"), root.get("roomType").get("type"), criteriaBuilder.count(root.get("roomType")), criteriaBuilder.sum(root.get("sumPrice"))).groupBy(root.get("roomType"));
        List<Tuple> tupleList = entityManager.createQuery(roomBookingCriteriaQuery).getResultList();
        List<RoomBookingTypeQueryModel> roomBookingTypeQueryModel = new ArrayList<>();
        for (Tuple item : tupleList) {
            roomBookingTypeQueryModel.add(new RoomBookingTypeQueryModel(item.get(0, Integer.class), item.get(1, String.class), item.get(2, Long.class), item.get(3, Long.class)));
        }


        return roomBookingTypeQueryModel;
    }





}
