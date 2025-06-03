package com.jakem.nettracker.plaid;

import com.plaid.client.model.AccountBase;
import com.plaid.client.model.AccountsGetRequest;
import com.plaid.client.model.AccountsGetResponse;
import com.plaid.client.model.CountryCode;
import com.plaid.client.model.InvestmentsHoldingsGetRequest;
import com.plaid.client.model.InvestmentsHoldingsGetResponse;
import com.plaid.client.model.Holding;
import com.plaid.client.model.ItemPublicTokenExchangeRequest;
import com.plaid.client.model.ItemPublicTokenExchangeResponse;
import com.plaid.client.model.LinkTokenCreateRequest;
import com.plaid.client.model.LinkTokenCreateRequestUser;
import com.plaid.client.model.LinkTokenCreateResponse;
import com.plaid.client.model.Products;
import com.plaid.client.request.PlaidApi;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class PlaidService {

    private final PlaidApi plaidApi;

    public PlaidService(PlaidApi plaidApi) {
        this.plaidApi = plaidApi;
    }

    public String createTokenLink(String clientUserId) throws IOException {
        LinkTokenCreateRequestUser user = new LinkTokenCreateRequestUser()
                .clientUserId(clientUserId);

        LinkTokenCreateRequest request = new LinkTokenCreateRequest()
                .clientName("Nettracker App")
                .products(List.of(Products.AUTH, Products.TRANSACTIONS, Products.INVESTMENTS))
                .countryCodes(List.of(CountryCode.US))
                .language("en")
                .user(user);

        LinkTokenCreateResponse response = plaidApi
                .linkTokenCreate(request)
                .execute()
                .body();

        return response.getLinkToken();
    }

    public String exchangePublicToken(String publicToken) throws IOException {
        ItemPublicTokenExchangeRequest request = new ItemPublicTokenExchangeRequest()
                .publicToken(publicToken);

        ItemPublicTokenExchangeResponse response = plaidApi
                .itemPublicTokenExchange(request)
                .execute()
                .body();

        return response.getAccessToken();
    }

    public List<AccountBase> getAccounts(String accessToken) throws IOException {
        AccountsGetRequest request = new AccountsGetRequest()
                .accessToken(accessToken);

        AccountsGetResponse response = plaidApi
                .accountsGet(request)
                .execute()
                .body();

        return response.getAccounts();
    }

    public List<Holding> getHoldings(String accessToken) throws IOException {
        InvestmentsHoldingsGetRequest request = new InvestmentsHoldingsGetRequest()
                .accessToken(accessToken);

        InvestmentsHoldingsGetResponse response = plaidApi
                .investmentsHoldingsGet(request)
                .execute()
                .body();

        return response.getHoldings();
    }
}