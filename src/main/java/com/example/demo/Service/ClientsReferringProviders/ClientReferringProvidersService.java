package com.example.demo.Service.ClientsReferringProviders;

import com.example.demo.Model.DTO.ClientReferringProvidersDTO;
import com.example.demo.Model.Entity.ClientReferringProviders;
import com.example.demo.Model.Entity.ClientReferringProviderId;
import com.example.demo.Model.VO.ClientReferringProvidersVO;
import com.example.demo.Repository.ClientReferringProvidersRepository;
import com.example.demo.Util.DateTimeConverter;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClientReferringProvidersService {
    private static final Logger logger = LoggerFactory.getLogger(ClientReferringProvidersService.class);

    @Autowired
    private ClientReferringProvidersRepository clientReferringProvidersRepository;

    @Transactional
    public boolean CreateClientsReferringProviders(ClientReferringProvidersDTO dto) {
        logger.info("Creating ClientsReferringProviders for clientId: {}, npiNumber: {}", dto.getClientId(), dto.getNpiNumber());
        try {
            ClientReferringProviderId key = new ClientReferringProviderId();
            key.setClientId(Long.valueOf(dto.getClientId()));
            key.setNpiNumber(dto.getNpiNumber());

            if (clientReferringProvidersRepository.existsById(key)) {
                logger.info("Client {} is already linked with NPI number {}.", dto.getClientId(), dto.getNpiNumber());
                return false;
            }

            ClientReferringProviders entity = new ClientReferringProviders();
            entity.setId(key);
            entity.setProviderFirstName(dto.getFirstName());
            entity.setProviderLastName(dto.getLastName());
            entity.setIsActive(dto.getIsActive());
            entity.setTaxonomyCode(dto.getTaxonomyCode());
            entity.setAddress(dto.getAddress());
            entity.setCity(dto.getCity());
            entity.setState(dto.getState());
            entity.setZipCode(dto.getZipCode());
            entity.setPhone(dto.getPhone());
            entity.setFax(dto.getFax());
            entity.setCreatedAt(Instant.now());
            entity.setModifiedAt(Instant.now());
            clientReferringProvidersRepository.save(entity);
            logger.info("ClientsReferringProviders created successfully.");
            return true;
        } catch (Exception e) {
            logger.error("Failed to create ClientsReferringProviders: {}", e.getMessage(), e);
            throw e;
        }
    }

    public ClientReferringProvidersVO GetClientsReferringProviders(String clientId, String npiNumber) {
        logger.info("Getting ClientsReferringProviders for clientId: {}, npiNumber: {}", clientId, npiNumber);
        try {
            ClientReferringProviderId key = new ClientReferringProviderId();
            key.setClientId(Long.valueOf(clientId));
            key.setNpiNumber(npiNumber);

            ClientReferringProviders entity = clientReferringProvidersRepository.findById(key).orElse(null);
            if (entity != null) {
                logger.info("Found ClientsReferringProviders.");
                return ConvertToClientsReferringProvidersVO(entity);
            } else {
                logger.info("No ClientsReferringProviders found.");
                return null;
            }
        } catch (Exception e) {
            logger.error("Failed to find ClientsReferringProviders: {}", e.getMessage(), e);
        }
        return null;
    }

    public List<ClientReferringProvidersVO> GetAllClientsReferringProviders(String clientId) {
        logger.info("Getting all referring providers for clientId: {}", clientId);
        try {
            List<ClientReferringProviders> list = clientReferringProvidersRepository.findAllByClientId(Long.valueOf(clientId));
            if (!list.isEmpty()) {
                return list.stream()
                        .map(this::ConvertToClientsReferringProvidersVO)
                        .collect(Collectors.toList());
            } else {
                logger.info("No referring providers found for clientId: {}", clientId);
                return Collections.emptyList();
            }
        } catch (Exception e) {
            logger.error("Failed to get referring providers: {}", e.getMessage(), e);
        }
        return Collections.emptyList();
    }

    @Transactional
    public void UpdateClientsReferringProviders(ClientReferringProvidersDTO dto) {
        logger.info("Updating ClientsReferringProviders for clientId: {}, npiNumber: {}", dto.getClientId(), dto.getNpiNumber());
        try {
            clientReferringProvidersRepository.UpdateClientsReferringProvider(
                    Long.valueOf(dto.getClientId()),
                    dto.getNpiNumber(),
                    dto.getFirstName(),
                    dto.getLastName(),
                    dto.getIsActive(),
                    dto.getTaxonomyCode(),
                    dto.getPhone(),
                    dto.getFax(),
                    dto.getAddress(),
                    dto.getCity(),
                    dto.getState(),
                    dto.getZipCode()
            );
            logger.info("ClientsReferringProviders updated successfully.");
        } catch (Exception e) {
            logger.error("Failed to update ClientsReferringProviders: {}", e.getMessage(), e);
            throw e;
        }
    }

    @Transactional
    public void DeleteClientsReferringProviders(String clientId, String npiNumber) {
        logger.info("Deleting ClientsReferringProviders for clientId: {}, npiNumber: {}", clientId, npiNumber);
        try {
            clientReferringProvidersRepository.deleteByClientIdAndNpiNumber(Long.valueOf(clientId), npiNumber);
            logger.info("ClientsReferringProviders deleted successfully.");
        } catch (Exception e) {
            logger.error("Failed to delete ClientsReferringProviders: {}", e.getMessage(), e);
            throw e;
        }
    }

    public ClientReferringProvidersVO ConvertToClientsReferringProvidersVO(ClientReferringProviders entity) {
        ClientReferringProvidersVO vo = new ClientReferringProvidersVO();
        vo.setClientId(String.valueOf(entity.getId().getClientId()));
        vo.setNpiNumber(entity.getId().getNpiNumber());
        vo.setFirstName(entity.getProviderFirstName());
        vo.setLastName(entity.getProviderLastName());
        vo.setIsActive(entity.getIsActive());
        vo.setTaxonomyCode(entity.getTaxonomyCode());
        vo.setPhone(entity.getPhone());
        vo.setFax(entity.getFax());
        vo.setAddress(entity.getAddress());
        vo.setCity(entity.getCity());
        vo.setState(entity.getState());
        vo.setZipCode(entity.getZipCode());
        vo.setCreatedAt(DateTimeConverter.DateTimeConvertFromInstant(entity.getCreatedAt()));
        vo.setModifiedAt(DateTimeConverter.DateTimeConvertFromInstant(entity.getModifiedAt()));
        return vo;
    }
}
