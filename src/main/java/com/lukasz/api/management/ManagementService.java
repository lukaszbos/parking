package com.lukasz.api.management;

import com.lukasz.api.bill.Bill;
import com.lukasz.api.bill.BillDto;
import com.lukasz.api.bill.BillMapper;
import com.lukasz.api.bill.BillService;
import com.lukasz.api.client.ClientService;
import com.lukasz.api.ticket.TicketDto;
import com.lukasz.api.ticket.TicketService;
import com.lukasz.api.utils.PaymentService;
import com.lukasz.api.utils.Tariff;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import static com.lukasz.api.utils.ChargeCalculator.calculateCharge;
import static com.lukasz.api.utils.ChargeCalculator.calculateTimeSpent;

@Component
public class ManagementService {

    private final BillService billService;
    private final TicketService ticketService;
    private final BillMapper billMapper;
    private final ClientService clientService;
    private final PaymentService paymentService;

    protected final Log logger = LogFactory.getLog(getClass());

    @Autowired
    ManagementService(BillService billService, TicketService ticketService, BillMapper billMapper,
                      ClientService clientService, PaymentService paymentService) {
        this.billService = billService;
        this.ticketService = ticketService;
        this.billMapper = billMapper;
        this.clientService = clientService;
        this.paymentService = paymentService;
    }

    public TicketDto addTicket(ManagementDto managementDto) {
//        BillDto billDto = new BillDto(managementDto.getBillId(), managementDto.getParking(), managementDto.getClient());
//        billController.addBill(billDto);
//        ResponseEntity responseEntityBill = billController.addBill(billDto);
//        logger.info("Zwrotka z kontrolera bill: " + responseEntityBill);

        TicketDto ticketDto = new TicketDto(managementDto.getTicketId(), managementDto.getEnteredParkingAt(), managementDto.isClientAtParking(),
                managementDto.getLeftParkingAt(), managementDto.getClient(), managementDto.getCarPark());
        TicketDto responseDto = ticketService.addTicket(ticketDto);
        //BillDto billDto = billService.addBill()
        logger.info("Zwrotka z kontrolera ticket: " + responseDto);

        return responseDto;
    }

    //TODO to wszystko powinno byc robione w mgmt maperze
    public TicketDto updateTicketAndAddBill(ManagementDto managementDto, UUID clientId) {
        TicketDto ticketDto = ticketService.getLastTicketOfClient(clientId);
        logger.info("Taki ticket otrzymalem z mgmt " + ticketDto);

        ticketDto.setLeftParkingAt(managementDto.getLeftParkingAt());
        ticketDto.setClientAtParking(managementDto.isClientAtParking());
        TicketDto responseDto = ticketService.updateTicket(ticketDto, ticketDto.getTicketId());

        if (clientLeftParking(ticketDto)) {
            LocalDateTime parkingDate = ticketDto.getEnteredParkingAt();
            LocalDateTime leftParkingDate = ticketDto.getLeftParkingAt();
            Tariff tariff = new Tariff(BigDecimal.valueOf(10.29), BigDecimal.valueOf(8.00));
            BigDecimal cost = calculateCharge(parkingDate, leftParkingDate, tariff);
            int timeSpent = calculateTimeSpent(parkingDate, leftParkingDate);

            //Client clientPass = clientService.getClient(managementDto.getClient().getClientId());
            Bill bill = new Bill(timeSpent, cost, ticketService.getClientById(clientId), managementDto.getCarPark());
            BillDto billDto = billMapper.toDto(bill);
            logger.info("Gotowy billDto " + billDto);
            BillDto addedBillDto = billService.addBill(billDto);
            logger.info("Taki bill dodalem " + addedBillDto);
            //paymentService.addPayment((HttpServletRequest) managementDto);
            //PŁATNOŚCI TUTAJ - PAYMENT


            //return addedBillDto;
        }

        return responseDto;
    }

    private boolean clientLeftParking(TicketDto requestDto) {
        return !requestDto.isClientAtParking();
    }

}
