package org.prgrms.kdt.customer.repository;

import org.prgrms.kdt.customer.domain.BannedCustomer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

enum BANNED_CUSTOMER_INDEX {
    BANNED_UUID (0), EMAIL (1), NAME (2), DESCRIPTION (3);

    BANNED_CUSTOMER_INDEX(int index) {
        this.index = index;
    }

    private final int index;

    public int getIndex() {
        return index;
    }
}

@Repository
public class FileCustomerRepository implements CustomerRepository {

    @Autowired
    ApplicationContext resourceLoader;

    @Override
    public List<BannedCustomer> getBlackListCSV() throws IOException {

        Resource resource = resourceLoader.getResource("file:customer/blacklist.csv");

        var items = Files.readAllLines(resource.getFile().toPath());
        return items.stream()
                .map(line -> Arrays.asList(line.split(",")))
                .map(list -> new BannedCustomer(
                        UUID.fromString(list.get(BANNED_CUSTOMER_INDEX.BANNED_UUID.getIndex())),
                        list.get(BANNED_CUSTOMER_INDEX.EMAIL.getIndex()),
                        list.get(BANNED_CUSTOMER_INDEX.NAME.getIndex()),
                        list.get(BANNED_CUSTOMER_INDEX.DESCRIPTION.getIndex())))
                .collect(Collectors.toList());
    }
}