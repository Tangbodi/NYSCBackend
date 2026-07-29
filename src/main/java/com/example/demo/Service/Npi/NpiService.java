package com.example.demo.Service.Npi;

import com.example.demo.Model.VO.NpiProviderVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Map;

@Service
@SuppressWarnings("unchecked")
public class NpiService {
    private static final Logger logger = LoggerFactory.getLogger(NpiService.class);
    private static final String NPPES_URL = "https://npiregistry.cms.hhs.gov/api/";

    @Autowired
    private RestTemplate restTemplate;

    public NpiProviderVO lookupProvider(String npi) {
        logger.info("Looking up NPI: {}", npi);
        try {
            String url = UriComponentsBuilder.fromHttpUrl(NPPES_URL)
                    .queryParam("number", npi)
                    .queryParam("version", "2.1")
                    .build().toUriString();

            Map<String, Object> response = restTemplate.getForObject(url, Map.class);
            if (response == null) return null;

            Integer resultCount = (Integer) response.get("result_count");
            if (resultCount == null || resultCount == 0) return null;

            List<Map<String, Object>> results = (List<Map<String, Object>>) response.get("results");
            if (results == null || results.isEmpty()) return null;

            Map<String, Object> provider = results.get(0);
            Map<String, Object> basic = (Map<String, Object>) provider.get("basic");

            NpiProviderVO vo = new NpiProviderVO();
            vo.setNpi((String) provider.get("number"));

            if (basic != null) {
                String firstName = (String) basic.get("first_name");
                String middleName = (String) basic.get("middle_name");
                String lastName = (String) basic.get("last_name");
                String orgName = (String) basic.get("organization_name");

                vo.setFirstName(firstName);
                vo.setMiddleName(middleName);
                vo.setLastName(lastName);

                if (orgName != null && !orgName.isBlank()) {
                    vo.setName(orgName);
                } else {
                    String fullName = buildFullName(firstName, middleName, lastName);
                    vo.setName(fullName);
                }
                vo.setPhone((String) basic.get("phone"));
            }

            // Primary taxonomy
            List<Map<String, Object>> taxonomies = (List<Map<String, Object>>) provider.get("taxonomies");
            if (taxonomies != null) {
                Map<String, Object> primary = taxonomies.stream()
                        .filter(t -> Boolean.TRUE.equals(t.get("primary")))
                        .findFirst()
                        .orElse(taxonomies.get(0));
                vo.setTaxonomy((String) primary.get("code"));
                vo.setTaxonomyCode((String) primary.get("code"));
                vo.setTaxonomyDescription((String) primary.get("desc"));
            }

            // Location address
            List<Map<String, Object>> addresses = (List<Map<String, Object>>) provider.get("addresses");
            if (addresses != null) {
                Map<String, Object> location = addresses.stream()
                        .filter(a -> "LOCATION".equals(a.get("address_purpose")))
                        .findFirst()
                        .orElse(addresses.get(0));
                vo.setAddress((String) location.get("address_1"));
                vo.setCity((String) location.get("city"));
                vo.setState((String) location.get("state"));
                String postal = (String) location.get("postal_code");
                if (postal != null && postal.length() > 5) postal = postal.substring(0, 5);
                vo.setPostalCode(postal);
            }

            return vo;
        } catch (Exception e) {
            logger.error("NPI lookup failed: {}", e.getMessage(), e);
            return null;
        }
    }

    private String buildFullName(String first, String middle, String last) {
        StringBuilder sb = new StringBuilder();
        if (first != null && !first.isBlank()) sb.append(first).append(" ");
        if (middle != null && !middle.isBlank()) sb.append(middle).append(" ");
        if (last != null && !last.isBlank()) sb.append(last);
        return sb.toString().trim();
    }
}
