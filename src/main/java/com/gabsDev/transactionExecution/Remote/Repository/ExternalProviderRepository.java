package com.gabsDev.transactionExecution.Remote.Repository;

import com.gabsDev.transactionExecution.Remote.Dto.ProviderRequest;
import com.gabsDev.transactionExecution.Remote.Dto.ProviderResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "account-provider", url = "${provider.account.url}")
public interface ExternalProviderRepository {

    @PostMapping("/provider/v1/execute")
    ProviderResponse applyTransaction(@RequestBody ProviderRequest request);
}
