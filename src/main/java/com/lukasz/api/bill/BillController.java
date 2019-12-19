package com.lukasz.api.bill;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/bills")
public class BillController {

    private BillService billService;

    @Autowired
    public BillController(BillService billService) {
        this.billService = billService;
    }


    @GetMapping()
    ResponseEntity<List<BillDto>> getBill( @RequestParam(required = false) UUID clientId) {
        List<BillDto> billDtos = billService.getBills(clientId);
        return new ResponseEntity<>(billDtos, HttpStatus.OK);
    }

    @GetMapping(value = "/{billId}")
    ResponseEntity<BillDto> getBillById(@PathVariable Long billId) {
        BillDto billDto = billService.getBillById(billId);
        return new ResponseEntity<>(billDto, HttpStatus.OK);
    }

    /*
        @GetMapping(value = "/{clientId}")
        ResponseEntity<BillDto> getLastBillOfClient(@PathVariable UUID clientId) {
            BillDto billDto = billService.getLastBillOfClient(clientId);
            return new ResponseEntity<>(billDto, HttpStatus.OK);
        }
    */
    @PostMapping()
    public ResponseEntity<BillDto> addBill(@RequestBody BillDto billDto) {
        BillDto responseDto = billService.addBill(billDto);
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    /*
        @PutMapping(value = "/{billId}")
        ResponseEntity<BillDto> updateBill(@RequestBody BillDto billDto, @PathVariable Long billId) {
            BillDto responseDto = billService.updateBill(billDto, billId);
            return new ResponseEntity<>(responseDto, HttpStatus.OK);
        }
    */
    @PatchMapping(value = "/{billId}")
    ResponseEntity<BillDto> updateBill(@RequestBody BillDto billDto, @PathVariable Long billId) {
        BillDto responseDto = billService.updateBill(billDto, billId);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{billId}")
    ResponseEntity<BillDto> deleteBill(@PathVariable Long billId) {
        BillDto billDto = billService.deleteBill(billId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}