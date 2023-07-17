package com.devsheila.ZerakiAPI.controller;

import com.devsheila.ZerakiAPI.model.Course;
import com.devsheila.ZerakiAPI.model.Institution;
import com.devsheila.ZerakiAPI.payload.ResponseBody;
import com.devsheila.ZerakiAPI.response.ResponseHandler;
import com.devsheila.ZerakiAPI.service.InstitutionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/institutions")
public class InstitutionController {


    private final InstitutionService institutionService;

    @Autowired
    public InstitutionController(InstitutionService institutionService){
        this.institutionService=institutionService;
    }

    //CREATE INSTITUTION
    @PostMapping
    public ResponseEntity<Object> addInstitution(@RequestBody Institution institution) {
        ResponseBody responseBody=institutionService.addInstitution(institution);
        boolean isSaved = responseBody.success;
        if (isSaved) {//IF SUCCESSFUL
            return ResponseHandler.responseBuilder(responseBody.message, HttpStatus.OK,responseBody.institution);

        } else {//IF FAILED
            return ResponseHandler.responseBuilder(responseBody.message, HttpStatus.BAD_REQUEST,responseBody.institution);

        }
    }

    //GEL ALL INSTITUTIONS
    @GetMapping
    public ResponseEntity<List<Institution>> getAllInstitutions() {
        List<Institution> institutions = institutionService.getAllInstitutions();
        return ResponseEntity.ok(institutions);
    }

    //GET SPECIFIC INSTITUITION
    @GetMapping("/{id}")
//    @ApiOperation(value ="Institutions id", notes="Provide institution details",response = ResponseEntity.class)
    public ResponseEntity<Object> getInstitutionDetails(@PathVariable("id") Long id)
    {
        Institution institution=institutionService.getInstitution(id);
        if(institution != null){
            return ResponseHandler.responseBuilder("Requested Institution are given here", HttpStatus.OK, institution);
        }else{
            return ResponseHandler.responseBuilder("Instituition does not exist", HttpStatus.BAD_REQUEST, null);
        }
    }



    //SEARCH INSTITUTION
    @GetMapping("/search")
    public ResponseEntity<Object> searchInstitutions(@RequestParam("query") String query) {

        return ResponseHandler.responseBuilder("Search results of "+query+" in institutions", HttpStatus.OK,institutionService.searchInstitutions(query));
    }



    //SORT INSTITUTIONS BY DESC OR ASC
    @GetMapping("/sort")
    public ResponseEntity<Object> sortInstitutions(@RequestParam("order") String order) {

        if(order.equalsIgnoreCase("asc")|| order.equalsIgnoreCase("desc")){
            List<Institution> institutions = institutionService.sortInstitutions(order);
            return ResponseHandler.responseBuilder("Sorted list of institutions", HttpStatus.OK, institutions);
        }else{
            return ResponseHandler.responseBuilder("Invalid sort order request", HttpStatus.BAD_REQUEST, null);
        }

    }

    //DELETE INSTITUTION BY ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteInstitution(@PathVariable("id") Long id) {

        Institution institution=institutionService.getInstitution(id);
        if(institution != null){
            boolean isDeleted = institutionService.deleteInstitution(id);
            if (isDeleted) {
                return ResponseHandler.responseBuilder("Institution deleted successfully.", HttpStatus.OK, institution);
            } else {
                return ResponseHandler.responseBuilder("Cannot delete institution assigned to a course.", HttpStatus.BAD_REQUEST, null);
            }
        }else{
            return ResponseHandler.responseBuilder("Institution does not exist", HttpStatus.BAD_REQUEST, null);
        }

    }


    //UPDATE INSTITUITION BY NAME
    @PutMapping("/{id}")
    public ResponseEntity<Object> updateInstitutionName(@PathVariable("id") Long id, @RequestBody String newName) {
        //check if institution exists
        Institution institution=institutionService.getInstitution(id);
        if(institution != null){
            boolean isUpdated = institutionService.updateInstitutionName(id, newName);
            if (isUpdated) {
                return ResponseHandler.responseBuilder("Institution name updated successfully.", HttpStatus.OK, institution);
            } else {
                return ResponseHandler.responseBuilder("Another institution with the same name already exists.",HttpStatus.BAD_REQUEST,null);
            }
        }else{
            //throw error if it doesn't
            return ResponseHandler.responseBuilder("Instituition does not exist", HttpStatus.BAD_REQUEST, null);

        }
    }



}
