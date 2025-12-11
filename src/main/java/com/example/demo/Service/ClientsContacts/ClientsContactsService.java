package com.example.demo.Service.ClientsContacts;

import com.example.demo.Model.DTO.ClientsInfoDTO;
import com.example.demo.Repository.ClientsContactsRepository;
import com.example.demo.Service.ClientsInfo.ClientsInfoService;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientsContactsService {
    private static final Logger logger = LoggerFactory.getLogger(ClientsContactsService.class);

    @Autowired
    private ClientsContactsRepository clientsContactsRepository;
    @Transactional
    public void CreateClientsContacts(ClientsInfoDTO clientsInfoDTO) {

    }
}
