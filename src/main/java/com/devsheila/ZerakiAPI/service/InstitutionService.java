package com.devsheila.ZerakiAPI.service;


import com.devsheila.ZerakiAPI.model.Course;
import com.devsheila.ZerakiAPI.model.Institution;
import com.devsheila.ZerakiAPI.payload.ResponseBody;
import com.devsheila.ZerakiAPI.repository.InstitutionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InstitutionService {

    @Autowired
    private InstitutionRepository institutionRepository;

    //CREATE AN INSTITUTION
    public ResponseBody addInstitution(Institution institution) {
        ResponseBody responseBody;

        if (institutionRepository.findByName(institution.getName()).isPresent()) {
            responseBody= new ResponseBody(false,"Institution with the same name already exists", (Institution) null);
            return responseBody; // Institution with the same name already exists
        }
        institutionRepository.save(institution);
        responseBody= new ResponseBody(true,"Institution created successfully",institution);

        return responseBody;
    }

    //GET ALL EXISTENT INSTITUTIONS
    public List<Institution> getAllInstitutions() {
        return institutionRepository.findAll();
    }


    //GET INSTITUTION BY ID
    public Institution getInstitution(Long id) {
        Optional<Institution> institutionOptional = institutionRepository.findById(id);

        if (institutionOptional.isPresent()) {
            Institution institution = institutionOptional.get();
            return institution;
        } else {
            return null;
        }
    }



    //SEARCH INSTITUTTIONS BY A QUESRY
    public List<Institution> searchInstitutions(String query) {
        return institutionRepository.findByNameContainingIgnoreCase(query);
    }


    //SORT INSTITUTION
    public List<Institution> sortInstitutions(String order) {
        if (order.equalsIgnoreCase("asc")) {
            return institutionRepository.findAllByOrderByNameAsc();
        } else {
            return institutionRepository.findAllByOrderByNameDesc();
        }
    }

    //UPDATE INSTITUTION NAME
    public boolean updateInstitutionName(Long id, String newName) {
        Optional<Institution> institutionOptional = institutionRepository.findById(id);//find current instituition
        Institution institution = institutionOptional.get();

        if (institution != null) {

            if (institutionRepository.findByName(newName).isEmpty()) {//if no other institutions have the same name
                institution.setName(newName);
                institutionRepository.save(institution);
                return true;
            }
        }
        return false; // Another institution with the same name already exists
    }

    //DELETE AN INSTITUTION
    public boolean deleteInstitution(Long id) {
        Optional<Institution> institutionOptional = institutionRepository.findById(id);
        if (institutionOptional.isPresent()) {
            Institution institution = institutionOptional.get();
            if (institution.getCourses().isEmpty()) {
                institutionRepository.deleteById(id);
                return true;
            }
        }
        return false; // Cannot delete institution assigned to a course
    }





}

